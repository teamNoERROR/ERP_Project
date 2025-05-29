package kr.co.noerror.DTO;



import lombok.Data;


@Data
public class IOSF_DTO {
	
	  // 창고 정보
    String wh_code;        // 창고 코드
    String wh_name;        // 창고 이름
    String wh_location;    // 창고 위치

    // 제품 정보
    String item_code;      // 제품 코드
    String item_name;      // 제품 이름
    String category_main;  // 제품 대분류
    String category_sub;   // 제품 소분류
    String use_yn;         // 제품 사용 여부

    // 거래처 정보
    String client_code;     // 거래처 코드
    String client_name;     // 거래처 이름
    String client_use_yn;   // 거래처 사용 여부
	
}
