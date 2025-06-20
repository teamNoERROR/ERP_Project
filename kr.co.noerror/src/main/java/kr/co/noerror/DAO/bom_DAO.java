package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.Mapper.bom_mapper;

@Repository("bom_DAO")
public class bom_DAO {
	
	@Autowired 
	@Qualifier(value="sqltemplate_oracle")
	private SqlSessionTemplate st;
	
	@Autowired
	private bom_mapper b_mapper;

	//완제품에 등록된 BOM조회 
	public int bom_check(String pd_code) {
		int bom_ck = this.st.selectOne("bom_check",pd_code);
		return bom_ck;
	}

	//BO 상세보기 
	public List<bom_DTO> bom_detail(String pd_code) {
		List<bom_DTO> bom_detail = this.st.selectList("bom_detail",pd_code);
		return bom_detail;
	}
	
	//부모bidx 찾기
	public Integer select_pidx(String bom_code) {
		Integer p_idx = this.st.selectOne("select_pidx", bom_code);
		return p_idx;
	}
	
	//BOM 등록 
	public int bom_insert(bom_DTO dto) {
		int bom_insert = this.st.insert("insert_item", dto);
		return bom_insert;
	}
	
	//bom총개수 
	public int bom_all_ea_sch(Map<String, String> map) {
		int result = this.st.selectOne("bom_all_ea_sch",map);
		return result;
	}
	
	
	//bom 리스트 
	public List<bom_DTO> bom_all_list_sch(Map<String, Object> map) {
		List<bom_DTO> pd_list = this.st.selectList("bom_all_list_sch",map);
		return pd_list;
	}

	//bom삭제 
	public int bom_delete(Map<String, Object> p) {
		int result = this.st.delete("bom_delete", p);
		return result;
	}

	//고유번호 중복체크 
	public int code_dupl_bom(String code) {
		int result = this.st.selectOne("bom_cd_ck", code);
		return result;
	}


	
	
	
	
}
