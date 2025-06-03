package kr.co.noerror.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.Mapper.IOSF_Warehouse_Mapper;

@Repository("IOSF_Warehouse_DAO")
public class IOSF_Warehouse_DAO implements IOSF_Warehouse_Mapper{

	@Resource(name = "sqltemplate_oracle")
	private SqlSessionTemplate iosf_ware_st;
	
	
	Map<Object, Object> params;
	
	@Override
	public int IOSF_warehouse_move(
			String wh_code, String inbound_code,
			String client_code, String item_code,
			String wh_type) {
		
		System.out.println("+++++" + wh_type);
		
		
		Map<Object, Object> params = new HashMap<>();
		params.put("wh_code", wh_code);
		params.put("inbound_code", inbound_code);	
		params.put("client_code", client_code);	
		params.put("item_code", item_code);	
		params.put("wh_type", wh_type);	
		
		
		
		int wh_save_result = this.iosf_ware_st.insert("IOSF_warehouse_move", params); 
		
		return wh_save_result;
	}
	
	
	//게시물 저장
	@Override
	public int IOSF_save_warehouse(Map<Object, Object> iosf_map){
		
		int wh_save_result = this.iosf_ware_st.insert("IOSF_save_warehouse", iosf_map); 

		return wh_save_result;
	}
	
	// 창고 수정
	@Override
	public int IOSF_modify_warehouse(Map<Object, Object> wh_map) {
		
		int iosf_modify_result = this.iosf_ware_st.update("IOSF_modify_warehouse", wh_map);
		
		return iosf_modify_result;
	}
	
	//검색된 결과 창고 리스트
	@Override
	public List<IOSF_DTO> IOSF_search_whpaged(String wh_search, Integer startIndex, Integer pageSize, String wh_type) {
		
		Map<Object, Object> params = new HashMap<>();
		params.put("m_search", wh_search);
		params.put("startIndex", startIndex);	
		params.put("pageSize", pageSize);	
		params.put("wh_type", wh_type);	
		
		List<IOSF_DTO> select_wh_list = this.iosf_ware_st.selectList("IOSF_search_whpaged",params);
		
		return select_wh_list;
	}
	
	//총 결과 창 리스트
	@Override
	public int IOSF_searchTotal(String wh_search, String wh_type) {
	
		Map<Object, Object> params = new HashMap<>();
		params.put("m_search", wh_search);
		params.put("startIndex", wh_type);	
		
		int totalCount = this.iosf_ware_st.selectOne("IOSF_searchTotal",params);
		return totalCount;
	}
	
	//게시물 총 갯수
	@Override
	public int IOSF_getTotalCount(String wh_type) {
		return this.iosf_ware_st.selectOne("IOSF_getTotalCount",wh_type);
	}
	
	//창고 리스트
	@Override
	public List<IOSF_DTO> IOSF_select_wh_list(int startIndex, int pageSize, int endIndex, String wh_type) {
		
		this.params = new HashMap<>();
		this.params.put("startIndex", startIndex);	//시작값
		this.params.put("pageSize", pageSize);		//페이지당 게시물 갯수 -> 현재 10개씩 
		this.params.put("endIndex", endIndex);		//페이지당 게시물 갯수 -> 현재 10개씩 
		this.params.put("wh_type", wh_type);		//창고 유형 확인
		List<IOSF_DTO> select_wh_list = this.iosf_ware_st.selectList("IOSF_select_wh_list", params);
		
		
		
		return select_wh_list;
	}
	
	
	//창고 게시물 상세 정보
	@Override
	public List<IOSF_DTO> IOSF_wh_SelectWithCode(String code, String wh_type) {
		this.params = new HashMap<>();
		this.params.put("code", code);
		this.params.put("wh_type", wh_type);
		
		
		List<IOSF_DTO> wh_detail_result = this.iosf_ware_st.selectList("IOSF_wh_SelectWithCode",this.params);
		
		System.out.println("dao " + wh_detail_result.get(0).getMaterial_date());
		
		return wh_detail_result;
	}
	
	
	//창고 게시물 삭제
	@Override
	public int IOSF_delete_warehouses(String wh_code, String code, String wh_type) {
		
		this.params = new HashMap<>();
		this.params.put("wh_code", wh_code);
		this.params.put("code", code);
		this.params.put("wh_type", wh_type);
		int wh_delete_result = this.iosf_ware_st.delete("IOSF_delete_warehouses",this.params);
		return wh_delete_result;
	}
	
}
