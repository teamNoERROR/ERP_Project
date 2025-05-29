package kr.co.noerror.Model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("M_paging")
public class M_paging {
	int clickPage = 0;  //사용자가 클릭한 페이지번호에 맞는 게시글 순차번호 계산값
	int bno = 0;
	//게시물 일련번호 계산
	public int serial_no(int goods_total_sch, int pageno, Integer post_ea)  {
		
		if(pageno == 1) {
			this.clickPage = 0;
		}else {  //1외의 다른 페이지를 클릭시
			this.clickPage = (pageno -1) * post_ea;
		}
		this.bno = goods_total_sch - this.clickPage;
		return this.bno;
	}
	
	

	//페이지개수
	public Map<String, Integer> page_ea (Integer pageno, Integer post_ea, Integer total_post)  {
		//pageno : 현재 페이지 번호 , post_ea :한페이지당 보여줄 게시물 개수 , total_post : 전체게시물 개수  
		
		int page_ea = 3; //한페이지에 보여줄 페이지 박스의 갯수
		int page_ea_total;  //총 페이지 개수 
		int start_pg;
		int end_pg;
//		int pgidx;
		
		page_ea_total = (int) Math.ceil((double) total_post / post_ea); 
//		pgidx = total_post / post_ea + (1-(total_post/post_ea)%1)%1;
		
		//페이징 박스 개수 1~3, 4~6, 7~
		start_pg = (((pageno -1) / page_ea)* page_ea )+1;
		end_pg = Math.min(start_pg + (page_ea - 1), page_ea_total);	
				
		
		Map<String, Integer> pageinfo = new HashMap<String, Integer>();
		if (total_post == 0) {
		    pageinfo.put("start_pg", 1);
		    pageinfo.put("end_pg", 1);
		}else {
			pageinfo.put("start_pg" , start_pg);  
			pageinfo.put("end_pg" , end_pg);  
		}
		pageinfo.put("pageno" , pageno);
		pageinfo.put("page_ea" , page_ea);  
		pageinfo.put("page_ea_total" , page_ea_total);  
//		pageinfo.put("pgidx" , pgidx);
		
		return pageinfo;
	}
}
