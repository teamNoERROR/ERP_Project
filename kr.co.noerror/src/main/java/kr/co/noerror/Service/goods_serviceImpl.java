package kr.co.noerror.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import kr.co.noerror.Controller.order_controller;
import kr.co.noerror.DAO.bom_DAO;
import kr.co.noerror.DAO.goods_DAO;
import kr.co.noerror.DTO.del_DTO;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.Mapper.bom_mapper;
import kr.co.noerror.Mapper.goods_mapper;
import kr.co.noerror.Model.M_file;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_random;
import kr.co.noerror.Model.M_unique_code_generator;

@Service
public class goods_serviceImpl implements goods_service {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	goods_mapper g_mapper;

	@Resource(name="goods_DAO")
	goods_DAO g_dao;
	
	@Resource(name="M_random")  //랜덤숫자생성 모델 
	M_random m_rno;
	
	@Resource(name="M_unique_code_generator")
	M_unique_code_generator makeCode;
	
	@Resource(name="M_file")   //파일첨부관련모델 
	M_file m_file;

	@Resource(name="M_paging")  //페이징 모델 
	M_paging m_pg;
	
	@Resource(name="file_DTO")  //파일첨부DTO
	file_DTO f_dto;
	
	@Resource(name="products_DTO")
	products_DTO p_dto;
	
	@Resource(name="del_DTO")
	del_DTO d_dto;
	
	List<String> list = null; 
	Map<String, String> map = null;
	

	
	
	//대분류 리스트
	@Override
	public JSONArray gd_class(String goods_type){
		this.list = new ArrayList<>();
		this.map = new HashMap<>();
	
		if("product".equals(goods_type) || "half".equals(goods_type) || goods_type == null ) {
			this.map.put("key", "pd_lc");
			
		}else if("item".equals(goods_type)) {
			this.map.put("key", "itm_lc");
			
		}
		this.list = this.g_dao.gd_class(this.map);
		JSONArray lc_list = new JSONArray(this.list);
		
		
		return lc_list;
	}
	
	//소분류리스트
	@Override
	public JSONArray sc_class(String goods_type, String pd_class1){
		this.list = new ArrayList<>();
		this.map = new HashMap<>();
		
		if("product".equals(goods_type) || "half".equals(goods_type) || goods_type == null) {
			this.map.put("key2", "pd_sc");
			this.map.put("PRODUCT_CLASS1", pd_class1);
			
		}else if("item".equals(goods_type) ){
			this.map.put("key2", "itm_sc");
			this.map.put("ITEMS_CLASS1", pd_class1);
			
		}
		
		this.list = this.g_dao.sc_class(this.map);
		JSONArray sc_list = new JSONArray(this.list);
		return sc_list;
	}
	
	//완제품 등록
	@Override
	public int pd_insert(products_DTO pdto, MultipartFile productImage) {
		boolean fileattach;
		int result = 0;
		try {
//			
			String pd_code = this.makeCode.generate("", code -> {
			    pdto.setPRODUCT_CODE(code); // 코드만 변경
			    return this.g_dao.code_dupl(pdto) > 0; // dto 전달
			});
			pdto.setPRODUCT_CODE(pd_code);
			
			fileattach = this.m_file.cdn_filesave(this.f_dto, productImage);
			if(fileattach == true) {  //FTP에 파일저장 완료 후 
				//dto에 파일명 장착
				pdto.setPD_FILE_NM(this.f_dto.getFilenm());
				pdto.setPD_FILE_RENM(this.f_dto.getFileRenm());
				pdto.setPD_API_FNM(this.f_dto.getApinm());
				pdto.setPD_IMG_SRC(this.f_dto.getImgPath());
				
				result = this.g_dao.pd_insert(pdto);
				
			}else { //FTP에 파일저장 실패 
				result = 0;
			}
			
		} catch (Exception e) {
			this.log.error(e.toString());
			e.printStackTrace();
		}		
		
		return result;
	}
	
	//부자재 등록
	@Override
	public int itm_insert(products_DTO pdto, MultipartFile itmImage) {
		boolean fileattach;
		int result = 0;
		try {
			String itm_code = this.makeCode.generate("", code -> {
			    pdto.setITEM_CODE(code); // 코드만 변경
			    return this.g_dao.code_dupl(pdto) > 0; 
			});
			pdto.setITEM_CODE(itm_code);

			fileattach = this.m_file.cdn_filesave(this.f_dto, itmImage);
			if(fileattach == true) {  //FTP에 파일저장 완료 후 
				//dto에 파일명 장착
				pdto.setITM_FILE_NM(this.f_dto.getFilenm());
				pdto.setITM_FILE_RENM(this.f_dto.getFileRenm());
				pdto.setITM_API_FNM(this.f_dto.getApinm());
				pdto.setITM_IMG_SRC(this.f_dto.getImgPath());
				
				result = this.g_dao.itm_insert(pdto);
			
			}else {  //FTP에 파일저장 실패 
				result = 0;
			}
			
		} catch (Exception e) {
			this.log.error(e.toString());
			e.printStackTrace();
		}		
		
		return result;
	}
	
	//제품 총개수 (+검색+페이징)
	public int gd_all_ea_sch(String type, String sclass, String keyword) {
		this.map = new HashMap<>();
		this.map.put("type", type);
		this.map.put("sclass", sclass);
		this.map.put("keyword",keyword);
		int goods_total = this.g_dao.gd_all_ea_sch(map);
		
		return goods_total;
	}
		
	//제품 리스트 (+검색+페이징)
	@Override
	public List<products_DTO> gd_all_list_sch(String type, String sclass, String keyword, Integer pageno, int post_ea) {
		int start = (pageno - 1) * post_ea + 1;
		int end = pageno * post_ea; 
		
		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("sclass", sclass);
		map.put("keyword",keyword);
		map.put("start", start);
		map.put("end", end);

		List<products_DTO> goods_list = this.g_dao.gd_all_list_sch(map); 
		
		return goods_list;
	}
	
		
	//제품 상세보기
	@Override
	public products_DTO pd_one_detail(String pd_code, String type) {
		this.map = new HashMap<>();
		if("product".equals(type)) {
			this.map.put("type", type);
			this.map.put("PRODUCT_CODE", pd_code);
			
		}else if("item".equals(type)) {
			this.map.put("type", type);
			this.map.put("ITEM_CODE", pd_code);
		}
		products_DTO goods_one = this.g_dao.pd_one_detail(this.map);
		
		return goods_one;
	}
	
	
	//제품 삭제 
	@Override
	public int pd_delete(del_DTO d_dto) {
		Map<String, Object> p = new HashMap<>();
		if("product".equals(d_dto.getType())) {
			p.put("type", d_dto.getType());
			p.put("PIDX", d_dto.getIdx());
			p.put("PRODUCT_CODE", d_dto.getCode());
			
		}else if("item".equals(d_dto.getType())) {
			p.put("type", d_dto.getType());
			p.put("IIDX", d_dto.getIdx());
			p.put("ITEM_CODE", d_dto.getCode());
		}
		
		int goods_del = this.g_dao.pd_delete(p);
		return goods_del;
	}

	@Override
	public String lclass_ck(String sclass) {
		String lclass_ck = this.g_dao.lclass_ck(sclass);
		return lclass_ck;
	}



	

	
	
	
	
	
}
