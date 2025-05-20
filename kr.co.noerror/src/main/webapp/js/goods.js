

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




let insertBtn = document.querySelector("#goodsInsert");
insertBtn.addEventListener("click", function(){
	location.href="./goods_insert.do";
});



