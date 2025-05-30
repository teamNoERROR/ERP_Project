package kr.co.noerror.DTO;

import lombok.Data;

@Data
public class purchase_DTO {
    private Long pidx;              // 자동 증가 기본키
    private String pch_code;         // 발주고유코드
    private String item_code;        // 제품고유코드
    private Integer item_qty;        // 구매수량
    private String pch_status;       // 발주상태
    private String due_date;           // 납기예정일
    private String memo;            // 비고
    private String request_date;       // 발주일자
    private String modify_date;        // 수정일자
    
    private String company_code;     // 거래처 고유코드
    private String company_name;     // 거래처 이름
    private String manager_code;     // 거래처 담당자 고유코드
    private String manager_name;     // 거래처 담당자 이름
}
