package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.Mapper.goods_mapper;

@Repository("goods_DAO")
public class goods_DAO implements goods_mapper {
	
//	@Autowired
//	goods_mapper g_mapper;
	
	@Resource(name = "sqltemplate_oracle")
	private SqlSessionTemplate st;

	List<Object> list = null;
	Map<Object, Object> map = null;
	Map<Object, List<Object>> map_list = null;
	
	//완제품 대분류 리스트 
	@Override
	public List<String> pd_class_list(Map<String, String> map) {
		List<String> result = null;
		
		try {
			result = this.st.selectList("pd_class_list",map);
		} catch (Exception e) {
			System.out.println("e :"  +e);
		}
		
		return result;
	}




	//완제품 등록
	@Override
	public int pd_insert(products_DTO pdto) {
		int result = this.st.insert("pd_insert",pdto);
		return result;
	}
	
}
