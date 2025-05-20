package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.order_DTO;

@Mapper
public interface order_mapper {
	List<order_DTO> order_list(Map<String, Object> mparam);
	int order_count();
}
