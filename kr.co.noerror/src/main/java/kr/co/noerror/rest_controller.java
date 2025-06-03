package kr.co.noerror;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.noerror.DAO.pchreq_DAO;
import kr.co.noerror.DTO.pchreq_res_DTO;

@RestController
public class rest_controller {

	@Autowired
	pchreq_DAO pdao;
	
	@ResponseBody
	@GetMapping("/purchase_detail_modal.do")
	public List<pchreq_res_DTO> purchase_detail_modal(@RequestParam(name="code") String pch_code) {
			
		List<pchreq_res_DTO> pch_details = this.pdao.purchase_detail(pch_code);
		System.out.println(pch_details);
		
		return pch_details;
		
	}
}
