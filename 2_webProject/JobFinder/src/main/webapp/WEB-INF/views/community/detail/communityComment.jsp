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
<link rel="stylesheet"
	href="/css/community/comment/commentWrapStyle.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/js/community/communityDetail.js"></script>
<script src="/js/community/postComment.js"></script>

</head>

<body>
	<div id="commentWrap" class="boxStyle contentBox">
		<!-- 		<form id="commentForm" method="post" action=""> -->
		<c:choose>
			<c:when test="${sessionScope.userType eq 'member'}">
				<c:choose>
					<c:when test="${sessionScope.userType eq 'member'}">
<!-- 일반 회원용 -->
						<div id="inputCommentWrap">
							<p>댓글</p>
							<textarea id="inputCommentBox" class="boxStyle" placeholder="댓글을 입력해주세요."></textarea>
							<div id="commentBtnWrap">
								<p>0/1000자</p>
								<button type="button" id="inputCommentBtn" class="inputBtn">등록</button>
							</div>
						</div>
						<!-- 댓글 입력시 정보부분 -->
						<input type="hidden" id="communityNo" value="${community.communityId}" />
						<script type="text/javascript">
							const memberId = ${sessionScope.userLogin.memberId};
						</script>


					</c:when>
					
					<c:when test="${sessionScope.admin ne null}">
<!-- 관리자용 댓글 -->

						<div id="inputCommentWrap">
							<p>댓글</p>
							<textarea id="inputCommentBox" class="boxStyle"
								placeholder="댓글을 입력해주세요."></textarea>
							<div id="commentBtnWrap">
								<p>0/1000자</p>
								<button type="button" id="inputCommentBtn" class="inputBtn">등록</button>
							</div>
						</div>
						<input type="hidden" id="communityNo"
							value="${community.communityId}" />
						<script type="text/javascript">
							/* const memberId = ${sessionScope.userLogin.memberId}; */
							/* 여기 just 관리자라고 뜨기 */
						</script>

					</c:when>
					
				</c:choose>
			</c:when>
			<c:otherwise> 
				<div id="inputCommentWrap">
					<p>댓글</p>
					<textarea id="inputCommentBox" class="boxStyle"
						placeholder="댓글을 입력해주세요."></textarea>
					<div id="commentBtnWrap">
						<p>0/1000자</p>
						<button type="button" class="inputBtn"
							onclick="alert(' 개인회원 전용 기능입니다. 개인 회원 전용으로 로그인해주세요.')">등록</button>
					</div>
				</div>
				<input type="hidden" id="communityNo" value="${community.communityId}" />
			</c:otherwise>
		</c:choose>

		<input type="hidden" id="memberReply" value="${sessionScope.userLogin.memberId}">
		<!-- 댓글 리스트가 여기에 들어옴 -->
		<div id="commentContainer"></div>
	</div>
<script src="/js/community/postComment.js"></script>
<script src="/js/community/reply.js"></script>

</body>
</html>
