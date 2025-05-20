package kr.co.noerror.DTO;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class WareHouse_DTO {

	//자동 증가값
	int widx;
	//     창고 코드    창고 이름    우편번호    도로명1  상세
	String wh_code, wh_name, wh_zipcode, wh_addr1, wh_addr2;

	//     창고 타입    사용 여부   		 생성일          수정일            창고 번호
	String wh_type, wh_use_flag, wh_insert_date, wh_modify_date, wh_number;
	
	//     관리자 이름	   관리자 아이디  관리자 직급   관리자 번호
	String wh_mg_name, wh_mg_id,  wh_mg_mp,  wh_mg_ph;
	
	// 				파일
	MultipartFile wh_file;
	//     창고 설명 	창고 비고
	String wh_desc, wh_note;
	
	
	
}
