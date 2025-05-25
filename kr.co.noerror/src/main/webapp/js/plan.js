function close_modal() {
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
  const due_date = selectedRow.getAttribute('data-due_date');

  console.log(order_code);
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
  inputs_row2[3].value = due_date;

  // 모달 닫기
  const modalEl = document.getElementById('orders_modal');
  const modalInstance = bootstrap.Modal.getInstance(modalEl);
  if (modalInstance) {
    modalInstance.hide();
  }
}