var ord_code = document.querySelector("#ord_code");
var comp_code = document.querySelector("#comp_code");
//var out_wh_code = document.querySelector("#out_wh_code");
var out_wh_name = document.querySelector("#out_wh_name");
var out_date = document.querySelector("#out_date");
var out_status = document.querySelector("#out_status");
var employee_code = document.querySelector("#employee_code");
var ob_memo = document.querySelector("#ob_memo");

/*--------------------------------------------------------------
출고리스트 수기등록 버튼 클릭 
--------------------------------------------------------------*/
function addOutbound(){
	location.href="./outbound_insert.do";
}

/*--------------------------------------------------------------
출고등록 저장버튼 클릭 
--------------------------------------------------------------*/
function insert_outBnd(){
	var outDate = new Date(out_date.value);
	var today = new Date();
    outDate.setHours(0, 0, 0, 0);
	today.setHours(0, 0, 0, 0);
	
	if(ord_code.value==""){
		alert("출고할 주문건을 선택하세요.");
		ord_code.focus();		
		
	}
	/*else if(out_wh_code.value == ""){
		alert("창고이름을 선택하세요.");
		out_wh_name.focus();	
		
	}*/
	else if(out_date.value == ""){
		alert("출고예정일자를 선택하세요.");
		out_date.focus();	
		
	}
	else if(outDate < today){
		alert("출고일자는 오늘 이전의 날짜를 선택할 수 없습니다.");
		out_date.value = "";  //선택날짜 초기화
		out_date.focus();	
	}
	else if(out_status.value == ""){
		alert("출고상태를 선택하세요.");
		out_status.focus();	
		
	}else if(employee_code.value==""){
		alert("책임 담당자가 등록되지 않았습니다. ");
		
	}else{
		// 각 항목 입력 확인 후 제출 
		if (!validateInputs()) return;
		outBndInsertOk();
	}
} 

//각 입력항목 확인 
function validateInputs() {
	var tbody = document.querySelector("#outbnd_pds");
	var rows = tbody.querySelectorAll('tr.pd_row');
	
	var out_pd_code = document.querySelectorAll(".out_pd_code");  //상품코드
	var ord_pd_qty = document.querySelectorAll(".ord_pd_qty"); //주문수량
	var all_pd_stock = document.querySelectorAll(".all_pd_stock");  //재고수
	var out_pd_qty = document.querySelectorAll(".out_pd_qty");  //출고수량
	var count = 0;
	
	for (var i = 0; i < rows.length; i++) {
		
		var ordPdQty = Number(ord_pd_qty[i].textContent.replace(/[^0-9.-]/g, '')); //주문수량
		var PdAllStk = Number(all_pd_stock[i].textContent.replace(/[^0-9.-]/g, '')); //재고수
		var outPdQty = Number(out_pd_qty[i].value.trim()); //출고수량
		
		if (out_pd_code[i].textContent == "") {
			alert("출고되는 상품 목록이 없습니다.\n 주문내역을 다시 확인해주세요.");
			return;
		} 
		
		if (outPdQty == "" || isNaN(outPdQty) || outPdQty <= 0) {
			alert("출고되는 수량을 정확히 입력해주세요");
			out_pd_qty[i].focus();
			return;
		}

		if(outPdQty > PdAllStk) {
			alert("제품의 출고수량이 전체재고수보다 많습니다.\n 다시 입력해주세요");
			out_pd_qty[i].focus();
			return;
		}
		
		if(outPdQty != ordPdQty) {
			count++;
		}
	} 
	if(count>0){
		if(confirm("제품의 출고수량이 주문수량과 다릅니다.\n 이대로 출고하시겠습니까?")){
			return true;
		}
	}else if(count==0){
		return true;
	}
}
		

/*
//미납되는 상품의 처리 
function noOut(out_value){
	var tr=out_value.closest("tr");
	var qty_input = tr.querySelector(".out_pd_qty");
	
	if(out_value.value == "미납"){
		qty_input.value = "0";
		qty_input.disabled = true;
		
	}else{
		qty_input.disabled = false;		
	}
}
*/
/*--------------------------------------------------------------
출고저장 ajax
--------------------------------------------------------------*/
function outBndInsertOk(){
	var out_pds = [];
	
	out_pds.push({
		ORD_CODE : ord_code.value,
		OUTBOUND_DATE: out_date.value,
		OUT_STATUS: out_status.value,
		EMPLOYEE_CODE : employee_code.value,
        OUT_MEMO: ob_memo.value,
	});	
	
	var tbody = document.querySelector("#outbnd_pds");
	var rows = tbody.querySelectorAll('tr.pd_row');
	
	rows.forEach(row => {
		var pdCd = row.querySelector(".out_pd_code");  //상품코드
		var outPdQty = row.querySelector(".out_pd_qty");  //출고수량
		
		//다 입력했으면 배열에 넣기
      	out_pds.push({
			PRODUCT_CODE: pdCd.textContent,
			OUT_PRODUCT_QTY:outPdQty.value,
      	});
	});
	
	fetch("./outbound_insertok.do", {
		method: "PUT",
		headers: {'content-type': 'application/json'},
		body : JSON.stringify(out_pds)
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		if(result=="all_complate"){
			alert("출고 처리가 완료되었습니다.");
			location.href="./outbound.do";
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}


/*--------------------------------------------------------------
출고건 상세보기 모달 
--------------------------------------------------------------*/
function openObDetail(event){
	
	var outbnd_code = event.querySelector(".ob_code").textContent.trim();
	var ord_code = event.querySelector(".ord_code").textContent.trim();  

	fetch("./outbnd_detail_modal.do?outbnd_code="+outbnd_code+"&ord_code="+ord_code, {
		method: "GET",
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
			var modal;
			document.getElementById("modalContainer").innerHTML = result;
			modal = new bootstrap.Modal(document.getElementById("out_detail_modal"));
			modal.show();
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}



















/*--------------------------------------------------------------
출고리스트 페이징 
--------------------------------------------------------------*/
function go_out_pg(ee){
	var keyword = ee.getAttribute('data-keyword');
	var page_no = ee.getAttribute('data-pageno');
	var status = ee.getAttribute('data-status');
	
	var params = {  
		    pageno: page_no,
		    post_ea: ee.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}

		if (status) {
		    var statusList = status.split(',');  // 배열로 분리
		    statusList.forEach((s) => {
		        params["out_status_lst"] = params["out_status_lst"] || [];
		        params["out_status_lst"].push(s);
		    });
		}
		var pString = new URLSearchParams(params).toString();
		location.href = "./outbound.do?" + pString;
}


/*-------------------------------------------------------------
출고리스트 게시물 개수 선택 
--------------------------------------------------------------*/
function outPostEa(){
	var form = document.querySelector("#obpg_frm");
	form.method = "GET";
	form.action = "./outbound.do";
	form.pageno.value = 1; 
	form.submit();
}

