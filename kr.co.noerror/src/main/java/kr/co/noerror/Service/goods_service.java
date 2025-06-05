package kr.co.noerror.Service;

import java.util.List;

import org.json.JSONArray;
import org.springframework.web.multipart.MultipartFile;

import kr.co.noerror.DTO.del_DTO;
import kr.co.noerror.DTO.products_DTO;

public interface goods_service {
	JSONArray gd_class(String goods_type);  //대분류 리스트 
	JSONArray sc_class(String goods_type, String pd_class1);  //소분류 리스트
//	List<String> pd_class_list(String pd_class1);	//완제품 대분류 리스트 
	
	int pd_insert(products_DTO pdto, MultipartFile productImage, String url);	//완제품 등록
	int itm_insert(products_DTO pdto, MultipartFile productImage, String url);	//부자재 등록
	
	int gd_all_ea_sch(String type, String sclass, String keyword);	//제품 총개수 (+페이징, 검색)
	List<products_DTO> gd_all_list_sch(String type, String sclass, String keyword, Integer pageno, int post_ea);	//제품 리스트(+페이징, 검색)

	products_DTO pd_one_detail(String pd_code, String type);  //제품 상세보기
	int pd_delete(del_DTO d_dto); 	//제품 삭제 
	String lclass_ck(String sclass);
	
}
