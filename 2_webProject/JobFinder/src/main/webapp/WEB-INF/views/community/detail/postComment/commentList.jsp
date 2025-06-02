<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<input type="hidden" id="commentNo_${comment.postCommentId}"
			value="${comment.postCommentId}">

			<div class="commentlist">
				<div>
					<p id='memName' class="commentBoxStyle">${comment.nickname}</p>
					<div id="commentlistNo_${comment.postCommentId}">
						<p class="commentBoxStyle">${comment.content}</p>
						<p class='commentBottom'>
							<span>${year}-${month}-${day} 작성</span> <a
								onclick='commentUpdateForm(${comment.postCommentId}, "${comment.content}")'>수정</a>
							<a onclick='commentDelete(${comment.postCommentId})'>삭제</a>
						</p>
						<p>
							<button onclick="testfnc(${comment.postCommentId}, '${comment.nickname}', '${comment.content}', '${comment.createAt}')">답글</button>
						</p>
					</div>
				</div>
			</div>
			

	</div>

</c:forEach>

<script>

function testfnc(commentId, nickname, content, createAt) {
    const target = $('#commentNo_' + commentId);

    // 이미 열려있는 답글창이 있으면 제거 (토글 동작)
    const existingReply = target.find('.replyBox');
    if (existingReply.length > 0) {
        existingReply.remove();
        return;
    }

    // 날짜 포맷
    const dateObj = new Date(createAt);
    const formattedDate = dateObj.getFullYear() + '-' +
        String(dateObj.getMonth() + 1).padStart(2, '0') + '-' +
        String(dateObj.getDate()).padStart(2, '0');

    // 답글 입력 UI
    const replyHtml = `
    	<div id="reply_id" class="replyBox" style="background-color: #F8F8F9">
		<input type="hidden" id="commentNo_${comment.postCommentId}"
		value="${comment.postCommentId}">
	<div class="commentlist">
		<div>
			<p id='memName' class="commentBoxStyle" style="margin-left: 50px;">\${nickname}</p>
			<div id="commentlistNo_${comment.postCommentId}"
				style="margin-left: 50px;">
				<p class="commentBoxStyle">\${content}</p>
				<p class='commentBottom'>
					<span>${year}-${month}-${day} 작성</span> <a
						onclick='commentUpdateForm(${comment.postCommentId}, "${comment.content}")'>수정</a>
					<a onclick='commentDelete(${comment.postCommentId})'>삭제</a>
				</p>
			</div>
		</div>
	</div>
	<div id="inputCommentWrap">
	<p>댓글</p>
	<textarea id="inputCommentBox" class="boxStyle"
		placeholder="댓글을 입력해주세요."></textarea>
	<div id="commentBtnWrap">
		<p>0/1000자</p>
		<button type="button" class="inputBtn"
			onclick="alermPopup(' 개인회원 전용 기능입니다. 개인 회원 전용으로 로그인해주세요.')">등록</button>
	</div>
</div>
</div>

    `;

    target.append(replyHtml);
}

</script>

