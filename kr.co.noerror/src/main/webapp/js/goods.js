function toggleButton(type) {
	/*const buttons = [
	    { id: 'product_list' },
	    { id: 'item_list' },
		{ id: 'consume_list' }
	];

	buttons.forEach(btn => {
		const elm = document.getElementById(btn.id);
		if (btn.id.includes(type)) {
		  elm.style.background = '#82CCDD';
		  elm.style.color = '#fff';
		  
		} else {
		  elm.style.background = 'transparent';
		  elm.style.color = '#000';
		}
	});*/

  	let url = "";
	if (type == 'product') {
	     url = "/goods.do?type="+type;
	} else if (type == 'item') {
	     url = "/goods.do?type="+type;
	} else if (type == 'consume') {
	 	     url = "/goods.do?type="+type;
	 	} 
 	location.href = url; // 전체 페이지 이동
}






//추가 버튼 누른 경우 
var addBtn = function(){
	location.href="./products_insert.do";
	
}


var product_type = document.querySelector("#product_type");
var products_class1 = document.querySelector("#products_class1");
var products_class2 = document.querySelector("#products_class2");
var product_name = document.querySelector("#product_name");
var product_spec = document.querySelector("#product_spec");
var product_unit = document.querySelector("#product_unit");
var pd_safe_stock = document.querySelector("#pd_safe_stock");

//var useY = document.querySelector("#useY");
//var useN = document.querySelector("#useN");
//var expireY = document.querySelector("#expireY");
//var expireN = document.querySelector("#expireN");

var product_cost = document.querySelector("#product_cost");
var product_price = document.querySelector("#product_price");
var memo = document.querySelector("#memo");
var productImage = document.querySelector("#productImage").files[0];
var url = document.querySelector("#url");

//제품 등록하기
function insert_pd(){
	
	
	if(product_type.value ==""){
		alert("제품유형을 선택하세요");
		product_type.focus();
	}
	else if(products_class1.value ==""){
		alert("대분류를 선택하세요");
		products_class1.focus();
	}
	//else if(products_class2.value ==""){ 
	//	alert("소분류를 선택하세요");
	//	products_class2.focus();
	//}
	else if(product_name.value ==""){ 
		alert("제품명을 입력하세요");
		product_name.focus();	
			
			
	}else if(product_spec.value ==""){ 
		alert("제품규격을 입력하세요");
		product_spec.focus();		
				
				
	}else if(product_unit.value ==""){ 
		alert("제품단위를 입력하세요");
		product_unit.focus();		
				
				
	}else if(pd_safe_stock.value ==""){ 
		alert("안전재고수를 입력하세요");
		pd_safe_stock.focus();			
					
					
	}
	else if(useY.checked == false && useN.checked == false){ 
		alert("사용유무를 선택하세요");
					
					
	}
	else if(expireY.checked == false && expireN.checked == false){ 
		alert("유통기한 사용유무를 선택하세요");
						
						
	}
	else if(product_cost.value ==""){ 
		alert("단가를 입력하세요");
		product_cost.focus();					
							
							
	}else if(product_price.value ==""){ 
		alert("판매가를 입력하세요");
		product_price.focus();					
							
							
	}else if(productImage){  //파일첨부 함경우 
		var imgSize = productImage.size; // 파일 크기
		var maxSize = 2 * 1024 * 1024; // 2MB제한
			
		if (!productImage.type.startsWith("image/")) {
    		alert("이미지 파일만 첨부 가능합니다 (jpg, png, gif 등)");
		}
		else if(imgSize > maxSize){
			alert("파일첨부 용량은 2MB이하만 가능합니다.");
			
		}else {
			if(confirm("제품등록을 완료허시겠습니까?")){
				this.insertProduct();
			}
		}
	}else {
		if(confirm("제품등록을 완료허시겠습니까?")){
			this.insertProduct();
		}
	}
}

