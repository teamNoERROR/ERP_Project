package kr.co.noerror.Controller;

import java.io.PrintWriter;
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

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.common_DAO;
import kr.co.noerror.DAO.pchreq_DAO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.client_DTO;
import kr.co.noerror.DTO.employee_DTO;
import kr.co.noerror.DTO.paging_info_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_paging2;
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
	private goods_service g_svc;  //품목 서비스
	
	@Autowired
	private inout_service io_svc;  //입출고리스트 서비스 
	
	@Autowired
	private client_service clt_svc;  //거래처 서비스
	
	@Autowired
	private generic_list_service<pchreq_res_DTO> pchreq_list_service;
	
	@Autowired
	pchreq_DAO pdao;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;


  @Resource(name="M_paging2")
	M_paging2 page_util;

	
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
		                  	    ,@RequestParam(value = "parent", required = true) String parent
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
		
		m.addAttribute("parentType",parent);
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
		
//		int inbound_total = this.io_svc.inbound_total(keyword, status); //입고 총개수
//		List<inout_DTO> inbound_all_list = this.io_svc.inbound_all_list(keyword, pageno, post_ea, status);  //입고 리스트 
		
		//페이징 관련 
		int pea = post_ea; 
//		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, pea, inbound_total);
//		int bno = this.m_pg.serial_no(inbound_total, pageno, pea); 
		
//		m.addAttribute("keyword",keyword);
//		m.addAttribute("bno", bno);
//		m.addAttribute("inbnd_total", inbound_total);
//		m.addAttribute("inbnd_list", inbound_all_list);
//		m.addAttribute("pageinfo", pageinfo);
//		m.addAttribute("pageno", pageno);
//		m.addAttribute("pea", pea);
		
		if ("modal2".equals(mode)) {
	        return "/modals/inbound_list_body_modal.html :: inbndMdList";
	    } else {
	        return "/modals/inbound_list_modal.html"; // 일반 뷰 전체
	    }
	}
	
	//발주 리스트 모달 띄우기 
  @GetMapping("/pch_list_modal.do")
  public String pch_list_modal(@ModelAttribute search_condition_DTO search_cond, Model model,@RequestParam(value="mode", required = false) String mode) {

    if (search_cond.getStatuses() != null && !search_cond.getStatuses().isEmpty()) {
      System.out.println(search_cond.getStatuses().get(0));
        List<String> statuses = search_cond.getStatuses().stream()
            .filter(s -> s != null && !s.trim().isEmpty())
            .collect(Collectors.toList());
        search_cond.setStatuses(statuses);
    }

      int search_count = this.pchreq_list_service.search_count(search_cond);

      paging_info_DTO paging_info = this.page_util.calculate(
          search_count, 
          search_cond.getPage_no(), 
          search_cond.getPage_size(), 
          page_block
      );

      List<pchreq_res_DTO> pch_list = this.pchreq_list_service.paged_list(search_cond, paging_info);

      model.addAttribute("pch_list", pch_list);
      model.addAttribute("paging", paging_info);
      model.addAttribute("condition", search_cond);

      if ("modal2".equals(mode)) {
          return "/modals/purchase_list_body_modal.html :: pchMdList";
      } else {
          return "/modals/purchase_list_modal.html"; 
      }
  }
	



		


}
