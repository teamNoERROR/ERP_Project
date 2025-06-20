/*--------------------------------------------------------------
  창고리스트에서 선택후 인풋란에 붙여넣기 
----------------------------------------------------------- */
let targetWhNameId = null;
let targetWhCodeId = null;

let selectedMoveData = [];  // 체크된 항목들 저장

function saveSelectedProducts() {
  const checked = document.querySelectorAll('.move-checkbox:checked');
  selectedMoveData = Array.from(checked).map(cb => ({
    wh_code: cb.dataset.wh_code.trim(),
    pd_name: cb.dataset.pd_name,
  }));
}


//타입별창고리스트 모달 열기
function whSelect(wh_type, name_id, code_id){
	targetWhNameId = name_id;
	targetWhCodeId = code_id;
	
	// 체크된 항목 저장
	saveSelectedProducts();
	
	fetch("./wh_type_list.do?wh_type="+wh_type, {
			method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		
		var modal= new bootstrap.Modal(document.getElementById("wh_type_list"));
		modal.show();
		
	}).catch(function(error) {
		
		console.log("통신오류발생" + error);
	});
} 


//창고 선택
function choiceWh() {
	var radios = document.querySelectorAll('input[name="iosfSelect"]');
	var selected_radio = null;
	
	for (var i = 0; i < radios.length; i++) {
	  if (radios[i].checked) {
	    selected_radio = radios[i];
	  }
	}
	
	if (!selected_radio) {
		alert("창고를 선택해 주세요.");
	 }
	 else{
		const whCode = selected_radio.value.trim();
		const whName = selected_radio.getAttribute('data-wh_name');
		const whZipcode = selected_radio.getAttribute('data-wh_zipcode');
		const whAddr1 = selected_radio.getAttribute('data-wh_addr1');
		const whAddr2 = selected_radio.getAttribute('data-wh_addr2');
		const whType = selected_radio.getAttribute('data-wh_type');  // ← 여기서 타입 읽기
		
		const whNameInput = document.getElementById(targetWhNameId);
		const whCodeInput = document.getElementById(targetWhCodeId);
		const whAddrInput = document.getElementById('wh_location');
		const whTypeInput = document.getElementById('wh_type');
		
		//선택된 창고가 이미 체크한 제품의 현재 창고인지 확인
		const duplicated = selectedMoveData.find(item => item.wh_code === whCode);
		if (duplicated) {
		  alert(`선택한 창고는 이미 ${duplicated.pd_name} 제품의 현재 창고입니다.\n다른 창고를 선택해주세요.`);
		  return;
		}
		
		if (whNameInput && whCodeInput) {
			whNameInput.value = whName;
			whCodeInput.value = whCode;
		} 
		if (whAddrInput) {
			whAddrInput.value = whZipcode +" "+ whAddr1 +" "+ whAddr2;
		}
		if (whTypeInput) {
			whTypeInput.value = whType;
		}
		
		// 예시: 타입별 처리 (원하면 타입별로 다르게 로직 분기 가능)
	   /*if (whType === '부자재창고') {
		   document.getElementById('wh_code').value = whCode;
	   		alert("창고를 선택하셨습니다.");
	  
	   } else if (whType === '완제품창고') {
		  document.getElementById('wh_code').value = whCode;
		   	alert("창고를 선택하셨습니다.");
	   } else {
	     console.log('기타 창고 타입');
	   }
		*/
	   
		// 선택 후 모달 닫기
	 	var modalElement = document.getElementById("wh_type_list");
		var modal = bootstrap.Modal.getInstance(modalElement);
		if (modal) {
		    modal.hide();
			setTimeout(() => {
				document.querySelector("body").focus(); // body에 포커스 주기
			}, 300);
		}
	}
};

