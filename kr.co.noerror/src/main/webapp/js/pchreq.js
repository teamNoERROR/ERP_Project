function select_mrp(){
	// 선택된 라디오 버튼 찾기
	const selectedRadio = document.querySelector('input[name="mrpSelect"]:checked');

	if (!selectedRadio) {
	  alert("MPP를 선택하세요.");
	  return;
	}

	// 선택된 라디오 버튼의 부모 <tr> 찾기
	const selectedRow = selectedRadio.closest('tr');
	if (!selectedRow) {
	  alert("선택된 MRP 행을 찾을 수 없습니다.");
	  return;
	}

	// data-* 속성 가져오기
	const mrpCode = selectedRow.getAttribute('data-mrp_code');
	
	// 폼 생성
    const form = document.createElement("form");
    form.method = "GET";
    form.action = "/mrp_result_select.do"

	// 데이터 input
	const dataInput = document.createElement("input");
	dataInput.type = "hidden";
	dataInput.name = "mrp_code";
	dataInput.value = mrpCode;
	form.appendChild(dataInput); 

	document.body.appendChild(form);
	form.submit();

	// 모달 닫기 (Bootstrap 5 기준)
	const modalEl = document.getElementById('mrp_list');
	const modalInstance = bootstrap.Modal.getInstance(modalEl);
	if (modalInstance) {
	  modalInstance.hide();
	}
}


function addToCart() {
  const activeTab = document.querySelector('.nav-link.active').id;
  const basket = document.getElementById('basketBody');

  // 기존 아이템 목록 추출 (중복 방지용)
  const existingItemCodes = new Set([...basket.querySelectorAll('tr[data-item_code]')].map(tr => tr.dataset.item_code));

  const groupedItems = {};

  if (activeTab === 'mrp-tab') {
    const rows = document.querySelectorAll('#mrp tbody tr');

    rows.forEach(row => {
      const checkbox = row.querySelector('input[type="checkbox"]');
      if (checkbox && checkbox.checked) {
        const item_code = row.children[3].innerText.trim();
        const item_name = row.children[4].innerText.trim();
        const item_spec = row.children[5].innerText.trim();
        const item_unit = row.children[6].innerText.trim();
        const item_cost = row.children[8].innerText.trim();
        const purchase_qty = row.children[11].querySelector('input').value;
        const company_code = row.children[12].innerText.trim();
        const company_name = row.children[13].innerText.trim();

        if (!purchase_qty || purchase_qty <= 0) {
          alert(`발주수량이 올바르지 않습니다. (${item_code})`);
          return;
        }

        if (existingItemCodes.has(item_code)) {
          alert(`${item_code} 자재는 이미 바구니에 있습니다.`);
          return;
        }

        if (!groupedItems[company_code]) {
          groupedItems[company_code] = {
            company_name,
            items: []
          };
        }

        groupedItems[company_code].items.push({
          item_code,
          item_name,
          item_spec,
          item_unit,
          purchase_qty,
          item_cost
        });

        existingItemCodes.add(item_code);
      }
    });

  } else if (activeTab === 'item-tab') {
	  const rows = document.querySelectorAll('#item tbody tr');
	  var count = 0;
	  rows.forEach(row => {
	    const checkbox = row.querySelector('input[type="checkbox"]');
	    if (checkbox && checkbox.checked) {
		  count++;
	      const item_code = row.children[1].querySelector('input')?.value.trim();
	      const item_name = row.children[2].querySelector('input')?.value.trim();
	      const item_spec = row.children[3].querySelector('input')?.value.trim();
	      const item_unit = row.children[4].querySelector('input')?.value.trim();
	      const purchase_qty = row.children[5].querySelector('input')?.value.trim();
	      const item_cost = row.children[6].querySelector('input')?.value.trim();
	      const company_name = row.children[7].querySelector('input')?.value.trim();
	      const company_code = company_name || "기타";
	      if (!purchase_qty || purchase_qty <= 0) {
	        alert(`발주수량이 올바르지 않습니다. (${item_code})`);
	        return;
	      }

	      if (existingItemCodes.has(item_code)) {
	        alert(`${item_code} 자재는 이미 바구니에 있습니다.`);
	        return;
	      }

	      if (!groupedItems[company_code]) {
	        groupedItems[company_code] = {
	          company_name,
	          items: []
	        };
	      }

	      groupedItems[company_code].items.push({
	        item_code,
	        item_name,
	        item_spec,
	        item_unit,
	        purchase_qty,
	        item_cost
	      });

	      existingItemCodes.add(item_code);
	    }
	  });
	}
	if(count == 0){
		alert(`검색한 후 최소 1개이상 선택해 주세요.`);
		return;
	}

  // 바구니에 추가
  for (const [company_code, group] of Object.entries(groupedItems)) {
    const company_name = group.company_name || '';
    const items = group.items;

    // 업체 정보 입력 폼
    const inputRow = document.createElement('tr');
    inputRow.innerHTML = `
      <td colspan="8" style="background-color:#eef; padding:10px;">
        <strong>
          업체코드: <span id="company_code_${company_code}">${company_code}</span> /
          업체명: <span id="company_name_${company_code}">${company_name}</span>
        </strong><br/>
        납기요청일: <input type="date" name="due_date_${company_code}" style="margin-right: 20px;" />
        결제수단: 
        <select name="pay_method_${company_code}" style="margin-right: 20px;">
          <option value="현금결제">현금결제</option>
          <option value="신용카드">신용카드</option>
          <option value="계좌이체">계좌이체</option>
          <option value="외상">외상</option>
        </select>
        비고: <input type="text" name="memo_${company_code}" size="40" />
      </td>
    `;
    basket.appendChild(inputRow);

    items.forEach(item => {
      const tr = document.createElement('tr');
      tr.dataset.item_code = item.item_code;
      tr.innerHTML = `
        <td>${item.item_code}</td>
        <td>${item.item_name}</td>
        <td>${item.item_spec}</td>
        <td>${item.item_unit}</td>
        <td>${item.purchase_qty}</td>
        <td>${item.item_cost}</td>
        <td>${item.purchase_qty * item.item_cost}</td>
        <td><button class="btn btn-sm btn-danger" onclick="removeItemRow(this)">삭제</button></td>
      `;
      basket.appendChild(tr);
    });
  }
}

