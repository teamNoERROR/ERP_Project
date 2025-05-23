package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("del_DTO")
public class del_DTO {
	int idx;
	String key, code;
}
