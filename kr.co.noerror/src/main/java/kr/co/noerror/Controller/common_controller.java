package kr.co.noerror.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.annotation.Resource;
import kr.co.noerror.DAO.common_DAO;
import kr.co.noerror.DTO.employee_DTO;

@Controller
public class common_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Resource(name="employee_DTO")
	employee_DTO emp_dto;
	
	@Autowired
	common_DAO emp_svc;
	
	
	@GetMapping("/employee_list.do")
	public String emp_list(Model m) {
		List<employee_DTO> all_data = this.emp_svc.emp_list();  //DB로드
	
		m.addAttribute("employees",all_data);
		
		return  "/modals/employee_list_modal.html";
//		return  null;
	}

}
