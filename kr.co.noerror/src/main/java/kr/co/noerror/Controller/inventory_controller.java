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
	
	
	//입고창고 + 부자재창고 아이템별 재고수
	@GetMapping("/item_stock.do")
	public String item_stock(HttpServletResponse res
							, @RequestParam("") String item_code 
							) throws IOException {
		this.pw = res.getWriter();
		try {
			int result = this.inv_svc.ind_item_stock(item_code);
			this.pw.print(result);
			
		} catch (Exception e) {
			this.pw.print("error");
			
		} finally {
			this.pw.close();
		}
		return null;
	}
	
	
	//재고관리 페이지 이동 + 완제품 재고리스트  
	@GetMapping("/inventory.do")
	public String inventory_list(Model m
								,@RequestParam(value = "keyword", required = false) String keyword
								,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
								,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea ) {
		List<IOSF_DTO> pd_stock_list = this.inv_svc.pd_stock_list();  //제품 재고 리스트
		Map<String, Integer> ind_pd_stock = this.inv_svc.ind_pd_stock();
		
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
		m.addAttribute("ind_pd_stock", ind_pd_stock);
		
//		m.addAttribute("pageinfo", pageinfo);
		
		return "/inventory/inventory_list.html";
	}
	
	
	
	
	

}
