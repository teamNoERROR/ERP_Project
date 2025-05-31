package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.products_DTO;

@Mapper
public interface goods_mapper {

//	List<String> pd_class_list(Map<String, String> map);  //완제품 품목분류 리스트 
	List<String> gd_class(Map<String, String> map); //제품 품목선택시 대분류 리스트 출력 
	List<String> s_class(Map<String, String> map); //소분류 리스트 출력 
	
	int code_dupl(products_DTO pdto); //중복검사
	
	int pd_insert(products_DTO pdto);  //완제품 등록 
	int itm_insert(products_DTO pdto);  //부자재 등록 
	
//	int pd_all_ea(String type);  //제품 총개수 
//	List<products_DTO> pd_all_list(String type);  //품목 리스트 
	
	int gd_all_ea_sch(Map<String, String> map);	//제품 총개수(+검색) 
	List<products_DTO> gd_all_list_sch(Map<String, Object> map);	//제품 리스트(+검색)
	
	products_DTO pd_one_detail (Map<String, String> map);  //제품 상세보기
	
	int pd_delete(Map<String, Object> p);  //제품 삭제 
	
	String lclass_ck(String sclass);
}
