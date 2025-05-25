package kr.co.noerror.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.order_DAO;
import kr.co.noerror.DAO.plan_DAO;
import kr.co.noerror.DTO.order_DTO;
import kr.co.noerror.DTO.plan_DTO;
import kr.co.noerror.DTO.temp_bom_DTO;
import kr.co.noerror.DTO.temp_client_DTO;
import kr.co.noerror.DTO.temp_products_DTO;
import kr.co.noerror.Model.M_random;

@Controller
public class plan_controller {
	private static final int page_ea = 3; //한페이지당 3개씩 출력
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="plan_DTO")
	plan_DTO pdto;
	
	@Autowired
	plan_DAO pdao;
	
	@Resource(name="M_random")
	M_random mrandom;
	
	@GetMapping("/orders_modal.do")
	public String orders_modal(Model m) {
		List<order_DTO> orders = this.pdao.orders_modal();
		m.addAttribute("orders",orders);
		return  "/modals/temp_order_list_modal.html";
	}
	
	@GetMapping("/production_in.do")
	public String production2(Model m) {
		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","생산계획리스트");
		m.addAttribute("mmenu","생산계획등록");
		return "/production/production_plan_insert.html";
	}

	@GetMapping("/bom_items.do")
	public String bom_items(Model m, @RequestParam(name="code") String bom_code) {

		List<temp_bom_DTO> bom_items = this.pdao.bom_items(bom_code);
		m.addAttribute("bom_items",bom_items);
		m.addAttribute("product_name",bom_items.get(0).getProduct_name());
		return  "/modals/temp_bom_items_modal.html";
	}
	
	@GetMapping("/production.do")
	public String production(Model m,
			@RequestParam(name="plan_status", required=false) String[] plan_statuses,
			@RequestParam(name="search_column", defaultValue="all", required=false) String search_column,
			@RequestParam(name="search_word", defaultValue="", required=false) String search_word,
			@RequestParam(name="pageno", defaultValue="1", required=false) Integer pageno
			) {
		
		Map<String, Object> mparam = new HashMap<>();
		mparam.put("scolumn", search_column);  //검색 컬럼
		mparam.put("sword", search_word);  //검색어
		if (plan_statuses != null) {
			mparam.put("plan_statuses", Arrays.asList(plan_statuses)); // Mapper에서 IN 처리
		}
		
		int data_cnt = this.pdao.plan_count(mparam);
		
		int page_cnt = (data_cnt-1) / page_ea + 1; //올림 처리하는 수식
		
		int start = (pageno - 1) * page_ea;  //oracle에서 해당페이지의 시작 번호
		int end = pageno * page_ea + 1;      //oracle에서 해당페이지의 종료 번호 + 1
		
		mparam.put("start", start);        //oracle 시작행 번호
		mparam.put("end", end);            //oracle 종료행 번호
		
		List<plan_DTO> all = this.pdao.plan_list(mparam);

		m.addAttribute("lmenu","생산 관리");
		m.addAttribute("smenu","생산계획리스트");
		
		//데이터, 페이징 정보를 모델에 전달
		m.addAttribute("all", all);
		m.addAttribute("page_cnt", page_cnt);
		m.addAttribute("pageno", pageno); 
		
	    //검색 조건을 모델에 다시 전달
	    m.addAttribute("search_column", search_column);
	    m.addAttribute("search_word", search_word);
	    m.addAttribute("plan_statuses", plan_statuses);
		
		return "/production/production_plan_list.html";
	}
	
}
