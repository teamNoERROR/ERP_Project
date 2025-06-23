package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import kr.co.noerror.DTO.mrp_result2_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.mrp_result_header_DTO;

public interface mrp_service {
	public Map<String, Object> save(List<mrp_result_DTO> summaryList, List<mrp_result2_DTO> detailList);
	public List<mrp_result_header_DTO> mrp_result_list(String mrp_status);
}