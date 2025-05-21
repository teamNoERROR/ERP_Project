var product_select = function () {
  // 모든 체크된 체크박스를 찾음
  const checkboxes = document.querySelectorAll('input[name="product_check"]:checked');

  if (checkboxes.length === 0) {
    alert('제품을 선택해 주세요.');
    return;
  }

  // 부모 테이블 tbody
  const tbody = document.getElementById('products');

  //부모 화면 전체 삭제
  document.querySelectorAll('tr.total-delete').forEach(tr => tr.remove());
	
  checkboxes.forEach(checkbox => {
	
    const row = checkbox.closest('tr');

    const product = {
      code: row.dataset.code,
      name: row.dataset.name,
      class1: row.dataset.class1,
      class2: row.dataset.class2,
      spec: row.dataset.spec,
	  unit: row.dataset.unit,
      cost: row.dataset.cost,
      price: row.dataset.price
    };
	 	
    // 부모 화면에 반영
	appendProductRow(tbody, product);
	
	/*
	if (count == 1) { //부모 화면 해당 라인에 작성
		document.getElementById('product_code').value = product.code;
	    document.getElementById('product_name').value = product.name;
	    document.getElementById('product_class1').value = product.class1;
	    document.getElementById('product_class2').value = product.class2;
	    document.getElementById('product_spec').value = product.spec;
	    document.getElementById('product_unit').value = product.unit;
	    document.getElementById('product_cost').value = product.cost;
	    document.getElementById('product_price').value = product.price;
	}
	else {  //부모 화면에 행 추가
		appendProductRow(tbody, product);
	}
	count++;
	*/
  });

  // 모달 닫기
  const modalEl = document.getElementById('products_list');
  const modalInstance = bootstrap.Modal.getInstance(modalEl);
  modalInstance.hide();
};

function appendProductRow(tbody, product) {
  const tr = document.createElement('tr');
  tr.className = "total-delete"
  tr.innerHTML = `
    <td><input type="text" class="form-control" value="${product.code}" readonly></td>
    <td><input type="text" class="form-control" value="${product.name}" readonly></td>
    <td><input type="text" class="form-control" value="${product.class1}" readonly></td>
    <td><input type="text" class="form-control" value="${product.class2}" readonly></td>
    <td><input type="text" class="form-control" value="${product.spec}" readonly></td>
	<td><input type="number" class="form-control" min="1" placeholder="주문수량"></td>
	<td><input type="text" class="form-control" value="${product.unit}" readonly></td>
    <td><input type="text" class="form-control" value="${product.cost}" readonly></td>
    <td><input type="text" class="form-control" value="${product.price}" readonly></td>
    <td class="text-center">
      <button type="button" class="btn btn-danger btn-sm" onclick="removeProductRow(this)">삭제</button>
    </td>
  `;
  tbody.appendChild(tr);
}


function submitOrder() {
  const rows = document.querySelectorAll('#products tr');
  const productList = [];

  rows.forEach(row => {
    const inputs = row.querySelectorAll('input');

    const product = {
      code: inputs[0].value,
      name: inputs[1].value,
      class1: inputs[2].value,
      class2: inputs[3].value,
      spec: inputs[4].value,
      quantity: inputs[5].value,
      unit: inputs[6].value,
      cost: inputs[7].value,
      price: inputs[8].value
    };

    productList.push(product);
  });

  // AJAX로 서버에 전송
  fetch('/saveOrder', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ products: productList })
  })
  .then(response => {
    if (!response.ok) throw new Error('저장 실패');
    return response.json();
  })
  .then(data => {
    alert('저장 성공!');
    // 페이지 새로고침 또는 이동 등
  })
  .catch(error => {
    alert('저장 중 오류 발생: ' + error.message);
  });
}
