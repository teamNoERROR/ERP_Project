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
import kr.co.noerror.DAO.ordreq_DAO;
import kr.co.noerror.DTO.client_DTO;
import kr.co.noerror.DTO.ordreq_req_DTO;
import kr.co.noerror.DTO.ordreq_res_DTO;
import kr.co.noerror.DTO.paging_info_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.Model.M_paging2;
import kr.co.noerror.Model.M_unique_code_generator;
import kr.co.noerror.Service.generic_list_service;
import kr.co.noerror.Service.ordreq_service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ordreq_controller {
	
	private static final int page_block = 3; //페이지 번호 출력갯수
	
	private final generic_list_service<ordreq_res_DTO> ordreq_list_service;  //generic_list_service<ordreq_res_DTO> 생성자 주입
	private final ordreq_service ordreq_service;                             //ordreq_service 생성자 주입
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="ordreq_res_DTO")
	ordreq_res_DTO ordreq_res_dto;
	
	@Autowired
	ordreq_DAO odao;
	
	@Autowired
	private M_unique_code_generator unique_code_generator;
	
	@Autowired
	M_paging2 m_pg2;
		
	@PostMapping("/order_save.do")
	@ResponseBody
    public Map<String, Object> saveOrder(@RequestBody List<Map<String, Object>> orders) {
        return ordreq_service.ordreq_save(orders);
    }
	
	@GetMapping("/order.do")
	public String pchreq_list(@ModelAttribute search_condition_DTO search_cond, Model model) {
		
	    int search_count = this.ordreq_list_service.search_count(search_cond);
	    
	    paging_info_DTO paging_info = this.m_pg2.calculate(
	    		search_count, 
	    		search_cond.getPage_no(), 
	    		search_cond.getPage_size(), 
	    		page_block
	    );

	    List<ordreq_res_DTO> ord_list = this.ordreq_list_service.paged_list(search_cond, paging_info);

	    model.addAttribute("all", ord_list);
	    model.addAttribute("paging", paging_info);
	    model.addAttribute("condition", search_cond);
	    
	    return "/production/order_list.html";
	}
	
//	@GetMapping("/order.do")
//	public String order(Model m,
//			@RequestParam(name="order_status", required=false) String[] order_statuses,
//			@RequestParam(name="search_column", defaultValue="all", required=false) String search_column,
//			@RequestParam(name="search_word", defaultValue="", required=false) String search_word,
//			@RequestParam(name="pageno", defaultValue="1", required=false) Integer pageno
//			) {
//		
//		Map<String, Object> mparam = new HashMap<>();
//		mparam.put("scolumn", search_column);  //검색 컬럼
//		mparam.put("sword", search_word);  //검색어
//		if (order_statuses != null) {
//			mparam.put("order_statuses", Arrays.asList(order_statuses)); // Mapper에서 IN 처리
//		}
//		
//		int data_cnt = this.odao.order_count(mparam);
//		
//		int page_cnt = (data_cnt-1) / page_ea + 1; //올림 처리하는 수식
//		
//		int start = (pageno - 1) * page_ea;  //oracle에서 해당페이지의 시작 번호
//		int end = pageno * page_ea + 1;      //oracle에서 해당페이지의 종료 번호 + 1
//		
//		mparam.put("start", start);        //oracle 시작행 번호
//		mparam.put("end", end);            //oracle 종료행 번호
//		
//		List<ordreq_DTO> all = this.odao.order_list(mparam);
//
//		m.addAttribute("lmenu","구매영업관리");
//		m.addAttribute("smenu","주문관리");
//		
//		//데이터, 페이징 정보를 모델에 전달
//		m.addAttribute("all", all);
//		m.addAttribute("page_cnt", page_cnt);
//		m.addAttribute("pageno", pageno); 
//		
//	    //검색 조건을 모델에 다시 전달
//	    m.addAttribute("search_column", search_column);
//	    m.addAttribute("search_word", search_word);
//	    m.addAttribute("order_statuses", order_statuses);
//		
//		return "/production/order_list.html";
//	}

	@GetMapping("/products_modal.do")
	public String products_modal(Model m) {
		List<products_DTO> products = this.odao.products_list();
		m.addAttribute("products",products);
		return  "/modals/temp_products_list_modal.html";
	}
	
	@GetMapping("/clients_modal.do")
	public String clients_modal(Model m) {
		List<client_DTO> clients = this.odao.clients_for_order();
		m.addAttribute("clients",clients);
		return  "/modals/temp_client_list_modal.html";
	}
	
	@GetMapping("/order_insert.do")
	public String order_insert(Model m) {
		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","주문관리");
		m.addAttribute("mmenu","주문등록");
		return "/production/order_insert.html";
	}
	
	@GetMapping("/ordreq_detail.do")
	public String ordreq_detail(@RequestParam(name="code") String order_code, Model m) {
		List<ordreq_res_DTO> details = this.odao.ordreq_detail(order_code);
		m.addAttribute("details",details);
		return "/modals/order_detail_modal.html";
	}
	
	@GetMapping("/ordreq_update.do")
	public String pchreq_update(@RequestParam(name="code") String order_code, Model m) {
		List<ordreq_res_DTO> details = this.odao.ordreq_detail(order_code);
		m.addAttribute("details",details);
		return "/production/order_update.html";
	}
	
	@PostMapping("/ordreq_updateok.do")
	@ResponseBody
	public Map<String, Object> pchreq_updateok(@RequestBody ordreq_req_DTO requestdto) {
		return ordreq_service.ordreq_update(requestdto);
	}
	
	@PostMapping("/ord_status_update.do")
	@ResponseBody
	public Map<String, Object> ord_status_update(@RequestBody Map<String, String> requestParam) {
		return ordreq_service.ord_status_update(requestParam);
	}
}
