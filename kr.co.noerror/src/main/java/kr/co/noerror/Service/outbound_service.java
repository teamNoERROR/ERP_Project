package kr.co.noerror.Service;

import java.util.List;

import kr.co.noerror.DTO.outbound_DTO;

public interface outbound_service {

	//출고리스트 
	List<outbound_DTO> outbound_all_list(String keyword, Integer pageno, int post_ea, String[] out_status_lst);

	//출고 등록 
	int outbnd_insert(String out_pds);

	
	List<outbound_DTO> outbound_detail(String ob_code, String ord_code);

}
