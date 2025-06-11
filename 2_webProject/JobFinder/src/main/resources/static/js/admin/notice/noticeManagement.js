// 공지 관리 JavaScript

function moveUpdateNotice(noticeId) {
    let noticeBtn = $('#modi_' + noticeId);
    let noticeNo = noticeBtn.val();

    $('#noticeFormNo').val(noticeNo);
    document.getElementById('noticeSelectOneForm').submit();
}

function deleteNotices(noticeIdList) {
    if (!confirm("삭제를 진행합니까?")) return;

    fetch("/admin/community/notice/delete", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(noticeIdList)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("서버 오류: " + response.status);
        }
        return response.text();
    })
    .then(data => {
        if (data == "삭제완료") {
			alert("공지 사항이 성공적으로 삭제되었습니다.");
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

function uploadNotice() {
    location.href = "/admin/community/notice/upload";
}

// DOM이 로드된 후 실행
$(document).ready(function() {
    // 개별 삭제 버튼 이벤트
    const delBtnArr = $(".delBtn");
    for (let i = 0; i < delBtnArr.length; i++) {
        delBtnArr[i].addEventListener("click", function (e) {
            const noticeId = e.target.value;
            deleteNotices([noticeId]);
        });
    }

    // 대량 삭제 버튼 이벤트
    document.getElementById("massDelCom").addEventListener("click", function () {
        const checked = document.querySelectorAll(".delPost:checked");
        const noticeIdList = Array.from(checked).map(el => el.value);

        if (noticeIdList.length === 0) {
            alert("삭제할 항목을 선택하세요.");
            return;
        }

        deleteNotices(noticeIdList);
    });

    // 검색 버튼 이벤트
    const searchNoticeBtn = document.getElementById("noticeSearchBtn");
    searchNoticeBtn.addEventListener("click", function(e) {
        const noticeKeywordVal = document.getElementById("noticeKeyword").value.trim();
        
        if (noticeKeywordVal !== "") {
            location.href = `/admin/community/notice?keyword=${encodeURIComponent(noticeKeywordVal)}`;
        } else {
            location.href = `/admin/community/notice`;
        }
    });

    // Enter 키 이벤트 추가
    document.getElementById("noticeKeyword").addEventListener("keypress", function(e) {
        if (e.key === "Enter") {
            searchNoticeBtn.click();
        }
    });
}); 


//전체 선택 체크박스

const selectAllCom = document.getElementById("selectAll");

selectAllCom.addEventListener("click", function(e) {
	    const checkboxes = document.querySelectorAll(".admin-checkbox");
    
    checkboxes.forEach(checkbox => {
        checkbox.checked = !checkbox.checked; // 현재 체크 상태를 반전시킴
    });
});