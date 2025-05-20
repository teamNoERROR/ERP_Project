package kr.co.noerror.DAO;


import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;


import jakarta.annotation.Resource;

import kr.co.noerror.Mapper.Warehouse_Save_Mapper;


@Repository("Warehouse_Save_DAO")
public class Warehouse_Save_DAO implements Warehouse_Save_Mapper{
	
	@Resource(name = "template")
	private SqlSessionTemplate whs_st;
		
	
	@Override
	public int warehouse_save(Map<String, Object> wh_map){
		
		int wh_save_result = this.whs_st.insert("wherehouse_save", wh_map); 
		return wh_save_result;
	}
	
	
	
}
