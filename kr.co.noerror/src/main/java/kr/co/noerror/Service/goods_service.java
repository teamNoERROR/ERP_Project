package kr.co.noerror.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import kr.co.noerror.DAO.goods_DAO;

@Service
@Repository("goods_service")
public class goods_service {
	//여기서 실제 구현 
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	goods_DAO g_dao;
	
	List<String> list = null; 
	Map<String, String> map = null;

	
	public List<String> pd_class_list(String pd_class1){
		this.list = new ArrayList<>();
		this.map = new HashMap<>();
		
		if(pd_class1==null) {
			this.map.put("key", "aa");
			
		}else if(pd_class1!=null) {
			this.map.put("key", pd_class1);
			
		}
		this.list = this.g_dao.pd_class_list(this.map);
		
		return this.list;
	}
	
	
	
	
	
}
