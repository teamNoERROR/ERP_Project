package kr.co.noerror.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.noerror.DAO.pchreq_DAO;
import kr.co.noerror.DTO.pchreq_DTO;
import kr.co.noerror.DTO.pchreq_detail_DTO;
import kr.co.noerror.DTO.pchreq_item_DTO;
import kr.co.noerror.DTO.pchreq_req_DTO;
import kr.co.noerror.Model.M_unique_code_generator;

@Service
public class pchreq_serviceImpl implements pchreq_service {

	@Autowired
    private pchreq_DAO pchreq_dao;
	
	@Autowired
	private M_unique_code_generator unique_code_generator;

    @Override
    public Map<String, Object> pchreq_save(Map<String, pchreq_req_DTO> requestMap) {

        Map<String, Object> response = new HashMap<>();
        pchreq_req_DTO pchreq_req_dto = null;

        int result1 = 0;
        int result2 = 0;
        int cnt = 0;

        try {
            for (Map.Entry<String, pchreq_req_DTO> entry : requestMap.entrySet()) {
                String company_code = entry.getKey();
                pchreq_req_dto = entry.getValue();

                // 중복 없는 발주 코드 생성
                String pch_code = this.unique_code_generator.generate("pch-", code -> pchreq_dao.pch_code_check(code) > 0);

                // 발주 헤더 DTO 생성
                pchreq_DTO pchreq_entity = new pchreq_DTO();
                pchreq_entity.setPch_code(pch_code);
                pchreq_entity.setCompany_code(company_code);
                pchreq_entity.setPch_status("발주요청");
                pchreq_entity.setDue_date(pchreq_req_dto.getDue_date());
                pchreq_entity.setPay_method(pchreq_req_dto.getPay_method());
                pchreq_entity.setEmp_code(pchreq_req_dto.getEmp_code());
                pchreq_entity.setMemo(pchreq_req_dto.getMemo());

                // 총 금액 계산
                Long pay_amount = 0L;
                for (pchreq_item_DTO pchreq_item_dto : pchreq_req_dto.getItems()) {
                    pay_amount += pchreq_item_dto.getItem_amount();
                }
                pchreq_entity.setPay_amount(pay_amount);

                // purchase_req 저장
                result1 += this.pchreq_dao.insert_purchase(pchreq_entity);

                cnt += pchreq_req_dto.getItems().size();

                // purchase_req_detail 저장
                for (pchreq_item_DTO idto : pchreq_req_dto.getItems()) {
                	pchreq_detail_DTO pchreq_detail_entity = new pchreq_detail_DTO();
                	pchreq_detail_entity.setPch_code(pch_code);
                	pchreq_detail_entity.setItem_code(idto.getItem_code());
                	pchreq_detail_entity.setItem_qty(idto.getItem_qty());
                    result2 += this.pchreq_dao.insert_pch_detail(pchreq_detail_entity);
                }
            }

            response.put("success", (result1 == requestMap.size()) && (result2 == cnt));

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
        }

        return response;
    }
    
    @Override
    public Map<String, Object> pchreq_update(pchreq_req_DTO pchreq_req_dto) {
    	
    	Map<String, Object> response = new HashMap<>();
    	int result1 = 0;
    	int result2 = 0;
    	try {
	    	// purchase_req 수정
	    	pchreq_DTO pchreq_entity = new pchreq_DTO();
            pchreq_entity.setPch_code(pchreq_req_dto.getPch_code());
            pchreq_entity.setDue_date(pchreq_req_dto.getDue_date());
            pchreq_entity.setPay_method(pchreq_req_dto.getPay_method());
            pchreq_entity.setEmp_code(pchreq_req_dto.getEmp_code());
            pchreq_entity.setMemo(pchreq_req_dto.getMemo());
	        result1 = this.pchreq_dao.pchreq_update(pchreq_entity);
	
	        // purchase_req_detail 수정
	        for (pchreq_item_DTO idto : pchreq_req_dto.getItems()) {
	        	pchreq_detail_DTO pchreq_detail_entity = new pchreq_detail_DTO();
	        	pchreq_detail_entity.setPch_code(pchreq_req_dto.getPch_code());
	        	pchreq_detail_entity.setItem_code(idto.getItem_code());
	        	pchreq_detail_entity.setItem_qty(idto.getItem_qty());
	            result2 += this.pchreq_dao.pchreq_detail_update(pchreq_detail_entity);
	        }
	        
            response.put("success", ((result1 == 1) && (result2 == pchreq_req_dto.getItems().size())));

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
        }

        return response;
    }
    
    @Override
    public Map<String, Object> update_pch_status(Map<String, String> requestParam) {
    	
    	Map<String, Object> response = new HashMap<>();
    	
    	try {
    		int result = this.pchreq_dao.update_pch_status(requestParam);
    		response.put("success", (result == 1));
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("success", false);
	    }
	
	    return response;
    }
}
