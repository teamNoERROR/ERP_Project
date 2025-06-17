function settingToday(){
	document.querySelector("#plan_start_date").value = new Date().toISOString().split('T')[0];
	document.querySelector("#dueDate").value = new Date().toISOString().split('T')[0];
	document.querySelector("#stock_start_date").value = new Date().toISOString().split('T')[0];
	document.querySelector("#out_date").value = new Date().toISOString().split('T')[0];
}

document.addEventListener("DOMContentLoaded", () => {
	  settingToday();
});


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



