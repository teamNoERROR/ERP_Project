package kr.co.noerror.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.noerror.DAO.prdplan_DAO;
import kr.co.noerror.DTO.paging_info_DTO;
import kr.co.noerror.DTO.pchreq_DTO;
import kr.co.noerror.DTO.pchreq_detail_DTO;
import kr.co.noerror.DTO.pchreq_item_DTO;
import kr.co.noerror.DTO.prdplan_DTO;
import kr.co.noerror.DTO.prdplan_detail_DTO;
import kr.co.noerror.DTO.prdplan_product_DTO;
import kr.co.noerror.DTO.prdplan_req_DTO;
import kr.co.noerror.DTO.prdplan_res_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.Model.M_unique_code_generator;

@Service
public class prdplan_serviceImpl implements prdplan_service, generic_list_service<prdplan_res_DTO> {
	
	@Autowired
    private prdplan_DAO prdplan_dao;
	
	@Autowired
	private M_unique_code_generator unique_code_generator;
	
	@Autowired
	inventory_service inv_svc; //재고 서비스 
	
	@Override
	public List<prdplan_res_DTO> paged_list(search_condition_DTO search_cond, paging_info_DTO paging_info) {
		Map<String, Object> params = new HashMap<>();
        params.put("search_word", search_cond.getSearch_word());
        params.put("statuses", search_cond.getStatuses());
        params.put("start", paging_info.getStart());
        params.put("end", paging_info.getEnd());
        return this.prdplan_dao.prdplan_paged_list(params);
	}
	
	@Override
	public int search_count(search_condition_DTO search_cond) {
		return this.prdplan_dao.prdplan_search_count(search_cond);
	}
	
	@Override
	public Map<String, Object> prdplan_save(prdplan_req_DTO plandto) {
		System.out.println("plandto : " + plandto);
		Map<String, Object> response = new HashMap<>();
		
        int result1 = 0;
        int result2 = 0;
        int cnt = 0;

        try {
            // 중복 없는 생산계획 코드 생성
            String plan_code = this.unique_code_generator.generate("PLN-", code -> prdplan_dao.plan_code_check(code) > 0);

            // 생산계획 테이블 엔티티 생성
            prdplan_DTO prdplan_entity = new prdplan_DTO();
            prdplan_entity.setPlan_code(plan_code);
            prdplan_entity.setOrder_code(plandto.getOrder_code());
            prdplan_entity.setCompany_code(plandto.getCompany_code());
            prdplan_entity.setPriority(plandto.getPriority());
            prdplan_entity.setStart_date(plandto.getStart_date());
            prdplan_entity.setEnd_date(plandto.getEnd_date());
            prdplan_entity.setCompany_code(plandto.getCompany_code());
            prdplan_entity.setEcode(plandto.getEcode());
            prdplan_entity.setPlan_status("생산계획수립");
            prdplan_entity.setMrp_status("미완료");
            prdplan_entity.setMemo(plandto.getMemo());

            // 생산계획 테이블에 저장
            result1 += this.prdplan_dao.prdplan_insert(prdplan_entity);

            cnt += plandto.getProducts().size();

            // 생산계획상세 테이블에 저장
            for (prdplan_product_DTO pdto : plandto.getProducts()) {
            	prdplan_detail_DTO prdplan_detail_entity = new prdplan_detail_DTO();
            	prdplan_detail_entity.setPlan_code(plan_code);
            	prdplan_detail_entity.setProduct_code(pdto.getProduct_code());
            	prdplan_detail_entity.setBom_code(pdto.getBom_code());
            	prdplan_detail_entity.setProduct_qty((long)pdto.getProduct_qty());
                result2 += this.prdplan_dao.prdplan_detail_insert(prdplan_detail_entity);
            }

            response.put("success", (result1 == 1) && (result2 == cnt));

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
        }

	    return response;
	}
	
	@Override
	public Map<String, Object> prdplan_update(prdplan_req_DTO reqdto) {
    	Map<String, Object> response = new HashMap<>();
    	int result1 = 0;
    	int result2 = 0;
    	try {
	    	// purchase_req 수정
	    	prdplan_DTO prdplan_entity = new prdplan_DTO();
	    	prdplan_entity.setPlan_code(reqdto.getPlan_code());
	    	prdplan_entity.setPriority(reqdto.getPriority());
	    	prdplan_entity.setStart_date(reqdto.getStart_date());
	    	prdplan_entity.setEnd_date(reqdto.getEnd_date());
	    	prdplan_entity.setEcode(reqdto.getEcode());
	    	prdplan_entity.setMemo(reqdto.getMemo());
	        result1 = this.prdplan_dao.prdplan_update(prdplan_entity);
	
	        // purchase_req_detail 수정
	        for (prdplan_product_DTO pdto : reqdto.getProducts()) {
	        	prdplan_detail_DTO prdplan_detail_entity = new prdplan_detail_DTO();
	        	prdplan_detail_entity.setPlan_code(reqdto.getPlan_code());
	        	prdplan_detail_entity.setProduct_code(pdto.getProduct_code());
	        	prdplan_detail_entity.setProduct_qty((long)pdto.getProduct_qty());
	            result2 += this.prdplan_dao.prdplan_detail_update(prdplan_detail_entity);
	        }
	        
            response.put("success", ((result1 == 1) && (result2 == reqdto.getProducts().size())));

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
        }

        return response;
	}
	
	@Override
	public Map<String, Object> plan_status_update(Map<String, String> requestParam) {
		Map<String, Object> response = new HashMap<>();
    	
    	try {
    		int result = this.prdplan_dao.plan_status_update(requestParam);
    		response.put("success", (result == 1));
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("success", false);
	    }
	
	    return response;
	}
}
