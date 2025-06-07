//입고리스트 수기등록 버튼 클릭 
function addInbound(){
	location.href="./inbound_insert.do";
}

//우상단 x 마크 클릭(리스트로 돌아가기)
function goInbndList(){
	location.href="./inbound.do"
}


//입고등록시 타입별창고리스트 모달 열기
function whSelect(wh_type){
	fetch("./wh_type_list.do?wh_type="+wh_type, {
			method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		
		var modal= new bootstrap.Modal(document.getElementById("wh_type_list"));
		modal.show();
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
} 


//창고리스트에서 선택후 인풋란에 붙여넣기  
function choiceWh() {
	var radios = document.querySelectorAll('input[name="choice_wh"]');
	
	var selected_radio = null;
	for (var i = 0; i < radios.length; i++) {
	  if (radios[i].checked) {
	    selected_radio = radios[i];
	  }
	}
	if (!selected_radio) {
	alert("창고를 선택해 주세요.");

	 }else{
		var wh_name = selected_radio.getAttribute('data-wh_name');
		
	  	//선택한 창고 데이터 붙여넣을 곳 
		var in_wh_name = document.querySelector('#in_wh_name');
		var in_wh_code = document.querySelector('#wh_code');
		  
		in_wh_name.value= wh_name;
		in_wh_code.value= selected_radio.value;
		
		// 선택 후 모달 닫기
	 	var modalElement = document.getElementById("wh_type_list");
		var modal = bootstrap.Modal.getInstance(modalElement);
		if (modal) {
		    modal.hide();
			setTimeout(() => {
				document.querySelector("body").focus(); // body에 포커스 주기
			}, 300);
		}
	}
};

var pch_code = document.querySelector("#pch_code");
var whname = document.querySelector("#in_wh_name");
var wh_code = document.querySelector("#wh_code");
var inbound_date = document.querySelector("#inbound_date");
var in_status = document.querySelector("#in_status");
var employee_code = document.querySelector("#employee_code");
var inb_memo = document.querySelector("#inb_memo");

var item_code = document.querySelectorAll(".item_code");  //발주상품 코드
var item_qty = document.querySelectorAll(".item_qty");  //입고된 양
var item_exp = document.querySelectorAll(".item_exp");  //유통기한
var item_deli = document.querySelectorAll(".item_deli");  //입고상태

var tbody = document.querySelector("#inbnd_items");
var rows = tbody.querySelectorAll('tr.item_row');



//입고리스트 저장버튼 클릭 
function insert_inBnd(){
	//console.log(item_code.textContent)
	if(pch_code.value==""){
		alert("발주내역을 선택하세요.");
		pch_code.focus();		
	}else if(wh_code.value == ""){
		alert("창고이름을 선택하세요.");
		whname.focus();	
	}else if(inbound_date.value == ""){
		alert("입고일자를 선택하세요.");
		inbound_date.focus();	
	}else if(in_status.value == ""){
		alert("입고상태를 선택하세요.");
		in_status.focus();	
	}else if(employee_code.value==""){
		alert("관리자가 등록되지 않았습니다. ")
	}else{
		// 각 항목 입력 확인
		for (var i = 0; i < rows.length; i++) {
			if (item_qty[i].value.trim() == "" || isNaN(item_qty[i].value) || Number(item_qty[i].value) <= 0) {
				alert("입고된 수량을 정확히 입력해주세요");
				item_qty[i].focus();
				return;
			}
			if (item_exp[i].value.trim() == "") {
				alert("입고된 상품의 유통기한을 입력해주세요");
				item_exp[i].focus();
				return;
			}
			if (item_deli[i].value.trim() === "") {
				alert("입고상태를 선택해주세요");
				item_deli[i].focus();
				return;
			}
		}
		inbndInsertOk();
	}
} 

//입고저장 ajax
function inbndInsertOk(){
	var in_items = [];
	
	rows.forEach(row => {
		var itmCd = row.querySelector(".item_code");
		var itmQty = row.querySelector(".item_qty");
		var itmExp = row.querySelector(".item_exp");
		var itmDeli = row.querySelector(".item_deli");
	    
		//다 입력했으면 배열에 넣기
      	in_items.push({
			PCH_CODE : pch_code.value,
	        ITEM_CODE: itmCd.textContent,
			ITEM_QTY: itmQty.value,
			ITEM_EXP:itmExp.value,
			ITEM_DEL: itmDeli.value,
			WH_CODE : wh_code.value.trim(),
	        IN_STATUS: in_status.value,
			INBOUND_DATE: inbound_date.value,
			EMPLOYEE_CODE: employee_code.value,
			INB_MEMO:inb_memo.value
      	});
	});
	fetch("./inbound_insertok.do", {

		method: "PUT",
		headers: {'content-type': 'application/json'},
		body : JSON.stringify(in_items)
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		if(result=="ok"){
			alert("입고 등록이 완료되었습니다.");
			location.href="./inbound.do";
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}

//입고건 상세보기 모달 
function openInbndDetail(event){
	
	var inbnd_code = event.currentTarget.querySelector(".inbnd_code").innerText;
	
	fetch("./inbnd_detail.do?inbnd_code="+inbnd_code, {
		method: "GET",
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
			var modal;
			document.getElementById("modalContainer").innerHTML = result;
			modal = new bootstrap.Modal(document.getElementById("inbnd_detail"));
			modal.show();
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}


