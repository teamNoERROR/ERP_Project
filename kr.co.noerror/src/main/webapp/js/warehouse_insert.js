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