package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("products_DTO")
public class products_DTO {
	
	int PIDX;
	String PRODUCT_CODE, PRODUCT_NAME, PRODUCT_TYPE, PRODUCT_CLASS1, PRODUCT_CLASS2;
	String PRODUCT_SPEC, PRODUCT_UNIT, PRODUCT_COST, PRODUCT_PRICE, PD_SAFE_STOCK;
	String FILE_NM, FILE_RENM, API_FNM, IMG_SRC;
	String USE_FLAG, MEMO, INSERT_DATE, MODIFY_DATE;
	
	
	

}
