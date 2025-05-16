// 기업 정보 수정 폼 객체 가져오기
let companyInforSubmitFormObj = document.getElementById("companyInforSubmitForm");

// data-company-id 속성에서 companyId 가져오기
const companyId = document.getElementById("companyData").dataset.companyId;

// 기업 정보 수정 폼이 제출될 때 실행
companyInforSubmitFormObj.addEventListener("submit", function(e) {
    e.preventDefault(); // 기본 폼 제출 동작 막기 (페이지 새로고침 방지)

	const passwordVal = document.getElementById("password").value;
	const passwordCheckVal = document.getElementById("passwordCheck").value;
	
/*	if(passwordVal!==passwordCheckVal){
		return alert("비밀번호를 다시확인해주세요");
	}
	*/
	
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
    fetch(`/company/info`, {
        method: 'PATCH',
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
            location.reload(); // 수정 후 목록으로 이동
        } else {
            alert("수정 실패");
        }
    })
    .catch(error => {
        alert("수정 실패");
        console.error("에러 발생:", error);
    });
});

// ------------------------- 기업 탈퇴 -------------------------

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
    const confirmed = confirm("탈퇴를 진행합니까?");
    if (confirmed) {
        // DELETE 요청 전송
        fetch(`/company/info/${companyId}`, {
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
                alert("탈퇴 성공");
                location.href = "/company/delete";
            } else {
                alert("탈퇴 실패");
            }
        })
        .catch(error => {
            alert("탈퇴 실패");
            console.error("에러 발생:", error);
        });
    }
});

/*--------우편번호 찾기(다음API)*/

    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postalCodeId').value = data.zonecode;
                document.getElementById("arenaName").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("address").focus();
            }
        }).open();
    }
	
	/*비밀번호 동일여부 체크*/
	var pwdPass = false;
	var pwdCheckPass = false;
	
	
	// 비밀번호 유효성 검사
	function valiCheckPwd(){
		
		pwdPass = false;
		
		var pwd = $("#password").val();
		
		if(pwd==""){
			$("#pwdStatus").html("");
			$("#password").removeAttr("style");
			pwdPass = true;
			return;
		}

		//조건 불충족 시 메시지가 출력
		var pwdStatusStr = '비밀번호는 영문, 숫자를 모두 포함하고 8자리 이상이어야 합니다.';
		if(!(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(pwd))){
			$("#pwdStatus").html(pwdStatusStr);
			$("#password").css("border", "1px solid red");
			askConfirm("비밀번호를");
		} else{
			$("#pwdStatus").html("");
			$("#password").removeAttr("style");
			pwdPass = true;
		}
		
	};

	// 비밀번호 확인
	function sameCheckPwd(){
		
		pwdCheckPass = false;
		
		var pwdCheck = $("#passwordCheck").val();
		var pwd = $("#password").val();
		
		// 동일한지 확인
		var pwdStatus2Str = '비밀번호가 다릅니다. 동일한 비밀번호를 입력해 주세요.';
		if(pwdCheck != pwd){
			$("#pwdStatus2").html(pwdStatus2Str);
			$("#passwordCheck").css("border", "1px solid red");
			askConfirm("비밀번호 확인을");
		}else{
			$("#pwdStatus2").html("");
			$("#passwordCheck").removeAttr("style");
			pwdCheckPass = true;
		}
		
	}
