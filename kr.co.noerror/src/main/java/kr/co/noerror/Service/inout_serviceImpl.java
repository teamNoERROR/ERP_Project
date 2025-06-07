package kr.co.noerror.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.inout_DAO;
import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.inout_DTO;
import kr.co.noerror.Mapper.inout_mapper;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_random;

@Service
public class inout_serviceImpl implements inout_service {

	@Autowired
	inout_mapper io_mapper;
	
	@Resource(name="inout_DAO")
	inout_DAO io_dao;
	
	@Resource(name="M_random")  //랜덤숫자생성 모델 
	M_random m_rno;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
		
	@Resource(name="inout_DTO")
	inout_DTO io_dto;
	
	List<String> list = null; 
	Map<String, String> map = null;
	
	//입고등록 인서트
	@Override
	public int inbnd_insert(String inbnd_item) {
		String inb_code;  //랜덤번호 생성 
		int dupl;  //중복확인
		
		do {
			inb_code = "INB-"+this.m_rno.random_no(); 
	        dupl = this.io_dao.code_dupl_inb(inb_code);
	    } while (dupl > 0);
		
	    
		JSONArray ja = new JSONArray(inbnd_item);
		int item_ea = ja.length();
		
		List<inout_DTO> itm_list = new ArrayList<>();
		
		for (int w = 0; w < item_ea; w++) {
		    JSONObject jo = ja.getJSONObject(w);
		    
		    inout_DTO dto = new inout_DTO();
		    
		    dto.setPCH_CODE(jo.getString("PCH_CODE")+"_"+(w+1));
		    dto.setITEM_CODE(jo.getString("ITEM_CODE"));
		    dto.setITEM_DEL(jo.getString("ITEM_DEL"));
		    dto.setITEM_QTY(jo.getInt("ITEM_QTY"));
		    dto.setITEM_EXP(jo.getString("ITEM_EXP"));
		    
		    dto.setWH_CODE(jo.getString("WH_CODE"));
		    dto.setIN_STATUS(jo.getString("IN_STATUS"));
		    dto.setINBOUND_DATE(jo.getString("INBOUND_DATE")); 
		    dto.setEMPLOYEE_CODE(jo.getString("EMPLOYEE_CODE"));
		    dto.setINB_MEMO(jo.getString("INB_MEMO")); 
	        dto.setINBOUND_CODE(inb_code);

		    itm_list.add(dto);
		   
		}
		int f_result=0;
		int result = 0;
		int count = 0;
		for (inout_DTO in_dto : itm_list) {
			result += this.io_dao.inbnd_insert(in_dto);
			
			if(result >= 1) {
				count++;
			}
		}
		System.out.println("svcIm : " + result);
		System.out.println("svcIm2 : " + count);
		
		
		if(itm_list.size() == count) {
			f_result = result;
			
		}else {
			f_result = 0;
		}
		return f_result;
	}

	//입고리스트 총개수 
	@Override
	public int inbound_total(String keyword) {
		this.map = new HashMap<>();
		this.map.put("keyword", String.valueOf(keyword).trim());
		
		int inbound_total = this.io_dao.inbound_total(map);
		return inbound_total;
	}
	
	//입고리스트 
	@Override
	public List<inout_DTO> inbound_all_list(String keyword, Integer pageno, int post_ea) {
		int start = (pageno - 1) * post_ea;
		int count = post_ea; 
		
		Map<String, Object> map = new HashMap<>();
		map.put("keyword", String.valueOf(keyword).trim());
		map.put("start", start);
		map.put("count", count);
		
		List<inout_DTO> inbound_all_list = this.io_dao.inbound_all_list(map);  
		return inbound_all_list;
	}

	//입고건 상세보기 
	@Override
	public List<inout_DTO> inbound_detail(String inbnd_code) {
		List<inout_DTO> inbound_detail = this.io_dao.inbound_detail(inbnd_code);
		return inbound_detail;
	}

}
