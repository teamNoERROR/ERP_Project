package kr.co.noerror.Service;

import java.util.List;

import kr.co.noerror.DTO.inbound_DTO;
import kr.co.noerror.DTO.outbound_DTO;

public interface outbound_service {

	List<outbound_DTO> outbound_all_list(String keyword, Integer pageno, int post_ea, String[] out_status_lst);

	int outbnd_insert(String out_pds);

}
