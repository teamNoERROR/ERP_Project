package kr.co.noerror.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class mrp_result_DTO {
    private String item_code;
    private String item_type;
    private String item_name;
    private int required_qty;
    private String item_unit;
    private int total_stock;
    private int safety_stock;
    private int reserved_stock;
    private int available_stock;
    private int shortage_stock;
}
