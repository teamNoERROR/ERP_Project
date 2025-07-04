package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("products_DTO")
public class products_DTO {
	
	//완제품 테이블(PRODUCTS) 컬럼
	Integer PIDX, PD_SAFE_STOCK;
	String PRODUCT_CODE, PRODUCT_NAME, PRODUCT_TYPE, PRODUCT_CLASS1, PRODUCT_CLASS2;
	String PRODUCT_SPEC, PRODUCT_UNIT, PRODUCT_COST, PRODUCT_PRICE;
	
	String PD_FILE_NM, PD_FILE_RENM, PD_API_FNM, PD_IMG_SRC;
	String PD_USE_FLAG, PD_EXP_FLAG, PD_MEMO, PD_INSERT_DATE, PD_MODIFY_DATE;
	
	
	//부자재테이블(ITEMS) 컬럼 
	Integer IIDX, ITM_SAFE_STOCK;
	String ITEM_CODE, ITEM_NAME, ITEM_TYPE, ITEM_CLASS1, ITEM_CLASS2;
	String ITEM_SPEC, ITEM_UNIT, ITEM_COST, COMPANY_CODE;
	String ITM_FILE_NM, ITM_FILE_RENM, ITM_API_FNM;
	String ITM_USE_FLAG, ITM_EXP_FLAG, ITM_MEMO, ITM_INSERT_DATE, ITM_MODIFY_DATE;
	
	//거래처 컬럼
	String COMPANY_NAME, CEO_NAME, BIZ_NUM, BIZ_TYPE, BIZ_ITEM;
	String COM_ZIP, COM_ADDR1, COM_ADDR2, COM_FILE_NM, COM_FILE_RENM,COM_API_FNM;
	String CLIENT_TYPE, COM_MEMO, COM_USE_FLAG;
	String MANAGER_CODE, MANAGER_NAME, MNG_EMAIL, MNG_TEL, MNG_PHONE, MNG_FAX;
	String MNG_PART, MNG_POSITION, MNG_MEMO;
	

}
