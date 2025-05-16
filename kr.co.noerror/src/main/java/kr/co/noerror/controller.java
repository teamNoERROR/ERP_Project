package kr.co.noerror;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {

	@GetMapping("/index.do")
	public String notice_list(Model m) {
		
		
		
		return "/index.html";
	}
	
}

