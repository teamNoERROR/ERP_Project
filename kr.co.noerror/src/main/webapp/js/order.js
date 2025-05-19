function addProductRow() {
	const tbody = document.getElementById('products');
	const row = document.createElement('tr');
	row.innerHTML = `
	<td style="text-align: center; vertical-align: middle;">
									<button
										class="btn btn-primary d-flex justify-content-center align-items-center"
										type="button" style="width: 40px; height: 40px;">
										<i class="bi bi-search"></i>
									</button>
								</td>
								<td><input type="text" class="form-control"
									placeholder="제품 코드" readonly></td>
								<td><input type="text" class="form-control"
									placeholder="제품명" readonly></td>
								<td><input type="text" class="form-control"
									placeholder="대분류" readonly></td>
								<td><input type="text" class="form-control"
									placeholder="소분류" readonly></td>
								<td><input type="number" class="form-control" min="1"
									placeholder="규격" readonly></td>
								<td><input type="number" class="form-control" min="1"
									placeholder="주문수량"></td>
								<td><select class="form-select">
										<option>ea</option>
										<option>g</option>
										<option>ml</option>
								</select></td>
								<td><input type="text" class="form-control"
									placeholder="제품단가" readonly></td>
								<td><input type="text" class="form-control"
									placeholder="판매가" readonly></td>
								<td class="text-center">
									<button type="button" class="btn btn-danger btn-sm"
										onclick="removeProductRow(this)">삭제</button>
								</td>
	    `;
	tbody.appendChild(row);
}


function removeProductRow(btn) {
	const row = btn.closest('tr');
	row.parentNode.removeChild(row);
}