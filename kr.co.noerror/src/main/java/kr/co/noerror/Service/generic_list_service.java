package kr.co.noerror.Service;

import java.util.List;

import kr.co.noerror.DTO.paging_info_DTO;
import kr.co.noerror.DTO.search_condition_DTO;

public interface generic_list_service<T> {
	int getTotalCount(search_condition_DTO search_cond);
    List<T> getPagedList(search_condition_DTO search_cond, paging_info_DTO paging_info);
}
