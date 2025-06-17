package kr.co.noerror.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DAO.mrp_DAO;
import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.mrp_input_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.Service.goods_service;
import kr.co.noerror.Service.inventory_service;

@Repository("mrp_Calulation")
public class M_mrp_Calulation {

	@Autowired
	mrp_DAO mdao;
	
	@Autowired
	private goods_service g_svc;
	
	@Autowired
	inventory_service inv_svc;
	
	
	public List<mrp_result_DTO> mrp_calc(List<mrp_input_DTO> mrp_inputs) {
		Map<String, mrp_result_DTO> aggregated = new HashMap<>();
		Map<String, Integer> ind_item_all_stock = this.inv_svc.ind_item_all_stock();
		
		for (mrp_input_DTO input : mrp_inputs) {
			List<bom_DTO> boms = this.mdao.boms_for_mrp(input.getBom_code());
			
			for (bom_DTO bom : boms) {
				String item_code = bom.getITEM_CODE();
				int required_qty = bom.getBOM_QTY() * input.getProduct_qty();
				int total_stock = ind_item_all_stock.getOrDefault(item_code, 0); 
				int safety_stock = this.mdao.itm_safe_stock(item_code);
				int reserved_stock = 0;
				int available_stock = total_stock - safety_stock - reserved_stock;
				int shortage_stock = Math.min(available_stock - required_qty, 0);
				aggregated.merge(item_code,
						new mrp_result_DTO(item_code, bom.getITEM_TYPE(), bom.getITEM_NAME(), required_qty, bom.getITEM_UNIT(), bom.getITEM_COST(), total_stock, safety_stock, reserved_stock, available_stock, shortage_stock),
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
