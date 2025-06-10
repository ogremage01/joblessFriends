
<%@ page language='java' contentType='text/html; charset=UTF-8'
         pageEncoding='UTF-8'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>error</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js" 
    integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" 
    crossorigin="anonymous"></script>
    
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=error" />
    <link rel="stylesheet" href="/css/common/common.css">
	<link rel="stylesheet" href="/css/auth/signUpSuccessView.css">
	
</head>

<body>

	<div id="container">
		<div id="containerWrap">
		
			<div id="logoDiv" class="headBlankLeft">
				<a href="/"><img alt="어디보잡 로고" src="/img/logo.svg" /></a>
			</div>
			
			<span class="material-symbols-outlined material-symbols-rounded">error</span>
			<p class="successinfo"><b>오류</b>가 <b>발생했습니다</b></p>
			
			<button class="goLoginBtn" onclick="location.href='/'">메인페이지로 이동</button>
		</div>
	</div>



</body>
</html>