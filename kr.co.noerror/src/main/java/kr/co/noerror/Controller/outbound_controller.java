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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DTO.outbound_DTO;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Service.outbound_service;

@Controller
public class outbound_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	private outbound_service out_svc;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	List<String> list = null; 
	Map<String, String> map = null;
	String url = "";
	String msg = "";
	
	//제품출고 리스트 
	@GetMapping("/outbound.do")
	public String outbound(Model m
						,@RequestParam(value = "keyword", required = false) String keyword
						,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
						,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea
						,@RequestParam(value="out_status_lst", required=false) String[] out_status_lst
						) {
	
		//리스트 첫접속시 체크박스 상태값
		if (out_status_lst == null) {
			out_status_lst = new String[] {"출고대기", "출고완료", "부분출고", "출고취소"};
		}
		List<outbound_DTO> outbound_all_list = this.out_svc.outbound_all_list(keyword, pageno, post_ea, out_status_lst);  //입고리스트 제품 리스트
		
		//페이징 관련 
		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, post_ea, outbound_all_list.size());
		int bno = this.m_pg.serial_no(outbound_all_list.size(), pageno, post_ea); 
		
		m.addAttribute("lmenu","입출고관리");
		m.addAttribute("smenu","제품 출고");
		m.addAttribute("mmenu","출고 리스트");
		
		m.addAttribute("out_statusList", out_status_lst); //체크박스 상태용
		m.addAttribute("keyword",keyword);
		
		m.addAttribute("outbound_total",outbound_all_list.size());
		m.addAttribute("outbound_all_list",outbound_all_list);  //데이터 리스트 
		
		m.addAttribute("bno", bno);
		m.addAttribute("pageinfo", pageinfo);
		
		return "/inout/outbound_list.html";
	}
	
	//출고등록 화면이동 
	@GetMapping("/outbound_insert.do")
	public String outbound_insert(Model m) {
		m.addAttribute("lmenu","입출고관리");
		m.addAttribute("smenu","자재 출고");
		m.addAttribute("mmenu","출고 등록");
		
		return "/inout/outbound_insert.html";
	}
	
	//출고등록
	@PutMapping("/outbound_insertok.do")
	public String outbound_insertok(@RequestBody String out_pds, HttpServletResponse res) {
		System.out.println("out_pds : " + out_pds);
		try {
			this.pw = res.getWriter();
			
			int result = this.out_svc.outbnd_insert(out_pds);
			
			if(result > 0) {
				this.pw.write("ok");  //출고 등록 완료 
				
			}else {
				this.pw.write("fail"); //출고 등록실패
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
