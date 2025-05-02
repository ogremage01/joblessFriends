<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

<style type="text/css">
	
	.authNav {
		list-style:none;
		display: flex;
	    align-items: center;
	    column-gap: 30px;
	    padding: 0px;
	}
	
	.authNavNavItem a {
		text-decoration: none;
		color: #D4D4D4;
		font-size: 18px;
	}
	
	.partitionNav {
		font-size: 15px;
		color: #D4D4D4;
		user-select: none;
	}
	
	.user a{
		color: black;
		font-weight: bold;
	}
	
	
	
	#signUpForm {
		width: 280px;
	}
	
	#signUpForm fieldset {
		border: 0px;
		padding: 0;
		
		display: flex;
		flex-direction: column;
	}
	
	#signUpForm legend {
		display: none;
	}
	
	#signUpForm input {
		height: 40px;
		padding: 5px 10px;
		border: 1px solid #CECECE;
		border-radius: 8px;
	}
	
	.btnStyle {
		border: none;
		background-color: #F69800;
		color: white;
		padding: 
	}
	
	#signUpBtn {
		height: 40px;
		border-radius: 8px;
		font-size: 18px;
		font-weight: bold;
		
	}
	
	
	#goLogin span {
		color:#898989;
		font-size: 12px;
	}

</style>

</head>

<body>

	<div id="wrap">
		<div id="logoDiv" class="headBlankLeft">
			<a href="/"><img alt="어디보잡 로고" src="/img/logo.svg" /></a>
		</div>
		
		<div id="authNavWrap">
			<ul class="authNav">
				<li class="authNavNavItem user">
					<a href="">개인회원</a>
				</li>
				<li class="authNavNavItem partitionNav">
					|
				</li>
				<li class="authNavNavItem company">
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
				<div id="loginSuggestion">
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
		
	</div>

</body>
</html>