package kr.co.noerror.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.mrp_result_DTO;

@Mapper
public interface purchase_mapper {
	List<mrp_result_DTO> mrp_result_select(String mrp_code);
}
