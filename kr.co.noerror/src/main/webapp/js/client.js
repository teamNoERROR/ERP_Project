//토글버튼 클릭시 페이지 이동
function clientToggle(type) {
  	let url = "";
	if (type == 'client') {
	     url = "./client.do?type="+type;
	} else if (type == 'p_client') {
	     url = "./client.do?type="+type;
	} 
 	location.href = url; //페이지 이동
}
