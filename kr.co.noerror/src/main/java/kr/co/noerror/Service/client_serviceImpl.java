package kr.co.noerror.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.client_DAO;
import kr.co.noerror.DTO.client_DTO;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.Mapper.client_mapper;
import kr.co.noerror.Model.M_file;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_unique_code_generator;

@Service
public class client_serviceImpl implements client_service {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	client_mapper clt_mapper;
	
	@Resource(name="client_DAO")
	client_DAO clt_dao;
	
	@Resource(name="M_unique_code_generator")
	M_unique_code_generator makeCode;
	
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

	//거래처 상세보기
	@Override
	public client_DTO clt_one_detail(String code, String cidx) {
		this.map = new HashMap<>();
		this.map.put("COMPANY_CODE", code);
		this.map.put("CIDX", cidx);
		
		client_DTO client_one = this.clt_dao.clt_one_detail(this.map);
		
		return client_one;
	}

	//거래처 등록 
	@Override
	public int clt_insert(client_DTO cdto, MultipartFile clientImage) {
		boolean fileattach;
		int result = 0;
		try {
//			
			//거래처 고유코드 중복검사 
			String clt_code = this.makeCode.generate("CLT-", code -> {
			    return this.clt_dao.clt_code_dupl(code) > 0; 
			});
			cdto.setCOMPANY_CODE(clt_code);
			
			String mng_code = this.makeCode.generate("MNG-", code -> {
			    return this.clt_dao.mng_code_dupl(code) > 0; 
			});
			cdto.setMANAGER_CODE(mng_code);
			
			//첨부파일 있는경우 
			if(clientImage != null) {
				fileattach = this.m_file.cdn_filesave(this.f_dto, clientImage);
				if(fileattach == true) {  //FTP에 파일저장 완료 후 
					//dto에 파일명 장착
					cdto.setCOM_FILE_NM(this.f_dto.getFilenm());
					cdto.setCOM_FILE_RENM(this.f_dto.getFileRenm());
					cdto.setCOM_API_FNM(this.f_dto.getApinm());
					
				}else { //FTP에 파일저장 실패 
					result = 0;
				}
			}
			
		
			result = this.clt_dao.clt_insert(cdto);
			
		} catch (Exception e) {
			this.log.error(e.toString());
			e.printStackTrace();
		}		
		
		return result;
	}
	
	
	
}
