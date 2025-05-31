package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("inout_DTO")
public class inout_DTO {
	
	int IIDX, ITEM_QTY;
	String INBOUND_CODE, PCH_CODE, ITEM_CODE, ITEM_DEL, ITEM_EXP, WH_CODE;
	String IN_STATUS, INBOUND_DATE, EMPLOYEE_CODE, INB_MEMO, INB_INSERT_DATE, INB_MODIFY_DATE;

}
