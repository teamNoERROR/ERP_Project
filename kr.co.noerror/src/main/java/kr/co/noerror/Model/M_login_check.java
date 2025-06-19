package kr.co.noerror.Model;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.servlet.http.HttpSession;

@Repository("login_ck")
public class M_login_check {
	
	@Autowired 
	HttpSession se;
	
	String msg = "";
	
	public String login_chk() {
		String mid = (String)this.se.getAttribute("mid");
		String mname = (String)this.se.getAttribute("mname");
		String mphone = (String)this.se.getAttribute("mphone");
		
		if(mid==null || mname==null || mphone==null) {
			this.msg = "no";
			
		}
		else {
			
			this.msg = "ok";
		}
		
		return this.msg;
	}
	
	

}
