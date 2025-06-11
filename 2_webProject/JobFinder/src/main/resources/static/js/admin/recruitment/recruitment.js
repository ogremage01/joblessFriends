//전체 선택 체크박스

const selectAllCom = document.getElementById("selectAll");

selectAllCom.addEventListener("click", function(e) {
	    const checkboxes = document.querySelectorAll(".delPost");
    
    checkboxes.forEach(checkbox => {
        checkbox.checked = !checkbox.checked; // 현재 체크 상태를 반전시킴
    });
});


function deleteRecruitments(jobPostIds) {
    if (!confirm("삭제를 진행합니까?")) return;

    fetch("/admin/recruitment", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(jobPostIds) // 배열 전달
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("서버 오류: " + response.status);
        }
        return response.text();
    })
    .then(data => {
        if (data == "삭제완료") {
            alert("공고가 성공적으로 삭제되었습니다.");
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

const delBtnArr = document.getElementsByClassName("delBtn");

for (let i = 0; i < delBtnArr.length; i++) {
    delBtnArr[i].addEventListener("click", function (e) {
        const jobPostId = e.target.value;
        deleteRecruitments([jobPostId]); // 단일도 배열로
    });
}

document.getElementById("massDelRecruitment").addEventListener("click", function () {
    const checked = document.querySelectorAll(".delPost:checked");
    const jobPostIds = Array.from(checked).map(el => el.value);

    if (jobPostIds.length === 0) {
        alert("삭제할 항목을 선택하세요.");
        return;
    }

    deleteRecruitments(jobPostIds);
});

const searchRecruitmentBtn = document.getElementById("recruitmentSearchBtn");

searchRecruitmentBtn.addEventListener("click", function(e){
    const recruitmentKeywordVal = document.getElementById("recruitmentKeyword").value.trim();
    
    // 검색어가 있을 때와 없을 때 모두 처리
    if (recruitmentKeywordVal !== "") {
        let url = '/admin/recruitment?page=1&keyword=' + recruitmentKeywordVal
        location.href = url;
    } else {
        // 검색어가 없으면 전체 목록으로
        location.href = `/admin/recruitment?page=1`;
    }
});

// Enter 키 이벤트 추가
document.getElementById("recruitmentKeyword").addEventListener("keypress", function(e) {
    if (e.key === "Enter") {
        searchRecruitmentBtn.click();
    }
});