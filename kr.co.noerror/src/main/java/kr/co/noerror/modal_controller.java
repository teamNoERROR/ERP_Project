package kr.co.noerror;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Service.goods_service;

@Controller
public class modal_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private goods_service g_svc;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	//입고 리스트 모달 띄우기 
	@GetMapping("/product_list.do")
	public String inbound_list_modal(Model m
								,@RequestParam(value = "keyword", required = false) String keyword
								,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
								,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea
								,@RequestParam(value="mode", required = false) String mode
								)  {
		
		int goods_total_sch = this.g_svc.gd_all_ea_sch("product", null, keyword); //제품 총개수
		List<products_DTO> goods_all_list_sch = this.g_svc.gd_all_list_sch("product", null, keyword, pageno, post_ea);  //제품 리스트
		
		//페이징 관련 
		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, post_ea, goods_total_sch);
		int bno = this.m_pg.serial_no(goods_total_sch, pageno, post_ea); 
		
			m.addAttribute("keyword",keyword);
			m.addAttribute("bno", bno);
			m.addAttribute("pd_total", goods_total_sch);
			m.addAttribute("pd_list", goods_all_list_sch);
			m.addAttribute("pageinfo", pageinfo);
		
		if ("modal2".equals(mode)) {
	        return "/modals/product_list_body_modal.html :: pdMdList";
	    } else {
	        return "/modals/product_list_modal.html"; // 일반 뷰 전체
	    }
	}
}
