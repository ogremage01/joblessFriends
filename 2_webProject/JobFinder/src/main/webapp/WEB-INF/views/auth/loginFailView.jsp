<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

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
	
	.authNavNavItem a {
		text-decoration: none;
		color: #D4D4D4;
		font-size: 20px;
	}
	
	.partitionNav {
		font-size: 15px;
		color: #D4D4D4;
		user-select: none;
	}

	/* 	선택 시 스타일 변경 */
	.user a{
		color: black;
		font-weight: bold;
	}
	
	
	
	/* 가입 폼 */
	
	#loginForm {
		width: 300px;
		margin: auto;
	}
	
	#loginForm fieldset {
		border: 0px;
		padding: 0;
		margin: 0;
		display: flex;
		gap: 10px;
		flex-direction: column;
	}
	
	#loginForm legend {
		display: none;
	}
	
	#loginForm input {
		height: 38px;
		padding: 5px 15px;
		border: 1px solid #E0E0E0;
		outline: none;
		border-radius: 8px;
		font-size: 16px;
	}
	
	#loginForm input:hover{
	border:1px solid #C9C9C9;
	}
	
	#loginForm input:focus {
		border: 1px solid #F69800;

	}
	
	
	
	/* 	버튼  */
	.btnStyle {
		border: none;
		background-color: #F69800;
		color: white;
	}
	
	#loginBtn {
		height: 50px;
		border-radius: 8px;
		font-size: 18px;
		font-weight: bold;
		cursor: pointer;
	}
	
	#loginBtn:hover {
		background-color: #DE8100;
	}
	
	
	#goLogin {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	
	/* 이미 회원이신가요? */
	#goLogin span {
		color:#898989;
		font-size: 12px;
	}



	/* 공통단 css 수정 */
	#container #containerWrap{
   		width: 300px;
   		margin-top: 0px;
   		padding-top: 100px;
    }
    
</style>


<script type="text/javascript">


</script>

</head>

<body>

	<div id="container">
		<div id="containerWrap">
		
			<div id="logoDiv" class="headBlankLeft">
				<a href="/"><img alt="어디보잡 로고" src="/img/logo.svg" /></a>
			</div>
			
			로그인 실패
			
		</div>
	</div>

</body>
</html>