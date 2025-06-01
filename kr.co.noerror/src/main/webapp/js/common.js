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
  임시
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
function openItemList(){
	fetch("./item_list.do", {
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