package kr.co.noerror.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class purchase_controller {

	@GetMapping("/purchase_in.do")
	public String purchase(Model m) {
		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","발주관리");
		m.addAttribute("mmenu","발주등록");
		return "/production/purchase_insert.html";
	}
}
