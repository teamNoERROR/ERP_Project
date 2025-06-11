package kr.co.noerror.Service;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.stock_DAO;

@Service
public class stock_serviceImpl implements stock_service {
	
	@Resource(name="stock_DAO")
	stock_DAO st_dao;
	
	@Override
	public int ind_item_stock(String item_code) {
		int ind_item_stock = this.st_dao.ind_item_stock(item_code);  
		return ind_item_stock;
	}

}
