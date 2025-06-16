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
import kr.co.noerror.DTO.inbound_DTO;
import kr.co.noerror.DTO.member_DTO;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Service.member_service;

@Controller
public class member_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	private member_service mb_svc;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	List<String> list = null; 
	Map<String, String> map = null;
	String url = "";
	String msg = "";
	

	@GetMapping("/employee.do")
	public String employee(Model m
//						,@RequestParam(value = "keyword", required = false) String keyword
//						,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
//						,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea
//						,@RequestParam(value="status_lst", required=false) String[] status_lst
						) {
		//리스트 첫접속시 체크박스 상태값
//		if (status_lst == null) {
//			status_lst = new String[] {"가입고", "입고완료", "입고취소"};
//		}
		String role;
		
		List<member_DTO> member_all_list = this.mb_svc.member_all_list();  //사원 리스트
		
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","사용자관리");
		m.addAttribute("mmenu","사원리스트");
		
		m.addAttribute("member_all_list",member_all_list);
		m.addAttribute("member_all_total",member_all_list.size());
		
		return "/member/member_list.html";
	}
	
	
	
	@GetMapping("/member_insert.do")
	public String member_insert(Model m
//						,@RequestParam(value = "keyword", required = false) String keyword
//						,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
//						,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea
//						,@RequestParam(value="status_lst", required=false) String[] status_lst
						) {
	
		
		
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","사용자관리");
		m.addAttribute("mmenu","사원 등록");
		
		
		return "/member/member_insert.html";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
