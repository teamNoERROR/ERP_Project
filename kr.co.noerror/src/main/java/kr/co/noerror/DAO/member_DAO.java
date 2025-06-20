package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.member_DTO;

@Repository("member_DAO")
public class member_DAO {
	
	@Autowired 
	@Qualifier(value="sqltemplate_oracle")
	private SqlSessionTemplate st;
	
	
	public List<member_DTO> member_all_list(Map<String, Object> map) {
		List<member_DTO> member_all_list = this.st.selectList("member_all_list",map);
		return member_all_list;
	}
	
	
//	public member_DTO login_member(Map<String, String> member_info ) {
//		member_DTO member_login = this.st.selectOne("member_login",member_info);
//		return member_login;
//	}
	
	public member_DTO login_member(member_DTO m_dto) {
		member_DTO member_login = this.st.selectOne("member_login",m_dto);
		return member_login;
	}

}
