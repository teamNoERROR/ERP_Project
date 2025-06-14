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
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.client_DTO;
import kr.co.noerror.DTO.products_DTO;
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
		
		m.addAttribute("keyword",keyword);
		
		m.addAttribute("bno", bno);
		m.addAttribute("client_total", client_total);
		m.addAttribute("client_list", client_list);
		
		m.addAttribute("pageinfo", pageinfo);
		m.addAttribute("pageno", pageno);
		
		return this.url;
	}
	
	
	//거래처 상세보기 모달 
	@PostMapping("/client_detail.do")
	public String client_detail(Model m, @RequestParam("cidx") String cidx
								, @RequestParam("code") String code
								, @RequestParam("type") String type) {
		
		client_DTO client_one = this.clt_svc.clt_one_detail(code, cidx);  //특정게시물 내용 가져오기
		
		if(client_one == null) {
			this.msg = "alert('시스템문제로 해당 거래처의 상세페이지를 불러올 수 없습니다.');"
					+ "history.go(-1);";
			m.addAttribute("msg", msg);
			this.url = "WEB-INF/views/message";
			
		}else {
			m.addAttribute("client_one", client_one);
			this.url = "/modals/client_detail_modal.html";
		}
		return this.url;
	}
	
	
	
	//거래처 등록하기 화면이동 
	@GetMapping("/client_insert.do")
	public String client_insert(Model m) throws IOException {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","거래처 관리");
		m.addAttribute("mmenu","거래처 등록");
		
		return "/client/client_insert.html";
	}
	
	
	
	//거래처 등록 
	@PostMapping("/client_insertok.do")
	public String client_insertok(@ModelAttribute client_DTO cdto,
									@RequestParam(value = "clientImage", required = false) MultipartFile clientImage,
									HttpServletResponse res) {
		try {
			this.pw = res.getWriter();
			
			int result = this.clt_svc.clt_insert(cdto, clientImage);  
			
			if(result > 0) {  
				this.pw.write("ok");  //거래처 등록 완료 
			}
			else {
				this.pw.write("fail"); //거래처 등록실패
			}
			
		} catch (IOException e) {
			this.pw.write("error"); //제품 등록실패
			this.log.error(e.toString());
			e.printStackTrace();
			
		} finally {
			this.pw.close();
		}
		
		return null;
	}
	
	
	//거래처 수정하기 화면이동 
	@GetMapping("/client_modify.do")
	public String client_modify(Model m, @RequestParam("code1") String cidx
								, @RequestParam("code2") String code
								) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","거래처 관리");
		m.addAttribute("mmenu","거래처 수정");
		
		client_DTO client_one = this.clt_svc.clt_one_detail(code, cidx);  //특정게시물 내용 가져오기
		System.out.println(client_one);
		m.addAttribute("client_one", client_one);
		return "/client/client_modify.html";
	}
	
	
	//거래처 정보 수정 
	@PostMapping("/client_modifyok.do")
	public String client_modifyok(@ModelAttribute client_DTO cdto
									,@RequestParam(value = "clientImage", required = false) MultipartFile clientImage
									,HttpServletResponse res) {
		try {
			this.pw = res.getWriter();
			int result = this.clt_svc.clt_modifyok(cdto, clientImage);  
			
			if(result > 0) {  
				this.pw.write("ok");  //거래처 수정 완료 
			}
			else {
				this.pw.write("fail"); //거래처 수정실패
			}
			
			
			
		} catch (Exception e) {
			System.out.println("error : " + e);
			e.printStackTrace();
		} finally {
			this.pw.close();
		}
		
		return null;
	}
	
}
