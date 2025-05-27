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
					
					<div class="inputGroup">
						<input id="companyName" name="companyName" type="text" onblur="noBlankNameFnc();" value="" placeholder="기업명" >
						<div id="nameStatus" class="valiCheckText"></div>
					</div>
					
					<div class="inputGroup">
						<input id="brn" name="brn" type="text" onblur="noBlankBrnFnc();" value="" placeholder="사업자 등록번호" >
						<div id="brnStatus" class="valiCheckText"></div>
					</div>
					
					<div class="inputGroup">
						<input id="representative" name="representative" type="text" onblur="noBlankRepFnc();" value="" placeholder="담당자 명" >
						<div id="repStatus" class="valiCheckText"></div>
					</div>
					
					<div class="inputGroup">
						<input id="tel" name="tel" type="text" onblur="noBlankTelFnc();" value="" placeholder="연락처">
						<div id="telStatus" class="valiCheckText"></div>
					</div>
					
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

var companyNamePass = false;
var brnPass = false;
var repPass = false;
var telPass = false;

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
		askConfirm("이메일을");
		return;
	}

	// 이메일 형식 체크
	if(!/^[A-Za-z0-9]+@[A-Za-z0-9]+\.[A-Za-z]{2,}$/.test(email)){
		emailStatus = "이메일 형식을 다시 확인해주세요. <br>(예시: test@email.com)"
		$("#emailStatus").html(emailStatus);
		$("#email").css("border", "1px solid red");
		askConfirm("이메일을");
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
			
			// data --> 존재 or 없음
			if(data === "존재"){
				emailStatusStr = "이미 가입된 이메일입니다.";
				$("#emailStatus").html(emailStatusStr);
				$("#email").css("border", "1px solid red");
				askConfirm("이메일을");
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
		askConfirm("비밀번호를");
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
		askConfirm("비밀번호 확인을");
	}else{
		$("#pwdStatus2").html("");
		$("#passwordCheck").removeAttr("style");
		pwdCheckPass = true;
	}
	
}

// -----------공백 체크-----------
// 기업명
function noBlankNameFnc() {
	var value = $("#companyName").val();
	
	if(value == ""){
		companyNamePass=false;
		var noBlank = "기업명을 입력해주세요.";
		$("#nameStatus").html(noBlank);
		$("#companyName").css("border", "1px solid red");
		askConfirm("기업명을");
		return;
	}else{
		$("#nameStatus").html("");
		$("#companyName").removeAttr("style");
		companyNamePass = true;
	}
}

// 사업자번호 숫자만 입력, 하이픈 자동 삽입
$(document).on('input', '#brn', function() {
	let value = $(this).val().replace(/\D/g, ''); // 숫자만 추출

	if (value.length > 3 && value.length <= 5) {
		value = value.slice(0, 3) + '-' + value.slice(3);
	} else if (value.length > 5) {
		value = value.slice(0, 3) + '-' + value.slice(3, 5) + '-' + value.slice(5, 10);
	}

	$(this).val(value);
});

// 사업자번호 유효성 검사 // onblur
function noBlankBrnFnc() {
	var value = $("#brn").val();
	
	// 공백체크
	if(value == ""){
		brnPass = false;
		var noBlank = "사업자 등록번호를 입력해주세요.";
		$("#brnStatus").html(noBlank);
		$("#brn").css("border", "1px solid red");
		askConfirm("사업자 등록번호를");
		return;
	}else{
		$("#brnStatus").html("");
		$("#brn").removeAttr("style");
	}
	
	// 형식체크
	const isValid = /^\d{3}-\d{2}-\d{5}$/.test(value);
	if (!isValid) {
		brnPass = false;
		var brnStatusStr = "형식이 올바르지 않습니다. xxx-xx-xxxxx 형식으로 입력하세요.";
		$("#brnStatus").html(brnStatusStr);
		$("#brn").css("border", "1px solid red");
		askConfirm("사업자 등록번호를");
		return;
	} else {
		$("#brnStatus").html("");
		$("#brn").removeAttr("style");
		brnPass = true;
	}
}

