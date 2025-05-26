package kr.co.noerror.Service;

import kr.co.noerror.DTO.bom_DTO;

public interface bom_service  {

	int bom_check(String pd_code);

	bom_DTO bom_detail(String pd_code);
	
	

}
