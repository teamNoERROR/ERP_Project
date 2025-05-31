package kr.co.noerror.Service;

import java.util.List;

import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.del_DTO;
import kr.co.noerror.DTO.products_DTO;

public interface bom_service  {

	int bom_check(String pd_code);  //BOM등록여부 체크 

	List<bom_DTO> bom_detail(String pd_code);  //BOM 상세보기 
	
	int bom_insert( String insert_item);  //BOM 등록 

	
	int bom_all_ea_sch(String sclass, String keyword);  //BOM등록된제품 전체개수 
	List<bom_DTO> bom_all_list_sch(String sclass, String keyword, Integer pageno, int post_ea); //BOM등록된제품 전체 리스트 

	int bom_delete(del_DTO d_dto);

}
