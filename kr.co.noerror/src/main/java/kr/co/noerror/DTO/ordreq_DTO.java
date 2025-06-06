package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("ordreq_DTO")
public class ordreq_DTO {
	private Long oidx;
    private String order_code;
    private String company_code;
    private String ecode;
    private String order_status;
    private String pay_method;
    private int pay_amount;
    private String due_date;
    private String memo;
    private String request_date;
    private String modify_date;  
}
