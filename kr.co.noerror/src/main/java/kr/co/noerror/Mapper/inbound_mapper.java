package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.inbound_DTO;
import kr.co.noerror.DTO.products_DTO;

@Mapper
public interface inbound_mapper {

	int code_dupl_inb(String inb_code); //코드중복검사
	
	int insert_inbnd(inbound_DTO dto);  //자재입고등록
	
	int inbound_total(Map<String, Object> map); //입고 총 개수 
	List<inbound_DTO> inbound_all_list(Map<String, Object> map); //입고 총 리스트 
	List<inbound_DTO> inbound_detail(Map<String, String> map); //입고건 상세보기
	
	int inbound_ok(inbound_DTO io_dto); //입고상태 변경 
}
