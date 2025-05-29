package kr.co.noerror.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;
import kr.co.noerror.DTO.Inbound_DTO;
import kr.co.noerror.Service.IOSF_Warehouse_Service;

@Controller
public class IOSF_Warehouse_Controller {
	
	Map<Object, Object> map = null;
	
	 String wh_type;
	    /*
			 wh_type : in -> 입고 창고 리스트
			 wh_type : out -> 출고 창고 리스트
			 wh_type : sub -> 부자재 창고 리스트
			 wh_type : finish -> 부자재 창고 리스트
	     */
	 @Resource(name="IOSF_Warehouse_Service")
	    IOSF_Warehouse_Service iosf_service;
	 @Resource(name="Inbound_DTO")
	 	Inbound_DTO in_dto;

	@PostMapping("/inbound_insert.do")
	public String inbound_insert() {
		
		return "/warehouse/inbound_list.html";
	}
	
	@GetMapping("/inbound_in_page.do")
	public String inbound_in_page() {
		
		return "/warehouse/inbound_insert.html";
	}
	
	@GetMapping("/inbound_list.do")
	public String inbound_list() {
		
		return "/warehouse/inbound_list.html";
	}
	
	@GetMapping("/outbound_list.do")
	public String outbound_list() {
		
		return "/warehouse/outbound_list.html";
	}
	
	@GetMapping("/warehouse_in_save.do")
	public String warehouse_in_save(Model m) {
		
		m.addAttribute("Inbound_DTO", this.in_dto);
		return "/warehouse/warehouse_in_insert.html";
	}
	
	//입고 창고
		@GetMapping("/warehouses_in_list.do")
		public String warehouse_in_list(Model m,
		        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
		        @RequestParam(value = "wh_search", required = false, defaultValue = "") String wh_search) {
			
			/*
			 * 
			 * 
			this.map = new HashMap<>();
			Map<Object, Object>	wh_list_map = this.map;
		
			wh_list_map = iosf_service.IOSF_wh_list(page, wh_search, this.wh_type);
			
			m.addAttribute("wh_in_list", wh_list_map.get("wh_list")); // 리스트
		    m.addAttribute("search_check", wh_list_map.get("search_check")); // 검색 여부
		    m.addAttribute("wh_check", wh_list_map.get("wh_check")); // 데이터 존재 여부
		    m.addAttribute("currentPage", wh_list_map.get("currentPage"));	//현재 페이지
		    m.addAttribute("totalCount", wh_list_map.get("totalCount"));	//총 게시물 갯수
		    m.addAttribute("totalPages", wh_list_map.get("totalPages"));	//총 페이지 갯수
		    m.addAttribute("pageSize", wh_list_map.get("pageSize"));
		    m.addAttribute("wh_search", wh_list_map.get("wh_search")); // 검색어 유지
			
			 */
			return "/warehouse/warehouses_in_list.html";
		}
}
