package kr.co.noerror;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.noerror.DAO.pchreq_DAO;
import kr.co.noerror.DTO.pchreq_res_DTO;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class rest_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	pchreq_DAO pdao;
	
	
	//이미지 전송 
	@ResponseBody
	@GetMapping("/img_attach/{filenm}")
	public byte[] cdn_listapi(@PathVariable String filenm) {
	
		byte[] img = null; //FE에게 CDN경로 이미지명을 전송 
		String img_url = null; 
		
		if("imgfile".equals(filenm)) {  
		//파라미터값에 맞는 DB에 정보를 확인 후 해당 정보가 있을 경우 DB에 저장된 경로를 변수에 저장 
			
			//img_url = "http://210.178.108.186:8080/noerror/imgfile/"+filenm;
			
			try {
				URL url = new URL(img_url);
				
				HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
				
				InputStream is = httpcon.getInputStream();  //해당 이미지를 바이트로 가져옴 
				img = IOUtils.toByteArray(is);  //byte변수에 가져온 이미지 전체를 저장 
				
				is.close();
				httpcon.disconnect();
						
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}else {
			this.log.info("해당경로에 대한 사항이 없습니다.");
		}
		return img;   
	}
}
