package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("temp_bom_DTO")
public class temp_bom_DTO {
	
	private Long bidx;          // 자동증가값 
    private String bom_code;     // BOM고유코드 
    private String product_code; // 제품고유코드
    private String product_name; // 제품명
    private String item_code;    // 자재고유코드 
    private String item_type;    // 자재유형 
    private String item_name;    // 자재명 
    private String item_unit;    // 자재단위 
    private String unit_price;   // 단가  
    private Integer quantity;    // 소요량
}
