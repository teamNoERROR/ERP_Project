package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.noerror.DTO.order_DTO;
import kr.co.noerror.DTO.temp_client_DTO;
import kr.co.noerror.DTO.temp_products_DTO;

@Service
public class order_DAO {

	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	public List<order_DTO> order_list(Map<String, Object> mparam){
		List<order_DTO> all = this.sql.selectList("order_list", mparam);
		return all;
	}
	
	public int order_count(Map<String, Object> mparam) {
		int cnt = this.sql.selectOne("order_count", mparam);
		return cnt;
	}
	
	public List<temp_client_DTO> client_list(){
		List<temp_client_DTO> all = this.sql.selectList("client_list");
		return all;
	}
	
	public List<temp_products_DTO> products_list(){
		List<temp_products_DTO> all = this.sql.selectList("products_list");
		return all;
	}
	
	public int insert_order(order_DTO odto) {
		int result = this.sql.insert("insert_order", odto);
		return result;
	}
}
