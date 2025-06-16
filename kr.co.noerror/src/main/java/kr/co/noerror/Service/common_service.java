package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.employee_DTO;

public interface common_service  {

	List<employee_DTO> emp_list();
	
	List<WareHouse_DTO> warehouse_list(String wh_type);

	String out_pd_list(String out_pd_data);
}
