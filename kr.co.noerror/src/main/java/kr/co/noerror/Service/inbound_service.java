package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import kr.co.noerror.DTO.inbound_DTO;

public interface inbound_service {

	//입고등록 
	int inbnd_insert(String inbnd_item);
	
	//입고리스트 총개수 
	int inbound_total(String keyword, String[] status_lst);

	//입고리스트 
	List<inbound_DTO> inbound_all_list(String keyword, Integer pageno, int post_ea, String[] status_lst);

	//입고건 상세보기 
	List<inbound_DTO> inbound_detail(String inbnd_code, String pch_cd);

	//기입고처리건 확인  + 입고상태변경(체크박스)
	Map<String, Integer> in_status_ck(String inbnd_data);

}
