package kr.co.noerror.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.DAO.goods_DAO;
import kr.co.noerror.DTO.bom_DTO;
import kr.co.noerror.DTO.del_DTO;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.Model.M_file;
import kr.co.noerror.Model.M_paging;
import kr.co.noerror.Model.M_random;
import kr.co.noerror.Service.bom_service;
import kr.co.noerror.Service.goods_service;

@Controller
public class goods_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	private goods_service g_svc;
	
	@Autowired
	private bom_service b_svc; 
	
	@Resource(name="goods_DAO")
	goods_DAO g_dao;
	
	@Resource(name="M_random")  //랜덤숫자생성 모델 
	M_random m_rno;
	
	@Resource(name="M_file")   //파일첨부관련모델 
	M_file m_file;
	
	@Resource(name="M_paging")  //페이징생성 모델 
	M_paging m_pg;
	
	@Resource(name="file_DTO")  //파일첨부DTO
	file_DTO f_dto;
	
	@Resource(name="products_DTO")
	products_DTO p_dto;
	
	@Resource(name="del_DTO")
	del_DTO d_dto;
	
	List<String> list = null; 
	Map<String, String> map = null;
	String url = "";
	String msg = "";
	
	//픔목관리 > 리스트 화면이동 
	@GetMapping({"/goods.do"})
	public String products_list(Model m, @RequestParam(value = "type", required = false) String type
								,@RequestParam(value = "keyword", required = false) String keyword
								,@RequestParam(value = "products_class2", required = false) String sclass
								,@RequestParam(value="pageno", defaultValue="1", required=false) Integer pageno
								,@RequestParam(value="post_ea", defaultValue="5", required=false) int post_ea ) {
		
		int goods_total_sch = this.g_svc.gd_all_ea_sch(type, sclass, keyword); //제품 총개수
		List<products_DTO> goods_all_list_sch = this.g_svc.gd_all_list_sch(type, sclass, keyword, pageno, post_ea);  //제품 리스트
		
		//페이징 관련 
		int pea = post_ea; 
		Map<String, Integer> pageinfo = this.m_pg.page_ea(pageno, pea, goods_total_sch);
		int bno = this.m_pg.serial_no(goods_total_sch, pageno, pea); 
		
		//제품타입에 따른 url 분류 
		if("product".equals(type) || type==null) {
			m.addAttribute("mmenu","완제품 리스트");
			this.url = "/goods/products_list.html";
			
		}else if("item".equals(type)) {
			m.addAttribute("mmenu","부자재 리스트");
			this.url = "/goods/items_list.html";
			
		}
		
		//리스트의 분류검색 리스트용 
		JSONArray lc_list = this.g_svc.gd_class(type);  //대분류목록
		this.list = new ArrayList<>();
		for (int i = 0; i < lc_list.length(); i++) {
			this.list.add(lc_list.getString(i));
		}
		
		if(sclass!=null) {
			String lclass_ck = this.g_svc.lclass_ck(sclass);
			m.addAttribute("lclass_ck",lclass_ck);
			m.addAttribute("sclass",sclass);

			JSONArray sc_list = this.g_svc.sc_class(type, lclass_ck);  //소분류 목록
			List<String> slist = new ArrayList<>();
			for (int i = 0; i < sc_list.length(); i++) {
				slist.add(sc_list.getString(i));
			}
			m.addAttribute("slist",slist);
		}
		
		//페이지로 보낼 것들 
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		
		m.addAttribute("keyword",keyword);
		m.addAttribute("lclass",this.list);
		
		m.addAttribute("bno", bno);
		m.addAttribute("no_goods", "등록된 제품이 없습니다");
		m.addAttribute("goods_total", goods_total_sch);
		m.addAttribute("goods_all_list", goods_all_list_sch);
		
		m.addAttribute("pageinfo", pageinfo);
		m.addAttribute("pageno", pageno);
		m.addAttribute("pea", pea);
		
		return this.url;
	}


	//완제품 등록하기 화면이동 
	@GetMapping("/products_insert.do")
	public String products_insert(Model m) throws IOException {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","제품 등록");
		
		return "/goods/products_insert.html";
	}
	
	//부자재 등록하기 화면이동 
	@GetMapping("/items_insert.do")
	public String goods_insert(Model m) throws IOException {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","부자재 등록");
		
		return "/goods/items_insert.html";
	}
	
	//제품유형 선택시 
	@GetMapping("/goods_type.do")
	public String goods_type(HttpServletResponse res
							, @RequestParam("type") String goods_type
							, @RequestParam(value="products_class1", required=false) String products_class1) throws IOException {
		this.pw = res.getWriter();
		try {
			JSONArray lc_list = this.g_svc.gd_class(goods_type); //완제품 식품 대분류 목록 가져오기 
			this.pw.print(lc_list);
			
		} catch (Exception e) {
			this.pw.print("error");
			
		} finally {
			this.pw.close();
		}
		return null;
	}
	
	
	//소분류리스트 전달
	@GetMapping("/goods_class.do")
	public String pd_sclass(HttpServletResponse res,  
							@RequestParam("type") String goods_type,
							@RequestParam("products_class1") String products_class1) throws IOException {
		this.pw = res.getWriter();
		try {
			JSONArray sc_list = this.g_svc.sc_class(goods_type, products_class1);  //소분류 가져오기
			this.pw.print(sc_list);
			
		} catch (Exception e) {
			this.pw.print("error");
			
		} finally {
			this.pw.close();
		}
		return null;
	}
	
	
	//제품 등록 (완제품)
	@PostMapping("/products_insertok.do")
	public String products_insertok(@ModelAttribute products_DTO pdto,
									@RequestParam(value = "productImage", required = false) MultipartFile productImage,
									@RequestParam(name = "url", required = false) String url,
									HttpServletResponse res) {
		System.out.println("이미지1 : "+productImage);
		System.out.println("pdto1 : "+pdto);
		try {
			this.pw = res.getWriter();
			
			int result = this.g_svc.pd_insert(pdto, productImage, url);   //db에 데이터 저장 
			if(result > 0) {  
				this.pw.write("ok");  //제품 등록 완료 
			}
			else {
				this.pw.write("fail"); //제품 등록실패
			}
			
		} catch (IOException e) {
			this.pw.write("error"); //제품 등록실패
			this.log.error(e.toString());
			e.printStackTrace();
			
		} finally {
			this.pw.close();
		}
		
		return null;
	}
	
	//제품 등록 (부자재)
	@PostMapping("/items_insertok.do")
	public String items_insertok(@ModelAttribute products_DTO pdto,
									@RequestParam(value = "productImage", required = false) MultipartFile productImage,
									@RequestParam(name = "url", required = false) String url,
									HttpServletResponse res) {
		
		System.out.println("이미지 : "+productImage);
		try {
			this.pw = res.getWriter();
			
			int result = this.g_svc.itm_insert(pdto, productImage, url);   //db에 데이터 저장
			if(result > 0) {  
				this.pw.write("ok");  //제품 등록 완료 
			}
			else {
				this.pw.write("fail"); //제품 등록실패
			}
			
		} catch (IOException e) {
			this.pw.write("ee"); //제품 등록실패
			this.log.error(e.toString());
			e.printStackTrace();
			
		} finally {
			this.pw.close();
		}
		
		return null;
	}

	
	//품목 상세보기 모달 
	@PostMapping("/goods_detail.do")
	public String goods_detail(Model m, @RequestParam("pd_code") String pd_code,  @RequestParam("type") String type) {
		System.out.println(pd_code);
		System.out.println(type);
		
		products_DTO goods_one = this.g_svc.pd_one_detail(pd_code, type);  //특정게시물 내용 가져오기
		List<bom_DTO> resultlist = this.b_svc.bom_detail(pd_code);  //bom상세보기 클릭시
		if(goods_one == null) {
			this.msg = "alert('시스템문제로 해당 제품의 상세페이지를 불러올 수 없습니다.');"
					+ "history.go(-1);";
			m.addAttribute("msg", msg);
			this.url = "WEB-INF/views/message";
			
		}else {
			if("product".equals(type)) {
				m.addAttribute("goods_one", goods_one);
				if(!resultlist.isEmpty()) {
					m.addAttribute("top_pd", resultlist.get(0).getPRODUCT_NAME());
					m.addAttribute("bom_result", resultlist);
				}
				this.url = "/modals/product_detail_modal.html";
				
			}else if("item".equals(type)) {
				
				m.addAttribute("goods_one", goods_one);
				this.url = "/modals/item_detail_modal.html";
			}
			
		}
		return this.url;
	}

	
	//제품 삭제 
	@DeleteMapping("/goods_delete.do/{key}")
	public String goods_delete(@PathVariable(name="key") String key,
								@RequestBody String data,
								HttpServletRequest req, HttpServletResponse res) throws IOException {
		System.out.println(data);
		this.pw = res.getWriter();
		
		try {
			JSONArray ja = new JSONArray(data);
			int data_ea = ja.length();
			int count = 0;
			int result = 0;
			
			for (int w = 0; w < data_ea; w++) {
			    JSONObject jo = ja.getJSONObject(w);
			    
			    String del_key = jo.getString("type")+"_del";
			    this.d_dto.setIdx(jo.getInt("idx"));
			    this.d_dto.setCode(jo.getString("code"));
			    this.d_dto.setType(jo.getString("type"));
			    
				
				if(key.equals(del_key)) {
	//				products_DTO goods_one = this.g_svc.pd_one_detail(pd_code);  //특정게시물 내용 가져오기(이미지 삭제용)
					if("bom".equals(jo.getString("type"))) {
						
						 this.d_dto.setCode2(jo.getString("code2"));
						result= this.b_svc.bom_delete(this.d_dto);
						
					}else {
						result =  this.g_svc.pd_delete(this.d_dto);
						if(result >= 1) {
							count++;
						}
						
					}
				}else {
					this.pw.write("key error");
				}
			}
			if(data_ea == count ) {  //모든 글 삭제 완료 
				this.pw.write("ok");
				
			}else {  //전체 삭제실패 
				this.pw.write("fail");
			}
		} catch (Exception e) {
			this.log.error(e.toString());
			e.printStackTrace();
		}
		
		return null;
	}
	

	
	
}
