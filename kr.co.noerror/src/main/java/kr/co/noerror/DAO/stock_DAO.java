package kr.co.noerror.DAO;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("stock_DAO")
public class stock_DAO {
	
	@Autowired 
	@Qualifier(value="sqltemplate_oracle")
	private SqlSessionTemplate st;
	
	
	public int ind_item_stock(String item_code) {
		int result = this.st.selectOne("ind_item_stock",item_code);
		return result;
	};
	

}
