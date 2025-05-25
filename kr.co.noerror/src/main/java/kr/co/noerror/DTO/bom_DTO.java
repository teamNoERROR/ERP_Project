package kr.co.noerror.DTO;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("bom_DTO")
public class bom_DTO {
	
//	Integer BIDX, PARENT_IDX, BOM_LEVEL, SEQ_NO, BOM_QTY;
//	String BOM_CODE, C_PRODUCT_CODE, C_ITEM_CODE, UNIT;
//	String INSERT_DATE, MODIFY_DATE;
//	List<bom_DTO> items;
	
	int bidx, parentIdx, bomLevel, seqNo, bomQty;
	String bomCode, cProductCode, cItemCode, unit;
	String insertDate, modifyDate;
}
