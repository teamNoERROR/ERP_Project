package kr.co.noerror.DTO;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("search_condition_DTO")
public class search_condition_DTO {
	    private String search_word = "";
	    private List<String> statuses;
	    private int page_no = 1;
	    private int page_size = 5;
}
