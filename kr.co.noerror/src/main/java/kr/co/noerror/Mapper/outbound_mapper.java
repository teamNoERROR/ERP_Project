package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.inbound_DTO;
import kr.co.noerror.DTO.outbound_DTO;

@Mapper
public interface outbound_mapper {
	
	List<outbound_DTO> outbound_all_list(Map<String, Object> map); //출고 총 리스트 
	int code_dupl_out(String out_code); //코드중복검사
	
	List<outbound_DTO> insert_outbnd(outbound_DTO out_dto); //출고등록 OUTBOUND 테이블에 저장 
	List<outbound_DTO> insert_outbnd_dtl(outbound_DTO out_dto);	//출고등록 OUTBOUND_DETAIL 테이블에 저장
	
	List<outbound_DTO> outbound_detail(Map<String, String> map);
	
	List<IOSF_DTO> fswh_all_list(Map<String, Object> mapp);
	
	//제품 재고 출고처리를 위한 정보
	List<IOSF_DTO> out_productList(String product_code);
	
	int out_fswh_result(IOSF_DTO wh_out_dto);
}

