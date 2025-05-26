package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("employee_DTO")
public class employee_DTO {
	
	// 관리자 자동 증가값
	int EIDX;
	
	//    관리자이름  관리자 코드	 관리자 부서   관리자 직급	  	관리자 전화번호							
	String ENAME,  ECODE,    EPART,    EPOSITION,   EPHONE;       
	
	//     관리자 번호	 관리자 이메일   관리자 팩스	관리자 메모		관리자 생성 시간		
	String  ETEL, 	 EEMAIL, 	 EFAX, 		MEMO, 		INSERT_DATE;


}
