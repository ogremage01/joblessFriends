<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="/css/member/memberMyPage.css" />
</head>
<body>
<jsp:include page="../common/header.jsp"/>

<div class="container">
    <div class="sidebar">
      <h2>
      	<a href="/member/mypage" style="text-decoration: none; color: inherit;">마이페이지</a>
      </h2>
      <ul>
        <li class="active">
        	<a href="/resume/management">이력서 관리</a>
        </li>
        <li>구직활동 내역</li>
        <li>개인정보 관리</li>
        <li>내가 찜한 공고</li>
      </ul>
    </div>

    <div class="main">
      <h1>마이페이지</h1>
    </div>
    
</div>
	
<jsp:include page="../common/footer.jsp" />
</body>
</html>