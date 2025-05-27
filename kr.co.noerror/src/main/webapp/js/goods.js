//토글버튼 클릭시 페이지 이동
function toggleButton(type) {
  	let url = "";
	if (type == 'product') {
	     url = "/goods.do?type="+type;
	} else if (type == 'item') {
	     url = "/goods.do?type="+type;
	} else if (type == 'consume') {
	 	 url = "/goods.do?type="+type;
	} 
 	location.href = url; //페이지 이동
}


//추가 버튼 누른 경우 (데품등록페이지로 이동)
var addBtn = function(){
	location.href="./products_insert.do";
}


//제품 유형 선택시 등록화면 세팅
function goodsType(){
	var goods_type = document.querySelector("#goods_type");
	fetch("/goods_type.do?goods_type=" + goods_type.value, {
		method: "GET"

	}).then(function(data) {
		return data.json();

	}).then(function(l_class) {
		var select_lc = document.querySelector("#products_class1");
		var open_price = document.querySelector(".open_price");
		var open_ccorp = document.querySelector(".open_ccorp");
		
		select_lc.innerHTML = "";
		var w=0;
		var lg_class = "";
		lg_class = `<option value="">`+"선택"+`</option>`;
		while(w< l_class.length-1){
			lg_class += `<option value="${l_class[w]}">`+l_class[w]+`</option>`;
			w++;
		}
		select_lc.innerHTML=lg_class;
	
		//완제품 선택시
		if(goods_type.value == "완제품" || goods_type.value == "반제품"){  //완제품,반제품 선택시
			//판매가 입력란 오픈
			open_price.innerHTML = `
				<div class="dynamic-price">
				<label class="form-label ss">판매가</label>	
				<input type="text" class="form-control" id="product_price">
				</div>
			`;
			
			//거래처 입력란 삭제
			var remove_corp = open_ccorp.querySelector(".dynamic-corp");
		    if (remove_corp){
				remove_corp.remove();
			}
			open_ccorp.className = "row my-3 open_ccorp ";
		
				
		//부자재 선택시 	
		}else {   
			//판매가 입력란 삭제 
			var remove_price = open_price.querySelector(".dynamic-price");
		    if (remove_price){
				remove_price.remove();
			}
			open_price.className = "open_price col-md-2";
			
			//거래처 입력란 오픈 
			open_ccorp.innerHTML = `
				<div class="row dynamic-corp"> 
				<div class="col-auto d-flex align-items-end pe-0 ">
				<button class="btn btn-primary" type="button">
					<i class="bi bi-search"></i>
				</button>
				</div>
				<div class="col-md-2">
					<label class="form-label">거래처코드</label> 
					<input type="text" class="form-control" id="p_corp">
				</div>
				<div class="col-md-3">
					<label class="form-label">거래처명</label> 
					<input type="text" class="form-control">
				</div>
					<div class="col-md-3">
					<label class="form-label">담당자명</label> 
					<input type="text" class="form-control">
				</div>
				<div class="col-md-3">
					<label class="form-label">연락처</label> 
					<input type="text" class="form-control">
				</div>
				</div>			
			`;
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}

//대분류 - 소분류 짝맞추기 
function lcSc(lc_value) {
	var goods_type = document.querySelector("#goods_type");
	fetch("./goods_class.do?goods_type=" + goods_type.value + 
			"&products_class1=" + lc_value, {
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


var goods_type = document.querySelector("#goods_type");
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
var purchase_corp = document.querySelector("#p_corp");
 
var memo = document.querySelector("#memo");
var productImage = document.querySelector("#productImage").files[0];
var url = document.querySelector("#url");
//제품 등록하기 + 유효성검사
function insert_pd(){
	
	
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
				
	//파일첨부 함경우 			
	}else if(productImage){  
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
	//선택제품유형이 완제품,반제품일 경우
	}else if(goods_type.value =="완제품" || goods_type.value =="반제품" ){
		if(product_price.value==""){
			alert("판매가를 입력하세요");
			product_price.focus();		
	 	}else {
			if(confirm("제품등록을 완료허시겠습니까?")){
				this.insertProduct();
			}
		}
			
	}else if(goods_type.value =="부자재"){
		if(purchase_corp.value==""){
			alert("판매가를 입력하세요");
			purchase_corp.focus();		
		}else {
			if(confirm("제품등록을 완료허시겠습니까?")){
				this.insertProduct();
			}
		}	
		 
	}
	//else  {
	//	if(confirm("제품등록을 완료허시겠습니까?")){
	//		this.insertProduct();
	//	}
	//}
}

//제품 등록
function insertProduct(){
	var use_flag = document.querySelector('input[name="use_flag"]:checked');
	var exp_flag = document.querySelector('input[name="exp_flag"]:checked');
	var product_price = document.querySelector("#product_price");
	
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
	formData.append("USE_FLAG", use_flag.value);
	formData.append("EXP_FLAG", exp_flag.value);
	formData.append("MEMO", memo.value);
	
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


//부자재 등록
function insertItem(){
	var use_flag = document.querySelector('input[name="use_flag"]:checked');
	var exp_flag = document.querySelector('input[name="exp_flag"]:checked');
	var purchase_corp = document.querySelector("#p_corp");
	
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
	
	fetch("./item_insertok.do", {

		method: "POST",
		body : new URLSearchParams(formData)
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		if(result=="ok"){
			alert("부자재 등록이 완료되었습니다.");
			location.href="./goods.do?type=item";
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}

//초기화 버튼 클릭 
function resetBtn(){
	
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

//제품상세보기
function open_detail(event){
	
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




//삭제버튼 누른경우
function deleteBtn(del_pd){
	var idx;
	var pd_code;
	var gd_type;
	var del_req = new Array();
	
	if(del_pd){ //모달에서 삭제시 (del_pd가 전달되었을떄)
		idx = del_pd.getAttribute("data-idx");
		pd_code = del_pd.getAttribute("data-pdcode");
		gd_type= del_pd.getAttribute("data-type");
		
		if(confirm("정말 삭제하시겠습니까? \n 삭제 후에는 복구되지 않습니다.")){
			del_req = [{idx: idx, code: pd_code, type : gd_type}]
			del_ajax(del_req);	//모달 안에서 1개만 삭제 
		}
			
	}else {  //리스트에서 체크박스로 삭제시 (del_pd 전달x)
		var checkboxes = document.querySelectorAll("input[name='selected_box']:checked");
		
		if (checkboxes.length == 0) {
			alert("삭제할 항목을 선택해주세요.");
			
		}else{
			if (confirm("정말 삭제하시겠습니까? \n 삭제 후에는 복구되지 않습니다.")) {
				
				checkboxes.forEach(chk => {
					idx = chk.getAttribute("data-idx");		
					pd_code = chk.getAttribute("data-pdcode");
					gd_type= chk.getAttribute("data-type");
					
					del_req.push({ idx: idx, code: pd_code, type : gd_type })
				});
				del_ajax(del_req); //체크박스로 1개~여러개 삭제 
			}
		}
	}
}

//삭제 ajax
function del_ajax(del_req){	
	fetch("./goods_delete.do/"+del_req[0].type+"_del", {
		method: "DELETE",
		headers: {"content-type": "application/json"},
		body: JSON.stringify(del_req)
			
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		console.log("result : "+result);
		if (result == "ok") {
		
			alert("삭제가 완료되었습니다.");
				
			// 모달 닫기
	        const modalElement = document.getElementById("modal");
	       	const modal = bootstrap.Modal.getInstance(modalElement);
	        if (modal) {
	            modal.hide();
				setTimeout(() => {
					document.activeElement.blur(); // 현재 포커스를 제거
				}, 300);
	        }
			//리스트 페이지 새로고침
	       	location.reload();
			
		}else if(result=="fail") {
			alert("시스템 문제로 일부 제품 삭제에 실패했습니다.");
		}else{
			console.log(result);
		}
	}).catch(function(error) {
		failCount++;
		console.log("통신오류발생" + error);
	});
}













