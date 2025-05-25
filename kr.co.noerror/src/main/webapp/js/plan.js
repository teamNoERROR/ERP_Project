function close_modal() {
	// 모달 닫기
	const modalEl = document.getElementById('bom_items');
	const modalInstance = bootstrap.Modal.getInstance(modalEl);
	if (modalInstance) {
	  modalInstance.hide();
	}
}

function product_select2() {
  // 선택된 라디오 버튼 찾기
  const selectedRadio = document.querySelector('input[name="product_check"]:checked');
  
  if (!selectedRadio) {
    alert("제품을 선택하세요.");
    return;
  }
  
  // data-* 속성 가져오기
  const product_name = selectedRow.getAttribute('data-product_name');
  const product_class1 = selectedRow.getAttribute('data-product_class1');
  const product_class2 = selectedRow.getAttribute('data-product_class2');
  const product_spec = selectedRow.getAttribute('data-product_spec');
  const product_unit = selectedRow.getAttribute('data-product_unit');
  const product_cost = selectedRow.getAttribute('data-product_cost');

  // 각 input 요소에 값 설정
  // 부모 페이지에서 거래처 정보 input들이 순서대로 위치하므로, 아래와 같이 선택
  const inputs = window.parent.document.querySelectorAll('.row.mb-3 input.form-control[readonly]');
  if (inputs.length < 6) {
    alert("거래처 정보를 표시할 input 요소를 찾을 수 없습니다.");
    return;
  }
  
  inputs[0].value = company_code;
  inputs[1].value = company_name;
  inputs[2].value = biz_num;
  inputs[3].value = manager_code;
  inputs[4].value = manager_name;
  inputs[5].value = phone_num;

  // 모달 닫기 (Bootstrap 5 기준)
  const modalEl = document.getElementById('client_list');
  const modalInstance = bootstrap.Modal.getInstance(modalEl);
  if (modalInstance) {
    modalInstance.hide();
  }
}