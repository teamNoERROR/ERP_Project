package kr.co.noerror.Controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.prdplan_DAO;
import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.employee_DTO;
import kr.co.noerror.DTO.paging_info_DTO;
import kr.co.noerror.DTO.pchreq_req_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;
import kr.co.noerror.DTO.plan_DTO;
import kr.co.noerror.DTO.prdplan_req_DTO;
import kr.co.noerror.DTO.prdplan_res_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.DTO.temp_bom_DTO;
import kr.co.noerror.Model.M_paging2;
import kr.co.noerror.Service.generic_list_service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class proplan_controller {
	
	private static final int page_block = 3; //페이지 번호 출력갯수
	
	private final generic_list_service<prdplan_res_DTO> prdplan_list_service;  //generic_list_service<pchreq_res_DTO> 생성자 주입
	private final kr.co.noerror.Service.prdplan_service prdplan_service;                             //pchreq_service 생성자 주입
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="plan_DTO")
	plan_DTO pdto;
	
	@Autowired
	prdplan_DAO pdao;
	
	@Autowired
	M_paging2 m_pg2;
	
	String [] no_chng_plnBtn = {"생산계획수립","생산계획확정","생산중","","생산지연","생산완료"};
	
	/*
	@GetMapping("/emps_modal.do")
	public String empls_modal(Model m) {
		List<employee_DTO> emps = this.pdao.emps_modal();
		m.addAttribute("emps",emps);
		return  "/modals/temp_emp_list_modal.html";
	}
	*/
	
	//생산계획 등록(주문)
	@GetMapping("/production_in.do")
	public String production2(Model m) {
		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","생산 계획");
		m.addAttribute("mmenu","주문 생산계획 등록");
		return "/production/production_plan_insert.html";
	}
	//생산계획 등록(주문)
//	@GetMapping("/production_in_stock.do")
//	public String production_in_stock(Model m) {
//		m.addAttribute("lmenu","생산 관리");
//		m.addAttribute("smenu","생산 계획");
//		m.addAttribute("mmenu","재고 생산계획 등록");
//		return "/production/production_plan_stock_insert.html";
//	}

	//생상계획 저장
	@PostMapping("/prdplan_save.do")
	@ResponseBody
    public Map<String, Object> prdplan_save(@RequestBody prdplan_req_DTO plandto) {
		return this.prdplan_service.prdplan_save(plandto);
    }
	
	@GetMapping("/bom_items.do")
	public String bom_items(Model m, @RequestParam(name="code") String bom_code) {

		List<bom_DTO> bom_items = this.pdao.bom_items(bom_code);
		m.addAttribute("bom_items",bom_items);
		m.addAttribute("product_name",bom_items.get(0).getPRODUCT_NAME());
		return  "/modals/bom_items_modal.html";
	}
	
	//생산계획 리스트 
	@GetMapping("/production.do")
	public String production(Model m, @ModelAttribute search_condition_DTO search_cond) {
		
		int search_count = this.prdplan_list_service.search_count(search_cond);
		
		paging_info_DTO paging_info = this.m_pg2.calculate(
				search_count, 
				search_cond.getPage_no(), 
				search_cond.getPage_size(), 
				page_block
				);
		
		List<prdplan_res_DTO> prd_plans = this.prdplan_list_service.paged_list(search_cond, paging_info, null);
		System.out.println(prd_plans);
		
		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","생산 계획");
		m.addAttribute("mmenu","생산 계획 리스트");
		
		m.addAttribute("prd_plans", prd_plans);
		m.addAttribute("paging", paging_info);
		m.addAttribute("condition", search_cond);
		
		return "/production/production_plan_list.html";
	}
	
	//생산계획 상세보기 
	@GetMapping("/prdplan_detail.do")
	public String prdplan_detail(@RequestParam(name="code") String plan_code, Model m) {
		
		List<prdplan_res_DTO> details = this.pdao.prdplan_detail(plan_code);
		System.out.println("details : " + details);
		m.addAttribute("details",details);
	    m.addAttribute("no_chng_plnBtn", no_chng_plnBtn);
		return "/modals/production_plan_detail_modal.html";
	}
	
	
	@GetMapping("/prdplan_update.do")
	public String prdplan_update(@RequestParam(name="code") String plan_code, Model m) {
		List<prdplan_res_DTO> details = this.pdao.prdplan_detail(plan_code);
		m.addAttribute("details",details);
		return "/production/production_plan_update.html";
	}
	
	@PostMapping("/prdplan_updateok.do")
	@ResponseBody
	public Map<String, Object> prdplan_updateok(@RequestBody prdplan_req_DTO requestdto) {
		System.out.println(requestdto);
		return prdplan_service.prdplan_update(requestdto);
	}
	
	@PostMapping("/plan_status_update.do")
	@ResponseBody
	public Map<String, Object> plan_status_update(@RequestBody Map<String, String> requestParam) {
		return prdplan_service.plan_status_update(requestParam);
	}
}
