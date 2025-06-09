package kr.co.noerror.DTO;

import java.util.List;

import lombok.Data;

//프론트앤드에서 전달받는 값을 받는 생산계획 정보 DTO
@Data
public class prdplan_req_DTO {
	private String plan_code;
	private String order_code;
    private String due_date;
    private String priority;
    private String start_date;
    private String end_date;
    private String company_code;
    private String ecode;
    private String ename;
    private String memo;
    private List<prdplan_product_DTO> products;
}
