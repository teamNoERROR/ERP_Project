package kr.co.noerror.Model;

import org.springframework.stereotype.Repository;

@Repository("M_random")
public class M_random {
	
	public String random_no () {

		int no = (int)(Math.random()*90000)+10000; 
		String ran_no = String.valueOf(no);

		return ran_no;
	}
	
	
	
	

}
