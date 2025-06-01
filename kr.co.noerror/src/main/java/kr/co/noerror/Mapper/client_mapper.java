package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.client_DTO;

@Mapper
public interface client_mapper {

	
	int client_total(Map<String, String> map);
	
	List<client_DTO> client_list(Map<String, Object> map);
}
