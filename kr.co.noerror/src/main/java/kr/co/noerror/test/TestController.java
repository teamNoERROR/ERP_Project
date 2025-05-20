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
	
	@GetMapping("/production/mrp.do")
	public String test_mrp(Model m) {
		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","mrp 계산");
		return "/production/mrp_list.html";
	}
	
	@GetMapping("/production/order.do")
	public String test_order(Model m) {
		
//		return "/order_list.html";
		return "/production/order_detail.html";
	}
	
	@GetMapping("/production/purchase.do")
	public String test_purchase(Model m) {
		return "/production/purchase_insert.html";
//		return "/purchase_list.html";
//		return "/purchase_detail.html";
	}
	
}
