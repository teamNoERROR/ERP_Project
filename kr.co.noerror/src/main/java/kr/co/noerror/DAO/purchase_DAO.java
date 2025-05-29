package kr.co.noerror.DAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.noerror.DTO.mrp_result_DTO;

@Service
public class purchase_DAO {
	
	@Autowired
	@Qualifier(value = "sqltemplate_oracle")
	private SqlSession sql;
	
	public List<mrp_result_DTO> mrp_result_select(String mrp_code){
		List<mrp_result_DTO> results = this.sql.selectList("mrp_result_select", mrp_code);
		return results;
	}
}
