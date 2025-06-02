package kr.co.noerror.DTO;

import lombok.Data;

@Data
public class pchreq_detail_DTO {
    private Long pidx;
    private String pch_code;
    private String item_code;
    private Long item_qty;
}