/*--------------------------------------------------------------
부자재리스트 모달에서 부자재 한번에 선택하기
--------------------------------------------------------------*/ 
function select_items (btn) {
	const parentType = btn.getAttribute('data-parenttype');
	
	if(parentType == 'bomPage') {
	  // 모든 체크된 체크박스를 찾음
	  var selected_box = document.querySelectorAll('input[name="select"]:checked');
	
	  if (selected_box.length == 0) {
		alert("제품을 1개 이상 선택해 주세요.");
		
	  }else{
		  // 부모 테이블 tbody
		  var tbody = document.querySelector('#bom_items');
		
		  //부모 테이블의 기존 행 전체 삭제
		  document.querySelectorAll('tr.item_add_row').forEach(tr => tr.remove());
	
		  	  selected_box.forEach(checkbox => {
			
		    var row = checkbox.closest('tr');
		
		    var item = {
		      code: row.dataset.code,
		      name: row.dataset.name,
		      class1: row.dataset.class1,
		      class2: row.dataset.class2,
		      spec: row.dataset.spec,
			  cost: row.dataset.cost,
		      pcomp: row.dataset.company
		    };
			
		    // 부모 화면에 반영
			appendItemsRow(tbody, item);
			
			document.querySelector("#bom_tr").innerHTML+=`
				<li class="bomlow"> ${item.name} </li>
			`;
		  });
		
		 // 모달 닫기
	  	var modalElement = document.getElementById("items_list");
	 	var modal = bootstrap.Modal.getInstance(modalElement);
		if (modal) {
		    modal.hide();
			setTimeout(() => {
				document.querySelector("body").focus(); // body에 포커스 주기
			}, 300);
		}
	  }
	}
	else if(parentType == 'pchreqPage'){
		// 모든 체크된 체크박스를 찾음
		  var selected_box = document.querySelectorAll('input[name="select"]:checked');

		  if (selected_box.length == 0) {
			alert("제품을 1개 이상 선택해 주세요.");
			
		  }else{
			  // 부모 테이블 tbody
			  var tbody = document.querySelector('#pch_items');
			
			  //부모 테이블의 기존 행 전체 삭제
			  document.querySelectorAll('tr.item_add_row').forEach(tr => tr.remove());

			  selected_box.forEach(checkbox => {
				
			    var row = checkbox.closest('tr');
			
			    var item = {
			      code: row.dataset.code,
			      name: row.dataset.name,
				  spec: row.dataset.spec,
			      unit: row.dataset.unit,
				  cost: row.dataset.cost,
			      pcomp: row.dataset.company
			    };
				
			    // 부모 화면에 반영
				appendItemsRow2(tbody, item);
			  });
			
			 // 모달 닫기
		  	var modalElement = document.getElementById("items_list");
		 	var modal = bootstrap.Modal.getInstance(modalElement);
			if (modal) {
			    modal.hide();
				setTimeout(() => {
					document.querySelector("body").focus(); // body에 포커스 주기
				}, 300);
			}
		  }
	}
	
	else if (typeof parentType !== 'undefined' && parentType === 'wh'){
		const selected_box = document.querySelectorAll('input[name="select"]:checked');
		
		console.log("함수 작동 1");
		if (selected_box.length !== 1) {
	      alert("입고하실 부자재를 1개 선택해 주세요.");
	      return;
	    }
		
	    const selected_tr = selected_box[0].closest("tr");

	    const itemCode = selected_tr.dataset.code;
	    const itemName = selected_tr.dataset.name;
	    const class1 = selected_tr.dataset.class1;
	    const class2 = selected_tr.dataset.class2;
	    const clientCode = selected_tr.dataset.company;
	    const clientName = selected_tr.dataset.company_name;
	    const clientFlag = selected_tr.dataset.company_use_flag;

	    document.querySelector('input[name="item_code"]').value = itemCode;
	    document.querySelector('input[name="item_name"]').value = itemName;
	    document.querySelector('input[name="category_main"]').value = class1;
	    document.querySelector('input[name="category_sub"]').value = class2;
	    document.querySelector('input[name="client_code"]').value = clientCode;
	    document.querySelector('select[name="clientUseYn"]').value = clientFlag;
	    document.querySelector('input[name="client_code"]').closest('.row').querySelectorAll('input')[1].value = clientName;

	    // 모달 닫기
	    const modalElement = document.getElementById("items_list");
	    const modal = bootstrap.Modal.getInstance(modalElement);
	    if (modal) {
	      modal.hide();
	      setTimeout(() => {
	        document.querySelector("body").focus();
	        document.querySelectorAll('.modal-backdrop').forEach(e => e.remove());
	        document.body.classList.remove('modal-open');
	        document.body.style.overflow = '';
	        document.body.style.paddingRight = '';
	      }, 300);
	    }

	    return; 
	  }
	else if (typeof parentType !== 'undefined' && parentType != 'wh_inb') {
	  	  if (selected_box.length == 0) {
	  	    alert("제품을 1개 이상 선택해 주세요.");
	  	  } else {
	  	    var tbody = document.querySelector('#bom_items');
	  	    document.querySelectorAll('tr.item_add_row').forEach(tr => tr.remove());
	  	
	  	    selected_box.forEach(checkbox => {
	  	      var row = checkbox.closest('tr');
	  	
	  	      var item = {
	  	        code: row.dataset.code,
	  	        name: row.dataset.name,
	  	        class1: row.dataset.class1,
	  	        class2: row.dataset.class2,
	  	        spec: row.dataset.spec,
	  	        cost: row.dataset.cost,
	  	        pcomp: row.dataset.company
	  	      };
	  	
	  	      // append 전에 null 체크
	  	      if (tbody) {
	  	        appendItemsRow(tbody, item);
	  	      }
	  	
	  	      document.querySelector("#bom_tr").innerHTML += `<li> ${item.name} </li>`;
	  	    });
	  	
	  	    // 모달 닫기 + backdrop 제거
	  	    var modalElement = document.getElementById("items_list");
	  	    var modal = bootstrap.Modal.getInstance(modalElement);
	  	    if (modal) {
	  	      modal.hide();
	  	      setTimeout(() => {
	  	        document.querySelector("body").focus();
	  	        document.querySelectorAll('.modal-backdrop').forEach(e => e.remove());
	  	        document.body.classList.remove('modal-open');
	  	        document.body.style.overflow = '';
	  	        document.body.style.paddingRight = '';
	  	      }, 300);
	  	    }
	  	  }
	  	}	
	
};


