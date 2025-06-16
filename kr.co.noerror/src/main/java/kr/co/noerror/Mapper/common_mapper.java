package kr.co.noerror.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.employee_DTO;

@Mapper
public interface common_mapper {
	
	List<employee_DTO> emp_list();  //관리자 리스트 전체데이터 출력
	List<WareHouse_DTO> warehouse_list(String wh_tp); //창고 리스트 전체 데이터 출력
	List<IOSF_DTO> out_pd_list(List<IOSF_DTO> outReqList);
}
