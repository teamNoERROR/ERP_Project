//주문처 선택 및 정보 작성
function client_select() {
  // 선택된 라디오 버튼 찾기
  const selectedRadio = document.querySelector('input[name="client_check"]:checked');
  
  if (!selectedRadio) {
    alert("거래처를 선택하세요.");
    return;
  }
  
  // 선택된 라디오 버튼의 부모 <tr> 찾기
  const selectedRow = selectedRadio.closest('tr');
  if (!selectedRow) {
    alert("선택된 거래처 행을 찾을 수 없습니다.");
    return;
  }
  
  // data-* 속성 가져오기
  const company_code = selectedRow.getAttribute('data-company_code');
  const company_name = selectedRow.getAttribute('data-company_name');
  const biz_num = selectedRow.getAttribute('data-biz_num');
  const manager_name = selectedRow.getAttribute('data-manager_name');
  const phone_num = selectedRow.getAttribute('data-phone_num');

  // 각 input 요소에 값 설정
  // 부모 페이지에서 거래처 정보 input들이 순서대로 위치하므로, 아래와 같이 선택
  const inputs = window.parent.document.querySelectorAll('.row.mb-3 input.form-control[readonly]');
  if (inputs.length < 5) {
    alert("거래처 정보를 표시할 input 요소를 찾을 수 없습니다.");
    return;
  }
  
  inputs[0].value = company_code;
  inputs[1].value = company_name;
  inputs[2].value = biz_num;
  inputs[3].value = manager_name;
  inputs[4].value = phone_num;

  // 모달 닫기 (Bootstrap 5 기준)
  const modalEl = document.getElementById('client_list');
  const modalInstance = bootstrap.Modal.getInstance(modalEl);
  if (modalInstance) {
    modalInstance.hide();
  }
}


//주문 품목 목록 선택 및 작성
function product_select() {
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
  });

  // 모달 닫기
  const modalEl = document.getElementById('products_list');
  const modalInstance = bootstrap.Modal.getInstance(modalEl);
  modalInstance.hide();
}

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

//주문 정보 저장
function order_save() {
  // 주문처 정보 읽기 (부모 페이지에서 readonly input 5개)
  const companyCode = document.getElementById('company_code').value.trim();
  const companyName = document.getElementById('company_name').value.trim();
  const managerName = document.getElementById('manager_name').value.trim();
  
  // 예시: 주문코드, 주문상태, 요청일자, 납기일 등은 임의값 혹은 별도 UI에서 받아야 함
  const orderCode = generateOrderCode(); // 임의 생성 함수 혹은 서버에서 발급받음
  const orderStatus = "주문요청";
  const dueDate = "";     // 필요시 폼에서 받아오세요
  const memo = "";        // 필요시 폼에서 받아오세요
  const requestDate = new Date().toISOString().slice(0,10); // 오늘 날짜, yyyy-MM-dd 형식
  const modifyDate = requestDate;

  const productsRows = document.querySelectorAll('#products tr.total-delete');
  if (productsRows.length === 0) {
    alert("주문 품목을 추가하세요.");
    return;
  }

  const orderItems = [];
  for (const row of productsRows) {
    const inputs = row.querySelectorAll('input.form-control');

    const productCode = inputs[0].value.trim();
    const orderQtyStr = inputs[5].value.trim();

    if (!orderQtyStr || isNaN(orderQtyStr) || Number(orderQtyStr) <= 0) {
      alert("주문 수량을 1 이상으로 입력해주세요.");
      inputs[5].focus();
      return;
    }

	orderItems.push({
	  orderCode: orderCode,
	  productCode: productCode,
	  orderQty: parseInt(orderQtyStr, 10),
	  companyCode: companyCode,
	  companyName: companyName,
	  managerName: managerName,
	  orderStatus: orderStatus,
	  dueDate: dueDate,
	  memo: memo,
	  requestDate: requestDate,
	  modifyDate: modifyDate
	});
  }

  // AJAX 예시 (fetch API)
  fetch('/order_save.do', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(orderItems)
  })
  .then(res => {
    if (!res.ok) throw new Error("서버 에러");
    return res.json();
  })
  .then(data => {
    alert("주문 저장 성공");
    // 필요시 화면 초기화 또는 리다이렉트
  })
  .catch(err => {
    alert("저장 실패: " + err.message);
  });
}

// 주문코드 임의 생성 예시
function generateOrderCode() {
  const now = new Date();
  return "ORD" + now.getFullYear() + 
         (now.getMonth()+1).toString().padStart(2,'0') + 
         now.getDate().toString().padStart(2,'0') +
         now.getHours().toString().padStart(2,'0') + 
         now.getMinutes().toString().padStart(2,'0') + 
         now.getSeconds().toString().padStart(2,'0');
}