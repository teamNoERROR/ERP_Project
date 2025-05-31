//토글버튼 클릭시 페이지 이동
function toggleButton(type) {
	if(type == 'product' || type == 'item'){
	 	location.href = "/goods.do?type="+type; //페이지 이동
	}else {
		alert("준비중입니다.");
	}
}

//대분류리스트 세팅 
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
			lg_class += `<option value="${l_class[w]}">`+l_class[w]+`</option>`;
			w++;
		}
		select_lc.innerHTML=lg_class;

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}

//대분류 - 소분류 짝맞추기 
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

//분류로 검색 
function class_sch(){
	var form = document.querySelector("#frm_class");
	/*
	fetch("./goods.do?type=" + goods_type.value 
			+ "&sclass=" + sclass, {
			method: "GET"
			
		}).then(function(data) {
			return data.text();

		}).then(function(result) {
		
		}).catch(function(error) {
			console.log("통신오류발생" + error);
		});
		*/
		form.type.value="product";
		
		form.action="goods.do";
		form.method="GET";
		form.submit();
}


//제품 검색 
function pdSearch(){
	var form = document.querySelector("#frm_word");
	var pclass2 = document.querySelector("#products_class2");
	var search_opt = document.querySelector("#search_opt");
	var keyword = document.querySelector("#keyword");
	
	/*
	if(keyword.value==""){
		alert("검색어를 입력하세요.");
		keyword.focus();
	}else {
		fetch("./goods_sch.do?type="+ goods_type.value + "&search_opt=" + search_opt.value + "&keyword="+keyword.value, {
			method: "GET",
			
		}).then(function(data) {
			return data.text();

		}).then(function(result) {
						
			
		}).catch(function(error) {
			console.log("통신오류발생" + error);
		});
	}
	*/
	if(keyword.value==""){
		alert("검색어를 입력하세요.");
		keyword.focus();
		return false;
	}else {
		form.action="./goods.do";	
		form.method="GET";
		form.submit();
	}		
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




//초기화 버튼 클릭 
function resetBtn(){
	location.reload();
}