//부자재목록 행 삭제
function removeItemRow(btn) {
	const row = btn.closest('tr');
	row.parentNode.removeChild(row);
}

/*--------------------------------------------------------------
부자재 리스트 모달에서 선택한 항목들 등록화면의 리스트에 붙여넣기 
--------------------------------------------------------------*/
//모달에서 선택한 부자재 리스트 등록화면(bom_insert)의 리스트에 붙여넣기 
function appendItemsRow(tbody, item) {
  const tr = document.createElement('tr');
  tr.className = "item_added"
  tr.innerHTML = `
    <td><input type="text" class="form-control item_code" value="${item.code}" readonly></td>
    <td><input type="text" class="form-control" value="${item.name}" readonly></td>
    <td><input type="text" class="form-control" value="${item.class1}" readonly></td>
    <td><input type="text" class="form-control" value="${item.class2}" readonly></td>
    <td><input type="text" class="form-control" value="${item.spec}" readonly></td>
    <td><input type="text" class="form-control text-end" value="${item.cost}" readonly></td>
    <td><input type="text" class="form-control" value="${item.pcomp}" readonly></td>
	<td><input type="number" class="form-control item_qty"  value="" min="1"></td>
	<td>
		<select class="form-select select_unit">
			<option value="" >선택</option>
			<option value="ea" data-itemunit="ea">ea</option>
			<option value="g" data-itemunit="g">g</option>
			<option value="ml" data-itemunit="ml">ml</option>
		</select>
	</td>
    <td class="text-center">
      <button type="button" class="btn btn-danger btn-sm" onclick="removeItemRow(this)">삭제</button>
    </td>
  `;
  tbody.append(tr);
}


//모달에서 선택한 리스트 등록화면(pchreq_insert)의 리스트에 붙여넣기 
function appendItemsRow2(tbody, item) {
  const tr = document.createElement('tr');
  tr.className = "item_added"
  tr.innerHTML = `
    <td><input type="checkbox"></td>
    <td><input type="text" class="form-control item_code" value="${item.code}" readonly></td>
    <td><input type="text" class="form-control" value="${item.name}" readonly></td>
    <td><input type="text" class="form-control" value="${item.spec}" readonly></td>
    <td><input type="text" class="form-control" value="${item.unit}" readonly></td>
    <td><input type="number" class="form-control" value=""></td>
    <td><input type="text" class="form-control text-end" value="${item.cost}" readonly></td>
    <td><input type="text" class="form-control" value="${item.pcomp}" readonly></td>
  `;
  tbody.append(tr);
}


