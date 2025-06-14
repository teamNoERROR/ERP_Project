package kr.co.noerror.DTO;



import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import lombok.Data;


@Data
@Repository("IOSF_DTO")
public class IOSF_DTO {
	
	// 창고 정보
	String win_idx; 	    // 창고 자동증가값
    String wh_code;        // 창고 코드
    String wh_type;        // 창고 유형 -> 입고창고, 부자재창고 ..
    String wh_name;        // 창고 이름
    String wh_kind;        // 창고 유형 -> in , out, ...
    String wh_location;    // 창고 위치
    String wh_zipcode, wh_addr1, wh_addr2; //창고 위치 가져옴
    String inbound_code;	// 입고 코드
    String inbound_date2;
    
    // 제품 정보
    String item_code;      // 제품 코드
    String item_name;      // 제품 이름
    String category_main;  // 제품 대분류
    String category_sub;   // 제품 소분류
    String use_yn;         // 제품 사용 여부
    String in_status; 	   // 입고 상태
    String item_count;	   // 제품 갯수
    LocalDateTime inbound_date;   // 입고 날짜
    
    
    
    //입고 창고
    String in_code;				// 입고 창고 고유 코드
    String in_date;				// 입고 창고 인서트 날짜
    String in_change;				// 입고 창고 인서트 날짜
    
    //출고 창고
    String out_code;				// 완제품창고 고유 코드
    String out_date;				// 완제품창고 인서트 날짜
    String out_status;
    String out_change;				// 입고 창고 인서트 날짜
    
    //완제품 창고
    String finish_code;				// 완제품창고 고유 코드
    String finish_date;				// 완제품창고 인서트 날짜
    String finish_change;				// 입고 창고 인서트 날짜
    String wfs_idx;
    String wfs_code;
    String fs_insert;
    String change_type;
    
    //부자재 창고
    String material_code; 				// 부자재창고 고유 코드
    String material_date; 				// 부자재창고 인서트 날짜
    String material_change;				// 입고 창고 인서트 날짜
    
    // 거래처 정보
    String client_code;     // 거래처 코드
    String client_name;     // 거래처 이름
    String client_use_yn;   // 거래처 사용 여부
    
    //등록사원
    String employee_code;
    String emp_name;
    
    String plan_code;  //생산계획번호 
    int pd_qty;	//변화수량 
    
    String pidx;
    String product_code; // 제품 코드
    String product_name; //제품명  
    String product_class1;
    String product_class2;
    String pd_file_renm;
    String pd_api_fnm;
    String pd_insert_date;
    int ind_pd_stock;
    
    String mv_wh_code;  //이동창고 코드 
    
    
}