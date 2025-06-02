// 댓글 관리 JavaScript

function deleteComments(commentIdList) {
    if (!confirm("삭제를 진행합니까?")) return;

    fetch("/admin/community/comment/delete", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(commentIdList) // 배열 전달
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("서버 오류: " + response.status);
        }
        return response.text();
    })
    .then(data => {
        if (data == "삭제완료") {
            location.reload();
        } else {
            alert("삭제 실패: 서버 응답 오류");
        }
    })
    .catch(error => {
        alert("삭제 실패");
        console.error("에러 발생:", error);
    });
}

function performCommentSearch() {
    const commentKeywordVal = document.getElementById("commentKeyword").value.trim();
    
    if (commentKeywordVal !== "") {
        location.href = `/admin/community/comment?keyword=${encodeURIComponent(commentKeywordVal)}`;
    } else {
        location.href = `/admin/community/comment`;
    }
}

// DOM이 로드된 후 실행
$(document).ready(function() {
    // 개별 삭제 버튼 이벤트
    const delBtnArr = $(".delBtn");

    for (let i = 0; i < delBtnArr.length; i++) {//선택된 삭제 목록
        delBtnArr[i].addEventListener("click", function (e) {
            const commentId = e.target.value;
            deleteComments([commentId]); // 단일도 배열로
        });
    }

    // 대량 삭제 버튼 이벤트
    document.getElementById("massDelCom").addEventListener("click", function () {
        const checked = document.querySelectorAll(".delPost:checked");
        const commentIdList = Array.from(checked).map(el => el.value);

        if (commentIdList.length === 0) {
            alert("삭제할 항목을 선택하세요.");
            return;
        }

        deleteComments(commentIdList);
    });

    // 검색 버튼 이벤트
    const searchCommentBtn = document.getElementById("commentSearchBtn");
    searchCommentBtn.addEventListener("click", performCommentSearch);

    // Enter 키 이벤트 추가
    document.getElementById("commentKeyword").addEventListener("keypress", function(e) {
        if (e.key === "Enter") {
            performCommentSearch();
        }
    });
}); 