/*--------------------------------------------------------------
완제품 리스트 모달에서 선택한 제품 등록화면의 리스트에 붙여넣기 
--------------------------------------------------------------*/
//BOM 등록화면에서 
function pdChoice(parentType) {
	console.log(parentType.getAttribute("data-parenttype"))
	var radios = document.querySelectorAll('input[name="selectpd"]');
	var selected_radio = null;
	var ptype = parentType.getAttribute("data-parenttype");
	
	for (var i = 0; i < radios.length; i++) {
	  if (radios[i].checked) {
	    selected_radio = radios[i];
	  }
	}
	if (!selected_radio) {
		alert("BOM을 등록할 제품을 선택해 주세요.");
		return;
	 }
	 if(ptype == "bomPdOpen"){
		
		var tr = selected_radio.closest('tr');
		var tdList = tr.querySelectorAll('td');
		
		var pd_cd = tr.getAttribute("data-code");
		var pd_nm = tr.getAttribute("data-name");
		var pd_tp = tdList[5].innerText.trim();
		var pd_cl1 = tr.getAttribute("data-class1");
		var pd_cl2 = tr.getAttribute("data-class2");
		var pd_sp = tr.getAttribute("data-spec");
		var pd_un = tr.getAttribute("data-unit");
		var pd_cst = tr.getAttribute("data-cost");
		var pd_prc = tr.getAttribute("data-price");
		var pd_mm = tr.getAttribute("data-memo");
		
		var product_code = document.querySelector('#product_code');
		var product_name = document.querySelector('#product_name');
		var product_type = document.querySelector('#product_type');
		var product_cls1 = document.querySelector('#product_cls1');
		var product_cls2 = document.querySelector('#product_cls2');
		var product_spec = document.querySelector('#product_spec');
		var product_unit = document.querySelector('#product_unit');
		var product_cst = document.querySelector('#product_cst');
		var product_prc = document.querySelector('#product_prc');
		var product_memo = document.querySelector('#product_memo');
		
		var fmt_cost = Number(pd_cst).toLocaleString();
		var fmt_cost2 = Number(pd_prc).toLocaleString();
		
		product_code.value= pd_cd;
		product_name.value= pd_nm;
		product_type.value= pd_tp;
		product_cls1.value= pd_cl1;
		product_cls2.value= pd_cl2;
		product_spec.value= pd_sp;
		product_unit.value= pd_un;
		product_cst.value = fmt_cost;
		product_prc.value = fmt_cost2;
		product_memo.value= pd_mm;
		
		var top_pd = product_name;
		document.querySelector("#bom_top_pd").innerHTML=`<i class="bi bi-caret-right-fill"></i>`+top_pd.value;
	}
	
	// 선택 후 모달 닫기
 	var modalElement = document.getElementById("product_list");
	var modal = bootstrap.Modal.getInstance(modalElement);
	if (modal) {
	    modal.hide();
		setTimeout(() => {
			document.querySelector("body").focus(); // body에 포커스 주기
		}, 300);
	}
}



