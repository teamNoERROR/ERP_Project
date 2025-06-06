package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.client_DTO;
import kr.co.noerror.DTO.ordreq_DTO;
import kr.co.noerror.DTO.ordreq_detail_DTO;
import kr.co.noerror.DTO.ordreq_res_DTO;
import kr.co.noerror.DTO.pchreq_DTO;
import kr.co.noerror.DTO.pchreq_detail_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.DTO.temp_client_DTO;
import kr.co.noerror.DTO.temp_products_DTO;

@Mapper
public interface ordreq_mapper {
	List<ordreq_DTO> order_list(Map<String, Object> mparam);
	int order_count(Map<String, Object> mparam);
	List<client_DTO> clients_for_order();
	List<products_DTO> products_list();
	int order_code_check(String order_code);
	int ordreq_insert(ordreq_DTO odto);
	int ordreq_detail_insert(ordreq_detail_DTO odto);
	List<ordreq_res_DTO> ordreq_paged_list(Map<String, Object> mparam);
	int ordreq_search_count(search_condition_DTO search_cond);
	List<ordreq_res_DTO> ordreq_detail(String order_code);
	int ordreq_update(ordreq_DTO odto);
	int ordreq_detail_update(ordreq_detail_DTO odto);
	int ord_status_update(Map<String, String> mparam);
}
