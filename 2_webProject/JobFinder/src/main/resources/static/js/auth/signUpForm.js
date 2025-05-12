$("#navCompany").click(function(event) {
	console.log("기업회원 전환");
	event.preventDefault();
	$("#navUser").css(
		{
			"color": "#D4D4D4",
		}
	);
	$("#navCompany").css(
		{
			"color": "black",
		}
	);

	let htmlStr = `
			<form id="signUpForm" name="signUpForm" method="post" action="/auth/signup/company" onsubmit="return submitCheck();">
				<fieldset>
					<legend>회원가입</legend>

					<div class="inputGroup">
						<input id="email" name="email" type="email" onblur="valiCheckEmail();" value="" placeholder="이메일" >
						<div id="emailStatus" class="valiCheckText"></div> <!-- 이메일 중복 여부 메시지가 표시될 곳 -->
					</div>
					
					<div class="inputGroup">
						<input id="password" name="password" type="password" onblur="valiCheckPwd();" onkeyup="sameCheckPwd();" value="" placeholder="비밀번호 (8자 이상/영문, 숫자 포함)" >
						<div id="pwdStatus" class="valiCheckText"></div> <!-- 비밀번호 확인 메시지가 표시될 곳 -->
					</div>
					
					<div class="inputGroup">
						<input id="passwordCheck" type="password" onblur="sameCheckPwd();" value="" placeholder="비밀번호 확인" >
						<div id="pwdStatus2" class="valiCheckText"></div> <!-- 비밀번호 확인 메시지가 표시될 곳 -->
					</div>
					
					<input id="companyName" name="companyName" type="text" value="" placeholder="기업명" >
					<input id="brn" name="brn" type="text" value="" placeholder="사업자 등록번호 ('-'포함)" >
					
					<input id="representative" name="representative" type="text" value="" placeholder="담당자 명" >
					<input id="tel" name="tel" type="text" value="" placeholder="연락처 ('-'포함)">
					
					<button type="submit" id="signUpBtn" class="btnStyle">가입하기</button>
				</fieldset>
			</form>
			`;

	$('#signUpFormWrap').html(htmlStr);

	let deleteSocialLogin = `
				<div id="loginSuggestion">
					<span>
						이미 회원이신가요?
						<a href="/auth/login">로그인</a>
					</span>
				</div>
			`;

	$('#goLogin').html(deleteSocialLogin);

}); // navCompany end


// 유효 체크 변수
var emailPass = false;
var pwdPass = false;
var pwdCheckPass = false;

// 이메일 유효성 검사
function valiCheckEmail(){
	
	console.log("valiCheckEmail 실행");
	emailPass=false;
	
	var email = $("#email").val();
	var emailStatus = "";
	
	// 공백 체크
	if(email == ""){
		noBlank = "이메일을 입력해주세요.";
		$("#emailStatus").html(noBlank);
		$("#email").css("border", "1px solid red");
		return;
	}

	// 이메일 형식 체크
	if(!/^[A-Za-z0-9]+@[A-Za-z0-9]+\.[A-Za-z]{2,}$/.test(email)){
		emailStatus = "이메일 형식을 다시 확인해주세요. <br>(예시: test@email.com)"
		$("#emailStatus").html(emailStatus);
		$("#email").css("border", "1px solid red");
		return;
	}
	
	// 중복확인
	var formAction = $("#signUpForm").attr("action");
	var actionMember = '/auth/signup/member';
	var url = '';
	
	//폼의 action속성에 따라 url을 다르게 지정
	if(formAction == actionMember){
		url = '/auth/check/member/email';
	}else{
		url = '/auth/check/company/email';
	};
	console.log("URL: " + url);
	
	$.ajax({
		type: 'post',
		url: url,
		data: { email: email },
		dataType: 'text',
		success: function(data) {
			console.log("이메일 중복확인 성공");
			
			// data --> 중복 or 사용가능
			if(data === "중복"){
				emailStatusStr = "이미 가입된 이메일입니다.";
				$("#emailStatus").html(emailStatusStr);
				$("#email").css("border", "1px solid red");
			}else{
				$("#emailStatus").html("");
				$("#email").removeAttr("style");
				emailPass = true;
			}
			
		},
		error: function(xhr, status, error) {
			console.log("error");
		}
	}); // ajax end
	
};

// 비밀번호 유효성 검사
function valiCheckPwd(){
	
	pwdPass = false;
	
	var pwd = $("#password").val();
	
	// 공백 체크
	if(pwd == ""){
		noBlank = "비밀번호를 입력해주세요.";
		$("#pwdStatus").html(noBlank);
		$("#password").css("border", "1px solid red");
		return;
	}
	
	//조건 불충족 시 메시지가 출력
	var pwdStatusStr = '비밀번호는 영문, 숫자를 모두 포함하고 8자리 이상이어야 합니다.';
	if(!(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(pwd))){
		$("#pwdStatus").html(pwdStatusStr);
		$("#password").css("border", "1px solid red");
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
		
	// 공백 체크
	if(pwdCheck == ""){
		noBlank = "비밀번호를 입력해주세요.";
		$("#pwdStatus2").html(noBlank);
		$("#passwordCheck").css("border", "1px solid red");
		return;
	}
	
	// 동일한지 확인
	var pwdStatus2Str = '비밀번호가 다릅니다. 동일한 비밀번호를 입력해 주세요.';
	if(pwdCheck != pwd){
		$("#pwdStatus2").html(pwdStatus2Str);
		$("#passwordCheck").css("border", "1px solid red");
	}else{
		$("#pwdStatus2").html("");
		$("#passwordCheck").removeAttr("style");
		pwdCheckPass = true;
	}
	
}

// 유효한 폼만 submit 가능
function submitCheck(){
	if(!emailPass || !pwdPass || !pwdCheckPass) return false;
	
	return true;
}

