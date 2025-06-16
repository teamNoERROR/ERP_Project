//부자재 관련 JS

var itm_type = document.querySelector("#goods_type");
var item_class1 = document.querySelector("#products_class1");
var item_class2 = document.querySelector("#products_class2");
var item_name = document.querySelector("#product_name");
var item_spec = document.querySelector("#product_spec");
var item_unit = document.querySelector("#product_unit");
var itm_safe_stock = document.querySelector("#pd_safe_stock");
var purchase_corp = document.querySelector("#cmp_code");
var item_cost = document.querySelector("#product_cost");
var itm_memo = document.querySelector("#memo");


/*--------------------------------------------------------------
부자재페이지 > 추가 버튼 누른 경우 
--------------------------------------------------------------*/
function addItem(){
	location.href="./items_insert.do";
}

/*--------------------------------------------------------------
제품 등록하기 + 유효성검사
--------------------------------------------------------------*/
function insertItm(){
	var itmImage = document.querySelector("#itemImage").files[0];
	
	if(itm_type.value ==""){
		alert("제품유형을 선택하세요");
		itm_type.focus();
	}
	else if(item_class1.value ==""){
		alert("대분류를 선택하세요");
		item_class1.focus();
	}
	else if(item_class2.value ==""){ 
		alert("소분류를 선택하세요");
		item_class2.focus();
	}
	else if(item_name.value ==""){ 
		alert("제품명을 입력하세요");
		item_name.focus();	
			
	}else if(item_spec.value ==""){ 
		alert("제품규격을 입력하세요");
		item_spec.focus();		
				
	}else if(item_unit.value ==""){ 
		alert("제품단위를 입력하세요");
		item_unit.focus();		
				
	}
	/*else if(itm_safe_stock.value ==""){ 
		alert("안전재고수를 입력하세요");
		itm_safe_stock.focus();	
				
	}*/
	else if(!reg_num.test(itm_safe_stock.value)){  //숫자만 입력 유효성 검사 
		alert("안전재고수는 숫자만 입력이 가능합니다.");
		itm_safe_stock.focus();	
		
	}else if(useY.checked == false && useN.checked == false){ 
		alert("사용유무를 선택하세요");
	}
	else if(expireY.checked == false && expireN.checked == false){ 
		alert("유통기한 사용유무를 선택하세요");
	}
	else if(item_cost.value ==""){ 
		alert("단가를 입력하세요");
		item_cost.focus();							
				
	}else if(!reg_num.test(item_cost.value)){
		alert("단가는 숫자만 입력이 가능합니다.");
		product_cost.focus();	
		
	}else if(purchase_corp.value==""){
		alert("거래처를 선택하세요");
		purchase_corp.focus();		
	}else if(itmImage){    //파일첨부 함경우 	
		var imgSize = itmImage.size; // 파일 크기
		var maxSize = 2 * 1024 * 1024; // 2MB제한
			
		if (!itmImage.type.startsWith("image/")) {
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

/*--------------------------------------------------------------
거래처 선택 후 인풋란에 붙여넣기
--------------------------------------------------------------*/
function pCltChoice(){

	var radios = document.querySelectorAll('input[name="sel_clt2"]');
	var selected_radio = null;
	
	for (var i = 0; i < radios.length; i++) {
	  if (radios[i].checked) {
	    selected_radio = radios[i];
	  }
	}
		
	if (!selected_radio) {
	alert("거래처를 선택해 주세요.");

	 }else{
		var tr = selected_radio.closest('tr');
		var tdList = tr.querySelectorAll('td');
		var com_code =  tdList[2].innerText.trim();
		var com_name = tdList[3].innerText.trim();
		var mng_name = tdList[7].innerText.trim();
		var mng_tel = tdList[8].innerText.trim();
			
		var cmp_code =  document.querySelector("#cmp_code");
		var cmp_name = document.querySelector("#cmp_name");
		var mng_nm = document.querySelector("#mng_nm");
		var mng_telno = document.querySelector("#mng_tel");

		cmp_code.value= com_code;
		cmp_name.value= com_name;
		mng_nm.value= mng_name;
		mng_telno.value= mng_tel;
		
		// 선택 후 모달 닫기
	 	var modalElement = document.getElementById("p_client_list");
		var modal = bootstrap.Modal.getInstance(modalElement);
		if (modal) {
		    modal.hide();
			setTimeout(() => {
				document.querySelector("body").focus(); // body에 포커스 주기
			}, 300);
		}
	}
}

/*--------------------------------------------------------------
부자재 등록 ajax
--------------------------------------------------------------*/
function insertItem(){
	var use_flag = document.querySelector('input[name="use_flag"]:checked');
	var exp_flag = document.querySelector('input[name="exp_flag"]:checked');
	var itmImage = document.querySelector("#itemImage").files[0];
	
	var formData = new FormData();
	formData.append("ITEM_TYPE", itm_type.value);
	formData.append("ITEM_CLASS1", item_class1.value);
	formData.append("ITEM_CLASS2", item_class2.value);
	formData.append("ITEM_NAME", item_name.value);
	formData.append("ITEM_SPEC", item_spec.value);
	formData.append("ITEM_UNIT", item_unit.value);
	formData.append("ITEM_COST", item_cost.value);
	formData.append("ITM_SAFE_STOCK", itm_safe_stock.value);
	formData.append("COMPANY_CODE", purchase_corp.value);
	formData.append("ITM_USE_FLAG", use_flag.value);
	formData.append("ITM_EXP_FLAG", exp_flag.value);
	formData.append("ITM_MEMO", memo.value);
	
    formData.append("itmImage", itmImage);
	
	fetch("./items_insertok.do", {

		method: "POST",
		body : formData
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		if(result=="ok"){
			alert("부자재 등록이 완료되었습니다.");
			location.href="./goods.do?type=item";
			
		}else if(result=="fail"){
			alert("시스템문제로 거래처등록에 실패했습니다.\n관리자에게 문의해주세요.");
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}
