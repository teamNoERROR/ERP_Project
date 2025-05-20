package kr.co.noerror.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class goods_controller {

	//품목 등록하기 화면이동 
	@GetMapping("/goods_insert.do")
	public String goods_insert(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","품목 등록");
		return "/goods/goods_insert.html";
	}
	
}
