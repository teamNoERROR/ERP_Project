package kr.co.noerror.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DAO.IOSF_Warehouse_DAO;
import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.Service.IOSF_Warehouse_Service;
import kr.co.noerror.Service.common_service;
import kr.co.noerror.Service.inventory_service;

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

    @Resource(name="IOSF_Warehouse_DAO")
       IOSF_Warehouse_DAO iosf_dao;

    @Resource(name="IOSF_DTO")
       IOSF_DTO iosf_dto;
    
    @Autowired
	common_service common_svc;

    @Autowired
	inventory_service inv_svc; 
   
   @GetMapping("/warehouse_in_savePage.do")
   public String warehouse_in_savePage(Model m) {
	   m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
		m.addAttribute("mmenu","입고 창고 관리");
		
      m.addAttribute("IOSF_DTO", this.iosf_dto);
      return "/warehouse/in_warehouse_insert.html";
   }
   
   @GetMapping("/warehouse_mt_savePage.do")
   public String warehouse_mt_savePage(Model m) {
	   m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
		m.addAttribute("mmenu","부자재 창고 관리");
		
      m.addAttribute("IOSF_DTO", this.iosf_dto);
      return "/warehouse/mt_warehouse_insert.html";
   }
   
   
   //완제품창고 수기등록 이동 
   @GetMapping("/warehouse_fs_savePage.do")
   public String warehouse_fs_savePage(Model m) {
	   m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
		m.addAttribute("mmenu","완제품 창고 관리");
		m.addAttribute("mmmenu","완제품 재고 수동등록");
		
//      m.addAttribute("IOSF_DTO", this.iosf_dto);
      return "/warehouse/fs_warehouse_insert2.html";
   }
   
   @GetMapping("/warehouse_out_savePage.do")
   public String warehouse_out_savePage(Model m) {
	   m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
		m.addAttribute("mmenu","출고 창고 관리");
		
      m.addAttribute("IOSF_DTO", this.iosf_dto);
      return "/warehouse/out_warehouse_insert.html";
   }
   
   
   @PostMapping("/out_warehouse_complete.do")
   @ResponseBody
   public Map<String, Object> out_warehouse_complete(@RequestBody Map<String, List<Map<String, Object>>> payload) {
       List<Map<String, Object>> items = payload.get("items");

       String outStatus = "출고완료";
       int successCount = 0;
       int failCount = 0;
       int updated = 0;
       for (Map<String, Object> item : items) {
           String outCode = (String) item.get("out_code");


           try {
               // 1. 출고상태 변경
               updated = iosf_dao.IOSF_out_update(outCode, outStatus); // 상태 변경               				
		
               /*
                
               // 2. 출고 처리
               int completed = iosf_dao.IOSF_out_complete(outCode); // 처리 완료
                */
               
               if (updated > 0 ) {
                   successCount++;
               } else {
                   failCount++;
               }

           } catch (Exception e) {
               e.printStackTrace();
               failCount++;
           }
       }

       Map<String, Object> result = new HashMap<>();
       result.put("success", failCount == 0); // 하나라도 실패하면 false
       result.put("successCount", successCount);
       result.put("failCount", failCount);

       return result;
   }
 
  /*
   //출고 창고 리스트
   @GetMapping("/warehouses_out_list.do")
   public String warehouses_out_list(Model m,
		      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
	          @RequestParam(value = "wh_search", required = false, defaultValue = "") String wh_search,
	          @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
  
	    m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
		m.addAttribute("mmenu","출고 창고 관리");
	   
	      this.map = new HashMap<>();
	      Map<Object, Object>   iosf_list_map = this.map;
	   
	      this.wh_type = "out";
	      
	      iosf_list_map = iosf_service.IOSF_wh_list(page, wh_search, this.wh_type, pageSize);
	      
	       m.addAttribute("out_wh_list", iosf_list_map.get("wh_list")); // 리스트
	       m.addAttribute("search_check", iosf_list_map.get("search_check")); // 검색 여부
	       m.addAttribute("wh_check", iosf_list_map.get("wh_check")); // 데이터 존재 여부
	       m.addAttribute("currentPage", iosf_list_map.get("currentPage"));   //현재 페이지
	       m.addAttribute("totalCount", iosf_list_map.get("totalCount"));   //총 게시물 갯수
	       m.addAttribute("totalPages", iosf_list_map.get("totalPages"));   //총 페이지 갯수
	       m.addAttribute("pageSize", iosf_list_map.get("pageSize"));
	       m.addAttribute("wh_search", iosf_list_map.get("wh_search")); // 검색어 유지
	       m.addAttribute("startPage", iosf_list_map.get("startPage")); // 검색어 유지
		   m.addAttribute("endPage", iosf_list_map.get("endPage")); // 검색어 유지
	   
	   return "/warehouse/out_warehouses_list.html";
   }
   */
   
   
 //완제품 창고 리스트
   @GetMapping({"/warehouses_fs_list.do", "/outbound.do"})
   public String warehouses_fs_list(Model m,
		   @RequestParam(value = "page", required = false, defaultValue = "1") int page,
	          @RequestParam(value = "wh_search", required = false, defaultValue = "") String wh_search,
	          @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			  @RequestParam(value = "name_wh_list", required = false) String fs_wh_list
//			  ,@RequestBody String out_pd_data
			  
		   		) {
	   m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
		m.addAttribute("mmenu","완제품 창고");
		
		//창고별 리스트 출력용
		List<String> schlist = new ArrayList<>();
 		JSONArray fswhList = this.iosf_service.wh_type_list("fs_wh");  //창고명목록
 		for (int i = 0; i < fswhList.length(); i++) {
 			schlist.add(fswhList.getString(i));
 		}
 		
		m.addAttribute("whNameList",schlist); 
		m.addAttribute("fs_wh_name",fs_wh_list); 	
		
		if("".equals(fs_wh_list)) {
			fs_wh_list = null;
		}
		
		//검색했을경우 
		if((fs_wh_list != null && !fs_wh_list.equals("")) || 
			(wh_search != null && !wh_search.trim().equals(""))) {
			m.addAttribute("mmmenu","검색결과");
		}
		
	   	this.map = new HashMap<>();
	    Map<Object, Object>   iosf_list_map = this.map;
	   
	    this.wh_type = "fs";
	    
	    Map<String, Integer> ind_pd_all_stock = this.inv_svc.ind_pd_all_stock();  //개별 완제품 재고수 
	    
	      iosf_list_map = iosf_service.IOSF_wh_list(page, wh_search.trim(), this.wh_type, pageSize, fs_wh_list);
	       m.addAttribute("fs_wh_list", iosf_list_map.get("wh_list")); // 리스트
	       m.addAttribute("search_check", iosf_list_map.get("search_check")); // 검색 여부
	       m.addAttribute("wh_check", iosf_list_map.get("wh_check")); // 데이터 존재 여부
	       m.addAttribute("currentPage", iosf_list_map.get("currentPage"));   //현재 페이지
	       m.addAttribute("totalCount", iosf_list_map.get("totalCount"));   //총 게시물 갯수
	       m.addAttribute("totalPages", iosf_list_map.get("totalPages"));   //총 페이지 갯수
	       m.addAttribute("pageSize", iosf_list_map.get("pageSize"));
	       m.addAttribute("wh_search", iosf_list_map.get("wh_search")); // 검색어 유지
	       m.addAttribute("startPage", iosf_list_map.get("startPage")); // 검색어 유지
		   m.addAttribute("endPage", iosf_list_map.get("endPage")); // 검색어 유지
		   
		   m.addAttribute("ind_pd_all_stock", ind_pd_all_stock); //개별 완제품 재고수
		   
		   System.out.println( iosf_list_map.get("wh_list"));
		   
	   return "/warehouse/fs_warehouses_list.html";
   }
   
   //부자재 창고 리스트
   @GetMapping("/warehouses_mt_list.do")
   public String warehouses_mt_list(Model m,
		   @RequestParam(value = "page", required = false, defaultValue = "1") int page,
	          @RequestParam(value = "wh_search", required = false, defaultValue = "") String wh_search,
	          @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
//	          @RequestParam(value = "mt_wh_name", required = false) String mt_wh_name,
	          @RequestParam(value = "wh_type", required = false) String wh_type,
	          @RequestParam(value = "name_wh_list", required = false) String mt_wh_list) {
  
	   m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","창고 관리");
		m.addAttribute("mmenu","부자재 창고");
		
		//창고별 리스트 출력용
		List<String> schlist = new ArrayList<>();
 		JSONArray mtwhList = this.iosf_service.wh_type_list("mt_wh");  
 		for (int i = 0; i < mtwhList.length(); i++) {
 			schlist.add(mtwhList.getString(i));
 		}
 		m.addAttribute("whNameList",schlist); 
		m.addAttribute("mt_wh_name",mt_wh_list); 	
		
		if("".equals(mt_wh_list)) {
			mt_wh_list = null;
		}
		
		//검색했을경우 
		if((mt_wh_list != null && !mt_wh_list.equals("")) || 
			(wh_search != null && !wh_search.trim().equals(""))) {
			m.addAttribute("mmmenu","검색결과");
		}
		
	   	this.map = new HashMap<>();
	      Map<Object, Object>   iosf_list_map = this.map;
	   
	      this.wh_type = "mt";
	      
	      iosf_list_map = iosf_service.IOSF_wh_list(page, wh_search.trim(), this.wh_type, pageSize, mt_wh_list);
	      
	       m.addAttribute("mt_wh_list", iosf_list_map.get("wh_list")); // 리스트
	       m.addAttribute("search_check", iosf_list_map.get("search_check")); // 검색 여부
	       m.addAttribute("wh_check", iosf_list_map.get("wh_check")); // 데이터 존재 여부
	       m.addAttribute("currentPage", iosf_list_map.get("currentPage"));   //현재 페이지
	       m.addAttribute("totalCount", iosf_list_map.get("totalCount"));   //총 게시물 갯수
	       m.addAttribute("totalPages", iosf_list_map.get("totalPages"));   //총 페이지 갯수
	       m.addAttribute("pageSize", iosf_list_map.get("pageSize"));
	       m.addAttribute("wh_search", iosf_list_map.get("wh_search")); // 검색어 유지
	       m.addAttribute("startPage", iosf_list_map.get("startPage")); // 검색어 유지
		   m.addAttribute("endPage", iosf_list_map.get("endPage")); // 검색어 유지
	       
	   return "/warehouse/mt_warehouses_list.html";
   }
    
   
   /*
   @GetMapping("/warehouses_in_list.do")
   public String warehouse_in_list(Model m,
          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
          @RequestParam(value = "wh_search", required = false, defaultValue = "") String wh_search,
          @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
       
       m.addAttribute("lmenu","기준정보관리");
       m.addAttribute("smenu","창고 관리");
       m.addAttribute("mmenu","입고 창고 관리");
       
       this.map = new HashMap<>();
       Map<Object, Object> iosf_list_map = this.map;

       this.wh_type = "in";

       iosf_list_map = iosf_service.IOSF_wh_list(page, wh_search, this.wh_type, pageSize);  // pageSize도 전달

       m.addAttribute("in_wh_list", iosf_list_map.get("wh_list")); // 리스트
       m.addAttribute("search_check", iosf_list_map.get("search_check")); // 검색 여부
       m.addAttribute("wh_check", iosf_list_map.get("wh_check")); // 데이터 존재 여부
       m.addAttribute("currentPage", iosf_list_map.get("currentPage"));   //현재 페이지
       m.addAttribute("totalCount", iosf_list_map.get("totalCount"));   //총 게시물 갯수
       m.addAttribute("totalPages", iosf_list_map.get("totalPages"));   //총 페이지 갯수
       m.addAttribute("pageSize", iosf_list_map.get("pageSize"));
       m.addAttribute("wh_search", iosf_list_map.get("wh_search")); // 검색어 유지
       m.addAttribute("startPage", iosf_list_map.get("startPage")); // 검색어 유지
	   m.addAttribute("endPage", iosf_list_map.get("endPage")); // 검색어 유지

       return "/warehouse/in_warehouses_list.html";
   }
   */
   //창고이동
   @PostMapping("/IOSF_warehouse_move.do")
   @ResponseBody
   public Map<String, Object> IOSF_warehouse_move(@RequestBody List<Map<String, Object>> paramList) {
	   
	   System.out.println("paramList + " + paramList);
	   
       int successCount = 0;
       int successCount2 = 0;
       for (Map<String, Object> param : paramList) {
           String wh_code = (String) param.get("wh_code");
           String wh_type = (String) param.get("wh_type");
           String product_code = (String) param.get("product_code");
           String pd_qty = (String) param.get("pd_qty");
           String emp_code = (String) param.get("emp_code");
           String planCode = (String) param.get("plan_code");
           String mv_wh_code = (String) param.get("mv_wh_code");
           String inbound_code = (String) param.get("inbound_code");
           String ind_pch_cd = (String) param.get("ind_pch_cd");
           String wmt_code = (String) param.get("wmt_code");
           String wfs_code = (String) param.get("wfs_code");
           String inv_lot = (String) param.get("inv_lot");
//           String in_status = (String) param.get("in_status");
//           String item_count = (String) param.get("item_count");
          
           int updatedCount = iosf_dao.IOSF_warehouse_move(wh_code, wh_type, product_code, pd_qty, emp_code,planCode,mv_wh_code, inbound_code, ind_pch_cd,wmt_code,wfs_code,inv_lot);
           successCount += updatedCount;
           System.out.println("successCount + " +successCount);
           System.out.println("updatedCount + " +updatedCount);
           //int updatedCount2 = iosf_dao.IOSF_warehouse_move_in(wh_code, wh_type, product_code, pd_qty, emp_code,planCode,mv_wh_code, inbound_code, ind_pch_cd,wmt_code);
           //successCount2 += updatedCount2;
       }

       
       
       Map<String, Object> result = new HashMap<>();
    	  if (successCount > 0) {
    		  result.put("success", true);
    		  result.put("message", successCount + "건 이동 완료");
    	  } else {
    		  result.put("success", false);
    		  result.put("message", "이동 처리 실패");
    	  }
       

       return result;
   }
  
   
   
   @PostMapping("/warehouse_iosf_save.do")
   public String warehouse_iosf_save(
         Model m, 
         IOSF_DTO iosf_dto,
         HttpServletResponse res,
         HttpServletRequest req         ) {
   
      this.check_insertOrModify = "save";   
      System.out.println(iosf_dto.getIn_status());
      String wh_kind = iosf_dto.getWh_type();
      
      if(wh_kind.equals("입고창고")){
    	  this.wh_type = "in";    	  
      }
      else if(wh_kind.equals("부자재창고")){
    	  this.wh_type = "mt";    	  
      }
      else if(wh_kind.equals("완제품창고")){
    	  this.wh_type = "fs";    	  
      }
      else if(wh_kind.equals("출고창고")){
    	  this.wh_type = "out";    	  
      }
      
      
      System.out.println("++++ "+wh_kind);
      try {
         res.setContentType("text/html; charset=utf-8");
         this.pw = res.getWriter();
         int iosf_save = iosf_service.IOSF_warehouse_SaveAndUpdate(iosf_dto, this.check_insertOrModify, this.wh_type);
         if(iosf_save > 0) {
        	 if(wh_kind.equals("입고창고")) {
        		 System.out.println("함수 작동 중 창고 저장");
            this.pw.print("<script>"
                  + "alert('창고가 정상적으로 저장 되었습니다.');"
                  + "location.href='warehouses_in_list.do';"
                  + "</script>");
        	 }
        	 else if(wh_kind.equals("부자재창고")) {
        		 
                 this.pw.print("<script>"
                       + "alert('창고가 정상적으로 저장 되었습니다.');"
                       + "location.href='warehouses_mt_list.do';"
                       + "</script>");
             	 }
        	 else if(wh_kind.equals("완제품창고")) {
        		 
                 this.pw.print("<script>"
                       + "alert('창고가 정상적으로 저장 되었습니다.');"
                       + "location.href='warehouses_fs_list.do';"
                       + "</script>");
             	 }
        	 else if(wh_kind.equals("출고창고")) {
        		 
                 this.pw.print("<script>"
                       + "alert('창고가 정상적으로 저장 되었습니다.');"
                       + "location.href='warehouses_out_list.do';"
                       + "</script>");
             	 }
         }else {
            this.pw.print("<script>"
                  + "alert('창고 저장 중 문제가 발생하였습니다."
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
   
 //창고 정보 상세 모달 페이지에 결과 값 전달
   @GetMapping("/out_warehouse_modal.do")
   public String out_warehouse_detail(
         Model m,
         @RequestParam(value = "out_code", required = true) String out_code
         ){
   
      this.wh_type = "out";
       List<IOSF_DTO>wh_list_details = iosf_service.IOSF_wh_SelectWithCode(out_code, this.wh_type);

       m.addAttribute("wh_details" , wh_list_details);
      
      return "/modals/out_warehouse_modal.html";
   }
      
 //창고 정보 상세 모달 페이지에 결과 값 전달
   @GetMapping("/fs_warehouse_modal.do")
   public String fs_warehouse_detail(
         Model m,
         @RequestParam(value = "finish_code", required = true) String fs_code
         ){
   
      this.wh_type = "fs";
       List<IOSF_DTO>wh_list_details = iosf_service.IOSF_wh_SelectWithCode(fs_code, this.wh_type);

       m.addAttribute("wh_details" , wh_list_details);
      
      return "/modals/fs_warehouse_modal.html";
   }
   
   //창고 정보 상세 모달 페이지에 결과 값 전달
   @GetMapping("/mt_warehouse_modal.do")
   public String mt_warehouse_detail(
         Model m,
         @RequestParam(value = "material_code", required = true) String material_code
         ){
   
      this.wh_type = "mt";
       List<IOSF_DTO>wh_list_details = iosf_service.IOSF_wh_SelectWithCode(material_code, this.wh_type);

       m.addAttribute("wh_details" , wh_list_details);
      
      return "/modals/mt_warehouse_modal.html";
   }
      
      
      //창고 정보 상세 모달 페이지에 결과 값 전달
      @GetMapping("/in_warehouse_modal.do")
      public String in_warehouse_detail(
            Model m,
            @RequestParam(value = "inbound_code", required = true) String inbound_code
            ){
      
         this.wh_type = "in";
          List<IOSF_DTO>wh_list_details = iosf_service.IOSF_wh_SelectWithCode(inbound_code, this.wh_type);

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
        
    	 m.addAttribute("lmenu","기준정보관리");
  		m.addAttribute("smenu","창고 관리");
  		m.addAttribute("mmenu","입고 창고 관리");
    	  
    	  this.wh_type = "in";
         List<IOSF_DTO>in_wh_modify_result = iosf_service.IOSF_wh_SelectWithCode(inbound_code, this.wh_type);
         m.addAttribute("in_wh_modify" , in_wh_modify_result);
         
         return "/warehouse/in_warehouse_modify.html";
      }
      
      
      @PostMapping("/in_warehouse_delete_page.do")
      @ResponseBody
      public String in_delete_warehouse(
    		  @RequestParam("in_code") String in_code
    		  ) {
         // 삭제 로직 수행
         
         String result = "";
         this.wh_type = "in";
         System.out.println("삭제 in " + in_code);
         
         int wh_save_result = iosf_service.IOSF_delete_warehouse(in_code, this.wh_type);
         System.out.println("결과 " + wh_save_result);
         
         if(wh_save_result > 0) {
            result = "suceses";
         }
         else {
            result = "fail";         
         }
         
         return result;
      }
      
      @PostMapping("/mt_warehouse_delete_page.do")
      @ResponseBody
      public String mt_delete_warehouse(
    		  @RequestParam("material_code") String material_code
    		  ) {
         // 삭제 로직 수행
         
         String result = "";
         this.wh_type = "mt";
         System.out.println("삭제 material_code " + material_code);
         
         int wh_save_result = iosf_service.IOSF_delete_warehouse(material_code, this.wh_type);
         System.out.println("결과 " + wh_save_result);
         
         if(wh_save_result > 0) {
            result = "suceses";
         }
         else {
            result = "fail";         
         }
         
         return result;
      }

      
      /*
      //완제품 출고 
      @PostMapping("/outProduct.do")
      public String out_product(@RequestBody String outData, HttpServletResponse res) throws IOException {
  		System.out.println("outData : " + outData);
    	  
    	  this.pw = res.getWriter();
  		
  		try {
  			
  			String out_product = this.iosf_service.out_product(outData);
  			if(out_product == "out_complete") {
  				this.pw.print("out_success");
  				
  			}else {
  				this.pw.print("out_fail");
  			}
  			
//  			String go_outlist = this.iosf_dao.go_outlist();
  			
  		} catch (Exception e) {
  			this.log.error(e.toString());
  			e.printStackTrace();
  			
  		} finally {
  			this.pw.close();
  		}
  		
  		return null;
  	}
     */ 
      
    //완제품 창고 재고조정 
	@PutMapping("/stock_changeOk.do")
	public String stock_changeOk(@RequestBody String stockChange, HttpServletResponse res) throws IOException  {
		this.pw = res.getWriter();
	
		try {
			String stock_change = this.iosf_service.stock_change(stockChange);
			if(stock_change == "save_complete") {
				this.pw.print("save_complete");
				
			}else {
				this.pw.print("save_fail");
			}
			
		} catch (Exception e) {
			this.log.error(e.toString());
			e.printStackTrace();
			
		} finally {
			this.pw.close();
		}
		
		return null;
	}
	      
	      
	      
}