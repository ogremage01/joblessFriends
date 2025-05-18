<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" ></script>


<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/auth/findAccountForm.css">

<script type="text/javascript">

	$(document).ready(function() {
		$("#navUser").css(
			{
				"color": "black", 
			} 
		);
	}); // $(document).ready end
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
						<a id="navUser" href="./find">개인회원</a>
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
			
				<div id="findFormWrap">
					<form id="findForm" name="findForm" method="post" action="/auth/find/memberPwd">
						<fieldset>
							<legend>개인회원 계정찾기</legend>
							<input id="email" name="email" type="email" value="" placeholder="이메일">
							
							<button type="submit" id="findPwdBtn" class="btnStyle">비밀번호 찾기</button>
						</fieldset>
					</form>
				</div>
				
				<div id="goLogin">
					<a href="./login"><b>로그인</b>으로 돌아가기 〉</a>
				</div>
				
			</div>
			
		</div>
	</div>
	
	<div id="askConfirm">
	</div>

</body>
<script src="/js/auth/findAccountForm.js"></script>
</html>