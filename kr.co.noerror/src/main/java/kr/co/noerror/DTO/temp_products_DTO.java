package kr.co.noerror.DTO;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("temp_products_DTO")
public class temp_products_DTO {

    private Long pidx;
    private String product_code;
    private String product_name;
    private String product_type;
    private String product_class1;
    private String product_class2;
    private String product_spec;
    private String product_unit;
    private Integer product_cost;
    private Integer product_price;
    private String use_flag;
    private Integer all_stock;
    private String memo;
    private String insert_date;
    private String modify_date;
}
