package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.client_DTO;
import kr.co.noerror.Mapper.client_mapper;

@Repository("client_DAO")
public class client_DAO {
	@Autowired 
	@Qualifier(value="sqltemplate_oracle")
	private SqlSessionTemplate st;
	
	@Autowired
	private client_mapper clt_mapper;

	public int client_total(Map<String, String> map) {
		int result = this.st.selectOne("client_total",map);
		return result;
	}

	public List<client_DTO> client_list(Map<String, Object> map) {
		List<client_DTO> client_list = this.st.selectList("client_list",map);
		return client_list;
	}

	public client_DTO clt_one_detail(Map<String, String> map) {
		client_DTO client_one  = this.st.selectOne("client_detail",map);
		return client_one;
	}

}
