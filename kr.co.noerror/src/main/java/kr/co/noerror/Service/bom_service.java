package kr.co.noerror.Service;

import java.util.List;

import kr.co.noerror.DTO.bom_DTO;

public interface bom_service  {

	int bom_check(String pd_code);

	List<bom_DTO> bom_detail(String pd_code);
	
	int bom_insert( String insert_item);

}
