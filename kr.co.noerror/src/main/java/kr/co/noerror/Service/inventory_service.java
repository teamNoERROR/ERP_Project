package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import kr.co.noerror.DTO.IOSF_DTO;

public interface inventory_service {


	//개별 완제품 재고수
	Map<String, Integer> ind_pd_all_stock();
	
	//완제품 재고리스트
	List<IOSF_DTO> pd_stock_list();
	
	//개별 부자재 재고수
	Map<String, Integer> ind_item_all_stock();
	
	//부자재 재고리스트
	List<IOSF_DTO> itm_stock_list();

	//창고별 보유중인 제품 각각의 재고수 
	List<IOSF_DTO> pd_by_whlist();
	
	// 상품 + 창고별 재고 
	Map<String, Integer> stockByWhnPd ();
	
	// 상품별 전체 재고
	Integer stockPdTotal (String pdCode);

	int wh_pd_sp();  
	
	
	
		
}
