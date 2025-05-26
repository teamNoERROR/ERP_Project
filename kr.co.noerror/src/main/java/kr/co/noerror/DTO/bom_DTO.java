package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("bom_DTO")
public class bom_DTO {
	
	int BIDX, PARENT_IDX;
	String BOM_CODE, C_PRODUCT_CODE, C_ITEM_CODE, BOM_LEVEL, SEQ_NO, BOM_QTY, UNIT;
	String INSERT_DATE, MODIFY_DATE;

}
