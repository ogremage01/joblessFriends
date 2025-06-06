// 목록 페이지로 돌아가기
function goBackToList() {
	const prevUrl = sessionStorage.getItem("prevNoticeListUrl");

	if (prevUrl || prevUrl!= prevUrl) {
		location.href = prevUrl;
	} else {
		location.href = "/community/notice";
	}
}


function deleteNotice(noticeIdList){
	let url = "/admin/community/notice/delete";

	if(confirm('공지 사항을 삭제하시겠습니까?')){
	
		fetch(url, {
			method: 'DELETE',
			headers: {
				            "Content-Type": "application/json"
				        },
				        body: JSON.stringify([noticeIdList]) // 배열 전달
				    })
				    .then(response => {
				        if (!response.ok) {
				            throw new Error("서버 오류: " + response.status);
				        }
				        return response.text();
				    })
				    .then(data => {
				        if (data == "삭제완료") {
				            location.href="/community/notice";
				        } else {
				            alert("삭제 실패: 서버 응답 오류");
				        }
				    })
				    .catch(error => {
				        alert("삭제 실패");
				        console.error("에러 발생:", error);
				    });
				}
}
