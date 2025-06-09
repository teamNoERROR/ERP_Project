package kr.co.noerror.DTO;

import lombok.Data;

@Data
public class pchreq_res_DTO {
	
	// 발주 정보 
    private Long pidx;               // 자동 증가 기본키
    private String pch_code;         // 발주고유코드
    private String due_date;         // 납기예정일
    private String pch_status;       // 발주상태
    private String memo;             // 비고
    private String request_date;     // 발주일자
    private String modify_date;      // 수정일자
    
    // 발주담당자 정보
    private String ecode = "-";         // 담당자 사원고드
    private String ename = "-";         // 담당자명
    
    // 결제 정보
    private String pay_method;       // 결제수단
    private Long pay_amount;      // 결제금액   
    
    // 거래처(발주처) 정보
    private String company_code = "-";     // 거래처 고유코드
    private String company_name = "-";     // 거래처 이름
    private String biz_num = "-";
    private String ceo_name = "-";
    private String com_addr1 = "-";
    private String com_addr2 = "-";
    private String manager_code = "-";     // 거래처 담당자 고유코드
    private String manager_name = "-";     // 거래처 담당자 이름
    private String mng_phone_num = "-";
    
    // 제품 발주 정보
    private String item_code;        // 자재코드
    private String item_type;        // 자재유형 
    private String item_name;        // 자제명 
    private String item_spec;        // 자제규격 
    private String item_unit;        // 자재단위
    private Long item_qty;        // 발주수량
    private Long item_cost;       // 자재단가
    private String item_class1;      // 대분류
    private String item_class2;      // 소분류
    
}
