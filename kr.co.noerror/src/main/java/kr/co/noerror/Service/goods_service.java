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
	
	//제품 리스트
	public List<products_DTO> pd_all_list() {
		List<products_DTO> goods_list = this.g_dao.pd_all_list();
		return goods_list;
	}
	
	//완제품 총개수 
	public int pd_all_ea() {
		int result = this.g_dao.pd_all_ea();
		return result;
	}
		
		
	//제품 상세보기
	public products_DTO pd_one_detail(String pd_code) {
		products_DTO goods_one = this.g_dao.pd_one_detail(pd_code);
		return goods_one;
	}
	
	
	//제품 삭제 
	public int pd_delete(int pidx, String pd_code) {
		Map<String, Object> p = new HashMap<>();
		p.put("PIDX", pidx);
		p.put("PRODUCT_CODE", pd_code);
		
		int goods_del = this.g_dao.pd_delete(p);
		return goods_del;
	}
	

	
	
	
	
	
}
