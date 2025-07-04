package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

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

	
	//개별 부자재 재고수
	public List<IOSF_DTO> ind_item_all_stock() {
		List<IOSF_DTO> result = this.st.selectList("ind_itm_stock");
		return result;
	}

	//부자재 재고 리스트
	public List<IOSF_DTO> itm_stock_list() {
		List<IOSF_DTO> itm_stock_list = this.st.selectList("itm_stock_list");
		return itm_stock_list;
	}

	
	//창고별 보유중인 제품 각각의 재고수
	public List<IOSF_DTO> pd_stock_bywh_list() {
		List<IOSF_DTO> pd_stock_bywh_list = this.st.selectList("pd_wh_list");
		return pd_stock_bywh_list;
	};
	
	
	// 상품 + 창고별 재고
	public List<IOSF_DTO> stockByWhnPd () {
		List<IOSF_DTO> stockByWhnPd = this.st.selectList("pd_wh_list");
		return stockByWhnPd;
	};
	
	// 상품별 전체 재고
	public Integer stockPdTotal (String pdCode) {
		Integer stockPdTotal = this.st.selectOne("pd_wh_list", pdCode);
		return stockPdTotal;
	}

	public int wh_pd_sp() {
		int wh_pd_sp = this.st.selectOne("wh_pd_sp");
		return wh_pd_sp;
	};

	
	


	

}
