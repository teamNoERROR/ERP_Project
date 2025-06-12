package kr.co.noerror.Service;

import java.util.List;

import kr.co.noerror.DTO.IOSF_DTO;

public interface inventory_service {

	int ind_item_stock(String item_code);

	//완제품 재고리스트
	List<IOSF_DTO> pd_stock_list();
	
	//완제품별 재고합
	int ind_pd_stock(String pd_code);

}
