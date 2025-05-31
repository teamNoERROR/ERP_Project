package kr.co.noerror.DAO;

import java.util.List;

import org.slf4j.Logger;       
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.noerror.Mapper.common_mapper;
import kr.co.noerror.Service.common_service;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.employee_DTO;

@Service
public class common_DAO implements common_service {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private common_mapper cm_mapper;
	
	@Override
	public List<employee_DTO> emp_list() {
		List<employee_DTO> all_data = this.cm_mapper.emp_list();
		return all_data;
	}
	
	@Override
	public List<WareHouse_DTO> warehouse_list(String wh_tp) {
		List<WareHouse_DTO> all_data = this.cm_mapper.warehouse_list(wh_tp);
		
		return all_data;
	}


	
	

}
