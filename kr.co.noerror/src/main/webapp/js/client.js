//토글버튼 클릭시 페이지 이동
function clientToggle(type) {
	location.href = "/client.do?type="+type; //페이지 이동
}

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


