package kr.co.noerror.Mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface stock_mapper {
	int ind_item_stock(String item_code);

}
