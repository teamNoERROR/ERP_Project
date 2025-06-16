package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import kr.co.noerror.DTO.IOSF_DTO;

public interface inventory_service {


	//개별 완제품 재고수
	Map<String, Integer> ind_pd_all_stock();
	
	//창고+완제품별 재고합
	Map<String, Integer> ind_pd_stock(String wh_code);

	//완제품 재고리스트
	List<IOSF_DTO> pd_stock_list();
	
	
	//개별 부자재 재고수
	Map<String, Integer> ind_item_all_stock();
	
	//부자재 재고리스트
	List<IOSF_DTO> itm_stock_list();
		
}
