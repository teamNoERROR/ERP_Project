package kr.co.noerror.Controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.mrp_DAO;
import kr.co.noerror.DTO.mrp_input_DTO;
import kr.co.noerror.DTO.mrp_result2_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.mrp_save_req_DTO;
import kr.co.noerror.DTO.prdplan_res_DTO;
import kr.co.noerror.Model.M_mrp_Calulation;
import kr.co.noerror.Service.mrp_service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class mrp_controller {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final mrp_service mrp_service;  //mrp_service 생성자 주입
	
	@Autowired
	mrp_DAO mdao;
	
	@Resource(name="mrp_Calulation")
	M_mrp_Calulation mrp_calc;
	
	@PostMapping("/go_purchase.do")
	public String go_purchase(@RequestParam("data") String json_data,
			@RequestParam("mrp_code") String mrp_code,
			Model model) {
	    ObjectMapper mapper = new ObjectMapper();
	    try {
	        List<mrp_result_DTO> mrp_results = mapper.readValue(
	        	json_data, new TypeReference<List<mrp_result_DTO>>() {}
	        );
	        model.addAttribute("mrp_code", mrp_code);
	        model.addAttribute("mrp_results", mrp_results);
	    } catch (Exception e) {  //JSON 형식 자체가 잘못된 경우
	        e.printStackTrace();
	    }

	    return "/production/purchase_insert.html";
	}
	
	@PostMapping("/mrp_calc.do")
	@ResponseBody
	public List<mrp_result2_DTO> calculateMrp(@RequestBody List<mrp_input_DTO> mrp_inputs) {		
	    return this.mrp_calc.mrp_calc(mrp_inputs);
	}

	@PostMapping("/mrp_save.do")
	@ResponseBody
	public Map<String, Object> saveMrpResults(@RequestBody mrp_save_req_DTO request) {
	    List<mrp_result_DTO> summaryList = request.getSummary();
	    List<mrp_result2_DTO> detailList = request.getDetail();
	    System.out.println(summaryList);
	    System.out.println(detailList);
	    
	    //MRP 계산결과 저장 및 production_plan 테이블에 mrp_status 값 업데이트
	    Map<String, Object> response = mrp_service.save(summaryList, detailList);
	    return response;
	}
	
	@GetMapping("/production_period.do")
	public String production_period(Model m,
			@RequestParam(name="start_date") Date start_date,
			@RequestParam(name="end_date") Date end_date
			) {
		System.out.println(start_date);
		System.out.println(end_date);
		Map<String, Object> mparam = new HashMap<>();
		mparam.put("start_date", start_date); 
		mparam.put("end_date", end_date);  
		
		List<prdplan_res_DTO> plans_period = this.mdao.plans_period(mparam);
		System.out.println(plans_period);
		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","mrp 계산");
		m.addAttribute("plans_period", plans_period);

		return "/production/mrp_list.html";
	}
	
	@GetMapping("/mrp.do")
	public String mrp(Model m) {
		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","mrp 계산");
		return "/production/mrp_list.html";
	}
}
