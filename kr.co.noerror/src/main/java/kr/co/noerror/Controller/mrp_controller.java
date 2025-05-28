package kr.co.noerror.Controller;

import java.util.ArrayList;
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

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.mrp_DAO;
import kr.co.noerror.DTO.mrp_input_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.plan_DTO;
import kr.co.noerror.Model.M_random;
import kr.co.noerror.Model.mrp_Calulation;

@Controller
public class mrp_controller {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	mrp_DAO mdao;
	
	@Resource(name="mrp_Calulation")
	mrp_Calulation mrp_calc;
	
	@Resource(name="M_random")
	M_random mrandom;
	
	@PostMapping("/mrp_save.do")
	@ResponseBody
	public Map<String, Object> mrp_save(@RequestBody List<mrp_result_DTO> results) {
		
		Map<String, Object> response = new HashMap<>();
		
        //중복 없는 mrp코드 생성
        String mrp_code = null;
        int count = 0;
        boolean is_duplicated = true;
        while(is_duplicated) {
        	mrp_code = "mrp-" + this.mrandom.random_no();
        	count = this.mdao.mrp_code_check(mrp_code);
        	if(count == 0){
        		is_duplicated = false;
        	}
        }
        
        try {
        	
        	//mrp_header 테이블에 저장
        	int result1 = this.mdao.insert_mrp_header(mrp_code);
        	
        	//mrp_detail 테이블에 저장
        	int result2 = 0;
            for (mrp_result_DTO mdto : results) {
            	mdto.setMrp_code(mrp_code);
                result2 += this.mdao.insert_mrp_detail(mdto);
            }

            if((result1==1) && (result2 == results.size())) {
	            response.put("success", true);
            }
            else {
            	response.put("success", false);
            }

        } catch (Exception e) {
        	System.out.println(e);
            response.put("success", false);
        }

	    return response;
	}
	
	//mrp 계산 및 계산내역 ajax 리턴
	@PostMapping("/mrp_calc.do")
	@ResponseBody
	public List<mrp_result_DTO> calculateMrp(@RequestBody List<mrp_input_DTO> mrp_inputs) {
	    List<mrp_result_DTO> mrp_results = this.mrp_calc.mrp_calc(mrp_inputs);
	    return mrp_results;  // JSON 응답
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
