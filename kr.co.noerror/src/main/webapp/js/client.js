/*--------------------------------------------------------------
토글버튼 클릭시 페이지 이동
--------------------------------------------------------------*/
function clientToggle(type) {
	location.href = "/client.do?type="+type; //페이지 이동
}


/*--------------------------------------------------------------
	거래처 추가 버튼 클릭 > 거래처 등록화면으로 이동 
--------------------------------------------------------------*/
function addClient() {
	location.href = "/client_insert.do"; //페이지 이동
}

/*--------------------------------------------------------------
	전역변수 
--------------------------------------------------------------*/
var client_type = document.querySelector("#client_type");
var comp_name = document.querySelector("#comp_name");
var biz_num = document.querySelector("#biz_num");
var biz_type = document.querySelector("#biz_type");
var biz_item = document.querySelector("#biz_item");
var ceo_name = document.querySelector("#ceo_name");
var com_zip = document.querySelector("#com_zip");
var com_addr1 = document.querySelector("#com_addr1");
var com_addr2 = document.querySelector("#com_addr2");
var com_memo = document.querySelector("#com_memo");
var mng_name = document.querySelector("#mng_name");
var mng_part = document.querySelector("#mng_part");
var mng_position = document.querySelector("#mng_position");
var mng_email = document.querySelector("#mng_email");
var mng_tel = document.querySelector("#mng_tel");
var mng_phone = document.querySelector("#mng_phone");
var mng_fax = document.querySelector("#mng_fax");
var mng_memo = document.querySelector("#mng_memo");
var addrBtn = document.querySelector("#addrBtn");
var clt_file;

/*--------------------------------------------------------------
	주소 검색   
--------------------------------------------------------------*/
function openAddr() {
    new daum.Postcode({
        oncomplete: function(data) {
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

            com_zip.value = data.zonecode;
            com_addr1.value = addr;
            com_addr2.focus();
        }
    }).open();
}


/*--------------------------------------------------------------
	거래처 등록 + 유효성검사  
--------------------------------------------------------------*/
function insert_client(){
	clt_file = document.querySelector("#clientImage").files[0];
	
	if(client_type.value ==""){
		alert("거래처 유형을 선택하세요");
		client_type.focus();
	}
	else if(comp_name.value ==""){
		alert("회사명을 선택하세요");
		comp_name.focus();
	}
	else if(biz_num.value ==""){ 
		alert("사업자번호를 입력하세요");
		biz_num.focus();
	}
	else if(biz_type.value ==""){ 
		alert("거래처의 업종을 입력하세요");
		biz_type.focus();	
			
	}else if(biz_item.value ==""){ 
		alert("거래처의 종목을 입력하세요");
		biz_item.focus();		
				
	}else if(ceo_name.value ==""){ 
		alert("거래처의 대표자명을 입력하세요");
		ceo_name.focus();		
				
	}else if(com_zip.value ==""){ 
		alert("주소를 검색 후 선택하세요");
		com_zip.focus();			
					
	}else if(cltUseY.checked == false && cltUseN.checked == false){ 
		alert("거래여부를 선택하세요");
	}
	else if(mng_name.value ==""){ 
		alert("거래처의 담당자 이름을 입력하세요");
		mng_name.focus();							
			
	}else if(mng_part.value==""){
		alert("담당자의 부서를 입력하세요");
		mng_part.focus();
				
	}
	else if(mng_position.value==""){
		alert("담당자의 직급을 입력하세요");
		mng_position.focus();
					
	}
	else if(mng_email.value==""){
		alert("담당자의 이메일을 입력하세요");
		mng_email.focus();
				
	}
	else if(mng_tel.value==""){
		alert("담당자의 직통번호를 입력하세요");
		mng_tel.focus();
				
	}
	else if(mng_phone.value==""){
		alert("담당자의 휴대폰번호를 입력하세요");
		mng_phone.focus();
				
	}
	else if(clt_file){   //파일첨부 함경우 
		var imgSize = clt_file.size; // 파일 크기
		var maxSize = 2 * 1024 * 1024; // 2MB제한
			
		if (!clt_file.type.startsWith("image/")) {
    		alert("이미지 파일만 첨부 가능합니다 (jpg, png, gif 등)");
		}
		else if(imgSize > maxSize){
			alert("파일첨부 용량은 2MB이하만 가능합니다.");
			
		}else {
			insertClient();
		}
	}else {
		insertClient();
	}
}

