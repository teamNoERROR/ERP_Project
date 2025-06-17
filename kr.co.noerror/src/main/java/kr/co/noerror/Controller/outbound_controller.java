package kr.co.noerror.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DTO.outbound_DTO;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Service.IOSF_Warehouse_Service;
import kr.co.noerror.Service.outbound_service;

@Controller
public class outbound_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	private outbound_service out_svc;
	
	 @Resource(name="IOSF_Warehouse_Service")
     IOSF_Warehouse_Service iosf_service;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	List<String> list = null; 
	Map<String, String> map = null;
	String url = "";
	String msg = "";
	
	//제품출고 리스트 
//	@GetMapping("")
//	public String outbound(Model m
//						,@RequestParam(value = "keyword", required = false) String keyword
//						,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
//						,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea
//						) {
	
		//리스트 첫접속시 체크박스 상태값
//		if (out_status_lst == null) {
//			out_status_lst = new String[] {"출고요청", "출고완료", "출고취소"};
//		}
		
		//페이징 관련 
//		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, post_ea, outbound_all_list.size());
//		int bno = this.m_pg.serial_no(outbound_all_list.size(), pageno, post_ea); 
		
//		List<outbound_DTO> outbound_all_list = this.out_svc.outbound_all_list(keyword, pageno, post_ea, out_status_lst);  //입고리스트 제품 리스트
//		m.addAttribute("out_statusList", out_status_lst); //체크박스 상태용
//		m.addAttribute("outbound_total",outbound_all_list.size());
//		m.addAttribute("outbound_all_list",outbound_all_list);  //데이터 리스트 
				
//		m.addAttribute("lmenu","입출고관리");
//		m.addAttribute("smenu","제품 출고");
//		m.addAttribute("mmenu","출고 요청");
		
//		m.addAttribute("keyword",keyword);
		
//		m.addAttribute("bno", bno);
//		m.addAttribute("pageinfo", pageinfo);
		
//		return "/inout/outbound_list.html";
//		return "/warehouse/fs_warehouses_list.html";
//	}
	
	//출고등록 화면이동 
	@GetMapping("/outbound_insert.do")
	public String outbound_insert(Model m) {
		m.addAttribute("lmenu","입출고관리");
		m.addAttribute("smenu","자재 출고");
		m.addAttribute("mmenu","출고 등록");
		
		return "/inout/outbound_insert.html";
	}
	
	//출고등록
	@PutMapping("/outbound_insertok.do")
	public String outbound_insertok(@RequestBody String out_pds, HttpServletResponse res) {
		try {
			this.pw = res.getWriter();
			
			String result = this.out_svc.outbnd_insert(out_pds);
			System.out.println(result);
			this.pw.write(result);  
		
		} catch (IOException e) {
			
			this.pw.write("error"); //출고 등록실패
			this.log.error(e.toString());
			e.printStackTrace();
			
		} finally {
			this.pw.close();
		}
		
		return null;
	}
	
	
	
	//출고내역 상세보기 모달
	@GetMapping("/outbnd_detail_modal.do")
	public String inbnd_detail_modal(Model m, @RequestParam("outbnd_code") String ob_code
									, @RequestParam("ord_code") String ord_code
									) {
		
		List<outbound_DTO> outbound_detail = this.out_svc.outbound_detail(ob_code, ord_code);
		
		
		m.addAttribute("outbound_detail", outbound_detail);
//		m.addAttribute("pch_qty_total", pch_qty_total);
//		m.addAttribute("inb_qty_total", inb_qty_total);
//		m.addAttribute("itm_cost_total", itm_cost_total);
//		m.addAttribute("pch_amount_total", pch_amount_total);
//		m.addAttribute("pch_cd", pch_cd);
		
		return "/modals/outbound_detail_modal.html";
	}
	
}
