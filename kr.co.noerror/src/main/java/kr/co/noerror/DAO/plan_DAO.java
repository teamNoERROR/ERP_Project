package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.noerror.DTO.order_DTO;
import kr.co.noerror.DTO.plan_DTO;
import kr.co.noerror.DTO.temp_bom_DTO;

@Service
public class plan_DAO {
	
	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	public List<plan_DTO> plan_list(Map<String, Object> mparam){
		List<plan_DTO> all = this.sql.selectList("plan_list", mparam);
		return all;
	}
	
	public int plan_count(Map<String, Object> mparam) {
		int cnt = this.sql.selectOne("plan_count", mparam);
		return cnt;
	}
	
	public List<temp_bom_DTO> bom_items(String bom_code){
		List<temp_bom_DTO> bom_items = this.sql.selectList("bom_items", bom_code);
		return bom_items;
	}
	
	public List<order_DTO> orders_modal(){
		List<order_DTO> orders = this.sql.selectList("orders_modal");
		return orders;
	}
}
