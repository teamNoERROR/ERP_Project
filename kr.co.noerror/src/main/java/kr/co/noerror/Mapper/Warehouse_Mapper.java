package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.WareHouse_DTO;



@Mapper
public interface Warehouse_Mapper {

	public List<WareHouse_DTO> select_wh_list(int startIndex, int pageSize);
	
	//창고 저장
	public int save_warehouse(Map<Object, Object> wh_map);
	
	//창고 수정
	public int modify_warehouse(Map<Object, Object> wh_map);
	
	//검색된 게시물 페이지
    public List<WareHouse_DTO> search_whpaged(String m_search, Integer startIndex, Integer pageSize); 
	
	//검색 게시물 총 갯수
    public int searchTotal(String m_search);
    
    // 전체 게시물 개수 조회
    public int getTotalCount();
    
    //창고 게시물 상세 정보
    public List<WareHouse_DTO> wh_SelectWithWhCode(String wh_code);

    //창고 게시물 삭제
    public int delete_warehouses(String wh_code);

    
}

