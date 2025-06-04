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
import kr.co.noerror.DTO.search_condition_DTO;

@Service
public class pchreq_DAO {
	
	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	public List<mrp_result_DTO> mrp_result_select(String mrp_code){
		List<mrp_result_DTO> results = this.sql.selectList("mrp_result_select", mrp_code);
		return results;
	}
	
	//발주코드 중복체크 용도
	public 	int pch_code_check(String pch_code) {
		int count = this.sql.selectOne("pch_code_check", pch_code);
		return count;
	}
	
	public int pchreq_insert(pchreq_DTO pdto) {
		int result = this.sql.insert("pchreq_insert", pdto);
		return result;
	}
	
	public int pchreq_detail_insert(pchreq_detail_DTO pdto) {
		int result = this.sql.insert("pchreq_detail_insert", pdto);
		return result;
	}
	
	//검색 및 페이징 후 pchreq 리스트
	public List<pchreq_res_DTO> paged_list(Map<String, Object> mparam){
		List<pchreq_res_DTO> all = this.sql.selectList("pchreq_paged_list", mparam);
		return all;
	}
	
	//검색후의 pchreq 리스트 갯수
	public int search_count(search_condition_DTO search_cond) {
		int cnt = this.sql.selectOne("pchreq_search_count", search_cond);
		return cnt;
	}
	
	public List<pchreq_res_DTO> pchreq_detail(String pch_code){
		List<pchreq_res_DTO> details = this.sql.selectList("pchreq_detail", pch_code);
		System.out.println(details);
		return details;
	}
	
	//발주정보 수정
	public int pchreq_update(pchreq_DTO pdto) {
		int result = this.sql.update("pchreq_update", pdto);
		return result;
	}
	
	//발주 상세정보 수정
	public int pchreq_detail_update(pchreq_detail_DTO pdto) {
		int result = this.sql.update("pchreq_detail_update", pdto);
		return result;		
	}
	
	//발주상태 업데이트
	public int pch_status_update(Map<String, String> mparam) {
		int result = this.sql.update("pch_status_update", mparam);
		return result;
	}
}
