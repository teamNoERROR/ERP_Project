package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface goods_mapper {

	List<String> pd_class_list(Map<String, String> map);  //완제품 대분류 리스트 
	
//	List<Object> pd_sc_list(String pd_class1);  //완제품 소분류 리스트 
}
