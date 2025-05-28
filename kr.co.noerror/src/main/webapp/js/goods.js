//토글버튼 클릭시 페이지 이동
function toggleButton(type) {
  	let url = "";
	console.log("sss" + type)
	if (type == 'product') {
	     url = "/goods.do?type="+type;
	} else if (type == 'item') {
	     url = "/goods.do?type="+type;
	} else if (type == 'consume') {
	 	 url = "/goods.do?type="+type;
	} 
 	location.href = url; //페이지 이동
}


//완제품페이지 > 추가 버튼 누른 경우 
var addProduct = function(){
	location.href="./products_insert.do";
}


//부자재페이지 > 추가 버튼 누른 경우 
var addItem = function(){
	location.href="./items_insert.do";
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










