package kr.co.noerror.Controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DTO.inout_DTO;
import kr.co.noerror.Service.inout_service;

@Controller
public class inout_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	private inout_service io_svc;
	
	//자재입고 리스트 
	@GetMapping("/inbound.do")
	public String inbound(Model m) {
		m.addAttribute("lmenu","입출고관리");
		m.addAttribute("smenu","자재 입고");
		m.addAttribute("mmenu","입고 리스트");
		return "/inout/inbound_list.html";
	}
	
	//자재입고등록 
	@GetMapping("/inbound_insert.do")
	public String inbound_insert(Model m) {
		m.addAttribute("lmenu","입출고관리");
		m.addAttribute("smenu","자재 입고");
		m.addAttribute("mmenu","입고 등록");
		
		return "/inout/inbound_insert.html";
	}
	
	@PutMapping("/inbound_insertok.do")
	public String inbound_insertok(@RequestBody String inbnd_item, HttpServletResponse res) {
		System.out.println(inbnd_item);
		
		try {
			this.pw = res.getWriter();
			
			int result = this.io_svc.inbnd_insert(inbnd_item);   //db에 데이터 저장 
			if(result > 0) {  
				this.pw.write("ok");  //입고 등록 완료 
			}
			else {
				this.pw.write("fail"); //입고 등록실패
			}
			
		} catch (IOException e) {
			this.pw.write("error"); //입고 등록실패
			this.log.error(e.toString());
			e.printStackTrace();
			
		} finally {
			this.pw.close();
		}
		
		return null;
	}
	
	

	
	
	

}
