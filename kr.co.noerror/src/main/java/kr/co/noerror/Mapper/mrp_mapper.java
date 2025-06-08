package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.plan_DTO;
import kr.co.noerror.DTO.prdplan_res_DTO;
import kr.co.noerror.DTO.temp_bom_DTO;

@Mapper
public interface mrp_mapper {
	List<prdplan_res_DTO> plans_period(Map<String, Object> mparam);
	List<bom_DTO> boms_for_mrp(String bom_code);
	int mrp_code_check(String mrp_code);
	int insert_mrp_header(String mrp_code);
	int insert_mrp_detail(mrp_result_DTO mdto);
}
