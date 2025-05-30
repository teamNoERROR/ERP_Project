package kr.co.noerror.DTO;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //mrp_Calulation class에서 new mrp_result_DTO(item_code, bom.getItem_name(), bom.getItem_type(), required_qty, bom.getItem_unit(), total_stock, safety_stock, reserved_stock, available_stock, shortage_stock) 사용시 필요
@NoArgsConstructor  //ajax JSON.stringify(data)를 @RequestBody List<mrp_input_DTO> mrp_inputs으로 받을 경우 기본생성자 필요
public class mrp_result_DTO {
	private String mrp_code;
    private String item_code;
    private String item_type;
    private String item_name;
    private int required_qty;
    private String item_unit;
    private int total_stock;
    private int safety_stock;
    private int reserved_stock;
    private int available_stock;
    private int shortage_stock;
    private String company_code;
    private String company_name;
    
	public mrp_result_DTO(String item_code, String item_type, String item_name, int required_qty,
			String item_unit, int total_stock, int safety_stock, int reserved_stock, int available_stock,
			int shortage_stock) {
		this.item_code = item_code;
		this.item_type = item_type;
		this.item_name = item_name;
		this.required_qty = required_qty;
		this.item_unit = item_unit;
		this.total_stock = total_stock;
		this.safety_stock = safety_stock;
		this.reserved_stock = reserved_stock;
		this.available_stock = available_stock;
		this.shortage_stock = shortage_stock;
	}
    
    
}
