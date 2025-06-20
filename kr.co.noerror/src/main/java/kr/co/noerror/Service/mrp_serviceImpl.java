package kr.co.noerror.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.noerror.DAO.mrp_DAO;
import kr.co.noerror.DTO.mrp_result2_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.mrp_result_header_DTO;
import kr.co.noerror.Model.M_unique_code_generator;

@Service
public class mrp_serviceImpl implements mrp_service {
	
	@Autowired
	private M_unique_code_generator unique_code_generator;
	
	@Autowired
    private mrp_DAO mrp_dao;
	
	@Override
	public Map<String, Object> save(List<mrp_result_DTO> summaryList, List<mrp_result2_DTO> detailList) {
	    Map<String, Object> response = new HashMap<>();
	    String mrp_code = this.unique_code_generator.generate("MRP-", code -> mrp_dao.mrp_code_check(code) > 0);

	    try {
	        // MRP Header 저장
	        int headerResult = this.mrp_dao.insert_mrp_header(mrp_code);

	        // Summary 저장
	        int summaryResult = 0;
	        for (mrp_result_DTO dto : summaryList) {
	            dto.setMrp_code(mrp_code);
	            summaryResult += this.mrp_dao.insert_mrp_summary(dto);
	        }

	        int detailResult = 0;
	        List<String> planCodeList = new ArrayList<>();
	        for (mrp_result2_DTO dto : detailList) {
	        	// 중복없는 plan_code list 생성
	        	if (!planCodeList.contains(dto.getPlan_code())) {
	        		planCodeList.add(dto.getPlan_code());
	        	}
	        	
	        	//Detail 저장
	            dto.setMrp_code(mrp_code);
	            detailResult += this.mrp_dao.insert_mrp_detail(dto);
	        }

	        // production_plan 테이블에 mrp_status 업데이트
	        int updateResult = 0;
	        for (String plan_code : planCodeList) {
	        	updateResult += this.mrp_dao.update_mrp_status(plan_code);
	        }
	        
	        boolean success = headerResult == 1
	                       && summaryResult == summaryList.size()
	                       && detailResult == detailList.size()
	                       && updateResult == planCodeList.size();

	        response.put("success", success);
	        response.put("mrp_code", success ? mrp_code : null);

	    } catch (Exception e) {
	        System.out.println("MRP 저장 중 오류: " + e.getMessage());
	        response.put("success", false);
	    }

	    return response;
	}
	
	@Override
	public List<mrp_result_header_DTO> mrp_result_list(String mrp_status) {
		List<mrp_result_header_DTO> mrp_list =  this.mrp_dao.mrp_result_list(mrp_status);
		return mrp_list;
	}
}
