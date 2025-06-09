package kr.co.noerror.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.employee_DTO;
import kr.co.noerror.DTO.ordreq_DTO;
import kr.co.noerror.DTO.ordreq_res_DTO;
import kr.co.noerror.DTO.pchreq_DTO;
import kr.co.noerror.DTO.pchreq_detail_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;
import kr.co.noerror.DTO.plan_DTO;
import kr.co.noerror.DTO.prdplan_DTO;
import kr.co.noerror.DTO.prdplan_detail_DTO;
import kr.co.noerror.DTO.prdplan_res_DTO;
import kr.co.noerror.DTO.search_condition_DTO;
import kr.co.noerror.DTO.temp_bom_DTO;

@Mapper
public interface proplan_mapper {
	List<prdplan_res_DTO> prdplan_paged_list(Map<String, Object> mparam);
	int prdplan_search_count(search_condition_DTO search_cond);
	List<bom_DTO> bom_items(String bom_code);
	List<ordreq_res_DTO> orders_modal();
	List<employee_DTO> emps_modal();
	int plan_code_check(String plan_code);
	int insert_plan(plan_DTO pdto);
	int prdplan_insert(prdplan_DTO pdto);
	int prdplan_detail_insert(prdplan_detail_DTO detaildto);
	List<prdplan_res_DTO> prdplan_detail(String plan_code);
	int prdplan_update(prdplan_DTO pdto);
	int prdplan_detail_update(prdplan_detail_DTO pdto);
	int plan_status_update(Map<String, String> mparam);
}
