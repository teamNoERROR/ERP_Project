package kr.co.noerror.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.noerror.DAO.order_DAO;
import kr.co.noerror.DTO.order_DTO;
import kr.co.noerror.DTO.temp_client_DTO;
import kr.co.noerror.DTO.temp_products_DTO;

@Controller
public class order_controller {
	
	private static final int page_ea = 3; //한페이지당 3개씩 출력
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	order_DAO odao;

	@PostMapping("/order_save.do")
	public ResponseEntity<?> saveOrders(@RequestBody List<order_DTO> orders) {
	    // unique 제약: ORDER_CODE + PRODUCT_CODE
	    // DB insert or update 로직 구현
		int result = 0;
		String msg = "";
	    for (order_DTO dto : orders) {
	    	System.out.println(dto);
	        //result += this.odao.insert_order(dto);
	    }
	    if(result == orders.size()) {
	    	msg = "저장 성공";
	    }
	    else {
	    	msg = "저장 실패";
	    }
	    return ResponseEntity.ok().body(msg);
	}
	
	@GetMapping("/temp_products_list.do")
	public String temp_product_list(Model m) {
		List<temp_products_DTO> products = this.odao.products_list();
		
		m.addAttribute("products",products);
		
		return  "/modals/temp_products_list_modal.html";
	}
	
	@GetMapping("/temp_client_list.do")
	public String temp_clent_list(Model m) {
		List<temp_client_DTO> clients = this.odao.client_list();
		
		m.addAttribute("clients",clients);
		
		return  "/modals/temp_client_list_modal.html";
	}
	
	@GetMapping("/order_insert.do")
	public String order_insert(Model m) {
		
		return "/production/order_insert.html";
	}
	
	@GetMapping("/order.do")
	public String order(Model m,
			@RequestParam(name="order_status", required=false) String[] order_statuses,
			@RequestParam(name="search_column", defaultValue="all", required=false) String search_column,
			@RequestParam(name="search_word", defaultValue="", required=false) String search_word,
			@RequestParam(name="pageno", defaultValue="1", required=false) Integer pageno
			) {
		
		Map<String, Object> mparam = new HashMap<>();
		mparam.put("scolumn", search_column);  //검색 컬럼
		mparam.put("sword", search_word);  //검색어
		if (order_statuses != null) {
			mparam.put("order_statuses", Arrays.asList(order_statuses)); // Mapper에서 IN 처리
		}
		
		int data_cnt = this.odao.order_count(mparam);
		
		int page_cnt = (data_cnt-1) / page_ea + 1; //올림 처리하는 수식
		
		int start = (pageno - 1) * page_ea;  //oracle에서 해당페이지의 시작 번호
		int end = pageno * page_ea + 1;      //oracle에서 해당페이지의 종료 번호 + 1
		
		mparam.put("start", start);        //oracle 시작행 번호
		mparam.put("end", end);            //oracle 종료행 번호
		
		List<order_DTO> all = this.odao.order_list(mparam);

		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","주문관리");
		
		//데이터, 페이징 정보를 모델에 전달
		m.addAttribute("all", all);
		m.addAttribute("page_cnt", page_cnt);
		m.addAttribute("pageno", pageno); 
		
	    //검색 조건을 모델에 다시 전달
	    m.addAttribute("search_column", search_column);
	    m.addAttribute("search_word", search_word);
	    m.addAttribute("order_statuses", order_statuses);
		
		return "/production/order_list.html";
	}
}
