package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Repository("file_DTO")
@Data
public class file_DTO {
	String filenm, fileRenm, apinm, imgPath;
}
