
// 복사 팝업
$("#copyBtn").click(function(){
	console.log("복사 실행")
	var text = $("#userEmail").text();
	navigator.clipboard.writeText(text);
	copyPop("복사되었습니다.");
});

function copyPop(msg) {
	$('#copyPop').html(msg);
	$('#copyPop').attr('class', 'active');
	setTimeout(function() {
		$('#copyPop').removeClass('active');
	}, 1200);
}


var oldPwdPass = false;

// 기존 비밀번호 검사
function checkOldPwd(){
	oldPwdPass = false;

	var pwd = $("#oldPassword").val();
	// 공백 체크
	if(pwd == ""){
		noBlank = "비밀번호를 입력해주세요.";
		$("#oldpwdStatus").html(noBlank);
		$("#oldPassword").css("border", "1px solid red");
		return;
	}
	
	var userPwd = $("#userPassword").val();
	
	var pwdStatusStr = "틀린 비밀번호입니다."
	if(pwd != userPwd){
		$("#oldpwdStatus").html(pwdStatusStr);
		$("#oldPassword").css("border", "1px solid red");
		return;
	}else{
		$("#oldpwdStatus").html("");
		$("#oldPassword").removeAttr("style");
		oldPwdPass = true;
	}

}

var pwdPass = false;

// 비밀번호 유효성 검사
function valiCheckPwd(){
	
	pwdPass = false;
	
	var pwd = $("#password").val();
	
	// 공백 체크
	if(pwd == ""){
		noBlank = "새 비밀번호를 입력해주세요.";
		$("#pwdStatus").html(noBlank);
		$("#password").css("border", "1px solid red");
		return;
	}
	
	//조건 불충족 시 메시지가 출력
	var pwdStatusStr = '비밀번호는 영문, 숫자를 모두 포함하고 8자리 이상이어야 합니다.';
	if(!(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(pwd))){
		$("#pwdStatus").html(pwdStatusStr);
		$("#password").css("border", "1px solid red");
		return;
	} else{
		$("#pwdStatus").html("");
		$("#password").removeAttr("style");
		pwdPass = true;
	}
	
};


var pwdCheckPass = false;

// 비밀번호 확인
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
	var pwdStatus2Str = '비밀번호가 다릅니다. 동일한 비밀번호를 입력해 주세요.';
	if(pwdCheck != pwd){
		$("#pwdCheckStatus").html(pwdStatus2Str);
		$("#passwordCheck").css("border", "1px solid red");
		return;
	}else{
		$("#pwdCheckStatus").html("");
		$("#passwordCheck").removeAttr("style");
		pwdCheckPass = true;
	}
	
}

// 비밀번호 변경 폼 submit
$('#pwdChangeForm').on('submit', function(e) {
	e.preventDefault(); // 기본 submit 막기
	
	checkOldPwd();
	sameCheckPwd();
	valiCheckPwd();
	
	if(!oldPwdPass) {
		$("#oldPassword").focus();
		return false;
	};
	
	if(!pwdPass) {
		$("#password").focus();
		return false;
	};
	
	if(!pwdCheckPass) {
		$("#passwordCheck").focus();
		return false;
	};
	

	$.ajax({
		url: '/member/passwordCheck',
		method: 'POST',
		data: $(this).serialize(),
		
		// ✅ Ajax 요청 직전에 버튼 비활성화
		beforeSend: function() {
			$('#changeBtn').attr("disabled", true);
		},
		
		success: function(result) {
			if (result == 1) {
				alert("비밀번호가 변경되었습니다.");
				location.reload();
			} else {
				alert("비밀번호에 변경에 실패했습니다.");
			}
		},
		error: function() {
			alert("서버 오류가 발생했습니다.");
		},
		
		// ✅ 요청 완료 후 버튼 다시 활성화
		complete: function() {
			$('#changeBtn').attr("disabled", false);
		}
		
	}); // ajax end	
	
})

