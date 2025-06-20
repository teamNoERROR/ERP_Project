/*--------------------------------------------------------------
사용자 관리 > 추가 버튼 누른 경우 (사원 등록페이지 이동)
--------------------------------------------------------------*/
function addMember(){
	location.href="./member_insert.do";
}

/*--------------------------------------------------------------
저장버튼 클릭 
--------------------------------------------------------------*/
function insert_member(){
	alert("ss");
}

/*--------------------------------------------------------------
  로그인 화면 이동  
--------------------------------------------------------------*/
function index_go(){
	
	location.href="./"
}


/*--------------------------------------------------------------
 사원 로그인 
--------------------------------------------------------------*/
function memberLogin(){
	var mid = document.querySelector("#userId");
	var mpass = document.querySelector("#userPw");
	
	if(mid.value ==""){
		alert("사원 번호를 입력하세요.");
		mid.focus();
	}
	else if(mpass.value ==""){
		alert("비밀번호를 입력하세요.");
		mpass.focus();
	}
	else{
		fetch("/member_loginOk.do", {
			method: "POST",
			headers: {"content-type": "application/x-www-form-urlencoded"},
			body: "ECODE="+mid.value+"&EMP_PASSWD="+mpass.value
	
		}).then(function(data) {
			return data.text();
	
		}).then(function(result) {
			console.log(result);
			if(result == "ok"){
				alert("로그인 되었습니다.");
				location.href="./main.do";
				
			}else if(result == "no"){
				alert("사원번호와 비밀번호를 다시 확인해주세요.");
				location.reload();
			}		
	
		}).catch(function(error) {
			console.log("통신오류발생" + error);
		});
	}
}

/*--------------------------------------------------------------
 사원 로그아웃
--------------------------------------------------------------*/
function mlogOut(){
	fetch("/member_logoutOk.do", {
		method: "GET",

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
		console.log(result);
		
		if(result == "ok"){
			alert("로그아웃 되었습니다.");
			location.href="./";
			
		}else {
			alert("시스팀문제로 로그아웃되지 못했습니다\n 관리자에게 문의해주세요.");
		}		

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
	
}

























































