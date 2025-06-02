package kr.co.noerror.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.client_DAO;
import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.client_DTO;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.Mapper.client_mapper;
import kr.co.noerror.Model.M_file;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_random;

@Service
public class client_serviceImpl implements client_service {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	client_mapper clt_mapper;
	
	@Resource(name="client_DAO")
	client_DAO clt_dao;
	
	@Resource(name="M_random")  //랜덤숫자생성 모델 
	M_random m_rno;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	@Resource(name="M_file")   //파일첨부관련모델 
	M_file m_file;
	
	@Resource(name="file_DTO")  //파일첨부DTO
	file_DTO f_dto;
	
	List<String> list = null; 
	Map<String, String> map = null;
	
	//거래처 수 
	@Override
	public int client_total(String type) {
		
		this.map = new HashMap<>();
		this.map.put("type", type);
//		this.map.put("keyword", String.valueOf(keyword).trim());
		
		int client_total = this.clt_dao.client_total(map);

		return client_total;
	}
	
	
	//거래처 리스트 
	@Override
	public List<client_DTO> client_list(String type, Integer pageno, int post_ea) {
		int start = (pageno - 1) * post_ea;
		int count = post_ea; 
		
		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
//		map.put("keyword", String.valueOf(keyword).trim());
		map.put("start", start);
		map.put("count", count);

		List<client_DTO> client_list = this.clt_dao.client_list(map); 
		
		
		return client_list;
	}
	
	
	
}
