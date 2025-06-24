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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.noerror.DTO.inbound_DTO;
import kr.co.noerror.DTO.member_DTO;
import kr.co.noerror.Model.M_login_check;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Service.member_service;

@Controller
public class member_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired 
	HttpSession se;
	
	@Autowired
	private member_service mb_svc;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	@Resource(name="login_ck") 
	M_login_check login_ck;  //로그인체크 모델 
	
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
	
	//로그인 
	@PostMapping("/member_loginOk.do")
	public String member_loginOk(@ModelAttribute member_DTO m_dto, HttpServletResponse res) throws Exception {
		this.pw = res.getWriter();
		
		member_DTO login_member = this.mb_svc.login_member(m_dto); 
		
		if(login_member == null){  //아이디 및 패스워드가 틀릴경우 	
			
			this.pw.write("no");
			
		}
		else {    //로그인 후 회원정보 세션에 저장 
			String mid = login_member.getECODE();	
			String mname = login_member.getENAME();
			String mposi = login_member.getEMP_POSITION();
			int role = login_member.getROLE_ID();
			this.se.setAttribute("mid", mid);  //아이디  
			this.se.setAttribute("mname", mname);  //이름
			this.se.setAttribute("mposi", mposi);  //직급
			this.se.setAttribute("role", role);  //권한
			
			this.pw.write("ok");
		} 
		return null;
	}
	
	
	@GetMapping("/member_logoutOk.do")
	public String member_logout(HttpServletResponse res) throws IOException {
		this.pw = res.getWriter();
		String login_yn = this.login_ck.login_chk();  //로그인 체크
		if(login_yn.equals("ok")){  //로그인 이미 되어있으면
			this.se.invalidate(); //세션에 저장된 정보들 파기 
			
			this.pw.write("ok");
		}
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
