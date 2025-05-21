package kr.co.noerror.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.noerror.DAO.order_DAO;
import kr.co.noerror.DTO.order_DTO;

@Controller
public class order_controller {
	
	private static final int page_ea = 5; //한페이지당 5개씩 출력
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	order_DAO odao;


	@GetMapping("/order.do")
	public String order(Model m,
			@RequestParam(name="search_column", defaultValue="ORDER_CODE", required=false) String search_column,
			@RequestParam(name="search_word", defaultValue="", required=false) String search_word,
			@RequestParam(name="pageno", defaultValue="1", required=false) Integer pageno
			) {
		
		int data_cnt = this.odao.order_count();
		int page_cnt = data_cnt / 5 + (1-((data_cnt/5)%1))%1;
		
		int start = (pageno - 1) * page_ea;  //oracle에서 해당페이지의 시작 번호
		int end = pageno * page_ea + 1;      //oracle에서 해당페이지의 종료 번호 + 1
		
		Map<String, Object> mparam = new HashMap<>();
		mparam.put("scolumn", search_column);  //검색 컬럼
		mparam.put("sword", search_word);  //검색어
		mparam.put("start", start);        //oracle 시작행 번호
		mparam.put("end", end);            //oracle 종료행 번호
		
		List<order_DTO> all = this.odao.order_list(mparam);

		m.addAttribute("lmenu","구매영업관리");
		m.addAttribute("smenu","주문관리");
		m.addAttribute("all", all);
		m.addAttribute("page_cnt", page_cnt);
		m.addAttribute("pageno", pageno); 
		
		return "/production/order_list.html";
	}
}
