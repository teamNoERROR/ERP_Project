package kr.co.noerror.DTO;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("bom_DTO")
public class bom_DTO {
	
	Integer BIDX, PARENT_IDX, BOM_LEVEL, SEQ_NO, BOM_QTY;
	String BOM_CODE, C_PRODUCT_CODE, C_ITEM_CODE, UNIT;
	String INSERT_DATE, MODIFY_DATE;
	
	//조인 셀렉트용 변수
	List<String> items;
	String ITEM_NAME, ITEM_CODE, ITEM_CLASS1, ITEM_CLASS2;
	String ITEM_SPEC, ITEM_TYPE, ITEM_UNIT, ITEM_COST,MEMO;
	
	String PRODUCT_NAME, PRODUCT_TYPE, PRODUCT_CLASS1, PRODUCT_CLASS2;
	String PRODUCT_SPEC, PRODUCT_UNIT, PRODUCT_COST, PRODUCT_PRICE;

}
