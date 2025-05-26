package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("temp_emp_DTO")
public class temp_emp_DTO {
	int eidx;
	String emp_code;
	String emp_name;
}
