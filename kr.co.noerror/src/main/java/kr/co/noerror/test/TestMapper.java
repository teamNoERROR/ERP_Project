package kr.co.noerror.test;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
	List<TestOracleDTO> oracle_all();
	List<TestMysqlDTO> mysql_all();

}
