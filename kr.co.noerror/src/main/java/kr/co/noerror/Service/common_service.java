package kr.co.noerror.Service;

import java.util.List;

import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.employee_DTO;

public interface common_service  {

	List<employee_DTO> emp_list();
	
	//창고타입별 창고리스트
	List<WareHouse_DTO> warehouse_list(String wh_type);

	String out_pd_list(String out_pd_data);

}
