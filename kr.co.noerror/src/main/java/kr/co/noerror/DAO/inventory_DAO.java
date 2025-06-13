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
	
	
	//입고창고 + 부자재창고 아이템별 재고수
	public int ind_item_stock(String item_code) {
		int result = this.st.selectOne("ind_item_stock",item_code);
		return result;
	}

	//개별 완제품 재고수
	public List<IOSF_DTO> ind_pd_stock() {
		List<IOSF_DTO> result = this.st.selectList("ind_pd_stock");
		return result;
	};

	//완제품 재고 리스트
	public List<IOSF_DTO> pd_stock_list() {
		List<IOSF_DTO> pd_stock_list = this.st.selectList("pd_stock_list");
		return pd_stock_list;
	}


	

}
