package kr.co.noerror.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DAO.goods_DAO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.Service.goods_service;

@Controller
public class goods_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	goods_service g_svc;
	
	@Resource(name="goods_DAO")
	goods_DAO g_dao;
	
	
	List<String> list = null; 
	Map<String, String> map = null;

	
	//픔목관리 > 완제품리스트 화면이동 
	@GetMapping("/goods.do")
	public String products_list(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","완제품 리스트");
		return "/goods/products_list.html";
	}
	
	//제품 등록하기 화면이동 
	@GetMapping("/products_insert.do")
	public String goods_insert(Model m, @RequestParam(value="products_class1", required=false) String products_class1) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","품목 등록");
		
		
		System.out.println("pd_class_list : " + products_class1);
		
		this.list = new ArrayList<String>();  //완제품 식품분류 목록 가져오기 
		this.list = this.g_svc.pd_class_list(products_class1);
		m.addAttribute("l_class",this.list);
		System.out.println(this.list);
//		JSONArray sc_list = new JSONArray();
//		for(String a : this.list ) {
//			sc_list.put(a);
//		}
//		m.addAttribute("s_class",sc_list);
		
		return "/goods/products_insert.html";
	}
	
	
	@GetMapping("/products_insertok.do")
	public String products_insertok(@ModelAttribute products_DTO pdto,
			@RequestParam(value = "productImage", required = false) MultipartFile productImage,
				HttpServletResponse res) {
		
		pdto.setPRODUCT_CLASS2("ff");
		System.out.println("pdto : " + pdto);
		
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//품목 상세보기 모달 
	@GetMapping("/goods_detail.do")
	public String goods_detail(Model m) {
		return "/modals/goods_detail_modal.html";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//자재 등록하기 화면이동 
	@GetMapping("/items_insert.do")
	public String items_insert(Model m, @RequestParam(value="goods_class1", required=false) String goods_class1) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","자재 등록");
		
		
//		System.out.println("goods_class1 : " + goods_class1);
		
//		this.list = new ArrayList<String>();  //완제품 식품분류 목록 가져오기 
//		this.list = this.g_svc.pd_class_list(goods_class1);
//		m.addAttribute("l_class",this.list);
//		System.out.println(this.list);
//			JSONArray sc_list = new JSONArray();
//			for(String a : this.list ) {
//				sc_list.put(a);
//			}
//			m.addAttribute("s_class",this.list);
		
		return "/goods/items_insert.html";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
