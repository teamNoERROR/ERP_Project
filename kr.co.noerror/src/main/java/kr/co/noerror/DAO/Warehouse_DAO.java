package kr.co.noerror.DAO;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;


import jakarta.annotation.Resource;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.Mapper.Warehouse_Mapper;


@Repository("Warehouse_DAO")
public class Warehouse_DAO implements Warehouse_Mapper{
	
	@Resource(name = "sqltemplate_oracle")
	private SqlSessionTemplate whs_st;
		
	Map<Object, Object> params;
	
	//게시물 저장
	@Override
	public int save_warehouse(Map<Object, Object> wh_map){
		
		int wh_save_result = this.whs_st.insert("save_warehouse", wh_map); 

		return wh_save_result;
	}

	
	// 창고 수정
	@Override
	public int modify_warehouse(Map<Object, Object> wh_map) {
		
		int wh_modify_result = this.whs_st.update("modify_warehouse", wh_map);
		
		return wh_modify_result;
	}
	
	//검색된 결과 창고 리스트
	@Override
	public List<WareHouse_DTO> search_whpaged(String wh_search, Integer startIndex, Integer pageSize) {
		
		Map<Object, Object> params = new HashMap<>();
		params.put("m_search", wh_search);
		params.put("startIndex", startIndex);	
		params.put("pageSize", pageSize);	
		
		List<WareHouse_DTO> select_wh_list = this.whs_st.selectList("search_whpaged",params);
		
		return select_wh_list;
	}
	
	//총 결과 창 리스트
	@Override
	public int searchTotal(String wh_search) {
		
		int totalCount = this.whs_st.selectOne("searchTotal",wh_search);
		return totalCount;
	}
	
	//게시물 총 갯수
	@Override
	public int getTotalCount() {
		return this.whs_st.selectOne("getTotalCount");
	}
	
	//검색된 창고 리스트
	@Override
	public List<WareHouse_DTO> select_wh_list(int startIndex, int pageSize) {
		
		params = new HashMap<>();
		params.put("startIndex", startIndex);	//시작값
		params.put("pageSize", pageSize);		//페이지당 게시물 갯수 -> 현재 10개씩 
		List<WareHouse_DTO> select_wh_list = this.whs_st.selectList("select_wh_list", params);
		
		return select_wh_list;
	}
	
	
	//창고 게시물 상세 정보
	@Override
	public List<WareHouse_DTO> wh_SelectWithWhCode(String wh_code) {
		
		List<WareHouse_DTO> wh_detail_result = this.whs_st.selectList("wh_SelectWithWhCode",wh_code);

		
		return wh_detail_result;
	}
	
	
	//창고 게시물 삭제
	@Override
	public int delete_warehouses(String wh_code) {
		
		System.out.println("dao +++++++++++++++++"+wh_code+"++++++++");
		int wh_delete_result = this.whs_st.delete("delete_warehouses",wh_code);
		return wh_delete_result;
	}
	
}
