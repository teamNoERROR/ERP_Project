function close_bom_items() {
	// 모달 닫기
	const modalEl = document.getElementById('bom_items');
	const modalInstance = bootstrap.Modal.getInstance(modalEl);
	if (modalInstance) {
	  modalInstance.hide();
	}
}

function order_select() {
  // 선택된 라디오 버튼 찾기
  const selectedRadio = document.querySelector('input[name="order_check"]:checked');
  
  if (!selectedRadio) {
    alert("제품을 선택하세요.");
    return;
  }
  
  // 선택된 라디오 버튼의 부모 <tr> 찾기
  const selectedRow = selectedRadio.closest('tr');
  
  // data-* 속성 가져오기
  const order_code = selectedRow.getAttribute('data-order_code');
  const product_code = selectedRow.getAttribute('data-product_code');
  const product_name = selectedRow.getAttribute('data-product_name');
  const bom_code = selectedRow.getAttribute('data-bom_code');
  const company_name = selectedRow.getAttribute('data-company_name');
  const order_qty = selectedRow.getAttribute('data-order_qty');
  const product_spec = selectedRow.getAttribute('data-product_spec');
  const product_unit = selectedRow.getAttribute('data-product_unit');
  const due_date = selectedRow.getAttribute('data-due_date');
  const company_code = selectedRow.getAttribute('data-company_code');

  // 각 input 요소에 값 설정
  // 부모 페이지에서 order 정보 input들이 순서대로 위치하므로, 아래와 같이 선택
  const inputs_row1 = window.parent.document.querySelectorAll('.row.mb-3.row1 input.form-control[readonly]');
  inputs_row1[0].value = order_code;
  inputs_row1[1].value = product_code
  inputs_row1[2].value = product_name;
  inputs_row1[3].value = bom_code;
  
  const inputs_row2 = window.parent.document.querySelectorAll('.row.mb-3.row2 input.form-control[readonly]');
  inputs_row2[0].value = company_name;
  inputs_row2[1].value = order_qty;
  inputs_row2[2].value = product_spec;
  inputs_row2[3].value = product_unit;
  
  const inputs_row3 = window.parent.document.querySelectorAll('.row.mb-3.row3 input.form-control[readonly]');
  inputs_row3[0].value = due_date;
 
  window.parent.document.getElementById('company_code_hidden').value = company_code;
  
  // 모달 닫기
  const modalEl = document.getElementById('orders_modal');
  const modalInstance = bootstrap.Modal.getInstance(modalEl);
  if (modalInstance) {
    modalInstance.hide();
  }
}

//생산계획 저장
function plan_save() {
  // 생산계획 정보 읽기
  const bom_code = document.getElementById('bom_code').value.trim();
  const order_code = document.getElementById('order_code').value.trim();
  const product_code = document.getElementById('product_code').value.trim();
  const product_qty = document.getElementById('product_qty').value.trim();
  const priority = document.getElementById('priority').value;
  const start_date = document.getElementById('start_date').value;
  const due_date = document.getElementById('due_date').value;
  const emp_code = document.getElementById('emp_code').value.trim();
  const memo = document.getElementById('memo').value.trim();
  const plan_date = new Date().toISOString().slice(0,10); // 오늘 날짜, yyyy-MM-dd 형식
  const modify_date = plan_date;
  const company_code = document.getElementById('company_code_hidden').value.trim();

  const plan_status = "계획";
  const mrp_status = "완료";
  
  if(order_code == ""){
	alert("주문코드를 조회하여 주문정보를 선택하세요.");
  }
  else if(priority == ""){
	alert("우선순위를 선택하세요.");
  }
  else if(start_date == ""){
	alert("생산시작예정일을 입력하세요.");
  }
  else if(due_date == ""){
	alert("생산완료예정일을 입력하세요.");
  }
  else if(emp_code == ""){
	alert("사원코드를 조회하여 담당자정보를 입력하세요.");
  }
  else if(memo == ""){
  alert("메모를 입력하세요.");
  }
  else{
	const production_plans = {
	  bom_code: bom_code,
	  order_code: order_code,
	  product_code: product_code,
	  product_qty: parseInt(product_qty, 10),
	  priority: priority,
	  start_date: start_date,
	  due_date: due_date,
	  company_code: company_code,
	  emp_code: emp_code,
	  memo: memo,
	  plan_date: plan_date,
	  modify_date: modify_date,
	  plan_status: plan_status,
	  mrp_status: mrp_status
	};
	
	fetch('/plan_save.do', {
	  method: 'POST',
	  headers: { 'Content-Type': 'application/json' },
	  body: JSON.stringify(production_plans)
	})
	.then(res => {
	  if (!res.ok) throw new Error("서버 에러");
	  return res.json();
	})
	.then(data => {
	  if (data.success) {
	    alert("저장 성공");
	    window.location.href = "/production.do";
	  } else {
	    alert("저장 실패(알수없는 오류");
	  }
	})
	.catch(err => {
	  alert("저장 실패 (요청 실패): " + err.message);
	});
  
  }
}
