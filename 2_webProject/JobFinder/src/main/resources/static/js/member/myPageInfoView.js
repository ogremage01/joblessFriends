
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



// 암호화 된 비밀번호 체크하기
function passwordExist(email, password){
	return new Promise((resolve, reject) => {
		$.ajax({
			url: '/auth/login/member',
			method: 'POST',
			data: {email:email, password:password},
			success: function(result) {
				if (result.success) {
					resolve(false);
				} else {
					resolve(true);
				}
			},
			error: function() {
				alert("서버 오류가 발생했습니다.");
				reject(false); // 오류 시 false를 reject
			},
		}); // ajax end
	});	
}

// 기존 비밀번호 검사
var oldPwdPass = false;
async function checkOldPwd(){
	oldPwdPass = false;

	var pwd = $("#oldPassword").val();
	// 공백 체크
	if(pwd == ""){
		noBlank = "비밀번호를 입력해주세요.";
		$("#oldpwdStatus").html(noBlank);
		$("#oldPassword").css("border", "1px solid red");
		oldPwdPass = false;
		return;
	}
	
	var email = $("#userEmail").text();
	var pwdStatusStr = "틀린 비밀번호입니다."
	
	try {
        // passwordExist 함수의 결과를 기다림
        var isIncorrect = await passwordExist(email, pwd);

        if (isIncorrect) {
            $("#oldpwdStatus").html(pwdStatusStr);
            $("#oldPassword").css("border", "1px solid red");
            oldPwdPass = false;
        } else {
            $("#oldpwdStatus").html("");
            $("#oldPassword").removeAttr("style");
            oldPwdPass = true;
        }
    } catch (error) {
        console.error("비밀번호 확인 오류:", error);
    }

}


// 비밀번호 유효성 검사
var pwdPass = false;
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
		pwdPass = false;
		return;
	} else{
		$("#pwdStatus").html("");
		$("#password").removeAttr("style");
		pwdPass = true;
	}
	
};



