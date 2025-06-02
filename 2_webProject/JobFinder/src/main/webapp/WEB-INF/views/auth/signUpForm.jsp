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
<link rel="stylesheet" href="/css/auth/googleIcon.css">

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
					<form id="signUpForm" name="signUpForm" method="post" action="/auth/signup/member" onsubmit="return submitCheck();">
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
						<div class="socialIcon">
							<div class="googleLogin">
								<form action="/oauth2/authorization/google" method="get">
									<button class="gsi-material-button" style="width:300px">
									  <div class="gsi-material-button-state"></div>
									  <div class="gsi-material-button-content-wrapper">
									    <div class="gsi-material-button-icon">
									      <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48" xmlns:xlink="http://www.w3.org/1999/xlink" style="display: block;">
									        <path fill="#EA4335" d="M24 9.5c3.54 0 6.71 1.22 9.21 3.6l6.85-6.85C35.9 2.38 30.47 0 24 0 14.62 0 6.51 5.38 2.56 13.22l7.98 6.19C12.43 13.72 17.74 9.5 24 9.5z"></path>
									        <path fill="#4285F4" d="M46.98 24.55c0-1.57-.15-3.09-.38-4.55H24v9.02h12.94c-.58 2.96-2.26 5.48-4.78 7.18l7.73 6c4.51-4.18 7.09-10.36 7.09-17.65z"></path>
									        <path fill="#FBBC05" d="M10.53 28.59c-.48-1.45-.76-2.99-.76-4.59s.27-3.14.76-4.59l-7.98-6.19C.92 16.46 0 20.12 0 24c0 3.88.92 7.54 2.56 10.78l7.97-6.19z"></path>
									        <path fill="#34A853" d="M24 48c6.48 0 11.93-2.13 15.89-5.81l-7.73-6c-2.15 1.45-4.92 2.3-8.16 2.3-6.26 0-11.57-4.22-13.47-9.91l-7.98 6.19C6.51 42.62 14.62 48 24 48z"></path>
									        <path fill="none" d="M0 0h48v48H0z"></path>
									      </svg>
									    </div>
									    <span class="gsi-material-button-contents">Google 계정으로 계속하기</span>
									    <span style="display: none;">Google 계정으로 로그인</span>
									  </div>
									</button>								
								</form>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			
		</div>
	</div>
	
	<div id="askConfirm">
	</div>
	

</body>
<script src="/js/auth/signUpForm.js"></script>
</html>