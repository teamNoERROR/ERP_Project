//발주처 JS 

/*--------------------------------------------------------------
발주처 상세보기 모달 오픈 
--------------------------------------------------------------*/

function openCltDetail(event){
	var cidx = event.getAttribute("data-idx");
	var clt_code = event.getAttribute("data-code");
	var clt_type = event.getAttribute("data-type");
	
	fetch("./client_detail.do", {
		method: "POST",
		headers: {"content-type": "application/x-www-form-urlencoded"},
		body: "cidx="+cidx+"&code="+clt_code+"&type="+clt_type
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		document.getElementById("modalContainer").innerHTML = result;
		var modal = new bootstrap.Modal(document.getElementById("client_detail_md"));
		modal.show();
		
	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
	
	
	
}


/*
//거래처 페이징 
function go_clt_pg(data){
	var keyword = data.getAttribute('data-keyword');
	var page_no = data.getAttribute('data-pageno');
	var sclass = data.getAttribute('data-sclass');
	
	var params = {  
		    type: data.getAttribute('data-type'),
		    pageno: page_no,
		    post_ea: data.getAttribute('data-pea'),
		};
		
		if (keyword) {  //키워드가 있으면
		    params["keyword"] = keyword;
		}

		var pString = new URLSearchParams(params).toString();
		location.href = "./client.do?" + pString;
}



//거래처 게시물 개수 선택 
function cltPostEa(type){
	var form = document.querySelector("#clt_pg_frm");
	form.method = "GET";
	form.action = "./client.do";
	form.type.value=type;
	form.pageno.value = 1; //
	form.submit();
}
*/

