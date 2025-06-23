package kr.co.noerror.DTO;

import java.util.List;

import lombok.Data;

@Data
public class mrp_save_req_DTO {
    private List<mrp_result_DTO> summary;
    private List<mrp_result2_DTO> detail;
}
