
function bomBtn(bom_open){
	var pd_code = bom_open.getAttribute("data-pdcode");
	
	fetch("./bom_check.do?pd_code="+pd_code, {
		method: "GET"
			
	}).then(function(data) {
		return data.text();
	
	}).then(function(result) {  
		if(result =="yes"){  //등록된 BOM 있음 
			
			var bom_open = new bootstrap.Modal(document.getElementById('bom_detail'));
			bom_open.show();
			bom_detail(pd_code);

				}else if(result =="no"){  //등록된 BOM없음 
			if(confirm("등록된 BOM 자료가 없습니다. \n지금 등록 하시겠습니까?")){
				location.href="./bom_insert.do?pd_code="+pd_code;
				
			}else {
				location.href="./goods.do";   //완제품 리스트로 이동 
			}
			
		}else {
			console.log(result);
		}
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}



function bomDetailOpen(bom_open){
	var pd_code = bom_open.getAttribute("data-pdcode");
	
	fetch("./bom_detail.do?pd_code="+pd_code, {
		method: "GET"
			
	}).then(function(data) {
		return data.text();
	
	}).then(function(result) {  
		document.getElementById("modalContainer").innerHTML = result;
			var bom_open = new bootstrap.Modal(document.getElementById('bom_detail'));
			bom_open.show();
			//bom_detail(pd_code);

		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}



function addBom(){
	location.href="./bom_insert.do";
}



function bomDelete(del_pd){
	var idx = 0;
	var pd_code;
	var bom_code;
	var gd_type;
	var del_req = new Array();
	
	if(del_pd){ //모달에서 삭제시 (del_pd가 전달되었을떄)
		pd_code = del_pd.getAttribute("data-pdcode");
		bom_code = del_pd.getAttribute("data-etccode");
		gd_type= del_pd.getAttribute("data-type");
		
		if(confirm("정말 삭제하시겠습니까? \n 삭제 후에는 복구되지 않습니다.")){
			del_req = [{
				idx : idx,
				code: pd_code, 
				code2: bom_code, 
				type : gd_type
			}]
			del_ajax(del_req);	//모달 안에서 1개만 삭제 
		}
			
	}else {  //리스트에서 체크박스로 삭제시 (del_pd 전달x)
		var checkboxes = document.querySelectorAll("input[name='bom_select']:checked");
		
		if (checkboxes.length == 0) {
			alert("삭제할 항목을 선택해주세요.");
			
		}else{
			if (confirm("정말 삭제하시겠습니까? \n 삭제 후에는 복구되지 않습니다.")) {
				
				checkboxes.forEach(chk => {
					pd_code = chk.getAttribute("data-pdcode");
					bom_code = chk.getAttribute("data-etccode");
					gd_type= chk.getAttribute("data-type");
					
					del_req.push({ 
						idx : idx,
						code: pd_code, 
						code2: bom_code, 
						type : gd_type 
					})
				});
				del_ajax(del_req); //체크박스로 1개~여러개 삭제 
			}
		}
	}
}





var top_pd_nm = document.querySelector("#product_name");
document.querySelector("#bom_top_pd").innerHTML=`<i class="bi bi-caret-right-fill"></i>`+top_pd_nm.value;

//부자재리스트 모달에서 부자재 한번에 선택하기 
function select_items() {
  const selected_box = document.querySelectorAll('input[name="select"]:checked');
    console.log("함수 작동 2");

  if (typeof parentType !== 'undefined' && parentType === 'wh') {
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
  
  if (typeof parentType !== 'undefined' && parentType != 'wh_inb') {
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
  }
  



//모달에서 선택한 리스트 등록화면의 리스트에 붙여넣기 
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
      <button type="button" class="btn btn-danger btn-sm" onclick="removeRow(this)">삭제</button>
    </td>
  `;
  tbody.append(tr);
}





//bom등록 저장
function bomSave(){
	var tbody = document.querySelector("#bom_items");
	var rows = tbody.querySelectorAll('.item_added'); // 테이블에서 데이터가 있는 행만 선택
	
	var pd_code = document.querySelector("#product_code");
	var pd_type = document.querySelector("#product_type");
	
  	var items = [];
	
	if (rows.length == 0) {
        alert("BOM 등록을 위한 부자재를 선택하세요.");
        return;
    }
  	rows.forEach(row => {
	    // 각 컬럼에서 값을 읽어오기
		var item_code = row.querySelector(".item_code");
		var item_qty =  row.querySelector('.item_qty');
		var item_unit = row.querySelector('.select_unit');
		console.log(item_code.value)
		
		if(item_qty.value == ""){
			alert("소요수량을 입력해야 합니다.");
			item_qty.focus();
			
		}else if(item_unit.value == ""){
			alert("단위를 입력해야 합니다.");
			item_unit.focus();
			
		}else{
		    if(item_qty.value > 0 && item_unit.value != "") {
				//다 입력했으면 배열에 넣기
		      	items.push({
					cProductCode : pd_code.value,
			        cItemCode: item_code.value,
					bomQty: Number(item_qty.value),
					unit: item_unit.value,
					pd_type:pd_type.value
		      	});
	    	}
		}
  	});
	fetch("./bom_insertok.do", {
		method: "PUT",
		headers: {'content-type': 'application/json'},
		body : JSON.stringify(items)
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		console.log("result : " + result)
		if(result=="ok"){
			alert("BOM 등록이 완료되었습니다.");
			location.href="./bom.do"
		}else if(result=="fail"){
			alert("BOM 등록에 실패했습니다.");
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}
/*--------------------------------------------------------------
bom 검색
--------------------------------------------------------------*/
function bomSearch(){
	var keyword = document.querySelector("#keyword");
	var sclass = document.querySelector("#products_class2");
	var form = document.querySelector("#frm_class");
	
	if(sclass.value=="" && keyword.value==""){  //분류, 키워드 모두 없는경우
		alert("검색어를 입력하세요.");
		keyword.focus();
		return false;
		
	}else if(sclass.value=="" && keyword.value!="") {  //키워드만 있는경우 
		sclass.value=null;
		form.action="bom.do";
		form.method="GET";
		form.submit();
		
	}else{  
		form.action="bom.do";
		form.method="GET";
		form.submit();
	}		
}

/*--------------------------------------------------------------
bom 페이징 
--------------------------------------------------------------*/
function go_bom_pg(ee){
	var keyword = ee.getAttribute('data-keyword');
	var page_no = ee.getAttribute('data-pageno');
	var sclass = ee.getAttribute('data-sclass');
	
	var params = {  
		    pageno: page_no,
		    post_ea: ee.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}

		if (sclass) {  //소분류 선택시 
		    params["products_class2"] = sclass;
		}

		var pString = new URLSearchParams(params).toString();
		location.href = "./bom.do?" + pString;
}

/*--------------------------------------------------------------
bom 게시물 개수 선택 
--------------------------------------------------------------*/
function bompostEa(){
	var form = document.querySelector("#bompg_frm");
	form.method = "GET";
	form.action = "./bom.do";
	form.pageno.value = 1; //
	form.submit();
}