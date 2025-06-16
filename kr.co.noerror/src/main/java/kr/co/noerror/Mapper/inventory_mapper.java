package kr.co.noerror.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.IOSF_DTO;

@Mapper
public interface inventory_mapper {
	int ind_item_stock(String item_code);  //입고창고 + 부자재창고 아이템별 재고수
	List<IOSF_DTO> ind_pd_all_stock();  //개별 완제품 재고수
	List<IOSF_DTO> pd_stock_list();  //완제품 재고 리스트

}
