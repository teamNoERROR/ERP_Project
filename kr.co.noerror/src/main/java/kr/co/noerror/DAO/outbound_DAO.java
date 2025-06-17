package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.IOSF_DTO;
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

	//출고등록 OUTBOUND 테이블에 저장 
	public int outbnd_insert(outbound_DTO out_dto) {
		int result = this.st.insert("insert_outbnd", out_dto);
		return result;
	}
	
	//출고등록 OUTBOUND_DETAIL 테이블에 저장 
	public int outbnd_dtl_insert(outbound_DTO out_dto) {
		int result = this.st.insert("insert_outbnd_dtl", out_dto);
		return result;
	}

	public List<outbound_DTO> outbound_detail(Map<String, String> map) {
		List<outbound_DTO> outbound_detail = this.st.selectList("outbound_detail",map);
		return outbound_detail;
	}

	public List<IOSF_DTO> fswh_all_list(Map<String, Object> mapp) {
		List<IOSF_DTO> fswh_all_list = this.st.selectList("fswh_all_list",mapp);
		return fswh_all_list;
	}

	
	
}
