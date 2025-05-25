// 예시 부자재 상세 데이터
const bomDetails = {
	1: {
		name: "부자재 1",
		code: "MAT-001",
		spec: "규격 A",
		qty: "10",
		unit: "EA",
		remark: "비고 없음"
	},
	2: {
		name: "부자재 2",
		code: "MAT-002",
		spec: "규격 B",
		qty: "5",
		unit: "EA",
		remark: "특이사항 있음"
	},
	3: {
		name: "부자재 3",
		code: "MAT-003",
		spec: "규격 C",
		qty: "20",
		unit: "EA",
		remark: "맛없음 "
	}
};




// 트리 클릭 이벤트
document.querySelectorAll('#bomTree li[data-id]').forEach(li => {
	li.addEventListener('click', function(e) {
		e.stopPropagation();
		// 선택 효과
		document.querySelectorAll('#bomTree li').forEach(el => el.classList.remove('selected'));
		this.classList.add('selected');
		// 상세보기 표시
		

		const detail = bomDetails[this.dataset.id];
		if (detail) {
          document.getElementById('bomDetail2').innerHTML = `
              <h5 class="mb-3">${detail.name}</h5>
              <table class="table table-bordered">
                  <tr><th>코드</th><td>${detail.code}</td></tr>
                  <tr><th>규격</th><td>${detail.spec}</td></tr>
                  <tr><th>수량</th><td>${detail.qty} ${detail.unit}</td></tr>
                  <tr><th>비고</th><td>${detail.remark}</td></tr>
              </table>
          `;
      	}
	});
});





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
	
	}).then(function(result) {
		if(result =="yes"){
			location.href="./bom_detail.do?pd_code="+pd_code;
			
		}else if(result =="no"){
			
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






//부자재리스트 모달에서 부자재 한번에 선택하기 
function select_items () {
  // 모든 체크된 체크박스를 찾음
  var selected_box = document.querySelectorAll('input[name="select"]:checked');
  console.log(selected_box)

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
    <td><input type="text" class="form-control" value="${item.code}" readonly></td>
    <td><input type="text" class="form-control" value="${item.name}" readonly></td>
    <td><input type="text" class="form-control" value="${item.class1}" readonly></td>
    <td><input type="text" class="form-control" value="${item.class2}" readonly></td>
    <td><input type="text" class="form-control" value="${item.spec}" readonly></td>
    <td><input type="text" class="form-control" value="${item.cost}" readonly></td>
    <td><input type="text" class="form-control" value="${item.pcomp}" readonly></td>
	<td><input type="text" class="form-control" min="1" placeholder="주문수량"></td>
	<td>
		<select class="form-select">
			<option>선택</option>
			<option>ea</option>
			<option>g</option>
			<option>ml</option>
		</select>
	</td>
    <td class="text-center">
      <button type="button" class="btn btn-danger btn-sm" onclick="removeRow(this)">삭제</button>
    </td>
  `;
  tbody.append(tr);
}


// 모달이 닫혔을 때 이벤트 감지
var close_md = document.querySelector('#close_md');
function show_list_md(){
	var listModal = new bootstrap.Modal(document.querySelector('#items_list'));
	listModal.show();
	
}






function bom_save(){
	
	
	
	
}
