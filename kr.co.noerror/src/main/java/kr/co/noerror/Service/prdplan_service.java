package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import kr.co.noerror.DTO.paging_info_DTO;
import kr.co.noerror.DTO.prdplan_req_DTO;
import kr.co.noerror.DTO.prdplan_res_DTO;
import kr.co.noerror.DTO.search_condition_DTO;

public interface prdplan_service {
	int search_count(search_condition_DTO search_cond);
	public List<prdplan_res_DTO> paged_list(search_condition_DTO search_cond, paging_info_DTO paging_info);
	Map<String, Object> prdplan_save(prdplan_req_DTO plandto);
}
