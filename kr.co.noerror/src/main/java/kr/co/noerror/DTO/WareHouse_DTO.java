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
	
	//    	관리자 사원번호  
	String  wh_mg_id;
	
	//  원본 파일명		 변경된 파일명 		파열 경로	  	
	String wh_file_ori, wh_file_new, wh_file_url, wh_file_NoExt;
	
	// 				파일
	MultipartFile wh_file;
	
	//     창고 설명 	창고 비고
	String wh_desc, wh_note;
	
	
	/***********************************관리자 테이블 (JOIN)**********************************************/
	// 관리자 자동 증가값
	int EIDX;
	
	//    관리자이름  관리자 코드	 관리자 부서   관리자 직급	  	관리자 전화번호							
	String ENAME,  ECODE,    EMP_PART,    EMP_POSITION,   EMP_PHONE,       
	//     관리자 번호	 관리자 이메일   관리자 팩스	관리자 메모		관리자 생성 시간		
			EMP_TEL, 	 EMP_EMAIL, 	 EMP_FAX, 		EMP_MEMO, 		EMP_INSERT_DATE;

	
	
	
	//모달에서 창고리스트 선택용 변수 
	String wh_tp;
}
