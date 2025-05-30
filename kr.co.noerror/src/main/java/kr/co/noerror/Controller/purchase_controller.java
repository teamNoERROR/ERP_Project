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
	
	@GetMapping("/purchase.do")
	public String purchase(Model m,
			@RequestParam(name="purchase_status", required=false) String[] purchase_statuses,
			@RequestParam(name="search_column", defaultValue="all", required=false) String search_column,
			@RequestParam(name="search_word", defaultValue="", required=false) String search_word,
			@RequestParam(name="pageno", defaultValue="1", required=false) Integer pageno
			) {
		
		Map<String, Object> mparam = new HashMap<>();
		mparam.put("scolumn", search_column);  //검색 컬럼
		mparam.put("sword", search_word);  //검색어
		if (purchase_statuses != null) {
			mparam.put("purchase_statuses", Arrays.asList(purchase_statuses)); // Mapper에서 IN 처리
		}
		
		int data_cnt = this.pdao.purchase_count(mparam);
		
		int page_cnt = (data_cnt-1) / page_ea + 1; //올림 처리하는 수식
		
		int start = (pageno - 1) * page_ea;  //oracle에서 해당페이지의 시작 번호
		int end = pageno * page_ea + 1;      //oracle에서 해당페이지의 종료 번호 + 1
		
		mparam.put("start", start);        //oracle 시작행 번호
		mparam.put("end", end);            //oracle 종료행 번호
		
		List<purchase_DTO> all = this.pdao.purchase_list(mparam);
		System.out.println(all);

		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","발주관리");
		
		//데이터, 페이징 정보를 모델에 전달
		m.addAttribute("all", all);
		m.addAttribute("page_cnt", page_cnt);
		m.addAttribute("pageno", pageno); 
		
	    //검색 조건을 모델에 다시 전달
	    m.addAttribute("search_column", search_column);
	    m.addAttribute("search_word", search_word);
	    m.addAttribute("purchase_statuses", purchase_statuses);
		
		return "/production/purchase_list.html";
	}
	
	@PostMapping("/purchase_request.do")
	@ResponseBody
    public Map<String, Object> purchase_request(@RequestBody List<purchase_DTO> purchases) {
		
		Map<String, Object> response = new HashMap<>();
		
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
        
        try {
        	//purchase_header 테이블에 저장
        	int result1 = this.pdao.insert_pch_header(pch_code);
        	
        	//purchase_request 테이블에 저장
        	int result2 = 0;
            for (purchase_DTO pdto : purchases) {
            	pdto.setPch_code(pch_code);
            	pdto.setPch_status("발주요청");
            	//System.out.println(pdto);
                result2 += this.pdao.insert_pch_detail(pdto);
            }

            if((result1==1) && (result2 == purchases.size())) {
	            response.put("success", true);
	            response.put("pch_code", pch_code);
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
