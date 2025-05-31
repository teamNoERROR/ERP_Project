package kr.co.noerror.Controller;

import java.util.List;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.common_DAO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.employee_DTO;

@Controller
public class common_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Resource(name="employee_DTO")
	employee_DTO emp_dto;
	
	@Autowired
	common_DAO common_svc;
	
	//관리자 리스트 모달 
	@GetMapping("/employee_list.do")
	public String emp_list(Model m) {
		List<employee_DTO> all_data = this.common_svc.emp_list();  //DB로드
	
		m.addAttribute("employees",all_data);
		
		return  "/modals/employee_list_modal.html";
	}
	
	//전체창고리스트 모달 
	@GetMapping("/warehouse_list.do")
	public String wh_list(Model m) {
		List<WareHouse_DTO> all_data = this.common_svc.warehouse_list(null);  //DB로드
		
		m.addAttribute("warehouse_list",all_data);
		
		return  "/modals/warehouse_list_modal.html";
	}
	
	//유형별 창고리스트 모달 
	@GetMapping("/wh_type_list.do")
	public String wh_type_list(Model m, @RequestParam("wh_type") String wh_type) {
		System.out.println(wh_type);
		List<WareHouse_DTO> all_data = this.common_svc.warehouse_list(wh_type);  //DB로드
		m.addAttribute("no_wh", "등록된 창고가 없습니다" );
		m.addAttribute("wh_tp_data", all_data);
		
		
		
		return  "/modals/wh_type_list_modal.html";
//		return null;
	}
	

}
