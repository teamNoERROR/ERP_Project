package kr.co.noerror.DAO;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import kr.co.noerror.Mapper.goods_mapper;

@Repository("goods_DAO")
public class goods_DAO implements goods_mapper {
	
	@Resource(name = "sqltemplate_oracle")
	private SqlSessionTemplate st;

	List<Object> list = null;
	Map<Object, Object> map = null;
	Map<Object, List<Object>> map_list = null;
	
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

//	@Override
//	public List<String> pd_sc_list(String pd_class1) {
//		List<String> result = this.st.selectList("pd_sc_list",pd_class1);
//		return result;
//	}
	
}
