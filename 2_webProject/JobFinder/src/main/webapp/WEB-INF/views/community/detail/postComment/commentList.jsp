<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- 화면에 처음 보여질 떄 나오는 화면: 정적 html 생성-->
<!-- 동적으로 바꿈으로서 필요 없어짐. 파일 디자인 끝내면서 삭제가능할지 오류 체크-->

<head>
<meta charset="UTF-8">

<link rel="stylesheet" href="/css/community/comment/commentStyle.css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/js/community/postComment.js"></script>
<script type="text/javascript">

</script>
</head>
<input type="hidden" name="no" value="${community.communityId}">

<c:if test="${empty commentsList}">
    <p>댓글이 없습니다.</p>
</c:if>
<c:forEach var="comment" items="${commentsList}">
    <div id="commentList">        
        <input type="hidden" id="communityNo" value="${comment.postCommentId}">
        <div id="commentlistNo_${comment.postCommentId}" class="commentlist">
        	<div>
	        	<p id='memName' class="commentBoxStyle">${comment.nickname}</p>
	            <p class="commentBoxStyle">${comment.content}</p>  
	            <p class='commentBottom'>
	            	<span>\${comment.createAt} 작성</span>
	            	<a onclick=''>수정</a> 
	            	<a onclick=''>삭제</a>
	            </p>
			</div>
        </div>
    </div>
</c:forEach>