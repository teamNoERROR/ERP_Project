package kr.co.noerror.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.bom_DAO;
import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.del_DTO;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.Mapper.bom_mapper;
import kr.co.noerror.Model.M_file;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_unique_code_generator;

@Service
public class bom_serviceImpl implements bom_service{
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	bom_mapper b_mapper;
	
	@Resource(name="bom_DAO")
	bom_DAO b_dao;

	@Resource(name="M_unique_code_generator")
	M_unique_code_generator makeCode;  //고유코드 생성모델
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	@Resource(name="M_file")   //파일첨부관련모델 
	M_file m_file;
	
	@Resource(name="file_DTO")  //파일첨부DTO
	file_DTO f_dto;
	
	@Resource(name="bom_DTO") 
	bom_DTO b_DTO;
	
	List<String> list = null; 
	Map<String, String> map = null;
	
	//BOM등록여부 체크 
	@Override
	public int bom_check(String pd_code) {
		int bom_ck = this.b_dao.bom_check(pd_code);
		return bom_ck;
	}

	//BOM 상세보기 
	@Override
	public List<bom_DTO> bom_detail(String pd_code) {
		List<bom_DTO> bom_detail = this.b_dao.bom_detail(pd_code);
		return bom_detail;
	}

	//BOM 등록
	@Override
	public int bom_insert( String insert_item) {
		//고유코드생성
        String bom_code = this.makeCode.generate("BOM-", code -> this.b_dao.code_dupl_bom(code) > 0);
	    
		JSONArray ja = new JSONArray(insert_item);
		int item_ea = ja.length();
		
		List<bom_DTO> bom_list = new ArrayList<>();
		
		for (int w = 0; w < item_ea; w++) {
		    JSONObject jo = ja.getJSONObject(w);
		    
		    bom_DTO b_DTO = new bom_DTO();
		    
		    b_DTO.setBOM_CODE(bom_code);  //bom 코드 장착 
		    
		    b_DTO.setPRODUCT_CODE(jo.getString("cProductCode"));
		    b_DTO.setITEM_CODE(jo.getString("cItemCode"));
		    b_DTO.setBOM_QTY(jo.getInt("bomQty"));
		    b_DTO.setBOM_UNIT(jo.getString("unit"));
		    
		    b_DTO.setBOM_SEQ_NO(w+1);
		    
		    if("product".equals(jo.getString("pd_type"))) {
		    	b_DTO.setPARENT_IDX(null); 
			    b_DTO.setBOM_LEVEL(0);
			    
		    }
		    else if("half".equals(jo.getString("pd_type"))) {
		    	
		    	Integer pidx = this.b_dao.select_pidx(bom_code);  //부모bidx 찾기
		    	System.out.println("select_pidx 반환값 = " + pidx);
		    	b_DTO.setPARENT_IDX(pidx); 
			    b_DTO.setBOM_LEVEL(1);
		    }
		    bom_list.add(b_DTO);
		   
		}
		int result = 0;
		for (bom_DTO bom : bom_list) {
			result += this.b_dao.bom_insert(bom);
		}
		System.out.println(result);
//		bom_DTO result = (this.b_DTO);
		return result;
	}

	//BOM등록된제품 전체개수 
	@Override
	public int bom_all_ea_sch(String sclass, String keyword) {
		this.map = new HashMap<>();
		this.map.put("sclass", sclass);
		this.map.put("keyword",keyword);
		
		int bom_total = this.b_dao.bom_all_ea_sch(map);
		return bom_total;
	}

	 //BOM등록된제품 리스트
	@Override
	public List<bom_DTO> bom_all_list_sch(String sclass, String keyword, Integer pageno, int post_ea, String parent) {
		int start = (pageno - 1) * post_ea;
		int count = post_ea; 
		
		Map<String, Object> map = new HashMap<>();
		map.put("keyword",keyword);
		map.put("sclass", sclass);
		map.put("start", start);
		map.put("count", count);
		map.put("parent", parent);
		System.out.println("parent : " + parent);
		System.out.println("map : " + map);
		List<bom_DTO> bom_list = this.b_dao.bom_all_list_sch(map);  
		return bom_list;
	}

	@Override
	public int bom_delete(del_DTO d_dto) {
		Map<String, Object> p = new HashMap<>();
		p.put("BOM_CODE", d_dto.getCode2());
		p.put("PRODUCT_CODE", d_dto.getCode());
		int bom_delete = this.b_dao.bom_delete(p);
		return bom_delete;
	}

}
