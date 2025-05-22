package kr.co.noerror.Model;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.springframework.stereotype.Repository;

@Repository("M_File_Rename")
public class M_File_Rename {
	//홍길동.jpg => 2025032755.jpg
	public String rename(String filenm) {
		//속성
		int com = filenm.lastIndexOf(".");
		String fnm = filenm.substring(com);
		
		//날짜
		Date day = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String today = sf.format(day);		//년월일
		
		//랜덤값
		int no = (int)Math.ceil(Math.random()*1000); 
		String makefile = today + no + fnm;	//파일명 예시)2025032715.속성명
		
		return makefile;
	}
	// 확장자 없는 파일명만 반환하는 메서드 (필요하면 사용)
    public String getNameWithoutExt(String filenm) {
        int dotIndex = filenm.lastIndexOf(".");
        if(dotIndex > 0) {
            return filenm.substring(0, dotIndex);
        }
        return filenm;
    }
}