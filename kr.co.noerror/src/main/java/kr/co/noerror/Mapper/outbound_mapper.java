package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.outbound_DTO;

@Mapper
public interface outbound_mapper {
	
	List<outbound_DTO> outbound_all_list(Map<String, Object> map); //입고 총 리스트 
	int code_dupl_out(String out_code); //코드중복검사
}
