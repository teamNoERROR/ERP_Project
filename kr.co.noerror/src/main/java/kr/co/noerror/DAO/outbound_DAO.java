package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.inbound_DTO;
import kr.co.noerror.DTO.outbound_DTO;

@Repository("outbound_DAO")
public class outbound_DAO {
	
	@Autowired 
	@Qualifier(value="sqltemplate_oracle")
	private SqlSessionTemplate st;

	public List<outbound_DTO> outbound_all_list(Map<String, Object> mapp) {
		List<outbound_DTO> outbound_all_list = this.st.selectList("outbound_all_list",mapp);
		return outbound_all_list;
	}

	//고유코드 중복 확인
	public int code_dupl_out(String out_code) {
		int result = this.st.selectOne("code_dupl_out",out_code);
		return result;
	}
	
	
}
