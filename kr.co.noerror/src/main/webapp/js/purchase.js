function addToCart() {

  const rows = document.querySelectorAll('#mrp tbody tr');
  const basket = document.getElementById('basketBody');

  rows.forEach(row => {
    const checkbox = row.querySelector('input[type="checkbox"]');
    if (checkbox && checkbox.checked) {
      const item_code = row.children[3].innerText.trim(); // 자재코드
      const item_type = row.children[4].innerText.trim(); // 자재유형
      const item_name = row.children[5].innerText.trim(); // 자재명
      const purchase_qty = row.children[10].querySelector('input').value; // 발주수량
      const item_unit = row.children[7].innerText.trim(); // 단위
      const company_name = row.children[11].innerText.trim(); // 단위

      if (!purchase_qty || purchase_qty <= 0) {
        alert(`발주수량이 올바르지 않습니다. (${item_code})`);
        return;
      }

      // 중복 확인
      const already = [...basket.children].some(r => r.dataset.item_code === item_code);
      if (already) {
        alert(`${item_code} 자재는 이미 바구니에 있습니다.`);
        return;
      }

      const tr = document.createElement('tr');
      tr.dataset.item_code = item_code;
      tr.innerHTML = `
        <td>${item_code}</td>
        <td>${item_type}</td>
        <td>${item_name}</td>
        <td>${purchase_qty}</td>
        <td>${item_unit}</td>
        <td>${company_name}</td>
        <td><button class="btn btn-sm btn-danger" onclick="removeRow(this)">삭제</button></td>
      `;
      basket.appendChild(tr);
    }
  });
}

function purchase_request() {
  const due_date = document.querySelector('input[name="due_date"]').value;
  const memo = document.getElementById('memo').value;

  // 바구니에 담긴 항목 수집
  const basket = document.querySelectorAll('#basketBody tr');
  const data = [];

  basket.forEach(row => {
    const purchase = {
      item_code: row.children[0].innerText,
      item_type: row.children[1].innerText,
      item_name: row.children[2].innerText,
      item_qty: parseInt(row.children[3].innerText.trim(), 10),
      item_unit: row.children[4].innerText,
      company_name: row.children[5].innerText,
	  due_date: due_date,
	  memo: memo
    };
	data.push(purchase);
  });

  // 데이터 유효성 검사
  if (data.length === 0) {
    alert("바구니에 항목이 없습니다.");
    return;
  }
  
  if (!due_date) {
    alert("납기요청일을 입력하세요.");
    return;
  }
  
  if (!memo) {
    alert("메모를 입력하세요.");
    return;
  }


  // Ajax 전송
  fetch('/purchase_request.do', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  })
  .then(res => {
    if (!res.ok) throw new Error('서버 오류');
    return res.json();
  })
  .then(response => {
      if (response.success) {
          alert("MRP 결과 저장 완료!");
  		  window.location.href = "/purchase.do";  // 페이지 이동
      } else {
          alert("저장 실패: " + response.message);
      }
  })
  .catch(err => {
      alert("에러 발생: " + err.message);
  });
}
