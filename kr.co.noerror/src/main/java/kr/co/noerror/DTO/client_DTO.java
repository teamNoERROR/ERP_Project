package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("client_DTO")
public class client_DTO {
	
	int CIDX;
	String COMPANY_CODE, COMPANY_NAME, CEO_NAME;
	String BIZ_NUM, BIZ_TYPE, BIZ_ITEM, COM_ZIPCODE, COM_ADDR1, COM_ADDR2;
	String COM_FILE_NM, COM_FILE_RENM, COM_API_FNM, COM_IMG_SRC;
	String CLIENT_TYPE, MANAGER_NAME, MANAGER_CODE, MNG_EMAIL, MNG_TEL_NUM, MNG_PHONE_NUM, MNG_FAX_NUM;
	String MNG_POSITION, MNG_PART, COM_MEMO, COM_USE_FLAG, COM_INSERT_DATE, COM_MODIFY_DATE;

}
