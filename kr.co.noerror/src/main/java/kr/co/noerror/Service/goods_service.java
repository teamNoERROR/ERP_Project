package kr.co.noerror.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import kr.co.noerror.Controller.order_controller;
import kr.co.noerror.DAO.goods_DAO;
import kr.co.noerror.DTO.del_DTO;
import kr.co.noerror.DTO.products_DTO;

@Service
@Repository("goods_service")
public class goods_service {

	//여기서 실제 구현 
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	goods_DAO g_dao;
	
	List<String> list = null; 
	Map<String, String> map = null;

	//완제품 대분류 리스트 
	public List<String> pd_class_list(String pd_class1){
		this.list = new ArrayList<>();
		this.map = new HashMap<>();
		
		if(pd_class1==null) {
			this.map.put("key", null);
			
		}else if(pd_class1!=null) {
			this.map.put("key", pd_class1);
			this.map.put("PRODUCT_CLASS1", pd_class1);
		}
		
		this.list = this.g_dao.pd_class_list(this.map);
		
		return this.list;
	}
	
	//완제품 등록
	public int pd_insert(products_DTO pdto) {
		int result = this.g_dao.pd_insert(pdto);
		return result;
	}
	
	//제품 리스트(완제품+부자재)
	public List<products_DTO> gd_all_list(String type) {
		List<products_DTO> goods_list = this.g_dao.pd_all_list(type);  
		return goods_list;
	}
	
	//제품 총개수 
	public int gd_all_ea(String type) {
		int goods_total = this.g_dao.pd_all_ea(type);    
		return goods_total;
	}
		
		
	//제품 상세보기
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
