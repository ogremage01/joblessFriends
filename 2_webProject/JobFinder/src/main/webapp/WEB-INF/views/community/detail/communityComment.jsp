<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/community/communityCommonStyle.css"> 
    <link rel="stylesheet" href="/css/community/comment/commentStyle.css">
    <link rel="stylesheet" href="/css/community/comment/commentWrapStyle.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="/js/community/communityDetail.js"></script>
    <script src="/js/community/postComment.js"></script>

</head>

<body>
<form method="post" action="">
    <div id="replyWrap" class="boxStyle contentBox">
        <div id="inputReplyWrap">
            <p>댓글</p>
            <textarea id="inputReplyBox" class="boxStyle" placeholder="댓글을 입력해주세요."></textarea>
            <div id="replyBtnWrap">
                <p>0/1000자</p>
                <button type="button" id="inputCommentBtn" class="inputBtn"
                    onclick="restRequestReply(${community.communityId}, null, null)">등록</button>
            </div>
        </div>


		<input type="hidden" id="communityNo" value="${community.communityId}" />
        <!-- 댓글 리스트가 여기에 들어옴 -->
        <div id="commentContainer">
        </div>
        <jsp:include page="/WEB-INF/views/community/detail/postComment/commentList.jsp" />
    </div>
</form>


<script type="text/javascript">


//비동기식(ajax)로 댓글 목록 가져오기
  $(document).ready(function () {
	
	   	const communityId = $("#communityNo").val();
	
	   $.ajax({
	       url: "/community/detail/comments/" + communityId,
	       type: "GET",
	       success: function (comments) {
	    	   let html = '';
	           
	               html += `
	            	   <input type="hidden" name="no" value="${community.communityId}">
	            	   		
	            	   <c:if test="${empty commentsList}">
	            	       <p>댓글이 없습니다.</p>
	            	   </c:if>

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

	               
	               `;
	          
	           $("#commentContainer").html(html);
	       }
	   });
  });

</script>
</body>
</html>
