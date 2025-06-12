//검색
$("#memberSearchBtn").click(function(e) {
	memberKeywordVal = $("#memberKeyword").val();
	if(memberKeywordVal!=null||memberKeywordVal!=""){
		window.location.href = `/admin/member/individual?keyword=${memberKeywordVal}`;
	}
});

//선택 탈퇴

 $("#massDelMem").click( function(e) {
    const delMemberArr = document.querySelectorAll(".delMember:checked");
	if(delMemberArr.length===0){
		alert("선택된 회원이 없습니다.");
		return;
		}
    const confirmed = confirm(`선택된 ${delMemberArr.length}명의 회원을 삭제하시겠습니까?\n\n※ 주의: 각 회원의 모든 이력서, 지원 이력, 북마크 등이 함께 삭제됩니다.\n이 작업은 되돌릴 수 없습니다.`);
    
    if(confirmed&&delMemberArr.length>0){
        
        // 로딩 표시
        const deleteButton = document.getElementById("massDelMem");
        const originalText = deleteButton.textContent;
        deleteButton.textContent = "처리중...";
        deleteButton.disabled = true;
        
        const jsonData = [];
        
        delMemberArr.forEach(cb=>{

            jsonData.push(cb.value);
        });
    
        console.log(jsonData);
        
        fetch('/admin/member/individual/',{

            method: 'DELETE',
            headers: {
                'content-type': 'application/json'
            
            },
            body: JSON.stringify(jsonData)
    
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

            if(data===delMemberArr.length){
                console.log('Success:', data);
                alert(`${data}명의 회원이 성공적으로 삭제되었습니다.\n관련된 모든 데이터가 함께 삭제되었습니다.`);
                location.reload();
            }else{
                alert(`삭제 처리 완료: ${data}명 성공 / ${delMemberArr.length}명 중\n일부 회원의 삭제가 실패했을 수 있습니다.`);
                location.reload();
            
            }
        })
        .catch(error => {
            console.error('Error:', error);
            let errorMessage = "삭제 실패";
            
            if (error.message.includes("서버 오류")) {
                errorMessage = "서버에서 오류가 발생했습니다. 관리자에게 문의하세요.";
            } else {
                errorMessage = "회원 대량 삭제 처리 중 오류가 발생했습니다: " + error.message;
            }
            
            alert(errorMessage);
            
            // 버튼 복원
            deleteButton.textContent = originalText;
            deleteButton.disabled = false;
        });
    
    }else{

        alert("선택된 회원이 없습니다.");
    }
});



const selectAllCom = document.getElementById("selectAll");

selectAllCom.addEventListener("click", function(e) {
	    const checkboxes = document.querySelectorAll(".delMember");
    
    checkboxes.forEach(checkbox => {
        checkbox.checked = !checkbox.checked; // 현재 체크 상태를 반전시킴
    });
});