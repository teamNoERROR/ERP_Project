package kr.co.noerror.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.IOSF_DTO;

@Mapper
public interface inventory_mapper {
	List<IOSF_DTO> ind_pd_all_stock();  //개별 완제품 재고수
	List<IOSF_DTO> pd_stock_list();  //완제품 재고 리스트
	
	List<IOSF_DTO> ind_item_all_stock();  //개별 부자재 재고수
	List<IOSF_DTO> itm_stock_list();  //부자재 재고 리스트

}
