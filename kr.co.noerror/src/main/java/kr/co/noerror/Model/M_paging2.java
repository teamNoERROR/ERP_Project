package kr.co.noerror.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.noerror.DTO.paging_info_DTO;

@Repository("M_paging2")
public class M_paging2 {

	@Autowired
	paging_info_DTO paging_info_dto;
	
    public paging_info_DTO calculate(int total_count, int page_no, int page_size, int page_block) {

        int page_cnt = (total_count - 1) / page_size + 1;
        int start = (page_no - 1) * page_size;
        int end = page_no * page_size + 1;

        int start_page = ((page_no - 1) / page_block) * page_block + 1;
        int end_page = Math.min(start_page + page_block - 1, page_cnt);

        paging_info_dto.setPage_no(page_no);
        paging_info_dto.setPage_size(page_size);
        paging_info_dto.setTotal_count(total_count);
        paging_info_dto.setStart(start);
        paging_info_dto.setEnd(end);
        paging_info_dto.setPage_cnt(page_cnt);
        paging_info_dto.setStart_page(start_page);
        paging_info_dto.setEnd_page(end_page);

        return paging_info_dto;
    }
}
