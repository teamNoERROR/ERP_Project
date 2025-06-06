package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("paging_info_DTO")
public class paging_info_DTO {
    private int page_no;
    private int page_size;
    private int total_count;
    private int start;
    private int end;
    private int page_cnt;
    private int start_page;
    private int end_page;	
}
