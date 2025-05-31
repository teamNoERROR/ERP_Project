package kr.co.noerror.DTO;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("bom_DTO")
public class bom_DTO {
	
	Integer BIDX, PARENT_IDX, BOM_LEVEL, BOM_SEQ_NO, BOM_QTY;
	String BOM_CODE, PRODUCT_CODE, ITEM_CODE, UNIT;
	String BOM_INSERT_DATE, BOM_MODIFY_DATE;
	
	//조인 셀렉트용 변수
	List<String> items;
	int IIDX;
	String ITEM_NAME, ITEM_TYPE, ITEM_CLASS1, ITEM_CLASS2;
	String ITEM_SPEC, ITEM_UNIT, ITEM_COST, COMPANY_CODE;
	String ITM_API_FNM, ITM_IMG_SRC;
	String ITM_USE_FLAG, ITM_EXP_FLAG, ITM_MEMO, ITM_INSERT_DATE;
	
	int PIDX;
	String PRODUCT_NAME, PRODUCT_TYPE, PRODUCT_CLASS1, PRODUCT_CLASS2;
	String PRODUCT_SPEC, PRODUCT_UNIT, PRODUCT_COST, PRODUCT_PRICE;
	String PD_API_FNM, PD_IMG_SRC, PD_USE_FLAG, PD_EXP_FLAG, PD_MEMO, PD_INSERT_DATE;

}
