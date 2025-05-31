package kr.co.noerror;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {

	//메인화면
	@GetMapping("/")
	public String index(Model m) {
		return "/member/member_login.html";
	}
		
	//메인화면
	@GetMapping("/main.do")
	public String main(Model m) {
		return "/common/main.html";
	}
	
	//자재입고 
	@GetMapping("/inbound.do")
	public String inbound(Model m) {
		m.addAttribute("lmenu","입출고관리");
		m.addAttribute("smenu","자재관리");
		return "/warehouse/inbound_list.html";
	}
	
	//제품 출고 
	@GetMapping("/outbound.do")
	public String outbound(Model m) {
		m.addAttribute("lmenu","입출고관리");
		m.addAttribute("smenu","제품 출고");
		return "/warehouse/outbound_list.html";
	}
	
	//창고관리 
	@GetMapping("/warehouse.do")
	public String warehouse(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
		return "/warehouse/warehouses_list.html";
	}
	
	//창고별 재고
	@GetMapping("/warehouse_stock.do")
	public String warehouse_stock(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
//		return "/warehouse/warehouses_in_list.html";
//		return "/warehouse/warehouses_out_list.html";
//		return "/warehouse/warehouses_pd_list.html";
		return "/warehouse/warehouses_it_list.html";
	}
	
	@GetMapping("/test.do")
	public String test(Model m) {
	
		return "/temp/warehouse_in.html";
	}

}

