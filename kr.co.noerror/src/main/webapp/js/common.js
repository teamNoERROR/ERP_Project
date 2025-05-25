/*--------------------------------------------------------------
  Toggle 버튼 클릭시
--------------------------------------------------------------*/
function toggleButton(type) {
	const buttons = [
		{ id: 'product-list', color: '#82CCDD' },
		{ id: 'item-list', color: '#82CCDD' },
		{ id: 'consum-list', color: '#82CCDD' }
	];

	// 버튼 스타일 초기화 및 선택한 버튼 강조
	buttons.forEach((btn, index) => {
		const el = document.getElementById(btn.id);
		if (index + 1 === type) {
			el.style.background = btn.color;
			el.style.color = '#fff';
		} else {
			el.style.background = 'transparent';
			el.style.color = '#000';
		}
	});

	// 리스트 로딩
	loadList(type);
}

function toggleButton2(type) {
	const buttons = [
		{ id: 'order-list', color: '#82CCDD' },
		{ id: 'product-list', color: '#82CCDD' },
	];

	// 버튼 스타일 초기화 및 선택한 버튼 강조
	buttons.forEach((btn, index) => {
		const el = document.getElementById(btn.id);
		if (index + 1 === type) {
			el.style.background = btn.color;
			el.style.color = '#fff';
		} else {
			el.style.background = 'transparent';
			el.style.color = '#000';
		}
	});

	// 리스트 로딩
	loadList(type);
}



/*--------------------------------------------------------------
  이미지 첨부시 미리보기
--------------------------------------------------------------*/
function previewFile() {
  const fileInput = document.getElementById('productImage');
  const preview = document.getElementById('previewImage');
  const fileNameDisplay = document.getElementById('fileName');

  const file = fileInput.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function(e) {
      preview.src = e.target.result;
    };
    reader.readAsDataURL(file);

    // 파일명 표시
    fileNameDisplay.textContent = file.name;
  } 
}




