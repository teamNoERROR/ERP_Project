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
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.order_DAO;
import kr.co.noerror.DTO.order_DTO;
import kr.co.noerror.DTO.temp_client_DTO;
import kr.co.noerror.DTO.temp_products_DTO;
import kr.co.noerror.Model.M_random;

@Controller
public class order_controller {
	
	private static final int page_ea = 5; //한페이지당 5개씩 출력
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="order_DTO")
	order_DTO odto;
	
	@Autowired
	order_DAO odao;
	
	@Resource(name="M_random")
	M_random mrandom;
	
	@PostMapping("/order_save.do")
	@ResponseBody
    public Map<String, Object> saveOrder(@RequestBody List<Map<String, Object>> orders) {
        Map<String, Object> response = new HashMap<>();
        
        //중복 없는 주문코드 생성
        String ORDER_CODE = null;
        int count = 0;
        boolean is_duplicated = true;
        while(is_duplicated) {
        	ORDER_CODE = "ORD-" + this.mrandom.random_no();
        	count = this.odao.order_code_check(ORDER_CODE);
        	if(count == 0){
        		is_duplicated = false;
        	}
        }
        
        try {
        	//order_header 테이블에 저장
        	int result1 = this.odao.insert_order_header(ORDER_CODE);
        	
        	//order_request 테이블에 저장
        	int result2 = 0;
            for (Map<String, Object> item : orders) {
                this.odto.setORDER_CODE(ORDER_CODE);
                this.odto.setPRODUCT_CODE((String)item.get("PRODUCT_CODE"));
                this.odto.setORDER_QTY((int)item.get("ORDER_QTY"));
                this.odto.setCOMPANY_CODE((String)item.get("COMPANY_CODE"));
                this.odto.setCOMPANY_NAME((String)item.get("COMPANY_NAME"));
                this.odto.setMANAGER_CODE((String)item.get("MANAGER_CODE"));
                this.odto.setMANAGER_NAME((String)item.get("MANAGER_NAME"));
                this.odto.setORDER_STATUS((String)item.get("ORDER_STATUS"));
                this.odto.setDUE_DATE((String)item.get("DUE_DATE"));
                this.odto.setMEMO((String)item.get("MEMO"));
                this.odto.setREQUEST_DATE((String)item.get("REQUEST_DATE"));
                this.odto.setMODIFY_DATE((String)item.get("MODIFY_DATE"));
 
                result2 += this.odao.insert_order(this.odto);
            }

            if((result1==1) && (result2 == orders.size())) {
	            response.put("success", true);
	            response.put("message", "주문 저장 성공");
            }
            else {
            	response.put("success", false);
                response.put("message", "저장 실패");
            }

        } catch (Exception e) {
        	System.out.println(e);
            response.put("success", false);
            response.put("message", "저장 실패: " + e.getMessage());
        }

        return response;
    }
	
	@GetMapping("/products_modal.do")
	public String products_modal(Model m) {
		List<temp_products_DTO> products = this.odao.products_list();
		
		m.addAttribute("products",products);
		
		return  "/modals/temp_products_list_modal.html";
	}
	
	@GetMapping("/clients_modal.do")
	public String clients_modal(Model m) {
		List<temp_client_DTO> clients = this.odao.client_list();
		
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
