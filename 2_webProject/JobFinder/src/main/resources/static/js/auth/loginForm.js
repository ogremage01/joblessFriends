
// 기업회원 전환
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

	let formHtmlStr = `
			<form id="companyLoginForm" name="loginForm" method="post" action="/auth/login/company" >
				<fieldset>
				<legend>로그인</legend>
				<input id="email" name="email" type="email" value="" placeholder="이메일" >
				<input id="password" name="password" type="password" value="" placeholder="비밀번호" >
				
				<button type="submit" id="loginBtn" class="btnStyle">로그인</button>
			</fieldset>
			</form>
			`;

	$("#loginFormWrap").html(formHtmlStr);

	let deleteSocialLogin = `
				<div id="account">
					<ul class="accountNav">
						<li class="accountItem findAccount">
							<a id="findAccount" href="./find">아이디/비밀번호 찾기</a>
						</li>
						<li class="accountItem partitionNav">
							|
						</li>
						<li class="accountItem signup">
							<a id="goSignUp" href="/auth/signup">회원가입</a>
						</li>
					</ul>
				</div>
			`;

	$('#goLogin').html(deleteSocialLogin);

}); // navCompany end

// 개인회원 로그인 ajax
$('#memberLoginForm').on('submit', function(e) {
	e.preventDefault(); // 기본 submit 막기

	if ($("#email").val() == "") {
		askConfirm("이메일을");
		return false;
	}
	if ($("#password").val() == "") {
		askConfirm("비밀번호를");
		return false;
	}
		
	$.ajax({
		url: '/auth/login/member',
		method: 'POST',
		data: $(this).serialize(),
		
		// ✅ Ajax 요청 직전에 버튼 비활성화
		beforeSend: function() {
			$('#loginBtn').attr("disabled", true);
		},
		
		success: function(response) {
			if (response.success) {
				window.location.href = '/'; // 로그인 성공 시 메인으로 이동
			} else {
				loginFailPop(response.message);
			}
		},
		error: function() {
			loginFailPop("서버 오류가 발생했습니다.");
		},
		
		// ✅ 요청 완료 후 버튼 다시 활성화
		complete: function() {
			$('#loginBtn').attr("disabled", false);
		}
		
	}); // ajax end
});

// 기업회원 로그인 ajax
$(document).on('submit', '#companyLoginForm', function(e){
	e.preventDefault(); // 기본 submit 막기

	if ($("#email").val() == "") {
		askConfirm("이메일을");
		return false;
	}
	if ($("#password").val() == "") {
		askConfirm("비밀번호를");
		return false;
	}
		
	$.ajax({
		url: '/auth/login/company',
		method: 'POST',
		data: $(this).serialize(),
		
		// ✅ Ajax 요청 직전에 버튼 비활성화
		beforeSend: function() {
			$('#loginBtn').attr("disabled", true);
		},
		
		success: function(response) {
			if (response.success) {
				window.location.href = '/'; // 로그인 성공 시 메인으로 이동
			} else {
				loginFailPop(response.message);
			}
		},
		error: function() {
			loginFailPop("서버 오류가 발생했습니다.");
		},
		
		// ✅ 요청 완료 후 버튼 다시 활성화
		complete: function() {
			$('#loginBtn').attr("disabled", false);
		}
		
	}); //ajax end
});

//로그인 실패 시 토스트 팝업
function loginFailPop(msg) {
	$('#askConfirm').html(msg);
	$('#askConfirm').attr('class', 'active');
	setTimeout(function() {
		$('#askConfirm').removeClass('active');
	}, 1500);
}

// 유효성 검사 토스트 팝업
function askConfirm(check) {
	var Str = check + " 입력하세요."
	$('#askConfirm').html(Str)
	$('#askConfirm').attr('class', 'active');
	setTimeout(function() {
		$('#askConfirm').removeClass("active");
	}, 1500);
}



