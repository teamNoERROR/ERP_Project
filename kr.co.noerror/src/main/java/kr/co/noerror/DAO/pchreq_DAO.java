package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.pchreq_DTO;
import kr.co.noerror.DTO.pchreq_detail_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;

@Service
public class pchreq_DAO {
	
	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	public List<mrp_result_DTO> mrp_result_select(String mrp_code){
		List<mrp_result_DTO> results = this.sql.selectList("mrp_result_select", mrp_code);
		return results;
	}
	
	public 	int pch_code_check(String pch_code) {
		int count = this.sql.selectOne("pch_code_check", pch_code);
		return count;
	}
	
	public int insert_purchase(pchreq_DTO pdto) {
		int result = this.sql.insert("insert_purchase", pdto);
		return result;
	}
	
	public int insert_pch_detail(pchreq_detail_DTO pdto) {
		int result = this.sql.insert("insert_pch_detail", pdto);
		return result;
	}
	
	public List<pchreq_res_DTO> purchase_list(Map<String, Object> mparam){
		List<pchreq_res_DTO> all = this.sql.selectList("purchase_list", mparam);
		return all;
	}
	
	public int purchase_count(Map<String, Object> mparam) {
		int cnt = this.sql.selectOne("purchase_count", mparam);
		return cnt;
	}
	
	public List<pchreq_res_DTO> purchase_detail(String pch_code){
		List<pchreq_res_DTO> details = this.sql.selectList("purchase_detail", pch_code);
		return details;
	}
}
