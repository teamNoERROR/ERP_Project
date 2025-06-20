package kr.co.noerror.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.outbound_DAO;
import kr.co.noerror.DAO.prdplan_DAO;
import kr.co.noerror.DTO.IOSF_DTO;
import kr.co.noerror.DTO.mrp_result_DTO;
import kr.co.noerror.DTO.paging_info_DTO;
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
	
	@Autowired
	private outbound_service out_svc;  //출고 서비스
	
	@Resource(name="outbound_DAO")
	outbound_DAO out_dao; // 출고 DAO
	
	//생산계획 리스트 
	@Override
	public List<prdplan_res_DTO> paged_list(search_condition_DTO search_cond, paging_info_DTO paging_info, String parent) {
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
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> plan_status_update(Map<String, String> requestParam) {
		Map<String, Object> response = new HashMap<>();
    	System.out.println("requestParam : " + requestParam);

    	try {
        	int result = this.prdplan_dao.plan_status_update(requestParam);  //plan_status 상태변경 
        	
        	String status = requestParam.get("plan_status");
            String planCode = requestParam.get("plan_code");
          //생산완료 상태일 경우에만 출고 처리
        	if ("생산완료".equals(status)) {
    			//mrp 정보 확인
    			List<mrp_result_DTO> mrp_result = this.prdplan_dao.select_mrp_result(planCode);
    			
    			//부자재 재고 출고처리
    			List<String> item_codes = new ArrayList<>(); 
    			List<Integer> item_qtys = new ArrayList<>();
    			
    			//mrp테이블에서 가져온 부자재 리스트 
    			for (int w = 0; w < mrp_result.size(); w++) {
    				String itmcode = mrp_result.get(w).getItem_code();
    				int itm_req_qty = mrp_result.get(w).getRequired_qty();
    				
    				item_codes.add(itmcode);
    				item_qtys.add(itm_req_qty);
    			}
    			
    			for (int i = 0; i < item_codes.size(); i++) {
    				String itmCode = item_codes.get(i);
    				int itmQty = item_qtys.get(i); 
    				
    				//inv_lot순으로 정렬된 리스트를 다시 리스트에 넣기 
    				Integer jego = this.prdplan_dao.out_itemQty(itmCode);
    				if(jego == null) {
    					jego = 0;
    				}
    				
    				List<IOSF_DTO> outitminfo_result2 = this.prdplan_dao.out_itemList2(itmCode);
    				
    				if(itmQty <= jego) {		//출고완료 insert 
//    					int availableQty = outitminfo_result2.get(i).getItem_qty();
//    					int usedQty = Math.min(availableQty, itmQty);
//    					for (IOSF_DTO lot : outitminfo_result2) {
    						if (itmQty <= 0) break;
        					
    						//부자재 출고완료 INSERT
    						Map<String, Object> outParams = new HashMap<>();
    						outParams.put("wh_code", outitminfo_result2.get(0).getWh_code());
    						outParams.put("inbound_code", "-");
    						outParams.put("ind_pch_code", outitminfo_result2.get(0).getInd_pch_cd() != null ? outitminfo_result2.get(0).getInd_pch_cd() : "-");
    						outParams.put("item_code", itmCode);
    						outParams.put("item_qty", itmQty);
    						outParams.put("employee_code", outitminfo_result2.get(0).getEmployee_code());
    						outParams.put("inv_lot", outitminfo_result2.get(0).getInv_lot());
    						outParams.put("product_code", itmCode);
    						outParams.put("wmt_code", outitminfo_result2.get(0).getWmt_code());
    						outParams.put("wh_type", "mt");
    						
    						System.out.println("outParams : " + outParams);
    						this.prdplan_dao.out_mtwh_result(outParams);   //부자재 출고처리
    						this.prdplan_dao.IOSF_warehouse_move_up(outParams);  //출고처리 후 원입고건 체크박스 막기
    						
//    					}
    					
    				}
    				
    				else if (itmQty > jego) {
    					throw new RuntimeException("재고 부족: " + itmCode);
    				}
    				
    				/*
    				 1. 완제품을 만들기 위한 각 부재 전체 수량(sum)
    				 2. wherehouse_material => 출고  각 부재 전체 수량(sum) 
    				*/
    				
    				
    				/*    					if (itmQty <= 0) break;
    					
    			
    					
    					
    				}
    				
    				
    				*/
    			}
        	}
        	
        	response.put("success", (result == 1));
        	return response;
        	   
        } catch (Exception e) {
			e.printStackTrace();
//			response.put("success", false);
			throw new RuntimeException("트랜잭션 롤백", e); // 반드시 던져야 rollback 됨
		}
	
	 
	}
	
	

}