function pchreq_save() {
  const ecode = document.getElementById("ecode").value;
  const ename = document.getElementById("ename").value;

  if (!ecode || !ename) {
	alert("발주담당자를 선택해주세요.");
	return;
  }
	
  const basketRows = document.querySelectorAll('#basketBody tr');
  const groupedData = {};
  let currentCompanyCode = '';
  let currentCompanyName = '';
  let currentInputs = {};

  basketRows.forEach(row => {
	const isCompanyRow = row.querySelector('strong');
	if (isCompanyRow) {
	  const codeEl = row.querySelector('span[id^="company_code_"]');
	  const nameEl = row.querySelector('span[id^="company_name_"]');

	  if (codeEl && nameEl) {
	    currentCompanyCode = codeEl.innerText.trim();
	    currentCompanyName = nameEl.innerText.trim();

	    const due_date_input = row.querySelector(`input[name="due_date_${currentCompanyCode}"]`);
	    const pay_method_select = row.querySelector(`select[name="pay_method_${currentCompanyCode}"]`);
	    const memo_input = row.querySelector(`input[name="memo_${currentCompanyCode}"]`);

	    currentInputs = {
	      due_date: due_date_input?.value || '',
	      pay_method: pay_method_select?.value || '',
	      memo: memo_input?.value || ''
	    };

	    // 유효성 검사
	    if (!currentInputs.due_date) {
	      alert(`[${currentCompanyName}] 납기일을 입력하세요.`);
	      throw new Error('납기일 누락');
	    }
	    if (!currentInputs.memo) {
	      alert(`[${currentCompanyName}] 비고를 입력하세요.`);
	      throw new Error('비고 누락');
	    }

	    groupedData[currentCompanyCode] = {
	      company_code: currentCompanyCode,
	      company_name: currentCompanyName,
	      due_date: currentInputs.due_date,
	      pay_method: currentInputs.pay_method,
		  ecode: ecode,
	      memo: currentInputs.memo,
	      items: []
	    };
	  }
	} else {
      // 자재 행 처리
      const purchase = {
        item_code: row.children[0].innerText.trim(),
        item_name: row.children[1].innerText.trim(),
        item_type: row.children[2].innerText.trim(),
        item_unit: row.children[3].innerText.trim(),
        item_qty: parseInt(row.children[4].innerText.trim(), 10),
        item_cost: parseFloat(row.children[5].innerText.trim()),
        item_amount: parseFloat(row.children[6].innerText.trim())
      };

      if (currentCompanyCode && groupedData[currentCompanyCode]) {
        groupedData[currentCompanyCode].items.push(purchase);
      }
    }
  });

  if (Object.keys(groupedData).length === 0) {
    alert("바구니에 항목이 없습니다.");
    return;
  }
  
  fetch('/pchreq_save.do', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(groupedData)
  })
  .then(res => {
    if (!res.ok) throw new Error('서버 오류');
    return res.json();
  })
  .then(response => {
	console.log("서버 응답:", response);
    if (response.success) {
      alert("발주정보 결과 저장 완료!");
      window.location.href = "/pchreq_list.do";
    } else {
      alert("저장 실패: " + response.message);
    }
  })
  .catch(err => {
    alert("에러 발생: " + err.message);
  });
}

