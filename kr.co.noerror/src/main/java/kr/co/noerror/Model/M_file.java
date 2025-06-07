package kr.co.noerror.Model;

import java.io.File;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.noerror.DTO.file_DTO;
import kr.co.noerror.Service.goods_serviceImpl;

@Repository("M_file")
public class M_file {
	Logger log = LoggerFactory.getLogger(this.getClass());
//	
//	@Autowired
//	goods_serviceImpl g_svc;
//	
	@Resource(name="file_DTO") 
	file_DTO f_dto;
	
	String file_rnm = null;
	
	final String host = "210.178.108.186";
	final String user="noerror";  
	final String pass="noerror250513";
	final int port = 60022;
	final String url = "210.178.108.186:8081";
	
	FTPClient fc = null;
	FTPClientConfig fcc = null;

	boolean result = false;   //ftp전송 결과값 true : 정상업로드 / false : 오류빌생 
	
	
	/*
	public boolean cdn_filesave(file_DTO f_dto, MultipartFile image_file) throws IOException {
		
		if(image_file != null) {  //첨부파일이 있을경우 
		try {
				this.file_rnm = this.file_rename(image_file.getOriginalFilename());  //원래파일명 리네임메소드에 전달 
				System.out.println("this.file_rnm" + this.file_rnm);
				
				this.fc = new FTPClient();  //FTP 서버에 접속하는 역할의 라이브러리 호출. cdn서버 연결 역할 
				this.fc.setControlEncoding("utf-8");  //cdn연결 후 파일전송시 한글파일명 깨짐 방지 
				
				this.fcc = new FTPClientConfig(); //FTP접속 클라인언트 객체 생성(ftp 접속정보를 배열로 저장)  
				System.out.println("this.fc" + this.fc);
				System.out.println("this.fcc" + this.fcc);
				this.fc.configure(fcc);
				this.fc.connect(this.url , this.port);  //ftp 연결 
				
				if(this.fc.login(this.user, this.pass)) {  //ftp로그인
					this.fc.setFileType(FTP.BINARY_FILE_TYPE);  //바이너리인지 타입만 확인 (이미지, 동영상, ZIP, PDF..)
					this.result = this.fc.storeFile("/imgfile/"+this.file_rnm, image_file.getInputStream());  //ftp디렉토리 경로 설정 후 해당 파일을 byte로 전송
					//파일 먼저 저장 후 DB저장
					//파일저장되면 여기서  this.result = true가 됨
					
					//file_DTO에 이미지명 정보 전달 
					f_dto.setFilenm(image_file.getOriginalFilename());  //원래파일명
					f_dto.setFileRenm(this.file_rnm);  //파일리네임
					
					String api_nm[] = this.file_rnm.split("[.]");
					f_dto.setApinm(api_nm[0]);  //api네임
					
					String api_url = "http://"+this.url+"/imgfile/"+this.file_rnm;
					f_dto.setImgPath(api_url);  //이미지경로
					
					File dir = new File("http://"+this.url+"/imgfile/");		
					if (!dir.exists()) { //해당 경로가 없을경우
						dir.mkdirs(); // 폴더 생성
					}
				}
				
		
			} catch (Exception e) {
				this.log.error(e.toString());
				
			} finally {
				 if (this.fc != null && this.fc.isConnected()) {
					 this.fc.disconnect();  //ftp 접속 해제 
				 }
				
			}
		
		}else { //파일 첨부 안되었어도 
			this.result = true;  //true 반환 
		}
		
		return this.result;
	}
	*/
	
	//sftp사용시
	public boolean cdn_filesave(file_DTO f_dto, MultipartFile image_file) {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
        	this.file_rnm = this.file_rename(image_file.getOriginalFilename());  //원래파일명 리네임메소드에 전달 

            JSch jsch = new JSch();
            session = jsch.getSession(this.user, this.host, this.port);
            session.setPassword(this.pass);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // 실제 파일 업로드
            try (InputStream fis = image_file.getInputStream()) {
                channelSftp.put(fis, "imgfile/" + this.file_rnm);
                result = true; // 업로드 성공
                
                //file_DTO에 이미지명 정보 전달 
                f_dto.setFilenm(image_file.getOriginalFilename());  //원래파일명
				f_dto.setFileRenm(this.file_rnm);  //파일리네임
				
				String api_nm[] = this.file_rnm.split("[.]");
				f_dto.setApinm(api_nm[0]);  //api네임
				
				String api_url = "http://"+this.url+"/imgfile/"+api_nm[0];
				f_dto.setImgPath(api_url);  //이미지경로
				
            }catch(Exception e2) {
            	 e2.printStackTrace();
                 this.result = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.result = false;
            
        } finally {
            if (channelSftp != null && channelSftp.isConnected()){
            	channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
            	session.disconnect();
            }
        }
        return this.result;
    }
   
	//cdn이미지 요청 
	public byte[] cdn_img(String filename) {
		
		byte[] imageBytes = null;
		Session session = null;
		ChannelSftp channelSftp = null;
		
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(this.user, this.host, this.port);
			session.setPassword(this.pass);
			
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			
			session.connect();
			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			
			try (InputStream is = channelSftp.get("imgfile/" + filename)) {
				imageBytes = is.readAllBytes();
				
			} catch(Exception e2) {
				System.out.println("파일을 찾을 수 없음: " + filename);
				 e2.printStackTrace();
			}
			
		} catch (Exception e) {
			 System.out.println("SFTP 연결 실패");
			 e.printStackTrace();
		} finally {
			if (channelSftp != null && channelSftp.isConnected()) {
				channelSftp.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
			
		}
		return imageBytes;
	}
	
	//cdn서버에 있는 해당 파일을 삭제하는 메소드 
	public boolean cdn_ftpdel(String fileyes, String idx) throws Exception {
		this.fc = new FTPClient();
		this.fcc = new FTPClientConfig();
		this.fc.configure(fcc);  
		this.fc.connect(this.host,this.port);  //연결 실행 
		this.fc.enterLocalPassiveMode();  //PassiveMode(전송모드)로 세팅(FTP접속환경)
	
//		if(this.fc.login(this.user, this.pass)) {
//			String filesize = this.fc.getSize("/imgfile/"+fileyes);
//			if(filesize != null) {  //ftp에 파일이 있을 때 
//				this.result = this.fc.deleteFile("/imgfile/"+fileyes);  //FTP에 있는 해당 파일명의 파일 삭제 
				//this.fc.removeDirectory("/imgfile/");   //FTP에 있는 해당 디렉토리 통째로 삭제 
				//this.fc.makeDirectory("/imgfile/");  //FTP에 디렉토리 생성 

//				if(this.result==true) {  //ftp서버에서 삭제가 완료 된 후 DB에서도 삭제 
//					this.g_svc.del_data(idx);  //고유값으로 db에서 삭제 실행 
//				}
				
//			}else {  //ftp에 파일이 없을 때 
//				this.g_svc.del_data(idx);  //그냥 DB에서만 삭제 
//				this.result = true;
//			}
//		}
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