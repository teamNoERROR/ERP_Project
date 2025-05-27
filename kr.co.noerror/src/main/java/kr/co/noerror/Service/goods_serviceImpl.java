package kr.co.noerror.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.Controller.order_controller;
import kr.co.noerror.DAO.bom_DAO;
import kr.co.noerror.DAO.goods_DAO;
import kr.co.noerror.DTO.del_DTO;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.Mapper.bom_mapper;
import kr.co.noerror.Mapper.goods_mapper;
import kr.co.noerror.Model.M_file;
import kr.co.noerror.Model.M_random;

@Service
public class goods_serviceImpl implements goods_service {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	goods_mapper g_mapper;

	@Resource(name="goods_DAO")
	goods_DAO g_dao;
	
	@Resource(name="M_random")  //랜덤숫자생성 모델 
	M_random m_rno;
	
	@Resource(name="M_file")   //파일첨부관련모델 
	M_file m_file;
	
	@Resource(name="file_DTO")  //파일첨부DTO
	file_DTO f_dto;
	
	@Resource(name="products_DTO")
	products_DTO p_dto;
	
	@Resource(name="del_DTO")
	del_DTO d_dto;
	
	List<String> list = null; 
	Map<String, String> map = null;

	//완제품 대분류 리스트
	@Override
	public JSONArray gd_class(String goods_type){
		this.list = new ArrayList<>();
		this.map = new HashMap<>();
	
		if("완제품".equals(goods_type) || "반제품".equals(goods_type)) {
			this.map.put("key", "pd_lc");
			
		}else if("부자재".equals(goods_type)) {
			this.map.put("key", "itm_lc");
			
		}
		this.list = this.g_dao.gd_class(this.map);
		JSONArray lc_list = new JSONArray(this.list);
		
		
		return lc_list;
	}
	
	@Override
	public JSONArray sc_class(String goods_type, String pd_class1){
		this.list = new ArrayList<>();
		this.map = new HashMap<>();
		
		if("완제품".equals(goods_type) || "반제품".equals(goods_type)) {
			this.map.put("key2", "pd_sc");
			this.map.put("PRODUCT_CLASS1", pd_class1);
		}else if("부자재".equals(goods_type)){
			this.map.put("key2", "itm_sc");
			this.map.put("ITEMS_CLASS1", pd_class1);
			
		}
		
		this.list = this.g_dao.sc_class(this.map);
		JSONArray sc_list = new JSONArray(this.list);
		return sc_list;
	}
	
	//제품 등록
	@Override
	public int gd_insert(products_DTO pdto) {
//		String goods_type = pdto.getPRODUCT_TYPE();  //제품유형 확인 
		String gd_code;  //랜덤번호 생성 
		int dupl;  //중복확인
//		System.out.println("goods_type : "+goods_type);
//		if("완제품".equals(goods_type) || "반제품".equals(goods_type)) {
//			this.p_dto.setPRODUCT_CODE(gd_code);
//			this.p_dto.setPRODUCT_TYPE(goods_type);
//			System.out.println("pdto itmcd : "+ this.p_dto.getITEM_CODE());
//			dupl = this.g_dao.code_dupl(this.p_dto);
//			
//			if(dupl > 0) {  //중복코드가 있을경우 
//				gd_code = this.m_rno.random_no(); //다시 추출
//			} else { //중복코드가 없을경우 
//				pdto.setPRODUCT_CODE(gd_code);  //제품코드 장착 
//				System.out.println("pdto itmcd2 : "+ pdto.getITEM_CODE());
//			}
//			
//		}else if("부자재".equals(goods_type)){
//			this.p_dto.setITEM_CODE(gd_code);  //제품코드 장착
//			this.p_dto.setITEM_TYPE(goods_type);
//			dupl = this.g_dao.code_dupl(this.p_dto);
//			
//			if(dupl > 0) {  //중복코드가 있을경우 
//				gd_code = this.m_rno.random_no(); //다시 추출
//			} else { //중복코드가 없을경우 
//				pdto.setITEM_CODE(gd_code);  //제품코드 장착 
//			}
//			
//		}
		if ("완제품".equals( pdto.getPRODUCT_TYPE()) || "반제품".equals( pdto.getPRODUCT_TYPE())) {
		    do {
		        gd_code = this.m_rno.random_no();
		        pdto.setPRODUCT_CODE(gd_code);
		        dupl = this.g_dao.code_dupl(pdto);
		    } while (dupl > 0);

		} else if ("부자재".equals( pdto.getITEM_TYPE())) {
		    do {
		        gd_code = this.m_rno.random_no();
		        pdto.setITEM_CODE(gd_code);
		        dupl = this.g_dao.code_dupl(pdto);
		    } while (dupl > 0);
		}
		System.out.println("COMPANY_CODE: " + pdto.getCOMPANY_CODE());
		int result = this.g_dao.gd_insert(pdto);
		return result;
	}
	
	//제품 리스트(완제품+부자재)
	@Override
	public List<products_DTO> gd_all_list(String type) {
		List<products_DTO> goods_list = this.g_dao.pd_all_list(type);  
		return goods_list;
	}
	
	//제품 총개수 
	@Override
	public int gd_all_ea(String type) {
		int goods_total = this.g_dao.pd_all_ea(type);    
		return goods_total;
	}
		
		
	//제품 상세보기
	@Override
	public products_DTO pd_one_detail(String pd_code, String type) {
		this.map = new HashMap<>();
		if("product".equals(type)) {
			this.map.put("type", type);
			this.map.put("PRODUCT_CODE", pd_code);
		}else if("item".equals(type)) {
			this.map.put("type", type);
			this.map.put("ITEM_CODE", pd_code);
			
		}
		products_DTO goods_one = this.g_dao.pd_one_detail(this.map);
		return goods_one;
	}
	
	
	//제품 삭제 
	@Override
	public int pd_delete(del_DTO d_dto) {
		Map<String, Object> p = new HashMap<>();
		if("product".equals(d_dto.getType())) {
			p.put("type", d_dto.getType());
			p.put("PIDX", d_dto.getIdx());
			p.put("PRODUCT_CODE", d_dto.getCode());
			
		}else if("item".equals(d_dto.getType())) {
			p.put("type", d_dto.getType());
			p.put("IIDX", d_dto.getIdx());
			p.put("ITEM_CODE", d_dto.getCode());
		}
		
		int goods_del = this.g_dao.pd_delete(p);
		return goods_del;
	}
	

	
	
	
	
	
}
