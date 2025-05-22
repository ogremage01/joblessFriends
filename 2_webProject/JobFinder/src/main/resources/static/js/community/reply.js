$(document).ready(function () {

	// ✅ 등록 버튼 클릭 (동적 요소 포함 → 이벤트 위임 사용)
	$(document).on("click", ".inputReplyBtn", function () {
		const commentId = $(this).data("comment-id");
		const memberId = $("#memberReply").val();

		console.log("memberId:", memberId);
		console.log("commentId:", commentId);

		if (!memberId) {
			alert("개인회원 전용 기능입니다. 개인 회원 전용으로 로그인해주세요.");
			return;
		}

		uploadReply(commentId, memberId);
	});

	// ✅ 리댓 목록 불러오기
	window.loadReplyList = function loadReplyList(commentId) {
		const target = $('#commentNo_' + commentId); // 리댓을 삽입할 대상
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
				console.log("받은 리댓 목록:", replies);
				let html = '';

				if (replies.length === 0) {
					html += `
						<div class='replyBox'>
							<div class="nonReplyBoundary">
								<span>아직 답글이 없습니다. 첫 답글을 남겨보세요!</span>
							</div>
							<div id="inputReplyWrap">
								<textarea id="inputReplyBox_${commentId}" class="boxStyle" placeholder="답글을 입력해주세요."></textarea>
								<div id="replyBtnWrap">
									<p>0/1000자</p>
									<button type="button" class="inputReplyBtn inputBtn" data-comment-id="${commentId}">등록</button>
								</div>
							</div>
						</div>`;
				} else {
					replies.forEach(function (reply) {
						const date = new Date(reply.createAt);
						const year = date.getFullYear();
						const month = date.getMonth() + 1; // 월은 0부터 시작하므로 +1
						const day = date.getDate();

						html += `
							<div class='replyBox'>
								<div class="replyBoundary">
									<div class="replylist replyBoxStyle">
										<p id='memName' style='margin-top: 0px'>${reply.nickname}</p>
										<div id="replylistNo_${reply.replyId}">
											<p>${reply.commentContent}</p>
											<p class='replyBottom'>
												<span>${year}-${month}-${day} 작성</span>
												<a onclick="replyUpdateForm(${reply.replyId}, '${reply.commentContent}')">수정</a>
												<a onclick='replyDelete(${reply.replyId})'>삭제</a>
											</p>
										</div>
									</div>
								</div>
							</div>`;
					});

					html += `
						<div id="inputReplyWrap">
							<textarea id="inputReplyBox_${commentId}" class="boxStyle" placeholder="답글을 입력해주세요."></textarea>
							<div id="replyBtnWrap">
								<p>0/1000자</p>
								<button type="button" class="inputReplyBtn inputBtn" data-comment-id="${commentId}">등록</button>
							</div>
						</div>`;
				}

				// 삽입
				$("#replyContainer_" + commentId).html(html);
			}
		});
	};

	// ✅ 리댓 등록 함수
	function uploadReply(commentId, memberId) {
		const contentReply = $('#inputReplyBox_' + commentId).val();
		const urlStr = "/community/detail/replyUpload/" + commentId;

		if (!contentReply.trim()) {
			alert("답글을 입력해주세요.");
			return;
		}

		$.ajax({
			url: urlStr,
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify({
				postCommentId: commentId,
				commentContent: contentReply,
				memberId: memberId
			}),
			success: function () {
				loadCommentList(); // 전체 댓글 다시 불러오기
				$('#inputReplyBox_' + commentId).val(''); // 입력창 초기화
			},
			error: function () {
				alert("댓글 등록에 실패했습니다.");
			}
		});

		alert("댓글이 저장되었습니다.");
		// 리댓 목록 새로 불러오기
		loadReplyList(commentId);

	}
	
});
