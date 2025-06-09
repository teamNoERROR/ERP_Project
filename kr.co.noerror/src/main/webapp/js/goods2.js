//완제품 관련 JS

var pd_type = document.querySelector("#goods_type");
var products_class1 = document.querySelector("#products_class1");
var products_class2 = document.querySelector("#products_class2");
var product_name = document.querySelector("#product_name");
var product_spec = document.querySelector("#product_spec");
var product_unit = document.querySelector("#product_unit");
var pd_safe_stock = document.querySelector("#pd_safe_stock");
var product_price = document.querySelector("#product_price");
var product_cost = document.querySelector("#product_cost");
var pd_memo = document.querySelector("#memo");

var reg_num = /^\d+$/;


/*--------------------------------------------------------------
완제품페이지 > 추가 버튼 누른 경우 (완제품등록페이지 이동)
--------------------------------------------------------------*/
function addProduct(){
	location.href="./products_insert.do";
}

/*--------------------------------------------------------------
완제품 등록하기 + 유효성검사
--------------------------------------------------------------*/
function insert_pd(){
	var pdfile = document.querySelector("#productImage").files[0];
	
	if(pd_type.value ==""){
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
					
	}else if(!reg_num.test(pd_safe_stock.value)){
		alert("안전재고수는 숫자만 입력이 가능합니다.");
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
			
	}else if(!reg_num.test(product_cost.value)){
		alert("단가는 숫자만 입력이 가능합니다.");
		product_cost.focus();	
		
	}else if(product_price.value==""){
		alert("판매가를 입력하세요");
		product_price.focus();
				
	}else if(!reg_num.test(product_price.value)){
		alert("판매가는 숫자만 입력이 가능합니다.");
		product_price.focus();	
		
	}else if(pdfile){   //파일첨부 함경우 
		var imgSize = pdfile.size; // 파일 크기
		var maxSize = 2 * 1024 * 1024; // 2MB제한
			
		if (!pdfile.type.startsWith("image/")) {
    		alert("이미지 파일만 첨부 가능합니다 (jpg, png, gif 등)");
		}
		else if(imgSize > maxSize){
			alert("파일첨부 용량은 2MB이하만 가능합니다.");
			
		}else {
			insertProduct();
		}
	}else {
		insertProduct();
	}
}

/*--------------------------------------------------------------
완제품 등록 ajax
--------------------------------------------------------------*/
function insertProduct(){
	var use_flag = document.querySelector('input[name="use_flag"]:checked');
	var exp_flag = document.querySelector('input[name="exp_flag"]:checked');
	var pdfile = document.querySelector("#productImage").files[0];

	var formData = new FormData();
	formData.append("PRODUCT_TYPE", pd_type.value);
	formData.append("PRODUCT_CLASS1", products_class1.value);
	formData.append("PRODUCT_CLASS2", products_class2.value);
	formData.append("PRODUCT_NAME", product_name.value);
	formData.append("PRODUCT_SPEC", product_spec.value);
	formData.append("PRODUCT_UNIT", product_unit.value);
	formData.append("PRODUCT_COST", product_cost.value);
	formData.append("PRODUCT_PRICE", product_price.value);
	formData.append("PD_SAFE_STOCK", pd_safe_stock.value);
	formData.append("PD_USE_FLAG", use_flag.value);
	formData.append("PD_EXP_FLAG", exp_flag.value);
	formData.append("PD_MEMO", memo.value);
	
    formData.append("productImage", pdfile);
	
	fetch("./products_insertok.do", {
		method: "POST",
		body : formData
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		if(result=="ok"){
			alert("제품 등록이 완료되었습니다.");
			location.href="./goods.do";
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}


/*--------------------------------------------------------------
완제품+부자재 페이징 
--------------------------------------------------------------*/
function go_pd_pg(ee){
	var keyword = ee.getAttribute('data-keyword');
	var page_no = ee.getAttribute('data-pageno');
	var sclass = ee.getAttribute('data-sclass');
	
	var params = {  
		    type: ee.getAttribute('data-type'),
		    pageno: page_no,
		    post_ea: ee.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}

		if (sclass) {  //소분류 선택시 
		    params["products_class2"] = sclass;
		}

		var pString = new URLSearchParams(params).toString();
		location.href = "./goods.do?" + pString;
}

/*--------------------------------------------------------------
완제품+부자재 게시물 개수 선택 
--------------------------------------------------------------*/
function gdPostEa(type){
	var form = document.querySelector("#pdpg_frm");
	form.method = "GET";
	form.action = "./goods.do";
	form.type.value=type;
	form.pageno.value = 1; //
	form.submit();
}