package kr.co.noerror;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.noerror.DAO.pchreq_DAO;
import kr.co.noerror.Service.goods_service;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class rest_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	pchreq_DAO pdao;
	
	@Autowired
	private goods_service g_svc;
	
	//이미지 전송 
	@ResponseBody
	@GetMapping("/imgfile/{filenm}")
	public byte[] cdn_listapi(@PathVariable(name="filenm") String filenm) {
	
		byte[] img = null; //FE에게 CDN경로 이미지명을 전송 
		String img_url = null; 
		
			
		if(!filenm.equals(null) || !filenm.equals("")) {
			img_url = "http://210.178.108.186:83/imgfile/";
			String cdn_imgs = this.g_svc.imgs_attach(filenm); //cdn 이미지 url 가져오기
			try {
				URL url = new URL(img_url+cdn_imgs);
				
				HttpURLConnection hc = (HttpURLConnection)url.openConnection();
				
				InputStream is = hc.getInputStream();  
				img = IOUtils.toByteArray(is);  
				
				is.close();
				hc.disconnect();
				
			} catch (Exception e) {
				this.log.error("error : "+e);
			}
			
		}else {
			this.log.info("해당경로에 대한 사항이 없습니다.");
		}
			
		return img;   
	}
	
	
	
	
	
}
