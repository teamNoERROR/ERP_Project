//var pd_st_cd=document.querySelectorAll(".st_pdcd");


/*
window.onload=function(){
	var stock_tbody = document.querySelector("#stock_tb");
	var stock_rows = stock_tbody.querySelectorAll('tr.stock_pd_row');
	var stPdCd;
	var stPdList = [];
	stock_rows.forEach(st_row => {

		stPdCd = st_row.querySelector(".st_pdcd").textContent;
		
		stPdList.push({
			stPdCd
	  	});
		
		console.log(stPdCd);
		console.log(stPdList);
	});
	
	fetch("./inventory.do", {
			method: "POST",
			headers: {"content-type": "application/json"},
			body : JSON.stringify(stPdList)
			
		}).then(function(data) {
			return data.json();
	
		}).then(function(result) {
			console.log(result)
	
		}).catch(function(error) {
			console.log("통신오류발생" + error);
	});
}
*/