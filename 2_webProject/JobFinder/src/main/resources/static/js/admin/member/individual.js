
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
    const confirmed = confirm("삭제하시겠습니까?");
    
    if(confirmed&&delMemberArr.length>0){
        
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
        .then(response => response.json())
        .then(data => {

            if(data===delMemberArr.length){
                console.log('Success:', data);
                alert("삭제 완료");
                location.reload();
            }else{

                alert("삭제 실패");
                location.reload();
            
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("삭제 실패");
        });
    
    }else{

        alert("선택된 회원이 없습니다.");
    }
});