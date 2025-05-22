package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("temp_clients_DTO")
public class temp_client_DTO {
	
    private String company_code;
    private String company_name;
    private String biz_num;
    private String manager_code;
    private String manager_name;
    private String phone_num;
}
