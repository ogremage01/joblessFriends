// 리댓 리스트
$(document).ready(function () {
	window.loadReplyList = function loadReplyList(commentId) {
		const target = $('#commentNo_' + commentId); // 리댓 html 생성할 곳
		const urlStr = "/community/detail/reply/" + commentId;

		const existingReply = target.find('.replyBox');
		if (existingReply.length > 0) {
			existingReply.remove();
			return;
		}

		$.ajax({
			url: urlStr,
			type: "GET",
			dataType: "json",
			success: function (replies) {
				console.log("받은 리댓 목록: ", replies);
				let html = '';

				if (replies.length === 0) {
					html += `<p>답글이 없습니다.</p>`;
				} else {
					replies.forEach(function (reply) {
						var date = new Date(reply.createAt);

						var year = date.getFullYear();
						var month = date.getMonth();
						var day = date.getDate();
						
						
						html += `
							<div id="reply_id" class="replyBox">
								<input type="hidden" id="reply_${reply.replyId}" value="${reply.replyId}">
								<div class="replylist replyBoxStyle">
									<p id='memName' style='margin-top: 0px'>이름</p>
									<div id="replylistNo_${reply.replyId}">
										<p>${reply.commentContent}</p>
										<p class='replyBottom'>
											<span>${year}-${month}-${day}  작성</span>
											<a onclick="replyUpdateForm(${reply.replyId}, '${reply.commentContent}')">수정</a>
											<a onclick='replyDelete(${reply.replyId})'>삭제</a>
										</p>
									</div>
								</div>
								
							</div>
						`;
					});
					
					html+=`
					<div id="inputReplyWrap" >
						<textarea id="inputReplyBox" class="boxStyle" placeholder="답글을 입력해주세요."></textarea>
						<div id="replyBtnWrap">
							<p>0/1000자</p>
							<button type="button" class="inputBtn"
								onclick="alert(' 개인회원 전용 기능입니다. 개인 회원 전용으로 로그인해주세요.')">등록</button>
						</div>
					</div>
					`
				}

				$("#replyContainer_" + commentId).html(html);
			}
		});
	};
}); // ✅ 이게 빠져 있었음!
