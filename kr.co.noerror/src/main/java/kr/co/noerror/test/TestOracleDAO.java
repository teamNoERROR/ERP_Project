package kr.co.noerror.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TestOracleDAO {

	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	List<TestOracleDTO> oracle_all(){
		List<TestOracleDTO> all = this.sql.selectList("oracle_all");
		return all;
	}
}
