document.addEventListener('DOMContentLoaded', () => {
  const modalContainer = document.getElementById('modalContainer') || document.body; // 모달 삽입 컨테이너, 없으면 body에 넣기
  const loadedModals = new Set(); // 이미 로딩된 모달 ID 저장

  document.querySelectorAll('.modal-trigger').forEach(trigger => {
    const modalUrl = trigger.dataset.modalUrl;
    const modalId = trigger.dataset.modalId;

    if (!modalUrl || !modalId) {
      console.warn('modal-trigger 요소에 data-modal-url, data-modal-id가 필요합니다.');
      return;
    }

    trigger.addEventListener('click', async () => {
      // 모달이 이미 로딩되었으면 바로 show
      if (loadedModals.has(modalId)) {
        const modalEl = document.getElementById(modalId);
        if (modalEl) {
          const modal = bootstrap.Modal.getInstance(modalEl) || new bootstrap.Modal(modalEl);
          modal.show();
          return;
        }
      }

      // 아직 로딩 안된 모달이면 fetch로 불러와서 DOM에 삽입 후 show
      try {
        const response = await fetch(modalUrl);
        if (!response.ok) throw new Error(`Failed to load modal HTML from ${modalUrl}`);
        const html = await response.text();

        // modalContainer에 삽입
        modalContainer.insertAdjacentHTML('beforeend', html);

        // 로딩 완료 표시
        loadedModals.add(modalId);

        // 모달 보여주기
        const modalEl = document.getElementById(modalId);
        if (modalEl) {
          const modal = new bootstrap.Modal(modalEl);

          // 모달 닫힐 때 DOM 삭제 및 Set에서 제거
          modalEl.addEventListener('hidden.bs.modal', () => {
            modalEl.remove();
            loadedModals.delete(modalId);
          });

          modal.show();
        }
      } catch (error) {
        console.error(error);
      }
    });
  });
});
