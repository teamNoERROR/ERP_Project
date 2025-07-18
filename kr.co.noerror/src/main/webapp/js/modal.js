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
function pcl_modal_pg (page){
	var parent = "pcl";
	var keyword = "";
	var pageno = "1";
	var post_ea = 5;
	var type = '';
	
	// 페이지 버튼에서 호출된 경우 버튼 클릭 시 페이징 처리
	if (page instanceof HTMLElement) {
		keyword = page.getAttribute('data-keyword') || '';
		parent = page.getAttribute('data-parent') || 'pcl';
		pageno = page.getAttribute('data-pageno') || 1;
		post_ea = page.getAttribute('data-pea') || 5;
		type = page.getAttribute('data-type') || '';
	} 
	// 검색창에서 직접 호출된 경우
	else {
		keyword = document.getElementById("keyword")?.value || '';
		parent = document.querySelector('[data-parenttype]')?.getAttribute('data-parenttype') || 'pcl';
		pageno = 1;
	}
	
	var params = {  
		parent: parent,  
	    type: type,
	    pageno: pageno,
	    post_ea: post_ea,
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
	var parent = "cl2";
	var keyword = "";
	var pageno = "1";
	var post_ea = 5;
	var type = '';
	
	// 페이지 버튼에서 호출된 경우 버튼 클릭 시 페이징 처리
	if (page instanceof HTMLElement) {
		keyword = page.getAttribute('data-keyword') || '';
		parent = page.getAttribute('data-parent') || 'cl2';
		pageno = page.getAttribute('data-pageno') || 1;
		post_ea = page.getAttribute('data-pea') || 5;
		type = page.getAttribute('data-type') || '';
	} 
	// 검색창에서 직접 호출된 경우
	else {
		keyword = document.getElementById("keyword")?.value || '';
		parent = document.querySelector('[data-parenttype]')?.getAttribute('data-parenttype') || 'cl2';
		pageno = 1;
	}
	
	var params = {  
			parent: parent,  
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
	var parent = "item";
	var keyword = "";
	var pageno = "1";
	var post_ea = 5;
	var type = '';
	
	// 페이지 버튼에서 호출된 경우 버튼 클릭 시 페이징 처리
	if (page instanceof HTMLElement) {
		keyword = page.getAttribute('data-keyword') || '';
		parent = page.getAttribute('data-parent') || 'item';
		pageno = page.getAttribute('data-pageno') || 1;
		post_ea = page.getAttribute('data-pea') || 5;
		type = page.getAttribute('data-type') || '';
	} 
	// 검색창에서 직접 호출된 경우
	else {
		keyword = document.getElementById("keyword")?.value || '';
		parent = document.querySelector('[data-parenttype]')?.getAttribute('data-parenttype') || 'item';
		pageno = 1;
	}
	
	var params = {  
			parent: parent,  
		    type: type,
		    pageno: pageno,
		    post_ea: post_ea,
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
	var parent = "product";
	var keyword = "";
	var pageno = "1";
	var post_ea = 5;
	var type = '';
	
	// 페이지 버튼에서 호출된 경우 버튼 클릭 시 페이징 처리
	if (page instanceof HTMLElement) {
		keyword = page.getAttribute('data-keyword') || '';
		parent = page.getAttribute('data-parent') || 'product';
		pageno = page.getAttribute('data-pageno') || 1;
		post_ea = page.getAttribute('data-pea') || 5;
		type = page.getAttribute('data-type') || '';
	} 
	// 검색창에서 직접 호출된 경우
	else {
		keyword = document.getElementById("keyword")?.value || '';
		parent = document.querySelector('[data-parenttype]')?.getAttribute('data-parenttype') || 'product';
		pageno = 1;
	}
	
	var params = {  
			parent: parent,  
		    type: type,
		    pageno: pageno,
		    post_ea: post_ea,
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
	var parent = "product2";
	var keyword = "";
	var pageno = "1";
	var post_ea = 5;
	var type = '';
	
	// 페이지 버튼에서 호출된 경우 버튼 클릭 시 페이징 처리
	if (page instanceof HTMLElement) {
		keyword = page.getAttribute('data-keyword') || '';
		parent = page.getAttribute('data-parent') || 'product2';
		pageno = page.getAttribute('data-pageno') || 1;
		post_ea = page.getAttribute('data-pea') || 5;
		type = page.getAttribute('data-type') || '';
	} 
	// 검색창에서 직접 호출된 경우
	else {
		keyword = document.getElementById("keyword")?.value || '';
		parent = document.querySelector('[data-parenttype]')?.getAttribute('data-parenttype') || 'product2';
		pageno = 1;
	}
	
	var params = {  
			parent: parent,  
		    type: type,
		    pageno: pageno,
		    post_ea: post_ea,
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
function bomListOpen(parent){
	console.log(parent)
	fetch("./bom_md_list.do?parent="+parent, {
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
	var parent = "bom";
	var keyword = "";
	var pageno = "1";
	var post_ea = 5;
	var type = '';
	
	// 페이지 버튼에서 호출된 경우 버튼 클릭 시 페이징 처리
	if (page instanceof HTMLElement) {
		keyword = page.getAttribute('data-keyword') || '';
		parent = page.getAttribute('data-parent') || 'bom';
		pageno = page.getAttribute('data-pageno') || 1;
		post_ea = page.getAttribute('data-pea') || 5;
		type = page.getAttribute('data-type') || '';
	} 
	// 검색창에서 직접 호출된 경우
	else {
		keyword = document.getElementById("keyword")?.value || '';
		parent = document.querySelector('[data-parenttype]')?.getAttribute('data-parenttype') || 'bom';
		pageno = 1;
	}
	
	var params = {  
			parent: parent,  
		    type: type,
		    pageno: pageno,
		    post_ea: post_ea,
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
	var parent = "inbnd";
	var keyword = "";
	var pageno = "1";
	var post_ea = 5;
	var type = '';
	
	// 페이지 버튼에서 호출된 경우 버튼 클릭 시 페이징 처리
	if (page instanceof HTMLElement) {
		keyword = page.getAttribute('data-keyword') || '';
		parent = page.getAttribute('data-parent') || 'inbnd';
		pageno = page.getAttribute('data-pageno') || 1;
		post_ea = page.getAttribute('data-pea') || 5;
		type = page.getAttribute('data-type') || '';
	} 
	// 검색창에서 직접 호출된 경우
	else {
		keyword = document.getElementById("keyword")?.value || '';
		parent = document.querySelector('[data-parenttype]')?.getAttribute('data-parenttype') || 'inbnd';
		pageno = 1;
	}
	
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
	var parent = "ord";
	var keyword = "";
	var pageno = "1";
	var post_ea = 5;
	var type = '';
	
	// 페이지 버튼에서 호출된 경우 버튼 클릭 시 페이징 처리
	if (page instanceof HTMLElement) {
		keyword = page.getAttribute('data-keyword') || '';
		parent = page.getAttribute('data-parent') || 'ord';
		pageno = page.getAttribute('data-pageno') || 1;
		post_ea = page.getAttribute('data-pea') || 5;
		type = page.getAttribute('data-type') || '';
	} 
	// 검색창에서 직접 호출된 경우
	else {
		keyword = document.getElementById("keyword")?.value || '';
		parent = document.querySelector('[data-parenttype]')?.getAttribute('data-parenttype') || 'ord';
		pageno = 1;
	}
	
	var params = {  
			parent: parent,  
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
	const statuses = ["발주완료"];
	fetch("./pch_list_modal.do?statuses="+statuses, {
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
	var parent = "pch";
	var search_word = "";
	var pageno = "1";
	var post_ea = 5;
	var type = '';
	
	// 페이지 버튼에서 호출된 경우 버튼 클릭 시 페이징 처리
	if (page instanceof HTMLElement) {
		search_word = page.getAttribute('data-keyword') || '';
		parent = page.getAttribute('data-parent') || 'pch';
		pageno = page.getAttribute('data-pageno') || 1;
		post_ea = page.getAttribute('data-pea') || 5;
		type = page.getAttribute('data-type') || '';
	} 
	// 검색창에서 직접 호출된 경우
	else {
		search_word = document.getElementById("keyword")?.value || '';
		parent = document.querySelector('[data-parenttype]')?.getAttribute('data-parenttype') || 'pch';
		pageno = 1;
	}
	
	var params = {  
			parent: parent,  
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
function empListOpen(parentType){
	fetch("./emp_list_modal.do?parent="+parentType, {
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

//사원리스트 모달 페이징
function emp_modal_pg (page){
	var parent = "emp";
	var search_word = "";
	var pageno = "1";
	var post_ea = 5;
	var type = '';

	// 페이지 버튼에서 호출된 경우 버튼 클릭 시 페이징 처리
	if (page instanceof HTMLElement) {
		search_word = page.getAttribute('data-keyword') || '';
		parent = page.getAttribute('data-parent') || 'emp';
		pageno = page.getAttribute('data-pageno') || 1;
		post_ea = page.getAttribute('data-pea') || 5;
		type = page.getAttribute('data-type') || '';
	} 
	// 검색창에서 직접 호출된 경우
	else {
		search_word = document.getElementById("keyword")?.value || '';
		parent = document.querySelector('[data-parenttype]')?.getAttribute('data-parenttype') || 'emp';
		pageno = 1;
	}
	
	var params = {  
			parent: parent,  
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
//출고요청 버튼 클릭 
function pdOutReq(){
	var out_list = document.querySelectorAll("input[name='checkCodes']:checked");
	var out_wh = document.querySelector("#wh_code");
	var out_req = new Array();
	
	if (out_list.length == 0) {
		alert("출고시킬 항목을 선택해주세요.");
	}
	/*else if (out_wh.value == ""){
		alert("출고시킬 창고를 선택해주세요.");
	}*/
	else{
		out_list.forEach(chkOut => {
			var out_pidx = chkOut.getAttribute("data-pdidx");		
			var out_pd_code = chkOut.getAttribute("data-product_code");
			var out_wh_code = chkOut.getAttribute("data-wh_code");
			
			out_req.push({ 
				idx: out_pidx, 
				code: out_pd_code,
				wh_code : out_wh_code
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
			.then(function(out_pd_list) {
				
				var outModal = document.getElementById("out_pd_modal");
				var outpd_tbody = outModal.querySelector('#out_pds');
				
				outpd_tbody.innerHTML = ''; // 기존 행 제거
				appendOutPdsRow(outpd_tbody, out_pd_list); // 새 행 추가
				
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
		    <td style="width:5%;">`+(i+1)+`</td>
			<td style="width:10%;" class="whCd">`+outList.wh_code+`</td>
			<td style="width:15%;" class="whNm">`+outList.wh_name+`</td>
		    <td style="width:10%;" class="pdCd">`+outList.product_code+`</td>
		    <td style="width:20%;" class="pdNm">`+outList.product_name+`</td>
		    <td style="width:10%;" >`+outList.stock_qty+`</td>	
			<td style="width:10%;" class="wh_pd_qty">`+outList.pd_qty+`</td>
		    <td style="width:10%;"><input type="number" name="out_qty" class="form-control out_qty" ></td>
		  `;
		  tbody.append(outTr);
	});	
}

//상품 출고
function goOutList(){
	var outPdTrList = document.querySelectorAll("tr.pd_row");
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
		if(result=="out_success"){
		 	alert("출고요청이 완료되었습니다.");
			location.reload();
			
		}else{
			alert("result : " + result);
		}
	
	
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	})
}

/*--------------------------------------------------------------
	모달  검색
----------------------------------------------------------- */
function MdSearch(event, type) {
    if (event.key === 'Enter') {
      MdSearchBtn(type);
    }
}


function MdSearchBtn(type) {
	console.log("type : " + type)
	if(type=="itmMd"){  //부자재모달 
		itm_modal_pg();
	}
	else if(type=="bomMd"){  //bom완제품 모달  
		bom_modal_pg();
	}
	else if(type=="cltMd"){  //거래처모달 
		pcl_modal_pg();
	}
	else if(type=="cltMd2"){  //발주처 모달
		cl2_modal_pg();
	}
	else if(type=="inbMd"){  //입고리스트모달 
		inbnd_modal_pg();
	}
	else if(type=="membMd"){  //사원리스트 모달  
		//bom_modal_pg();
	}
	else if(type=="ordMd"){  //주문건리스트 모달 
		ord_modal_pg();
	}
	else if(type=="ordMd2"){  //주문건리스트 모달
		ord_modal_pg();
	}
	else if(type=="pdMd"){  //완제품리스트모달 
		pd_modal_pg();
	}
	else if(type=="pdMd2"){  //완제품리스트모달 
		pd_modal_pg();
	}
	else if(type=="pchMd"){  //발주리스트 모달 
		pch_modal_pg();
	}
	/*else if(type=="whMd"){  //유형별 창고리스트 모달  
		bom_modal_pg();
	}*/
		
 }
 
 
 /*———————————————————————————————
 	MRP 리스트 모달 
 ————————————————————————————— */
 function mrpListOpen(){
 	const mrp_status = "발주 미완료";
 	fetch("./mrp_list_modal.do?status="+mrp_status, {
 		method: "GET",

 	}).then(function(data) {
 		return data.text();

 	}).then(function(result) {
 		document.getElementById("modalContainer").innerHTML = result;
 		
 		var modal= new bootstrap.Modal(document.getElementById("mrp_list"));
 		modal.show();
 		
 	}).catch(function(error) {
 		
 		console.log("통신오류발생" + error);
 	});
 }

