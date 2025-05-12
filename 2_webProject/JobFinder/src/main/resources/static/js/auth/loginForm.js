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
			<form id="loginForm" name="loginForm" method="post" action="/auth/login/company">
				<fieldset>
				<legend>로그인</legend>
				<input id="email" name="email" type="email" value="" placeholder="이메일">
				<input id="password" name="password" type="password" value="" placeholder="비밀번호">
				
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