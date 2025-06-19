package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.inbound_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.outbound_DTO;

@Repository("outbound_DAO")
public class outbound_DAO {
	
	@Autowired 
	@Qualifier(value="sqltemplate_oracle")
	private SqlSessionTemplate st;

	public List<outbound_DTO> outbound_all_list(Map<String, Object> mapp) {
		List<outbound_DTO> outbound_all_list = this.st.selectList("outbound_all_list",mapp);
		return outbound_all_list;
	}

	//고유코드 중복 확인
	public int code_dupl_out(String out_code) {
		int result = this.st.selectOne("code_dupl_out",out_code);
		return result;
	}

	//출고등록 OUTBOUND 테이블에 저장 
	public int outbnd_insert(outbound_DTO out_dto) {
		int result = this.st.insert("insert_outbnd", out_dto);
		return result;
	}
	
	//출고등록 OUTBOUND_DETAIL 테이블에 저장 
	public int outbnd_dtl_insert(outbound_DTO out_dto) {
		int result = this.st.insert("insert_outbnd_dtl", out_dto);
		return result;
	}

	public List<outbound_DTO> outbound_detail(Map<String, String> map) {
		List<outbound_DTO> outbound_detail = this.st.selectList("outbound_detail",map);
		return outbound_detail;
	}

	public List<IOSF_DTO> fswh_all_list(Map<String, Object> mapp) {
		List<IOSF_DTO> fswh_all_list = this.st.selectList("fswh_all_list",mapp);
		return fswh_all_list;
	}

	//제품 재고 출고처리를 위한 정보 
	public List<IOSF_DTO> out_productList(String product_code) {
		List<IOSF_DTO> out_productList = this.st.selectList("outPd_info",product_code);
		return out_productList;
	}

	//완제품 창고에서 출고처리
	public int out_fswh_result(Map<String, Object> outParams) {
		int out_fswh_result = this.st.insert("fs_warehouse_out",outParams);
		return out_fswh_result;
	}

	//부자재 출고를 위한 mrp정보 확인 
	public List<mrp_result_DTO> select_mrp_result(String plan_code) {
		List<mrp_result_DTO> select_mrp_result = this.st.selectList("select_mrp_result",plan_code);
		return select_mrp_result;
	}

	//부자재 재고 출고처리를 위한 정보 
	public List<IOSF_DTO> out_itemList(String itmCode) {
		List<IOSF_DTO> out_itemList = this.st.selectList("outItm_info",itmCode);
		return out_itemList;
	}

	//부자재 창고에서 출고처리 
	public int out_mtwh_result(Map<String, Object> outParams) {
		int out_mtwh_result = this.st.insert("mt_warehouse_out",outParams);
		return out_mtwh_result;
	}
	
	
}
