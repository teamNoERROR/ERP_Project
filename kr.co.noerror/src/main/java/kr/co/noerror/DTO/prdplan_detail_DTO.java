package kr.co.noerror.DTO;

import lombok.Data;

//production_plan_detail 테이블과 1:1 매핑되는 엔티티
@Data
public class prdplan_detail_DTO {
	private Long pidx;
	private String plan_code;
    private String product_code;
    private String bom_code;
    private Long product_qty;
}
