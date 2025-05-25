package kr.co.noerror.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.noerror.DTO.bom_DTO;

@Mapper
public interface bom_mapper {
	
	int bom_check(String pd_code);  //BOM등록여부
	
	bom_DTO bom_detail(String pd_code);  //BOM 조회
	
	int bom_cd_ck();  //bom bidx 조회 
	int bom_insert(List<bom_DTO> insert_item); //BOM 등록

}
