package kr.co.noerror.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ordreq_req_DTO {
	private String order_code;
    private String company_code;
    private String company_name;
    private String due_date;
    private String pay_method;
    private String ecode;
    private String memo;
    private List<ordreq_product_DTO> products;
}
