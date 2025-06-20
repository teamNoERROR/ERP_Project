var pch_code = document.querySelector("#pch_code");
var comp_code = document.querySelector("#comp_code");
var wh_name = document.querySelector("#in_wh_name");
var wh_code = document.querySelector("#in_wh_code");
var inbound_date = document.querySelector("#inbound_date");
var in_status = document.querySelector("#in_status");
var employee_code = document.querySelector("#employee_code");
var inb_memo = document.querySelector("#inb_memo");


/*--------------------------------------------------------------
입고리스트 수기등록 버튼 클릭 
--------------------------------------------------------------*/
function addInbound(){
	location.href="./inbound_insert.do";
}

inbound_date.valueAsDate = new Date();	
/*--------------------------------------------------------------
입고리스트 저장버튼 클릭 
--------------------------------------------------------------*/
function insert_inBnd(){
	var tbody = document.querySelector("#inbnd_items");
	var rows = tbody.querySelectorAll('tr.item_row');
	
	var item_qty = document.querySelectorAll(".item_qty");  //입고된 양
	var item_exp = document.querySelectorAll(".item_exp");  //유통기한
	var item_deli = document.querySelectorAll(".item_deli");  //입고상태
	//console.log(item_cd.textContent)
	
	var inbDate = new Date(inbound_date.value);
	var today = new Date();
    inbDate.setHours(0, 0, 0, 0);
	today.setHours(0, 0, 0, 0);
	
	if(pch_code.value==""){
		alert("발주내역을 선택하세요.");
		pch_code.focus();		
		
	}else if(wh_code.value == ""){
		alert("창고이름을 선택하세요.");
		wh_name.focus();	
		
	}else if(inbound_date.value == ""){
		alert("입고일자를 선택하세요.");
		inbound_date.focus();	
		
	}else if(inbDate > today){
		alert("입고일자는 오늘 이후의 날짜를 선택할 수 없습니다.");
		inbound_date.value = "";  //선택날짜 초기화
		inbound_date.focus();	
		
	}else if(in_status.value == ""){
		alert("입고상태를 선택하세요.");
		in_status.focus();	
		
	}else if(employee_code.value==""){
		alert("책임 담당자가 등록되지 않았습니다. ");
		
	}else{
		// 각 항목 입력 확인
		for (var i = 0; i < rows.length; i++) {
			var expDate = new Date(item_exp[i].value);
			expDate.setHours(0, 0, 0, 0);
			
			if(item_deli[i].value.trim() != "미납"){
				if (item_qty[i].value.trim() == "" || isNaN(item_qty[i].value) || Number(item_qty[i].value) <= 0) {
					alert("입고된 수량을 정확히 입력해주세요");
					item_qty[i].focus();
					return;
				}else if(!reg_num.test(item_qty[i].value)){
					alert("수량은 숫자만 입력이 가능합니다.");
					item_qty[i].focus();
					return;
				}
				if (item_exp[i].value.trim() == "") {
					alert("입고된 상품의 유통기한을 입력해주세요");
					item_exp[i].focus();
					return;
				}else if(expDate < today){
					alert("유통기한은 오늘 이전의 날짜를 선택할 수 없습니다.");
					item_exp[i].value = "";  //선택날짜 초기화
					item_exp[i].focus();	
					return;
				}
			}
			if (item_deli[i].value.trim() == "") {
				alert("입고상태를 선택해주세요");
				item_deli[i].focus();
				noDeli(item_deli[i]);
				return;
			}
		}
		inbndInsertOk();
	}
} 

//미납된 상품의 처리 
function noDeli(deli_value){
	var tr=deli_value.closest("tr");
	var exp_input = tr.querySelector(".item_exp");
	var qty_input = tr.querySelector(".item_qty");
	
	if(deli_value.value == "미납"){
		exp_input.value = "0001-01-01";
		exp_input.disabled = true;
		
		qty_input.value = "0";
		qty_input.disabled = true;
		
	}else{
		exp_input.value = "";
		exp_input.disabled = false;
		qty_input.disabled = false;		
	}
}

