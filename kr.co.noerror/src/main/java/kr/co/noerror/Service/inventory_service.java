package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import kr.co.noerror.DTO.IOSF_DTO;

public interface inventory_service {

	//입고창고 + 부자재창고 아이템별 재고수
	int ind_item_stock(String item_code);

	//개별 완제품 재고수
	Map<String, Integer> ind_pd_all_stock();
	
	//창고+완제품별 재고합
	Map<String, Integer> ind_pd_stock(String wh_code);

	//완제품 재고리스트
	List<IOSF_DTO> pd_stock_list();
}
