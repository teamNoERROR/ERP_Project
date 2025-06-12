package kr.co.noerror.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.IOSF_DTO;

@Mapper
public interface inventory_mapper {
	int ind_item_stock(String item_code);
	int ind_pd_stock(String pd_code);
	List<IOSF_DTO> pd_stock_list();

}
