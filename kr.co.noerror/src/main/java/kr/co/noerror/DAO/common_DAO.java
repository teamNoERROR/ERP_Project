package kr.co.noerror.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.employee_DTO;

@Repository("common_DAO")
public class common_DAO  {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	@Qualifier(value="sqltemplate_oracle")
	private SqlSessionTemplate st;
	
	public List<employee_DTO> emp_list() {
		List<employee_DTO> emp_list = this.st.selectList("emp_list");
		return emp_list;
	}

	public List<WareHouse_DTO> warehouse_list(String wh_tp) {
		List<WareHouse_DTO> warehouse_list = this.st.selectList("warehouse_list",wh_tp);
		return warehouse_list;
	}

	public List<IOSF_DTO> out_pd_list(List<IOSF_DTO> outReqList) {
		List<IOSF_DTO> out_pd_list = this.st.selectList("pd_out_list", outReqList);
		return out_pd_list;
	}


	
	
	


	
	

}