/*--------------------------------------------------------------
입고저장 ajax
--------------------------------------------------------------*/
function inbndInsertOk(){
	var in_items = [];
	var tbody = document.querySelector("#inbnd_items");
	var rows = tbody.querySelectorAll('tr.item_row');
	
	rows.forEach(row => {
		var itmCd = row.querySelector(".item_code");
		var itmQty = row.querySelector(".item_qty");
		var itmExp = row.querySelector(".item_exp");
		var itmDeli = row.querySelector(".item_deli");
		console.log(itmCd.textContent)
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

/*--------------------------------------------------------------
입고건 상세보기 모달 
--------------------------------------------------------------*/
function openInbndDetail(event){
	
	var inbnd_code = event.querySelector(".inbnd_code").textContent.trim();
	var pch_cd =   event.querySelector(".pch_code").textContent.trim();  //pch-00000 
	var ind_pcd = event.querySelector(".pch_code").getAttribute("data-indpch"); //pch-00000_1

	fetch("./inbnd_detail_modal.do?pch_code="+pch_cd+"&inbnd_code="+inbnd_code+"&ind_pcd="+ind_pcd, {
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

/*--------------------------------------------------------------
입고상태 변경 
--------------------------------------------------------------*/
function inbCngOk(){
	var inbnd_ckbx = document.querySelectorAll("input[name='inb_sel']:checked");
	var in_status = document.querySelector("#in_status");
	
	if(inbnd_ckbx.length==0){
		alert("입고상태를 변경할 상품이 선택되지 않았습니다.");
		
	}else if(in_status.value == ""){
		alert("입고상태를 선택하세요.");
		
	}else{

		var status_chg = new Array();
		inbnd_ckbx.forEach(inchk => {
			inb_code = inchk.getAttribute("data-inbcode");
			indpchcd = inchk.getAttribute("data-indpch");
			
			status_chg.push({ 
				code: inb_code, 
				code2: indpchcd,
				statusinb : in_status.value
			})
		});
		
		console.log(JSON.stringify(status_chg));
		
		fetch("./inbnd_ok.do", {
			method: "PATCH",
			headers: {"content-type": "application/json"},
			body: JSON.stringify(status_chg)
				
		}).then(function(data) {
			return data.text();

		}).then(function(result) {
			if (result == "ok") {
				alert("상태 변경이 완료되었습니다.");
		       	
				// 체크된 체크박스들 비활성화
				 inbnd_ckbx.forEach(cb => {
				     cb.disabled = true;
				 });
				location.reload();
				
			}else if(result=="fail") {
				alert("시스템 문제로 일부 제품의 상태 변경에 실패했습니다.");
				location.reload();
				
			}else{
				console.log(result);
			}
		}).catch(function(error) {
			console.log("통신오류발생" + error);
		});
		
	}
}



/*--------------------------------------------------------------
체크박스 클릭시 리스트 변경 
--------------------------------------------------------------*/
function inStatusCk(ck){
	const params = new URLSearchParams();
	const checked = document.querySelectorAll('input[name="status_lst"]:checked');
	if(checked.length > 0){
		Array.from(checked).forEach(cb => {
		    params.append('status_lst', cb.value);
	  	});
	}else {
		alert("상태값을 최소 하나는 선택해야 합니다.");
		ck.checked=true;
		return;
	}
	const keyword = document.querySelector('input[name="keyword"]');
	if (keyword && keyword.value.trim() != "") {
		params.append('keyword', keyword.value.trim());
	}
	
	location.href = "inbound.do?" + params.toString();
}  


//검색버튼 
function inSearch(){
	var form = document.querySelector("#inbnd_search");
	if(form.keyword.value.trim() == ""){
		alert("검색어를 입력하세요");
		return false;
	}else {
		
		form.method = "GET";
		form.action = "./inbound.do";
		return true;
	}
	
}
/*--------------------------------------------------------------
입고리스트 페이징 
--------------------------------------------------------------*/
function go_in_pg(ee){
	var keyword = ee.getAttribute('data-keyword');
	var page_no = ee.getAttribute('data-pageno');
	var status_lst = ee.getAttribute('data-status');
	
	var params = {  
		    type: ee.getAttribute('data-type'),
		    pageno: page_no,
		    post_ea: ee.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}

		if (status_lst) {
		    var statusList = status_lst.split(',');  // 배열로 분리
		    statusList.forEach((s) => {
		        params["status_lst"] = params["status_lst"] || [];
		        params["status_lst"].push(s);
		    });
		}
		var pString = new URLSearchParams(params).toString();
		location.href = "./inbound.do?" + pString;
}


/*--------------------------------------------------------------
입고리스트 게시물 개수 선택 
--------------------------------------------------------------*/
function inbPostEa(type){
	var form = document.querySelector("#inbpg_frm");
	form.method = "GET";
	form.action = "./inbound.do";
	form.type.value=type;
	form.pageno.value = 1; //
	
	form.submit();
}