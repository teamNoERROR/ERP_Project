package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.inout_DTO;
import kr.co.noerror.DTO.products_DTO;

@Mapper
public interface inout_mapper {

	int code_dupl_inb(String inb_code); //코드중복검사
	
	int insert_inbnd(inout_DTO dto);  //자재입고등록
	
	int inbound_total(Map<String, String> map);
	List<inout_DTO> inbound_all_list(Map<String, Object> map);
	List<inout_DTO> inbound_detail(String inbnd_code);
}
