package kr.co.noerror.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DAO.common_DAO;
import kr.co.noerror.DAO.pchreq_DAO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.client_DTO;
import kr.co.noerror.DTO.employee_DTO;
import kr.co.noerror.DTO.inout_DTO;
import kr.co.noerror.DTO.paging_info_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_paging_util;
import kr.co.noerror.Service.client_service;
import kr.co.noerror.Service.generic_list_service;
import kr.co.noerror.Service.goods_service;
import kr.co.noerror.Service.inout_service;

@Controller
public class common_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());

	PrintWriter pw = null;

	private static final int page_block = 3; //페이지 번호 출력갯수

	
	@Resource(name="employee_DTO")
	employee_DTO emp_dto;
	
	@Autowired
	common_DAO common_svc;
	
	@Autowired
	private goods_service g_svc;
	
	@Autowired
	private inout_service io_svc;
	
	@Autowired
	private client_service clt_svc;
	
	@Autowired
	private generic_list_service<pchreq_res_DTO> pch_svc;
	
	@Autowired
	pchreq_DAO pdao;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
    @Resource(name="M_paging_util")
    M_paging_util page_util;
	
	//관리자 리스트 모달 
	@GetMapping("/employee_list.do")
	public String emp_list(Model m) {
		List<employee_DTO> all_data = this.common_svc.emp_list();  //DB로드
	
		m.addAttribute("employees",all_data);
		
		return  "/modals/employee_list_modal.html";
	}
	
	//전체창고리스트 모달 
	@GetMapping("/warehouse_list.do")
	public String wh_list(Model m) {
		List<WareHouse_DTO> all_data = this.common_svc.warehouse_list(null);  //DB로드
		
		m.addAttribute("warehouse_list",all_data);
		
		return  "/modals/warehouse_list_modal.html";
	}
	
	//유형별 창고리스트 모달 띄우기
	@GetMapping("/wh_type_list.do")
	public String wh_type_list(Model m, @RequestParam("wh_type") String wh_type) {
		System.out.println(wh_type);
		List<WareHouse_DTO> all_data = this.common_svc.warehouse_list(wh_type);  //DB로드
		m.addAttribute("no_wh", "등록된 창고가 없습니다" );
		m.addAttribute("wh_tp_data", all_data);
		
		
		
		return  "/modals/wh_type_list_modal.html";
//		return null;
	}
	
	
	//거래처리스트 모달 띄우기 
	@GetMapping({"/client_list.do"})
	public String client_list(Model m
							,@RequestParam(value = "keyword", required = false) String keyword
							,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
							,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea 
							,@RequestParam(value="mode", required = false) String mode) {
		
		int client_total = this.clt_svc.client_total("client"); //제품 총개수
		List<client_DTO> client_list = this.clt_svc.client_list("client", pageno, post_ea);  //제품 리스트
	
		
		//페이징 관련 
		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, post_ea, client_total);
		int bno = this.m_pg.serial_no(client_total, pageno, post_ea); 
		
		m.addAttribute("keyword",keyword);
		m.addAttribute("bno", bno);
		m.addAttribute("clt_total", client_total);
		m.addAttribute("clt_list", client_list);
		m.addAttribute("pageinfo", pageinfo);
		m.addAttribute("pageno", pageno);
		m.addAttribute("pea", post_ea);
		
		if ("modal2".equals(mode)) {
	        return "/modals/client_list_body_modal.html :: pgList";
	    } else {
	        return "/modals/client_list_modal.html"; // 일반 뷰 전체
	    }
		
//		return "/modals/client_list_modal.html";
	}

	
	//발주처리스트 모달 띄우기 
	@GetMapping("/client_list2.do")
	public String client_list2(Model m
							,@RequestParam(value = "keyword", required = false) String keyword
							,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
							,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea 
							,@RequestParam(value="mode", required = false) String mode) {
		
		int client_total = this.clt_svc.client_total("p_client"); //제품 총개수
		List<client_DTO> client_list = this.clt_svc.client_list("p_client", pageno, post_ea);  //제품 리스트
	
		
		//페이징 관련 
		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, post_ea, client_total);
		int bno = this.m_pg.serial_no(client_total, pageno, post_ea); 
		
		m.addAttribute("keyword",keyword);
		m.addAttribute("bno", bno);
		m.addAttribute("clt_total", client_total);
		m.addAttribute("clt_list", client_list);
		m.addAttribute("pageinfo", pageinfo);
		m.addAttribute("pageno", pageno);
		m.addAttribute("pea", post_ea);
		
		if ("modal2".equals(mode)) {
	        return "/modals/client2_list_body_modal.html :: cl2PgList";
	    } else {
	        return "/modals/client2_list_modal.html"; // 일반 뷰 전체
	    }
		
//		return "/modals/clientp_list_modal.html";
	}
	
	
	//부자재 리스트 모달 띄우기 
	@GetMapping("/item_list.do")
	public String item_list(Model m
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
	        return "/modals/item_list_body_modal.html :: itmMdList";
	    } else {
	        return "/modals/items_list_modal.html"; 
	    }
