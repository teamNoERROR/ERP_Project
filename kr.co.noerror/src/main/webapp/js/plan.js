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





//주문 생산계획 정보 저장
function submitOrderPlan() {
    // 필수 필드 가져오기
    const order_code = document.querySelector('#order_code').value.trim();
    const company_code = document.querySelector('#company_code').value.trim();
    const due_date = document.querySelector('#due_date').value;
    const priority = document.querySelector('#priority').value;
    const start_date = document.querySelector('#plan_start_date').value;
    const end_date = document.querySelector('#plan_end_date').value;
    const ecode = document.querySelector('#emp_code').value;
    const ename = document.querySelector('#emp_name').value;
    const memo = document.querySelector('#plan_note').value;

    // 유효성 검사
    if (!order_code || !due_date || !start_date || !end_date || !ecode) {
        alert("필수 w항목을 모두 입력해 주세요.");
        return;
    }

    // 상세 데이터 수집
    const productRows = document.querySelectorAll('#plan_detail_products tr.product_row');
    if (productRows.length === 0) {
        alert("생산계획 상세 항목이 없습니다.");
        return;
    }

    const details = [];
    let invalidQty = false;

    productRows.forEach(row => {
        const product_code = row.querySelector('.product_code').innerText.trim();
        const bom_code = row.querySelector('.bom_code').innerText.trim();
        const product_qty = parseInt(row.querySelector('.plan_qty').value);
		
        if (isNaN(product_qty) || product_qty <= 0) {
            invalidQty = true;
        }

        details.push({
            product_code: product_code,
            bom_code: bom_code,
            product_qty: product_qty
        });
    });

    if (invalidQty) {
        alert("생산 수량은 0보다 커야 합니다.");
        return;
    }

    // Header + Detail 결합
    const planData = {
        order_code,
		company_code,
        due_date,
        priority,
        start_date,
        end_date,
        ecode,
        ename,
        memo,
        products: details
    };

    // 서버 전송
    fetch('/prdplan_save.do', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(planData)
    })
    .then(response => response.json())
    .then(result => {
        if (result.success) {
            alert("저장 성공!");
            window.location.href = "/production.do";
        } else {
            alert("저장 실패: " + (result.message || "알 수 없는 오류"));
        }
    })
    .catch(error => {
        console.error("저장 중 오류 발생:", error);
        alert("서버 통신 오류 발생");
    });
}


//재고 생산계획 정보 저장
function submitStockPlan() {
    // 필수 필드 가져오기
    const priority = document.querySelector('#stock_priority').value;
    const start_date = document.querySelector('#stock_start_date').value;
    const end_date = document.querySelector('#stock_end_date').value;
    const ecode = document.querySelector('#stock_emp_code').value;
    const memo = document.querySelector('#stock_note').value;

    // 유효성 검사
    if (!priority || !start_date || !end_date || !ecode) {
        alert("필수 항목을 모두 입력해 주세요.");
        return;
    }

    // 상세 데이터 수집
    const productRows = document.querySelectorAll('#plan_detail_products_stock tr.product_row_stock');
    if (productRows.length === 0) {
        alert("생산계획 상세 항목이 없습니다.");
        return;
    }

    const details = [];
    let invalidQty = false;

    productRows.forEach(row => {
        const product_code = row.querySelector('.product_code').innerText.trim();
        const bom_code = row.querySelector('.bom_code').innerText.trim();
        const product_qty = parseInt(row.querySelector('.stk_plan_qty').value);
		
        if (isNaN(product_qty) || product_qty <= 0) {
            invalidQty = true;
        }

        details.push({
            product_code: product_code,
            bom_code: bom_code,
            product_qty: product_qty
        });
    });

    if (invalidQty) {
        alert("생산 수량은 0보다 커야 합니다.");
        return;
    }

    // Header + Detail 결합
    const planData = {
        priority,
        start_date,
        end_date,
        ecode,
        memo,
        products: details
    };

    // 서버 전송
    fetch('/prdplan_save.do', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(planData)
    })
    .then(response => response.json())
    .then(result => {
        if (result.success) {
            alert("저장 성공!");
            window.location.href = "/production.do";
        } else {
            alert("저장 실패: " + (result.message || "알 수 없는 오류"));
        }
    })
    .catch(error => {
        console.error("저장 중 오류 발생:", error);
        alert("서버 통신 오류 발생");
    });
}




function plan_status_update() {
    const selectEl = document.getElementById("modal-status-select");
    const selectedStatus = selectEl.value;
    const plan_code = selectEl.getAttribute("data-plan-code");
	
    if (selectedStatus === "생산상태 선택") {
        alert("생산상태를 선택하세요.");
        return;
    }

    if (!plan_code) {
        alert("생산계획코드를 찾을 수 없습니다.");
        return;
    }

    const data = {
        plan_code: plan_code,
        plan_status: selectedStatus
    };

    fetch("/plan_status_update.do", {
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

function prdplan_update() {
	const frm = document.getElementById('frm');

	const priority = frm.querySelector('select[name="priority"]');
	const startDate = frm.querySelector('input[name="start_date"]');
	const endDate = frm.querySelector('input[name="end_date"]');
	const ecode = frm.querySelector('input[name="ecode"]');
	const ename = frm.querySelector('input[name="ename"]');
	const memo = frm.querySelector('textarea[name="memo"]');
	const rows = document.querySelectorAll('#prdplan-tbody tr');
	
	// 유효성 검사
	if (!priority.value) {
		alert("우선순위를 선택하세요.");
		priority.focus();
		return;
	} 
	if (!ename.value.trim()) {
		alert("생산계획 담당자가 입력되지 않았습니다.");
		ename.focus();
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
		plan_code: frm.querySelector('input[name="plan_code"]').value,
		priority: priority.value,
		start_date: startDate.value,
		end_date: endDate.value,
		ecode: ecode.value,
		memo: memo.value,
		products: products
	};

	// AJAX 통신 (POST)
	fetch('/prdplan_updateok.do', {
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
			alert('생산계획정보가 수정되었습니다.');
			window.location.href = "/production.do";
		} else {
			alert('수정 실패: ' + (result.message || '알 수 없는 오류'));
		}
	})
	.catch(error => {
		console.error(error);
		alert('수정 중 오류 발생');
	});
}


