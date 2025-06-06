package kr.co.noerror.DTO;

import lombok.Data;

@Data
public class pchreq_DTO {
	private Long pidx;
    private String pch_code;
    private String company_code;
    private String pch_status;
    private String due_date;
    private Long pay_amount;
    private String pay_method;
    private String ecode;
    private String memo;
    private String request_date;
    private String modify_date;
}
