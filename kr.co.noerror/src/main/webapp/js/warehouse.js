
/*
document.addEventListener('DOMContentLoaded', function() {
  // 모든 modal-trigger 클래스 tr에 클릭 이벤트 붙이기
  document.querySelectorAll('.modal-trigger').forEach(function(row) {
    row.addEventListener('click', function(e) {
      e.preventDefault();

      const url = this.dataset.modalUrl;  // data-modal-url 속성 가져오기
      if (!url) return;

      // Fetch API로 모달 내용 받아오기
      fetch(url)
        .then(response => {
          if (!response.ok) throw new Error('네트워크 오류');
          return response.text();
        })
        .then(html => {
          // 모달 컨테이너에 html 삽입
          const modalContainer = document.getElementById('modalContainer');
          modalContainer.innerHTML = html;

          // Bootstrap 모달 객체 생성 및 띄우기
          const modalElement = document.getElementById('wh_modal');
          const modal = new bootstrap.Modal(modalElement);
          modal.show();

          // 모달 닫을 때 컨테이너 비우기
          modalElement.addEventListener('hidden.bs.modal', () => {
            modalContainer.innerHTML = '';
          }, { once: true });
        })
        .catch(err => {
          alert('모달 데이터를 불러오는데 실패했습니다.');
          console.error(err);
        });
    });
  });
});
*/
// ********************************************* 창고 저장 js ********************************************
//창고 값 검증
function wh_save() {
  const form = document.getElementById('frm');

  // 창고유형
  const whType = form.querySelector('[name="wh_type"]').value;
  if (!whType) {
    alert("창고유형을 선택해주세요.");
    form.querySelector('[name="wh_type"]').focus();
    return;
  }

  // 창고명
  const whName = form.querySelector('[name="wh_name"]').value.trim();
  if (!whName) {
    alert("창고명을 입력해주세요.");
    form.querySelector('[name="wh_name"]').focus();
    return;
  }

  // 이미지
  const imageInput = form.querySelector('#productImage');
  const image = imageInput.files[0];

  if (!image) {
    alert("썸네일 이미지를 업로드하세요.");
    imageInput.focus();
    return;
  }

  if (image.size > 2 * 1024 * 1024) {
    alert("이미지 크기는 2MB 이하로 업로드해주세요.");
    imageInput.focus();
    return;
  }
  
  //전화번호
  const whNumberInput = form.querySelector('[name="wh_number"]');
  let whNumber = whNumberInput.value.trim().replace(/-/g, ''); // 하이픈 제거

  if (!/^\d{7,13}$/.test(whNumber)) {
    alert("전화번호는 숫자만 7~13자리로 입력해주세요.");
    whNumberInput.focus();
    return;
  }


  // 사용여부
  const whUseFlag = form.querySelector('input[name="wh_use_flag"]:checked');
  if (!whUseFlag) {
    alert("사용 여부를 선택해주세요.");
    form.querySelector('input[name="wh_use_flag"]').focus();
    return;
  }

  // 창고 주소 - 도로명주소 필수
  const whAddr1 = form.querySelector('[name="wh_addr1"]').value.trim();
  if (!whAddr1) {
    alert("도로명 주소를 입력해주세요.");
    form.querySelector('[name="wh_addr1"]').focus();
    return;
  }

  // 창고 주소 - 상세주소 필수
  const whAddr2 = form.querySelector('[name="wh_addr2"]').value.trim();
  if (!whAddr2) {
    alert("상세주소를 입력해주세요.");
    form.querySelector('[name="wh_addr2"]').focus();
    return;
  }

  // 우편번호 - 숫자 5자리
  const whZip = form.querySelector('[name="wh_zipcode"]').value.trim();
  if (!/^\d{5}$/.test(whZip)) {
    alert("우편번호는 5자리 숫자로 입력해주세요.");
    form.querySelector('[name="wh_zipcode"]').focus();
    return;
  }

  // 관리자명
  const mgName = form.querySelector('[name="wh_mg_name"]').value.trim();
  if (!mgName) {
    alert("창고 관리자명을 입력해주세요.");
    form.querySelector('[name="wh_mg_name"]').focus();
    return;
  }

  // 사원번호 (영문+숫자, 4~10자리)
  const mgId = form.querySelector('[name="wh_mg_id"]').value.trim();
  if (!/^[a-zA-Z0-9]{4,10}$/.test(mgId)) {
    alert("사원번호는 영문+숫자 조합으로 4~10자리로 입력해주세요.");
    form.querySelector('[name="wh_mg_id"]').focus();
    return;
  }

  // 부서/직급
  const mgMp = form.querySelector('[name="wh_mg_mp"]').value.trim();
  if (!mgMp) {
    alert("부서/직급을 입력해주세요.");
    form.querySelector('[name="wh_mg_mp"]').focus();
    return;
  }

   
  // 연락처 (휴대폰 형식: 0102223344)
  const mgPh = form.querySelector('[name="wh_mg_ph"]').value.trim();
  if (!/^\d{7,13}$/.test(mgPh)) {
    alert("사원 연락처는 숫자만 7~13자리로 입력해주세요.");
    form.querySelector('[name="wh_mg_ph"]').focus();
    return;
  }

  // 설명 (선택사항 - 길이 제한)
  const desc = form.querySelector('[name="wh_desc"]').value.trim();
  if (desc.length > 500) {
    alert("설명은 500자 이내로 입력해주세요.");
    form.querySelector('[name="wh_desc"]').focus();
    return;
  }

  // 비고 (선택사항 - 길이 제한)
  const note = form.querySelector('[name="wh_note"]').value.trim();
  if (note.length > 300) {
    alert("비고는 300자 이내로 입력해주세요.");
    form.querySelector('[name="wh_note"]').focus();
    return;
  }

  // 모든 조건 통과 시 제출
  frm.submit();
  
  }
  
  
  
  function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
        
                
                } 

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('wh_zipcode').value = data.zonecode;
                document.getElementById("wh_addr2").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("wh_addr2").focus();
            }
        }).open();
    }