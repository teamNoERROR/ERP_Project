package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.noerror.DTO.plan_DTO;
import kr.co.noerror.DTO.temp_bom_DTO;

@Service
public class mrp_DAO {
	
	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	public List<plan_DTO> plans_period(Map<String, Object> mparam){
		List<plan_DTO> plans = this.sql.selectList("plans_period", mparam);
		return plans;
	}
	
	public List<temp_bom_DTO> boms_for_mrp(String bom_code){
		List<temp_bom_DTO> boms = this.sql.selectList("boms_for_mrp", bom_code);
		return boms;
	}

}
