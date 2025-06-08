package kr.co.noerror.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.ordreq_DAO;
import kr.co.noerror.DTO.ordreq_DTO;
import kr.co.noerror.DTO.ordreq_detail_DTO;
import kr.co.noerror.DTO.ordreq_product_DTO;
import kr.co.noerror.DTO.ordreq_req_DTO;
import kr.co.noerror.DTO.ordreq_res_DTO;
import kr.co.noerror.DTO.paging_info_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.Model.M_unique_code_generator;

@Service
public class ordreq_serviceImpl implements ordreq_service, generic_list_service<ordreq_res_DTO> {
	
	@Autowired
    private ordreq_DAO ordreq_dao;
	
	@Resource(name="ordreq_DTO")
	ordreq_DTO ordreq_dto;
	
	@Resource(name="ordreq_detail_DTO")
	ordreq_detail_DTO ord_detail_dto;
	
	@Autowired
	private M_unique_code_generator unique_code_generator;
	
	@Override
	public int search_count(search_condition_DTO search_cond) {
		return this.ordreq_dao.search_count(search_cond);
	}
	
	@Override
	public List<ordreq_res_DTO> paged_list(search_condition_DTO search_cond, paging_info_DTO paging_info) {
		Map<String, Object> params = new HashMap<>();
        params.put("search_word", search_cond.getSearch_word());
        params.put("statuses", search_cond.getStatuses());
        params.put("start", paging_info.getStart());
        params.put("end", paging_info.getEnd());
        return this.ordreq_dao.paged_list(params);
	}
	
	@Override
    public Map<String, Object> ordreq_save(List<Map<String, Object>> orders) {

		 Map<String, Object> response = new HashMap<>();
	        
	        //중복 없는 주문코드 생성
	        String order_code = this.unique_code_generator.generate("ORD-", code -> ordreq_dao.order_code_check(code) > 0);
	        
	        try {
	        	//order_request 테이블에 저장
	        	this.ordreq_dto.setOrder_code(order_code);
	        	this.ordreq_dto.setCompany_code((String)orders.get(0).get("COMPANY_CODE"));
	        	this.ordreq_dto.setEcode((String)orders.get(0).get("ECODE"));
	        	this.ordreq_dto.setOrder_status("주문요청");
	        	this.ordreq_dto.setPay_method((String)orders.get(0).get("PAY_METHOD"));
	        	this.ordreq_dto.setDue_date((String)orders.get(0).get("DUE_DATE"));
	        	this.ordreq_dto.setMemo((String)orders.get(0).get("MEMO"));

	        	 // 총 금액 계산
	            int pay_amount = 0;
	            for (Map<String, Object> order : orders) {
	                pay_amount += Long.parseLong((String)order.get("PRODUCT_AMOUNT"));
	            }
	            this.ordreq_dto.setPay_amount(pay_amount);
	        	
	        	int result1 = this.ordreq_dao.ordreq_insert(this.ordreq_dto);
	        	
	        	//order_req_detail 테이블에 저장
	        	int result2 = 0;
	            for (Map<String, Object> order : orders) {
	                this.ord_detail_dto.setOrder_code(order_code);
	                this.ord_detail_dto.setProduct_code((String)order.get("PRODUCT_CODE"));
	                this.ord_detail_dto.setProduct_qty(Long.parseLong((String)order.get("PRODUCT_QTY")));
	 
	                result2 += this.ordreq_dao.ordreq_detail_insert(this.ord_detail_dto);
	            }

	            if((result1==1) && (result2 == orders.size())) {
		            response.put("success", true);
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
	
	@Override
    public Map<String, Object> ordreq_update(ordreq_req_DTO ordreq_req_dto) {
    	
    	Map<String, Object> response = new HashMap<>();
    	int result1 = 0;
    	int result2 = 0;
    	try {
	    	// purchase_req 수정
	    	ordreq_DTO ordreq_entity = new ordreq_DTO();
            ordreq_entity.setOrder_code(ordreq_req_dto.getOrder_code());
            ordreq_entity.setDue_date(ordreq_req_dto.getDue_date());
            ordreq_entity.setPay_method(ordreq_req_dto.getPay_method());
            ordreq_entity.setEcode(ordreq_req_dto.getEcode());
            ordreq_entity.setMemo(ordreq_req_dto.getMemo());
            
	        result1 = this.ordreq_dao.ordreq_update(ordreq_entity);
	
	        // purchase_req_detail 수정
	        for (ordreq_product_DTO pdto : ordreq_req_dto.getProducts()) {
	        	ordreq_detail_DTO ordreq_detail_entity = new ordreq_detail_DTO();
	        	ordreq_detail_entity.setOrder_code(ordreq_req_dto.getOrder_code());
	        	ordreq_detail_entity.setProduct_code(pdto.getProduct_code());
	        	ordreq_detail_entity.setProduct_qty(pdto.getProduct_qty());
	            result2 += this.ordreq_dao.ordreq_detail_update(ordreq_detail_entity);
	        }
	        
            response.put("success", ((result1 == 1) && (result2 == ordreq_req_dto.getProducts().size())));

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
        }

        return response;
    }
	
    @Override
    public Map<String, Object> ord_status_update(Map<String, String> requestParam) {
    	
    	Map<String, Object> response = new HashMap<>();
    	
    	try {
    		int result = this.ordreq_dao.ord_status_update(requestParam);
    		response.put("success", (result == 1));
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("success", false);
	    }
	
	    return response;
    }
}
