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
//타입별창고리스트 모달 열기
let targetWhNameId = null;
let targetWhCodeId = null;

function whSelect(wh_type, name_id, code_id){
	targetWhNameId = name_id;
	targetWhCodeId = code_id;
	
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
	var radios = document.querySelectorAll('input[name="iosfSelect"]');
	var selected_radio = null;
	
	for (var i = 0; i < radios.length; i++) {
	  if (radios[i].checked) {
	    selected_radio = radios[i];
	  }
	}
	
	if (!selected_radio) {
	alert("창고를 선택해 주세요.");

	 }else{
		const whCode = selected_radio.value;
		const whName = selected_radio.getAttribute('data-wh_name');
		const whZipcode = selected_radio.getAttribute('data-wh_zipcode');
		const whAddr1 = selected_radio.getAttribute('data-wh_addr1');
		const whAddr2 = selected_radio.getAttribute('data-wh_addr2');
		const whType = selected_radio.getAttribute('data-wh_type');  // ← 여기서 타입 읽기

		const whNameInput = document.getElementById(targetWhNameId);
		const whCodeInput = document.getElementById(targetWhCodeId);
		
		if (whNameInput && whCodeInput) {
			whNameInput.value = whName;
			whCodeInput.value = whCode;
		} 
		
		// 예시: 타입별 처리 (원하면 타입별로 다르게 로직 분기 가능)
	   if (whType === '부자재창고') {
		   document.getElementById('wh_code').value = whCode;
	   		alert("창고를 선택하셨습니다.");
	  
	   } else if (whType === '완제품창고') {
		  document.getElementById('wh_code').value = whCode;
		   	alert("창고를 선택하셨습니다.");
	   } else {
	     console.log('기타 창고 타입');
	   }
		
	   
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

/*--------------------------------------------------------------*/
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
function pcl_modal_pg (page){
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

/*--------------------------------------------------------------*/
//발주처리스트 모달 오픈 
function pCltListOpen(){
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

/*--------------------------------------------------------------*/
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

/*--------------------------------------------------------------*/
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


/*--------------------------------------------------------------*/
//발주건 리스트 모달 오픈
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


//발주내역 상세보기  
function pchDetailBtn(pch_code){
	var ph_code = pch_code.getAttribute("data-etccode");
	console.log("pch_code:", ph_code); 
	fetch("./pchreq_detail.do?code="+ph_code, {
		method: "GET"
			
	}).then(function(data) {
		return data.text();
	
	}).then(function(result) {  
			
		// 모달 내부 내용 채우기
		document.querySelector("#modalContainer2").innerHTML = result;
		var pch_detail_modal = new bootstrap.Modal(document.getElementById('pch_modal'));
		pch_detail_modal.show();
				
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}
