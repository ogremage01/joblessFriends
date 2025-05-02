<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

<style type="text/css">
	#


</style>

</head>

<body>

	<div id="wrap">
		<div id="logoDiv" class="headBlankLeft">
			<a href="/"><img alt="어디보잡 로고" src="/img/logo.svg" /></a>
		</div>
		
		<div>
			<ul class="authNav">
				<li class="">
					<a href="">개인회원</a>
				</li>
				<li class="partitionNav">
					|
				</li>
				<li class="">
					<a href="">기업회원</a>
				</li>
			</ul>
		</div>
		
		<div id="container">
			<div id="signUpFormWrap">
				<form id="signUpForm" name="signUpForm" method="" action="">
					<fieldset>
						<legend>회원가입</legend>
						<input id="email" name="email" type="text" value="" placeholder="이메일">
						<input id="password" name="password" type="password" value="" placeholder="비밀번호">
						<input id="passwordCheck" name="passwordCheck" type="password" value="" placeholder="비밀번호 확인">
						<button type="submit" id="signUpBtn" class="btnStyle">가입하기</button>
					</fieldset>
				</form>
			</div>
			
			<div id="goLogin">
				<span>
					이미 회원이신가요?
					<a href="#">로그인</a>
				</span>
			</div>
			
			<div id="socialLogin">
				<span>소셜계정으로 간편 로그인</span>
			</div>
			
		</div>
		
	</div>

</body>
</html>