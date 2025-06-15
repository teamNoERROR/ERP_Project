package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("mrp_input_DTO")
public class mrp_input_DTO {
	private String bom_code;
	private int product_qty;
	
	private String item_code;
}
