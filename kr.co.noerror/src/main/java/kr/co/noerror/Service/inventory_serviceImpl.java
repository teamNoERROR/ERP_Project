package kr.co.noerror.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.inventory_DAO;
import kr.co.noerror.DTO.IOSF_DTO;

@Service
public class inventory_serviceImpl implements inventory_service {
	
	@Resource(name="inventory_DAO")
	inventory_DAO inv_dao;
	
	//입고창고 + 부자재창고 아이템별 재고수
	@Override
	public int ind_item_stock(String item_code) {
		int ind_item_stock = this.inv_dao.ind_item_stock(item_code);  
		return ind_item_stock;
	}

	//완제품별 재고합
	@Override
	public Map<String, Integer> ind_pd_stock() {
		List<IOSF_DTO> ind_pd_stock = this.inv_dao.ind_pd_stock(); 
		Map<String, Integer> stock_qty = new HashMap<>();
		for (IOSF_DTO dto : ind_pd_stock) {
			stock_qty.put(dto.getProduct_code(), dto.getInd_pd_stock());
		}
		return stock_qty;
	}
	
	//완제품 재고 리스트
	@Override
	public List<IOSF_DTO> pd_stock_list() {
		List<IOSF_DTO> pd_stock_list = this.inv_dao.pd_stock_list(); 
		return pd_stock_list;
	}
	

}
