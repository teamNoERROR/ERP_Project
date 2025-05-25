/*--------------------------------------------------------------
  Toggle 버튼 클릭시
--------------------------------------------------------------*/



/*--------------------------------------------------------------
  이미지 첨부시 미리보기
--------------------------------------------------------------*/
function previewFile() {
  const fileInput = document.getElementById('productImage');
  const preview = document.getElementById('previewImage');
  const fileNameDisplay = document.getElementById('fileName');

  const file = fileInput.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function(e) {
      preview.src = e.target.result;
    };
    reader.readAsDataURL(file);

    // 파일명 표시
    fileNameDisplay.textContent = file.name;
  } 
}




