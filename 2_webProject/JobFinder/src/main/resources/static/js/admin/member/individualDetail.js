// 기업 정보 수정 폼 객체 가져오기
let memberInforSubmitFormObj = document.getElementById("memberInforSubmitForm");

// data-member-id 속성에서 memberId 가져오기
const memberId = document.getElementById("memberData").dataset.memberId;

// 초기화 시 유효성도 초기화
function resetFun() {
	$("#pwdCheckStatus").html("");
	$("#passwordCheck").removeAttr("style");
	pwdCheckPass = true;
}

// 비밀번호 변경 시 체크
var pwdCheckPass = true;
function sameCheckPwd(){
	
	pwdCheckPass = false;
	
	var pwdCheck = $("#passwordCheck").val();
	var pwd = $("#password").val();
		
	// 공백 체크
	if(pwdCheck == ""){
		noBlank = "비밀번호 확인을 입력해주세요.";
		$("#pwdCheckStatus").html(noBlank);
		$("#passwordCheck").css("border", "1px solid red");
		return;
	}
	
	// 동일한지 확인
	var pwdStatusStr = '비밀번호가 다릅니다. 동일한 비밀번호를 입력해 주세요.';
	if(pwdCheck != pwd){
		$("#pwdCheckStatus").html(pwdStatusStr);
		$("#passwordCheck").css("border", "1px solid red");
		return;
	}else{
		$("#pwdCheckStatus").html("");
		$("#passwordCheck").removeAttr("style");
		pwdCheckPass = true;
	}
	
}
// 개인회원 정보 수정 폼이 제출될 때 실행
memberInforSubmitFormObj.addEventListener("submit", function(e) {
    e.preventDefault(); // 기본 폼 제출 동작 막기 (페이지 새로고침 방지)

	if(!pwdCheckPass && $("#password").val()!=""){
		$("#passwordCheck").focus();
		return;
	}
		
	
    // 폼 데이터 FormData 객체로 수집
    const formData = new FormData(memberInforSubmitFormObj);
    const jsonData = {};

    // 빈값이 아닌 항목만 jsonData 객체에 담기
    formData.forEach((value, key) => {
        if (value.trim() !== "" && key !== "createAt" && key !== "modifiedAt") {
            jsonData[key] = value;
        }
    });

    console.log(jsonData); // 전송 전 콘솔 확인

    // PATCH 요청으로 수정된 기업 정보 전송
    fetch(`/admin/member/individual/${memberId}`, {
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
            location.href = "/admin/member/individual"; // 수정 후 목록으로 이동
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
    memberIdVal = document.getElementById("memberId").value;

    console.log(memberIdVal);

    // 요청에 포함될 JSON 데이터 구성
    const jsonData = {
        "memberId": memberIdVal
    };

    // 사용자에게 확인 요청
    const confirmed = confirm("강제탈퇴를 진행합니까?");
    if (confirmed) {
        // DELETE 요청 전송
        fetch(`/admin/member/individual/${memberId}`, {
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
                alert("회사 정보가 성공적으로 삭제되었습니다.");
                location.href = "/admin/member/individual";
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