/*--------------------------------------------------------------
거래처 리스트 모달에서 선택한 거래처 등록화면의 리스트에 붙여넣기 
--------------------------------------------------------------*/
function choiceClt(parentType) {
	var radios = document.querySelectorAll('input[name="selected_clt"]');
	var selected_radio = null;
	var ptype = parentType.getAttribute("data-parenttype");
	
	
	for (var i = 0; i < radios.length; i++) {
	  if (radios[i].checked) {
	    selected_radio = radios[i];
	  }
	}
	if (!selected_radio) {
		alert("거래처를 선택해 주세요.");
		return;
	 }
	 if(ptype == "orderPage"){
		var tr = selected_radio.closest('tr');
		var tdList = tr.querySelectorAll('td');
		
		var com_code =  tdList[2].innerText.trim();
		var com_name = tdList[3].innerText.trim();
		var bnum = tdList[6].innerText.trim();
		var mng_code = tr.getAttribute("data-mngcode").trim();
		var mng_name = tdList[7].innerText.trim();
		var phnum = tr.getAttribute("data-mngphn").trim();
		
		var company_code = document.querySelector('#company_code');
		var company_name = document.querySelector('#company_name');
		var biz_num = document.querySelector('#biz_num');
		var manager_code = document.querySelector('#manager_code');
		var manager_name = document.querySelector('#manager_name');
		var phone_num = document.querySelector('#phone_num');
		  
		company_code.value= com_code;
		company_name.value= com_name;
		biz_num.value= bnum;
		manager_code.value= mng_code;
		manager_name.value= mng_name;
		phone_num.value= phnum;
	}
	
	// 선택 후 모달 닫기
 	var modalElement = document.getElementById("client_list");
	var modal = bootstrap.Modal.getInstance(modalElement);
	if (modal) {
	    modal.hide();
		setTimeout(() => {
			document.querySelector("body").focus(); // body에 포커스 주기
		}, 300);
	}
}

/*--------------------------------------------------------------
모달 주문리스트에서 선택후 주문 정보 인풋란에 붙여넣기  
--------------------------------------------------------------*/
function choiceOrd(parentType) {
	var radios = document.querySelectorAll('input[name="choice_ord"]');
	var selected_radio = null;
	var ptype = parentType.getAttribute("data-parenttype");
	
	for (var i = 0; i < radios.length; i++) {
	  if (radios[i].checked) {
	    selected_radio = radios[i];
	  }
	}
	if (!selected_radio) {
		alert("주문내역을 선택해 주세요.");
		return;
	 }
	 
	 //생산등록일 경우 
	 if(ptype == "pdcPlnPage"){
		var tr = selected_radio.closest('tr');
		var tdList = tr.querySelectorAll('td');
		
		var orderCode =  tdList[2].innerText.trim();
		var companyCode =  tdList[3].innerText.trim();
		var companyName =  tdList[4].innerText.trim();
		var managerName =  tdList[5].innerText.trim();
		var dueDate =  tdList[7].innerText.trim();
		
		//production_plan_insert.html에 붙는 부분
		var order_code = document.querySelector('#order_code');
		var company_code = document.querySelector('#company_code');
		var company_name = document.querySelector('#company_name');
		var manager_name = document.querySelector('#manager_name');
		var due_date = document.querySelector('#due_date');
		  
		ordDtlLoad(orderCode);   //발주목록 테이블에 붙여넣기
		
		order_code.value= orderCode;
		company_code.value= companyCode;
		company_name.value= companyName;
		manager_name.value= managerName;
		due_date.value= dueDate;
	
	}
	//출고등록일 경우 
	else if(ptype == "outbndPage"){
 		var tr = selected_radio.closest('tr');
 		var tdList = tr.querySelectorAll('td');
 		
		var orderCode =  tdList[2].innerText.trim();
		var companyCode =  tdList[3].innerText.trim();
		var companyName =  tdList[4].innerText.trim();
		var mngName =  tdList[5].innerText.trim();
		
		var ord_code = document.querySelector('#ord_code');
		var comp_name = document.querySelector('#comp_name');
		var comp_code = document.querySelector('#comp_code');
		var mng_name = document.querySelector('#mng_name');
		
		ordDtlLoad2(orderCode);   //발주목록 테이블에 붙여넣기
				
		ord_code.value= orderCode;
		comp_name.value= companyCode;
		comp_code.value= companyName;
		mng_name.value= mngName;
 	}
	
	// 선택 후 모달 닫기
 	var modalElement = document.getElementById("ordreq_list");
	var modal = bootstrap.Modal.getInstance(modalElement);
	if (modal) {
	    modal.hide();
		setTimeout(() => {
			document.querySelector("body").focus(); // body에 포커스 주기
		}, 300);
	}
};



