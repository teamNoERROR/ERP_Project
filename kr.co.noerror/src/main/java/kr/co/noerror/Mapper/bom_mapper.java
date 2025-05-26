package kr.co.noerror.Mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.bom_DTO;

@Mapper
public interface bom_mapper {
	
	int bom_check(String pd_code);  //BOM등록여부
	
	bom_DTO bom_detail(String pd_code);  //BOM 조회

}
