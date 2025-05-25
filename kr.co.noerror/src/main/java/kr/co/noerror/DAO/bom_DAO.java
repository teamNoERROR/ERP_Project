package kr.co.noerror.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.bom_DTO;
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
	public bom_DTO bom_detail(String pd_code) {
		bom_DTO bom_detail = this.st.selectOne("bom_detail",pd_code);
		return bom_detail;
	}
	
	//BOM 등록 
	public int bom_insert(List<bom_DTO> insert_item) {
		int bom_insert = this.st.insert("bom_insert", insert_item);
		return bom_insert;
	}
	
	
	
}
