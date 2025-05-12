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
			<form id="signUpForm" name="signUpForm" method="post" action="/auth/signup/company">
				<fieldset>
					<legend>회원가입</legend>
					<input id="email" name="email" type="email" value="" placeholder="이메일">
					<input id="password" name="password" type="password" value="" placeholder="비밀번호 (8자 이상/영문, 숫자 포함)">
					<input id="passwordCheck" type="password" value="" placeholder="비밀번호 확인">
					
					<input id="companyName" name="companyName" type="text" value="" placeholder="기업명">
					<input id="brn" name="brn" type="text" value="" placeholder="사업자 등록번호 ('-'포함)">
					
					<input id="representative" name="representative" type="text" value="" placeholder="담당자 명">
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
