// 직군 관리 JavaScript

function deleteJobGroups(jobGroupIdList) {
    if (!confirm("삭제를 진행합니까?")) return;

    fetch("/admin/job/jobGroup/delete", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(jobGroupIdList)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("서버 오류: " + response.status);
        }
        return response.text();
    })
    .then(data => {
        if (data == "삭제완료") {
			alert("직군이 성공적으로 삭제되었습니다.");
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

function addJobGroup() {
    const jobGroupInsertVal = document.getElementById("jobGroupInsert").value.trim();
    
    if (!jobGroupInsertVal) {
        alert("직군명을 입력해주세요.");
        return;
    }
    
    fetch("/admin/job/jobGroup/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            jobGroupName: jobGroupInsertVal
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("서버 오류: " + response.status);
        }
        return response.text();
    })
    .then(data => {
        if (data == "추가완료") {
            alert("직군이 추가되었습니다.");
            location.reload();
        } else {
            alert("추가 실패: 서버 응답 오류");
        }
    })
    .catch(error => {
        alert("추가 실패");
        console.error("에러 발생:", error);
    });
}

function performJobGroupSearch() {
    const jobGroupKeywordVal = document.getElementById("jobGroupKeyword").value.trim();
    
    // 검색어가 있을 때와 없을 때 모두 처리
    if (jobGroupKeywordVal !== "") {
        let url = '/admin/job/jobGroup?page=1&keyword=' + encodeURIComponent(jobGroupKeywordVal);
        location.href = url;
    } else {
        // 검색어가 없으면 전체 목록으로
        location.href = `/admin/job/jobGroup?page=1`;
    }
}

// DOM이 로드된 후 실행
$(document).ready(function() {
    // 개별 삭제 버튼 이벤트
    const delBtnArr = document.getElementsByClassName("delBtn");

    for (let i = 0; i < delBtnArr.length; i++) {
        delBtnArr[i].addEventListener("click", function (e) {
            const jobGroupId = e.target.value;
            deleteJobGroups([jobGroupId]);
        });
    }

    // 대량 삭제 버튼 이벤트
    document.getElementById("massDelJobGroup").addEventListener("click", function () {
        const checked = document.querySelectorAll(".delCompany:checked");
        const jobGroupIdList = Array.from(checked).map(el => el.value);

        if (jobGroupIdList.length === 0) {
            alert("삭제할 항목을 선택하세요.");
            return;
        }

        deleteJobGroups(jobGroupIdList);
    });

    // 직군 추가 버튼 이벤트
    const jobGroupInsertBtn = document.getElementById("jobGroupInsertBtn");
    jobGroupInsertBtn.addEventListener('click', addJobGroup);

    // 검색 버튼 이벤트
    const searchjobGroupBtn = document.getElementById("jobGroupSearchBtn");
    const jobGroupKeywordInput = document.getElementById("jobGroupKeyword");

    searchjobGroupBtn.addEventListener("click", performJobGroupSearch);

    // Enter 키 이벤트 추가
    jobGroupKeywordInput.addEventListener("keypress", function(e) {
        if (e.key === "Enter") {
            performJobGroupSearch();
        }
    });

    // 직군명 입력 필드의 Enter 키 이벤트
    document.getElementById("jobGroupInsert").addEventListener("keypress", function(e) {
        if (e.key === "Enter") {
            addJobGroup();
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