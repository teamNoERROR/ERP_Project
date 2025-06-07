package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.bom_DTO;

@Mapper
public interface bom_mapper {
	
	int bom_check(String pd_code);  //BOM등록여부
	
	bom_DTO bom_detail(String pd_code);  //BOM 상세보기
	
	int bom_cd_ck(String code);  //고유번호 중복체크  
	
	Integer select_pidx(String bom_code); //부모bidx 찾기
	int insert_item(bom_DTO dto); //BOM 등록
	
	int bom_all_ea_sch(Map<String, String> map);	//BOM 총개수(검색) 
	List<bom_DTO> bom_all_list_sch(Map<String, Object> map);	//BOM 리스트(검색)
	
	int bom_delete(Map<String, Object> p);  //bom 삭제

}
