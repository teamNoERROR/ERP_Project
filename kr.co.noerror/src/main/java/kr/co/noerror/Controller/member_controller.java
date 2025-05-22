package kr.co.noerror.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class member_controller {

	@GetMapping("/employee.do")
	public String test(Model m) {
	
		return "/member/member_insert.html";
	}
}
