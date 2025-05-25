package kr.co.noerror.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.bom_DAO;
import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.Mapper.bom_mapper;
import kr.co.noerror.Model.M_file;
import kr.co.noerror.Model.M_random;

@Service
public class bom_serviceImpl implements bom_service{
	@Autowired
	bom_mapper b_mapper;
	
	@Resource(name="bom_DAO")
	bom_DAO b_dao;
	
	@Resource(name="M_random")  //랜덤숫자생성 모델 
	M_random m_rno;
	
	@Resource(name="M_file")   //파일첨부관련모델 
	M_file m_file;
	
	@Resource(name="file_DTO")  //파일첨부DTO
	file_DTO f_dto;
	
	List<String> list = null; 
	Map<String, String> map = null;
	
	@Override
	public int bom_check(String pd_code) {
		int bom_ck = this.b_dao.bom_check(pd_code);
		return bom_ck;
	}

	@Override
	public bom_DTO bom_detail(String pd_code) {
		bom_DTO bom_detail = this.b_dao.bom_detail(pd_code);
		return bom_detail;
	}

	@Override
	public int bom_insert(List<bom_DTO> insert_item) {
		int result = this.b_dao.bom_insert(insert_item);
		return result;
	}

}
