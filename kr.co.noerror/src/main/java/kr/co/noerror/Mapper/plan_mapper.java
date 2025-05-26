package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.order_DTO;
import kr.co.noerror.DTO.plan_DTO;
import kr.co.noerror.DTO.temp_bom_DTO;

@Mapper
public interface plan_mapper {
	List<plan_DTO> plan_list(Map<String, Object> mparam);
	int plan_count(Map<String, Object> mparam);
	List<temp_bom_DTO> bom_items(String bom_code);
	List<order_DTO> orders_modal();
}