function pch_status_update() {
    const selectEl = document.getElementById("modal-status-select");
    const selectedStatus = selectEl.value;
    const pch_code = selectEl.getAttribute("data-pch-code");

    if (selectedStatus === "발주상태 선택") {
        alert("발주상태를 선택하세요.");
        return;
    }

    if (!pch_code) {
        alert("발주코드를 찾을 수 없습니다.");
        return;
    }

    const data = {
        pch_code: pch_code,
        pch_status: selectedStatus
    };

    fetch("/pch_status_update.do", {
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
            location.reload(); // 또는 모달만 닫기: $('#pch_modal').modal('hide');
        } else {
            alert("상태 변경에 실패했습니다.");
        }
    })
    .catch(err => {
        console.error("에러 발생:", err);
        alert("서버 오류가 발생했습니다.");
    });
}

//발주바구니 비우기
function clear_cart(){
	const basket = document.getElementById('basketBody');
	basket.innerHTML = ''; // 바구니 내용 전체 제거
}

//주문수량 변경시 구매금액 자동 변경
document.addEventListener('DOMContentLoaded', function () {
	const qtyInputs = document.querySelectorAll('.qty-input');

	function updateRowAmount(row) {
		const qtyInput = row.querySelector('.qty-input');
		const costCell = row.querySelector('.cost-input');
		const amountCell = row.querySelector('.amount-cell');

		let qty = parseInt(qtyInput.value);
		let cost = parseInt(costCell.dataset.cost);

		if (isNaN(qty) || qty < 1) qty = 1;
		if (isNaN(cost) || cost < 0) cost = 0;

		const amount = qty * cost;
		amountCell.textContent = amount.toLocaleString();
		return amount;
	}

	function updateTotalAmount() {
		let total = 0;
		document.querySelectorAll('#product-tbody tr').forEach(row => {
			const qtyInput = row.querySelector('.qty-input');
			const costCell = row.querySelector('.cost-input');

			const qty = parseInt(qtyInput.value) || 0;
			const cost = parseInt(costCell.dataset.cost) || 0;
			total += qty * cost;
		});

		document.querySelectorAll('.total-amount').forEach(el => {
			el.textContent = total.toLocaleString();
		});
	}

	function onInputChange() {
		const row = this.closest('tr');
		updateRowAmount(row);
		updateTotalAmount();
	}

	qtyInputs.forEach(input => input.addEventListener('input', onInputChange));
});

function pchreq_update() {
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
		alert("발주 담당자를 입력하세요.");
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

	const items = [];
	for (const row of rows) {
		const itemCode = row.querySelector('.item-code').value;
		const qtyInput = row.querySelector('.qty-input');
		const qty = parseInt(qtyInput.value);
		if (isNaN(qty) || qty < 1) {
			alert('수량은 1 이상이어야 합니다.');
			qtyInput.focus();
			return;
		}
		items.push({
			item_code: itemCode,
			item_qty: qty
		});
	}
	
	// 서버로 보낼 데이터 구성
	const data = {
		pch_code: frm.querySelector('input[name="pch_code"]').value,
		due_date: dueDate.value,
		ecode: ecode.value,
		pay_method: payMethod.value,
		memo: frm.querySelector('textarea[name="memo"]').value,
		items: items
	};

	// AJAX 통신 (POST)
	fetch('/pchreq_updateok.do', {
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
			alert('발주정보가 수정되었습니다.');
			window.location.href = "/pchreq_list.do";
		} else {
			alert('수정 실패: ' + (result.message || '알 수 없는 오류'));
		}
	})
	.catch(error => {
		console.error(error);
		alert('수정 중 오류 발생');
	});
}
