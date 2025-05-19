// 예시 부자재 상세 데이터
const bomDetails = {
	1: {
		name: "부자재 1",
		code: "MAT-001",
		spec: "규격 A",
		qty: "10",
		unit: "EA",
		remark: "비고 없음"
	},
	2: {
		name: "부자재 2",
		code: "MAT-002",
		spec: "규격 B",
		qty: "5",
		unit: "EA",
		remark: "특이사항 있음"
	},
	3: {
		name: "부자재 3",
		code: "MAT-003",
		spec: "규격 C",
		qty: "20",
		unit: "EA",
		remark: "맛없음 "
	}
};




// 트리 클릭 이벤트
document.querySelectorAll('#bomTree li[data-id]').forEach(li => {
	li.addEventListener('click', function(e) {
		e.stopPropagation();
		// 선택 효과
		document.querySelectorAll('#bomTree li').forEach(el => el.classList.remove('selected'));
		this.classList.add('selected');
		// 상세보기 표시
		

		const detail = bomDetails[this.dataset.id];
		if (detail) {
          document.getElementById('bomDetail2').innerHTML = `
              <h5 class="mb-3">${detail.name}</h5>
              <table class="table table-bordered">
                  <tr><th>코드</th><td>${detail.code}</td></tr>
                  <tr><th>규격</th><td>${detail.spec}</td></tr>
                  <tr><th>수량</th><td>${detail.qty} ${detail.unit}</td></tr>
                  <tr><th>비고</th><td>${detail.remark}</td></tr>
              </table>
          `;
      	}
	});
});









function addRow() {
	const tbody = document.getElementById('bomItems');
	const row = document.createElement('tr');
	row.innerHTML = `
			<td style="text-align: center; vertical-align: middle;">
				<button class="btn btn-primary d-flex justify-content-center align-items-center" type="button" style="width: 40px; height: 40px;">
		        <i class="bi bi-search"></i>
		    	</button>
			</td>
	        <td><input type="text" class="form-control" placeholder="자재 코드" readonly></td>
	        <td><input type="text" class="form-control" placeholder="자재명" readonly></td>
			<td><input type="number" class="form-control" min="1" placeholder="규격"></td>
	        <td><input type="number" class="form-control" min="1" placeholder="수량"></td>
	        <td>
	            <select class="form-select">
	                <option>ea</option>
	                <option>g</option>
	                <option>ml</option>
	            </select>
	        </td>
	        <td class="text-center">
	            <button type="button" class="btn btn-danger btn-sm" onclick="removeRow(this)">삭제</button>
	        </td>
	    `;
	tbody.appendChild(row);
}


function removeRow(btn) {
	const row = btn.closest('tr');
	row.parentNode.removeChild(row);
}
