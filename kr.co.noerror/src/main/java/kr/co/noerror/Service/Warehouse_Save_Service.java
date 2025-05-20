package kr.co.noerror.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.Warehouse_Save_DAO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.Warehouse_Fiile_VO;
import kr.co.noerror.Model.M_File_Rename;

@Service
@Repository("Warehouse_Save_Service")
public class Warehouse_Save_Service {

	@Resource(name="M_File_rename")
    M_File_Rename fname;
	
	@Resource(name="Warehouse_Fiile_VO")
	Warehouse_Fiile_VO wh_file_vo;
	
	@Resource(name="Warehouse_Save_DAO")
    Warehouse_Save_DAO ws_dao;
	
	public int warehouse_save(
			Warehouse_Fiile_VO wh_file_vo,
			WareHouse_DTO wh_dto,
			HttpServletRequest req,
			HttpServletResponse res
			){
		int whsave_result = 0;
		
		String file_new = null;
		MultipartFile wh_file = wh_dto.getWh_file();
		if(wh_file.getSize() > 0) {
			
			try {
				file_new = this.fname.rename(wh_file.getOriginalFilename());
				//웹 디렉토리 생성한 파일명으로 저장하는 코드
				String url = req.getServletContext().getRealPath("/mini_mdw_upload/");
				
				FileCopyUtils.copy(wh_file.getBytes(),new File(url + file_new));
				wh_file_vo.setWh_file_url("/wh_images/" +file_new); //웹디렉토리 경로 및 파일명
				wh_file_vo.setWh_file_new(file_new);		//중복 방지 위해 파일명 변경값
				wh_file_vo.setWh_file_ori(wh_file.getOriginalFilename());	//사용자가 적용한 파일명
				
				// 창고 정보 , 창고 이미지 dto가 따로 있기 때문에 map을 사용해서 두개 다 보냄
				Map<String, Object> wh_map = new HashMap<>();
				wh_map.put("wh_dto", wh_dto);
				wh_map.put("file_dto", wh_file_vo); 
				
				
				whsave_result = this.ws_dao.warehouse_save(wh_map);
				
			}catch(Exception e) {
					System.out.println("추 천 게시물 등록 오류발생" + e);
				}
				
			}
			
			return whsave_result;
	}
	
}
