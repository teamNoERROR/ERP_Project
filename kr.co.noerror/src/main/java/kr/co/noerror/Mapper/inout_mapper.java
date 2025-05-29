package kr.co.noerror.Mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.inout_DTO;
import kr.co.noerror.DTO.products_DTO;

@Mapper
public interface inout_mapper {

	int code_dupl_inb(String inb_code); //중복검사
	
	int insert_inbnd(inout_DTO dto);
}
