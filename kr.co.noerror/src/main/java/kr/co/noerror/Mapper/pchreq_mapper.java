package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.pchreq_DTO;
import kr.co.noerror.DTO.pchreq_detail_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;
import kr.co.noerror.DTO.search_condition_DTO;

@Mapper
public interface pchreq_mapper {
	List<mrp_result_DTO> mrp_result_select(String mrp_code);
	int pch_code_check(String pch_code);
	int pchreq_insert(pchreq_DTO pdto);
	int pchreq_detail_insert(pchreq_detail_DTO pdto);
	List<pchreq_res_DTO> pchreq_paged_list(Map<String, Object> mparam);
	int pchreq_search_count(search_condition_DTO search_cond);
	List<pchreq_res_DTO> pchreq_detail(String pch_code);
	int pchreq_update(pchreq_DTO pdto);
	int pchreq_detail_update(pchreq_detail_DTO pdto);
	int pch_status_update(Map<String, String> mparam);
}
