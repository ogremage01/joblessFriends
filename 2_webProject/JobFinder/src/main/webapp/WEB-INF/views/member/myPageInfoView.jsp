<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/member/memberMyPage.css" />
<link rel="stylesheet" href="/css/member/myPageInfoView.css" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>

<jsp:include page="../common/header.jsp"/>

	<div class="container">
		<div id="containerWrap">
			<jsp:include page="../common/mypageSidebar.jsp"/>
		    <div class="main">
		    
		    	<div class="content">
			    	<h1 class="mainTitle">
			    		<i class="bi bi-person-gear" style="color: #F69800;"></i>
			    		개인정보 관리
			    	</h1>
	    			<input id="memberId" type="hidden" value="${sessionScope.userLogin.memberId}"/>
	    			<input id="provider" type="hidden" value="${sessionScope.userLogin.provider}"/>
			    	<div class="accountInfo">
				    	<h2>계정 정보</h2>
				    	<div>
				    		<span class="infoCategory">닉네임</span>
				    		<span id="userNickname" class="socialInfo">${sessionScope.userLogin.nickname}</span>
				    		<button type="button" class="infoBtn" id="nickChangeBtn">변경</button>
				    	</div>
				    	<div>
				    		<span class="infoCategory">이메일</span>
				    		<span id="userEmail" class="socialInfo">${sessionScope.userLogin.email}</span>
				    		
				    		<c:if test="${sessionScope.userLogin.provider eq 'google'}">
				    			<span class="socialInfo inGoogle">Google에서 관리됨</span>
				    		</c:if>
				    		
				    		<c:if test="${sessionScope.userLogin.provider eq 'normal'}">
					    		<button type="button" class="infoBtn" id="copyBtn">복사</button>
				    			<span id="copyPop">복사되었습니다.</span>
				    		</c:if>
				    	</div>
			    		<c:if test="${sessionScope.userLogin.provider eq 'normal'}">
					    	<div id="passwordChange">
					    		<span class="infoCategory">비밀번호 변경</span>
					    		<div id="pwdChangeMain">
						    		<form id="pwdChangeForm" class="formStyle" method="post" action="/member/passwordCheck">
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
						    			<button class="btnStyle" type="submit">변경하기</button>
						    		</form>
						    		<div class="changeReset">
						    			<span class="resetBtn">
							    			초기화
						    			</span>
						    		</div>
					    		</div>
					    	</div>
					    </c:if>
			    	</div>
		    		<div class="accountInfo accountDelete">
				    	<h2>탈퇴하기</h2>
		    			<div>
		    				<form id="deleteCheckForm" class="formStyle" onsubmit="return false">
					    			<c:if test="${sessionScope.userLogin.provider eq 'normal'}">
						    			<div class="inputGroup">
						    				<label for="passwordDel">현재 비밀번호</label>
											<input id="passwordDel" name="password" type="password" onblur="valiCheckPwdDel();" value="" placeholder="비밀번호 입력"/>			    		
						    				<div id="passwordDelStatus" class="valiCheckText"></div>
						    			</div>
					    			</c:if>
				    			<button id="delModalOpen" class="btnStyle" type="button">회원탈퇴</button>
				    		</form>
		    			</div>
		    		</div>
		    </div>
		</div>    
	</div>
	
	<div class="popup-wrap" id="popup">
   		<div class="modalPopup">
          <div class="popContent">
            <span class="popTitle"></span><br>
            <span class="popText"></span>
          </div>
          <div class="popBtns">
          	<button class="popBtnStyle popCancel" type="button"></button>
          	<button class="popBtnStyle popSubmit" type="button"></button>
          </div>
    	</div>
    </div>
<jsp:include page="../common/footer.jsp" />

</body>
<script src="/js/member/myPageInfoView.js"></script>
</html>