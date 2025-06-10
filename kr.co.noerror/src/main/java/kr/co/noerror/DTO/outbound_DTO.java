package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("outbound_DTO")
public class outbound_DTO {
	
	int OIDX;
	String OUTBOUND_CODE, ORD_CODE, WH_CODE, OUT_STATUS;
	String OUTBOUND_REQ_DATE, OUTBOUND_DATE, EMPLOYEE_CODE, OUT_MEMO, OUT_INSERT_DATE, OUT_MODIFY_DATE;
	
	//조인용 변수
	int PRODUCT_QTY;
	String PRODUCT_CODE, IND_OUT_STATUS, OUT_PD_MEMO;
	

}
