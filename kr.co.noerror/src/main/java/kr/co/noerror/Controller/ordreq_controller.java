package kr.co.noerror.Controller;

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
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	@Autowired
	private goods_service g_svc;  //품목 서비스
		
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
	    
	    List<ordreq_res_DTO> ord_list = this.ordreq_list_service.paged_list(search_cond, paging_info);

	    model.addAttribute("all", ord_list);
	    model.addAttribute("paging", paging_info);
	    model.addAttribute("condition", search_cond);
	    
	    return "/production/order_list.html";
	}
	
	//modal에 주문리스트 띄우기
	@GetMapping("/ord_list_modal.do")
	public String orders_modal(@ModelAttribute search_condition_DTO search_cond
								, Model model
								, @RequestParam(value="mode", required = false) String mode
								, @RequestParam(value = "parent", required = true) String parent) {
		
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
	    model.addAttribute("parentType",parent);
	    
		if ("modal2".equals(mode)) {
	        return "/modals/order_list_body_modal.html :: ordMdList";
	    } else {
	        return "/modals/order_list_modal.html"; 
	    }
	}
	
	//생산계획 작성시 주문코드에 해당하는 제품목록 제공
	@ResponseBody
	@GetMapping("/ordreq_products.do")
	public List<ordreq_res_DTO> ordreq_products(@RequestParam(name="code") String order_code) {
		List<ordreq_res_DTO> ordreq_products = this.odao.ordreq_products(order_code);
		return ordreq_products;
	}
	
	//제품 리스트 모달 띄우기(orderPage)
	@GetMapping("/products_modal.do")
	public String products_modal(Model m
								,@RequestParam(value="mode", required = false) String mode
								,@RequestParam(value = "keyword", required = false) String keyword
								,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
								,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea
								) {
		
		List<products_DTO> products = this.g_svc.gd_all_list_sch("product", null, keyword, pageno, post_ea);  //제품 리스트
		System.out.println("products : " + products.size() );
		
		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, post_ea, products.size());
		int bno = this.m_pg.serial_no(products.size(), pageno, post_ea); 
		
		m.addAttribute("products",products);
		m.addAttribute("pageinfo", pageinfo);
		m.addAttribute("bno", bno);
		m.addAttribute("keyword",keyword);
		
		if ("modal2".equals(mode)) {
	        return "/modals/product_list_body_modal2.html :: pdMdList2";
	    } else {
	    	return  "/modals/product_list_modal2.html";
	    }
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
