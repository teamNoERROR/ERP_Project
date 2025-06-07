package kr.co.noerror.Controller;

import java.io.PrintWriter;
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
import kr.co.noerror.DTO.client_DTO;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Service.client_service;

@Controller
public class client_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	client_service clt_svc; 
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	List<String> list = null; 
	Map<String, String> map = null;
	String url = "";
	String msg = "";
	
	//거래처관리 > 거래처, 발주처 리스트 조회 
	@GetMapping("/client.do")
	public String client(Model m
							, @RequestParam(value = "type", required = false) String type
							,@RequestParam(value = "keyword", required = false) String keyword
							,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
							,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea ) {
		int client_total = this.clt_svc.client_total(type); //제품 총개수
		List<client_DTO> client_list = this.clt_svc.client_list(type, pageno, post_ea);  //제품 리스트
	
		System.out.println("type : " + type);
		
		//거래처타입에 따른 url 분류 
		if("client".equals(type) || type==null) {
			m.addAttribute("mmenu","거래처 리스트");
			this.url = "/client/client_list.html";
			
		}else if("p_client".equals(type)) {
			m.addAttribute("mmenu","발주처 리스트");
			this.url = "/client/client2_list.html";
		}
		
		//검색했을경우 
		if(keyword != null) {
			m.addAttribute("mmmenu","검색결과");
		}
				
		//페이징 관련 
		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, post_ea, client_total);
		int bno = this.m_pg.serial_no(client_total, pageno, post_ea); 
		System.out.println(pageinfo);
		
		//페이지로 보낼 것들 
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","거래처 관리");
		
//		m.addAttribute("keyword",keyword);
		
		m.addAttribute("bno", bno);
		m.addAttribute("client_total", client_total);
		m.addAttribute("client_list", client_list);
		
		m.addAttribute("pageinfo", pageinfo);
		m.addAttribute("pageno", pageno);
		
		return this.url;
	}
}
