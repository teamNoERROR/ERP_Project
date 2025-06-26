package kr.co.noerror.Model;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.client_DAO;
import kr.co.noerror.DTO.del_DTO;
import kr.co.noerror.DTO.file_DTO;

@Repository("M_file")
public class M_file {
	Logger log = LoggerFactory.getLogger(this.getClass());
//	
//	@Autowired
//	goods_serviceImpl g_svc;

	@Resource(name="client_DAO")
	client_DAO clt_dao;
	
	@Resource(name="file_DTO") 
	file_DTO f_dto;
	
	@Resource(name="del_DTO") 
	del_DTO del_dto;
	
	String file_rnm = null;
	
	final String host = "noerror.nnyong.world";
	final String user="erp";  
	final String pass="pPinyong0413";
	final int port = 9021;
	
	FTPClient fc = null;
	FTPClientConfig fcc = null;

	boolean result = false;   //ftp전송 결과값 true : 정상업로드 / false : 오류빌생 
	
	
	
	public boolean cdn_filesave(file_DTO f_dto, MultipartFile image_file) throws IOException {
		
		try {
				this.file_rnm = this.file_rename(image_file.getOriginalFilename());  //원래파일명 리네임메소드에 전달 
				
				this.fc = new FTPClient();  //FTP 서버에 접속하는 역할의 라이브러리 호출. cdn서버 연결 역할 
				this.fc.setControlEncoding("utf-8");  //cdn연결 후 파일전송시 한글파일명 깨짐 방지 
				
				this.fcc = new FTPClientConfig(); //FTP접속 클라인언트 객체 생성(ftp 접속정보를 배열로 저장)  
			
				
				this.fc.configure(fcc);
				this.fc.connect(this.host , this.port);  //ftp 연결 
				
				if(this.fc.login(this.user, this.pass)) {  //ftp로그인
					
					this.fc.enterLocalPassiveMode();
					this.fc.setFileType(FTP.BINARY_FILE_TYPE);  //바이너리인지 타입만 확인 (이미지, 동영상, ZIP, PDF..)
					this.result = this.fc.storeFile("/www/tomcat-10.1.41/webapps/ROOT/imgfile/"+this.file_rnm, image_file.getInputStream());  //ftp디렉토리 경로 설정 후 해당 파일을 byte로 전송
					//파일 먼저 저장 후 DB저장
					//파일저장되면 여기서  this.result = true가 됨
					
					//file_DTO에 이미지명 정보 전달 
					f_dto.setFilenm(image_file.getOriginalFilename());  //원래파일명
					f_dto.setFileRenm(this.file_rnm);  //파일리네임
					
					String api_nm[] = this.file_rnm.split("[.]");
					f_dto.setApinm(api_nm[0]);  //api네임
					
					//String api_url = "http://"+this.url+"/imgfile/"+this.file_rnm;
					//f_dto.setImgPath(api_url);  //이미지경로
					
					/*
					File dir = new File("http://"+this.url+"/noerror/imgfile/");		
					if (!dir.exists()) { //해당 경로가 없을경우
						dir.mkdirs(); // 폴더 생성
					}
					*/
				}else {
					this.log.info("파일전송에 대해 오류가 발생했습니다.");
					this.result = false;
				}
		
			} catch (Exception e) {
				this.log.error(e.toString());
				
			} finally {
				 if (this.fc != null && this.fc.isConnected()) {
					 this.fc.disconnect();  //ftp 접속 해제 
				 }
				
			}
		return this.result;
	}
	
		
	
	
	//cdn서버에 있는 해당 파일을 삭제하는 메소드 
	public boolean cdn_ftpdel(String fileYes) throws Exception {
		this.fc = new FTPClient();
		this.fcc = new FTPClientConfig();
		this.fc.configure(fcc);  
		this.fc.connect(this.host,this.port);  //연결 실행 
		this.fc.enterLocalPassiveMode();  //PassiveMode(전송모드)로 세팅(FTP접속환경)
	
		if(this.fc.login(this.user, this.pass)) {
			String filesize = this.fc.getSize("/imgfile/"+fileYes);
			if(filesize != null) {  //ftp에 파일이 있을 때 
				
				this.result = this.fc.deleteFile("/imgfile/"+fileYes);  //FTP에 있는 해당 파일명의 파일 삭제 
				//파일 삭제 성공시 result == true
				
			}else {  //ftp에 파일이 없을 때 
				this.result = true; //그래도 db에서 삭제 진행 
			}
		}
		return this.result;
		
	}
	
	
//	public file_DTO filesave(file_DTO f_dto, MultipartFile image_file, HttpServletRequest req, String savePath) throws IOException {
//		this.file_rnm = this.file_rename(image_file.getOriginalFilename());  //원래파일명 리네임메소드에 전달 
//		
//		f_dto.setFilenm(image_file.getOriginalFilename());  // 원래파일명 dto에 저장 
//		f_dto.setFileRenm(this.file_rnm);  //새로 붙인 파일명 dto에 저장  
//		
//		String filePath = req.getServletContext().getRealPath(savePath);
//		System.out.println(filePath);
//		
//		File dir = new File(filePath);		
//		if (!dir.exists()) { //해당 경로가 없을경우
//		    dir.mkdirs(); // 폴더 생성
//		}
//		
//		FileCopyUtils.copy(image_file.getBytes(),new File(filePath+this.file_rnm));  //웹디렉톨에 리네임한 파일명으로 저장 
//		f_dto.setImgPath(savePath+this.file_rnm);  //웹디렉토리 경로+리네임 파일명 dto에 저장
//		
//		return f_dto;
//	}
	
	
	//파일 리네임 메소드
	public String file_rename(String filenm) {
		//속성(파일확장자)
		int sub = filenm.lastIndexOf(".");  
		String ext = filenm.substring(sub);  
	
		Date date = new Date();  
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String today = sf.format(date);
		
		int no = (int)(Math.random()*90000)+10000; 
		String makeFileRenm = today+no+ext;

		return makeFileRenm;
	}
	
	

}