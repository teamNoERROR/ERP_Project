//주문처 선택 및 정보 작성
function client_select_for_order() {
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
  const manager_code = selectedRow.getAttribute('data-manager_code');
  const manager_name = selectedRow.getAttribute('data-manager_name');
  const phone_num = selectedRow.getAttribute('data-phone_num');

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
  const modalEl = document.getElementById('clients_modal_for_order');
  const modalInstance = bootstrap.Modal.getInstance(modalEl);
  if (modalInstance) {
    modalInstance.hide();
  }
}


//주문 품목 목록 선택 및 작성
function product_select_for_order() {
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
  const modalEl = document.getElementById('products_modal_for_order');
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
	<td><input type="number" class="form-control qty-input" min="1" placeholder="주문수량"></td>
	<td><input type="text" class="form-control" value="${product.unit}" readonly></td>
    <td><input type="text" class="form-control cost-input" value="${product.cost}" readonly></td>
    <td><input type="text" class="form-control amount-cell" placeholder=판매금액" readonly></td>
    <td class="text-center">
      <button type="button" class="btn btn-danger btn-sm" onclick="removeProductRow(this)">삭제</button>
    </td>
  `;
  tbody.appendChild(tr);
  
  // 👉 주문수량 입력 시 판매금액 자동 계산
    const qtyInput = tr.querySelector('.qty-input');
    const costInput = tr.querySelector('.cost-input');
    const amountCell = tr.querySelector('.amount-cell');

    qtyInput.addEventListener('input', () => {
      const qty = parseFloat(qtyInput.value) || 0;
      const cost = parseFloat(costInput.value) || 0;
      const amount = qty * cost;
      amountCell.value = amount.toLocaleString(); // 천단위 콤마 추가
    });
}

//주문 정보 저장
function order_save() {
  // 주문처 정보 읽기 (부모 페이지에서 readonly input 5개)
  const companyCode = document.getElementById('company_code').value.trim();
  const companyName = document.getElementById('company_name').value.trim();
  const managerCode = document.getElementById('manager_code').value.trim();
  const managerName = document.getElementById('manager_name').value.trim();
  
  // 예시: 주문코드, 주문상태, 요청일자, 납기일 등은 임의값 혹은 별도 UI에서 받아야 함
  const orderStatus = "주문요청";
  const dueDate = "2025-06-15";     
  const ecode = "EMP001";
  const payMethod = "현금결제";
  const whCode = "WHS-20390";
  const memo = "특별한 사항 없음";        
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
	const orderQty = orderQtyStr.replace(/,/g, '');
	const productAmount = inputs[8].value.trim().replace(/,/g, '');

	orderItems.push({
	  COMPANY_CODE: companyCode,
	  ECODE: ecode,
	  ORDER_STATUS: orderStatus,
	  PAY_METHOD: payMethod,
	  DUE_DATE: dueDate,
	  MEMO: memo,
	  REQUEST_DATE: requestDate,
	  MODIFY_DATE: modifyDate,
	  PRODUCT_CODE: productCode,
	  WH_CODE: whCode,
	  PRODUCT_QTY: orderQty,
	  PRODUCT_AMOUNT: productAmount
	});
  }

  // AJAX (fetch API)  
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
    if (data.success) {
      alert("저장 성공");
      window.location.href = "/order.do";
    } else {
      alert("저장 실패(알수없는 오류)");
    }
  })
  .catch(err => {
    alert("저장 실패 (요청 실패): " + err.message);
  });
}

function ordreq_update() {
	const frm = document.getElementById('frm');

	const dueDate = frm.querySelector('input[name="due_date"]');
	const ecode = frm.querySelector('input[name="ecode"]');
	const ename = frm.querySelector('input[name="ename"]');
	const payMethod = frm.querySelector('select[name="pay_method"]');
	const rows = document.querySelectorAll('#product-tbody tr');

	// 유효성 검사
	if (!dueDate.value) {
		alert("납기요청일을 입력하세요.");
		dueDate.focus();
		return;
	} 
	if (!ename.value.trim()) {
		alert("주문 담당자를 입력하세요.");
		ename.focus();
		return;
	} 
	if (!payMethod.value) {
		alert("결제 수단을 선택하세요.");
		payMethod.focus();
		return;
	} 
	
	if (rows.length === 0) {
		alert('최소 한 개 이상의 제품이 있어야 합니다.');
		return;
	}

	const products = [];
	for (const row of rows) {
		const productCode = row.querySelector('.product-code').value;
		const qtyInput = row.querySelector('.qty-input');
		const qty = parseInt(qtyInput.value);
		if (isNaN(qty) || qty < 1) {
			alert('수량은 1 이상이어야 합니다.');
			qtyInput.focus();
			return;
		}
		products.push({
			product_code: productCode,
			product_qty: qty
		});
	}
	
	// 서버로 보낼 데이터 구성
	const data = {
		order_code: frm.querySelector('input[name="order_code"]').value,
		due_date: dueDate.value,
		ecode: ecode.value,
		pay_method: payMethod.value,
		memo: frm.querySelector('textarea[name="memo"]').value,
		products: products
	};

	// AJAX 통신 (POST)
	fetch('/ordreq_updateok.do', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data)
	})
	.then(response => {
		if (!response.ok) {
			throw new Error('서버 응답 오류');
		}
		return response.json(); 
	})
	.then(result => {
		if (result.success) {
			alert('주문정보가 수정되었습니다.');
			window.location.href = "/order.do";
		} else {
			alert('수정 실패: ' + (result.message || '알 수 없는 오류'));
		}
	})
	.catch(error => {
		console.error(error);
		alert('수정 중 오류 발생');
	});
}

//주문상태 변경하기
function ord_status_update() {
    const selectEl = document.getElementById("modal-status-select");
    const selectedStatus = selectEl.value;
    const order_code = selectEl.getAttribute("data-order-code");

    if (selectedStatus === "주문상태 선택") {
        alert("주문상태를 선택하세요.");
        return;
    }

    if (!order_code) {
        alert("주문코드를 찾을 수 없습니다.");
        return;
    }

    const data = {
        order_code: order_code,
        order_status: selectedStatus
    };

    fetch("/ord_status_update.do", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(result => {
        if (result.success) {
            alert("상태가 성공적으로 변경되었습니다.");
            location.reload(); 
        } else {
            alert("상태 변경에 실패했습니다.");
        }
    })
    .catch(err => {
        console.error("에러 발생:", err);
        alert("서버 오류가 발생했습니다.");
    });
}

