package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.inout_DTO;
import kr.co.noerror.Mapper.inout_mapper;

@Repository("inout_DAO")
public class inout_DAO {
	@Autowired 
	@Qualifier(value="sqltemplate_oracle")
	private SqlSessionTemplate st;
	
	@Autowired
	private inout_mapper io_mapper;
	
	//고유코드 중복확인 
	public int code_dupl_inb(String inb_code) {
		int result = this.st.selectOne("code_dupl_inb",inb_code);
		return result;
	}

	//입고등록 
	public int inbnd_insert(inout_DTO dto) {
		int inbnd_insert = this.st.insert("insert_inbnd", dto);
		return inbnd_insert;
	}


	//입고 총 개수 
	public int inbound_total(Map<String, String> map) {
		int inbnd_insert = this.st.selectOne("inbound_total", map);
		return inbnd_insert;
	}

	//입고 총 리스트 
	public List<inout_DTO> inbound_all_list(Map<String, Object> map) {
		List<inout_DTO> inbound_all_list = this.st.selectList("inbound_all_list",map);
		return inbound_all_list;
	}

	//입고건 상세보기 
	public List<inout_DTO> inbound_detail(String inbnd_code) {
		List<inout_DTO> inbound_detail = this.st.selectList("inbound_detail",inbnd_code);
		return inbound_detail;
	}

}
