package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.prdplan_req_DTO;

public interface prdplan_service {
	Map<String, Object> prdplan_save(prdplan_req_DTO plandto);
	Map<String, Object> prdplan_update(prdplan_req_DTO reqdto);
	Map<String, Object> plan_status_update(Map<String, String> requestParam);
	

}
