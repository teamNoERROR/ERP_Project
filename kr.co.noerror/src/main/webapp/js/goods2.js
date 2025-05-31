var goods_type = document.querySelector("#goods_type");
var products_class1 = document.querySelector("#products_class1");
var products_class2 = document.querySelector("#products_class2");
var product_name = document.querySelector("#product_name");
var product_spec = document.querySelector("#product_spec");
var product_unit = document.querySelector("#product_unit");
var pd_safe_stock = document.querySelector("#pd_safe_stock");
var product_price = document.querySelector("#product_price");
var product_cost = document.querySelector("#product_cost");
var memo = document.querySelector("#memo");
var productImage = document.querySelector("#productImage").files[0];
var url = document.querySelector("#url");


//완제품페이지 > 추가 버튼 누른 경우 (완제품등록페이지 이동)
function addProduct(){
	location.href="./products_insert.do";
}



//제품 등록하기 + 유효성검사
function insert_pd(){
	/*
	var use_flag = document.querySelector('input[name="use_flag"]:checked');
	var exp_flag = document.querySelector('input[name="exp_flag"]:checked');
		
	console.log(goods_type.value);
	console.log(products_class1.value);
	console.log(products_class2.value);
	console.log(product_name.value);
	console.log(product_spec.value);
	console.log(product_unit.value);
	console.log(pd_safe_stock.value);
	console.log(product_price.value);
	console.log(product_cost.value);
	console.log(memo.value);
	console.log(productImage);
	
	console.log(use_flag.value);
	console.log(exp_flag.value);
	*/
	
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
				
			
	}else if(product_price.value==""){
			alert("판매가를 입력하세요");
			product_price.focus();	
				
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
			insertProduct();
		}
	}else {
		insertProduct();
	}
}


//제품 등록 ajax
function insertProduct(){
	var use_flag = document.querySelector('input[name="use_flag"]:checked');
	var exp_flag = document.querySelector('input[name="exp_flag"]:checked');
	
	var formData = new FormData();
	formData.append("PRODUCT_TYPE", goods_type.value);
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
	
    formData.append("productImage", productImage);
	formData.append("url", "아이피써놓기");
	
	fetch("./products_insertok.do", {

		method: "POST",
		body : new URLSearchParams(formData)
		
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



//제품상세보기
function open_gd_detail(event){
	
	var pd_code = event.currentTarget.querySelector(".pd_code").innerText;
	var gd_type =event.currentTarget.querySelector(".sb").getAttribute("data-type");
	
	fetch("./goods_detail.do", {
		method: "POST",
		headers: {"content-type": "application/x-www-form-urlencoded"},
		body: "pd_code="+pd_code+"&type="+gd_type
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		console.log(result)
		var modal;
		document.getElementById("modalContainer").innerHTML = result;
		
		if(gd_type == "product"){
			modal = new bootstrap.Modal(document.getElementById("pd_detail"));
			
		}else if(gd_type == "item"){
			modal = new bootstrap.Modal(document.getElementById("itm_detail"));
			
		}	
		modal.show();
		
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}






//완제품 페이징 
function go_pd_pg(ee){
	var kw = ee.getAttribute('data-keyword');
	var no = ee.getAttribute('data-pageno');
	var tp = ee.getAttribute('data-type');
	var pea = ee.getAttribute('data-pea');
	var sc = ee.getAttribute('data-sclass');
	
	if(!kw || kw == "" || !sc ||sc ==null){  //검색 없는경우 
		location.href="./goods.do?type="+tp+"&pageno="+no+"&post_ea="+pea;
	}
	else if(!kw || kw != "") {  //검색어가 있는경우 
		location.href="./goods.do?type="+tp+"&keyword="+kw+"&pageno="+no+"&post_ea="+pea;
	}
	else if(sc ||sc !=null) { 
		location.href="./goods.do?type="+tp+"&sclass="+sc+"&pageno="+no+"&post_ea="+pea;	
			
	}	
	/*else { 
		location.href="./goods.do?type="+tp+"&sclass="+sc+"&pageno="+no+"&post_ea="+pea;	
			
	}*/
}



//완제품 게시물 개수 선택 
function gdPostEa(type){
	var form = document.querySelector("#pdpg_frm");
	form.method = "GET";
	form.action = "./goods.do";
	form.type.value=type;
	form.pageno.value = 1; //
	form.submit();
}