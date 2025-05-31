package kr.co.noerror.Controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.Inbound_DTO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.Service.IOSF_Warehouse_Service;

@Controller
public class IOSF_Warehouse_Controller {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	
	//창고 저장, 수정 구분하기 위한 변수
    String check_insertOrModify;
	
	Map<Object, Object> map = null;
	PrintWriter pw = null;
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

	 @Resource(name="IOSF_DTO")
	 	IOSF_DTO iosf_dto;

	
	@GetMapping("/warehouse_in_savePage.do")
	public String warehouse_in_savePage(Model m) {
		
		m.addAttribute("IOSF_DTO", this.iosf_dto);
		return "/warehouse/in_warehouse_insert.html";
	}
	
	@PostMapping("/warehouse_in_save.do")
	public String warehouse_in_save(
			Model m, 
			IOSF_DTO iosf_dto,
			HttpServletResponse res,
			HttpServletRequest req			) {
	
		this.check_insertOrModify = "save";	
		this.wh_type = "in";
		
		
		try {
			res.setContentType("text/html; charset=utf-8");
			this.pw = res.getWriter();
			int iosf_save = iosf_service.IOSF_warehouse_SaveAndUpdate(iosf_dto, this.check_insertOrModify, this.wh_type);
			if(iosf_save > 0) {
				this.pw.print("<script>"
						+ "alert('입고 창고가 정상적으로 저장 되었습니다.');"
						+ "location.href='warehouses_in_list.do';"
						+ "</script>");
			}else {
				this.pw.print("<script>"
						+ "alert('입고 창고 저장 중 문제가 발생하였습니다."
						+ "\n 관리자에게 문의하세요');"
						+ "history.go(-1);"
						+ "</script>");
			}
			
		} catch (Exception e) {
			this.pw.print("오류가 발생하였습니다 관리자에게 문의하세요.");
			this.log.info("IOSF 입고 창고 오류 "+e.toString());
		}finally {
			if(this.pw != null) {
				this.pw.close();
			}
		}
		return null;
	}
	
		//입고 창고 리스트
		@GetMapping("/warehouses_in_list.do")
		public String warehouse_in_list(Model m,
		        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
		        @RequestParam(value = "wh_search", required = false, defaultValue = "") String wh_search) {
			
		
			this.map = new HashMap<>();
			Map<Object, Object>	iosf_list_map = this.map;
		
			this.wh_type = "in";
			
			iosf_list_map = iosf_service.IOSF_wh_list(page, wh_search, this.wh_type);
			
			m.addAttribute("in_wh_list", iosf_list_map.get("wh_list")); // 리스트
		    m.addAttribute("search_check", iosf_list_map.get("search_check")); // 검색 여부
		    m.addAttribute("wh_check", iosf_list_map.get("wh_check")); // 데이터 존재 여부
		    m.addAttribute("currentPage", iosf_list_map.get("currentPage"));	//현재 페이지
		    m.addAttribute("totalCount", iosf_list_map.get("totalCount"));	//총 게시물 갯수
		    m.addAttribute("totalPages", iosf_list_map.get("totalPages"));	//총 페이지 갯수
		    m.addAttribute("pageSize", iosf_list_map.get("pageSize"));
		    m.addAttribute("wh_search", iosf_list_map.get("wh_search")); // 검색어 유지
			
			 
			return "/warehouse/in_warehouses_list.html";
		}
		
		
		//창고 정보 상세 모달 페이지에 결과 값 전달
		@GetMapping("/in_warehouse_modal.do")
		public String in_warehouse_detail(
				Model m,
				@RequestParam(value = "inbound_code", required = true) String inbound_code
				){
		
			this.wh_type = "in";
		 	List<IOSF_DTO>wh_list_details = iosf_service.IOSF_wh_SelectWithInboundCode(inbound_code, this.wh_type);

		 	m.addAttribute("wh_details" , wh_list_details);
			
			return "/modals/in_warehouse_modal.html";
		}
		
		@PostMapping("/in_warehouse_modify.do")
		public String IOSF_modify_warehouse(
		        Model m,
		        IOSF_DTO iosf_dto,
		        HttpServletResponse res,
		        HttpServletRequest req
		) {

			res.setContentType("text/html; charset=UTF-8"); 
			PrintWriter pwa = null;
			
			this.log.info("wh_code :: " + iosf_dto.getWh_code());
			this.log.info("inbound_date :: " + iosf_dto.getInbound_date());
			
			this.wh_type = "in";
		    this.check_insertOrModify = "update";
		    
		    int wh_modify = iosf_service.IOSF_warehouse_SaveAndUpdate(iosf_dto, this.check_insertOrModify, this.wh_type);
		  
		    try {
		        pwa = res.getWriter();

		        if (wh_modify > 0) {
		            pwa.print("<script>"
		                    + "alert('정상적으로 입고창고가 수정 되었습니다.');"
		                    + "location.href='/warehouses_in_list.do';"
		                    + "</script>");
		        } else {
		            pwa.print("<script>"
		                    + "alert('입고창고를 수정하지 못하였습니다.');"
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
		@GetMapping("/in_warehouse_modify_page.do")
		public String in_modify_warehouse_page(
				@RequestParam(value = "inbound_code", required = true) String inbound_code,
				Model m) {
			this.wh_type = "in";
			List<IOSF_DTO>in_wh_modify_result = iosf_service.IOSF_wh_SelectWithInboundCode(inbound_code, this.wh_type);
			m.addAttribute("in_wh_modify" , in_wh_modify_result);
			
			return "/warehouse/in_warehouse_modify.html";
		}
		
		
		@PostMapping("/in_warehouse_delete_page.do")
		@ResponseBody
		public String in_delete_warehouse(@RequestParam("inbound_code") String inbound_code) {
			// 삭제 로직 수행
			
			String result = "";
			this.wh_type = "in";
			
			int wh_save_result = iosf_service.IOSF_delete_warehouse(inbound_code, this.wh_type);
			System.out.println("결과 " + wh_save_result);
			
			if(wh_save_result > 0) {
				result = "suceses";
			}
			else {
				result = "fail";			
			}
			
			return result;
		}

}

