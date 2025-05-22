package kr.co.noerror;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {

	//메인화면
	@GetMapping("/")
	public String main(Model m) {
		return "/member/member_login.html";
	}
		
	//거래처관리
	@GetMapping("/client.do")
	public String client_list(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","거래처 관리");
		return "/client/client_list.html";
	}
	
	//발주처관리
	@GetMapping("/p_client.do")
	public String p_client_list(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","발주처 관리");
		return "/client/client2_list.html";
	}
	

	
	//품목관리 > 부자재리스트 
	@GetMapping("/items.do")
	public String item_list(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","부자재 리스트");
		return "/goods/items_list.html";
	}
	

	
	//BOM 등록하기
	@GetMapping("/bom.do")
	public String bom_insert(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","bom 등록하기");
//		return "/bom_detail.html";
		return "/goods/bom_insert.html";
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
	
	//mrp 계산
	@GetMapping("/mrp.do")
	public String mrp(Model m) {
		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","mrp 계산");
		return "/production/mrp_list.html";
	}
	
	//생산계획등록
	@GetMapping("/production.do")
	public String production(Model m) {
		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","생산계획리스트");
		m.addAttribute("mmenu","생산계획등록");
		return "/production/production_plan_list.html";
	}
	
	//생산계획등록
	@GetMapping("/production_in.do")
	public String production2(Model m) {
		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","생산계획리스트");
		m.addAttribute("mmenu","생산계획등록");
		return "/production/production_plan_insert.html";
	}

	
	@GetMapping("/purchase.do")
	public String purchase(Model m) {
		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","발주관리");
		return "/production/purchase_list.html";
	}

	
	
	@GetMapping("/test.do")
	public String test(Model m) {
	
		return "/temp/order_insert_test.html";
	}

	
}

