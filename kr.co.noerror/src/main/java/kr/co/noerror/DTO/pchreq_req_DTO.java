package kr.co.noerror.DTO;

import java.util.List;

import lombok.Data;

@Data
public class pchreq_req_DTO {
	private String pch_code;
    private String company_code;
    private String company_name;
    private String due_date;
    private String pay_method;
    private String ecode;
    private String memo;
    private List<pchreq_item_DTO> items;
}
