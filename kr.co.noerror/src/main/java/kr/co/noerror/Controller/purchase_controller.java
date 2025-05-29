package kr.co.noerror.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.noerror.DAO.mrp_DAO;
import kr.co.noerror.DAO.purchase_DAO;
import kr.co.noerror.DTO.mrp_result_DTO;

@Controller
public class purchase_controller {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	mrp_DAO mdao;
	
	@Autowired
	purchase_DAO pdao;

	@GetMapping("/mrp_result_select.do")
	public String mrp_result_select(@RequestParam(name="mrp_code") String mrp_code,
			Model m) {
		
		List<mrp_result_DTO> mrp_results = this.pdao.mrp_result_select(mrp_code);
		
		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","발주관리");
		m.addAttribute("mmenu","발주등록");
		
		m.addAttribute("mrp_results",mrp_results);
		
		return "/production/purchase_insert.html";
	}
	
	@GetMapping("/purchase_in.do")
	public String purchase(Model m) {
		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","발주관리");
		m.addAttribute("mmenu","발주등록");
		return "/production/purchase_insert.html";
	}
}
