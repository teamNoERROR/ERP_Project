package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import kr.co.noerror.DTO.ordreq_req_DTO;

public interface ordreq_service {
	Map<String, Object> ordreq_save(List<Map<String, Object>> orders);
	Map<String, Object> ordreq_update(ordreq_req_DTO reqdto);
	Map<String, Object> ord_status_update(Map<String, String> requestParam);
}
