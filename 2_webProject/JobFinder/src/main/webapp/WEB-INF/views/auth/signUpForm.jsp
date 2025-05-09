<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

<script src="https://code.jquery.com/jquery-3.7.1.min.js" ></script>

<link rel="stylesheet" href="/css/common/common.css">
<style type="text/css">

	/*		로고		*/
	#logoDiv {
		text-align: center;
		margin-bottom: 50px;
	}
	
	/* 	개인회원 | 기업회원  */
	
	.authNav {
		list-style:none;
		display: flex;
	    align-items: center;
	    justify-content: center;
	    column-gap: 30px;
	    padding: 0px;
	    margin: 20px auto;
	}
	
	.authNavItem a {
		text-decoration: none;
		color: #D4D4D4;
		font-size: 20px;
	}
	
	.partitionNav {
		font-size: 15px;
		color: #D4D4D4;
		user-select: none;
	}

	
	
	/* 가입 폼 */
	
	#signUpForm {
		width: 300px;
		margin: auto;
	}
	
	#signUpForm fieldset {
		border: 0px;
		padding: 0;
		margin: 0;
		display: flex;
		gap: 10px;
		flex-direction: column;
	}
	
	#signUpForm legend {
		display: none;
	}
	
	#signUpForm input {
		height: 38px;
		padding: 5px 15px;
		border: 1px solid #E0E0E0;
		outline: none;
		border-radius: 8px;
		font-size: 16px;
	}
	
	#signUpForm input:hover{
	border:1px solid #C9C9C9;
	}
	
	#signUpForm input:focus {
		border: 1px solid #F69800;

	}
	
	
	
	/* 	버튼  */
	.btnStyle {
		border: none;
		background-color: #F69800;
		color: white;
	}
	
	#signUpBtn {
		height: 50px;
		border-radius: 8px;
		font-size: 18px;
		font-weight: bold;
		cursor: pointer;
	}
	
	#signUpBtn:hover {
		background-color: #DE8100;
	}
	
	
	#goLogin {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	
	#goLogin span {
		color:#898989;
		font-size: 12px;
	}
	
	#loginSuggestion a{
		text-decoration: none;
		font-weight: bold;
		color: #898989;
	}
	


	/* 공통단 css 수정 */
	#container #containerWrap{
   		width: 300px;
   		margin-top: 0px;
   		padding-top: 100px;
    }
    
</style>


<script type="text/javascript">

	$(document).ready(function() {
		

		$("#navUser").css(
			{
				"color": "black", 
			} 
		);
		
		$("#navCompany").click(function(event) {
			console.log("클릭실행");
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
					<input id="password" name="password" type="password" value="" placeholder="비밀번호">
					<input id="passwordCheck" type="password" value="" placeholder="비밀번호 확인">
					
					<input id="companyName" name="companyName" type="text" value="" placeholder="기업명">
					<input id="brn" name="brn" type="text" value="" placeholder="사업자 등록번호">
					
					<input id="representative" name="representative" type="text" value="" placeholder="담당자 명">
					<input id="tel" name="tel" type="text" value="" placeholder="연락처">
					
					<button type="submit" id="signUpBtn" class="btnStyle">가입하기</button>
				</fieldset>
			</form>
			`;
			
			$('#signUpFormWrap').html(htmlStr);
			
		}); // navCompany end
		
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
							<input id="password" name="password" type="password" value="" placeholder="비밀번호">
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
</html>