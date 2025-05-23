<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>대댓글 연습 목록</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	crossorigin="anonymous"></script>

</head>
<body>

	<div id="replyContainer">
		<div class="replyBox" style="background-color: #F8F8F9">
			<input type="hidden"  id="replyId" value="${replyId}">
			<div class="replylist">
				<div>
					<p id='memName' class="commentBoxStyle" style="margin-left: 50px;">${nickname}</p>
					<div id="replylistNo_${replyId}" style="margin-left: 50px;">
						<p class="commentBoxStyle">${content}</p>
						<p class='replyBottom'>
							<span>${createAt} 작성</span> <a
								onclick='replyUpdateForm(${replyId}, "${content}")'>수정</a> <a
								onclick='replyDelete(${replyId})'>삭제</a>
						</p>
					</div>
				</div>
			</div>
			<div id="inputReplyWrap">

				<textarea id="inputReplyBox" class="boxStyle"
					placeholder="답글을 입력해주세요."></textarea>
				<div id="replyBtnWrap">
					<p>0/1000자</p>
					<button type="button" class="inputBtn"
						onclick="alert(' 개인회원 전용 기능입니다. 개인 회원 전용으로 로그인해주세요.')">등록</button>
				</div>
			</div>
		</div>

	</div>

</body>

<script type="text/javascript">

		//리댓 리스트
		function loadReplyList(commentId){
			const target = $('#commentNo_' + commentId);//리댓 html 생성할 곳
			const urlStr="/community/detail/reply/" + commentId;
			const postCommentId = $('#commentNo').val;//댓글 아이디
			
			// 이미 열려있는 답글창이 있으면 제거 (토글 동작)
		    const existingReply = target.find('.replyBox');
		    if (existingReply.length > 0) {
		        existingReply.remove();
		        return;
		    }
			
			$.ajax({
				url: urlStr,
				type: "Get",
				dataType: "json",//서버에서 받아옴
				success: function(replies){//replies : 컨트롤러에서 replyList(urlStr경로)의 반환값
					console.log("받은 리댓 목록: ", replies);
					
					
					var date = new Date(comment.createAt);
                	
                	var year = date.getFullYear();
                	var month = date.getMonth();
                	var day = date.getDate();
                	
					html += `
						<div class="replyBox" style="background-color: #F8F8F9">
						<input type="hidden" id="replyId" value="${replyId}">
							<div class="replylist">
								<div>
									<p id='memName' class="commentBoxStyle" style="margin-left: 50px;">${nickname}</p>
									<div id="replylistNo_${replyId}"
										style="margin-left: 50px;">
										<p class="commentBoxStyle">${content}</p>
										<p class='replyBottom'>
											<span>${createAt} 작성</span> <a
												onclick='replyUpdateForm(${replyId}, "${content}")'>수정</a>
											<a onclick='replyDelete(${replyId})'>삭제</a>
										</p>
									</div>
								</div>
							</div>
							<div id="inputReplyWrap">
	
							<textarea id="inputReplyBox" class="boxStyle" placeholder="답글을 입력해주세요." ></textarea>
							<div id="replyBtnWrap">
								<p>0/1000자</p>
								<button type="button" class="inputBtn"
									onclick="alert(' 개인회원 전용 기능입니다. 개인 회원 전용으로 로그인해주세요.')">등록</button>
							</div>
						</div>
					`;
				});
              
                
            }
            $("#replyContainer").html(html);
		}

</script>
</html>

