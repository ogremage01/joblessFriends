/**
 * 
 */


//------------------------------------기업 목록 JS

//기업 검색

const searchComBtn = document.getElementById("companySearchBtn");

searchComBtn.addEventListener("click", function(e){
    const companyKeywordVal = document.getElementById("companyKeyword").value;
    
    if(companyKeywordVal!=null||companyKeywordVal!=""){
        
        location.href=`/admin/member/company?keyword=${companyKeywordVal}`
        
    }
    
    
    
    
});
//전체 선택 체크박스

const selectAllCom = document.getElementById("selectAll");

selectAllCom.addEventListener("click", function(e) {
	    const checkboxes = document.querySelectorAll(".admin-checkbox");
    
    checkboxes.forEach(checkbox => {
        checkbox.checked = !checkbox.checked; // 현재 체크 상태를 반전시킴
    });
});

//기업 대량 탈퇴
const massDelComBtn = document.getElementById("massDelCom");

massDelComBtn.addEventListener("click", function(e) {
    const delCompanyArr = document.querySelectorAll(".ds:checked");
	    if(delCompanyArr.length===0){
			        alert("선택된 기업이 없습니다.");
        return;
		    }
    const confirmed = confirm(`선택된 ${delCompanyArr.length}개 기업을 삭제하시겠습니까?\n\n※ 주의: 각 기업의 모든 채용공고, 지원자 데이터, 관련 파일 등이 함께 삭제됩니다.\n이 작업은 되돌릴 수 없습니다.`);
    
    if(confirmed&&delCompanyArr.length>0){
        
        // 로딩 표시
        const deleteButton = document.getElementById("massDelCom");
        const originalText = deleteButton.textContent;
        deleteButton.textContent = "처리중...";
        deleteButton.disabled = true;
        
        const jsonData = [];
        
        delCompanyArr.forEach(cb=>{

            jsonData.push(cb.value);
        });
    
        console.log(jsonData);
        
        fetch('/admin/member/company/',{

            method: 'DELETE',
            headers: {
                'content-type': 'application/json'
            
            },
            body: JSON.stringify(jsonData)  // JSON.stringify를 통해 body에 데이터 포함
    
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(`서버 오류 (${response.status}): ${text}`);
                });
            }
            return response.json();
        })
        .then(data => {

            if(data===delCompanyArr.length){
                console.log('Success:', data);
                alert(`${data}개 기업이 성공적으로 삭제되었습니다.\n관련된 모든 채용공고와 데이터가 함께 삭제되었습니다.`);
                location.reload();
            }else{
                alert(`삭제 처리 완료: ${data}개 성공 / ${delCompanyArr.length}개 중\n일부 기업의 삭제가 실패했을 수 있습니다.`);
                location.reload();
            
            }
        })
        .catch(error => {
            console.error('Error:', error);
            let errorMessage = "삭제 실패";
            
            if (error.message.includes("서버 오류")) {
                errorMessage = "서버에서 오류가 발생했습니다. 관리자에게 문의하세요.";
            } else {
                errorMessage = "기업 대량 삭제 처리 중 오류가 발생했습니다: " + error.message;
            }
            
            alert(errorMessage);
            
            // 버튼 복원
            deleteButton.textContent = originalText;
            deleteButton.disabled = false;
        });
    
    }else{

        alert("오류가 발생했습니다.");
    }
});

