package kr.co.noerror.test;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("TestMysqlDTO")
public class TestMysqlDTO {
	String mname;
	int mage;
}
