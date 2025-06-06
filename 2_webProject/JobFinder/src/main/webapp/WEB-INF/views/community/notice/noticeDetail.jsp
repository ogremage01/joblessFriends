<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지 | ${notice.title}</title>

<link rel="stylesheet" href="/css/community/communityCommonStyle.css">
<link rel="stylesheet" href="/css/community/communityListOneStyle.css">
<link rel="stylesheet" href="/css/community/communityDetailStyle.css">
<link rel="stylesheet" href="/css/community/communityNav.css">
<link rel="stylesheet" href="/css/common/common.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	crossorigin="anonymous"></script>

<style type="text/css">

/* 블록 인용 스타일 */
blockquote {
	background-color: #f0f0f0; /* 배경색 지정 */
	border-left: 5px solid #ccc; /* 왼쪽에 회색 띠 추가 */
	padding: 0px 10px; /* 여백 추가 */
	margin-left: 10px; /* 왼쪽 여백 추가 */
	font-style: italic; /* 기울임체 적용 */
	color: #555; /* 텍스트 색상 */
}

blockquote p {
	margin: 10px;
}
</style>


</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div id="container">
		<div id="containerWrap">
			<div class="wrap">
				<div>
					<jsp:include
						page="/WEB-INF/views/community/list/communitySideBar.jsp" />
				</div>

				<div>
					<input type="hidden" name="no" value="${notice.noticeId}">

					<div class="boxStyle contentBox">
						<div id='noticeType'>${notice.noticeCategory.noticeCategoryContent}</div>
						<h2 class="detailTitle">${notice.title}</h2>

						<div id='infoContent'>
							<div style="min-width: 40px;">
								<svg xmlns="http://www.w3.org/2000/svg" width="20" height="24"
									fill="#a2a6b1" class="bi bi-eye" viewBox="0 0 20 16">
						  	<path
										d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z" />
						  	<path
										d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0" />
						</svg>
								<span style="margin-right: 10px;">${notice.views}</span> <span>|</span>
							</div>
							<div style="width: 400px">
								<svg xmlns="http://www.w3.org/2000/svg" width="400" height="24"
									fill="#a2a6b1" class="bi bi-chat-left" viewBox="0 0 340 16">
								  	<text x="0" y="12" font-size="12">
											<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
												value="${notice.createAt}" /> 작성</text>
								  	<c:if test="${notice.modifiedAt ne null}">
									  	<text x="143" y="12" font-size="12">|</text>
									  	<text x="150" y="12" font-size="12">
												<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${notice.modifiedAt}" /> 수정</text>
								  	</c:if>
								</svg>
							</div>
						</div>

						<div id="contentText"><c:out value="${contentHtml}" escapeXml="false" /></div>

						<div id="bottomSide">
							<p style="margin: 0px;"></p>
							<c:if test="${sessionScope.userType eq 'admin'}">
								<div id="aTags">
									<a href="/admin/community/notice/update?no=${notice.noticeId}">수정</a> <a
										onclick="deleteNotice(${notice.noticeId})">삭제</a>
								</div>
							</c:if>
						</div>
					</div>

				</div>
			</div>
			<div class='detailBtn_BottomStyle'>
				<button class='detailBtnStyle' onclick="goBackToList()">목록 보기</button>
				<button class='detailBtnStyle' onclick='location.href="#"'>▲TOP</button>
			</div>
		</div>
	</div>


	<jsp:include page="/WEB-INF/views/community/topBar.jsp" />
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="/js/community/notice/noticeDetail.js"></script>
</body>
</html>

