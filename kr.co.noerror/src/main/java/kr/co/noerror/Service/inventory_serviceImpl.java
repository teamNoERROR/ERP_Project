package kr.co.noerror.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.inventory_DAO;
import kr.co.noerror.DTO.IOSF_DTO;

@Service
public class inventory_serviceImpl implements inventory_service {
	
	@Resource(name="inventory_DAO")
	inventory_DAO inv_dao;
	
	@Override
	public int ind_item_stock(String item_code) {
		int ind_item_stock = this.inv_dao.ind_item_stock(item_code);  
		return ind_item_stock;
	}

	//완제품 재고리스트
	@Override
	public List<IOSF_DTO> pd_stock_list() {
		List<IOSF_DTO> pd_stock_list = this.inv_dao.pd_stock_list(); 
		return pd_stock_list;
	}

	//완제품별 재고합
	@Override
	public int ind_pd_stock(String pd_code) {
		int ind_pd_stock = this.inv_dao.ind_pd_stock(pd_code);  
		return ind_pd_stock;
	}
	
	

}
