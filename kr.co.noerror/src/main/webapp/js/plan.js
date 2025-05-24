function close_modal() {
	// 모달 닫기
	const modalEl = document.getElementById('bom_items');
	const modalInstance = bootstrap.Modal.getInstance(modalEl);
	if (modalInstance) {
	  modalInstance.hide();
	}
}