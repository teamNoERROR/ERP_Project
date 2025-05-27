package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("products_DTO")
public class products_DTO {
	
	//완제품 테이블(PRODUCTS) 컬럼
	int PIDX;
	String PRODUCT_CODE, PRODUCT_NAME, PRODUCT_TYPE, PRODUCT_CLASS1, PRODUCT_CLASS2;
	String PRODUCT_SPEC, PRODUCT_UNIT, PRODUCT_COST, PRODUCT_PRICE, PD_SAFE_STOCK;
	
	String FILE_NM, FILE_RENM, API_FNM, IMG_SRC;
	String USE_FLAG, EXP_FLAG, MEMO, INSERT_DATE, MODIFY_DATE;
	
	//부자재테이블(ITEMS) 컬럼 
	int IIDX;
	String ITEM_CODE, ITEM_NAME, ITEM_TYPE, ITEM_CLASS1, ITEM_CLASS2;
	String ITEM_SPEC, ITEM_UNIT, ITEM_COST, ITM_SAFE_STOCK, COMPANY_CODE;
	
	
	

}
