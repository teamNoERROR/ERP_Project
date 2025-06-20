package kr.co.noerror.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.outbound_DAO;
import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.inbound_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.outbound_DTO;
import kr.co.noerror.Mapper.outbound_mapper;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_unique_code_generator;

@Service
public class outbound_serviceImpl implements outbound_service{
	
	@Autowired
	outbound_mapper out_mapper;
	
	@Resource(name="outbound_DAO")
	outbound_DAO out_dao;
	
	@Resource(name="M_unique_code_generator")
	M_unique_code_generator makeCode;  //고유코드 생성모델
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	List<String> list = null; 
	Map<String, String> map = null;

	//출고리스트 
	@Override
	public List<outbound_DTO> outbound_all_list(String keyword, Integer pageno, int post_ea, String[] out_status_lst) {
		int start = (pageno - 1) * post_ea;
		int count = post_ea; 
		
		List<String> out_statusList = (out_status_lst == null) ? Collections.emptyList() : Arrays.asList(out_status_lst);
		Map<String, Object> mapp = new HashMap<>();
		mapp.put("keyword", keyword);
		mapp.put("start", start);
		mapp.put("count", count);
		mapp.put("OUT_STATUS", out_statusList);
		
		List<outbound_DTO> outbound_all_list = this.out_dao.outbound_all_list(mapp);  
		return outbound_all_list;
		
	}

	//출고 등록 
	@Override
	@Transactional
	public String outbnd_insert(String out_pds) {
		
		 //고유코드생성
        String out_code = this.makeCode.generate("OUT-", code -> this.out_dao.code_dupl_out(code) > 0);
        
        JSONArray ja = new JSONArray(out_pds);
        JSONObject jo = ja.getJSONObject(0);
		int pd_ea = ja.length();
		
		outbound_DTO out_dto = new outbound_DTO();
		
		//OUTBOUND 테이블에 저장
		out_dto.setOUTBOUND_CODE(out_code);
		out_dto.setORD_CODE(jo.getString("ORD_CODE"));
		out_dto.setOUT_STATUS(jo.getString("OUT_STATUS"));
		out_dto.setOUTBOUND_DATE(jo.getString("OUTBOUND_DATE"));
		out_dto.setEMPLOYEE_CODE(jo.getString("EMPLOYEE_CODE"));
		out_dto.setOUT_MEMO(jo.getString("OUT_MEMO"));
		
		int result = this.out_dao.outbnd_insert(out_dto);
		if(result<=0) {
			throw new RuntimeException("출고 테이블 저장 실패");
		}
		
		List<outbound_DTO> out_pd_list = new ArrayList<>();
		
		//OUTBOUND_DETAIL 테이블에 저장 
		for (int w = 1; w < pd_ea; w++) {
			JSONObject jo2 = ja.getJSONObject(w);
			 outbound_DTO out_detail = new outbound_DTO();
			 
			 out_detail.setOUTBOUND_CODE(out_dto.getOUTBOUND_CODE());
			 out_detail.setORD_CODE(out_dto.getORD_CODE());
			 out_detail.setPRODUCT_CODE(jo2.getString("PRODUCT_CODE"));
			 out_detail.setOUT_PRODUCT_QTY(jo2.getInt("OUT_PRODUCT_QTY"));

			 out_pd_list.add(out_detail);
		}
		
		int count = 0;
		for (outbound_DTO detail_dto : out_pd_list) {
			int inserted = this.out_dao.outbnd_dtl_insert(detail_dto);
	        if (inserted > 0) {
	        	count++;
	        }
		}
		
		if (count < out_pd_list.size()) {
	        throw new RuntimeException("출고 상세 저장 실패: 일부 항목 저장 실패");
	    }
		
		
		//완제품 재고 출고처리
		List<String> pd_codes = new ArrayList<>(); 
		List<Integer> pd_qtys = new ArrayList<>();
		
		JSONArray out_ja = new JSONArray(out_pds);
		for (int f = 1; f < pd_ea; f++) {
			JSONObject out_jo = out_ja.getJSONObject(f);
			
			String pdcode = out_jo.getString("PRODUCT_CODE");
			int pd_out_qty = out_jo.getInt("OUT_PRODUCT_QTY");

			pd_codes.add(pdcode);
			pd_qtys.add(pd_out_qty);
		}
		
		System.out.println("pd_codes : " + pd_codes);
		System.out.println("pd_out_qty : " + pd_qtys);
		
		for (int i = 0; i < pd_codes.size(); i++) {
			String pdCode = pd_codes.get(i);
			int ordPdQty = pd_qtys.get(i); // 제품별 출고 수량

			List<IOSF_DTO> outpdinfo_result = this.out_dao.out_productList(pdCode);

			for (IOSF_DTO lot : outpdinfo_result) {
				if (ordPdQty <= 0) break;

				int availableQty = lot.getPd_qty();
				int usedQty = Math.min(availableQty, ordPdQty);

				// 출고완료 INSERT
				Map<String, Object> outParams = new HashMap<>();
				outParams.put("wh_code", lot.getWh_code());
				outParams.put("plan_code", lot.getPlan_code());
				outParams.put("product_code", pdCode);
				outParams.put("pd_qty", usedQty);
				outParams.put("employee_code", lot.getEmployee_code());
				outParams.put("inv_lot", lot.getInv_lot());

				this.out_dao.out_fswh_result(outParams);
				ordPdQty -= usedQty;
			}
			
			if (ordPdQty > 0) {
				throw new RuntimeException("재고 부족: " + pdCode);
			}
		}
			
		return "all_complate";
	}

	//출고 상세보기 
	@Override
	public List<outbound_DTO> outbound_detail(String ob_code, String ord_code) {
		this.map = new HashMap<>();
		this.map.put("outbnd_code", ob_code);
		this.map.put("ord_cd", ord_code);
		
		List<outbound_DTO> outbound_detail = this.out_dao.outbound_detail(this.map);
		
		
//		String ind_pchcd="";
//		int pch_qty_total=0;  //총 주문수량 
//		int inb_qty_total=0;  //총 입고수량 
//		int itm_cost_total=0;  //총 제품 단가
//		int pch_amount_total=0;  //총 구매금액
//		
//		for(outbound_DTO sum : outbound_detail) {
//			pch_qty_total += sum.getP_QTY();        // P_QTY 누적
//		    inb_qty_total += sum.getITEM_QTY();     // ITEM_QTY 누적
//		    itm_cost_total += sum.getITEM_COST();   // ITEM_COST 누적 (단가 총합이 맞는지 확인 필요)
//		    pch_amount_total += sum.getPAY_AMOUNT(); // PAY_AMOUNT 누적
//		    
//		    ind_pchcd += sum.getIND_PCH_CD();
//		}
		return outbound_detail;
	}

	//완제품창고리스트이동(출고처리)
	@Override
	public List<IOSF_DTO> fswh_all_list(String keyword, Integer pageno, int post_ea) {
		int start = (pageno - 1) * post_ea;
		int count = post_ea; 
		
		Map<String, Object> mapp = new HashMap<>();
		mapp.put("keyword", keyword);
		mapp.put("start", start);
		mapp.put("count", count);
		
		List<IOSF_DTO> fswh_all_list = this.out_dao.fswh_all_list(mapp);
		return fswh_all_list;
	}



//	@Override
//	public void out_mtwh_result(Map<String, Object> outParams) {
//		// TODO Auto-generated method stub
//		
//	}
}
