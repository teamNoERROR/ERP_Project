/*--------------------------------------------------------------
  Toggle 버튼 클릭시
--------------------------------------------------------------*/

function toggleButton2(type) {
	const buttons = [
		{ id: 'order-list', color: '#82CCDD' },
		{ id: 'product-list', color: '#82CCDD' },
	];

	// 버튼 스타일 초기화 및 선택한 버튼 강조
	buttons.forEach((btn, index) => {
		const el = document.getElementById(btn.id);
		if (index + 1 === type) {
			el.style.background = btn.color;
			el.style.color = '#fff';
		} else {
			el.style.background = 'transparent';
			el.style.color = '#000';
		}
	});

	// 리스트 로딩
	loadList(type);
}


/*--------------------------------------------------------------
  이미지 첨부시 미리보기
--------------------------------------------------------------*/
function previewFile() {
  const fileInput = document.getElementById('productImage');
  const preview = document.getElementById('previewImage');
  const fileNameDisplay = document.getElementById('fileName');

  const file = fileInput.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function(e) {
      preview.src = e.target.result;
    };
    reader.readAsDataURL(file);

    // 파일명 표시
    fileNameDisplay.textContent = file.name;
  } 
}




/*--------------------------------------------------------------
  리스트 모달
----------------------------------------------------------- */

//거래처리스트 모달 오픈 
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

//발주처리스트 모달 오픈 
function pcltListOpen(){
	fetch("./client_list2.do", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		
		//var modal= new bootstrap.Modal(document.getElementById("client_list"));
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
		    type: page.getAttribute('data-type'),
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
		//document.querySelector('#client_list .modal-body').innerHTML = result;
		document.querySelector('#p_client_list .modal-body').innerHTML = result;
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
}


//부자재리스트 모달 오픈 
function openItemList(parentType){
	fetch("./item_list.do?parent=" + parentType, {
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

//입고건 리스트 모달 오픈
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




//페이징에서 리스트 모달 오픈
function pchListOpen2(el) {
	console.log(el);
    const pageNo = el.getAttribute("data-page-no");
    const searchWord = el.getAttribute("data-search-word");
    const statuses = el.getAttribute("data-statuses");
    const pageSize = el.getAttribute("data-page-size");
	
    // URL 파라미터 구성
    const params = new URLSearchParams();
    params.append("page_no", pageNo ?? "1");
	params.append("search_word", searchWord ?? ""); // null이면 빈 문자열로 처리
	params.append("statuses", statuses ?? ""); // null이면 빈 문자열로 처리
	params.append("page_size", pageSize);
	
    fetch("./pch_list.do?" + params.toString(), {
        method: "GET"
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

function pchListOpen3(selectEl) {
    const pageSize = selectEl.value;

    const params = new URLSearchParams();
    params.append("page_size", pageSize);

	fetch("./pch_list.do?" + params.toString(), {
	        method: "GET"
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



/*
//발주내역 상세보기  
function pchDetailBtn(pch_code){
	console.log("pch_code:", pch_code); 
	fetch("./purchase_detail.do?code="+pch_code, {
		method: "GET"
			
	}).then(function(data) {
		return data.text();
	
	}).then(function(result) {  
			
		// 모달 내부 내용 채우기
		document.querySelector("#pch_modal .modal-content").innerHTML = result;
		var pch_detail_modal = new bootstrap.Modal(document.getElementById('pch_modal'));
		pch_detail_modal.show();
				
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}
*/