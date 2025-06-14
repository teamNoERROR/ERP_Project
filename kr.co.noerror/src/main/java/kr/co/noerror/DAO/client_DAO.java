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

	//거래처 수 
	public int client_total(Map<String, String> map) {
		int result = this.st.selectOne("client_total",map);
		return result;
	}

	//거래처 리스트 
	public List<client_DTO> client_list(Map<String, Object> map) {
		List<client_DTO> client_list = this.st.selectList("client_list",map);
		return client_list;
	}

	//특정거래처 조회 
	public client_DTO clt_one_detail(Map<String, String> map) {
		client_DTO client_one  = this.st.selectOne("client_detail",map);
		return client_one;
	}

	//거래처 코드번호 중복검사 
	public int clt_code_dupl(String code) {
		int result = this.st.selectOne("clt_code_dupl",code);
		return result;
	}
	
	//거래처 담당자 코드번호 중복검사 
	public int mng_code_dupl(String code) {
		int result = this.st.selectOne("mng_code_dupl",code);
		return result;
	}
	
	//거래처 등록
	public int clt_insert(client_DTO cdto) {
		int result = this.st.insert("clt_insert",cdto);
		return result;
	}

	//거래처 정보 수정 
	public int clt_modifyok(client_DTO cdto) {
		int result = this.st.update("clt_modify",cdto);
		return result;
	}



}
