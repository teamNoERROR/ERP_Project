package kr.co.noerror.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.Mapper.IOSF_Warehouse_Mapper;

@Repository("IOSF_Warehouse_DAO")
public class IOSF_Warehouse_DAO implements IOSF_Warehouse_Mapper{

	@Resource(name = "sqltemplate_oracle")
	private SqlSessionTemplate iosf_ware_st;
	
	
	Map<Object, Object> params;
	
	//게시물 저장
	@Override
	public int IOSF_save_warehouse(Map<Object, Object> wh_map){
		
		int wh_save_result = this.iosf_ware_st.insert("save_warehouse", wh_map); 

		return wh_save_result;
	}

	
	// 창고 수정
	@Override
	public int IOSF_modify_warehouse(Map<Object, Object> wh_map) {
		
		int wh_modify_result = this.iosf_ware_st.update("modify_warehouse", wh_map);
		
		return wh_modify_result;
	}
	
	//검색된 결과 창고 리스트
	@Override
	public List<WareHouse_DTO> IOSF_search_whpaged(String wh_search, Integer startIndex, Integer pageSize, String wh_type) {
		
		Map<Object, Object> params = new HashMap<>();
		params.put("m_search", wh_search);
		params.put("startIndex", startIndex);	
		params.put("pageSize", pageSize);	
		
		List<WareHouse_DTO> select_wh_list = this.iosf_ware_st.selectList("search_whpaged",params);
		
		return select_wh_list;
	}
	
	//총 결과 창 리스트
	@Override
	public int IOSF_searchTotal(String wh_search) {
		
		int totalCount = this.iosf_ware_st.selectOne("searchTotal",wh_search);
		return totalCount;
	}
	
	//게시물 총 갯수
	@Override
	public int IOSF_getTotalCount() {
		return this.iosf_ware_st.selectOne("getTotalCount");
	}
	
	//검색된 창고 리스트
	@Override
	public List<WareHouse_DTO> IOSF_select_wh_list(int startIndex, int pageSize, String wh_type) {
		
		params = new HashMap<>();
		params.put("startIndex", startIndex);	//시작값
		params.put("pageSize", pageSize);		//페이지당 게시물 갯수 -> 현재 10개씩 
		List<WareHouse_DTO> select_wh_list = this.iosf_ware_st.selectList("select_wh_list", params);
		
		return select_wh_list;
	}
	
	
	//창고 게시물 상세 정보
	@Override
	public List<WareHouse_DTO> IOSF_wh_SelectWithWhCode(String wh_code) {
		
		List<WareHouse_DTO> wh_detail_result = this.iosf_ware_st.selectList("wh_SelectWithWhCode",wh_code);

		
		return wh_detail_result;
	}
	
	
	//창고 게시물 삭제
	@Override
	public int IOSF_delete_warehouses(String wh_code) {
		
		System.out.println("dao +++++++++++++++++"+wh_code+"++++++++");
		int wh_delete_result = this.iosf_ware_st.delete("delete_warehouses",wh_code);
		return wh_delete_result;
	}
	
}
