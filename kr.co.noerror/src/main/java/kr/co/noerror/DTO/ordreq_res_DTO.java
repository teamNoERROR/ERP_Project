package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("ordreq_res_DTO")
public class ordreq_res_DTO {
	
	// 주문 정보 
    private Long oidx;               // 자동 증가 기본키
    private String order_code;         // 주문고유코드
    private String due_date;         // 납기예정일
    private String order_status;       // 주문상태
    private String memo;             // 비고
    private String request_date;     // 주문일자
    private String modify_date;      // 수정일자
    
    // 주문담당자 정보
    private String ecode;         // 담당자 사원고드
    private String ename;         // 담당자명
    
    // 결제 정보
    private String pay_method;       // 결제수단
    private Long pay_amount;      // 결제금액   
    
    // 거래처(주문처) 정보
    private String company_code;     // 거래처 고유코드
    private String company_name;     // 거래처 이름
    private String biz_num;
    private String ceo_name;
    private String com_addr1;
    private String com_addr2;
    private String manager_code;     // 거래처 담당자 고유코드
    private String manager_name;     // 거래처 담당자 이름
    private String mng_phone;
    
    // 제품 주문 정보
    private String product_code;        // 제품코드
    private String product_type;        // 제품유형 
    private String product_name;        // 제품명 
    private String product_spec;        // 제품규격 
    private String product_unit;        // 제품단위
    private Long product_qty;        // 주문수량
    private Long product_cost;       // 제품단가
    private String product_class1;      // 대분류
    private String product_class2;      // 소분류
    private Long product_price;		//판매가
    
    //BOM code
    private String bom_code = "-";
    
    //상품별 재고수 
    int ind_pd_all_stock;
    
}
