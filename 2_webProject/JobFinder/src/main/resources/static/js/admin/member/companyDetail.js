// 기업 정보 수정 폼 객체 가져오기
let companyInforSubmitFormObj = document.getElementById("companyInforSubmitForm");

// 메타 태그에서 CSRF 정보 가져오기 -활성화시 적용 예정
/*const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');*/


// data-company-id 속성에서 companyId 가져오기
const companyId = document.getElementById("companyData").dataset.companyId;

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
// 기업 정보 수정 폼이 제출될 때 실행
companyInforSubmitFormObj.addEventListener("submit", function(e) {
	e.preventDefault(); // 기본 폼 제출 동작 막기 (페이지 새로고침 방지)

	if(!pwdCheckPass && $("#password").val()!=""){
		$("#passwordCheck").focus();
		return;
	}
	
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
			'Content-Type': 'application/json',
			[csrfHeader]: csrfToken
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
				//,[csrfHeader]: csrfToken csrf 활성화시 적용예정
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
					alert("회원 정보가 성공적으로 삭제되었습니다.");
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