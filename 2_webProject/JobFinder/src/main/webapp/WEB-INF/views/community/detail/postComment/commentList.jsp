<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- 이거 없어도 될듯: 좀더 진행하고 삭제시 오류 없을떄 삭제할것-->


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
	            <p class='dateFont'><fmt:formatDate pattern="yyyy-MM-dd" value="${comment.createAt}"/> 작성</p>
	            <button class='replyBtn'>답글</button>
            </div>
        </div>
    </div>
</c:forEach>