// 비밀번호 확인
var pwdCheckPass = false;
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
$('#pwdChangeForm').on('submit',async function(e) {
	e.preventDefault(); // 기본 submit 막기
	
	await checkOldPwd();
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
	
	var memberId = $("#memberId").val();
	var password = $("#password").val();
	
	$.ajax({
		url: '/member/passwordCheck',
		method: 'POST',
		data: {memberId: memberId, password: password},
		
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

//비밀번호 입력 초기화
$(".resetBtn").click(function(){
	$("#oldpwdStatus").html("");
	$("#oldPassword").removeAttr("style");
	$("#oldPassword").val("");
	
	$("#pwdStatus").html("");
	$("#password").removeAttr("style");
	$("#password").val("");
	
	$("#pwdCheckStatus").html("");
	$("#passwordCheck").removeAttr("style");
	$("#passwordCheck").val("");
});

//입력 유효성 확인	
var delPwdPass = false;
async function valiCheckPwdDel(){
	delPwdPass = false;
	var pwd = $("#passwordDel").val();
	var email = $("#userEmail").text();
	var pwdStatusStr = "틀린 비밀번호입니다."

	// 비밀번호를 비동기적으로 확인
    try {
        // passwordExist 함수가 끝날 때까지 기다림
        var isIncorrect = await passwordExist(email, pwd);

        if (isIncorrect && pwd != "") {
            $("#passwordDelStatus").html(pwdStatusStr);
            $("#passwordDel").css("border", "1px solid red");
            delPwdPass = false;
        } else {
            $("#passwordDelStatus").html("");
            $("#passwordDel").removeAttr("style");
            delPwdPass = true;
        }
    } catch (error) {
        console.error("비밀번호 검증 오류:", error);
    }
}

///////////////////모달 팝업

// 탈퇴 모달 열기
$("#delModalOpen").click(async function(){
	
	//일반 회원일 경우 비밀번호 체크
	if($("#provider").val() == 'normal'){
		var pwd = $("#passwordDel").val();
		
		// 공백 체크
		if(pwd == ""){
			var htmlStr = "비밀번호를 입력해주세요.";
			$("#passwordDelStatus").html(htmlStr);
			$("#passwordDel").css("border", "1px solid red");
			return;
		}
		
		await valiCheckPwdDel();
	
		if(!delPwdPass) return;
	}
	
	$(".popTitle").html("정말 탈퇴하시겠어요?");
	$(".popText").html("회원탈퇴 시 관련된 정보는 모두 삭제되며<br>복구가 불가능합니다.");
	$(".popCancel").html("취소");
	$(".popSubmit").html("탈퇴하기");
	$(".popSubmit").addClass("deleteId");

	$("#popup").css('display','flex').hide().fadeIn();
});

// 탈퇴 버튼
$(".popBtns").on("click", ".deleteId", function() {
	var memberId = $("#memberId").val();
	
	$.ajax({
			url: '/member/delete/'+ memberId,
			method: 'DELETE',
			data: { memberId: memberId },
			success: function(result) {
				if (result == 1) {
					alert("탈퇴되었습니다. \n지금까지 이용해주셔서 감사합니다.");
					location.href='/auth/logout';
				} else {
					alert("회원탈퇴에 실패했습니다.");
				}
			},
			error: function() {
				alert("서버 오류가 발생했습니다.");
				location.reload();
			},
		}); // ajax end	
});

var maxLength = 15;  // 최대 글자 수

// 닉네임 변경 모달 열기
$("#nickChangeBtn").click(async function(){
	
	//팝업 내용 넣기
	$(".popTitle").html("닉네임 변경");
	$(".popText").html("변경할 닉네임을 입력하세요.<span id='charCount'>(0/15)</span>");
	var userNickname = $("#userNickname").html();
	var inputHtml =`<input id="nicknameInput" type="text" value="${userNickname}" maxlength="15">`;
	$(".popContent").append(inputHtml);
	$(".popCancel").html("취소");
	$(".popSubmit").html("변경하기");
	$(".popSubmit").addClass("changeNick");
	
	var nickLength = userNickname.length;
	//글자 수 세기 기본값
	$("#charCount").text("(" + nickLength + "/"+maxLength+")");
	if(nickLength >= maxLength){
		$("#charCount").addClass("maxReached");  // 빨간색으로 변경
	}
	
	//열기
	$("#popup").css('display','flex').hide().fadeIn();
});

// 닉네임 유효성 검사
$(".popContent").on("input", "#nicknameInput", function() {
	
	var inputValue = $(this).val().replace(/\s/g, ''); // 공백 제거
	$(this).val(inputValue);
	var inputLength = inputValue.length;  // 현재 입력된 글자 수
	
	// 글자 수 초과하면 더 이상 입력되지 않음
	if (inputLength > maxLength) {
		$(this).val(inputValue.substring(0, maxLength));  // 15자 초과 시 잘라냄
		inputLength = mexLength;
	}

	// 글자 수 표시
	$("#charCount").text("(" + inputLength + "/" + maxLength + ")");

	// 글자 수가 최대에 도달하면 색상 변경
	if (inputLength >= maxLength) {
		$("#charCount").addClass("maxReached");  // 빨간색으로 변경
	} else {
		$("#charCount").removeClass("maxReached");  // 색상 원래대로
	}
});

// 닉네임 변경 버튼
$(".popBtns").on("click", ".changeNick", function() {
	var nicknameInput = $("#nicknameInput").val();
	if(nicknameInput==""){
		alert("입력된 닉네임이 없습니다.");
		return;
	}
	var memberId = $("#memberId").val();
	$.ajax({
			url: '/member/nickchange',
			method: 'POST',
			data: { nickname: nicknameInput, memberId: memberId },
			success: function(result) {
				if (result == 1) {
					alert("닉네임이 변경되었습니다.");
					location.reload();
				} else {
					alert("닉네임 변경에 실패했습니다.");
				}
			},
			error: function() {
				alert("서버 오류가 발생했습니다.");
				location.reload();
			},
		}); // ajax end	
});

// 모달팝업 닫기
function modalClose(){
	$("#popup").fadeOut(400, function() {  // 400ms로 페이드아웃, 완료 후 콜백 실행
	       // 모달 닫기 후 내용 초기화
	       $(".popTitle").html("");
	       $(".popText").html("");
	       $(".popCancel").html("");
	       $(".popSubmit").html("");
		   
		   if ($(".popSubmit").hasClass("deleteId")) {
		       $(".popSubmit").removeClass("deleteId");
		   }
		   if ($(".popSubmit").hasClass("changeNick")) {
		       $(".popSubmit").removeClass("changeNick");
		   }
		   
		   if ($("#nicknameInput").length) {
		       $("#nicknameInput").remove();
		   }
	   });
}

// 팝업에서 모달 닫기
$(".popCancel").click(function(){
	//모달 닫기 함수 호출
	modalClose();
});



