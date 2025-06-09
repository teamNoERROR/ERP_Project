package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("ordreq_detail_DTO")
public class ordreq_detail_DTO {
    private Long oidx;
    private String order_code;
    private String product_code;
    private Long product_qty;
}
