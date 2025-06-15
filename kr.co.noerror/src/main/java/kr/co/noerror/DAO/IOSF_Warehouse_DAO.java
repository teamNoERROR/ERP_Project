package kr.co.noerror.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.Mapper.IOSF_Warehouse_Mapper;

@Repository("IOSF_Warehouse_DAO")
public class IOSF_Warehouse_DAO implements IOSF_Warehouse_Mapper{

	@Resource(name = "sqltemplate_oracle")
	private SqlSessionTemplate iosf_ware_st;
	
	 @Resource(name="IOSF_DTO")
	  IOSF_DTO iosf_dto;
	 
	Map<Object, Object> params;
	
	Random random = new Random();
	String wh_uq;

	int randomNumber;
	
	
	@Override
	public int IOSF_out_update(String outCode, String outStatus) {
		Map<Object, Object> params = new HashMap<>();
		
		
		params.put("out_code", outCode);
		params.put("outStatus", outStatus);
		System.out.println("dat ++ " + outCode + " " + outStatus);
		int out_result = this.iosf_ware_st.insert("IOSF_out_update", params); 

		
		return out_result;
	}
	
	
	@Override
	public int IOSF_out_complete(String outCode) {
		Map<Object, Object> params = new HashMap<>();
		
	
		this.randomNumber = 10000 + this.random.nextInt(90000);
		this.wh_uq = "OUT-" +  this.randomNumber;
		params.put("out_code", outCode);
		params.put("wh_uq", this.wh_uq);
		int out_result = this.iosf_ware_st.insert("IOSF_out_complete", params); 

		
		return out_result;
	}
	
	//창고 이동
	@Override
	public int IOSF_warehouse_move(String wh_code, 
									String wh_type,
									String product_code,
									String pd_qty,
									String emp_code,
									String planCode, 
									String mv_wh_code) {
		
//		if (in_status == null || in_status.trim().isEmpty()) {
//		    in_status = "입고완료";
//		}
		
		/*
        if("mt".equals(wh_type)) {                	
        	this.randomNumber = 10000 + this.random.nextInt(90000);
        	 this.wh_uq = "WMT-" + randomNumber;
        }
        else if("fs".equals(wh_type)) {                	
        	this.randomNumber = 10000 + this.random.nextInt(90000);
        	 this.wh_uq = "WFS-" + randomNumber;
        }
        else if("out".equals(wh_type)) {                	
        	this.randomNumber = 10000 + this.random.nextInt(90000);
        	 this.wh_uq = "WOUT-" + randomNumber;
        }
        */
		Map<Object, Object> params = new HashMap<>();
		params.put("wh_code", wh_code);
		params.put("wh_type", wh_type);	
		params.put("product_code", product_code);	
		params.put("pd_qty", pd_qty);	
		params.put("employee_code", emp_code);
		params.put("plan_code", planCode.substring(0, Math.min(3, planCode.length())));
		params.put("mv_wh_code", mv_wh_code);
//		params.put("wh_uq", this.wh_uq);
		
		int wh_save_result = this.iosf_ware_st.insert("IOSF_warehouse_move", params); 
		
		return wh_save_result;
	}
	
	//이동된 창고 입고처리 
	@Override
	public int IOSF_warehouse_move_in(String wh_code, 
									String wh_type,
									String product_code,
									String pd_qty,
									String emp_code,
									String planCode, 
									String mv_wh_code) {
		
		Map<Object, Object> params = new HashMap<>();
		params.put("wh_type", wh_type);	
		params.put("product_code", product_code);	
		params.put("pd_qty", pd_qty);	
		params.put("employee_code", emp_code);
		params.put("plan_code", planCode.substring(0, Math.min(3, planCode.length())));
		params.put("mv_wh_code", mv_wh_code);
		
		int wh_save_result2 = this.iosf_ware_st.insert("IOSF_warehouse_move_in", params); 
		
		return wh_save_result2;
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
	public List<IOSF_DTO> IOSF_search_whpaged(String wh_search, Integer startIndex, Integer pageSize, String wh_type, String wh_name) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("m_search", wh_search);
		params.put("startIndex", startIndex);	
		params.put("pageSize", pageSize);	
		params.put("wh_type", wh_type);	
		params.put("wh_name", wh_name);	
		
		List<IOSF_DTO> select_wh_list = this.iosf_ware_st.selectList("IOSF_search_whpaged",params);
		System.out.println("ekdh : " + select_wh_list);
		return select_wh_list;
	}
	
	//총 결과 창 리스트
	@Override
	public int IOSF_searchTotal(String wh_search, String wh_type, String fs_wh_name) {
	
		Map<Object, Object> params = new HashMap<>();
		params.put("m_search", wh_search);
		params.put("wh_type", wh_type);	
		params.put("fs_wh_name", fs_wh_name);	
		
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
	public List<IOSF_DTO> IOSF_select_wh_list(int startIndex, int pageSize, String wh_type) {
	    this.params = new HashMap<>();
	    this.params.put("startIndex", startIndex);
	    this.params.put("pageSize", pageSize);
	    this.params.put("wh_type", wh_type);
	    
	    return this.iosf_ware_st.selectList("IOSF_select_wh_list", params);
	}
	
	//창고 게시물 상세 정보
	@Override
	public List<IOSF_DTO> IOSF_wh_SelectWithCode(String code, String wh_type) {
		this.params = new HashMap<>();
		this.params.put("code", code);
		this.params.put("wh_type", wh_type);
		
		
		List<IOSF_DTO> wh_detail_result = this.iosf_ware_st.selectList("IOSF_wh_SelectWithCode",this.params);
		
		String addr1 = wh_detail_result.get(0).getWh_addr1();
		String addr2 = wh_detail_result.get(0).getWh_addr2();
		String zipcode = wh_detail_result.get(0).getWh_zipcode();
		String address = "(" + zipcode +")" + addr1 + " " + addr2; 
		
		
		System.out.print(address);
		wh_detail_result.get(0).setWh_location(address);
		
		System.out.println("dao " + wh_detail_result.get(0).getMaterial_date());
		
		return wh_detail_result;
	}
	
	
	//창고 게시물 삭제
	@Override
	public int IOSF_delete_warehouses(String code, String wh_type) {
		
		this.params = new HashMap<>();
		this.params.put("code", code);
		this.params.put("wh_type", wh_type);
		int wh_delete_result = this.iosf_ware_st.delete("IOSF_delete_warehouses",this.params);
		return wh_delete_result;
	}

	
	//완제품 출고처리 
	public int out_productList(Map<String, Object> insertData) {
		int out_productList = this.iosf_ware_st.insert("fs_warehouse_out",insertData);
		return out_productList;
	}

	//창고별 리스트 출력용
	public List<String> wh_nm_list(Map<String, String> schMap) {
		System.out.println("schMap : "+ schMap);
		List<String> wh_type_list = this.iosf_ware_st.selectList("wh_nm_list", schMap);
		return wh_type_list;
	}
	
}