

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


var product_type = document.querySelector("#product_type");
var products_class1 = document.querySelector("#products_class1");
var products_class2 = document.querySelector("#products_class2");
var product_name = document.querySelector("#product_name");
var product_spec = document.querySelector("#product_spec");
var product_unit = document.querySelector("#product_unit");
var pd_safe_stock = document.querySelector("#pd_safe_stock");

//var useY = document.querySelector("#useY");
//var useN = document.querySelector("#useN");
//var expireY = document.querySelector("#expireY");
//var expireN = document.querySelector("#expireN");

var product_cost = document.querySelector("#product_cost");
var product_price = document.querySelector("#product_price");
var memo = document.querySelector("#memo");
var productImage = document.querySelector("#productImage").files[0];
var url = document.querySelector("#url");

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
	else if(useY.checked == false && useN.checked == false){ 
		alert("사용유무를 선택하세요");
					
					
	}
	else if(expireY.checked == false && expireN.checked == false){ 
		alert("유통기한 사용유무를 선택하세요");
						
						
	}
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

//제품 등록
function insertProduct(){
	var use_flag = document.querySelector('input[name="use_flag"]:checked');
	var exp_flag = document.querySelector('input[name="exp_flag"]:checked');
	
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
	formData.append("MEMO", memo.value);
	
	if (productImage) { //새로 파일 첨부를 한 경우 
    	formData.append("productImage", productImage);
		formData.append("url", "아이피써놓기");
	}
	
	fetch("./products_insertok.do", {

		method: "POST",
		body : new URLSearchParams(formData)
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		alert("제품 등록이 완료되었습니다.");
		location.href="./goods.do";
		

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
	
	

}


//초기화 버튼 클릭 
var resetBtn = function(){
	
	//document.querySelector("#productImage").value=""
	//document.querySelector('#previewImage').src = "./img/no-image.svg";
	//product_type.value="";
	//products_class1.value="";
	//products_class2.value="";
	//product_name.value="";
	//product_spec.value="";
	//product_unit.value="";
	//pd_safe_stock.value="";
	//use_flag.value="";
	//exp_flag.value="";
	//product_cost.value="";
	//product_price.value="";
	//memo.value="";
	//productImage.value="";
	
	location.reload();
}


function open_detail(event){
	
	var pd_code = event.currentTarget.querySelector(".pd_code").innerText;
	fetch("./goods_detail.do", {
		method: "POST",
		headers: {"content-type": "application/x-www-form-urlencoded"},
		body: "pd_code="+pd_code
				
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		var modal = new bootstrap.Modal(document.getElementById("goods_detail"));
		modal.show();
		
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}



function deleteBtn(del_pd){
	var idx;
	var pd_code;
	
	if(del_pd){ //모달에서 삭제시 (del_pd가 전달되었을떄)
		idx = del_pd.getAttribute("data-idx");
		pd_code = del_pd.getAttribute("data-pdcode");
		if(confirm("정말 삭제하시겠습니까? \n 삭제 후에는 복구되지 않습니다.")){
			del_ajax(idx,pd_code,1,1);	//모달 안에서 1개만 삭제 
		}
			
	}else {
		var checkboxes = document.querySelectorAll("input[name='selected_box']:checked");
		if (checkboxes.length == 0) {
			alert("삭제할 항목을 선택해주세요.");
			
		}else{
			if (confirm("정말 삭제하시겠습니까? \n 삭제 후에는 복구되지 않습니다.")) {
				var count = 0;
				checkboxes.forEach(chk => {
					idx = chk.getAttribute("data-idx");		
					pd_code = chk.getAttribute("data-pdcode");
					
					del_ajax(idx, pd_code, ++count, checkboxes.length);	//체크박스로 여러개 삭제 
				});
			}
		}
	}
}

function del_ajax(idx,pd_code,count,total){
		
	fetch("./goods_delete.do/"+idx+"_del", {
		method: "DELETE",
		headers: {"content-type": "application/json"},
		body: JSON.stringify({
		      	code: pd_code, 
				idx : idx
		  	})
			
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
			
			if (count == total) {
				alert("삭제가 완료되었습니다.");
				
				// 모달 닫기
	            const modalElement = document.getElementById("goods_detail");
	            const modal = bootstrap.Modal.getInstance(modalElement);
	            if (modal) {
	                modal.hide();
	            }
				
				//리스트 페이지 새로고침
               	location.reload();
       		
		}else {
			if(result=="ok"){
				alert("시스템 문제로 제품번호 "+ pd_code +"번 제품 \n삭제 실패");
			}
		}
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}






//대분류 - 소분류 짝맞추기 
function lg_class() {

	var pd_class = document.querySelector("#products_class1");
	fetch("./goods_class.do?products_class1=" + pd_class.value, {
		method: "GET"
		
	}).then(function(data) {
		return data.json();

	}).then(function(s_class) {
		
		var select_sc = document.querySelector("#products_class2");
		select_sc.innerHTML = "";
		var w=0;
		var sclass = "";
		sclass = `<option value="">`+"선택"+`</option>`;
		while(w< s_class.length){
			sclass += `<option value="${s_class[w]}">`+s_class[w]+`</option>`;
			w++;
		}
			select_sc.innerHTML=sclass;
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}






