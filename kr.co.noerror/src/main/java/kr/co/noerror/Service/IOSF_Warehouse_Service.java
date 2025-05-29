package kr.co.noerror.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import jakarta.annotation.Resource;
import kr.co.noerror.DAO.IOSF_Warehouse_DAO;
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
    

    Random random = new Random();

    
    // 입 출고 부자재 완제춤 각각 창고 게시판
    public Map<Object, Object> IOSF_wh_list(int page, String wh_search, String wh_type) { 
    	
    	
    	Map<Object, Object> wh_map = new HashMap<>();
    	
    	try {
            // 한 페이지당 게시글 수
            final int pageSize = 5;
            int startIndex = (page - 1) * pageSize;
            int totalCount;

            List<WareHouse_DTO> wh_list_result;
            
            boolean isSearch = (wh_search != null && !wh_search.trim().isEmpty());
            
            if(isSearch) {	//검색한 경우 (검색된 리스트)
                wh_list_result = this.iosf_dao.IOSF_search_whpaged(wh_search, startIndex, pageSize, wh_type);
                totalCount = this.iosf_dao.IOSF_searchTotal(wh_search);
            } else {	//검색 안한경우 (전체 리스트)
                wh_list_result = this.iosf_dao.IOSF_select_wh_list(startIndex, pageSize, wh_type);
                totalCount = this.iosf_dao.IOSF_getTotalCount();
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
    	
    	return null;
    }
    
 
    
}
