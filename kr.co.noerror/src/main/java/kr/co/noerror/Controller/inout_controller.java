package kr.co.noerror.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DAO.pchreq_DAO;
import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.inout_DTO;
import kr.co.noerror.DTO.pchreq_res_DTO;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Service.inout_service;

@Controller
public class inout_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	private inout_service io_svc;
	
	@Autowired
	pchreq_DAO pdao;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	@Resource(name="inout_DTO")
	inout_DTO io_dto;
	
	List<String> list = null; 
	Map<String, String> map = null;
	String url = "";
	String msg = "";
	
	//자재입고 리스트 
	@GetMapping("/inbound.do")
	public String inbound(Model m
						,@RequestParam(value = "keyword", required = false) String keyword
						,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
						,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea
						,@RequestParam(value="status_lst", required=false) String[] status_lst
						) {
		//리스트 첫접속시 체크박스 상태값
		if (status_lst == null) {
			status_lst = new String[] {"가입고", "입고완료", "입고취소"};
		}
		
		List<inout_DTO> inbound_all_list = this.io_svc.inbound_all_list(keyword, pageno, post_ea, status_lst);  //입고리스트 제품 리스트
		int inbound_total = this.io_svc.inbound_total(keyword, status_lst); //입고리스트 제품 총개수
		
		//페이징 관련 
		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, post_ea, inbound_total);
		int bno = this.m_pg.serial_no(inbound_total, pageno, post_ea); 
		
		m.addAttribute("lmenu","입출고관리");
		m.addAttribute("smenu","자재 입고");
		m.addAttribute("mmenu","입고 리스트");
		
		m.addAttribute("keyword",keyword);
		m.addAttribute("bno", bno);
		m.addAttribute("inbound_total",inbound_total);
		m.addAttribute("inbound_all_list",inbound_all_list);  //데이터 리스트 
		m.addAttribute("statusList", status_lst); //체크박스 상태용
		m.addAttribute("pageinfo", pageinfo);
		m.addAttribute("pageno", pageno);
		
		return "/inout/inbound_list.html";
	}
	
	//자재입고등록 화면이동 
	@GetMapping("/inbound_insert.do")
	public String inbound_insert(Model m) {
		m.addAttribute("lmenu","입출고관리");
		m.addAttribute("smenu","자재 입고");
		m.addAttribute("mmenu","입고 등록");
		
		return "/inout/inbound_insert.html";
	}
	
	//자재입고등록
	@PutMapping("/inbound_insertok.do")
	public String inbound_insertok(@RequestBody String inbnd_item, HttpServletResponse res) {
		try {
			this.pw = res.getWriter();
			
			int result = this.io_svc.inbnd_insert(inbnd_item); 
			if(result > 0) {
				this.pw.write("ok");  //입고 등록 완료 
				
			}else {
				this.pw.write("fail"); //입고 등록실패
			}
			
		} catch (IOException e) {
			this.pw.write("error"); //입고 등록실패
			this.log.error(e.toString());
			e.printStackTrace();
			
		} finally {
			this.pw.close();
		}
		
		return null;
	}
	
	
	//입고내역 상세보기 모달
	@GetMapping("/inbnd_detail_modal.do")
	public String inbnd_detail_modal(Model m, @RequestParam("inbnd_code") String inbnd_code
								, @RequestParam("pch_code") String pch_cd
								, @RequestParam("ori_pcd") String ori_pcd) {
		
		List<inout_DTO> inbound_detail = this.io_svc.inbound_detail(inbnd_code, ori_pcd);
		String ind_pchcd="";
		int pch_qty_total=0;  //총 주문수량 
		int inb_qty_total=0;  //총 입고수량 
		int itm_cost_total=0;  //총 제품 단가
		int pch_amount_total=0;  //총 구매금액
		
		for(inout_DTO sum : inbound_detail) {
			pch_qty_total += sum.getP_QTY();        // P_QTY 누적
		    inb_qty_total += sum.getITEM_QTY();     // ITEM_QTY 누적
		    itm_cost_total += sum.getITEM_COST();   // ITEM_COST 누적 (단가 총합이 맞는지 확인 필요)
		    pch_amount_total += sum.getPAY_AMOUNT(); // PAY_AMOUNT 누적
		    
		    ind_pchcd += sum.getIND_PCH_CD();
		}
		System.out.println(ind_pchcd);
		
		m.addAttribute("inbnd_detail", inbound_detail);
		m.addAttribute("pch_qty_total", pch_qty_total);
		m.addAttribute("inb_qty_total", inb_qty_total);
		m.addAttribute("itm_cost_total", itm_cost_total);
		m.addAttribute("pch_amount_total", pch_amount_total);
		m.addAttribute("pch_cd", pch_cd);
		
		return "/modals/inbound_detail_modal.html";
	}
	
	
	//입고상태변경처리
	@PatchMapping("/inbnd_ok.do")
	public String inbnd_ok( @RequestBody String inbnd_data, HttpServletResponse res) throws IOException {
		try {
			this.pw = res.getWriter();
			
			JSONArray ja = new JSONArray(inbnd_data);
			int data_ea = ja.length();
			
			 Map<String, Integer> status = this.io_svc.in_status_ck(inbnd_data);  //기존 입고처리 유무 확인 
			if(status.get("aleady_count")>0) {  //기존처리건이 있으면 
				this.pw.write("기존 처리된 건이"+status.get("aleady_count")+ "개 있습니다.");
				
			}else if(data_ea == status.get("updated")) {
				this.pw.write("ok");
				
			}else {
				this.pw.write("fail");
			}
			
		} catch (Exception e) {
			this.pw.write("error");
			this.log.error(e.toString());
			e.printStackTrace();
			
		} finally {
			this.pw.close();
		}
		
		return null;
	}
	


}
