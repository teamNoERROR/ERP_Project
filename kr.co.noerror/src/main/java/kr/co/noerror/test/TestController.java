package kr.co.noerror.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@Autowired
	TestOracleDAO oracledao;
	
	@Autowired
	TestMysqlDAO mysqldao;
	
	@GetMapping("/test/oracle_list.do")
	public String oracle_list(Model m) {
		List<TestOracleDTO> oracle_all = this.oracledao.oracle_all();
		m.addAttribute("oracle_all", oracle_all);
		return "/test_oracle.html";
	}
	
	@GetMapping("/test/mysql_list.do")
	public String mysql_list(Model m) {
		List<TestMysqlDTO> mysql_all = this.mysqldao.mysql_all();
		m.addAttribute("mysql_all", mysql_all);
		return "/test_mysql.html";
	}
	
}
