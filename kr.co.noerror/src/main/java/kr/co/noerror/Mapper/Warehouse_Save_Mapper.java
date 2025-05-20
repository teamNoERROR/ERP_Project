package kr.co.noerror.Mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface Warehouse_Save_Mapper {
	
	public int warehouse_save(Map<String, Object> wh_map);
}
