//*******************************창고 상세보기에서 삭제*********************************** */
function deleteWarehouse(button) {
  const confirmed = confirm("정말 삭제하시겠습니까?");
  if (!confirmed) return;

  const url = button.getAttribute('data-modal-url');

  fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
  .then(response => {
    if (!response.ok) {
      throw new Error("삭제 요청 실패");
    }
    return response.text();
  })
  .then(result => {
    if (result.trim() === 'suceses') { // 컨트롤러에 맞춰서 "suceses"로 비교
      alert("삭제 성공");
      const modal = bootstrap.Modal.getInstance(button.closest('.modal'));
      if (modal) modal.hide();
      location.reload();
    } else {
      alert("삭제 실패: 서버 응답이 예상과 다릅니다.");
    }
  })
  .catch(error => {
    alert("삭제 중 오류 발생: " + error.message);
    console.error(error);
  });
}


 //*******************************관리자 모달 리스트 선택 후 반환 **************************************** */
 function selectEmp() {
    const selectedRadio = document.querySelector('input[name="empSelect"]:checked');
	
	 if (!selectedRadio) {
      alert('직원을 선택해주세요.');
      return;
    }

    const empCode = selectedRadio.value;
    const empName = selectedRadio.getAttribute('data-ename');
    const empPart = selectedRadio.getAttribute('data-epart');
    const empPosition = selectedRadio.getAttribute('data-eposition');
    const empPhone = selectedRadio.getAttribute('data-ephone');

    document.getElementById('wh_mg_id').value = empCode;
    document.getElementById('wh_mg_name').value = empName;
    document.getElementById('wh_mg_mp').value = empPart + ' / ' + empPosition;
    document.getElementById('wh_mg_ph').value = empPhone;

	alert("직원을 선택 하셨습니다.");
    // 모달 닫기
    const modalElement = document.getElementById('emp_list');
    const modal = bootstrap.Modal.getInstance(modalElement) || new bootstrap.Modal(modalElement);
    modal.hide();
  }

 
 //*******************************부자재 모달 리스트 선택 후 반환 **************************************** */
 function select_iosf_wh() {
   const selectedRadio = document.querySelector('input[name="iosfSelect"]:checked');

   if (!selectedRadio) {
     alert('창고를 선택해주세요.');
     return;
   }

   const whCode = selectedRadio.value;
   const whName = selectedRadio.getAttribute('data-wh_name');
   const whZipcode = selectedRadio.getAttribute('data-wh_zipcode');
   const whAddr1 = selectedRadio.getAttribute('data-wh_addr1');
   const whAddr2 = selectedRadio.getAttribute('data-wh_addr2');
   const whType = selectedRadio.getAttribute('data-wh_type');  

   // 예시: 타입별 처리 (원하면 타입별로 다르게 로직 분기 가능)
   if (whType === '부자재창고') {
	    document.getElementById('wh_code').value = whCode;
   		alert("창고를 선택하셨습니다.");
	    document.getElementById('wh_location').value = "("+ whZipcode + ")" + whAddr1 + " " + whAddr2;
		document.getElementById('wh_type').value = whType;
		document.getElementById('wh_name').value = whName;
  
   } else if (whType === '완제품창고') {
	    document.getElementById('wh_code').value = whCode;
		alert("창고를 선택하셨습니다.");
		document.getElementById('wh_location').value = "("+ whZipcode + ")" + whAddr1 + " " + whAddr2;
		document.getElementById('wh_type').value = whType;
        document.getElementById('wh_name').value = whName;
	   
		
   } else if (whType === '입고창고') {
	   document.getElementById('wh_code').value = whCode;
	    alert("창고를 선택하셨습니다.");
	   document.getElementById('wh_location').value = "("+ whZipcode + ")" + whAddr1 + " " + whAddr2;
	   document.getElementById('wh_type').value = whType;
	   document.getElementById('wh_name').value = whName;
	 
		
   } else if (whType === '출고창고') {
	    document.getElementById('wh_code').value = whCode;
		alert("창고를 선택하셨습니다.");
		document.getElementById('wh_location').value = "("+ whZipcode + ")" + whAddr1 + " " + whAddr2;
		document.getElementById('wh_type').value = whType;
		document.getElementById('wh_name').value = whName;
		 
	   }
   
   else {
     console.log('기타 창고 타입');
   }

   

   // 모달 닫기
   const modalElement = document.getElementById('wh_type_list'); // ← ID 확인하세요
   const modal = bootstrap.Modal.getInstance(modalElement) || new bootstrap.Modal(modalElement);
   modal.hide();
 }

