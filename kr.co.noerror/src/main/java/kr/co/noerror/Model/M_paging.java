package kr.co.noerror.Model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("M_paging")
public class M_paging {
	int startp = 0; //limit시작번호 
	int clickPage = 0;  //사용자가 클릭한 페이지번호에 맞는 게시글 순차번호 계산값
	
	//게시물 일련번호 계산
	public int serial_no(int pageno, Integer post_ea)  {
		
		if(pageno == 1) {
			this.clickPage = 0;
		}else {  //1외의 다른 페이지를 클릭시
			this.clickPage = (pageno -1) * post_ea;
		}
		
		return this.clickPage;
	}
	
	
	//쿼리문 limit에 값 전달
	public Map<String, Object> paging (Integer pageno, Integer post_ea)  {
		/* 1p 클릭 => limit 0,10
		   2p 클릭 => limit 10,10
		   3p 클릭 => limit 20,10
		   ...
		*/
		this.startp = (pageno-1)* post_ea;  //limit 시작번호
		
		Map<String, Object> page = new HashMap<String, Object>(); 
		page.put("start_p" , this.startp);  //limit의 시작번호 
		page.put("post_ea" , post_ea);  //limit의 두번째 번호
		
		return page;
	}
	
	//페이지개수
	public Map<String, Object> page_ea (Integer pageno, Integer post_ea, Integer total_post)  {
		//pageno : 현재 페이지 번호 , post_ea :한페이지당 보여줄 게시물 개수 , total_post : 전체게시물 개수  
		
		int page_ea = 3; //한페이지에 보여줄 페이징의 갯수
		int page_ea_total;  //총 페이지 개수 
		int start_pg;
		int end_pg;
//		int start;
//		int end;
		int pgidx;
		
		page_ea_total = (int) Math.ceil((double) total_post / post_ea); 
		pgidx = total_post / post_ea + (1-(total_post/post_ea)%1)%1;
		
		//페이징 박스 개수 1~3, 4~6, 7~
		start_pg = (((pageno -1) / page_ea)* page_ea )+1;
		end_pg = Math.min(start_pg+(page_ea - 1), page_ea_total);	
				
		//게시물 시작 번호 ~ 끝번호  
		
		
		Map<String, Object> pageinfo = new HashMap<String, Object>(); 
		pageinfo.put("page_ea_total" , page_ea_total);  
		pageinfo.put("page_ea" , page_ea);  
		pageinfo.put("start_pg" , start_pg);  
		pageinfo.put("page_ea" , page_ea);  
//		pageinfo.put("start" , start);  
//		pageinfo.put("end" , end); 
		pageinfo.put("pgidx" , pgidx);
		
		return pageinfo;
	}
}
