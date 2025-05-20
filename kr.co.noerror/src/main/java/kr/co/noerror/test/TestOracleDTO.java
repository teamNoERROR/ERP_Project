package kr.co.noerror.test;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("TestOracleDTO")
public class TestOracleDTO {
	String oname;
	int oage;
}
