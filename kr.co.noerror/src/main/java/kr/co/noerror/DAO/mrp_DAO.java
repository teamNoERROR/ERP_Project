package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.plan_DTO;
import kr.co.noerror.DTO.prdplan_res_DTO;
import kr.co.noerror.DTO.temp_bom_DTO;

@Service
public class mrp_DAO {
	
	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	public List<prdplan_res_DTO> plans_period(Map<String, Object> mparam){
		List<prdplan_res_DTO> plans = this.sql.selectList("plans_period", mparam);
		return plans;
	}
	
	public List<bom_DTO> boms_for_mrp(String bom_code){
		List<bom_DTO> boms = this.sql.selectList("boms_for_mrp", bom_code);
		return boms;
	}
	
	public 	int mrp_code_check(String mrp_code) {
		int count = this.sql.selectOne("mrp_code_check", mrp_code);
		return count;
	}
	
	public int insert_mrp_header(String mrp_code) {
		int result = this.sql.insert("insert_mrp_header", mrp_code);
		return result;
	}
	
	public int insert_mrp_detail(mrp_result_DTO mdto) {
		int result = this.sql.insert("insert_mrp_detail", mdto);
		return result;
	}

}
