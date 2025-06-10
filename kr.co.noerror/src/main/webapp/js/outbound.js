var ord_code = document.querySelector("#ord_code");
var comp_code = document.querySelector("#comp_code");
var out_wh_code = document.querySelector("#out_wh_code");
var out_wh_name = document.querySelector("#out_wh_name");
var out_date = document.querySelector("#out_date");
var out_status = document.querySelector("#out_status");
//var employee_code = document.querySelector("#employee_code");
var ob_memo = document.querySelector("#ob_memo");

/*--------------------------------------------------------------
출고리스트 수기등록 버튼 클릭 
--------------------------------------------------------------*/
function addOutbound(){
	location.href="./outbound_insert.do";
}


/*--------------------------------------------------------------
출고리스트 저장버튼 클릭 
--------------------------------------------------------------*/
function insert_outBnd(){
	var tbody = document.querySelector("#outbnd_pds");
	var rows = tbody.querySelectorAll('tr.pd_row');
	
	var out_pd_code = document.querySelectorAll(".out_pd_code");
	var out_pd_qty = document.querySelectorAll(".out_pd_qty");
	var ind_out_status = document.querySelectorAll(".ind_out_status");
	
	
	var outDate = new Date(out_date.value);
	var today = new Date();
    outDate.setHours(0, 0, 0, 0);
	today.setHours(0, 0, 0, 0);
	
	if(ord_code.value==""){
		alert("출고할 주문건을 선택하세요.");
		ord_code.focus();		
		
	}else if(out_wh_code.value == ""){
		alert("창고이름을 선택하세요.");
		out_wh_name.focus();	
		
	}else if(out_date.value == ""){
		alert("출고예정일자를 선택하세요.");
		out_date.focus();	
		
	}else if(outDate < today){
		alert("출고일자는 오늘 이전의 날짜를 선택할 수 없습니다.");
		out_date.value = "";  //선택날짜 초기화
		out_date.focus();	
		
	}else if(out_status.value == ""){
		alert("입고상태를 선택하세요.");
		out_status.focus();	
		
	}else if(employee_code.value==""){
		alert("책임 담당자가 등록되지 않았습니다. ");
		
	}else{
		// 각 항목 입력 확인
		for (var i = 0; i < rows.length; i++) {
			if (out_pd_code[i].value == "") {
				alert("출고되는 상품이 없습니다.\n주문건을 다시 확인해주세요");
				out_pd_code[i].focus();
				return;
			}
			
			if (out_pd_qty[i].value.trim() == "" || isNaN(out_pd_qty[i].value) || Number(out_pd_qty[i].value) < 0) {
				alert("출고되는 수량을 정확히 입력해주세요");
				out_pd_qty[i].focus();
				return;
			}else if(!reg_num.test(out_pd_qty[i].value)){
				alert("수량은 숫자만 입력이 가능합니다.");
				out_pd_qty[i].focus();
				return;
			}
			
			if (ind_out_status[i].value.trim() == "") {
				alert("출고상태를 선택해주세요");
				ind_out_status[i].focus();
				noOut(ind_out_status[i]);
				return;
			}
		}
		outBndInsertOk();
	}
} 

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

/*--------------------------------------------------------------
출고저장 ajax
--------------------------------------------------------------*/
function outBndInsertOk(){
	var out_pds = [];
	
	out_pds.push({
		ORD_CODE : ord_code.value,
        WH_CODE: out_wh_code.value.trim(),
		OUT_STATUS: out_status.value,
		OUTBOUND_DATE: out_date.value,
		EMPLOYEE_CODE : employee_code.value,
        OUT_MEMO: ob_memo.value,
	});	
	
	var tbody = document.querySelector("#outbnd_pds");
	var rows = tbody.querySelectorAll('tr.pd_row');
	
	rows.forEach(row => {
		var pdCd = row.querySelector(".out_pd_code");
		var pdQty = row.querySelector(".out_pd_qty");
		var pdOutSt = row.querySelector(".ind_out_status");
		
		//다 입력했으면 배열에 넣기
      	out_pds.push({
			
			PRODUCT_CODE: pdCd.textContent,
			PRODUCT_QTY:pdQty.value,
			IND_OUT_STATUS : pdOutSt.value
      	});
	});
	console.log(out_pds)
	fetch("./outbound_insertok.do", {
		method: "PUT",
		headers: {'content-type': 'application/json'},
		body : JSON.stringify(out_pds)
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		if(result=="ok"){
			alert("출고 등록이 완료되었습니다.");
			location.href="./outbound.do";
		}

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

