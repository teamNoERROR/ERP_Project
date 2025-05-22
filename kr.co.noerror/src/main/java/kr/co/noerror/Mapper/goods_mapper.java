package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.products_DTO;

@Mapper
public interface goods_mapper {

	List<String> pd_class_list(Map<String, String> map);  //완제품 대분류 리스트 
	int pd_insert(products_DTO pdto);  //완제품 등록 
}
