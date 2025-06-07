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
        alert("필수 항목을 모두 입력해 주세요.");
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
        plan_products: details
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
            window.location.href = "/production_plan_list.do";
        } else {
            alert("저장 실패: " + (result.message || "알 수 없는 오류"));
        }
    })
    .catch(error => {
        console.error("저장 중 오류 발생:", error);
        alert("서버 통신 오류 발생");
    });
}

