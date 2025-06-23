/*--------------------------------------------------------------
토글버튼 클릭시 페이지 이동
--------------------------------------------------------------*/
function stockToggleButton(type) {
	if(type == 'product'){
	 	location.href = "/inventory.do?"; //페이지 이동
	}else if(type == 'item'){
		location.href = "/inventory_itm.do"; //페이지 이동
	}
}
