<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>adminLogin</title>
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/auth/loginForm.css">
<style type="text/css">

#authNavWrap {
	text-align: center;
}
#loginHeader {
	font-size: 24px;
	font-weight: bold;
	display: inline-block;
	margin-bottom: 20px;
}
</style>
</head>
<body>
<div id="container">
		<div id="containerWrap">
		
			<div id="logoDiv" class="headBlankLeft">
				<a href="/"><img alt="어디보잡 로고" src="/img/logo.svg" /></a>
			</div>
			
			<div id="authNavWrap">
				<span id="loginHeader">관리자 로그인</span>
			</div>
			
			<div id="content">
				<div id="loginFormWrap">
					<form id="memberLoginForm" name="loginForm" method="post" action="./login">
						<fieldset>
							<legend>로그인</legend>
							<input type="text" class="form-control" name="adminId" placeholder="아이디 입력" >
							<input type="password" class="form-control" name="password" placeholder="비밀번호 입력">
							<button type="submit" id="loginBtn" class="btnStyle">로그인</button>
							
						</fieldset>
					</form>
				</div>
			</div>
</body>
</html>