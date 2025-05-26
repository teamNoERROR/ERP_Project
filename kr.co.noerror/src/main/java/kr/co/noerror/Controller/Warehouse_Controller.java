package kr.co.noerror.Controller;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    //창고 저장, 수정 구분하기 위한 변수
    String check_insertOrModify;

    
    Warehouse_Controller(Application application) {
        this.application = application;
    }	

    
    //파일명을 개발자가 원하는 형태로 변경
	@GetMapping("/warehouse_insert.do")
	public String insert_warehouse(Model m, WareHouse_DTO wareInsertDTO) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
		m.addAttribute("wareInsertDTO", wareInsertDTO);

		return "/warehouse/warehouse_insert.html";
	}
	
	//창고 저장
	@PostMapping("/warehouse_save.do")
	public String save_warehouse(
			WareHouse_DTO wh_dto,
			HttpServletResponse res,
			HttpServletRequest req
			) {
			
			this.check_insertOrModify = "save";
			int wh_save_result = ws_service.warehouse_SaveAndUpdate(this.check_insertOrModify, wh_dto, req, res);
	    	
			try {
				res.setContentType("text/html; charset=UTF-8");
				PrintWriter pwa = res.getWriter();
				
				if(wh_save_result > 0) {
					System.out.print("저장 완");
						pwa.print("<script>"
							+ "alert('정상적으로 창고가 저장 되었습니다.');" 
							+ "location.href='/warehouses_list.do';"
							+ "</script>");
				}else {
						pwa.print("<script>"
							+ "alert('창고를 저장하지 못하였습니다.');"
							+ "history.go(-1);" 
							+ "</script>");
			}
				if(pwa != null) {					
					pwa.close();
				}

			} 
			catch (Exception e) {

			}

		return null;
	}
	
	
	@PostMapping("/warehouse_modify.do")
	public String modify_warehouse(
	        Model m,
	        WareHouse_DTO wh_dto,
	        HttpServletResponse res,
	        HttpServletRequest req
	) {

		res.setContentType("text/html; charset=UTF-8"); 
		PrintWriter pwa = null;
		
	    this.check_insertOrModify = "update";
	    
	    int wh_modify = ws_service.warehouse_SaveAndUpdate(this.check_insertOrModify, wh_dto, req, res);
	    
	    System.out.println("con +++++"+wh_modify);
	    

	    try {
	        pwa = res.getWriter();

	        if (wh_modify > 0) {
	            pwa.print("<script>"
	                    + "alert('정상적으로 창고가 수정 되었습니다.');"
	                    + "location.href='/warehouses_list.do';"
	                    + "</script>");
	        } else {
	            pwa.print("<script>"
	                    + "alert('창고를 수정하지 못하였습니다.');"
	                    + "history.go(-1);"
	                    + "</script>");
	        }

	    } catch (Exception e) {
	        e.printStackTrace(); 
	    } finally {
	        if (pwa != null) {
	            pwa.close(); 
	        }
	    }

	    return null;
	}

	//창고 정보 수정 페이지로 이동
	@GetMapping("/warehouse_modify_page.do")
	public String modify_warehouse_page(
			@RequestParam(value = "wh_code", required = true) String wh_code,
			Model m) {
		
		List<WareHouse_DTO>wh_modify_result = ws_service.wh_SelectWithWhCode(wh_code);
		m.addAttribute("wh_modify" , wh_modify_result);
		
		return "/warehouse/warehouse_modify.html";
	}
	
	
	@PostMapping("/warehouse_delete_page.do")
	@ResponseBody
	public String delete_warehouse(@RequestParam("wh_code") String wh_code) {
		// 삭제 로직 수행
		
		String result = "";
		
		int wh_save_result = ws_service.delete_warehouse(wh_code);
		System.out.println("결과 " + wh_save_result);
		
		if(wh_save_result > 0) {
			result = "suceses";
		}
		else {
			result = "fail";			
		}
		
		return result;
	}
	  
	
	//창고 게시판 출력
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
	

	
	//창고 정보 상세 모달 페이지에 결과 값 전달
	@GetMapping("/warehouse_modal.do")
	public String warehouse_detail(
			Model m,
			@RequestParam(value = "wh_code", required = true) String wh_code
			){
	
		
	 	List<WareHouse_DTO>wh_list_details = ws_service.wh_SelectWithWhCode(wh_code);

	 	m.addAttribute("wh_dtails" , wh_list_details);
		
		return "/modals/warehouse_modal.html";
	}
	
	
	
		
    	
  
}



























