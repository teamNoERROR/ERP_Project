package kr.co.noerror.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.noerror.DAO.mrp_DAO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.Model.M_unique_code_generator;

@Service
public class mrp_serviceImpl implements mrp_service {
	
	@Autowired
	private M_unique_code_generator unique_code_generator;
	
	@Autowired
    private mrp_DAO mrp_dao;
	
	@Override
	public Map<String, Object> mrp_save(List<mrp_result_DTO> results) {
		
		Map<String, Object> response = new HashMap<>();
		
        //중복 없는 mrp코드 생성
        String mrp_code = this.unique_code_generator.generate("MRP-", code -> mrp_dao.mrp_code_check(code) > 0);

        
        try {
        	
        	//mrp_header 테이블에 저장
        	int result1 = this.mrp_dao.insert_mrp_header(mrp_code);
        	
        	//mrp_detail 테이블에 저장
        	int result2 = 0;
            for (mrp_result_DTO mdto : results) {
            	mdto.setMrp_code(mrp_code);
                result2 += this.mrp_dao.insert_mrp_detail(mdto);
            }

            if((result1==1) && (result2 == results.size())) {
	            response.put("success", true);
	            response.put("mrp_code", mrp_code);
            }
            else {
            	response.put("success", false);
            }

        } catch (Exception e) {
        	System.out.println(e);
            response.put("success", false);
        }
		return response;
	}
}
