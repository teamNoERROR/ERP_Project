function validateInboundForm() {
  const form = document.getElementById('inboundWhForm');

  // 필수 필드 정의
  const requiredFields = [
    { id: 'wh_code', label: '창고코드' },
    { id: 'wh_name', label: '창고명' },
    { id: 'wh_location', label: '창고위치' },
    { id: 'item_code', label: '제품코드' },
    { id: 'item_name', label: '부자재명' },
    { id: 'item_count', label: '수량' },
    { id: 'in_status', label: '입고 상태' },
    { id: 'category_main', label: '제품 대분류' },
    { id: 'category_sub', label: '제품 소분류' },
    { id: 'client_code', label: '거래처 코드' },
    { id: 'employee_code', label: '등록 사원 코드' }
  ];

  for (let field of requiredFields) {
    const el = document.getElementById(field.id);
    if (!el || el.value.trim() === '' || el.value === null) {
      alert(`[${field.label}] 값을 입력해주세요.`);
      el.focus();
      return;
    }
  }

  // 선택 필드 확인: 입고 상태 select
  const inStatusSelect = document.querySelector('select[name="in_status"]');
  if (inStatusSelect && inStatusSelect.value === "") {
    alert("[입고 상태]를 선택해주세요.");
    inStatusSelect.focus();
    return;
  }

  // 검증 통과 후 폼 제출
  form.submit();
}

/*
// 출고창고에서 출고처리
function completeOutbound() {
  // 체크된 체크박스 선택
  const checkedBoxes = document.querySelectorAll('.outWareHouse-checkbox:checked');

  if (checkedBoxes.length === 0) {
    alert('출고완료할 항목을 선택해주세요.');
    return;
  }

  // 체크된 항목들의 데이터 수집
  const selectedItems = Array.from(checkedBoxes).map(cb => ({
    out_code: cb.dataset.out_code
	
  }));

  // 확인 창
  if (!confirm('선택한 항목을 출고완료 처리하시겠습니까?')) return;

  // Ajax 요청
  fetch('/out_warehouse_complete.do', { // 이 부분에 실제 처리할 URL을 입력하세요
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ items: selectedItems }) // 서버에 items라는 키로 데이터 전송
  })
  .then(response => response.json())
  .then(result => {
    if (result.success) {
      alert('출고완료 처리되었습니다.');
      location.reload(); // 성공 시 새로고침
    } else {
      alert('출고완료 처리에 실패했습니다.');
    }
  })
  .catch(error => {
    console.error('오류 발생:', error);
    alert('서버 요청 중 오류가 발생했습니다.');
  });
}
*/

//특정창고정보만 보기 select태그선택
function fsWhCh(){
	searchWH();
	
}
function searchWH(){
	var form = document.querySelector("#frm");
	form.submit();
	
}