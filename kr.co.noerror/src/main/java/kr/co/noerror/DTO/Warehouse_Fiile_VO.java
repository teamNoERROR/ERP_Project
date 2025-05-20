package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import lombok.Data;

@Data
@Repository("Warehouse_Fiile_VO")
public class Warehouse_Fiile_VO {
	//  원본 파일명		 변경된 파일명 		파열 경로	  	
	String wh_file_ori, wh_file_new, wh_file_url;
	
}
