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
	
	
	//개별 완제품 재고수
	@Override
	public Map<String, Integer> ind_pd_all_stock() {
		List<IOSF_DTO> ind_pd_all_stock = this.inv_dao.ind_pd_all_stock(); 
		
		Map<String, Integer> all_stock_qty = new HashMap<>();
		for (IOSF_DTO dto : ind_pd_all_stock) {
			if (dto.getInd_pd_stock() == null) {
			    dto.setInd_pd_stock(0);
			}
			all_stock_qty.put(dto.getProduct_code(), dto.getInd_pd_stock());
		}
		return all_stock_qty;
	}
	
	//창고별+완제품 재고수
	@Override
	public Map<String, Integer> ind_pd_stock(String wh_code) {
		List<IOSF_DTO> ind_pd_stock = this.inv_dao.ind_pd_stock(wh_code);
		
		Map<String, Integer> stock_qty = new HashMap<>();
		for (IOSF_DTO dto : ind_pd_stock) {
			stock_qty.put(dto.getProduct_code(), dto.getInd_pd_stock());
		}
		System.out.println("stock_qty : " + stock_qty);
		return stock_qty;
	}
	
	//완제품 재고 리스트
	@Override
	public List<IOSF_DTO> pd_stock_list() {
		List<IOSF_DTO> pd_stock_list = this.inv_dao.pd_stock_list(); 
		return pd_stock_list;
	}
	
	
	
	//개별 부자재 재고수
	@Override
	public Map<String, Integer> ind_item_all_stock() {
		List<IOSF_DTO> ind_item_all_stock = this.inv_dao.ind_item_all_stock(); 
		
		Map<String, Integer> all_stock_qty = new HashMap<>();
		for (IOSF_DTO dto : ind_item_all_stock) {
			if (dto.getInd_itm_stock() == null) {
			    dto.setInd_itm_stock(0);
			}
			all_stock_qty.put(dto.getItem_code(), dto.getInd_itm_stock());
		}
		
		System.out.println("all_stock_qty : " + all_stock_qty);
		return all_stock_qty;
	}

	//부자재 재고리스트
	@Override
	public List<IOSF_DTO> itm_stock_list() {
		List<IOSF_DTO> itm_stock_list = this.inv_dao.itm_stock_list(); 
		return itm_stock_list;
	}

	

}
