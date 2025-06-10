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

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.outbound_DAO;
import kr.co.noerror.DTO.inbound_DTO;
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
	public int outbnd_insert(String out_pds) {
		 //고유코드생성
        String out_code = this.makeCode.generate("OUT-", code -> this.out_dao.code_dupl_out(code) > 0);
        
        JSONArray ja = new JSONArray(out_pds);
		JSONObject jo1 = ja.getJSONObject(0);
		outbound_DTO dto = new outbound_DTO();
		
		dto.setORD_CODE(jo1.getString("ORD_CODE"));
	    dto.setWH_CODE(jo1.getString("ITEM_CODE"));
	    dto.setOUT_STATUS(jo1.getString("OUT_STATUS"));
	    dto.setOUTBOUND_DATE(jo1.getString("OUTBOUND_DATE"));
	    dto.setOUT_MEMO(jo1.getString("OUT_MEMO"));
	    
	    int pd_ea = ja.length();
		List<outbound_DTO> out_pd_list = new ArrayList<>();
		
		
		for (int w = 0; w < pd_ea; w++) {
		    JSONObject jo2 = ja.getJSONObject(w);
		    outbound_DTO dto2 = new outbound_DTO();
		    dto2.setPRODUCT_CODE(jo2.getString("PRODUCT_CODE"));
		    dto2.setPRODUCT_QTY(jo2.getInt("PRODUCT_QTY"));
		    dto2.setIND_OUT_STATUS(jo2.getString("IND_OUT_STATUS")); 

		    out_pd_list.add(dto);
		   
		}
        
        
        
		return 0;
	}
}
