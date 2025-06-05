package kr.co.noerror.Service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.member_DAO;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.Model.M_file;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_unique_code_generator;

@Service
public class member_serviceImpl implements member_service{
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="member_DAO")
	member_DAO mb_dao;
	
	@Resource(name="M_unique_code_generator")
	M_unique_code_generator makeCode;  //고유코드 생성모델
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	@Resource(name="M_file")   //파일첨부관련모델 
	M_file m_file;
	
	@Resource(name="file_DTO")  //파일첨부DTO
	file_DTO f_dto;
	
	List<String> list = null; 
	Map<String, String> map = null;


}
