package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("Inbound_DTO")
public class Inbound_DTO {
    // 창고 정보
    String wh_code;
    String wh_name;
    String wh_location;

    // 제품 정보
    String product_code;
    String product_name;
    String product_category1;
    String product_category2;
    String product_use_flag;

    // 수량 및 입고일, 기타사항
    int quantity;
    String inbound_date;
    String etc;

    // 거래처 정보
    String client_code;
    String client_name;
    String client_use_flag;
}