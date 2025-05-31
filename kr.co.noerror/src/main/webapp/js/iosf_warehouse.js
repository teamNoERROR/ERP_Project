function validateInboundForm() {
  const form = document.getElementById('inboundWhForm');

  // 창고 코드
  const whCode = form.querySelector('[name="wh_code"]').value.trim();
  if (!whCode) {
    alert("창고를 선택해주세요.");
    return;
  }

  // 제품 코드
  const itemCode = form.querySelector('[name="item_code"]').value.trim();
  if (!itemCode) {
    alert("제품 코드를 입력해주세요.");
    form.querySelector('[name="item_code"]').focus();
    return;
  }

  // 제품명
  const itemName = form.querySelector('[name="item_name"]').value.trim();
  if (!itemName) {
    alert("제품명을 입력해주세요.");
    form.querySelector('[name="item_name"]').focus();
    return;
  }

  // 대분류
  const categoryMain = form.querySelector('[name="category_main"]').value.trim();
  if (!categoryMain) {
    alert("제품 대분류를 입력해주세요.");
    form.querySelector('[name="category_main"]').focus();
    return;
  }

  // 소분류
  const categorySub = form.querySelector('[name="category_sub"]').value.trim();
  if (!categorySub) {
    alert("제품 소분류를 입력해주세요.");
    form.querySelector('[name="category_sub"]').focus();
    return;
  }

  // 사용 여부
  const useYn = form.querySelector('[name="use_yn"]').value;
  if (!useYn) {
    alert("제품 사용 여부를 선택해주세요.");
    form.querySelector('[name="use_yn"]').focus();
    return;
  }

  // 거래처 코드
  const clientCode = form.querySelector('[name="client_code"]').value.trim();
  if (!clientCode) {
    alert("거래처 코드를 입력해주세요.");
    form.querySelector('[name="client_code"]').focus();
    return;
  }

  // 거래처명
  const clientName = form.querySelector('[name="client_name"]').value.trim();
  if (!clientName) {
    alert("거래처명을 입력해주세요.");
    form.querySelector('[name="client_name"]').focus();
    return;
  }

  // 거래처 사용 여부
 let clientUseYnValue = document.getElementById('clientUseYn').value;
  if (!clientUseYnValue) {
    alert("거래처 사용 여부를 선택해주세요.");
    form.querySelector('[name="client_use_flag"]').focus();
    return;
  }

  // 모든 조건 통과 시 폼 제출
  form.submit();
}