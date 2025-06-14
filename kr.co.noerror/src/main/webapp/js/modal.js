/*--------------------------------------------------------------
	거래처 리스트 모달 
----------------------------------------------------------- */
function cltListOpen(parentType){
	fetch("./client_list.do?parent="+parentType, {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		
		var modal= new bootstrap.Modal(document.getElementById("client_list"));
		modal.show();
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

//거래처리스트 모달 페이징
function cl_modal_pg (page){
	var parent = page.getAttribute('data-parent');
	var keyword = page.getAttribute('data-keyword');
	var page_no = page.getAttribute('data-pageno');
	
	var params = {  
			parent: parent,  
		  //  type: page.getAttribute('data-type'),
		    pageno: page_no,
		    post_ea: page.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}
		var pString = new URLSearchParams(params).toString();
		
		
	fetch("./client_list.do?"+pString+"&mode=modal2", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.querySelector('#client_list .modal-body').innerHTML = result;
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
	발주처리스트 모달 오픈 
----------------------------------------------------------- */
function pcltListOpen(){
	fetch("./client_list2.do", {
			method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		
		var modal= new bootstrap.Modal(document.getElementById("p_client_list"));
		modal.show();
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

//발주처리스트 모달 페이징
function cl2_modal_pg (page){
	var keyword = page.getAttribute('data-keyword');
	var page_no = page.getAttribute('data-pageno');
	
	var params = {  
		    pageno: page_no,
		    post_ea: page.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}
		var pString = new URLSearchParams(params).toString();
		
		
	fetch("./client_list2.do?"+pString+"&mode=modal2", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.querySelector('#p_client_list .modal-body').innerHTML = result;
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
	부자재리스트 모달 오픈
----------------------------------------------------------- */
//bom등록화면에서 완제품 미선택시 아이템 추가 방지 
function open_itm_list() {
    var productCode = document.getElementById('product_code').value;
    
    if (!productCode || productCode.trim() === "") {
        alert("제품을 먼저 선택해주세요.");
        return; 
    }
    openItemList('bomPage'); 
}

//부자재리스트 모달 오픈 
function openItemList(parentType){
	fetch("./item_list.do?parent="+parentType, {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		
		var modal= new bootstrap.Modal(document.getElementById("items_list"));
		modal.show();
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}


//부자재리스트 모달 페이징
function itm_modal_pg (page){
	console.log(page)
	//var parent = page.getAttribute('data-parent');
	var keyword = page.getAttribute('data-keyword');
	var page_no = page.getAttribute('data-pageno');
	
	var params = {  
			parent: parent,  
		    type: page.getAttribute('data-type'),
		    pageno: page_no,
		    post_ea: page.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}
		var pString = new URLSearchParams(params).toString();
		
		
	fetch("./item_list.do?"+pString+"&mode=modal2", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.querySelector('#items_list .modal-body').innerHTML = result;
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
	완제품 리스트 모달 오픈 
----------------------------------------------------------- */
function pdListOpen(prt){
	
	fetch("./product_md_list.do?parent="+prt, {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		
		var modal= new bootstrap.Modal(document.getElementById("product_list"));
		modal.show();
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

//완제품 모달 페이징
function pd_modal_pg (page){
	var parent = page.getAttribute('data-parent');
	var keyword = page.getAttribute('data-keyword');
	var page_no = page.getAttribute('data-pageno');
	
	var params = {  
			parent: parent, 
		    pageno: page_no,
		    post_ea: page.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}
		var pString = new URLSearchParams(params).toString();
		
		
	fetch("./product_md_list.do?"+pString+"&mode=modal2", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.querySelector('#product_list .modal-body').innerHTML = result;
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}





//완제품 모달 페이징(product_list_body_modal2)
function pd_modal_pg2 (page){
	var keyword = page.getAttribute('data-keyword');
	var page_no = page.getAttribute('data-pageno');
	
	var params = {  
		    pageno: page_no,
		    post_ea: page.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}
		var pString = new URLSearchParams(params).toString();
		
		
	fetch("./products_modal.do?"+pString+"&mode=modal2", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.querySelector('#products_modal_for_order .modal-body').innerHTML = result;
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
	bom등록된 제품 리스트 모달 오픈 
----------------------------------------------------------- */
function bomListOpen(){
	
	fetch("./bom_md_list.do", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		
		var modal= new bootstrap.Modal(document.getElementById("bom_list_modal"));
		modal.show();
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

//bom완제품 모달 페이징
function bom_modal_pg (page){
	var parent = page.getAttribute('data-parent');
	var keyword = page.getAttribute('data-keyword');
	var page_no = page.getAttribute('data-pageno');
	
	var params = {  
			parent: parent, 
		    pageno: page_no,
		    post_ea: page.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}
		var pString = new URLSearchParams(params).toString();
		
		
	fetch("./bom_md_list.do?"+pString+"&mode=modal2", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.querySelector('#bom_list_modal .modal-body').innerHTML = result;
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}



/*--------------------------------------------------------------
	입고건 리스트 모달 오픈
----------------------------------------------------------- */
function inbndListOpen(){
	fetch("./inbound_list.do", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		
		var modal= new bootstrap.Modal(document.getElementById("inbounds_list"));
		modal.show();
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

//입고리스트 모달 페이징
function inbnd_modal_pg (page){
	var keyword = page.getAttribute('data-keyword');
	var page_no = page.getAttribute('data-pageno');
	
	var params = {  
		    type: page.getAttribute('data-type'),
		    pageno: page_no,
		    post_ea: page.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}
		var pString = new URLSearchParams(params).toString();
		
		
	fetch("./inbound_list.do?"+pString+"&mode=modal2", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.querySelector('#inbounds_list .modal-body').innerHTML = result;
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
	주문건 리스트 모달 오픈 ('주문확인' 상태인것만 오픈)
----------------------------------------------------------- */
function ordListOpen(parentType){
	const statuses = ["주문확인"];
	/*const statuses = new URLSearchParams();
	statuses.append("statuses", "주문확인");
	
	const planStatus = ["생산계획수립", "생산계획확정", "생산중", "생산완료", "생산지연"];
	planStatus.forEach(plstatus => {
	  statuses.append("plan_status", plstatus);
	});*/

	fetch("./ord_list_modal.do?statuses="+statuses+"&parent="+parentType, {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
				
		var modal= new bootstrap.Modal(document.getElementById("ordreq_list"));
		modal.show();
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

//주문리스트 모달 페이징
function ord_modal_pg (page){
	var keyword = page.getAttribute('data-keyword');
	var pageno = page.getAttribute('data-pageno');
	
	var params = {  
		    page_no: pageno,
		    page_size: page.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["search_word"] = keyword;
		}
		var pString = new URLSearchParams(params).toString();
		
		
	fetch("./ord_list_modal.do?"+pString+"&mode=modal2", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.querySelector('#ordreq_list .modal-body').innerHTML = result;
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
	발주건 리스트 모달 오픈
----------------------------------------------------------- */
function pchListOpen(){
	fetch("./pch_list_modal.do", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		
		var modal= new bootstrap.Modal(document.getElementById("purchase_list"));
		modal.show();
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

//발주리스트 모달 페이징
function pch_modal_pg (page){
	var search_word = page.getAttribute('data-keyword');
	var pageno = page.getAttribute('data-pageno');
	
	var params = {  
		    pageno: pageno,
		    page_ea: page.getAttribute('data-pea'),
		};
		
		if (search_word) {  //키워드가 있으면
		    params["search_word"] = search_word;
		}
		var pString = new URLSearchParams(params).toString();
		
		
	fetch("./pch_list_modal.do?"+pString+"&mode=modal2", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.querySelector('#purchase_list .modal-body').innerHTML = result;
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
	사원 리스트 모달 오픈
----------------------------------------------------------- */
function empListOpen(){
	fetch("./emp_list_modal.do", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		
		var modal= new bootstrap.Modal(document.getElementById("member_list_modal"));
		modal.show();
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

//발주리스트 모달 페이징
function emp_modal_pg (page){
	var search_word = page.getAttribute('data-keyword');
	var pageno = page.getAttribute('data-pageno');
	
	var params = {  
		    pageno: pageno,
		    page_ea: page.getAttribute('data-pea'),
		};
		
		if (search_word) {  //키워드가 있으면
		    params["search_word"] = search_word;
		}
		var pString = new URLSearchParams(params).toString();
		
		
	fetch("./emp_list_modal.do?"+pString+"&mode=modal2", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.querySelector('#member_list_modal .modal-body').innerHTML = result;
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
	출고요청 품목 모달  
----------------------------------------------------------- */
function pdOutReq(){
	var out_list = document.querySelectorAll("input[name='checkCodes']:checked");
	var out_req = new Array();
	
	if (out_list.length == 0) {
		alert("출고시킬 항목을 선택해주세요.");
	}
	else{
		out_list.forEach(chkOut => {
			var out_pidx = chkOut.getAttribute("data-pdidx");		
			var out_pd_code = chkOut.getAttribute("data-product_code");
			
			out_req.push({ 
				idx: out_pidx, 
				code: out_pd_code
			})
		});
		
		fetch("./outPd_listMd.do", {
			method: "POST",
			headers: {"content-type": "application/json"},
			body: JSON.stringify(out_req)
	
		})
		.then(function(data) {
			return data.text();
		})
		.then(function(result) {
			document.getElementById("modalContainer").innerHTML = result;
			
			fetch("./outPd_listMd2.do", {
				method: "POST",
				headers: {"content-type": "application/json"},
				body: JSON.stringify(out_req)
			})
			.then(function(response) {
				return response.json();
			})
			.then(function(jsonList) {
				
				var outModal = document.getElementById("out_pd_modal");
				var outpd_tbody = outModal.querySelector('#out_pds');
				
				outpd_tbody.innerHTML = ''; // 기존 행 제거
				appendOutPdsRow(outpd_tbody, jsonList); // 새 행 추가
				
				var modal= new bootstrap.Modal(outModal);
				modal.show();
			})
			.catch(function(error) {
				console.log("데이터로드실패" + error);
			});
		}).catch(function(error) {
			console.log("통신오류발생" + error);
		});
	}
}

//모달에 출고상품 테이블 뭍여넣기 
function appendOutPdsRow(tbody, dataList){
	
	dataList.forEach((outList,i) => {
		
		var outTr = document.createElement('tr');
		outTr.className = "outPd_row";
		outTr.setAttribute("data-whcd", outList.wh_code);
		
		outTr.innerHTML = `
		    <td style="width:10%;">`+(i+1)+`</td>
		    <td style="width:20%;" class="pdCd">`+outList.product_code+`</td>
		    <td style="width:50%;" class="pdNm">`+outList.product_name+`</td>
		    <td style="width:10%;" >`+outList.stock_qty+`</td>
		    <td style="width:10%;"><input type="number" name="out_qty" class="form-control out_qty" ></td>
		  `;
		  tbody.append(outTr);
	});	
}

//상품 출고
function goOutList(){
	var outPdTrList = document.querySelectorAll("tr.outPd_row");
	var empCd = document.querySelector("#employee_code");
	var qty = [];
	
	outPdTrList.forEach(inputData => {
		var outPdCd = inputData.querySelector(".pdCd").textContent;
		var outPdQty = inputData.querySelector("input[name='out_qty']").value;
		var outwh = inputData.getAttribute("data-whcd");
		var outPdnm = inputData.querySelector(".pdNm").textContent;
		
		qty.push({
			pdcode : outPdCd,
			outqty : outPdQty,
			outwh : outwh,
			outnm : outPdnm
		}); 
	});
	var alldata = {
	  empcode: empCd.value,
	  outList: qty  
	};
	
	console.log(JSON.stringify(alldata))
	fetch("./outProduct.do", {
		method: "POST",
		headers: {"content-type": "application/json"},
		body: JSON.stringify(alldata)
	})
	.then(function(data) {
		return data.text();
	})
	.then(function(result) {

	
	
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	})
	
	
	
}
