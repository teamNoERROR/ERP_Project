/*--------------------------------------------------------------
	거래처 리스트 모달 
----------------------------------------------------------- */
function cltListOpen(){
	fetch("./client_list.do", {
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
	var parent = page.getAttribute('data-parent');
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
function pdListOpen(){
	fetch("./product_list.do", {
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
		
		
	fetch("./product_list.do?"+pString+"&mode=modal2", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.querySelector('#product_list .modal-body').innerHTML = result;
		
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