package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import kr.co.noerror.DTO.mrp_result_DTO;

public interface mrp_service {
	Map<String, Object> mrp_save(List<mrp_result_DTO> results);
}
