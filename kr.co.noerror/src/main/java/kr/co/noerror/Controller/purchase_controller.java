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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.mrp_DAO;
import kr.co.noerror.DAO.purchase_DAO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.order_DTO;
import kr.co.noerror.DTO.purchase_DTO;
import kr.co.noerror.DTO.purchase_item_DTO;
import kr.co.noerror.DTO.purchase_req_DTO;
import kr.co.noerror.Model.M_random;

@Controller
public class purchase_controller {
	
	private static final int page_ea = 5; //한페이지당 5개씩 출력
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	mrp_DAO mdao;
	
	@Autowired
	purchase_DAO pdao;
	
	@Resource(name="M_random")
	M_random mrandom;           
	
	@GetMapping("/purchase_detail.do")
	public String purchase_detail(@RequestParam(name="code") String pch_code, Model m) {
		List<purchase_DTO> details = this.pdao.purchase_detail(pch_code);
		System.out.println(details.get(0));
		m.addAttribute("details",details);
		return "/modals/purchase_detail_modal.html";
	}
	
	@GetMapping("/purchase.do")
	public String purchase(Model m,
			@RequestParam(name="pch_status", required=false) String[] pch_statuses,
			@RequestParam(name="search_column", defaultValue="all", required=false) String search_column,
			@RequestParam(name="search_word", defaultValue="", required=false) String search_word,
			@RequestParam(name="pageno", defaultValue="1", required=false) Integer pageno
			) {
		
		Map<String, Object> mparam = new HashMap<>();
		mparam.put("scolumn", search_column);  //검색 컬럼
		mparam.put("sword", search_word);  //검색어
		if (pch_statuses != null) {
			mparam.put("pch_statuses", Arrays.asList(pch_statuses)); // Mapper에서 IN 처리
		}
		
		int data_cnt = this.pdao.purchase_count(mparam);
		
		int page_cnt = (data_cnt-1) / page_ea + 1; //올림 처리하는 수식
		
		int start = (pageno - 1) * page_ea;  //oracle에서 해당페이지의 시작 번호
		int end = pageno * page_ea + 1;      //oracle에서 해당페이지의 종료 번호 + 1
		
		mparam.put("start", start);        //oracle 시작행 번호
		mparam.put("end", end);            //oracle 종료행 번호
		
		List<purchase_DTO> all = this.pdao.purchase_list(mparam);

		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","발주관리");
		
		//데이터, 페이징 정보를 모델에 전달
		m.addAttribute("all", all);
		m.addAttribute("page_cnt", page_cnt);
		m.addAttribute("pageno", pageno); 
		
	    //검색 조건을 모델에 다시 전달
	    m.addAttribute("search_column", search_column);
	    m.addAttribute("search_word", search_word);
	    m.addAttribute("pch_statuses", pch_statuses);
		
		return "/production/purchase_list.html";
	}
	
	@PostMapping("/purchase_request.do")
	@ResponseBody
    public Map<String, Object> purchase_request(@RequestBody Map<String, purchase_req_DTO> requestMap) {
		
		Map<String, Object> response = new HashMap<>();
		purchase_req_DTO pdto = null;
		
		int result1 = 0;
		int result2 = 0;
		int cnt = 0;
		try {
			for (Map.Entry<String, purchase_req_DTO> entry : requestMap.entrySet()) {
                String company_code = entry.getKey();
                pdto = entry.getValue();
			
		        //중복 없는 발주코드 생성
		        String pch_code = null;
		        int count = 0;
		        boolean is_duplicated = true;
		        while(is_duplicated) {
		        	pch_code = "pch-" + this.mrandom.random_no();
		        	count = this.pdao.pch_code_check(pch_code);
		        	if(count == 0){
		        		is_duplicated = false;
		        	}
		        }
		        
		        purchase_DTO pch = new purchase_DTO();
		        pch.setPch_code(pch_code);
		        pch.setCompany_code(company_code);
		        pch.setPch_status("발주요청");
		        pch.setDue_date(pdto.getDue_date());
		        pch.setPay_method(pdto.getPay_method());
		        pch.setEmp_code("EMP-00001");
		        pch.setMemo(pdto.getMemo());
		        
	        	int pay_amount = 0; 	
	            for (purchase_item_DTO idto : pdto.getItems()) {
	            	pay_amount += idto.getItem_amount();
	            }
	            pch.setPay_amount(pay_amount);
	            //purchase_req_header에 저장
	            result1 += this.pdao.insert_pch_header(pch);
	            
	            cnt += pdto.getItems().size();
	            for (purchase_item_DTO idto : pdto.getItems()) {
	            	pch.setItem_code(idto.getItem_code());
	            	pch.setItem_qty(idto.getItem_qty());
	            	pay_amount += idto.getItem_amount();
	            	//purchase_req_detail에 저자
	            	result2 += this.pdao.insert_pch_detail(pch);
	            }
	            
	        }
			System.out.println(result1);
			System.out.println(requestMap.size());
			System.out.println(result2);
			System.out.println(cnt);
            if((result1==requestMap.size()) && (result2 == cnt)) {
	            response.put("success", true);
            }
            else {
            	response.put("success", false);
            }

        } catch (Exception e) {
        	System.out.println(e);
            response.put("success", false);
        }

	    return response;
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
	
	@GetMapping("/purchase_in.do")
	public String purchase(Model m) {
		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","발주관리");
		m.addAttribute("mmenu","발주등록");
		return "/production/purchase_insert.html";
	}
}