//******************************창고 페이징************************* */ 
document.addEventListener('DOMContentLoaded', function () {
  const pageSizeSelect = document.getElementById('pageSizeSelect');
  if (!pageSizeSelect) return;

  pageSizeSelect.addEventListener('change', function () {
    const selectedPageSize = this.value;

    const currentPath = window.location.pathname;

    // 쿼리 파라미터 파싱
    const params = new URLSearchParams(window.location.search);
    const whSearch = params.get('wh_search') || '';

    // 쿼리 갱신
    params.set('page', '1');
    params.set('pageSize', selectedPageSize);

    const url = `${currentPath}?${params.toString()}`;
    window.location.href = url;
  });
});
 
 
 //******************************창고 모달 리스트 선택 후 반환 **************************************** */
 function selectWarehouse() {
    const selectedRadio = document.querySelector('input[name="whSelect"]:checked');

  if (!selectedRadio) {
      alert('창고를 선택해주세요.');
      return;
    }

    const whCode = selectedRadio.value;
    const whName = selectedRadio.getAttribute('data-wh_name');
    const whZipcode = selectedRadio.getAttribute('data-wh_zipcode');
    const whAddr1 = selectedRadio.getAttribute('data-wh_addr1');
    const whAddr2 = selectedRadio.getAttribute('data-wh_addr2');

	document.getElementById('wh_code').value = whCode;
    document.getElementById('wh_name').value = whName;
    document.getElementById('wh_location').value = '(' + whZipcode + ')' + ' ' + whAddr1 + ' ' + whAddr2;
	
 	alert("창고를 선택 하셨습니다.");
    // 모달 닫기
    const modalElement = document.getElementById('warehouse_list');
    const modal = bootstrap.Modal.getInstance(modalElement) || new bootstrap.Modal(modalElement);
    modal.hide();
  }


 //********************전체 창고 -> 창고 선택 탭***********************************************************
 function toggleWh(type) {
   	let url = "";
 	if (type == 'all') {
 	     url = "/warehouses_list.do";
 	}
	else if(type == 'in'){
 	     url = "/warehouses_in_list.do";		
	}
	else if(type == 'mt'){		
 	     url = "/warehouses_mt_list.do";		
	}
	else if(type == 'fs'){		
	 	     url = "/warehouses_fs_list.do";		
	}
	else if(type == 'out'){		
	 	     url = "/warehouses_out_list.do";			
	}		
		  
	location.href = url; //페이지 이동
 }


 //**************************************창고로 입고 정보 이동*************************************************
 
 function selectRow(event,checkbox) {
	 
	event.stopPropagation(); // 모달 열리는 <tr> 클릭 이벤트 차단
    
	 const inboundCode = checkbox.dataset.inbound_code;
     const itemCode = checkbox.dataset.item_code;
     const clientCode = checkbox.dataset.client_code;
     const whCode = checkbox.dataset.wh_code;
     const whType = checkbox.dataset.wh_type;

     const inStatusRaw = checkbox.dataset.in_status; 
     const inChange = checkbox.dataset.in_change;
     const itemCount = checkbox.dataset.item_count;

     // inStatusRaw가 없거나 빈 값이면 '입고완료'로 설정
     const inStatus = (inStatusRaw && inStatusRaw.trim() !== '') ? inStatusRaw : '입고완료';

     document.getElementById('inbound_code').value = inboundCode;
     document.getElementById('item_code').value = itemCode;
     document.getElementById('client_code').value = clientCode;
     document.getElementById('wh_type').value = whType;
     document.getElementById('in_status').value = inStatus;
     document.getElementById('item_count').value = itemCount;

     if (!checkbox.classList.contains('move-checkbox')) {
         document.getElementById('wh_code').value = whCode;
     }

     console.log("선택된 값:", inboundCode, itemCode, clientCode, whCode, inStatus, itemCount);
 }
 
//체크박스 형태로 여러개의 값 전송
// document.addEventListener('DOMContentLoaded', function () {
function whMove(){	
	
   const moveBtn = document.querySelector('.btn-info');
   if (!moveBtn) return;

   // 복제하여 기존 이벤트 제거
   const clonedBtn = moveBtn.cloneNode(true);
   moveBtn.replaceWith(clonedBtn); // 기존 버튼을 교체

   clonedBtn.addEventListener('click', function () {
     const checkedBoxes = document.querySelectorAll('.move-checkbox:checked');
     if (checkedBoxes.length === 0) {
       alert('이동할 항목을 선택해주세요.');
       return;
     }

     const moveData = [];
     checkedBoxes.forEach(box => {
       const wh_code = box.getAttribute('data-wh_code');
       const inbound_code = box.getAttribute('data-inbound_code');
       const item_code = box.getAttribute('data-item_code');
       const wh_type = box.getAttribute('data-wh_type');
       const in_status = box.getAttribute('data-in_status');
       const item_count = box.getAttribute('data-item_count');
	   
       if (wh_code && inbound_code && item_code && wh_type && item_count) {
         moveData.push({ wh_code, inbound_code, item_code ,wh_type, in_status, item_count});
       }
     });

     if (moveData.length === 0) {
       alert('선택한 항목의 데이터가 누락되었거나 유효하지 않습니다.');
       return;
     }

     fetch('/IOSF_warehouse_move.do', {
       method: 'POST',
       headers: { 'Content-Type': 'application/json' },
       body: JSON.stringify(moveData)
     })
     .then(res => {
       if (!res.ok) throw new Error('서버 오류');
       return res.json();
     })
	 .then(data => {
	   if (data.success) {
	    
		const wh_type = moveData[0].wh_type; // 첫 항목의 창고 타입 사용 (필요 시 공통성 체크)
		let wh_kind = "";

		if (wh_type == "in") {
		    wh_kind = "입고";
		} else if (wh_type == "mt") {
		    wh_kind = "부자재";
		} else if (wh_type == "fs") {
		    wh_kind = "완제품";
		} else if (wh_type == "out") {
		    wh_kind = "출고";
		}
	     if (confirm(`이동이 완료되었습니다. ${wh_kind} 리스트로 이동하시겠습니까?`)) {
	       location.href = `/warehouses_${wh_type}_list.do`;
	     } else {
	       alert("이동만 완료되고 현재 페이지에 머물렀습니다.");
	     }
	   } else {
	     alert("이동 처리 실패: " + (data.message || "알 수 없는 오류"));
	   }
	 })
     .catch(err => {
       console.error(err);
       alert("이동 중 오류가 발생했습니다.");
     });
   });
 }
 //});

