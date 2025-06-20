package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.member_DTO;

@Mapper
public interface member_mapper {
	List<member_DTO> member_all_list(Map<String, Object> map); //사원 총 리스트 
	public member_DTO login_member(member_DTO m_dto);
}
