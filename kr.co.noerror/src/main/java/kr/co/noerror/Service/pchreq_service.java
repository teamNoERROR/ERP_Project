package kr.co.noerror.Service;

import java.util.Map;

import kr.co.noerror.DTO.pchreq_req_DTO;

public interface pchreq_service {
	Map<String, Object> pchreq_save(Map<String, pchreq_req_DTO> requestMap);
	Map<String, Object> pchreq_update(pchreq_req_DTO reqdto);
	Map<String, Object> update_pch_status(Map<String, String> requestParam);
}
