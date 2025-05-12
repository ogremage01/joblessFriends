<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" ></script>


<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/auth/loginForm.css">

<script type="text/javascript">

	$(document).ready(function() {
		$("#navUser").css(
			{
				"color": "black", 
			} 
		);
	});

</script>

</head>

<body>

	<div id="container">
		<div id="containerWrap">
		
			<div id="logoDiv" class="headBlankLeft">
				<a href="/"><img alt="어디보잡 로고" src="/img/logo.svg" /></a>
			</div>
			
			<div id="authNavWrap">
				<ul class="authNav">
					<li class="authNavItem user">
						<a id="navUser" href="./login">개인회원</a>
					</li>
					<li class="authNavItem partitionNav">
						|
					</li>
					<li class="authNavItem company">
						<a id="navCompany" href="#">기업회원</a>
					</li>
				</ul>
			</div>
			
			<div id="content">
			
				<div id="loginFormWrap">
					<form id="loginForm" name="loginForm" method="post" action="/auth/login/member">
						<fieldset>
							<legend>로그인</legend>
							<input id="email" name="email" type="email" value="" placeholder="이메일">
							<input id="password" name="password" type="password" value="" placeholder="비밀번호">
							
							<button type="submit" id="loginBtn" class="btnStyle">로그인</button>
						</fieldset>
					</form>
				</div>
				
				<div id="goLogin">
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
					
					<div id="socialLogin">
						<span>소셜계정으로 간편 로그인</span>
					</div>
				</div>
				
			</div>
			
		</div>
	</div>

</body>
<script src="/js/auth/loginForm.js"></script>
</html>