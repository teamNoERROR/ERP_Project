package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import kr.co.noerror.DTO.paging_info_DTO;
import kr.co.noerror.DTO.pchreq_req_DTO;
import kr.co.noerror.DTO.prdplan_req_DTO;
import kr.co.noerror.DTO.prdplan_res_DTO;
import kr.co.noerror.DTO.search_condition_DTO;

public interface prdplan_service {
	Map<String, Object> prdplan_save(prdplan_req_DTO plandto);
	Map<String, Object> prdplan_update(prdplan_req_DTO reqdto);
	Map<String, Object> plan_status_update(Map<String, String> requestParam);
}
