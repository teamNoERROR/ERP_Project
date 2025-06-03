package kr.co.noerror.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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
import kr.co.noerror.DAO.pchreq_DAO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.order_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;
import kr.co.noerror.DTO.pchreq_item_DTO;
import kr.co.noerror.DTO.pchreq_req_DTO;
import kr.co.noerror.Model.M_random;
import kr.co.noerror.Service.pchreq_service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class pchreq_controller {
	
	private static final int page_block = 3; //페이지 번호 출력갯수
	private final pchreq_service pchreq_service;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	mrp_DAO mdao;
	
	@Autowired
	pchreq_DAO pdao;

	@GetMapping("/purchase_in.do")
	public String purchase(Model m) {
		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","발주관리");
		m.addAttribute("mmenu","발주등록");
		return "/production/purchase_insert.html";
	}
	
	@GetMapping("/mrp_result_select.do")
	public String mrp_result_select(@RequestParam(name="mrp_code") String mrp_code,
			Model m) {
		
		List<mrp_result_DTO> mrp_results = this.pdao.mrp_result_select(mrp_code);
		
		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","발주관리");
		m.addAttribute("mmenu","발주등록");
		
		m.addAttribute("mrp_code",mrp_code);
		m.addAttribute("mrp_results",mrp_results);
		
		return "/production/purchase_insert.html";
	}
	
	@PostMapping("/pchreq_save.do")
	@ResponseBody
	public Map<String, Object> pchreq_save(@RequestBody Map<String, pchreq_req_DTO> requestMap) {
		return pchreq_service.pchreq_save(requestMap);
	}
	
	@GetMapping("/purchase.do")
	public String purchase(Model m,
			@RequestParam(name="views", defaultValue="5", required=false) Integer page_ea,
			@RequestParam(name="pageno", defaultValue="1", required=false) Integer pageno,
			@RequestParam(name="pch_status", required=false) String[] pch_statuses,
			@RequestParam(name="search_word", defaultValue="", required=false) String search_word
			) {
		
		Map<String, Object> mparam = new HashMap<>();
		mparam.put("sword", search_word);  //검색어
		if (pch_statuses != null) {
			mparam.put("pch_statuses", Arrays.asList(pch_statuses)); // Mapper에서 IN 처리
		}
		
		int data_cnt = this.pdao.purchase_count(mparam);
		
		int page_cnt = (data_cnt-1) / page_ea + 1; //올림 처리하는 수식
		
		int start = (pageno - 1) * page_ea;  //oracle에서 해당페이지의 시작 번호
		int end = pageno * page_ea + 1;      //oracle에서 해당페이지의 종료 번호 + 1
		
		int start_page = ((pageno - 1) / page_block) * page_block + 1;
		int end_page = Math.min(start_page + page_block - 1, page_cnt);
		
		mparam.put("start", start);        //oracle 시작행 번호
		mparam.put("end", end);            //oracle 종료행 번호
		
		List<pchreq_res_DTO> all = this.pdao.purchase_list(mparam);
		
		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","발주관리");
		
		//데이터, 페이징 정보를 모델에 전달
		m.addAttribute("all", all);
		m.addAttribute("start_page", start_page);
		m.addAttribute("end_page", end_page);
		m.addAttribute("view", page_ea);
		m.addAttribute("page_cnt", page_cnt);
		m.addAttribute("pageno", pageno); 
		
		//검색 조건을 모델에 다시 전달
		m.addAttribute("search_word", search_word);
		m.addAttribute("pch_statuses", pch_statuses);
		
		return "/production/purchase_list.html";
	}
	
	@GetMapping("/purchase_detail.do")
	public String purchase_detail(@RequestParam(name="code") String pch_code, Model m) {
		List<pchreq_res_DTO> details = this.pdao.purchase_detail(pch_code);
		m.addAttribute("details",details);
		return "/modals/purchase_detail_modal.html";
	}
	
	@GetMapping("/purchase_update.do")
	public String purchase_update(@RequestParam(name="code") String pch_code, Model m) {
		List<pchreq_res_DTO> details = this.pdao.purchase_detail(pch_code);
		m.addAttribute("details",details);
		return "/production/purchase_update.html";
	}
	
	@PostMapping("/pchreq_updateok.do")
	@ResponseBody
	public Map<String, Object> pchreq_updateok(@RequestBody pchreq_req_DTO requestdto) {
		return pchreq_service.pchreq_update(requestdto);
	}
	
	@PostMapping("/update_pch_status.do")
	@ResponseBody
	public Map<String, Object> update_pch_status(@RequestBody Map<String, String> requestParam) {
		return pchreq_service.update_pch_status(requestParam);
	}
	
}
