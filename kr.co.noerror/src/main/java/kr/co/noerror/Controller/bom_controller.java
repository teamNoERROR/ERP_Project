package kr.co.noerror.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DAO.goods_DAO;
import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.Model.M_file;
import kr.co.noerror.Model.M_random;
import kr.co.noerror.Service.bom_service;
import kr.co.noerror.Service.goods_service;

@Controller
public class bom_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	private bom_service b_svc; 
	
	@Autowired
	private goods_service g_svc; 
	
	@Resource(name="goods_DAO")
	goods_DAO g_dao;
	
	@Resource(name="M_random")  //랜덤숫자생성 모델 
	M_random m_rno;
	
	@Resource(name="M_file")   //파일첨부관련모델 
	M_file m_file;
	
	@Resource(name="file_DTO")  //파일첨부DTO
	file_DTO f_dto;
	
	List<String> list = null; 
	Map<String, String> map = null;
	String url = "";
	String msg = "";
	 
	 
	 
	//BOM등록여부 조회 
	@GetMapping("/bom_check.do")
	public String bom_check(HttpServletResponse res, @RequestParam("pd_code")String pd_code ) throws IOException {
		this.pw = res.getWriter();
		
		try {
			int result = this.b_svc.bom_check(pd_code);  //제품 리스트
			if(result > 0) {   //등록된 BOM 있음 
				this.pw.write("yes");
				
			}else {  //등록된 BOM 없음 
				this.pw.write("no");
			}
			
		} catch (Exception e) {
			this.pw.write("error");
		} finally {
			this.pw.close();
		}
		
		return null;
	}
	
	
	
	//BOM 상세보기 화면이동 
	@GetMapping("/bom_detail.do")
	public String bom_detail(Model m, @RequestParam("pd_code") String pd_code) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","완제품 상세보기");
		m.addAttribute("mmmenu","bom 상세보기");
	
		List<bom_DTO> resultlist = this.b_svc.bom_detail(pd_code);
		if(resultlist != null) {
			m.addAttribute("top_pd", resultlist.get(0).getPRODUCT_NAME());
			m.addAttribute("bom_detail", resultlist);
		}
		System.out.println(resultlist);
		return "/goods/bom_detail.html";
	}
	
	
	//BOM등록 화면 이동 
	@GetMapping("/bom_insert.do")
	public String bom_insert(Model m, @RequestParam("pd_code")String pd_code)  {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","완제품 상세보기");
		m.addAttribute("mmmenu","bom 등록하기");
		
		products_DTO result = this.g_svc.pd_one_detail(pd_code,"product");
		m.addAttribute("bom_pd",result);
		
		
		return "/goods/bom_insert.html";
	}
	
	//부자재 리스트 모달 띄우기 
	@GetMapping("/bom_item_list.do")
	public String bom_item_list(Model m)  {
		int goods_total = this.g_svc.gd_all_ea("item"); //제품 총개수
		List<products_DTO> goods_all_list = this.g_svc.gd_all_list("item");  //제품 리스트 
		
		m.addAttribute("no_items", "등록된 제품이 없습니다" );
		m.addAttribute("items_total", goods_total);
		m.addAttribute("items_list", goods_all_list);
		
		
		return "/modals/items_list_modal.html";
	}
	
	
	
	//bom 등록하기 
	@PutMapping("/bom_insertok.do")
	public String bom_insertok(@RequestBody String insert_item, HttpServletResponse res) throws IOException  {
		this.pw = res.getWriter();
		
		int result = this.b_svc.bom_insert(insert_item);
		if(result>0) {
			this.pw.print("ok");  //등록 완료 
		}else {
			this.pw.print("fail");  //등록 실패 
		}
		
		return null;
	}
	
	
	
}
