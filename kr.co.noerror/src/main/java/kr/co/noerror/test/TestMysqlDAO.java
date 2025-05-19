package kr.co.noerror.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TestMysqlDAO {

	@Autowired
	@Qualifier(value = "sqltemplate_mysql")
	private SqlSession sql;
	
	List<TestMysqlDTO> mysql_all(){
		List<TestMysqlDTO> all = this.sql.selectList("mysql_all");
		return all;
	}
}
