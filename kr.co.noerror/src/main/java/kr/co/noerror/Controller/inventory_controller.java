package kr.co.noerror.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Service.inventory_service;

@Controller
public class inventory_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	inventory_service inv_svc; 
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	

	
	//재고관리 페이지 이동 + 완제품 재고리스트  
	@GetMapping("/inventory.do")
	public String inventory_list(Model m
								,@RequestParam(value = "keyword", required = false) String keyword
								,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
								,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea 
								,@RequestParam(value="wh_code", defaultValue="5", required=false) String wh_code ) {
		List<IOSF_DTO> pd_stock_list = this.inv_svc.pd_stock_list();  //제품 재고 리스트
		Map<String, Integer> ind_pd_all_stock = this.inv_svc.ind_pd_all_stock();  //개별 완제품 재고수 
		//페이징 관련 
//		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, post_ea, gd_stock_list_sch.size());
//		int bno = this.m_pg.serial_no(gd_stock_list_sch.size(), pageno, post_ea); 
		
		
		
	
		//페이지로 보낼 것들 
		m.addAttribute("lmenu","입출고관리");
		m.addAttribute("smenu","재고 관리");
		m.addAttribute("smenu","품목 리스트");
		
		//검색했을경우 
		if(keyword != null) {
			m.addAttribute("mmmenu","검색결과");
		}

		m.addAttribute("keyword",keyword);
		
//		m.addAttribute("bno", bno);
		m.addAttribute("stock_pd_total", pd_stock_list.size());
		m.addAttribute("stock_pd_list", pd_stock_list);
		m.addAttribute("ind_pd_stock", ind_pd_all_stock);
		
//		m.addAttribute("pageinfo", pageinfo);
		
		return "/inventory/inventory_list.html";
	}
	
	
	//부자재 재고리스트  
	@GetMapping("/inventory_itm.do")
	public String inventory_itm(Model m
								,@RequestParam(value = "keyword", required = false) String keyword
								,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
								,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea 
								,@RequestParam(value="wh_code", defaultValue="5", required=false) String wh_code ) {
		List<IOSF_DTO> itm_stock_list = this.inv_svc.itm_stock_list();  //부자재 재고 리스트
		Map<String, Integer> ind_item_all_stock = this.inv_svc.ind_item_all_stock();  //개별 부자재 재고수 
		//페이징 관련 
//			Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, post_ea, gd_stock_list_sch.size());
//			int bno = this.m_pg.serial_no(gd_stock_list_sch.size(), pageno, post_ea); 
		System.out.println("itm_stock_list : " + itm_stock_list);
		System.out.println("ind_item_all_stock : " + ind_item_all_stock);
		
	
		//페이지로 보낼 것들 
		m.addAttribute("lmenu","입출고관리");
		m.addAttribute("smenu","재고 관리");
		m.addAttribute("smenu","부자재 리스트");
		
		//검색했을경우 
		if(keyword != null) {
			m.addAttribute("mmmenu","검색결과");
		}

		m.addAttribute("keyword",keyword);
		
//			m.addAttribute("bno", bno);
		m.addAttribute("stock_itm_total", itm_stock_list.size());
		m.addAttribute("stock_itm_list", itm_stock_list);
		m.addAttribute("ind_itm_stock", ind_item_all_stock);
		
//			m.addAttribute("pageinfo", pageinfo);
		
		return "/inventory/inventory_itm_list.html";
	}
	
	
	
	

}
