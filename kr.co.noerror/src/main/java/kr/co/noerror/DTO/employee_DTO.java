package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("employee_DTO")
public class employee_DTO {
	
	int EIDX;
	String ENAME, ECODE, EPART, EPOSITION, EPHONE, ETEL, EEMAIL, EFAX, MEMO, INSERT_DATE;

}
