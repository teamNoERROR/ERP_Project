package kr.co.noerror.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.noerror.DTO.client_DTO;

public interface client_service {

	//거래처 수 
	int client_total(String type);

	//거래처 리스트 
	List<client_DTO> client_list(String type, Integer pageno, int post_ea);

	//특정 거래처 정보 불러오기 
	client_DTO clt_one_detail(String code, String cidx);

	//거래처 등록 
	int clt_insert(client_DTO cdto, MultipartFile clientImage);

}
