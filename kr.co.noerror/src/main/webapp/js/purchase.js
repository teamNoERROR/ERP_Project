function addToCart() {
  const rows = document.querySelectorAll('#mrp tbody tr');
  const basket = document.getElementById('basketBody');

  const groupedItems = {};

  rows.forEach(row => {
    const checkbox = row.querySelector('input[type="checkbox"]');
    if (checkbox && checkbox.checked) {
      const item_code = row.children[3].innerText.trim();
      const item_type = row.children[4].innerText.trim();
      const item_name = row.children[5].innerText.trim();
      const item_unit = row.children[7].innerText.trim();
      const item_cost = row.children[8].innerText.trim();
      const purchase_qty = row.children[11].querySelector('input').value;
      const company_code = row.children[12].innerText.trim();
      const company_name = row.children[13].innerText.trim();

      if (!purchase_qty || purchase_qty <= 0) {
        alert(`발주수량이 올바르지 않습니다. (${item_code})`);
        return;
      }

      const already = [...basket.querySelectorAll('tr')].some(r => r.dataset.item_code === item_code);
      if (already) {
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
        item_type,
        item_name,
        purchase_qty,
        item_unit,
        item_cost
      });
    }
  });

  // 기존 목록 초기화
  basket.innerHTML = '';

  // 화면에 출력
  for (const [company_code, group] of Object.entries(groupedItems)) {
    const company_name = group.company_name;
    const items = group.items;

    // 🔷 업체 정보 입력 폼
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
	      <option value="현금결재">현금결재</option>
	      <option value="신용카드">신용카드</option>
	      <option value="계좌이체">계좌이체</option>
	      <option value="외상">외상</option>
	    </select>
	    비고: <input type="text" name="memo_${company_code}" size="40" />
	  </td>
	`;
    basket.appendChild(inputRow);

    // 🔷 자재 목록
    items.forEach(item => {
      const tr = document.createElement('tr');
      tr.dataset.item_code = item.item_code;
      tr.innerHTML = `
        <td>${item.item_code}</td>
        <td>${item.item_type}</td>
        <td>${item.item_name}</td>
        <td>${item.purchase_qty}</td>
        <td>${item.item_unit}</td>
        <td>${item.item_cost}</td>
        <td>${item.purchase_qty * item.item_cost}</td>
        <td><button class="btn btn-sm btn-danger" onclick="removeRow(this)">삭제</button></td>
      `;
      basket.appendChild(tr);
    });
  }
}

function purchase_request() {
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
	      memo: currentInputs.memo,
	      items: []
	    };
	  }
	} else {
      // 자재 행 처리
      const purchase = {
        item_code: row.children[0].innerText.trim(),
        item_type: row.children[1].innerText.trim(),
        item_name: row.children[2].innerText.trim(),
        item_qty: parseInt(row.children[3].innerText.trim(), 10),
        item_unit: row.children[4].innerText.trim(),
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
  
  fetch('/purchase_request.do', {
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
    if (response.success) {
      alert("발주정보 결과 저장 완료!");
      window.location.href = "/purchase.do";
    } else {
      alert("저장 실패: " + response.message);
    }
  })
  .catch(err => {
    alert("에러 발생: " + err.message);
  });
}