//			return "/modals/items_list_modal.html";
	}
	
	
	//입고 리스트 모달 띄우기 
	@GetMapping("/inbound_list.do")
	public String inbound_list_modal(Model m
								,@RequestParam(value = "keyword", required = false) String keyword
								,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
								,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea
								,@RequestParam(value="mode", required = false) String mode
								,@RequestParam(value = "status", required = false) String[] status
								)  {
		
		int inbound_total = this.io_svc.inbound_total(keyword, status); //입고 총개수
		List<inout_DTO> inbound_all_list = this.io_svc.inbound_all_list(keyword, pageno, post_ea, status);  //입고 리스트 
		
		//페이징 관련 
		int pea = post_ea; 
		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, pea, inbound_total);
		int bno = this.m_pg.serial_no(inbound_total, pageno, pea); 
		
		m.addAttribute("keyword",keyword);
		m.addAttribute("bno", bno);
		m.addAttribute("inbnd_total", inbound_total);
		m.addAttribute("inbnd_list", inbound_all_list);
		m.addAttribute("pageinfo", pageinfo);
		m.addAttribute("pageno", pageno);
		m.addAttribute("pea", pea);
		
		if ("modal2".equals(mode)) {
	        return "/modals/inbound_list_body_modal.html :: inbndMdList";
	    } else {
	        return "/modals/inbound_list_modal.html"; // 일반 뷰 전체
	    }
//			return "/modals/inbound_list_modal.html";
	}
	
	
	//발주 리스트 모달 띄우기 
	@GetMapping("/pch_list.do")
	public String pch_list_modal(@ModelAttribute search_condition_DTO search_cond, Model model) {

	    int search_count = this.pch_svc.search_count(search_cond);
	    
	    paging_info_DTO paging_info = this.page_util.calculate(
	    		search_count, 
	    		search_cond.getPage_no(), 
	    		search_cond.getPage_size(), 
	    		page_block
	    );

	    List<pchreq_res_DTO> pch_list = this.pch_svc.paged_list(search_cond, paging_info);

	    model.addAttribute("pch_list", pch_list);
	    model.addAttribute("paging", paging_info);
	    model.addAttribute("condition", search_cond);
	    return "/modals/purchase_list_body_modal.html";
	}
	
	/*
	@GetMapping({"/pch_list.do"})
	public String pch_list_modal(Model m
								,@RequestParam(name="views", defaultValue="5", required=false) Integer page_ea
								,@RequestParam(name="pageno", defaultValue="1", required=false) Integer pageno
								,@RequestParam(name="pch_status", required=false) String[] pch_statuses
								,@RequestParam(name="search_word", defaultValue="", required=false) String search_word
//								,@RequestParam(name="code",  required=false) String pch_code
								,@RequestParam(value="mode", required = false) String mode
								)  {
		System.out.println(page_ea);
		System.out.println(pageno);
		System.out.println(mode);
		
		int page_block = 3;
		Map<String, Object> mparam = new HashMap<>();
		mparam.put("sword", search_word);  //검색어
		if (pch_statuses != null) {
			mparam.put("pch_statuses", Arrays.asList(pch_statuses)); // Mapper에서 IN 처리
		}
		
		//int data_cnt = this.pdao.search_count(mparam);
		
		//int page_cnt = (data_cnt-1) / page_ea + 1; //올림 처리하는 수식
		
		int start = (pageno - 1) * page_ea;  //oracle에서 해당페이지의 시작 번호
		int end = pageno * page_ea + 1;      //oracle에서 해당페이지의 종료 번호 + 1
		
		int start_page = ((pageno - 1) / page_block) * page_block + 1;
		//int end_page = Math.min(start_page + page_block - 1, page_cnt);
		
		mparam.put("start", start);        //oracle 시작행 번호
		mparam.put("end", end);            //oracle 종료행 번호
		
		List<pchreq_res_DTO> all = this.pdao.paged_list(mparam);
	
		
		//데이터, 페이징 정보를 모델에 전달
		m.addAttribute("pch_list", all);
		m.addAttribute("start_page", start_page);
		//m.addAttribute("end_page", end_page);
		m.addAttribute("view", page_ea);
		//m.addAttribute("page_cnt", page_cnt);
		m.addAttribute("pageno", pageno); 
		
		//검색 조건을 모델에 다시 전달
		m.addAttribute("search_word", search_word);
		m.addAttribute("pch_statuses", pch_statuses);
		
//		List<pchreq_res_DTO> details = this.pdao.pchreq_detail(pch_code);
//		m.addAttribute("details",details);
		
		if ("modal2".equals(mode)) {
	        return "/modals/purchase_list_body_modal.html :: pchMdList";
	    } else {
	        return "/modals/purchase_list_modal.html"; 
	    }
	}

	*/
		


}
