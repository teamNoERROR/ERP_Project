package kr.co.noerror.DTO;

import lombok.Data;

@Data
public class pchreq_item_DTO {
    private String item_code;
    private String item_type;
    private String item_name;
    private Long item_qty;
    private String item_unit;
    private Long item_cost;
    private Long item_amount;
}
