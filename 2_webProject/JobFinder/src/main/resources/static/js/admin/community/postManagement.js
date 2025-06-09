function deleteCommunities(communityIdList) {
    if (!confirm("삭제를 진행합니까?")) return;

    fetch("/admin/community/post/delete", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(communityIdList) // 배열 전달
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("서버 오류: " + response.status);
        }
        return response.text();
    })
    .then(data => {
        if (data == "삭제완료") {
			alert("게시물이 성공적으로 삭제되었습니다.");
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

const delBtnArr = $(".delBtn");

for (let i = 0; i < delBtnArr.length; i++) {//선택된 삭제 목록
    delBtnArr[i].addEventListener("click", function (e) {
        const communityId = e.target.value;
        deleteCommunities([communityId]); // 단일도 배열로
    });
}

document.getElementById("massDelCom").addEventListener("click", function () {
    const checked = document.querySelectorAll(".delPost:checked");
    const communityIdList = Array.from(checked).map(el => el.value);

    if (communityIdList.length === 0) {
        alert("삭제할 항목을 선택하세요.");
        return;
    }

    deleteCommunities(communityIdList);
});

const searchCommunityBtn = document.getElementById("communitySearchBtn");

searchCommunityBtn.addEventListener("click", function(e){
    const communityKeywordVal = document.getElementById("communityKeyword").value.trim();
    console.log("검색 키워드: "+communityKeywordVal);
    if (communityKeywordVal !== "") {
        location.href = `/admin/community/post?keyword=\${encodeURIComponent(communityKeywordVal)}`;
    } else {
        location.href = `/admin/community/post`;
    }
});

// Enter 키 이벤트 추가
document.getElementById("communityKeyword").addEventListener("keypress", function(e) {
    if (e.key === "Enter") {
        searchCommunityBtn.click();
    }
});

//전체 선택 체크박스

const selectAllCom = document.getElementById("selectAll");

selectAllCom.addEventListener("click", function(e) {
	    const checkboxes = document.querySelectorAll(".admin-checkbox");
    
    checkboxes.forEach(checkbox => {
        checkbox.checked = !checkbox.checked; // 현재 체크 상태를 반전시킴
    });
});/**
 * 
 */