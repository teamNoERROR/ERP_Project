package kr.co.noerror.Service;

import java.util.List;

import kr.co.noerror.DTO.member_DTO;

public interface member_service {

	List<member_DTO> member_all_list();

	member_DTO login_member(member_DTO m_dto);

}
