var product_select = function () {
  console.log("TEST");
  // 모든 체크박스 찾기
  const checkboxes = document.querySelectorAll('input[name="product_check"]');
  
  let selected_row = null;
  
  checkboxes.forEach(checkbox => {
    if (checkbox.checked) {
      // checkbox의 부모 <tr> 찾기
      selected_row = checkbox.closest('tr');
      console.log("테스트");
    }
  });

  if (selected_row) {
    // data-* 속성 읽기
    const code = selected_row.dataset.code;
    const name = selected_row.dataset.name;
    const class1 = selected_row.dataset.class1;
    const class2 = selected_row.dataset.class2;
    const spec = selected_row.dataset.spec;
    const cost = selected_row.dataset.cost;
    const price = selected_row.dataset.price;
    
    // 부모 화면에 반영
    document.getElementById('product_code').value = code;
    document.getElementById('product_name').value = name;
    document.getElementById('product_class1').value = class1;
    document.getElementById('product_class2').value = class2;
    document.getElementById('product_spec').value = spec;
    document.getElementById('product_cost').value = cost;
    document.getElementById('product_price').value = price;

    // 모달 닫기
    const modalEl = document.getElementById('products_list');
    const modalInstance = bootstrap.Modal.getInstance(modalEl);
    modalInstance.hide();
  } else {
    alert('제품을 선택해 주세요.');
  }
};
