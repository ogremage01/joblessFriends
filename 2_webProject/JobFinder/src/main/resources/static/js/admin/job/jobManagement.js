/**
 * 직무 관리 JavaScript
 */

document.addEventListener("DOMContentLoaded", function() {
    console.log("직무 관리 스크립트 로드됨");
    
    // 직무 추가 버튼 이벤트
    document.getElementById("jobInsertBtn").addEventListener("click", insertJob);
    
    // 직무 검색 버튼 이벤트
    document.getElementById("jobSearchBtn").addEventListener("click", performJobSearch);
    
    // 검색 엔터키 이벤트
    document.getElementById("jobKeyword").addEventListener("keypress", function(e) {
        if (e.key === 'Enter') {
            performJobSearch();
        }
    });
    
    // 직무 추가 엔터키 이벤트
    document.getElementById("jobInsert").addEventListener("keypress", function(e) {
        if (e.key === 'Enter') {
            insertJob();
        }
    });
    
    // 개별 삭제 버튼 이벤트
    document.querySelectorAll(".delBtn").forEach(function(btn) {
        btn.addEventListener("click", function() {
            const jobId = parseInt(this.value);
            deleteJob([jobId]);
        });
    });
    
    // 대량 삭제 버튼 이벤트
    document.getElementById("massDelJob").addEventListener("click", massDeleteJobs);
});

// 직무 추가 함수
function insertJob() {
    const jobNameInput = document.getElementById("jobInsert");
    const jobGroupSelect = document.getElementById("jobGroupSelect");
    
    const jobName = jobNameInput.value.trim();
    const jobGroupId = parseInt(jobGroupSelect.value);
    
    if (!jobName) {
        alert("직무명을 입력해주세요.");
        jobNameInput.focus();
        return;
    }
    
    if (!jobGroupId) {
        alert("직군을 선택해주세요.");
        jobGroupSelect.focus();
        return;
    }
    
    const requestData = {
        jobName: jobName,
        jobGroupId: jobGroupId
    };
    
    fetch("/admin/job/singleJob/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestData)
    })
    .then(response => response.text())
    .then(data => {
        if (data === "추가완료") {
            alert("직무가 성공적으로 추가되었습니다.");
            location.reload();
        } else {
            alert("직무 추가에 실패했습니다: " + data);
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("직무 추가 중 오류가 발생했습니다.");
    });
}

// 직무 삭제 함수
function deleteJob(jobIdList) {
    if (!confirm("선택한 직무를 삭제하시겠습니까?")) {
        return;
    }
    
    fetch("/admin/job/singleJob/delete", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(jobIdList)
    })
    .then(response => response.text())
    .then(data => {
        if (data === "삭제완료") {
            alert("직무가 성공적으로 삭제되었습니다.");
            location.reload();
        } else {
            alert("직무 삭제에 실패했습니다: " + data);
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("직무 삭제 중 오류가 발생했습니다.");
    });
}

// 대량 삭제 함수
function massDeleteJobs() {
    const checkboxes = document.querySelectorAll(".delJob:checked");
    
    if (checkboxes.length === 0) {
        alert("삭제할 직무를 선택해주세요.");
        return;
    }
    
    const jobIdList = Array.from(checkboxes).map(cb => parseInt(cb.value));
    deleteJob(jobIdList);
}

// 검색 함수
function performJobSearch() {
    const jobKeywordVal = document.getElementById("jobKeyword").value.trim();
    
    if (jobKeywordVal !== "") {
        location.href = `/admin/job/singleJob?page=1&keyword=${jobKeywordVal}`;
    } else {
        location.href = `/admin/job/singleJob?page=1`;
    }
} 

//전체 선택 체크박스

const selectAllCom = document.getElementById("selectAll");

selectAllCom.addEventListener("click", function(e) {
	    const checkboxes = document.querySelectorAll(".admin-checkbox");
    
    checkboxes.forEach(checkbox => {
        checkbox.checked = !checkbox.checked; // 현재 체크 상태를 반전시킴
    });
});