package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.pchreq_DTO;
import kr.co.noerror.DTO.pchreq_detail_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;

@Mapper
public interface purchase_mapper {
	List<mrp_result_DTO> mrp_result_select(String mrp_code);
	int pch_code_check(String pch_code);
	int insert_purchase(pchreq_DTO pdto);
	int insert_pch_detail(pchreq_detail_DTO pdto);
	List<pchreq_res_DTO> purchase_list(Map<String, Object> mparam);
	int purchase_count(Map<String, Object> mparam);
	List<pchreq_res_DTO> purchase_detail(String pch_code);
}
