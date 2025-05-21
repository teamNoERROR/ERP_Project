

function loadList(type) {
	const table = document.getElementById('table-container');
	const container = document.getElementById('list_container');

	// 기존 테이블 숨기기
	if (table) table.style.display = 'none';

	// 리스트 타입에 따라 다른 내용 표시 (더미 데이터 사용 예시)
	const dummyData = {
		1: ['완제품 A', '완제품 B', '완제품 C'],
		2: ['부자재 X', '부자재 Y'],
		3: ['소모품 1', '소모품 2', '소모품 3']
	};

	const titles = ['완제품', '부자재', '소모품'];
	const items = dummyData[type] || [];

	container.innerHTML = `
		<h4>${titles[type - 1]} 리스트</h4>
		<ul style="list-style: none; padding: 0;">
			${items.map(item => `<li style="padding: 5px 0;">${item}</li>`).join('')}
		</ul>
	`;
}



/*
let insertBtn = document.querySelector("#goodsInsert");
insertBtn.addEventListener("click", function(){
	location.href="./goods_insert.do";
});

let modifyBtn = document.querySelector("#goodsInsert");
insertBtn.addEventListener("click", function(){
	location.href="./goods_insert.do";
});
*/

//대분류 - 소분류 짝맞추기 
function lg_class() {

	var pd_class = document.querySelector("#goods_class1");
	console.log("pd_class.value:", pd_class.value);
	fetch("/goods_insert.do?goods_class1=" + pd_class.value, {

		method: "GET"

	}).then(function(data) {
		return data.text();;

	}).then(function(result) {
		/*
		var select_sc = document.querySelector("#pd_class2");
		var w=0;
		while(w<sc_list.length){
			var option = document.createElement("option");
			option.innerHTML = sc_list[w];
			option.value = sc_list[w];
			select_sc.append(option);
			w++;
		}
		*/

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}



//제품유형 구분으로 입력항목 가리기 
function goods_type() {
	var goods_type = document.querySelector("#goods_type");
	fetch("/goods_type.do?goods_type=" + goods_type.value, {
		method: "GET"

	}).then(function(data) {
		return data.text();;

	}).then(function(result) {
		var open_price = document.querySelector(".open_price");
		var price_box = document.createElement("div");
		var open_ccorp = document.querySelector(".open_ccorp");
		var corp_box = document.createElement("div");
		
		var prev_price = open_price.querySelector(".dynamic-price");
	    if (prev_price){
			prev_price.remove();
		}

	    var prev_corp = open_ccorp.querySelector(".dynamic-corp");
	    if (prev_corp){
			prev_corp.remove();
		} 
		
		//중복생성 방지 
		
		
		if (result == "완제품" || result == "반제품") {
			price_box.className = "col-md-2 dynamic-price";
			price_box.innerHTML = `
					<label  class="form-label">판매가 (원)</label>
					<input type="text" class="form-control">
				`
			open_price.appendChild(price_box);

		} else if (result == "부자재" || result == "소모품") {
			corp_box.className = "row mb-3 dynamic-corp";
			corp_box.innerHTML = `
				<div class="col-auto d-flex align-items-end pe-0">
					<button class="btn btn-primary" type="button">
					<i class="bi bi-search"></i>
					</button>
				</div>
				<div class="col-md-2">
					<label class="form-label">거래처코드</label> 
					<input type="text" class="form-control">
				</div>
				<div class="col-md-3">
					<label class="form-label">거래처명</label> 
					<input type="text" class="form-control">
				</div>
					<div class="col-md-3">
					<label class="form-label">담당자명</label> 
					<input type="text" class="form-control">
				</div>
				<div class="col-md-3">
					<label class="form-label">연락처</label> 
					<input type="text" class="form-control">
				</div>
				`
			open_ccorp.appendChild(corp_box);


		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});


}




