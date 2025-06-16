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
		List<IOSF_DTO> ind_pd_all_stock = this.inv_dao.ind_pd_stock(); 
		
		Map<String, Integer> all_stock_qty = new HashMap<>();
		for (IOSF_DTO dto : ind_pd_all_stock) {
			if (dto.getInd_pd_stock() == null) {
			    dto.setInd_pd_stock(0);
			}
			all_stock_qty.put(dto.getProduct_code(), dto.getInd_pd_stock());
		}
		return all_stock_qty;
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

	//창고별 보유중인 제품 각각의 재고수
	@Override
	public List<IOSF_DTO> pd_by_whlist() {
		List<IOSF_DTO> pd_stock_bywh_list = this.inv_dao.pd_stock_bywh_list(); 
		return pd_stock_bywh_list;
	}

	// 상품 + 창고별 재고
	@Override
	public Integer stockByWhnPd (String whCode, String pdCode) {
		Map<String, String> mapp = new HashMap<>();
		mapp.put("whCode", whCode);
		mapp.put("pdCode", pdCode);
		Integer stockByWhnPd = this.inv_dao.stockByWhnPd(mapp);
		return stockByWhnPd;
	};
	
	// 상품별 전체 재고
	@Override
	public Integer stockPdTotal (String pdCode) {
		Integer stockPdTotal = this.inv_dao.stockPdTotal(pdCode);
		return stockPdTotal;
	}


	@Override
	public int wh_pd_sp() {
		int wh_pd_sp = this.inv_dao.wh_pd_sp();
		return wh_pd_sp;
	};
	

}