// ********************************************* 창고 체크박스로 삭제 *****************************************
function deleteWh(wh_type) {
    const checkedBoxes = document.querySelectorAll('input[name="deleteCodes"]:checked');

    if (checkedBoxes.length === 0) {
        alert("삭제할 항목을 선택하세요.");
        return;
    }

    if (!confirm("선택한 항목을 삭제하시겠습니까?")) {
        return;
    }

    let url = '/warehouse_delete_page.do'; // 기본
    let deleteCount = 0;
    let failCount = 0;

    checkedBoxes.forEach(cb => {
        const wh_code = cb.value;
        const in_code = cb.getAttribute("data-in_code");
        const material_code = cb.getAttribute("data-material_code");
        const finish_code = cb.getAttribute("data-finish_code");
        const out_code = cb.getAttribute("data-out_code");


        const params = new URLSearchParams();

        if (wh_type === 'in') {
            url = '/in_warehouse_delete_page.do';
            params.append('in_code', in_code);
        }
		else if(wh_type === 'mt'){
			url = '/mt_warehouse_delete_page.do';
		    params.append('material_code', material_code);
		}
		else if(wh_type === 'fs'){
					url = '/fs_warehouse_delete_page.do';
				    params.append('finish_code', finish_code);
		}
		else if(wh_type === 'out'){
					url = '/out_warehouse_delete_page.do';
				    params.append('out_code', out_code);
		}   
		else {
            // 기타 유형 처리 (예: all, out 등)
            params.append('wh_code', wh_code);
        }

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params
        })
        .then(res => res.text())
        .then(result => {
            if (result === 'suceses') {
                deleteCount++;
            } else {
                failCount++;
            }

            if (deleteCount + failCount === checkedBoxes.length) {
                if (failCount > 0) {
                    alert(`${failCount}건 삭제 실패. 나머지는 성공.`);
                } else {
                    alert("모두 삭제 성공!");
                }
                location.reload();
            }
        })
        .catch(err => {
            failCount++;
            console.error(err);
        });
    });
}



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
  const imageInput = form.querySelector('#whImage');
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
  
  /*
  //전화번호
  const whNumberInput = form.querySelector('[name="wh_number"]');
  let whNumber = whNumberInput.value.trim().replace(/-/g, ''); // 하이픈 제거

  if (!/^\d{7,13}$/.test(whNumber)) {
    alert("전화번호는 숫자만 7~13자리로 입력해주세요.");
    whNumberInput.focus();
    return;
  }
	*/
	

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
  const mgName = form.querySelector('[name="wh_mg_name"]').value;
  if (!mgName) {
    alert("창고 관리자를 선택해주세요.");
    form.querySelector('[name="wh_mg_name"]').focus();
    return;
  }

  /*
  // 사원번호 (영문+숫자, 4~10자리)
  const mgId = form.querySelector('[name="wh_mg_id"]').value.trim();
  if (!/^[a-zA-Z0-9]{4,10}$/.test(mgId)) {
    alert("사원번호는 영문+숫자 조합으로 4~10자리로 입력해주세요.");
    form.querySelector('[name="wh_mg_id"]').focus();
    return;
  }
*/
	const mgId = form.querySelector('[name="wh_mg_id"]').value.trim();
  if (!/^[A-Z]{3}-\d{5}$/.test(mgId)) {
    alert("사원번호를 입력해주세요.");
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

   /*
  // 연락처 (휴대폰 형식: 0102223344)
  const mgPh = form.querySelector('[name="wh_mg_ph"]').value.trim();
  if (!/^01[0-9]-\d{3,4}-\d{4}$/.test(mgPh)) {
    alert("사원 연락처는 '010-1234-5678' 형식으로 입력해주세요.");
    form.querySelector('[name="wh_mg_ph"]').focus();
    return;
  }
  */
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
  form.submit();
  
  }
    
  //**********************************************다음 주소 api********************************************
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
                document.getElementById("wh_addr1").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("wh_addr2").focus();
            }
        }).open();
    }
	
