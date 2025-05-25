package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.WareHouse_DTO;



@Mapper
public interface Warehouse_Mapper {

	public List<WareHouse_DTO> select_wh_list(int startIndex, int pageSize);
	
	public int warehouse_save(Map<Object, Object> wh_map);
	//검색된 게시물 페이지
    public List<WareHouse_DTO> search_whpaged(String m_search, Integer startIndex, Integer pageSize); 
	
	//검색 게시물 총 갯수
    public int searchTotal(String m_search);
    
    // 전체 게시물 개수 조회
    public int getTotalCount();
    
    //창고 게시물 상세 정보
    public List<WareHouse_DTO> view_wh_detail(String wh_code);
}
