package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.noerror.DTO.order_DTO;

@Service
public class order_DAO {

	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	public List<order_DTO> order_list(Map<String, Object> mparam){
		List<order_DTO> all = this.sql.selectList("order_list", mparam);
		return all;
	}
	
	public int order_count() {
		int cnt = this.sql.selectOne("order_count");
		return cnt;
	}
}
