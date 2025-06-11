package kr.co.noerror.Controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.noerror.Service.stock_service;

@Controller
public class stock_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	@Autowired
	stock_service st_svc; 
	
	
	//입고창고 + 부자재창고 아이템별 재고수
	@GetMapping("/item_stock.do")
	public String item_stock(HttpServletResponse res
							, @RequestParam("") String item_code 
							) throws IOException {
		this.pw = res.getWriter();
		try {
			int result = this.st_svc.ind_item_stock(item_code);
			this.pw.print(result);
			
		} catch (Exception e) {
			this.pw.print("error");
			
		} finally {
			this.pw.close();
		}
		return null;
	}
	

}
