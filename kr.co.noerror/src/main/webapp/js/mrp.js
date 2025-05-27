// 체크박스 전체 선택/해제 스크립트
document.addEventListener("DOMContentLoaded", function () {
  const checkAll = document.getElementById("check-all");
  const rowCheckboxes = document.querySelectorAll(".row-checkbox");

  checkAll.addEventListener("change", function () {
    rowCheckboxes.forEach(cb => cb.checked = checkAll.checked);
  });
});

//mrp 계산
document.getElementById("mrp_calc").addEventListener("click", function () {
    const checkboxes = document.querySelectorAll(".row-checkbox:checked");
    const dataToSend = [];

    checkboxes.forEach(checkbox => {
        const bomCode = checkbox.dataset.bomCode;
        const qty = parseInt(checkbox.dataset.productQty);
        dataToSend.push({ bom_code: bomCode, product_qty: qty });
    });

    if (dataToSend.length === 0) {
        alert("MRP 계산할 행을 선택하세요.");
        return;
    }

    fetch("/mrp_calc.do", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dataToSend)
    })
    .then(res => res.json())
    .then(result => {
        alert("MRP 계산 완료!");
        console.log("결과:", result);
		
		const tbody = document.getElementById("mrp-result-tbody");
		    tbody.innerHTML = ""; // 이전 내용 초기화

		    result.forEach((item, index) => {
		        const row = document.createElement("tr");

		        row.innerHTML = `
		            <td><input type="checkbox"></td>
		            <td>${index + 1}</td>
		            <td>${item.item_code}</td>
		            <td>${item.item_type}</td>
		            <td>${item.item_name}</td>
		            <td>${item.required_qty}</td>
		            <td>${item.item_unit}</td>
		            <td>${item.total_stock}</td>
		            <td>${item.safety_stock}</td>
		            <td>${item.reserved_stock}</td>
		            <td>${item.available_stock}</td>
		            <td>${item.shortage_stock}</td>
		        `;

		        tbody.appendChild(row);
		    });
		
		
    })
    .catch(err => {
        alert("에러 발생: " + err.message);
    });
});


