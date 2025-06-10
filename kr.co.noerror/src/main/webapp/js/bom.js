/*--------------------------------------------------------------
bom조회하기로 이동 
--------------------------------------------------------------*/
function bomBtn(bom_open){
	var pd_code = bom_open.getAttribute("data-pdcode");
	
	fetch("./bom_check.do?pd_code="+pd_code, {
		method: "GET"
			
	}).then(function(data) {
		return data.text();
	
	}).then(function(result) {  
		if(result =="yes"){  //등록된 BOM 있음 
			
			var bom_open = new bootstrap.Modal(document.getElementById('bom_detail'));
			bom_open.show();
			bom_detail(pd_code);

				}else if(result =="no"){  //등록된 BOM없음 
			if(confirm("등록된 BOM 자료가 없습니다. \n지금 등록 하시겠습니까?")){
				location.href="./bom_insert.do?pd_code="+pd_code;
				
			}else {
				location.href="./goods.do";   //완제품 리스트로 이동 
			}
			
		}else {
			console.log(result);
		}
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}


/*--------------------------------------------------------------
bom 상세보기 모달 오픈
--------------------------------------------------------------*/
function bomDetailOpen(bom_open){
	var pd_code = bom_open.getAttribute("data-pdcode");
	
	fetch("./bom_detail.do?pd_code="+pd_code, {
		method: "GET"
			
	}).then(function(data) {
		return data.text();
	
	}).then(function(result) {  
		document.getElementById("modalContainer").innerHTML = result;
			var bom_open = new bootstrap.Modal(document.getElementById('bom_detail'));
			bom_open.show();
			//bom_detail(pd_code);

		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}


/*--------------------------------------------------------------
bom추가하기
--------------------------------------------------------------*/
function addBom(){
	location.href="./bom_insert.do";
}


/*--------------------------------------------------------------
bom 삭제
--------------------------------------------------------------*/
function bomDelete(del_pd){
	var idx = 0;
	var pd_code;
	var bom_code;
	var gd_type;
	var del_req = new Array();
	
	if(del_pd){ //모달에서 삭제시 (del_pd가 전달되었을떄)
		pd_code = del_pd.getAttribute("data-pdcode");
		bom_code = del_pd.getAttribute("data-etccode");
		gd_type= del_pd.getAttribute("data-type");
		
		if(confirm("정말 삭제하시겠습니까? \n 삭제 후에는 복구되지 않습니다.")){
			del_req = [{
				idx : idx,
				code: pd_code, 
				code2: bom_code, 
				type : gd_type
			}]
			del_ajax(del_req);	//모달 안에서 1개만 삭제 
		}
			
	}else {  //리스트에서 체크박스로 삭제시 (del_pd 전달x)
		var checkboxes = document.querySelectorAll("input[name='bom_select']:checked");
		
		if (checkboxes.length == 0) {
			alert("삭제할 항목을 선택해주세요.");
			
		}else{
			if (confirm("정말 삭제하시겠습니까? \n 삭제 후에는 복구되지 않습니다.")) {
				
				checkboxes.forEach(chk => {
					pd_code = chk.getAttribute("data-pdcode");
					bom_code = chk.getAttribute("data-etccode");
					gd_type= chk.getAttribute("data-type");
					
					del_req.push({ 
						idx : idx,
						code: pd_code, 
						code2: bom_code, 
						type : gd_type 
					})
				});
				del_ajax(del_req); //체크박스로 1개~여러개 삭제 
			}
		}
	}
}


/*--------------------------------------------------------------
bom등록 트리화면 
--------------------------------------------------------------*/
var top_pd_nm = document.querySelector("#product_name");
document.querySelector("#bom_top_pd").innerHTML=`<i class="bi bi-caret-right-fill"></i>`+top_pd_nm.value;



/*--------------------------------------------------------------
bom등록 저장
--------------------------------------------------------------*/
function bomSave(){
	var tbody = document.querySelector("#bom_items");
	var rows = tbody.querySelectorAll('.item_added'); // 테이블에서 데이터가 있는 행만 선택
	
	var pd_code = document.querySelector("#product_code");
	var pd_type = document.querySelector("#product_type");
	
  	var items = [];
	
	if (rows.length == 0) {
        alert("BOM 등록을 위한 부자재를 선택하세요.");
        return;
    }
  	rows.forEach(row => {
	    // 각 컬럼에서 값을 읽어오기
		var item_code = row.querySelector(".item_code");
		var item_qty =  row.querySelector('.item_qty');
		var item_unit = row.querySelector('.select_unit');
		console.log(item_code.value)
		
		if(item_qty.value == ""){
			alert("소요수량을 입력해야 합니다.");
			item_qty.focus();
			
		}else if(item_unit.value == ""){
			alert("단위를 입력해야 합니다.");
			item_unit.focus();
			
		}else{
		    if(item_qty.value > 0 && item_unit.value != "") {
				//다 입력했으면 배열에 넣기
		      	items.push({
					cProductCode : pd_code.value,
			        cItemCode: item_code.value,
					bomQty: Number(item_qty.value),
					unit: item_unit.value,
					pd_type:pd_type.value
		      	});
	    	}
		}
  	});
	fetch("./bom_insertok.do", {
		method: "PUT",
		headers: {'content-type': 'application/json'},
		body : JSON.stringify(items)
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		console.log("result : " + result)
		if(result=="ok"){
			alert("BOM 등록이 완료되었습니다.");
			location.href="./bom.do"
		}else if(result=="fail"){
			alert("BOM 등록에 실패했습니다.");
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}
/*--------------------------------------------------------------
bom 검색
--------------------------------------------------------------*/
function bomSearch(){
	var keyword = document.querySelector("#keyword");
	var sclass = document.querySelector("#products_class2");
	var form = document.querySelector("#frm_class");
	
	if(sclass.value=="" && keyword.value==""){  //분류, 키워드 모두 없는경우
		alert("검색어를 입력하세요.");
		keyword.focus();
		return false;
		
	}else if(sclass.value=="" && keyword.value!="") {  //키워드만 있는경우 
		sclass.value=null;
		form.action="bom.do";
		form.method="GET";
		form.submit();
		
	}else{  
		form.action="bom.do";
		form.method="GET";
		form.submit();
	}		
}

/*--------------------------------------------------------------
bom 페이징 
--------------------------------------------------------------*/
function go_bom_pg(ee){
	var keyword = ee.getAttribute('data-keyword');
	var page_no = ee.getAttribute('data-pageno');
	var sclass = ee.getAttribute('data-sclass');
	
	var params = {  
		    pageno: page_no,
		    post_ea: ee.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}

		if (sclass) {  //소분류 선택시 
		    params["products_class2"] = sclass;
		}

		var pString = new URLSearchParams(params).toString();
		location.href = "./bom.do?" + pString;
}

/*--------------------------------------------------------------
bom 게시물 개수 선택 
--------------------------------------------------------------*/
function bompostEa(){
	var form = document.querySelector("#bompg_frm");
	form.method = "GET";
	form.action = "./bom.do";
	form.pageno.value = 1; //
	form.submit();
}