//제품 등록
function insertProduct(){
	var use_flag = document.querySelector('input[name="use_flag"]:checked');
	var exp_flag = document.querySelector('input[name="exp_flag"]:checked');
	
	var formData = new FormData();
	formData.append("PRODUCT_TYPE", product_type.value);
	formData.append("PRODUCT_CLASS1", products_class1.value);
	formData.append("PRODUCT_CLASS2", products_class2.value);
	formData.append("PRODUCT_NAME", product_name.value);
	formData.append("PRODUCT_SPEC", product_spec.value);
	formData.append("PRODUCT_UNIT", product_unit.value);
	formData.append("PRODUCT_COST", product_cost.value);
	formData.append("PRODUCT_PRICE", product_price.value);
	formData.append("PD_SAFE_STOCK", pd_safe_stock.value);
	formData.append("USE_FLAG", use_flag.value);
	formData.append("EXP_FLAG", exp_flag.value);
	formData.append("MEMO", memo.value);
	
	if (productImage) { //새로 파일 첨부를 한 경우 
    	formData.append("productImage", productImage);
		formData.append("url", "아이피써놓기");
	}
	
	fetch("./products_insertok.do", {

		method: "POST",
		body : new URLSearchParams(formData)
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		alert("제품 등록이 완료되었습니다.");
		location.href="./goods.do";
		

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
	
	

}


//초기화 버튼 클릭 
function resetBtn(){
	
	//document.querySelector("#productImage").value=""
	//document.querySelector('#previewImage').src = "./img/no-image.svg";
	//product_type.value="";
	//products_class1.value="";
	//products_class2.value="";
	//product_name.value="";
	//product_spec.value="";
	//product_unit.value="";
	//pd_safe_stock.value="";
	//use_flag.value="";
	//exp_flag.value="";
	//product_cost.value="";
	//product_price.value="";
	//memo.value="";
	//productImage.value="";
	
	location.reload();
}

//상세보기
function open_detail(event){
	
	var pd_code = event.currentTarget.querySelector(".pd_code").innerText;
	var gd_type =event.currentTarget.querySelector(".sb").getAttribute("data-type");
	
	fetch("./goods_detail.do", {
		method: "POST",
		headers: {"content-type": "application/x-www-form-urlencoded"},
		body: "pd_code="+pd_code+"&type="+gd_type
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		console.log(result)
		var modal;
		document.getElementById("modalContainer").innerHTML = result;
		
		if(gd_type == "product"){
			modal = new bootstrap.Modal(document.getElementById("pd_detail"));
			
		}else if(gd_type == "item"){
			modal = new bootstrap.Modal(document.getElementById("itm_detail"));
			
		}	
		modal.show();
		
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}




//삭제버튼 누른경우
function deleteBtn(del_pd){
	var idx;
	var pd_code;
	var gd_type;
	var del_req = new Array();
	
	if(del_pd){ //모달에서 삭제시 (del_pd가 전달되었을떄)
		idx = del_pd.getAttribute("data-idx");
		pd_code = del_pd.getAttribute("data-pdcode");
		gd_type= del_pd.getAttribute("data-type");
		
		if(confirm("정말 삭제하시겠습니까? \n 삭제 후에는 복구되지 않습니다.")){
			del_req = [{idx: idx, code: pd_code, type : gd_type}]
			del_ajax(del_req);	//모달 안에서 1개만 삭제 
		}
			
	}else {  //리스트에서 체크박스로 삭제시 (del_pd 전달x)
		var checkboxes = document.querySelectorAll("input[name='selected_box']:checked");
		
		if (checkboxes.length == 0) {
			alert("삭제할 항목을 선택해주세요.");
			
		}else{
			if (confirm("정말 삭제하시겠습니까? \n 삭제 후에는 복구되지 않습니다.")) {
				
				checkboxes.forEach(chk => {
					idx = chk.getAttribute("data-idx");		
					pd_code = chk.getAttribute("data-pdcode");
					gd_type= chk.getAttribute("data-type");
					
					del_req.push({ idx: idx, code: pd_code, type : gd_type })
				});
				del_ajax(del_req); //체크박스로 1개~여러개 삭제 
			}
		}
	}
}

//삭제 ajax
function del_ajax(del_req){	
	fetch("./goods_delete.do/"+del_req[0].type+"_del", {
		method: "DELETE",
		headers: {"content-type": "application/json"},
		body: JSON.stringify(del_req)
			
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		console.log("result : "+result);
		if (result == "ok") {
		
			alert("삭제가 완료되었습니다.");
				
			// 모달 닫기
	        const modalElement = document.getElementById("modal");
	       	const modal = bootstrap.Modal.getInstance(modalElement);
	        if (modal) {
	            modal.hide();
				setTimeout(() => {
					document.activeElement.blur(); // 현재 포커스를 제거
				}, 300);
	        }
			//리스트 페이지 새로고침
	       	location.reload();
			
		}else if(result=="fail") {
			alert("시스템 문제로 일부 제품 삭제에 실패했습니다.");
		}else{
			console.log(result);
		}
	}).catch(function(error) {
		failCount++;
		console.log("통신오류발생" + error);
	});
}






//대분류 - 소분류 짝맞추기 
function lg_class() {

	var pd_class = document.querySelector("#products_class1");
	fetch("./goods_class.do?products_class1=" + pd_class.value, {
		method: "GET"
		
	}).then(function(data) {
		return data.json();

	}).then(function(s_class) {
		
		var select_sc = document.querySelector("#products_class2");
		select_sc.innerHTML = "";
		var w=0;
		var sclass = "";
		sclass = `<option value="">`+"선택"+`</option>`;
		while(w< s_class.length){
			sclass += `<option value="${s_class[w]}">`+s_class[w]+`</option>`;
			w++;
		}
			select_sc.innerHTML=sclass;
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}






