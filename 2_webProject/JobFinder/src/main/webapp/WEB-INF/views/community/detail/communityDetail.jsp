<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 | ${community.title}</title>

<link rel="stylesheet" href="/css/community/communityCommonStyle.css">
<link rel="stylesheet" href="/css/common/common.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js" crossorigin="anonymous"></script>

<style type="text/css">
.wrap {
	display: flex;
	justify-content: space-between;
	margin-top: 100px;
}

.contentBox {
	width: 860px;
	padding: 30px;
	min-height: 200px;
	margin-bottom: 20px;
}

.contentBox h2 {
	max-width: 870px;
	margin: 0px;
	margin-bottom: 13px;
}

#contentText {
	margin-top: 20px;
	white-space: pre-line;
	overflow-wrap: break-word;
}

#bottomSide {
	display: flex;
	margin-top: 50px;
	color: #6D707B;
}

#aTags {
	margin-left: auto;
}

#aTags a {
	margin-left: 20px;
}

 /* 취소선 스타일 */

    /* 블록 인용 스타일 */
    blockquote {
        background-color: #f0f0f0; /* 배경색 지정 */
        border-left: 5px solid #ccc; /* 왼쪽에 회색 띠 추가 */
        padding: 0px 10px; /* 여백 추가 */
        margin-left: 10px; /* 왼쪽 여백 추가 */
        font-style: italic; /* 기울임체 적용 */
        color: #555; /* 텍스트 색상 */
        
    }
    blockquote p{
    	margin: 10px;
    }
    

</style>


</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div id="containerWrap">
	<div class="wrap">
		<div>
			<jsp:include page="/WEB-INF/views/community/list/communitySideBar.jsp" />
		</div>

		<div>
			<input type="hidden" name="no" value="${community.communityId}">

			<div class="boxStyle contentBox">
				<h2>${community.title}</h2>

				<div id='infoContent'>
					<div>
						<svg xmlns="http://www.w3.org/2000/svg" width="80" height="24"
							fill="#a2a6b1" class="bi bi-eye" viewBox="0 0 75 16">
				  	<path
								d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z" />
				  	<path
								d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0" />
					<text x="40" y="13" font-size="12">${community.views}</text>
					<text x="67" y="12" font-size="12">|</text>
				</svg>
					</div>
					<div style="width: 400px">
						<svg xmlns="http://www.w3.org/2000/svg" width="400" height="24"
							fill="#a2a6b1" class="bi bi-chat-left" viewBox="0 0 340 16">
						  	<text x="0" y="12" font-size="12" ><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${community.createAt}"/> 작성</text>
						  	<c:if test="${community.modifiedAt ne null}">
							  	<text x="143" y="12" font-size="12">|</text>
							  	<text x="150" y="12" font-size="12" ><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${community.modifiedAt}"/> 수정</text>
						  	</c:if>
						</svg>
					</div>
				</div>

				<div id="contentText"><c:out value="${contentHtml}" escapeXml="false"/>
				</div>

				<div id="bottomSide">
					<p style="margin: 0px;">작성자 ${community.nickname}</p>
					<div id="aTags">
						<a href="./update?no=${community.communityId}">수정</a>
						<a onclick="deleteCommunity(${community.communityId})">삭제</a>
						
					</div>
				</div>
			</div>

			<div>
				<jsp:include page="/WEB-INF/views/community/detail/communityReply.jsp" />
			</div>
		</div>
	</div>
</div>


	<jsp:include page="/WEB-INF/views/community/topBar.jsp"/>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
<script src="/js/community/communityDetail.js"></script>
</html>