/*--------------------------------------------------------------
주문받은 완제품 리스트 로드 
--------------------------------------------------------------*/
//생산등록페이지에서 
function ordDtlLoad(order_code){
	fetch("./ordreq_products.do?code="+order_code, {
		method: "GET",
		
	}).then(function(data) {
		return data.json();

	}).then(function(result) {
		var tbody = document.querySelector('#plan_detail_products');
		
		//기존 행 전체 삭제
		document.querySelectorAll('tr.product_row').forEach(tr => tr.remove());
		
		//결과값 붙이기 
		result.forEach((resultProduct, index) => {
				
			var tr = document.createElement('tr');
		  	tr.className = "product_row " 
			tr.innerHTML = `
			  <td>${index + 1}</td>
			  <td class="product_code">${resultProduct.product_code}</td>
			  <td>${resultProduct.product_name}</td>
			  <td>${resultProduct.product_spec}</td>
			  <td>${resultProduct.product_unit}</td>
			  <td>${resultProduct.ind_pd_all_stock} (/${resultProduct.pd_safe_stock})</td>
			  <td>
			    <input type="number" class="form-control plan_qty" value="${resultProduct.product_qty}">
			  </td>
			  <td class="bom_code">${resultProduct.bom_code}</td>
			  <td>
			    <button type="button" class="btn btn-sm btn-danger" onclick="removeItemRow(this)">
			      삭제
			    </button>
			  </td>
			  `;
		  	tbody.append(tr);
		});  
		  
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}

//출고등록 페이지에서 
function ordDtlLoad2(order_code){
	
	fetch("./ordreq_products.do?code="+order_code, {
		method: "GET",
		
	}).then(function(data) {
		return data.json();

	}).then(function(result) {
		var tbody = document.querySelector('#outbnd_pds');
		var due;
		
		//기존 행 전체 삭제
		document.querySelectorAll('tr.pd_row').forEach(tr => tr.remove());
		
		//결과값 붙이기 
		result.forEach((resultProduct, index) => {
			var tr = document.createElement('tr');
		  	tr.className = "pd_row " 
			tr.innerHTML = `
				<td>`+(index+1)+`</td>
			    <td class="out_pd_code">`+resultProduct.product_code+`</td>
			    <td>`+resultProduct.product_name+`</td>
			    <td>`+resultProduct.product_spec+`</td>
			    <td>`+resultProduct.product_unit+`</td>
			    <td class="text-end">`+resultProduct.product_price.toLocaleString()+`</td>
				<td class="text_red ord_pd_qty">`+resultProduct.product_qty.toLocaleString()+`</td>
			    <td class="all_pd_stock">`+resultProduct.ind_pd_all_stock.toLocaleString()+`</td>
				<td><input type="number" min="1" class="form-control out_pd_qty"></td>
			  `;
		  	tbody.append(tr);
	
			due = resultProduct.due_date.split(" ")[0];
		});  
		
		document.querySelector("#out_req_date").value = due;
		  
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
발주리스트모달 
--------------------------------------------------------------*/
//발주리스트모달에서 선택후 인풋란에 붙여넣기 
function choicePch() {
	var radios = document.querySelectorAll('input[name="choice_pch"]');
	var selected_radio = null;
		
	for (var i = 0; i < radios.length; i++) {
	  if (radios[i].checked) {
	    selected_radio = radios[i];
	  }
	}
	if (!selected_radio) {
		alert("발주내역을 선택해 주세요.");

	 }else{
		var tr = selected_radio.closest('tr');
		var tdList = tr.querySelectorAll('td');
		var pchcode =  tdList[2].innerText.trim();
		var cmp_name = tdList[4].innerText.trim();
		var cmp_code = tr.getAttribute("data-com_code");
		
		//inbnd_insert.html에 붙는 부분
		var pch_code = document.querySelector('#pch_code');
		var comp_name = document.querySelector('#comp_name');
		var comp_code = document.querySelector('#comp_code');
		  
		pchDtlLoad(pchcode);   //발주목록 테이블에 붙여넣기
		
		pch_code.value= pchcode;
		comp_name.value= cmp_name;
		comp_code.value= cmp_code;
		
	
		
		// 선택 후 모달 닫기
	 	var modalElement = document.getElementById("purchase_list");
		var modal = bootstrap.Modal.getInstance(modalElement);
		if (modal) {
		    modal.hide();
			setTimeout(() => {
				document.querySelector("body").focus(); // body에 포커스 주기
			}, 300);
		}
	}
};

//발주한 부자재 리스트 로드 
function pchDtlLoad(pch_code){
	fetch("./purchase_detail_modal.do?code="+pch_code, {
		method: "GET",
		
	}).then(function(data) {
		return data.json();

	}).then(function(result) {
		var tbody = document.querySelector('#inbnd_items');
		
		//기존 행 전체 삭제
		document.querySelectorAll('tr.item_row').forEach(tr => tr.remove());
		
		//결과값 붙이기 
		result.forEach((resultItem, index) => {
			
			var tr = document.createElement('tr');
		  	tr.className = "item_row " 
		  	tr.innerHTML = `
			    <td>`+(index+1)+`</td>
			    <td class="item_code">`+resultItem.item_code+`</td>
			    <td>`+resultItem.item_name+`</td>
			    <td>`+resultItem.item_spec+`</td>
			    <td>`+resultItem.item_qty+`</td>
			    <td>`+resultItem.item_unit+`</td>
			    <td>`+resultItem.item_class1+`</td>
				<td>`+resultItem.item_class2+`</td>
				<td class="text-end">`+resultItem.item_cost+`</td>
				<td><input type="number" class="form-control item_qty" min="1"></td>
				<td>
					<select class="form-select item_deli" aria-label="Default select example" onchange='noDeli(this)'>
						<option value="납품완료" >납품완료</option>
					</select>
				</td>
				<td><input type="date" class="form-control item_exp"></td>
			  `;
		  	tbody.append(tr);
			
			// tr 안에서 item_exp를 찾아서 오늘 날짜 설정
			/*let expInput = tr.querySelector(".item_exp");
			expInput.valueAsDate = new Date(); // 오늘 날짜로 세팅*/
		});  
		  
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}

/*--------------------------------------------------------------
	사원 리스트모달 
--------------------------------------------------------------*/
//발주리스트모달에서 선택후 인풋란에 붙여넣기 
function choiceEmp(parent) {
	console.log(parent.getAttribute("data-parenttype"));
	var prt = parent.getAttribute("data-parenttype");
	var radios = document.querySelectorAll('input[name="selectMem"]');
	var selected_radio = null;
		
	for (var i = 0; i < radios.length; i++) {
	  if (radios[i].checked) {
	    selected_radio = radios[i];
	  }
	}
	
	if (!selected_radio) {
		alert("직원을 선택해 주세요.");
		return;
	}
	 
	var tr = selected_radio.closest('tr');
	var tdList = tr.querySelectorAll('td');
	
	var emp_code =  tdList[2].innerText.trim();
	var emp_name = tdList[3].innerText.trim();
	var emp_part = tdList[4].innerText.trim();
	var emp_position = tdList[5].innerText.trim();
	var emp_tel = tdList[6].innerText.trim();
	
	//창고등록시 직원 선택
	if(prt == "wh_emp"){
		var wh_mg_cd = document.querySelector('#wh_mg_cd');
		var wh_mg_name = document.querySelector('#wh_mg_name');
		var wh_mg_mp = document.querySelector('#wh_mg_mp');
		var wh_mg_ph = document.querySelector('#wh_mg_ph');
		  
		wh_mg_cd.value= emp_code;
		wh_mg_name.value= emp_name;
		wh_mg_mp.value= emp_part + "/" + emp_position;
		wh_mg_ph.value= emp_tel;
	}
	//생산계획(재고)등록시 직원 선택 
	else if(prt == "stk_pln_emp"){
		var pln_mg_cd = document.querySelector('#pln_mg_cd');
		var pln_mg_name = document.querySelector('#pln_mg_name');
		
		pln_mg_cd.value= emp_code;
		pln_mg_name.value= emp_name;
	}
	
	// 선택 후 모달 닫기
 	var modalElement = document.getElementById("member_list_modal");
	var modal = bootstrap.Modal.getInstance(modalElement);
	if (modal) {
	    modal.hide();
		setTimeout(() => {
			document.querySelector("body").focus(); // body에 포커스 주기
		}, 300);
	}
};
