package kr.co.noerror.Controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class client_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	List<String> list = null; 
	Map<String, String> map = null;
	String url = "";
	String msg = "";
	
	@GetMapping("/client.do")
	public String client_list(Model m, @RequestParam(value = "type", required = false) String type ) {
		
		//거래처타입에 따른 url 분류 
		if("client".equals(type) || type==null) {
			m.addAttribute("mmenu","거래처 리스트");
			this.url = "/client/client_list.html";
			
		}else if("p_client".equals(type)) {
			m.addAttribute("mmenu","발주처 리스트");
			this.url = "/client/client2_list.html";
		}
		
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","거래처 관리");
		return this.url;
		
	}
}
