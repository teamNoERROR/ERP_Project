

function loadList(type) {
	const table = document.getElementById('table-container');
	const container = document.getElementById('list_container');

	// 기존 테이블 숨기기
	if (table) table.style.display = 'none';

	// 리스트 타입에 따라 다른 내용 표시 (더미 데이터 사용 예시)
	const dummyData = {
		1: ['완제품 A', '완제품 B', '완제품 C'],
		2: ['부자재 X', '부자재 Y'],
		3: ['소모품 1', '소모품 2', '소모품 3']
	};

	const titles = ['완제품', '부자재', '소모품'];
	const items = dummyData[type] || [];

	container.innerHTML = `
		<h4>${titles[type - 1]} 리스트</h4>
		<ul style="list-style: none; padding: 0;">
			${items.map(item => `<li style="padding: 5px 0;">${item}</li>`).join('')}
		</ul>
	`;
}


var addBtn = function(){
	location.href="./products_insert.do";
	
}

//저장하기 버튼 누른 경우 
var insertBtn = function(){
	
	
}


var productImage = document.querySelector("#productImage");
var product_type = document.querySelector("#product_type");
var products_class1 = document.querySelector("#products_class1");
var products_class2 = document.querySelector("#products_class2");
var product_name = document.querySelector("#product_name");
var product_spec = document.querySelector("#product_spec");
var product_unit = document.querySelector("#product_unit");
var pd_safe_stock = document.querySelector("#pd_safe_stock");
var use_flag = document.getElementsByName("use_flag");
var exp_flag = document.getElementsByName("exp_flag");
var product_cost = document.querySelector("#product_cost");
var product_price = document.querySelector("#product_price");
var memo = document.querySelector("#memo");
var productImage = productImage.files[0];
var useChecked = false;
var expChecked = false;
for (let i = 0; i < use_flag.length; i++) {
    if (use_flag[i].checked) {
        useChecked = true;
        break;
    } 
}
for (var j=0; j <exp_flag.length; j++){
	if(expChecked[j].checked){
		expChecked = true;
		break;
	}
}
console.log(useChecked);
console.log(expChecked);

//제품 등록하기
function insert_pd(){
	
	
	if(product_type.value ==""){
		alert("제품유형을 선택하세요");
		product_type.focus();
	}
	else if(products_class1.value ==""){
		alert("대분류를 선택하세요");
		products_class1.focus();
	}
	//else if(products_class2.value ==""){ 
	//	alert("소분류를 선택하세요");
	//	products_class2.focus();
	//}
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
	/*else if(!useChecked){ 
		alert("사용유무를 선택하세요");
		use_flag[0].focus();			
					
					
	}
	else if(!expChecked){ 
		alert("유통기한 사용유무를 선택하세요");
		exp_flag[0].focus();					
						
						
	}*/
	else if(product_cost.value ==""){ 
		alert("단가를 입력하세요");
		product_cost.focus();					
							
							
	}else if(product_price.value ==""){ 
		alert("판매가를 입력하세요");
		product_price.focus();					
							
							
	}else if(productImage){  //파일첨부 함경우 
		var imgSize = productImage.size; // 파일 크기
		var maxSize = 2 * 1024 * 1024; // 2MB제한
			
		if (!productImage.type.startsWith("image/")) {
    		alert("이미지 파일만 첨부 가능합니다 (jpg, png, gif 등)");
		}
		else if(imgSize > maxSize){
			alert("파일첨부 용량은 2MB이하만 가능합니다.");
			
		}else {
			if(confirm("제품등록을 완료허시겠습니까?")){
				this.insertProduct();
			}
		}
	}else {
		if(confirm("제품등록을 완료허시겠습니까?")){
			this.insertProduct();
		}
	}
}

function insertProduct(){
	
	var formData = new FormData();
	formData.append("PRODUCT_TYPE", product_type.value);
	formData.append("PRODUCT_CLASS1", products_class1.value);
	formData.append("PRODUCT_CLASS2", products_class2.value);
	formData.append("PRODUCT_NAME", product_name.value);
	formData.append("PRODUCT_SPEC", product_spec.value);
	formData.append("PRODUCT_UNIT", product_unit.value);
	formData.append("PRODUCT_COST", product_cost.value);
	formData.append("PRODUCT_PRICE", product_price.value);
	formData.append("PD_SAFE_STOCK", pd_safe_stock.value);
	formData.append("USE_FLAG", use_flag.value);
	formData.append("EXP_FLAG", exp_flag.value);
	
	if(memo.value != ""){
		formData.append("MEMO", memo.value);
	}

	if (productImage) { //새로 파일 첨부를 한 경우 
    	formData.append("productImage", productImage);
	}
	
	fetch("./products_insertok.do", {

		method: "POST",
		body : formData
		
		

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		console.log(result)

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
	
	

}
var resetBtn = function(){
	
	
	
}





//대분류 - 소분류 짝맞추기 
function lg_class() {

	var pd_class = document.querySelector("#products_class1");
	console.log("pd_class.value:", pd_class.value);
	fetch("./products_insert.do?products_class1=" + pd_class.value, {

		method: "GET"

	}).then(function(data) {
		return data.text();;

	}).then(function(result) {
		/*
		var select_sc = document.querySelector("#pd_class2");
		var w=0;
		while(w<sc_list.length){
			var option = document.createElement("option");
			option.innerHTML = sc_list[w];
			option.value = sc_list[w];
			select_sc.append(option);
			w++;
		}
		*/

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}






