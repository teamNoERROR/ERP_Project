package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.noerror.DTO.client_DTO;
import kr.co.noerror.DTO.ordreq_DTO;
import kr.co.noerror.DTO.ordreq_detail_DTO;
import kr.co.noerror.DTO.ordreq_res_DTO;
import kr.co.noerror.DTO.pchreq_DTO;
import kr.co.noerror.DTO.pchreq_detail_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.DTO.temp_client_DTO;
import kr.co.noerror.DTO.temp_products_DTO;

@Service
public class ordreq_DAO {

	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	public List<ordreq_DTO> order_list(Map<String, Object> mparam){
		List<ordreq_DTO> all = this.sql.selectList("order_list", mparam);
		return all;
	}
	
	public int order_count(Map<String, Object> mparam) {
		int cnt = this.sql.selectOne("order_count", mparam);
		return cnt;
	}
	
	public List<client_DTO> clients_for_order(){
		List<client_DTO> all = this.sql.selectList("clients_for_order");
		return all;
	}
	
	public List<products_DTO> products_list(){
		List<products_DTO> all = this.sql.selectList("products_list");
		return all;
	}
	
	public 	int order_code_check(String order_code) {
		int count = this.sql.selectOne("order_code_check", order_code);
		return count;
	}
	
	public int ordreq_insert(ordreq_DTO odto) {
		int result = this.sql.insert("ordreq_insert", odto);
		return result;
	}
	
	public int ordreq_detail_insert(ordreq_detail_DTO odto) {
		int result = this.sql.insert("ordreq_detail_insert", odto);
		return result;
	}
	
	//검색 및 페이징 후 리스트
	public List<ordreq_res_DTO> paged_list(Map<String, Object> mparam){
		List<ordreq_res_DTO> all = this.sql.selectList("ordreq_paged_list", mparam);
		return all;
	}
	
	//검색후의 리스트 갯수
	public int search_count(search_condition_DTO search_cond) {
		int cnt = this.sql.selectOne("ordreq_search_count", search_cond);
		return cnt;
	}
	
	//주문정보 조회
	public List<ordreq_res_DTO> ordreq_detail(String order_code){
		List<ordreq_res_DTO> details = this.sql.selectList("ordreq_detail", order_code);
		System.out.println(details);
		return details;
	}
	
	//주문정보 수정
	public int ordreq_update(ordreq_DTO odto) {
		int result = this.sql.update("ordreq_update", odto);
		return result;
	}
	
	//주문 상세정보 수정
	public int ordreq_detail_update(ordreq_detail_DTO odto) {
		int result = this.sql.update("ordreq_detail_update", odto);
		return result;		
	}
	
	//주문상태 업데이트
	public int ord_status_update(Map<String, String> mparam) {
		int result = this.sql.update("ord_status_update", mparam);
		return result;
	}
}
