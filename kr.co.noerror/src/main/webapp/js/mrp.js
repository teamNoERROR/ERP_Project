// 체크박스 전체 선택/해제 스크립트
document.addEventListener("DOMContentLoaded", function () {
  const checkAll = document.getElementById("check-all");
  const rowCheckboxes = document.querySelectorAll(".row-checkbox");

  checkAll.addEventListener("change", function () {
    rowCheckboxes.forEach(cb => cb.checked = checkAll.checked);
  });
});

//mrp 계산
function mrp_calc() {
  const checkedBoxes = document.querySelectorAll(".row-checkbox:checked");
  const bomCodes = Array.from(checkedBoxes).map(cb => cb.value);

  if (bomCodes.length === 0) {
    alert("MRP 계산할 행을 선택하세요.");
    return;
  }

  const form = document.getElementById("mrpForm");
  
  // 기존 hidden input 제거 (중복 방지)
  form.innerHTML = "";

  // bomCodes[] input들 추가
  bomCodes.forEach(code => {
    const input = document.createElement("input");
    input.type = "hidden";
    input.name = "bomCodes"; // name은 List 매핑을 위해 동일하게
    input.value = code;
    form.appendChild(input);
  });

  form.submit();
}


function mrp_calc() {
  const checkedBoxes = document.querySelectorAll(".row-checkbox:checked");
  
  if (checkedBoxes.length === 0) {
    alert("MRP 계산할 항목을 선택하세요.");
    return;
  }

  // 동적으로 form 생성
  const form = document.createElement("form");
  form.method = "POST";
  form.action = "/mrp_calc.do";
  
  checkedBoxes.forEach((checkbox, index) => {
    const row = checkbox.closest("tr");

    const bomCode = checkbox.value;
    const qty = row.querySelector(".product-qty")?.textContent?.trim();

    const bomInput = document.createElement("input");
    bomInput.type = "hidden";
    bomInput.name = `bomList[${index}].bom_code`;
    bomInput.value = bomCode;

    const qtyInput = document.createElement("input");
    qtyInput.type = "hidden";
    qtyInput.name = `bomList[${index}].product_qty`;
    qtyInput.value = qty;

    form.appendChild(bomInput);
    form.appendChild(qtyInput);
  });

  document.body.appendChild(form);
  form.submit();
}


