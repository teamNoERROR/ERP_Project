package kr.co.noerror;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.noerror.DTO.member_DTO;
import kr.co.noerror.Model.M_login_check;
import kr.co.noerror.Service.member_service;

@Controller
public class controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="login_ck") 
	M_login_check login_ck;  //로그인체크 모델 
	
	@Autowired
	private member_service mb_svc;
	
	PrintWriter pw =null;
	String msg = "";
	
	//메인화면
	@GetMapping("/")
	public String index(HttpServletResponse res) throws IOException {
		this.pw = res.getWriter();
		
		String login_yn = this.login_ck.login_chk();  //로그인 체크
		
		if(login_yn.equals("ok")){  //로그인 이미 되어있으면
			this.pw.print("");
		}
		
		return "/member/member_login.html";
	}
		
	//메인화면
	@GetMapping("/main.do")
	public String main(Model m) {
		String login_yn = this.login_ck.login_chk();  //로그인 체크
		if(login_yn.equals("ok")){  //로그인 이미 되어있으면
//			member_DTO login_member = this.mb_svc.member_one(); 
		}
		
		return "/common/main.html";
	}
	


}

