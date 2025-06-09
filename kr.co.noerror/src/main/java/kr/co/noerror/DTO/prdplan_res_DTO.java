package kr.co.noerror.DTO;

import lombok.Data;

//프로트앤드에 전달하는 생산계획 정보
@Data
public class prdplan_res_DTO {
	private Long pidx;                  
    private String plan_code;            
    private String bom_code;              
    private String order_code;            
    private String product_code;     
    private String product_name;            
    private Integer product_qty;      
    private String product_spec;
    private String product_unit;            
    private String priority;             
    private String start_date;    
    private String end_date;    
    private String due_date;            
    private String company_code;       
    private String company_name;            
    private String ceo_name;            
    private String biz_num;            
    private String manager_name;            
    private String mng_phone_num;            
    private String com_addr1;            
    private String com_addr2;            
    private String ecode;           
    private String ename;                
    private String plan_status;        
    private String mrp_status;       
    private String memo;        
    private String plan_date;            
    private String modify_date;          
}
