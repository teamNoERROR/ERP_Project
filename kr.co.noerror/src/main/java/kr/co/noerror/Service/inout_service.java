package kr.co.noerror.Service;

import java.util.List;

import kr.co.noerror.DTO.inout_DTO;

public interface inout_service {

	//입고등록 
	int inbnd_insert(String inbnd_item);
	
	//입고리스트 총개수 
	int inbound_total(String sclass, String keyword);

	//입고리스트 
	List<inout_DTO> inbound_all_list(String sclass, String keyword, Integer pageno, int post_ea);

	List<inout_DTO> inbound_detail(String inbnd_code);

}
