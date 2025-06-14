package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.IOSF_DTO;



@Mapper
public interface IOSF_Warehouse_Mapper {
		
		
		//iosf 게시판
		public List<IOSF_DTO> IOSF_select_wh_list(int startIndex, int pageSize, String wh_type);

		//출고 처리
		public int IOSF_out_complete(String outCode);
		public int IOSF_out_update(String outCode, String outStatus);
		
		
		//창고 저장
		public int IOSF_save_warehouse(Map<Object, Object> iosf_map);
		
		//창고 수정
		public int IOSF_modify_warehouse(Map<Object, Object> wh_map);
		
		//검색된 게시물 페이지
	    public List<IOSF_DTO> IOSF_search_whpaged(String m_search, Integer startIndex, Integer pageSize, String wh_type, String fs_wh_name); 
		
		//검색 게시물 총 갯수
	    public int IOSF_searchTotal(String m_search, String wh_type, String fs_wh_name);
	    
	    // 전체 게시물 개수 조회
	    public int IOSF_getTotalCount(String wh_type);
	    
	    //창고 게시물 상세 정보
	    public List<IOSF_DTO> IOSF_wh_SelectWithCode(String code, String wh_type);

	    //창고 게시물 삭제
	    public int IOSF_delete_warehouses(String in_code, String wh_type);
	    
	    //입고창고 리스트에서 입고 정보 -> 부자재 창고로 이동
	    //창고 이동 
	    public int IOSF_warehouse_move(
	    		String wh_code, String wh_type, String product_code, 
	    		String pd_qty , String emp_code,  String planCode, String mv_wh_code);
	    
	    public int IOSF_warehouse_move_in(
	    		String wh_code, String wh_type, String product_code, 
	    		String pd_qty , String emp_code,  String planCode, String mv_wh_code);
	    
	    //완제품 창고에서 출고 
	    int out_productList(Map<String, Object> insertData);
	    //창고별 리스트 검색용
	    List<String> wh_nm_list(Map<String, String> schMap);
}