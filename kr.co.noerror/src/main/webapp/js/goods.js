/*--------------------------------------------------------------
토글버튼 클릭시 페이지 이동
--------------------------------------------------------------*/
function toggleButton(type) {
	if(type == 'product' || type == 'item'){
	 	location.href = "/goods.do?type="+type; //페이지 이동
	}else {
		alert("준비중입니다.");
	}
}


/*--------------------------------------------------------------
대분류리스트 세팅 
--------------------------------------------------------------*/

function goodsType(goods_type){
	//goods_type = document.querySelector("#goods_type");
	fetch("/goods_type.do?type=" + goods_type, {
		method: "GET"

	}).then(function(data) {
		return data.json();

	}).then(function(l_class) {
		var select_lc = document.querySelector("#products_class1");
		
		select_lc.innerHTML = "";
		var w=0;
		var lg_class = "";
		lg_class = `<option value="">`+"선택"+`</option>`;
		while(w< l_class.length){
			lg_class += `<option value="`+l_class[w]+`">`+l_class[w]+`</option>`;
			w++;
		}
		select_lc.innerHTML=lg_class;

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}


/*--------------------------------------------------------------
대분류 - 소분류 짝맞추기 
--------------------------------------------------------------*/
function lcSc(lc_value) {
	var goods_type = document.querySelector("#goods_type");
	fetch("./goods_class.do?type=" + goods_type.value + 
			"&products_class1=" + lc_value, {
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
			sclass += `<option value="`+s_class[w]+`">`+s_class[w]+`</option>`;
			w++;
		}
		select_sc.innerHTML=sclass;
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}


/*--------------------------------------------------------------
분류로 검색 
--------------------------------------------------------------*/
function class_sch(type){
	var form = document.querySelector("#frm_class");
	if(type=="product" || type == "item"){
		form.action="goods.do";
		
	}else if(type=="bom") {
		form.action="bom.do";
	}
	form.method="GET";
	form.submit();
}


/*--------------------------------------------------------------
제품 검색 
--------------------------------------------------------------*/
function pdSearch(){
	var type = document.querySelector("#type");
	var keyword = document.querySelector("#keyword");
	var sclass = document.querySelector("#products_class2");
	var form = document.querySelector("#frm_class");
	
	if(sclass.value=="" && keyword.value==""){  //분류, 키워드 모두 없는경우
		alert("검색어를 입력하세요.");
		keyword.focus();
		return false;
		
	}else if(sclass.value=="" && keyword.value!="") {  //키워드만 있는경우 
		sclass.value=null;
		form.action="goods.do";
		form.method="GET";
		form.submit();
		
	}else{  
		form.action="goods.do";
		form.method="GET";
		form.submit();
	}		
}


/*--------------------------------------------------------------
삭제버튼 누른경우
--------------------------------------------------------------*/
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
			del_req = [{
				idx: idx, 
				code: pd_code, 
				type : gd_type}]
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
					
					del_req.push({ 
						idx: idx, 
						code: pd_code, 
						type : gd_type })
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
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
제품상세보기 모달
--------------------------------------------------------------*/
function open_gd_detail(event){
	var pd_code = event.currentTarget.querySelector(".pd_code").innerText;
	var gd_type =event.currentTarget.querySelector(".sb").getAttribute("data-type");
	
	fetch("./goods_detail.do", {
		method: "POST",
		headers: {"content-type": "application/x-www-form-urlencoded"},
		body: "pd_code="+pd_code+"&type="+gd_type
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		var modal;
		document.getElementById("modalContainer").innerHTML = result;
		
		if(gd_type == "product" || gd_type == "half"){
			modal = new bootstrap.Modal(document.getElementById("pd_detail"));
			
		}else if(gd_type == "item"){
			modal = new bootstrap.Modal(document.getElementById("itm_detail"));
			
		}	
		modal.show();
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
제품 수정하기 
--------------------------------------------------------------*/
//화면이동
function gdModify(btn){
	var type= btn.getAttribute("data-type");
	var code= btn.getAttribute("data-code");
	
	location.href="./goods_modify.do?type="+type+"&code="+code;
		
}

//제품 정보 수정 
function modify_goods(btn){
	var idx= btn.getAttribute("data-idx");
	var code= btn.getAttribute("data-code");
	var type= btn.getAttribute("data-type");
	var gd_file;
	
	if(type == 'product' || type == 'half'){
		gd_file = document.querySelector("#productImage").files[0];
		
		if(pd_type.value ==""){
			alert("제품유형을 선택하세요");
			pd_type.focus();
		}
		else if(products_class1.value ==""){
			alert("대분류를 선택하세요");
			products_class1.focus();
		}
		else if(products_class2.value ==""){ 
			alert("소분류를 선택하세요");
			products_class2.focus();
		}
		else if(product_name.value ==""){ 
			alert("제품명을 입력하세요");
			product_name.focus();	
				
		}else if(product_spec.value ==""){ 
			alert("제품규격을 입력하세요");
			product_spec.focus();		
					
		}else if(product_unit.value ==""){ 
			alert("제품단위를 입력하세요");
			product_unit.focus();		
					
		}
		else if(gd_file){   //파일첨부 함경우 
			var imgSize = gd_file.size; // 파일 크기
			var maxSize = 2 * 1024 * 1024; // 2MB제한
				
			if (!gd_file.type.startsWith("image/")) {
	    		alert("이미지 파일만 첨부 가능합니다 (jpg, png, gif 등)");
			}
			else if(imgSize > maxSize){
				alert("파일첨부 용량은 2MB이하만 가능합니다.");
				
			}else {
				gdModifyOk(idx,code,type);
			}
		}else {
			gdModifyOk(idx,code,type);
		}
		
	}else if(type == 'item'){
		gd_file = document.querySelector("#itemImage").files[0];
		
		if(itm_type.value ==""){
			alert("제품유형을 선택하세요");
			itm_type.focus();
		}
		else if(item_class1.value ==""){
			alert("대분류를 선택하세요");
			item_class1.focus();
		}
		else if(item_class2.value ==""){ 
			alert("소분류를 선택하세요");
			item_class2.focus();
		}
		else if(item_name.value ==""){ 
			alert("제품명을 입력하세요");
			item_name.focus();	
				
		}else if(item_spec.value ==""){ 
			alert("제품규격을 입력하세요");
			item_spec.focus();		
					
		}else if(item_unit.value ==""){ 
			alert("제품단위를 입력하세요");
			item_unit.focus();		
					
		}
		else if(useY.checked == false && useN.checked == false){ 
			alert("사용유무를 선택하세요");
		}
		else if(expireY.checked == false && expireN.checked == false){ 
			alert("유통기한 사용유무를 선택하세요");
		}
		else if(item_cost.value ==""){ 
			alert("단가를 입력하세요");
			item_cost.focus();							
					
		}else if(!reg_num.test(item_cost.value)){
			alert("단가는 숫자만 입력이 가능합니다.");
			product_cost.focus();	
			
		}else if(purchase_corp.value==""){
			alert("거래처를 선택하세요");
			purchase_corp.focus();		
		}
		else if(gd_file){   //파일첨부 함경우 
			var imgSize = gd_file.size; // 파일 크기
			var maxSize = 2 * 1024 * 1024; // 2MB제한
				
			if (!gd_file.type.startsWith("image/")) {
	    		alert("이미지 파일만 첨부 가능합니다 (jpg, png, gif 등)");
			}
			else if(imgSize > maxSize){
				alert("파일첨부 용량은 2MB이하만 가능합니다.");
				
			}else {
				gdModifyOk(idx,code,type);
			}
		}else {
			gdModifyOk(idx,code,type);
		}
	}
}

//게시글 수정(ajax)
function gdModifyOk(idx,code,type){
	console.log("idx2 : " + idx)
	console.log("code2 : " + code)
	console.log("type2 : " + type)
	
	
	var use_flag = document.querySelector('input[name="use_flag"]:checked');
	var exp_flag = document.querySelector('input[name="exp_flag"]:checked');
	var pdfile;
	var formData = new FormData();
	
	if(type == 'product' || type == 'half'){
		pdfile = document.querySelector("#productImage").files[0];
		
		formData.append("PIDX", idx);
		formData.append("PRODUCT_CODE", code);
		formData.append("PRODUCT_TYPE", type);
		formData.append("PRODUCT_NAME", product_name.value);
		formData.append("PRODUCT_CLASS1", products_class1.value);
		formData.append("PRODUCT_CLASS2", products_class2.value);
		formData.append("PRODUCT_SPEC", product_spec.value);
		formData.append("PRODUCT_UNIT", product_unit.value);
		formData.append("PRODUCT_COST", product_cost.value);
		formData.append("PRODUCT_PRICE", product_price.value);
		formData.append("PD_SAFE_STOCK", pd_safe_stock.value);
		formData.append("PD_USE_FLAG", use_flag.value);
		formData.append("PD_EXP_FLAG", exp_flag.value);
		formData.append("PD_MEMO", pd_memo.value);
		
	}else if(type == 'item'){
		pdfile = document.querySelector("#itemImage").files[0];
		
		formData.append("IIDX", idx);
		formData.append("ITEM_CODE", code);
		formData.append("ITEM_TYPE", type);
		formData.append("ITEM_NAME", item_name.value);
		formData.append("ITEM_CLASS1", item_class1.value);
		formData.append("ITEM_CLASS2", item_class2.value);
		formData.append("ITEM_SPEC", item_spec.value);
		formData.append("ITEM_UNIT", item_unit.value);
		formData.append("ITEM_COST", item_cost.value);
		formData.append("ITM_SAFE_STOCK", itm_safe_stock.value);
		formData.append("ITM_USE_FLAG", use_flag.value);
		formData.append("ITM_EXP_FLAG", exp_flag.value);
		formData.append("ITM_MEMO", itm_memo.value);
		formData.append("COMPANY_CODE", purchase_corp.value);
	}

	if (pdfile) { //새로 파일 첨부를 한 경우 
    	formData.append("goodsImage", pdfile);
	}
		
	fetch("./goods_modifyok.do", {
		method: "POST",
		body : formData
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		if(result=="ok"){
			alert("제품 정보가 수정되었습니다.");
			
			if(type == "product" || type == "half"){
				location.href="./goods.do?type=product";
			}else if(type == "item"){
				location.href="./goods.do?type=item";
			}
			
		}else if(result=="fail"){
			alert("시스템문제로 제품 정보 수정에 실패했습니다.\n관리자에게 문의해주세요.");
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}


