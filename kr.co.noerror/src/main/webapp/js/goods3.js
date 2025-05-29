var goods_type = document.querySelector("#goods_type");
var products_class1 = document.querySelector("#products_class1");
var products_class2 = document.querySelector("#products_class2");
var product_name = document.querySelector("#product_name");
var product_spec = document.querySelector("#product_spec");
var product_unit = document.querySelector("#product_unit");
var pd_safe_stock = document.querySelector("#pd_safe_stock");
var purchase_corp = document.querySelector("#p_corp");
var product_cost = document.querySelector("#product_cost");
var memo = document.querySelector("#memo");
var productImage = document.querySelector("#productImage").files[0];
var url = document.querySelector("#url");

//제품 등록하기 + 유효성검사
function insert_itm(){
	if(goods_type.value ==""){
		alert("제품유형을 선택하세요");
		goods_type.focus();
	}
	else if(products_class1.value ==""){
		alert("대분류를 선택하세요");
		products_class1.focus();
	}
	else if(products_class2.value ==""){ 
		alert("소분류를 선택하세요");
		products_class2.focus();
	}
	else if(product_name.value ==""){ 
		alert("제품명을 입력하세요");
		product_name.focus();	
			
	}else if(product_spec.value ==""){ 
		alert("제품규격을 입력하세요");
		product_spec.focus();		
				
	}else if(product_unit.value ==""){ 
		alert("제품단위를 입력하세요");
		product_unit.focus();		
				
	}else if(pd_safe_stock.value ==""){ 
		alert("안전재고수를 입력하세요");
		pd_safe_stock.focus();			
					
	}
	else if(useY.checked == false && useN.checked == false){ 
		alert("사용유무를 선택하세요");
					
	}
	else if(expireY.checked == false && expireN.checked == false){ 
		alert("유통기한 사용유무를 선택하세요");
						
	}
	else if(product_cost.value ==""){ 
		alert("단가를 입력하세요");
		product_cost.focus();							
				
	}else if(purchase_corp.value==""){
		alert("거래처를 입력하세요");
		purchase_corp.focus();		
	}
	//파일첨부 함경우 	
	else if(productImage){  
		var imgSize = productImage.size; // 파일 크기
		var maxSize = 2 * 1024 * 1024; // 2MB제한
			
		if (!productImage.type.startsWith("image/")) {
    		alert("이미지 파일만 첨부 가능합니다 (jpg, png, gif 등)");
		}
		else if(imgSize > maxSize){
			alert("파일첨부 용량은 2MB이하만 가능합니다.");
			
		}else {
			insertItem();
		}
		
	}else {
		insertItem();
	}
}


//부자재 등록 ajax
function insertItem(){
	var use_flag = document.querySelector('input[name="use_flag"]:checked');
	var exp_flag = document.querySelector('input[name="exp_flag"]:checked');
	
	var formData = new FormData();
	formData.append("ITEM_TYPE", goods_type.value);
	formData.append("ITEM_CLASS1", products_class1.value);
	formData.append("ITEM_CLASS2", products_class2.value);
	formData.append("ITEM_NAME", product_name.value);
	formData.append("ITEM_SPEC", product_spec.value);
	formData.append("ITEM_UNIT", product_unit.value);
	formData.append("ITEM_COST", product_cost.value);
	formData.append("ITM_SAFE_STOCK", pd_safe_stock.value);
	formData.append("COMPANY_CODE", purchase_corp.value);
	formData.append("USE_FLAG", use_flag.value);
	formData.append("EXP_FLAG", exp_flag.value);
	formData.append("MEMO", memo.value);
	
    formData.append("productImage", productImage);
	formData.append("url", "아이피써놓기");
	
	fetch("./items_insertok.do", {

		method: "POST",
		body : new URLSearchParams(formData)
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		console.log(result)
		if(result=="ok"){
			alert("부자재 등록이 완료되었습니다.");
			location.href="./goods.do?type=item";
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}

