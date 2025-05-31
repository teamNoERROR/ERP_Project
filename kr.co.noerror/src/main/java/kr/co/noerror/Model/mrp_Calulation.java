package kr.co.noerror.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DAO.mrp_DAO;
import kr.co.noerror.DTO.mrp_input_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.temp_bom_DTO;

@Repository("mrp_Calulation")
public class mrp_Calulation {

	@Autowired
	mrp_DAO mdao;
	
	public List<mrp_result_DTO> mrp_calc(List<mrp_input_DTO> mrp_inputs) {
		Map<String, mrp_result_DTO> aggregated = new HashMap<>();
		
		for (mrp_input_DTO input : mrp_inputs) {
			List<temp_bom_DTO> boms = this.mdao.boms_for_mrp(input.getBom_code());
			
			for (temp_bom_DTO bom : boms) {
				String item_code = bom.getItem_code();
				int required_qty = bom.getQuantity() * input.getProduct_qty();
				int total_stock = 200;
				int safety_stock = 50;
				int reserved_stock = 30;
				int available_stock = total_stock - safety_stock - reserved_stock;
				int shortage_stock = Math.min(available_stock - required_qty, 0);
				aggregated.merge(item_code,
						new mrp_result_DTO(item_code, bom.getItem_type(), bom.getItem_name(), required_qty, bom.getItem_unit(), total_stock, safety_stock, reserved_stock, available_stock, shortage_stock),
                        (oldVal, newVal) -> {
                            oldVal.setRequired_qty(oldVal.getRequired_qty() + newVal.getRequired_qty());
                            return oldVal;
                        }						
					);
			}
		}
		return new ArrayList<>(aggregated.values());
	}
}
