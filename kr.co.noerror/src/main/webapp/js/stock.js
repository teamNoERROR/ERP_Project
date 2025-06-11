function item_stock (){
	fetch("/item_stock.do?item_code=" + item_code, {
		method: "GET"

	}).then(function(data) {
		return data.text();

	}).then(function(result) {
	

	}).catch(function(error) {
		console.log("통신오류발생" + error);
	});
}