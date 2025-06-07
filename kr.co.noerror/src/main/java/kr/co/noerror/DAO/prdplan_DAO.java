package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.noerror.DTO.employee_DTO;
import kr.co.noerror.DTO.ordreq_DTO;
import kr.co.noerror.DTO.ordreq_res_DTO;
import kr.co.noerror.DTO.plan_DTO;
import kr.co.noerror.DTO.prdplan_DTO;
import kr.co.noerror.DTO.prdplan_detail_DTO;
import kr.co.noerror.DTO.prdplan_res_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.DTO.temp_bom_DTO;

@Service
public class prdplan_DAO {
	
	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	//검색 및 페이징 후 pchreq 리스트
	public List<prdplan_res_DTO> prdplan_paged_list(Map<String, Object> mparam){
		List<prdplan_res_DTO> all = this.sql.selectList("prdplan_paged_list", mparam);
		return all;
	}
	
	//검색후의 pchreq 리스트 갯수
	public int prdplan_search_count(search_condition_DTO search_cond) {
		int cnt = this.sql.selectOne("prdplan_search_count", search_cond);
		return cnt;
	}
	
	public List<temp_bom_DTO> bom_items(String bom_code){
		List<temp_bom_DTO> bom_items = this.sql.selectList("bom_items", bom_code);
		return bom_items;
	}
	
	public List<ordreq_res_DTO> orders_modal(){
		List<ordreq_res_DTO> orders = this.sql.selectList("orders_modal");
		return orders;
	}
	
	public List<employee_DTO> emps_modal(){
		List<employee_DTO> emps = this.sql.selectList("emps_modal");
		return emps;		
	}
	
	public 	int plan_code_check(String plan_code) {
		int count = this.sql.selectOne("plan_code_check", plan_code);
		return count;
	}
	
	public int insert_plan(plan_DTO pdto) {
		int result = this.sql.insert("insert_plan", pdto);
		return result;
	}
	
	public int prdplan_insert(prdplan_DTO pdto) {
		int result = this.sql.insert("prdplan_insert", pdto);
		return result;
	}
	
	public int prdplan_detail_insert(prdplan_detail_DTO detaildto) {
		int result = this.sql.insert("prdplan_detail_insert", detaildto);
		return result;
	}
}