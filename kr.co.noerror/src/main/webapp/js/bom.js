








function removeRow(btn) {
	const row = btn.closest('tr');
	row.parentNode.removeChild(row);
}



//bom조회하기로 이동 
function bomBtn(bom_open){
	var pd_code = bom_open.getAttribute("data-pdcode");
	
	fetch("./bom_check.do?pd_code="+pd_code, {
		method: "GET"
			
	}).then(function(data) {
		return data.text();
	
	}).then(function(result) {  //등록된 BOM 있음 
		if(result =="yes"){  
			
			var bom_open = new bootstrap.Modal(document.getElementById('bom_detail'));
			bom_open.show();
			bom_detail(pd_code);
			//location.href="./bom_detail.do?pd_code="+pd_code;
			//bom_detail(pd_code);
			
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


//bom_detail 모달
function bom_detail(pd_code){
	console.log(pd_code)
	fetch("./bom_detail.do?pd_code="+pd_code, {
		method: "GET"
			
	}).then(function(data) {
		return data.text();
	
	}).then(function(result) {  //등록된 BOM 있음 
		if(result =="ok"){ 
			console.log("result" + result); 
			
					
		}
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}



//자재 추가 버튼 클릭 => 부자재리스트 모달 오픈 
function open_item_list(){
	fetch("./bom_item_list.do", {
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


var top_pd_nm = document.querySelector("#product_name");
document.querySelector("#bom_top_pd").innerHTML=`<i class="bi bi-caret-right-fill"></i>`+top_pd_nm.value;

//부자재리스트 모달에서 부자재 한번에 선택하기 
function select_items () {
  // 모든 체크된 체크박스를 찾음
  var selected_box = document.querySelectorAll('input[name="select"]:checked');

  if (selected_box.length == 0) {
	alert("제품을 1개 이상 선택해 주세요.");
	
  }else{
	  // 부모 테이블 tbody
	  var tbody = document.querySelector('#bom_items');
	
	  //부모 테이블의 기존 행 전체 삭제
	  document.querySelectorAll('tr.item_add_row').forEach(tr => tr.remove());
		

	  selected_box.forEach(checkbox => {
		
	    var row = checkbox.closest('tr');
	
	    var item = {
	      code: row.dataset.code,
	      name: row.dataset.name,
	      class1: row.dataset.class1,
	      class2: row.dataset.class2,
	      spec: row.dataset.spec,
		  cost: row.dataset.cost,
	      pcomp: row.dataset.company
	    };
		
	    // 부모 화면에 반영
		appendItemsRow(tbody, item);
		
		
		
		document.querySelector("#bom_tr").innerHTML+=`
			<li> ${item.name} </li>
		`;
		
		
	
	  });
	
	 // 모달 닫기
  	var modalElement = document.getElementById("items_list");
 	var modal = bootstrap.Modal.getInstance(modalElement);
	if (modal) {
	    modal.hide();
		setTimeout(() => {
			document.querySelector("body").focus(); // body에 포커스 주기
		}, 300);
	}
	
	
	
	
	
	
	
	
	
	
  }

};


//모달에서 선택한 리스트 등록화면의 리스트에 붙여넣기 
function appendItemsRow(tbody, item) {
  const tr = document.createElement('tr');
  tr.className = "item_added"
  tr.innerHTML = `
    <td><input type="text" class="form-control item_code" value="${item.code}" readonly></td>
    <td><input type="text" class="form-control" value="${item.name}" readonly></td>
    <td><input type="text" class="form-control" value="${item.class1}" readonly></td>
    <td><input type="text" class="form-control" value="${item.class2}" readonly></td>
    <td><input type="text" class="form-control" value="${item.spec}" readonly></td>
    <td><input type="text" class="form-control text-end" value="${item.cost}" readonly></td>
    <td><input type="text" class="form-control" value="${item.pcomp}" readonly></td>
	<td><input type="number" class="form-control item_qty"  value="" min="1"></td>
	<td>
		<select class="form-select select_unit">
			<option value="" >선택</option>
			<option value="ea" data-itemunit="ea">ea</option>
			<option value="g" data-itemunit="g">g</option>
			<option value="ml" data-itemunit="ml">ml</option>
		</select>
	</td>
    <td class="text-center">
      <button type="button" class="btn btn-danger btn-sm" onclick="removeRow(this)">삭제</button>
    </td>
  `;
  tbody.append(tr);
}





//bom등록 저장
function bom_save(){
	var tbody = document.querySelector("#bom_items");
	var rows = tbody.querySelectorAll('tr.item_added'); // 테이블에서 데이터가 있는 행만 선택
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
	console.log(JSON.stringify(items))
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
		}else if(result=="fail"){
			alert("BOM 등록에 실패했습니다.");
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}

