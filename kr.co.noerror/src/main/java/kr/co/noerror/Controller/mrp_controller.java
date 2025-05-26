package kr.co.noerror.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.mrp_DAO;
import kr.co.noerror.DTO.bom_and_qty_DTO;
import kr.co.noerror.DTO.plan_DTO;
import kr.co.noerror.Model.mrp_Calculation;

@Controller
public class mrp_controller {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	mrp_DAO mdao;
	
	@Resource(name="mrp_Calculation")
	mrp_Calculation mrp_calc;
	
	@PostMapping("/mrp_calc.do")
	public String calculateMrp(@ModelAttribute("bomList") List<bom_and_qty_DTO> bomList) {
	    System.out.println(bomList);
	    return null;
	}
	
	@GetMapping("/production_period.do")
	public String production_period(Model m,
			@RequestParam(name="start_date") String start_date,
			@RequestParam(name="end_date") String end_date
			) {
		
		Map<String, Object> mparam = new HashMap<>();
		mparam.put("start_date", start_date); 
		mparam.put("end_date", end_date);  
		
		List<plan_DTO> plans_period = this.mdao.plans_period(mparam);
		
		m.addAttribute("plans_period", plans_period);

		return "/production/mrp_list.html";
	}
}
