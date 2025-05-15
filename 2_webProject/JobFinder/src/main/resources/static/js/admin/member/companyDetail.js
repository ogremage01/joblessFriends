// 기업 정보 수정 폼 객체 가져오기
let companyInforSubmitFormObj = document.getElementById("companyInforSubmitForm");

// data-company-id 속성에서 companyId 가져오기
const companyId = document.getElementById("companyData").dataset.companyId;

// 기업 정보 수정 폼이 제출될 때 실행
companyInforSubmitFormObj.addEventListener("submit", function(e) {
    e.preventDefault(); // 기본 폼 제출 동작 막기 (페이지 새로고침 방지)

    // 폼 데이터 FormData 객체로 수집
    const formData = new FormData(companyInforSubmitFormObj);
    const jsonData = {};

    // 빈값이 아닌 항목만 jsonData 객체에 담기
    formData.forEach((value, key) => {
        if (value.trim() !== "") {
            jsonData[key] = value;
        }
    });

    console.log(jsonData); // 전송 전 콘솔 확인

    // PATCH 요청으로 수정된 기업 정보 전송
    fetch(`/admin/member/company/${companyId}`, {
        method: 'PATCH', // RESTful하게 PATCH 사용
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonData) // JSON 형식으로 변환
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("서버 오류: " + response.status);
        }
        return response.text();
    })
    .then(data => {
        if (data === "1") {
            alert("수정 성공");
            location.href = "/admin/member/company"; // 수정 후 목록으로 이동
        } else {
            alert("수정 실패");
        }
    })
    .catch(error => {
        alert("수정 실패");
        console.error("에러 발생:", error);
    });
});

// ------------------------- 기업 강퇴 -------------------------

// 강제 삭제 버튼 가져오기
deleteBtn = document.getElementById("delete");

deleteBtn.addEventListener("click", function(e) {
    // 삭제할 회사 ID 가져오기
    companyIdVal = document.getElementById("companyId").value;

    console.log(companyIdVal);

    // 요청에 포함될 JSON 데이터 구성
    const jsonData = {
        "companyId": companyIdVal
    };

    // 사용자에게 확인 요청
    const confirmed = confirm("강제탈퇴를 진행합니까?");
    if (confirmed) {
        // DELETE 요청 전송
        fetch(`/admin/member/company/${companyId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("서버 오류: " + response.status);
            }
            return response.text();
        })
        .then(data => {
            if (data === "1") {
                alert("삭제 성공");
                location.href = "/admin/member/company";
            } else {
                alert("삭제 실패");
            }
        })
        .catch(error => {
            alert("삭제 실패");
            console.error("에러 발생:", error);
        });
    }
});