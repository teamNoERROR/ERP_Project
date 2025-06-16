package kr.co.noerror.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.Warehouse_DAO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.Model.M_File_Rename;
import kr.co.noerror.Model.M_file;

import java.util.Random;

@Service
@Repository("Warehouse_Service")
public class Warehouse_Service {

    @Resource(name="M_File_Rename")
    M_File_Rename fname;


    @Resource(name="Warehouse_DAO")
    Warehouse_DAO ws_dao;
    
    
    @Resource(name="M_file")   //파일첨부관련모델 
	M_file m_file;
    
    @Resource(name="file_DTO")  //파일첨부DTO
	file_DTO f_dto;

    Random random = new Random();

    // 창고 등록 / 수정
    public int warehouse_SaveAndUpdate(
    		String check_insertOrModify,
            WareHouse_DTO wh_dto,
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        int wh_result = 0;

        MultipartFile wh_file = wh_dto.getWh_file();
        if(wh_file != null && wh_file.getSize() > 0) {  //첨부파일 있는 경우 

            try {
            	/*
                // 파일명 변경
                String file_new = this.fname.rename(wh_file.getOriginalFilename());
                // 실제 저장 경로
                String url = req.getServletContext().getRealPath("/noerror_erp_upload/");

                FileCopyUtils.copy(wh_file.getBytes(), new File(url + file_new));

                wh_dto.setWh_file_url("/wh_images/" + file_new);
                wh_dto.setWh_file_new(file_new);
                wh_dto.setWh_file_ori(wh_file.getOriginalFilename());
				*/
            	boolean fileattach;
            	fileattach = this.m_file.cdn_filesave(this.f_dto, wh_file);
				if(fileattach == true) {  //FTP에 파일저장 완료 후 
					//dto에 파일명 장착
					wh_dto.setWh_file_ori(this.f_dto.getFilenm());
					wh_dto.setWh_file_new(this.f_dto.getFileRenm());
					wh_dto.setWh_api_fnm(this.f_dto.getApinm());
					
				}
            	
            	
            	
                // 창고 코드 랜덤 생성
                int randomNumber = 10000 + this.random.nextInt(90000);
                
                //저장일 경우에만 wh_code 생성
                if(check_insertOrModify == "save") {                	
                	String wh_code = "WH-" + randomNumber;
                	wh_dto.setWh_code(wh_code);
                }

                // DTO를 맵에 담아 DAO로 전송
                Map<Object, Object> wh_map = new HashMap<>();
                wh_map.put("wh_dto", wh_dto);
                // wh_map.put("file_dto", wh_file_vo); // 필요시 주석 해제
                
                if(check_insertOrModify == "save") {               		//창고 등록 	
                	wh_result = this.ws_dao.save_warehouse(wh_map);
                }
                else if(check_insertOrModify == "update"){             //창고 수정  	
                	wh_result = this.ws_dao.modify_warehouse(wh_map);
                }

            } catch(Exception e) {
                System.out.println("창고 등록 오류발생 : " + e);
            }

        }

        return wh_result;
    }

    // 창고 게시판 목록 및 검색
    public Map<Object, Object> warehouse_list(int page, String wh_search, int pageSize) {
        
        Map<Object, Object> wh_map = new HashMap<>();

        try {
            // 한 페이지당 게시글 수
            int startIndex = (page - 1) * pageSize;
            int totalCount;

            List<WareHouse_DTO> wh_list_result;

            boolean isSearch = (wh_search != null && !wh_search.trim().isEmpty());

            if (isSearch) {
                // 검색한 경우
                wh_list_result = this.ws_dao.search_whpaged(wh_search, startIndex, pageSize);
                totalCount = this.ws_dao.searchTotal(wh_search);
            } else {
                // 검색 안 한 경우
                wh_list_result = this.ws_dao.select_wh_list(startIndex, pageSize);
                totalCount = this.ws_dao.getTotalCount();
                System.out.println("ware : " + totalCount);
            }

            int totalPages = (int) Math.ceil((double) totalCount / pageSize);

            // 페이지 블록 계산 추가 (3개씩 보여주기)

         // 변경된 코드
         int blockSize = 3; // 한 번에 보여줄 페이지 수 (홀수 권장)
         int halfBlock = blockSize / 2;

         int startPage = page - halfBlock;
         int endPage = page + halfBlock;

         // startPage가 1보다 작으면 1로 맞추고 endPage도 조정
         if (startPage < 1) {
             startPage = 1;
             endPage = Math.min(blockSize, totalPages);
         }

         // endPage가 총 페이지보다 크면 totalPages로 맞추고 startPage도 조정
         if (endPage > totalPages) {
             endPage = totalPages;
             startPage = Math.max(1, endPage - blockSize + 1);
         }
            // 디버깅용 로그
            System.out.println("service " + wh_list_result.get(0).getECODE());
            System.out.println("service " + wh_list_result.get(0).getWh_name());

            // 결과 맵 저장
            wh_map.put("wh_list", wh_list_result);
            wh_map.put("search_check", isSearch ? "yesdata" : "nodata");
            wh_map.put("wh_check", wh_list_result.isEmpty() ? "nodata" : "yesdata");
            wh_map.put("currentPage", page);
            wh_map.put("totalCount", totalCount);
            wh_map.put("totalPages", totalPages);
            wh_map.put("pageSize", pageSize);
            wh_map.put("wh_search", wh_search);

            // 페이징 블록 추가
            wh_map.put("startPage", startPage);
            wh_map.put("endPage", endPage);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return wh_map;
    }

    
 
    
    //창고 게시판 게시물 상세 정보
    public List<WareHouse_DTO> wh_SelectWithWhCode(String wh_code){
    	
    	List<WareHouse_DTO> wh_detail_result;
    	wh_detail_result = ws_dao.wh_SelectWithWhCode(wh_code);
    	
    	return wh_detail_result;
    }
    
    //창고 삭제
    public int delete_warehouse(String wh_code) {
    	
    	int wh_delete_result = ws_dao.delete_warehouses(wh_code);
    	
    	return wh_delete_result;
    }
    
}