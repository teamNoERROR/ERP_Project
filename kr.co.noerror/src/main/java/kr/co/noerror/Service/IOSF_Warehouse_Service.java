package kr.co.noerror.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.IOSF_Warehouse_DAO;
import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.Model.M_File_Rename;
import java.util.Random;

@Service
@Repository("IOSF_Warehouse_Service")
public class IOSF_Warehouse_Service {

    @Resource(name="M_File_Rename")
    M_File_Rename fname;

    @Resource(name="IOSF_Warehouse_DAO")
    IOSF_Warehouse_DAO iosf_dao;
    
    @Resource(name="IOSF_DTO")
    IOSF_DTO iosf_dto;
    
    

    Random random = new Random();

    public int IOSF_warehouse_SaveAndUpdate(IOSF_DTO iosf_dto ,String check_insertOrModify, String wh_type) {
    	
    	int iosf_result = 0;	//성공여부 결과 반환
    	
    	//int save_iosf_result = this.iosf_dao.IOSF_save_warehouse(iosf_dto);
    	try {
    		
            // DTO를 맵에 담아 DAO로 전송
            Map<Object, Object> wh_map = new HashMap<>();
            
            
            //입고 창고 save할 경우엔 입고코드 생성 후 인서트
            if(check_insertOrModify == "save") {
            	// 창고 코드 랜덤 생성
                int randomNumber = 10000 + this.random.nextInt(90000);
                String inbound_code = "IWH-" + randomNumber;
            	iosf_dto.setInbound_code(inbound_code);
            }
            
            wh_map.put("iosf_dto", iosf_dto);
            wh_map.put("wh_type", wh_type);
            // wh_map.put("file_dto", wh_file_vo); // 필요시 주석 해제
            
            if(check_insertOrModify == "save") {               		//창고 등록 	
            	iosf_result = this.iosf_dao.IOSF_save_warehouse(wh_map);
            }
            else if(check_insertOrModify == "update"){             //창고 수정  	
            	iosf_result = this.iosf_dao.IOSF_modify_warehouse(wh_map);
            }

        } catch(Exception e) {
            System.out.println("창고 등록 오류발생 : " + e);
        }
    	
    	return iosf_result;
    }
    
    // 입 출고 부자재 완제춤 각각 창고 게시판
    public Map<Object, Object> IOSF_wh_list(int page, String wh_search, String wh_type) { 
    	
    	
    	Map<Object, Object> wh_map = new HashMap<>();
    	
    	try {
            // 한 페이지당 게시글 수
            final int pageSize = 5;
            int startIndex = (page - 1) * pageSize;
            int totalCount;

            List<IOSF_DTO> wh_list_result;
            
            boolean isSearch = (wh_search != null && !wh_search.trim().isEmpty());
            
            if(isSearch) {	//검색한 경우 (검색된 리스트)
                wh_list_result = this.iosf_dao.IOSF_search_whpaged(wh_search, startIndex, pageSize, wh_type);
                totalCount = this.iosf_dao.IOSF_searchTotal(wh_search, wh_type);
            } else {	//검색 안한경우 (전체 리스트)
                wh_list_result = this.iosf_dao.IOSF_select_wh_list(startIndex, pageSize, wh_type);
                totalCount = this.iosf_dao.IOSF_getTotalCount(wh_type);
                System.out.println("iosf : "+totalCount);
            }
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);
            
            wh_map.put("wh_list", wh_list_result);
            wh_map.put("search_check", isSearch ? "yesdata" : "nodata");
            wh_map.put("wh_check", wh_list_result.isEmpty() ? "nodata" : "yesdata");
            wh_map.put("currentPage", page);
            wh_map.put("totalCount", totalCount);
            wh_map.put("totalPages", totalPages);
            wh_map.put("pageSize", pageSize);
            wh_map.put("wh_search", wh_search);

        } catch(Exception e) {
            e.printStackTrace();
        }
    	
    	return wh_map;
    }
    
    //창고 게시판 게시물 상세 정보
    public List<IOSF_DTO> IOSF_wh_SelectWithInboundCode(String inbound_code, String wh_type){
    	
    	List<IOSF_DTO> wh_detail_result;
    	wh_detail_result = iosf_dao.IOSF_wh_SelectWithInboundCode(inbound_code, wh_type);
    	
    	return wh_detail_result;
    }
    
    //창고 삭제
    public int IOSF_delete_warehouse(String wh_code, String inbound_code ,String wh_type) {
    	
    	int wh_delete_result = iosf_dao.IOSF_delete_warehouses(wh_code, inbound_code, wh_type);
    	
    	return wh_delete_result;
    }
    
 
    
}
