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

function loadList(type) {
	const table = document.getElementById('table-container');
	const container = document.getElementById('list_container');

	// 기존 테이블 숨기기
	if (table) table.style.display = 'none';

	// 리스트 타입에 따라 다른 내용 표시 (더미 데이터 사용 예시)
	const dummyData = {
		1: ['완제품 A', '완제품 B', '완제품 C'],
		2: ['부자재 X', '부자재 Y'],
		3: ['소모품 1', '소모품 2', '소모품 3']
	};

	const titles = ['완제품', '부자재', '소모품'];
	const items = dummyData[type] || [];

	container.innerHTML = `
		<h4>${titles[type - 1]} 리스트</h4>
		<ul style="list-style: none; padding: 0;">
			${items.map(item => `<li style="padding: 5px 0;">${item}</li>`).join('')}
		</ul>
	`;
}





//품목 등록 이미지 미리보기 
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
  } else {
    preview.src = '';
    fileNameDisplay.textContent = '';
  }
}