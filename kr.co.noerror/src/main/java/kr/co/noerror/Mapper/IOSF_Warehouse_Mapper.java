
package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.IOSF_DTO;



@Mapper
public interface IOSF_Warehouse_Mapper {
		
		
		public List<IOSF_DTO> IOSF_select_wh_list(int startIndex, int pageSize, String wh_type);
	
		//창고 저장
		public int IOSF_save_warehouse(Map<Object, Object> iosf_map);
		
		//창고 수정A
		public int IOSF_modify_warehouse(Map<Object, Object> wh_map);
		
		//검색된 게시물 페이지
	    public List<IOSF_DTO> IOSF_search_whpaged(String m_search, Integer startIndex, Integer pageSize, String wh_type); 
		
		//검색 게시물 총 갯수
	    public int IOSF_searchTotal(String m_search, String wh_type);
	    
	    // 전체 게시물 개수 조회
	    public int IOSF_getTotalCount(String wh_type);
	    
	    //창고 게시물 상세 정보
	    public List<IOSF_DTO> IOSF_wh_SelectWithInboundCode(String inbound_code, String wh_type);

	    //창고 게시물 삭제
	    public int IOSF_delete_warehouses(String wh_code, String inbound_code, String wh_type);
}
