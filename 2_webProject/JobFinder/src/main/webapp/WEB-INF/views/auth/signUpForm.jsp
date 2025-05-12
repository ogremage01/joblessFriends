<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

<script src="https://code.jquery.com/jquery-3.7.1.min.js" ></script>

<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/auth/signUpForm.css">

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
						<a id="navUser" href="./signup">개인회원</a>
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
			
				<div id="signUpFormWrap">
					<form id="signUpForm" name="signUpForm" method="post" action="/auth/signup/member">
						<fieldset>
							<legend>회원가입</legend>
							<input id="email" name="email" type="email" value="" placeholder="이메일">
							<input id="password" name="password" type="password" onblur="valiCheckPwd();" value="" placeholder="비밀번호 (8자 이상/영문, 숫자 포함)">
							<input id="passwordCheck" type="password" value="" placeholder="비밀번호 확인">
							
							<button type="submit" id="signUpBtn" class="btnStyle">가입하기</button>
						</fieldset>
					</form>
				</div>
				
				<div id="goLogin">
					<div id="loginSuggestion">
						<span>
							이미 회원이신가요?
							<a href="/auth/login">로그인</a>
						</span>
					</div>
					
					<div id="socialLogin">
						<span>소셜계정으로 간편 로그인</span>
					</div>
				</div>
				
			</div>
			
		</div>
	</div>

</body>
<script src="/js/auth/signUpForm.js"></script>
</html>