package kr.co.noerror;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {

	@GetMapping("/client.do")
	public String client_list(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","거래처 관리");
//		return "/client_list.html";
		return "/client/client_insert.html";
//		return "/client_modify.html";
	}
	
	@GetMapping("/p_client.do")
	public String p_client_list(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","발주처 관리");
		return "/client/client2_list.html";
	}
	
	@GetMapping("/goods.do")
	public String goods_list(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","완제품 리스트");
		return "/products_list.html";
//		return "/goods_insert.html";
	}
	
	@GetMapping("/items.do")
	public String item_list(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","부자재 리스트");
		return "/items_list.html";
	}
	
	
	@GetMapping("/bom.do")
	public String bom_insert(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","bom 리스트");
		return "/bom_detail.html";
//		return "/bom_insert.html";
	}
	
	
	@GetMapping("/goods_insert.do")
	public String goods_insert(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","품목 등록");
		return "/goods_insert.html";
	}
	
	
	@GetMapping("/warehouse.do")
	public String warehouse(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
//		return "/warehouse_insert.html";
		return "/warehouses_list.html";
//		return "/warehouse_modify.html";
	}
	
	@GetMapping("/warehouse_stock.do")
	public String warehouse_stock(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
//		return "/warehouses_in_list.html";
//		return "/warehouses_out_list.html";
//		return "/warehouses_pd_list.html";
		return "/warehouses_it_list.html";
	}
	
	
	@GetMapping("/mrp.do")
	public String mrp(Model m) {
		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","mrp 계산");
		return "/mrp_list.html";
	}
	
	
	@GetMapping("/production.do")
	public String production(Model m) {
		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","생산계획리스트");
		m.addAttribute("mmenu","생산계획등록");
		return "/production_plan_list.html";
//		return "/production_plan_insert.html";
	}
	
	
	@GetMapping("/order.do")
	public String order(Model m) {
		
//		return "/order_list.html";
		return "/order_detail.html";
	}
	
	@GetMapping("/purchase.do")
	public String purchase(Model m) {
		return "/purchase_insert.html";
//		return "/purchase_list.html";
//		return "/purchase_detail.html";
	}
	
	@GetMapping("/bound.do")
	public String bound(Model m) {
		return "/outbound_list.html";
//		return "/inbound_list.html";
	}
}

