package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.noerror.DTO.plan_DTO;

@Service
public class mrp_DAO {
	
	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	public List<plan_DTO> plans_period(Map<String, Object> mparam){
		List<plan_DTO> plans = this.sql.selectList("plans_period", mparam);
		return plans;
	}

}
