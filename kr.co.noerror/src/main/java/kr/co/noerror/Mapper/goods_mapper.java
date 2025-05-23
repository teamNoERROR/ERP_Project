package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.products_DTO;

@Mapper
public interface goods_mapper {

	List<String> pd_class_list(Map<String, String> map);  //완제품 품목분류 리스트 
	int pd_insert(products_DTO pdto);  //완제품 등록 
	int pd_all_ea();  //완제품 등록 
	List<products_DTO> pd_all_list();  //완제품 품목분류 리스트 
	
	products_DTO pd_one_detail (String pd_code);  //제품 상세보기
	int pd_delete(Map<String, Object> p);
}
