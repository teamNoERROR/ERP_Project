// 체크박스 전체 선택/해제 스크립트(mrp_input)
document.addEventListener("DOMContentLoaded", function () {
  const checkAll = document.getElementById("check-all-input");
  const rowCheckboxes = document.querySelectorAll(".row-checkbox-input");

  checkAll.addEventListener("change", function () {
    rowCheckboxes.forEach(cb => cb.checked = checkAll.checked);
  });
});

document.addEventListener("change", function (e) {
  if (e.target.classList.contains("row-checkbox-input")) {
    const all = document.querySelectorAll(".row-checkbox-input");
    const checked = document.querySelectorAll(".row-checkbox-input:checked");
    document.getElementById("check-all-input").checked = all.length === checked.length;
  }
});

// 체크박스 전체 선택/해제 스크립트(mrp_result)
document.addEventListener("DOMContentLoaded", function () {
  const checkAll = document.getElementById("check-all-result");

  checkAll.addEventListener("change", function () {
    const rowCheckboxes = document.querySelectorAll(".row-checkbox-result"); //최신 DOM 기준
    rowCheckboxes.forEach(cb => cb.checked = checkAll.checked);
  });
});

document.addEventListener("change", function (e) {
  if (e.target.classList.contains("row-checkbox-result")) {
    const all = document.querySelectorAll(".row-checkbox-result");
    const checked = document.querySelectorAll(".row-checkbox-result:checked");
    document.getElementById("check-all-result").checked = all.length === checked.length;
  }
});


//mrp 계산
document.getElementById("mrp_calc").addEventListener("click", function () {
    const checkboxes = document.querySelectorAll(".row-checkbox-input:checked");
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
		
		const tbody = document.getElementById("mrp-result-tbody");
		    tbody.innerHTML = ""; // 이전 내용 초기화

		    result.forEach((item, index) => {
		        const row = document.createElement("tr");

		        row.innerHTML = `
		            <td><input type="checkbox" checked disabled class="row-checkbox-result"></td>
		            <td>${index + 1}</td>
		            <td>${item.item_code}</td>
		            <td>${item.item_name}</td>
		            <td>${item.required_qty}</td>
		            <td>${item.item_unit}</td>
		            <td>${item.item_cost}</td>
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

//MRP 계산결과 저장
function calc_save() {
    const dataToSave = collectMRPResultData();

    if (dataToSave.length === 0) {
        alert("데이터베이스에 저장할 체크된 행이 없습니다.");
        return;
    }

    fetch("/mrp_save.do", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(dataToSave)
    })
    .then(res => res.json())
    .then(response => {
        if (response.success) {
            alert("MRP 결과 저장 완료!");
			
			// mrp_code를 숨겨진 input에 저장
			document.getElementById("mrpCodeHidden").value = response.mrp_code;
        } else {
            alert("저장 실패: " + response.message);
        }
    })
    .catch(err => {
        alert("에러 발생: " + err.message);
    });
}

//발주화면으로 이동
function go_purchase() {
	const mrpCode = document.getElementById("mrpCodeHidden").value;
	
	if (!mrpCode) {
	        alert("MRP 계산 및 MRP 저장을 완료한 후 이용 가능합니다.");
	        return;
	}

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
}

function collectMRPResultData() {
    const rows = document.querySelectorAll("#mrp-result-tbody tr");
    const resultData = [];

    rows.forEach(row => {
        const checkbox = row.querySelector(".row-checkbox-result");
        if (checkbox && checkbox.checked) {
            const cells = row.querySelectorAll("td");

            resultData.push({
                item_code: cells[2].textContent.trim(),
                item_name: cells[3].textContent.trim(),
                required_qty: parseInt(cells[4].textContent.trim()),
                item_unit: cells[5].textContent.trim(),
                item_cost: cells[6].textContent.trim(),
                total_stock: parseInt(cells[7].textContent.trim()),
                safety_stock: parseInt(cells[8].textContent.trim()),
                reserved_stock: parseInt(cells[9].textContent.trim()),
                available_stock: parseInt(cells[10].textContent.trim()),
                shortage_stock: parseInt(cells[11].textContent.trim())
            });
        }
    });

    return resultData;
}

//엑셀 다운로드
function excel_download() {
    const rows = document.querySelectorAll("#mrp-result-tbody tr");
    const resultData = [];

    rows.forEach(row => {
        const checkbox = row.querySelector(".row-checkbox-result");
        if (checkbox && checkbox.checked) {
            const cells = row.querySelectorAll("td");

            resultData.push({
                '품목코드': cells[2].textContent.trim(),
                '품목유형': cells[3].textContent.trim(),
                '품목명': cells[4].textContent.trim(),
                '소요량': parseInt(cells[5].textContent.trim()),
                '단위': cells[6].textContent.trim(),
                '총재고': parseInt(cells[7].textContent.trim()),
                '안전재고': parseInt(cells[8].textContent.trim()),
                '예약재고': parseInt(cells[9].textContent.trim()),
                '가용재고': parseInt(cells[10].textContent.trim()),
                '부족재고': parseInt(cells[11].textContent.trim())
            });
        }
    });

    if (resultData.length === 0) {
        alert("엑셀로 다운로드할 체크된 행이 없습니다.");
        return;
    }

    // 워크시트 생성
    const worksheet = XLSX.utils.json_to_sheet(resultData);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "MRP 결과");

	// 오늘 날짜 yyyy-mm-dd 형식으로 얻기
	const today = new Date();
	const yyyy = today.getFullYear();
	const mm = String(today.getMonth() + 1).padStart(2, '0');  // 월은 0부터 시작
	const dd = String(today.getDate()).padStart(2, '0');
	const formattedDate = `${yyyy}-${mm}-${dd}`;

	// 파일명 지정
	XLSX.writeFile(workbook, `MRP계산_${formattedDate}.xlsx`);
}



