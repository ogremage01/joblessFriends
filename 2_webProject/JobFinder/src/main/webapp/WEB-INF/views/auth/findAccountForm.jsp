<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
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
	
	#findForm {
		width: 300px;
		margin: auto;
	}
	
	#findForm fieldset {
		border: 0px;
		padding: 0;
		margin: 0;
		display: flex;
		gap: 10px;
		flex-direction: column;
	}
	
	#findForm legend {
		display: none;
	}
	
	#findForm input {
		height: 38px;
		padding: 5px 15px;
		border: 1px solid #E0E0E0;
		outline: none;
		border-radius: 8px;
		font-size: 16px;
	}
	
	#findForm input:hover{
	border:1px solid #C9C9C9;
	}
	
	#findForm input:focus {
		border: 1px solid #F69800;

	}
	
	
	
	/* 	버튼  */
	.btnStyle {
		border: none;
		background-color: #F69800;
		color: white;
		
		height: 50px;
		border-radius: 8px;
		font-size: 18px;
		font-weight: bold;
		cursor: pointer;
	}
	
	.companyBtn {
		width: 145px;
		font-size: 16px;
	}
	
	#loginBtn:hover {
		background-color: #DE8100;
	}
	
	
	#goLogin {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	
	#goLogin a {
		color:#898989;
		font-size: 12px;
		text-decoration: none;
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
			
			let formHtmlStr = `
				<form id="findForm" name="findForm" method="post" action="/auth/">
					<fieldset>
						<legend>기업회원 계정찾기</legend>
						<input id="representative" name="representative" type="text" value="" placeholder="담당자 명">
						<input id="brn" name="brn" type="text" value="" placeholder="사업자 등록번호">
						
						<div style="display: flex; gap: 10px;">
							<button type="button" id="findIdBtn" class="btnStyle companyBtn" onclick="findCompanyId();">아이디 찾기</button>
							<button type="submit" id="findPwdBtn" class="btnStyle companyBtn">비밀번호 찾기</button>
						</div>	
					</fieldset>
				</form>
			`;
			
			$('#findFormWrap').html(formHtmlStr);
			
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
					<form id="findForm" name="findForm" method="post" action="/auth/login/member">
						<fieldset>
							<legend>개인회원 계정찾기</legend>
							<input id="email" name="email" type="email" value="" placeholder="이메일">
							
							<button type="submit" id="findPwdBtn" class="btnStyle">비밀번호 찾기</button>
						</fieldset>
					</form>
				</div>
				
				<div id="goLogin">
					<a href="./login"><b>로그인</b>으로 돌아가기</a>
				</div>
				
			</div>
			
		</div>
	</div>

</body>
</html>