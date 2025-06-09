// 스킬 관리 JavaScript

function deleteSkills(skillIdList) {
    if (!confirm("삭제를 진행합니까?")) return;

    fetch("/admin/skill/delete", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(skillIdList)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("서버 오류: " + response.status);
        }
        return response.text();
    })
    .then(data => {
        if (data == "삭제완료") {
            alert("스킬이 성공적으로 삭제되었습니다.");
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

function addSkill() {
    const skillName = document.getElementById("skillInsert").value.trim();
    const jobGroupId = document.getElementById("jobGroupSelect").value;
    
    if (!skillName) {
        alert("스킬명을 입력해주세요.");
        return;
    }
    
    if (!jobGroupId) {
        alert("직군을 선택해주세요.");
        return;
    }
    
    fetch("/admin/skill/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            tagName: skillName,
            jobGroupId: parseInt(jobGroupId)
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
            alert("스킬이 추가되었습니다.");
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

function performSkillSearch() {
    const skillKeywordVal = document.getElementById("skillKeyword").value.trim();
    
    if (skillKeywordVal !== "") {
        location.href = `/admin/skill?page=1&keyword=${skillKeywordVal}`;
    } else {
        location.href = `/admin/skill?page=1`;
    }
}

// DOM이 로드된 후 실행
$(document).ready(function() {
    // 개별 삭제 버튼 이벤트
    const delBtnArr = document.getElementsByClassName("delBtn");
    
    for (let i = 0; i < delBtnArr.length; i++) {
        delBtnArr[i].addEventListener("click", function (e) {
            const skillId = e.target.value;
            deleteSkills([skillId]);
        });
    }
    
    // 대량 삭제 버튼 이벤트
    document.getElementById("massDelSkill").addEventListener("click", function () {
        const checked = document.querySelectorAll(".delSkill:checked");
        const skillIdList = Array.from(checked).map(el => el.value);
    
        if (skillIdList.length === 0) {
            alert("삭제할 항목을 선택하세요.");
            return;
        }
    
        deleteSkills(skillIdList);
    });
    
    // 스킬 추가 버튼 이벤트
    const skillInsertBtn = document.getElementById("skillInsertBtn");
    skillInsertBtn.addEventListener('click', addSkill);
    
    // 검색 버튼 이벤트
    const searchSkillBtn = document.getElementById("skillSearchBtn");
    const skillKeywordInput = document.getElementById("skillKeyword");
    
    searchSkillBtn.addEventListener("click", performSkillSearch);
    
    // Enter 키 이벤트
    skillKeywordInput.addEventListener("keypress", function(e) {
        if (e.key === "Enter") {
            performSkillSearch();
        }
    });
    
    // 스킬명 입력 필드의 Enter 키 이벤트
    document.getElementById("skillInsert").addEventListener("keypress", function(e) {
        if (e.key === "Enter") {
            addSkill();
        }
    });
    
    // 직군 선택의 Enter 키 이벤트
    document.getElementById("jobGroupSelect").addEventListener("keypress", function(e) {
        if (e.key === "Enter") {
            addSkill();
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