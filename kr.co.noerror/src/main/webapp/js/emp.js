//emp_list_modal에서 선택한 사원정보를 부모 페이지에 넣
function emp_select() {
  // 선택된 라디오 버튼 찾기
  const selectedRadio = document.querySelector('input[name="emp_check"]:checked');
  
  if (!selectedRadio) {
    alert("사원을 선택하세요.");
    return;
  }
  
  // 선택된 라디오 버튼의 부모 <tr> 찾기
  const selectedRow = selectedRadio.closest('tr');
  
  // data-* 속성 가져오기
  const ecode = selectedRow.getAttribute('data-ecode');
  const ename = selectedRow.getAttribute('data-ename');

  // 각 input 요소에 값 설정
  // 부모 페이지에서 emp 정보 input들은 ecode, ename 순서이어야 함. 부모페이지의 해당역역 class="empinfo" 포함되어야 함
  const inputs_emp = window.parent.document.querySelectorAll('.empinfo input.form-control[readonly]');
  inputs_emp[0].value = ecode;
  inputs_emp[1].value = ename

  // 모달 닫기
  const modalEl = document.getElementById('emps_modal');
  const modalInstance = bootstrap.Modal.getInstance(modalEl);
  if (modalInstance) {
    modalInstance.hide();
  }
}