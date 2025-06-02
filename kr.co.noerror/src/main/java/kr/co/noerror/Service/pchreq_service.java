package kr.co.noerror.Service;

import java.util.Map;

import kr.co.noerror.DTO.pchreq_req_DTO;

public interface pchreq_service {
	Map<String, Object> pchreq_save(Map<String, pchreq_req_DTO> requestMap);
}
