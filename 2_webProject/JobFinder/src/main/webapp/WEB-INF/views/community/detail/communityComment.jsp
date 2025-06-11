<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/community/communityCommonStyle.css">
<link rel="stylesheet" href="/css/community/comment/commentStyle.css">
<link rel="stylesheet" href="/css/community/comment/commentWrapStyle.css">
<link rel="stylesheet" href="/css/community/toastPopup.css"> 

<link rel="stylesheet"
	href="/css/community/comment/commentWrapStyle.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/js/community/communityDetail.js"></script>
<script src="/js/community/postComment.js"></script>

</head>

<body>
	<div id="commentWrap" class="boxStyle contentBox" style="padding-top: 5px">
		<!-- 		<form id="commentForm" method="post" action=""> -->
		<c:choose>
			<c:when test="${sessionScope.userLogin ne null}"><!-- 유저로그인 중 -->
				<c:choose>
					<c:when test="${sessionScope.userType eq 'member'}"><!-- 유저타입이 '멤버' -->
<!-- 일반 회원용 -->
						<div id="inputCommentWrap">
							<p style="font-weight: bold; font-size:18px">댓글</p>
							<textarea id="inputCommentBox" class="boxStyle" placeholder="댓글을 입력해주세요."></textarea>
							<div id="commentBtnWrap"  class="maxStyle">
								<p class='countComment'>0/300 자</p>
								<button type="button" id="inputCommentBtn" class="inputBtn" data-usertype='${sessionScope.userType}'>등록</button>
							</div>
						</div>
						<!-- 댓글 입력시 정보부분 -->
						<input type="hidden" id="communityNo" value="${community.communityId}" />
						<script type="text/javascript">
						  	const userType = "${sessionScope.userType}";
							const memberId = "${sessionScope.userLogin.memberId}";
						</script>

					</c:when>
					
					<c:when test="${sessionScope.userType eq 'company'}"> <!-- 유저타입이 회사 -->
<!-- 기업 회원용 -->				
						
						<p style="font-weight: bold; font-size:18px">댓글</p>
						
						<!-- 댓글 입력시 정보부분: 오류 안나게 일단은 지정 -->
						<input type="hidden" id="communityNo" value="${community.communityId}" />
						<script type="text/javascript">
							const userType = "";
							const memberId = 0;
						</script>
					</c:when>
					<c:otherwise>
						<div id="inputCommentWrap"><!-- 관리자용 댓글 -->	
							<p>댓글</p>
						</div>
						
						<!-- 댓글 입력시 정보부분 -->
						<input type="hidden" id="communityNo" value="${community.communityId}" />
						<script type="text/javascript">
							const userType = "admin";
							const memberId = 0;
						</script>
					</c:otherwise>				
				</c:choose>
			</c:when>
			<c:otherwise> 
				<div id="inputCommentWrap" style="padding-top: 0px">
					<p style="margin-top: 0px">댓글</p>
					<textarea id="inputCommentBox" class="boxStyle" placeholder="댓글을 입력해주세요."></textarea>
					<div id="commentBtnWrap">
						<p class='countComment'>0/1000자</p>
						<button type="button" id="inputCommentBtn" class="inputBtn" data-usertype='${sessionScope.userType}'>등록</button>
					</div>
				</div>
					<input type="hidden" id="communityNo" value="${community.communityId}" />
					<script type="text/javascript">
					  	const userType = "";
						const memberId = 0;
					</script>
				
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${sessionScope.userLogin ne null}"><!-- 유저로그인 중 -->
				<c:choose>
					<c:when test="${sessionScope.userType eq 'member'}">
						<input type="hidden" id="memberReply" value="${sessionScope.userLogin.memberId}">
					</c:when>
				</c:choose>
			</c:when>
			<c:when test="${sessionScope.admin ne null}">
				<input type="hidden" id="memberReply" value="${sessionScope.userLogin.adminId}">
			</c:when>
		</c:choose>
		<!-- 댓글 리스트가 여기에 들어옴 -->
		<div id="commentContainer"></div>
		<div id="askConfirm">
		</div>
	</div>
	


<script src="/js/community/toastPopup.js"></script>
<script src="/js/community/postComment.js"></script>
<script src="/js/community/reply.js"></script>

</body>
</html>
