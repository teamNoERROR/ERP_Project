package kr.co.noerror.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.inbound_DTO;

@Repository("inventory_DAO")
public class inventory_DAO {
	
	@Autowired 
	@Qualifier(value="sqltemplate_oracle")
	private SqlSessionTemplate st;
	
	
	public int ind_item_stock(String item_code) {
		int result = this.st.selectOne("ind_item_stock",item_code);
		return result;
	}

	public int ind_pd_stock(String pd_code) {
		int result = this.st.selectOne("ind_pd_stock",pd_code);
		return result;
	};

	public List<IOSF_DTO> pd_stock_list() {
		List<IOSF_DTO> pd_stock_list = this.st.selectList("pd_stock_list");
		return pd_stock_list;
	}


	

}
