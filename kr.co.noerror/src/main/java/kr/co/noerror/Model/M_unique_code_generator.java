package kr.co.noerror.Model;

import java.util.function.Function;

import org.springframework.stereotype.Repository;

@Repository("M_unique_code_generator")
public class M_unique_code_generator {

	//중복없는 고유코드 생성
    public String generate(String prefix, Function<String, Boolean> exists_checker) {
        String code;
        boolean is_duplicated;

        do {
        	int no = (int) (Math.random() * 99999) + 1; // 1~99999 사이 숫자 생성
        	code = prefix + String.format("%05d", no); // 5자리 문자열로 변환
            is_duplicated = exists_checker.apply(code);
        } while (is_duplicated);

        return code;
    }
}
