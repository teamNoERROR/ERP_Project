package kr.co.noerror.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.client_DAO;
import kr.co.noerror.DAO.common_DAO;
import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.employee_DTO;

@Service
public class common_serviceImpl implements common_service {
	
	
	@Resource(name="common_DAO")
	common_DAO cmn_dao;
	
	
	@Override
	public List<employee_DTO> emp_list() {
		List<employee_DTO> all_data = this.cmn_dao.emp_list();
		return all_data;
	}
	
	@Override
	public List<WareHouse_DTO> warehouse_list(String wh_tp) {
		List<WareHouse_DTO> warehouse_list = this.cmn_dao.warehouse_list(wh_tp);
		return warehouse_list;
	}

	//출고제품 리스트 모달 
	@Override
	public String out_pd_list(String out_pd_data) {
		JSONArray ja = new JSONArray(out_pd_data);
		int data_ea = ja.length();
		List<IOSF_DTO> outReqList = new ArrayList<>();
		for (int w = 0; w < data_ea; w++) {
		    JSONObject jo = ja.getJSONObject(w);
		    
		    IOSF_DTO wfs_dto = new IOSF_DTO();
		    
		    wfs_dto.setPidx(jo.getString("idx"));
		    wfs_dto.setProduct_code(jo.getString("code"));
		    
		    outReqList.add(wfs_dto);
		}
		
		List<IOSF_DTO> out_list = this.cmn_dao.out_pd_list(outReqList);
		
		JSONArray dataArr = new JSONArray();

	    for (IOSF_DTO dto : out_list) {
	        JSONObject jo = new JSONObject();
	        jo.put("product_code", dto.getProduct_code());
	        jo.put("product_name", dto.getProduct_name());
	        jo.put("pd_qty", dto.getPd_qty());
	        dataArr.put(jo);
	    }
		
		return dataArr.toString();
	}

}
