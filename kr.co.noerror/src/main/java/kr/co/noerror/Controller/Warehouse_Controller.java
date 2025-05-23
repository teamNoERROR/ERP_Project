package kr.co.noerror.Controller;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.Application;
import kr.co.noerror.DAO.Warehouse_DAO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.Service.Warehouse_Service;

@Controller
public class Warehouse_Controller {

 

    private final Application application;
    PrintWriter pw = null;
 
    Map<Object, Object> map = null;
    
    @Resource(name="Warehouse_DAO")
    Warehouse_DAO ws_dao;
    
    @Resource(name="Warehouse_Service")
    Warehouse_Service ws_service;	
	

    
    Warehouse_Controller(Application application) {
        this.application = application;
    }	
 
    
    //파일명을 개발자가 원하는 형태로 변경
	
	@GetMapping("/warehouse_insert.do")
	public String warehouse_insert(Model m, WareHouse_DTO wareInsertDTO) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
		m.addAttribute("wareInsertDTO", wareInsertDTO);

		return "/warehouse/warehouse_insert.html";
	}
	
	//창고 저장
	@PostMapping("/warehouse_save.do")
	public String warehouse_save(
			WareHouse_DTO wh_dto,
			HttpServletResponse res,
			HttpServletRequest req
			) {
		
			int wh_save_result = ws_service.warehouse_save(wh_dto, req, res);
	    	
			if(wh_save_result > 0) {
				this.pw.println("<script>"
						+ "alert('정상적으로 창고가 저장 되었습니다.');" 
						+ "location.href='/warehouses_list.do;"
						+ "</script>");
			} else {
				this.pw.println("<script>"
						+ "alert('창고를 저장하지 못하였습니다.');"
						+ "history.go(-1);" 
						+ "</script>");
			}


		return null;
	}
	  
	@GetMapping("/warehouses_list.do")
	public String warehouses_list(
			Model m,
	        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
	        @RequestParam(value = "wh_search", required = false, defaultValue = "") String wh_search) { 
			
		
		this.map = new HashMap<>();
		Map<Object, Object>	wh_list_map = this.map;
		
		wh_list_map = ws_service.warehouse_list(page, wh_search);
		
		m.addAttribute("wh_list", wh_list_map.get("wh_list")); // 리스트
	    m.addAttribute("search_check", wh_list_map.get("search_check")); // 검색 여부
	    m.addAttribute("wh_check", wh_list_map.get("wh_check")); // 데이터 존재 여부
	    m.addAttribute("currentPage", wh_list_map.get("currentPage"));
	    m.addAttribute("totalCount", wh_list_map.get("totalCount"));
	    m.addAttribute("totalPages", wh_list_map.get("totalPages"));
	    m.addAttribute("pageSize", wh_list_map.get("pageSize"));
	    m.addAttribute("wh_search", wh_list_map.get("wh_search")); // 검색어 유지

		
		return "/warehouse/warehouses_list.html";
	}
	
	@GetMapping("/warehouse_modal.do")
	public String warehouse_detail(
			Model m,
			@RequestParam(value = "wh_code", required = true) String wh_code
			){
	
		//System.out.println("cont --"+wh_code+"##");
	 	List<WareHouse_DTO>wh_list_details = ws_service.view_wh_detail(wh_code);
	 	//System.out.println("조회 결과 수:   " + wh_list_details.size());
	 	
	 	//System.out.println("cont - -------------" + wh_list_details.get(0).getWh_code());
	 	m.addAttribute("wh_dtails" , wh_list_details);
		
		return "/modals/warehouse_modal.html";
	}
    	
  
}



























