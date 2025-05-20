package kr.co.noerror.Controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.Application;
import kr.co.noerror.DAO.Warehouse_Save_DAO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.Warehouse_Fiile_VO;
import kr.co.noerror.Model.M_File_Rename;
import kr.co.noerror.Service.Warehouse_Save_Service;

@Controller
public class Warehouse_Controller {

    private final Application application;
    PrintWriter pw = null;
   
    @Resource(name="Warehouse_Save_DAO")
    Warehouse_Save_DAO ws_dao;
    
    @Resource(name="Warehouse_Save_Service")
    Warehouse_Save_Service ws_service;

	@Resource(name="M_File_rename")
    M_File_Rename fname;
	
	
	@Resource(name="Warehouse_Fiile_VO")
	Warehouse_Fiile_VO wh_file_vo;

    
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
			Map<String, Object> paramMap = new HashMap<>();


			ws_service.warehouse_save(wh_file_vo, wh_dto, req, res);
	    		
	 
		
		return null;
	}
	  
    
	    	
  
}



























