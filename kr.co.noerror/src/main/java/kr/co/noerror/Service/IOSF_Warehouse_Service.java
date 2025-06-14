package kr.co.noerror.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.IOSF_Warehouse_DAO;
import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.Model.M_File_Rename;

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
            
            
            String code;
            String code2;
            //입고 창고 save할 경우엔 입고코드 생성 후 인서트
            if(check_insertOrModify == "save") {
            	// 창고 코드 랜덤 생성
                int randomNumber = 10000 + this.random.nextInt(90000);
                code2 = "INB-" + randomNumber;
                if(wh_type.equals("in")) {
                	code = "IWH-" + randomNumber;
                	iosf_dto.setIn_code(code);
                	iosf_dto.setInbound_code(code2);
                }
                else if(wh_type.equals("mt")) {
                	code = "MWH-" + randomNumber;                	
                	iosf_dto.setMaterial_code(code);
                	iosf_dto.setInbound_code(code2);
                }
                else if(wh_type.equals("fs")) {
                	code = "FWH-" + randomNumber;                	
                	iosf_dto.setFinish_code(code);
                	iosf_dto.setInbound_code(code2);
                }
                else if(wh_type.equals("out")) {
                	code = "OWH-" + randomNumber;                	
                	iosf_dto.setOut_code(code);
                	iosf_dto.setInbound_code(code2);
                }
            }
            
            wh_map.put("iosf_dto", iosf_dto);
            wh_map.put("wh_type", wh_type);
            
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
    
    public Map<Object, Object> IOSF_wh_list(int page, String wh_search, String wh_type, int pageSize) { 
        
        Map<Object, Object> wh_map = new HashMap<>();
        
        try {
            int startIndex = (page - 1) * pageSize;
            int totalCount;

            List<IOSF_DTO> wh_list_result;
            
            boolean isSearch = (wh_search != null && !wh_search.trim().isEmpty());
            
            if(isSearch) {    // 검색한 경우 (검색된 리스트)
                wh_list_result = this.iosf_dao.IOSF_search_whpaged(wh_search, startIndex, pageSize, wh_type);
                totalCount = this.iosf_dao.IOSF_searchTotal(wh_search, wh_type);
            } else {    // 검색 안한 경우 (전체 리스트)
                wh_list_result = this.iosf_dao.IOSF_select_wh_list(startIndex, pageSize, wh_type);  // endIndex 제거
                totalCount = this.iosf_dao.IOSF_getTotalCount(wh_type);
            }
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);
            
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
            
            System.out.println("iosf : "+totalCount);
            System.out.println("wh_list_result size: " + wh_list_result.size());         
            
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
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return wh_map;
    }
    
    //창고 게시판 게시물 상세 정보
    public List<IOSF_DTO> IOSF_wh_SelectWithCode(String code, String wh_type){
    	
    	List<IOSF_DTO> wh_detail_result;
    	wh_detail_result = iosf_dao.IOSF_wh_SelectWithCode(code, wh_type);
    	
    	return wh_detail_result;
    }
    
    //창고 삭제
    public int IOSF_delete_warehouse(String code, String wh_type) {
    	
    	int wh_delete_result = iosf_dao.IOSF_delete_warehouses(code,  wh_type);
    	
    	return wh_delete_result;
    }

    
    
    
    
    //완제품 출고 
	public String out_product(String outData) {
		Map<String, Object> insertData = new HashMap<>();
		
		JSONObject jo = new JSONObject(outData);
		insertData.put("employee_code", jo.getString("empcode"));
		int out_product = 0;
		int go_outlist = 0;
		int count = 0;
		
		JSONArray ja = jo.getJSONArray("outList");
		int data_ea = ja.length();
		
		for (int w = 0; w < data_ea; w++) {
		    JSONObject jo2 = ja.getJSONObject(w);
		    
		    String planCode = jo2.getString("outnm").replaceAll(" ", "");
		    
		    insertData.put("wh_type", "fs");
		    insertData.put("wh_code", jo2.getString("outwh"));
		    insertData.put("plan_code", planCode.substring(0, Math.min(3, planCode.length())));
		    insertData.put("product_code", jo2.getString("pdcode"));
		    insertData.put("pd_qty", jo2.getString("outqty"));
		    
		    out_product = this.iosf_dao.out_productList(insertData);
		    
		    if(out_product > 0) {
		    	count++;
		    }
		}
		System.out.println(count);
		if(count == data_ea) {
			return "out_complete";
		}else {
			return ""+count+"";
		}
		
		
	}
    
}