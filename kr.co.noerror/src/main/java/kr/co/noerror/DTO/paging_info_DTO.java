package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("paging_info_DTO")
public class paging_info_DTO {
    private int page_no;  //클릭한 페이지 수 
    private int page_size;  //한번에 보이는 게시물 개수 
    private int total_count;  //검색 후 게시물 총개수 
    private int start;  //
    private int end;
    private int page_cnt;  //페이지박스 개수
    private int start_page;  //보이는 페이징의  첫페이지
    private int end_page;	 //보이는 페이징의 마지막
}
