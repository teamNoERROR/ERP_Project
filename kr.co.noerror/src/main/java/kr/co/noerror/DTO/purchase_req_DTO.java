package kr.co.noerror.DTO;

import java.util.List;

import lombok.Data;

@Data
public class purchase_req_DTO {
    private String company_code;
    private String company_name;
    private String due_date;
    private String pay_method;
    private String memo;
    private List<purchase_item_DTO> items;
}
