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
import kr.co.noerror.DAO.mrp_DAO;
import kr.co.noerror.DAO.pchreq_DAO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.paging_info_DTO;
import kr.co.noerror.DTO.pchreq_req_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_paging2;
import kr.co.noerror.Service.generic_list_service;
import kr.co.noerror.Service.goods_service;
import kr.co.noerror.Service.pchreq_service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class pchreq_controller {
	
	private static final int page_block = 3; //페이지 번호 출력갯수
	
	private final generic_list_service<pchreq_res_DTO> pchreq_list_service;  //generic_list_service<pchreq_res_DTO> 생성자 주입
	private final pchreq_service pchreq_service;                             //pchreq_service 생성자 주입
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	mrp_DAO mdao;
	
	@Autowired
	pchreq_DAO pdao;
	
	@Autowired
	paging_info_DTO paging_info;
	
	@Autowired
	private goods_service g_svc;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	@Autowired
	M_paging2 m_pg2;
	
	//버튼 누름 방지
//    String [] no_chng_pchBtn = {"주문확인","주문취소","생산계획수립","생산계획확정"};

	//발주저장 
	@GetMapping("/pchreq_insert.do")
	public String pchreq_insert(Model m) {
		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","발주관리");
		m.addAttribute("mmenu","발주등록");
		return "/production/purchase_insert.html";
	}
	
	//
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
	
	//
	@PostMapping("/pchreq_save.do")
	@ResponseBody
	public Map<String, Object> pchreq_save(@RequestBody Map<String, pchreq_req_DTO> requestMap) {
		System.out.println(requestMap);
		return pchreq_service.pchreq_save(requestMap);
	}
	
	//발주 리스트 
	@GetMapping("/pchreq_list.do")
	public String pchreq_list(@ModelAttribute search_condition_DTO search_cond, Model model) {
		
	    int search_count = this.pchreq_list_service.search_count(search_cond);
	    
	    paging_info_DTO paging_info = this.m_pg2.calculate(
	    		search_count, 
	    		search_cond.getPage_no(), 
	    		search_cond.getPage_size(), 
	    		page_block
	    );

	    List<pchreq_res_DTO> pch_list = this.pchreq_list_service.paged_list(search_cond, paging_info, null);

	    model.addAttribute("lmenu","구매영업관리");
	    model.addAttribute("smenu","발주 관리");
		model.addAttribute("mmenu","발주 리스트");
		
	    model.addAttribute("pch_list", pch_list);
	    model.addAttribute("paging", paging_info);
	    model.addAttribute("condition", search_cond);
	    
	    return "/production/purchase_list.html";
	}
	
	//발주리스트 모달연계용 서브페이지 
	@GetMapping("/pchreq_list_sub.do")
	public String pchreq_list_sub (@ModelAttribute search_condition_DTO search_cond, Model model) {
		
		int search_count = this.pchreq_list_service.search_count(search_cond);
		    
	    paging_info_DTO paging_info = this.m_pg2.calculate(
	    		search_count, 
	    		search_cond.getPage_no(), 
	    		search_cond.getPage_size(), 
	    		page_block
	    );

	    List<pchreq_res_DTO> pch_list = this.pchreq_list_service.paged_list(search_cond, paging_info,null);

	    model.addAttribute("lmenu","구매영업관리");
	    model.addAttribute("smenu","발주 관리");
		model.addAttribute("mmenu","발주 리스트");
		
	    model.addAttribute("pch_list", pch_list);
	    model.addAttribute("paging", paging_info);
	    model.addAttribute("condition", search_cond);
	    
	    return "/production/purchase_list_sub.html";
	}
	
	/*
	@GetMapping("/pchreq_list2.do")
	public String pchreq_list2(Model m,
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
		
		int data_cnt = this.pdao.search_count(mparam);
		
		int page_cnt = (data_cnt-1) / page_ea + 1; //올림 처리하는 수식
		
		int start = (pageno - 1) * page_ea;  //oracle에서 해당페이지의 시작 번호
		int end = pageno * page_ea + 1;      //oracle에서 해당페이지의 종료 번호 + 1
		
		int start_page = ((pageno - 1) / page_block) * page_block + 1;
		int end_page = Math.min(start_page + page_block - 1, page_cnt);
		
		mparam.put("start", start);        //oracle 시작행 번호
		mparam.put("end", end);            //oracle 종료행 번호
		
		List<pchreq_res_DTO> all = this.pdao.paged_list(mparam);
		
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
	*/
	
	
	//발주내역 상세보기 
	@GetMapping("/pchreq_detail.do")
	public String pchreq_detail(@RequestParam(name="code") String pch_code, Model m) {
		
		List<pchreq_res_DTO> details = this.pdao.pchreq_detail(pch_code);
		
//		List<String> allStatuses = Arrays.asList("발주완료", "발주취소", "진행중", "지연중", "가입고", "입고완료", "반품");
//		m.addAttribute("allStatuses", allStatuses);

//		Map<String, List<String>> statusMap = new HashMap<>();
//		statusMap.put("발주완료", Arrays.asList("진행중", "지연중", "가입고", "입고완료"));
//		statusMap.put("진행중", Arrays.asList("지연중", "가입고", "입고완료"));
//		statusMap.put("지연중", Arrays.asList("가입고", "입고완료"));
//		statusMap.put("가입고", Arrays.asList("입고완료"));
//		statusMap.put("입고완료", Arrays.asList("반품"));
//
//		m.addAttribute("nextStatusMap", statusMap);
		
		m.addAttribute("details",details);
		
		return "/modals/purchase_detail_modal.html";
		
	}
	
	@GetMapping("/pchreq_update.do")
	public String pchreq_update(@RequestParam(name="code") String pch_code, Model m) {
		List<pchreq_res_DTO> details = this.pdao.pchreq_detail(pch_code);
		m.addAttribute("details",details);
		return "/production/purchase_update.html";
	}
	
	@PostMapping("/pchreq_updateok.do")
	@ResponseBody
	public Map<String, Object> pchreq_updateok(@RequestBody pchreq_req_DTO requestdto) {
		return pchreq_service.pchreq_update(requestdto);
	}
	
	@PostMapping("/pch_status_update.do")
	@ResponseBody
	public Map<String, Object> pch_status_update(@RequestBody Map<String, String> requestParam) {
		return pchreq_service.pch_status_update(requestParam);
	}
	
	//부자재 리스트 모달 띄우기 
	@GetMapping("/item_list2.do")
	public String item_list2(Model m
								,@RequestParam(value = "type", required = false) String type
								,@RequestParam(value = "keyword", required = false) String keyword
								,@RequestParam(value = "products_class2", required = false) String sclass
								,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
								,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea
								,@RequestParam(value="mode", required = false) String mode
								)  {
		
		int goods_total = this.g_svc.gd_all_ea_sch("item", sclass, keyword); //제품 총개수
		List<products_DTO> goods_all_list = this.g_svc.gd_all_list_sch("item",sclass, keyword, pageno, post_ea);  //제품 리스트 
		
		//페이징 관련 
		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, post_ea, goods_total);
		int bno = this.m_pg.serial_no(goods_total, pageno, post_ea); 
		
		m.addAttribute("keyword",keyword);
		m.addAttribute("bno", bno);
		m.addAttribute("items_total", goods_total);
		m.addAttribute("items_list", goods_all_list);
		m.addAttribute("pageinfo", pageinfo);
		m.addAttribute("pageno", pageno);
		m.addAttribute("pea", post_ea);
		
		
		if ("modal2".equals(mode)) {
	        return "/modals/item_list_body_modal2.html :: itmMdList";
	    } else {
	        return "/modals/items_list_modal2.html"; 
	    }
	}
	
}