// 담당자 명
function noBlankRepFnc() {
	var value = $("#representative").val();
	
	if(value == ""){
		repPass = false;
		var noBlank = "담당자명을 입력해주세요.";
		$("#repStatus").html(noBlank);
		$("#representative").css("border", "1px solid red");
		askConfirm("담당자명을");
		return;
	}else{
		$("#repStatus").html("");
		$("#representative").removeAttr("style");
		repPass = true;
	}
}

// 연락처 하이픈 자동 삽입
$(document).on('input', '#tel', function() {
	let raw = $(this).val().replace(/\D/g, ''); // 숫자만 추출
	let formatted = '';

	if (raw.startsWith('02')) {
		// 2자리 지역번호 (서울)
		if (raw.length <= 2) {
			formatted = raw;
		} else if (raw.length <= 5) {
			formatted = raw.slice(0, 2) + '-' + raw.slice(2);
		} else {
			const mid = raw.length - 4 >= 3 ? raw.length - 4 : raw.length - 3;
			formatted = raw.slice(0, 2) + '-' + raw.slice(2, mid) + '-' + raw.slice(mid);
		}
	}
	else if (/^0\d{2}/.test(raw)) {
		// 3자리 지역번호 또는 휴대폰 번호
		if (raw.length <= 3) {
			formatted = raw;
		} else if (raw.length <= 7) {
			formatted = raw.slice(0, 3) + '-' + raw.slice(3);
		} else {
			const mid = raw.length - 4 >= 3 ? raw.length - 4 : raw.length - 3;
			formatted = raw.slice(0, 3) + '-' + raw.slice(3, mid) + '-' + raw.slice(mid);
		}
	}
	else {
		// 그 외 번호는 그냥 숫자만 보여주기
		formatted = raw;
	}

	$(this).val(formatted);
});

// 연락처 유효성 검사
function noBlankTelFnc() {
	var value = $("#tel").val();
	
	// 공백 체크
	if(value == ""){
		telPass = false;
		var noBlank = "연락처를 입력해주세요.";
		$("#telStatus").html(noBlank);
		$("#tel").css("border", "1px solid red");
		askConfirm("연락처를");
		return;
	}else{
		$("#telStatus").html("");
		$("#tel").removeAttr("style");
	}
	
	// 한국 전화번호 정규식 (02, 0505, 010 등)
	const phoneRegex = /^(0\d{1,3})-(\d{3,4})-(\d{4})$/;

	if (!phoneRegex.test(value)) {
		telPass = false;
		var telStatusStr = "유효하지 않은 전화번호입니다. 예: 010-1234-5678";
		$("#telStatus").html(telStatusStr);
		$("#tel").css("border", "1px solid red");
		askConfirm("연락처를");
	} else {
		$("#telStatus").html("");
		$("#tel").removeAttr("style");
		telPass = true;
	}
}

// ----------------------

// 유효성 검사 토스트 팝업 함수
function askConfirm(check){
	var Str = check + " 다시 확인해주세요."
	$('#askConfirm').html(Str)
	$('#askConfirm').attr('class','active');
	setTimeout(function(){
		$('#askConfirm').removeClass("active");
    },1500);
}


// 유효한 폼만 submit 가능
function submitCheck(){
	
	
	noBlankTelFnc();
	noBlankRepFnc();
	noBlankBrnFnc();
	noBlankNameFnc();

	sameCheckPwd();
	valiCheckPwd();
	valiCheckEmail();
	
	if(!pwdPass) {
		return false;
	};
	
	if(!pwdCheckPass) {
		askConfirm("비밀번호 확인을");
		return false;
	};
	
	//기업회원 인풋 체크
	var formAction = $("#signUpForm").attr("action");
	var actionCompany = '/auth/signup/company';
	if(formAction == actionCompany){
		
		if(!companyNamePass) {
			return false;
		};
		
		if(!brnPass) {
			return false;
		};
		
		if(!repPass) {
			return false;
		};
		
		if(!telPass) {
			return false;
		};
	}
	
	return true;
}

