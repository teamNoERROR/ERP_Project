package kr.co.noerror.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
import kr.co.noerror.DTO.del_DTO;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.DTO.products_DTO;
import kr.co.noerror.Model.M_file;
import kr.co.noerror.Model.M_random;
import kr.co.noerror.Service.goods_service;

@Controller
public class goods_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	goods_service g_svc;
	
	@Resource(name="goods_DAO")
	goods_DAO g_dao;
	
	@Resource(name="M_random")  //랜덤숫자생성 모델 
	M_random m_rno;
	
	@Resource(name="M_file")   //파일첨부관련모델 
	M_file m_file;
	
	@Resource(name="file_DTO")  //파일첨부DTO
	file_DTO f_dto;
	
	List<String> list = null; 
	Map<String, String> map = null;

	
	//픔목관리 > 완제품리스트 화면이동 
	@GetMapping("/goods.do")
	public String products_list(Model m) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","완제품 리스트");
		
		int goods_total =  this.g_svc.pd_all_ea();   //제품 총개수 
		List<products_DTO> goods_all_list = this.g_svc.pd_all_list();  //제품 리스트 출력 
		
		String no_goods = "등록된 제품이 없습니다";
//		if(goods_all_list==null) {
			m.addAttribute("no_goods", no_goods );
			
//		}else {
			m.addAttribute("goods_all_list", goods_all_list);
			m.addAttribute("goods_total", goods_total);
//		}
		
		return "/goods/products_list.html";
	}
	
	
	
	//제품 등록하기 화면이동 
	@GetMapping("/products_insert.do")
	public String goods_insert(Model m) throws IOException {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","품목 등록");
		
		this.list = new ArrayList<String>();  //완제품 식품분류 목록 가져오기 
		this.list = this.g_svc.pd_class_list(null);
		m.addAttribute("l_class",this.list);
		
		return "/goods/products_insert.html";
	}

	//소분류리스트 전달 api
	@GetMapping("/goods_class.do")
	public String pd_sclass(HttpServletResponse res, @RequestParam("products_class1") String products_class1) throws IOException {
		this.pw = res.getWriter();
		
		try {
			List<String> s_class = this.g_svc.pd_class_list(products_class1);
			
			JSONArray sc_list = new JSONArray();
			for(String a : s_class) {
				sc_list.put(a);
			}
			this.pw.print(sc_list);
			
		} catch (Exception e) {
			this.pw.print("error");
			
		} finally {
			this.pw.close();
		}
		return null;
	}
	
	
	//완제품 등록 
	@PostMapping("/products_insertok.do")
	public String products_insertok(@ModelAttribute products_DTO pdto,
									@RequestParam(value = "productImage", required = false) MultipartFile productImage,
									@RequestParam(name = "url", required = false) String url,
									HttpServletResponse res) {
		
		System.out.println("이미지 : "+productImage);
		
		try {
			this.pw = res.getWriter();
			
			String pd_code = this.m_rno.random_no();
			pdto.setPRODUCT_CODE(pd_code);  //제품코드 장착 
			
			
			boolean fileattach = this.m_file.cdn_filesave(this.f_dto, productImage, url);  
			if(fileattach == true) {  //FTP에 파일저장 완료 후 
				//dto에 파일명 장착
				pdto.setFILE_NM(this.f_dto.getFilenm());
				pdto.setFILE_RENM(this.f_dto.getFileRenm());
				pdto.setAPI_FNM(this.f_dto.getApinm());
				pdto.setIMG_SRC(this.f_dto.getImgPath());
				
				int result = this.g_svc.pd_insert(pdto);   //db에 데이터 저장 
				if(result > 0) {  
					this.pw.write("ok");  //제품 등록 완료 
				}
				else {
					this.pw.write("fail"); //제품 등록실패
				}
			}else {
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
	public String goods_detail(Model m, @RequestParam("pd_code") String pd_code) {
		products_DTO goods_one = this.g_svc.pd_one_detail(pd_code);  //특정게시물 내용 가져오기
		
		if(goods_one == null) {
			m.addAttribute("ssss", "ssss");
			
		}else {
			m.addAttribute("goods_one", goods_one);
		}
		return "/modals/goods_detail_modal.html";
	}
	
	
	//제품 삭제 
	@DeleteMapping("/goods_delete.do/{key}")
	public String goods_delete(@PathVariable(name="key") String key,
								@RequestBody del_DTO d_dto,
								HttpServletRequest req, HttpServletResponse res) throws IOException {
		this.pw = res.getWriter();
		
		try {
			String del_key = d_dto.getIdx()+"_del";
			String pd_code = d_dto.getCode();
			int pidx = d_dto.getIdx();
			
			if(key.equals(del_key)) {
//				products_DTO goods_one = this.g_svc.pd_one_detail(pd_code);  //특정게시물 내용 가져오기(이미지 삭제용)

				int result =  this.g_svc.pd_delete(pidx, pd_code);
				if(result > 0) {  //글삭제 완료 
					this.pw.write("ok");
					
				}else {  //삭제실패 
					this.pw.write("fail");
				}
			}
			else {
				this.pw.write("key error");
			}
			
		} catch (Exception e) {
			this.log.error(e.toString());
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	
//	@GetMapping("/goods_detail.do")
//	public String goods_detail_get() {
//	    return "redirect:/"; // 또는 빈 페이지, 에러 페이지
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//자재 등록하기 화면이동 
	@GetMapping("/items_insert.do")
	public String items_insert(Model m, @RequestParam(value="goods_class1", required=false) String goods_class1) {
		m.addAttribute("lmenu","기준정보관리");
		m.addAttribute("smenu","품목 관리");
		m.addAttribute("mmenu","자재 등록");
		
		
//		System.out.println("goods_class1 : " + goods_class1);
		
//		this.list = new ArrayList<String>();  //완제품 식품분류 목록 가져오기 
//		this.list = this.g_svc.pd_class_list(goods_class1);
//		m.addAttribute("l_class",this.list);
//		System.out.println(this.list);
//			JSONArray sc_list = new JSONArray();
//			for(String a : this.list ) {
//				sc_list.put(a);
//			}
//			m.addAttribute("s_class",this.list);
		
		return "/goods/items_insert.html";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
