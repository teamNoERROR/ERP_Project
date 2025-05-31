package kr.co.noerror.DTO;

import lombok.Data;

@Data
public class purchase_item_DTO {
    private String item_code;
    private String item_type;
    private String item_name;
    private int item_qty;
    private String item_unit;
    private double item_cost;
    private double item_amount;
}
