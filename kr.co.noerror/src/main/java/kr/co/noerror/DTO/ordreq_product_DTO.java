package kr.co.noerror.DTO;

import lombok.Data;

@Data
public class ordreq_product_DTO {
    private String product_code;
    private String product_type;
    private String product_name;
    private Long product_qty;
    private String product_unit;
    private Long product_cost;
    private Long product_amount;
}
