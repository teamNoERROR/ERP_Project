package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("client_DTO")
public class client_DTO {
	
	int CIDX;
	String COMPANY_CODE, COMPANY_NAME, CEO_NAME;
	String BIZ_NUM, BIZ_TYPE, BIZ_ITEM, COM_ZIP, COM_ADDR1, COM_ADDR2;
	String COM_FILE_NM, COM_FILE_RENM, COM_API_FNM;
	String CLIENT_TYPE, COM_MEMO;
	String MANAGER_CODE, MANAGER_NAME, MNG_EMAIL, MNG_TEL, MNG_PHONE, MNG_FAX;
	String MNG_PART, MNG_POSITION, MNG_MEMO, COM_USE_FLAG, COM_INSERT_DATE, COM_MODIFY_DATE;

}