/*--------------------------------------------------------------
거래처 등록 ajax
--------------------------------------------------------------*/
function insertClient(){
	var use_flag = document.querySelector('input[name="com_use_flag"]:checked');
	clt_file = document.querySelector("#clientImage").files[0];

	var formData = new FormData();
	formData.append("CLIENT_TYPE", client_type.value);
	formData.append("COMPANY_NAME", comp_name.value);
	formData.append("BIZ_NUM", biz_num.value);
	formData.append("BIZ_TYPE", biz_type.value);
	formData.append("BIZ_ITEM", biz_item.value);
	formData.append("CEO_NAME", ceo_name.value);
	formData.append("COM_ZIP", com_zip.value);
	formData.append("COM_ADDR1", com_addr1.value);
	formData.append("COM_ADDR2", com_addr2.value);
	formData.append("COM_USE_FLAG", use_flag.value);
	formData.append("COM_MEMO", com_memo.value);
	formData.append("MANAGER_NAME", mng_name.value);
	formData.append("MNG_EMAIL", mng_email.value);
	formData.append("MNG_TEL", mng_tel.value);
	formData.append("MNG_PHONE", mng_phone.value);
	formData.append("MNG_FAX", mng_fax.value);
	formData.append("MNG_PART", mng_part.value);
	formData.append("MNG_POSITION", mng_position.value);
	formData.append("MNG_MEMO", mng_memo.value);
	
    formData.append("clientImage", clt_file);
	
	fetch("./client_insertok.do", {
		method: "POST",
		body : formData
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		if(result=="ok"){
			alert("거래처 등록이 완료되었습니다.");
			
			if(client_type.value == "판매처"){
				location.href="./client.do?type=client";
			}else if(client_type.value == "구매처"){
				location.href="./client.do?type=p_client";
			}
			
		}else if(result=="fail"){
			alert("시스템문제로 거래처등록에 실패했습니다.\n관리자에게 문의해주세요.");
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}


/*--------------------------------------------------------------
	거래처 상세보기 모달 오픈 
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


/*--------------------------------------------------------------
	거래처 정보 수정  
--------------------------------------------------------------*/
function cltModify(btn){
	var cidx= btn.getAttribute("data-cidx");
	var c_code= btn.getAttribute("data-cltcode");
	
	location.href="./client_modify.do?code1="+cidx+"&code2="+c_code;
}


//	거래처 정보 수정 
function modify_client(btn){
	var cidx= btn.getAttribute("data-idx");
	var c_code= btn.getAttribute("data-code");
	
	clt_file = document.querySelector("#clientImage").files[0];
		
	if(client_type.value ==""){
		alert("거래처 유형을 선택하세요");
		client_type.focus();
	}
	else if(comp_name.value ==""){
		alert("회사명을 선택하세요");
		comp_name.focus();
	}
	else if(biz_num.value ==""){ 
		alert("사업자번호를 입력하세요");
		biz_num.focus();
	}
	else if(biz_type.value ==""){ 
		alert("거래처의 업종을 입력하세요");
		biz_type.focus();	
			
	}else if(biz_item.value ==""){ 
		alert("거래처의 종목을 입력하세요");
		biz_item.focus();		
				
	}else if(ceo_name.value ==""){ 
		alert("거래처의 대표자명을 입력하세요");
		ceo_name.focus();		
				
	}else if(com_zip.value ==""){ 
		alert("주소를 검색 후 선택하세요");
		com_zip.focus();			
					
	}else if(cltUseY.checked == false && cltUseN.checked == false){ 
		alert("거래여부를 선택하세요");
	}
	else if(mng_name.value ==""){ 
		alert("거래처의 담당자 이름을 입력하세요");
		mng_name.focus();							
			
	}else if(mng_part.value==""){
		alert("담당자의 부서를 입력하세요");
		mng_part.focus();
				
	}
	else if(mng_position.value==""){
		alert("담당자의 직급을 입력하세요");
		mng_position.focus();
					
	}
	else if(mng_email.value==""){
		alert("담당자의 이메일을 입력하세요");
		mng_email.focus();
				
	}
	else if(mng_tel.value==""){
		alert("담당자의 직통번호를 입력하세요");
		mng_tel.focus();
				
	}
	else if(mng_phone.value==""){
		alert("담당자의 휴대폰번호를 입력하세요");
		mng_phone.focus();
				
	}
	else if(clt_file){   //파일첨부 함경우 
		var imgSize = clt_file.size; // 파일 크기
		var maxSize = 2 * 1024 * 1024; // 2MB제한
			
		if (!clt_file.type.startsWith("image/")) {
    		alert("이미지 파일만 첨부 가능합니다 (jpg, png, gif 등)");
		}
		else if(imgSize > maxSize){
			alert("파일첨부 용량은 2MB이하만 가능합니다.");
			
		}else {
			cltModifyOk(cidx,c_code);
		}
	}else {
		cltModifyOk(cidx,c_code);
	}
}

//게시글 수정(ajax)
function cltModifyOk(cidx,c_code){
	var use_flag = document.querySelector('input[name="com_use_flag"]:checked');
	clt_file = document.querySelector("#clientImage").files[0];

	var formData = new FormData();
	formData.append("CIDX", cidx);
	formData.append("COMPANY_CODE", c_code);
	formData.append("COMPANY_NAME", comp_name.value);
	formData.append("BIZ_NUM", biz_num.value);
	formData.append("BIZ_TYPE", biz_type.value);
	formData.append("BIZ_ITEM", biz_item.value);
	formData.append("CEO_NAME", ceo_name.value);
	formData.append("COM_ZIP", com_zip.value);
	formData.append("COM_ADDR1", com_addr1.value);
	formData.append("COM_ADDR2", com_addr2.value);
	formData.append("COM_USE_FLAG", use_flag.value);
	formData.append("COM_MEMO", com_memo.value);
	formData.append("MANAGER_NAME", mng_name.value);
	formData.append("MNG_EMAIL", mng_email.value);
	formData.append("MNG_TEL", mng_tel.value);
	formData.append("MNG_PHONE", mng_phone.value);
	formData.append("MNG_FAX", mng_fax.value);
	formData.append("MNG_PART", mng_part.value);
	formData.append("MNG_POSITION", mng_position.value);
	formData.append("MNG_MEMO", mng_memo.value);
	
	if (clt_file) { //새로 파일 첨부를 한 경우 
    	formData.append("clientImage", clt_file);
	}
		
	fetch("./client_modifyok.do", {
		method: "POST",
		body : formData
		
	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		if(result=="ok"){
			alert("거래처 정보가 수정되었습니다.");
			
			if(client_type.value == "판매처"){
				location.href="./client.do?type=client";
			}else if(client_type.value == "구매처"){
				location.href="./client.do?type=p_client";
			}
			
		}else if(result=="fail"){
			alert("시스템문제로 거래처 정보 수정에 실패했습니다.\n관리자에게 문의해주세요.");
		}

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
	
	
	
	
}






/*--------------------------------------------------------------
	거래처 페이징 
--------------------------------------------------------------*/
function go_clt_pg(data){
	var keyword = data.getAttribute('data-keyword');
	var page_no = data.getAttribute('data-pageno');
	
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


/*--------------------------------------------------------------
	거래처 게시물 개수 선택 
--------------------------------------------------------------*/
function cltPostEa(type){
	var form = document.querySelector("#clt_pg_frm");
	form.method = "GET";
	form.action = "./client.do";
	form.type.value=type;
	form.pageno.value = 1; //
	form.submit();
}


