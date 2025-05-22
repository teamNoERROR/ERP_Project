package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("order_DTO")
public class order_DTO {

    private Long oidx;               // 자동 증가 ID
    private String orderCode;        // 주문 고유 코드
    private String productCode;      // 제품 고유 코드
    private int orderQty;            // 수량 (g)
    private String companyCode;      // 구매처 고유 코드
    private String companyName;      // 구매처 이름
    private String managerCode;      // 거래처 담당자 고유 코드
    private String managerName;      // 거래처 담당자명
    private String orderStatus;      // 주문 상태 ('주문요청', '주문취소' 등)
    private String dueDate;          // 납기 요청일 (yyyy-MM-dd 형식의 문자열)
    private String memo;             // 비고
    private String requestDate;   // 주문일자
    private String modifyDate;    // 수정일
    
}
