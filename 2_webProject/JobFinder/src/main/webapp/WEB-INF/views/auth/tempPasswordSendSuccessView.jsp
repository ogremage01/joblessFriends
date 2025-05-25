<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>임시비밀번호 발급완료</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=check_circle" />

<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/auth/signUpSuccessView.css">

</head>

<body>

	<div id="container">
		<div id="containerWrap" style="width: 400px; font-size: 22px;">
		
			<div id="logoDiv" class="headBlankLeft">
				<a href="/"><img alt="어디보잡 로고" src="/img/logo.svg" /></a>
			</div>
			
			<span class="material-symbols-rounded">check_circle</span>
			<p class="successinfo">가입된 이메일로<br><b>임시 비밀번호</b>가 발송되었습니다</p>
			
			<button class="goLoginBtn" onclick="location.href='/auth/login'">로그인 바로가기</button>
		</div>
	</div>

</body>
</html>