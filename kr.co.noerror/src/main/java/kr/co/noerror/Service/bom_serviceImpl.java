package kr.co.noerror.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.bom_DAO;
import kr.co.noerror.DAO.goods_DAO;
import kr.co.noerror.DTO.bom_DTO;

@Service
public class bom_serviceImpl implements bom_service{
	@Resource(name="bom_DAO")
	bom_DAO b_dao;
	
	@Override
	public int bom_check(String pd_code) {
		int bom_ck = this.b_dao.bom_check(pd_code);
		return bom_ck;
	}

	@Override
	public bom_DTO bom_detail(String pd_code) {
		bom_DTO bom_detail = this.b_dao.bom_detail(pd_code);
		return bom_detail;
	}

}
