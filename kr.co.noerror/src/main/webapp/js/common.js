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
function previewFile(type) {
	var fileInput;
	var preview = document.getElementById('previewImage');
	//var fileNameDisplay = document.getElementById('fileName');
	
	if(type == 'client'){
	  fileInput = document.getElementById('clientImage');
	  
	}else if(type == 'product'){
		fileInput = document.getElementById('productImage');
		
	}else if(type == 'item'){
		fileInput = document.getElementById('itemImage');
		
	}else if(type == "warehouse"){
		fileInput = document.getElementById('whImage');
	}
		
	var file = fileInput.files[0];
	if (file) {
	    var reader = new FileReader();
	    reader.onload = function(e) {
	      preview.src = e.target.result;
	    };
	    reader.readAsDataURL(file);
	
	    // 파일명 표시
	    //fileNameDisplay.textContent = file.name;
	} 
}

/*--------------------------------------------------------------
초기화 버튼 클릭
--------------------------------------------------------------*/
function resetBtn(){
	location.reload();
}

/*--------------------------------------------------------------
 등록화면에서 우상단 x표시 클릭 
--------------------------------------------------------------*/
function goBack(){
	history.go(-1);
}

/*--------------------------------------------------------------
  창고리스트 모달 
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

