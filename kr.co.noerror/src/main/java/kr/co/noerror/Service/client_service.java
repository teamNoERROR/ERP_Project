package kr.co.noerror.Service;

import java.util.List;

import kr.co.noerror.DTO.client_DTO;

public interface client_service {

	//거래처 수 
	int client_total(String type);

	//거래처 리스트 
	List<client_DTO> client_list(String type, Integer pageno, int post_ea);

	//틍정 거래처 정보 불러오기 
	client_DTO clt_one_detail(String code, String cidx);

}
