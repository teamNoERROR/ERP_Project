package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.inbound_DTO;
import kr.co.noerror.Mapper.inbound_mapper;

@Repository("inbound_DAO")
public class inbound_DAO {
	@Autowired 
	@Qualifier(value="sqltemplate_oracle")
	private SqlSessionTemplate st;
	
//	@Autowired
//	private inout_mapper io_mapper;
	
	//고유코드 중복확인 
	public int code_dupl_inb(String inb_code) {
		int result = this.st.selectOne("code_dupl_inb",inb_code);
		return result;
	}

	//입고등록 
	public int inbnd_insert(inbound_DTO dto) {
		int inbnd_insert = this.st.insert("insert_inbnd", dto);
		return inbnd_insert;
	}

	//입고 총 개수 
	public int inbound_total(Map<String, Object> map) {
		int inbnd_insert = this.st.selectOne("inbound_total", map);
		return inbnd_insert;
	}

	//입고 총 리스트 
	public List<inbound_DTO> inbound_all_list(Map<String, Object> map) {
		List<inbound_DTO> inbound_all_list = this.st.selectList("inbound_all_list",map);
		return inbound_all_list;
	}

	//입고건 상세보기 
	public List<inbound_DTO> inbound_detail(Map<String, String> map) {
		List<inbound_DTO> inbound_detail = this.st.selectList("inbound_detail",map);
		return inbound_detail;
	}

	//입고상태 확인
	public int in_status_ck(inbound_DTO io_dto) {
		int  in_status_ck = this.st.selectOne("in_status_ck", io_dto);
		return  in_status_ck;
	}
	
	//입고처리 
	public int inbound_ok(inbound_DTO io_dto) {
		int  inbound_ok = this.st.update("inbound_ok", io_dto);
		return  inbound_ok;
	}

	

}
