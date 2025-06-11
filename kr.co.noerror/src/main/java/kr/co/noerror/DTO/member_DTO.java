package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("member_DTO")
public class member_DTO {

	int EIDX;
	String ECODE, ENAME, EMP_PASSWD, EMP_BIRTH;
	String EMP_ZIP, EMP_ADDR1, EMP_ADDR2, EMP_PART, EMP_POSITION, EMP_TEL, EMP_FAX, EMP_PHONE, EMP_EMAIL;
	String EMP_USE_FLAG, EMP_FILE_NM, EMP_FILE_RENM, EMP_API_FNM, EMP_MEMO, EMP_INSERT_DATE, EMP_MODIFY_DATE;

	//조인용 변수 
	int ROLE_ID;
	String ROLE_NAME, ROLES;

}
