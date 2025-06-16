package kr.co.noerror.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import kr.co.noerror.DTO.pchreq_res_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_paging2;
import kr.co.noerror.Model.M_unique_code_generator;
import kr.co.noerror.Service.generic_list_service;
import kr.co.noerror.Service.goods_service;
import kr.co.noerror.Service.inventory_service;
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
	inventory_service inv_svc; 
	
	@Autowired
	private M_unique_code_generator unique_code_generator;
	
	@Autowired
	M_paging2 m_pg2;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	@Autowired
	private goods_service g_svc;  //품목 서비스
	
	 //버튼 누름 방지
    String [] no_chng_ordBtn = {"주문확인","주문취소","생산계획수립","생산계획확정"};
		
	@PostMapping("/order_save.do")
	@ResponseBody
    public Map<String, Object> saveOrder(@RequestBody List<Map<String, Object>> orders) {
        return ordreq_service.ordreq_save(orders);
    }
	
	//주문 리스토 출력
	@GetMapping("/order.do")
	public String ordreq_list(@ModelAttribute search_condition_DTO search_cond, Model model) {
		
	    int search_count = this.ordreq_list_service.search_count(search_cond);
	    
	    paging_info_DTO paging_info = this.m_pg2.calculate(
	    		search_count, 
	    		search_cond.getPage_no(), 
	    		search_cond.getPage_size(), 
	    		page_block
	    );
	    System.out.println(paging_info);
	    
	    List<ordreq_res_DTO> ord_list = this.ordreq_list_service.paged_list(search_cond, paging_info, null);

	    model.addAttribute("lmenu","구매영업관리");
	    model.addAttribute("smenu","주문 관리");
	    model.addAttribute("mmenu","주문 리스트");
	    
	    model.addAttribute("all", ord_list);
	    model.addAttribute("paging", paging_info);
	    model.addAttribute("condition", search_cond);
	    
	    return "/production/order_list.html";
	}
	
	
	
	//생산계획 작성시 주문제품목록 제공
	@ResponseBody
	@GetMapping("/ordreq_products.do")
	public List<ordreq_res_DTO> ordreq_products(@RequestParam(name="code") String order_code) {
		List<ordreq_res_DTO> ordreq_products = this.odao.ordreq_products(order_code);
		Map<String, Integer> ind_pd_all_stock = this.inv_svc.ind_pd_all_stock();  //상품별 재고수
		for (ordreq_res_DTO dto : ordreq_products) {
		    String code = dto.getProduct_code();
		    int stockQty = ind_pd_all_stock.getOrDefault(code, 0);  // 없으면 0
		    dto.setInd_pd_all_stock(stockQty);  // 재고 주입
		}
		
		return ordreq_products;
	}
	
	
	/*
	@GetMapping("/clients_modal.do")
	public String clients_modal(Model m) {
		List<client_DTO> clients = this.odao.clients_for_order();
		m.addAttribute("clients",clients);
		return  "/modals/temp_client_list_modal.html";
	}*/
	
	
	
	@GetMapping("/order_insert.do")
	public String order_insert(Model m) {
		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","주문 관리");
		m.addAttribute("mmenu","주문 등록");
		return "/production/order_insert.html";
	}
	
	
	//주문건 상세보기 
	@GetMapping("/ordreq_detail.do")
	public String ordreq_detail(@RequestParam(name="code") String order_code, Model m) {
		
		
		List<ordreq_res_DTO> details = this.odao.ordreq_detail(order_code);  //주문상세
		
		Map<String, Integer> ind_pd_all_stock = this.inv_svc.ind_pd_all_stock();  //상품별 재고수 
		
		m.addAttribute("details",details);
		m.addAttribute("ind_pd_all_stock",ind_pd_all_stock);
		 m.addAttribute("no_chng_ordBtn", no_chng_ordBtn); // 상태 변경 불가
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
