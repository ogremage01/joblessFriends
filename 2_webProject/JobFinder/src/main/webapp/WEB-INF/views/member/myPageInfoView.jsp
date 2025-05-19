<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/member/memberMyPage.css" />
<link rel="stylesheet" href="/css/member/myPageInfoView.css" />
</head>
<body>

<jsp:include page="../common/header.jsp"/>

	<div class="container">
		<div id="containerWrap">
			<jsp:include page="../common/mypageSidebar.jsp"/>
		    <div class="main">
		    
		    	<div class="content">
			    	<h1 class="mainTitle">개인정보 관리</h1>
			    	<div class="accountInfo">
				    	<h2>계정 정보</h2>
				    	<div>
				    		<span class="infoCategory">이메일</span>
				    		<span id="userEmail">${sessionScope.userLogin.email}</span>
				    		<button type="button" id="copyBtn">복사</button>
			    			<span id="copyPop">복사되었습니다.</span>
				    	</div>
				    	<div id="passwordChange">
				    		<span class="infoCategory">비밀번호 변경</span>
				    		<form id="pwdChangeForm" method="post" action="/member/passwordCheck" onsubmit="return submitCheck();">
				    			<input id="userPassword" type="hidden" value="${sessionScope.userLogin.password}"/>
				    			<input id="memberId" name="memberId"type="hidden" value="${sessionScope.userLogin.memberId}"/>
				    			<div class="inputGroup">
				    				<label for="oldPassword">현재 비밀번호</label>
									<input id="oldPassword" name="oldPassword" type="password" value="" onblur="checkOldPwd();" placeholder="비밀번호 입력"/>			    		
				    				<div id="oldpwdStatus" class="valiCheckText"></div>
				    			</div>
				    			<div class="inputGroup">
				    				<label for="password">새 비밀번호</label>
									<input id="password" name="password" type="password" value="" onblur="valiCheckPwd();" onkeyup="sameCheckPwd();" placeholder="새 비밀번호 (8자 이상/영문, 숫자 포함)"/>			    		
				    				<div id="pwdStatus" class="valiCheckText"></div>
				    			</div>
				    			<div class="inputGroup">
									<input id="passwordCheck" type="password" value="" onblur="sameCheckPwd();" placeholder="비밀번호 확인"/>    		
				    				<div id="pwdCheckStatus" class="valiCheckText"></div>
				    			</div>
				    			<button id="changeBtn" type="submit">변경하기</button>
				    		</form>
				    	</div>
			    	</div>
		    	</div>
		      
		    </div>
		</div>    
	</div>
	
<jsp:include page="../common/footer.jsp" />

</body>
<script src="/js/member/myPageInfoView.js"></script>
</html>