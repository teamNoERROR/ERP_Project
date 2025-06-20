package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.employee_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.ordreq_DTO;
import kr.co.noerror.DTO.ordreq_res_DTO;
import kr.co.noerror.DTO.pchreq_DTO;
import kr.co.noerror.DTO.pchreq_detail_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;
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
	
	public List<bom_DTO> bom_items(String bom_code){
		List<bom_DTO> bom_items = this.sql.selectList("bom_items", bom_code);
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
	
	public List<prdplan_res_DTO> prdplan_detail(String plan_code){
		List<prdplan_res_DTO> details = this.sql.selectList("prdplan_detail", plan_code);
		return details;
	}
	
	public int prdplan_update(prdplan_DTO pdto) {
		int result = this.sql.update("prdplan_update", pdto);
		return result;
	}
	
	public int prdplan_detail_update(prdplan_detail_DTO pdto) {
		int result = this.sql.update("prdplan_detail_update", pdto);
		return result;		
	}
	
	public int plan_status_update(Map<String, String> mparam) {
		int result = this.sql.update("plan_status_update", mparam);
		return result;
	}
	
	
	//생산완료 처리시 부자재 창고 재고 차감 
	//부자재 출고를 위한 mrp정보 확인 
	public List<mrp_result_DTO> select_mrp_result(String plan_code) {
		List<mrp_result_DTO> select_mrp_result = this.sql.selectList("select_mrp_result",plan_code);
		return select_mrp_result;
	}

	//부자재 재고 출고처리를 위한 정보 
	public List<IOSF_DTO> out_itemList2(String itmCode) {
		List<IOSF_DTO> out_itemList2 = this.sql.selectList("outItm_info2",itmCode);
		return out_itemList2;
	}
	
	//부자재 총재고수 
	public Integer out_itemQty(String itmCode) {
		Integer out_itemQty = this.sql.selectOne("outItm_info",itmCode);
		System.out.println("out_itemQty : " + out_itemQty);
		return out_itemQty;
	}

	//부자재 창고에서 출고처리 
	public int out_mtwh_result(Map<String, Object> outParams) {
		int out_mtwh_result = this.sql.insert("mt_warehouse_out",outParams);
		return out_mtwh_result;
	}
	
	public int IOSF_warehouse_move_up(Map<String, Object> outParams) {
		int result = this.sql.update("IOSF_warehouse_move_up", outParams);
		return result;
	}
}