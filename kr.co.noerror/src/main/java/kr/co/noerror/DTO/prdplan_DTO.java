package kr.co.noerror.DTO;

import lombok.Data;

//production_plan 테이블과 1:1 매핑되는 엔티티
@Data
public class prdplan_DTO {
	private Long pidx;
	private String plan_code;
	private String order_code;
	private String priority;
	private String start_date;
	private String end_date;
	private String company_code;
	private String ecode;
    private String plan_status;
    private String mrp_status;
    private String memo;
    private String plan_date;
    private String modify_date;
}
