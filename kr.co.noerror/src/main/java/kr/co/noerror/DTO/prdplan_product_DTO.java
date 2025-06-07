package kr.co.noerror.DTO;

import lombok.Data;

//프론트앤드에서 전달받는 생산할 제품 정보를 받는 DTO
@Data
public class prdplan_product_DTO {
    private String product_code;
    private String bom_code;
    private int product_qty;
}
