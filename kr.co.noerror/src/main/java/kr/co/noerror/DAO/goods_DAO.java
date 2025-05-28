package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.Mapper.bom_mapper;
import kr.co.noerror.Mapper.goods_mapper;

@Repository("goods_DAO")
public class goods_DAO {
	
	@Autowired 
	@Qualifier(value="sqltemplate_oracle")
	private SqlSessionTemplate st;

	@Autowired
	private goods_mapper g_mapper;
	
	//대분류 리스트 
	public List<String> gd_class(Map<String, String> map) {
		List<String> result = this.st.selectList("gd_class",map);
		return result;
	}

	//소분류 리스트
	public List<String> sc_class(Map<String, String> map) {
		List<String> result = this.st.selectList("s_class",map);
		return result;
	}

	//제품코드 중복검사
	public int code_dupl(products_DTO pdto) {
		int result = this.st.selectOne("code_dupl",pdto);
		return result;
	};

	//완제품 등록
	public int pd_insert(products_DTO pdto) {
		int result = this.st.insert("pd_insert",pdto);
		return result;
	}

	//부자재 등록
	public int itm_insert(products_DTO pdto) {
		int result = this.st.insert("itm_insert",pdto);
		return result;
	}


	//제품 상세보기 
	public products_DTO pd_one_detail(Map<String, String> map ) {
		products_DTO goods_one = this.st.selectOne("pd_one_detail", map);
		return goods_one;
	}

	
	//제품 총개수 
	public int pd_all_ea(String type) {
		int result = this.st.selectOne("pd_all_ea",type);
		return result;
	}
	public int gd_all_ea_sch(Map<String, String> map) {
		int result = this.st.selectOne("gd_all_ea_sch",map);
		return result;
	}

	//제품 리스트 보기 
	public List<products_DTO> pd_all_list(String type) {
		List<products_DTO> pd_list = this.st.selectList("pd_all_list",type);
		return pd_list;
	}
	public List<products_DTO> gd_all_list_sch(Map<String, Object> map) {
		List<products_DTO> pd_list = this.st.selectList("gd_all_list_sch",map);
		return pd_list;
	}





	//제품 삭제 
	public int pd_delete(Map<String, Object> p) {
		int result = this.st.delete("pd_delete", p);
		return result;
	}

	public String lclass_ck(String sclass) {
		String result = this.st.selectOne("lclass_ck", sclass);
		return result;
	}



	
}
