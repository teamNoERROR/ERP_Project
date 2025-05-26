package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.plan_DTO;

@Mapper
public interface mrp_mapper {
	List<plan_DTO> plans_period(Map<String, Object> mparam);
}
