// 리댓 리스트
$(document).ready(function () {
	//리댓 작성 클릭 이벤트	
	$("#inputReplyBtn").off("click").click(function() {
	    uploadReply(commentId, memberId); // 항상 1회만 등록됨
	});
	
	
	//리댓 목록 뽑아오기: 전역으로 loadReplyList 함수 선언
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
					html += `
					<div class='replyBox'>
						<div id="reply_id" class="nonReplyBoundary">
							<span>아직 답글이 없습니다. 첫 답글을 남겨보세요!</span>
						</div>
						<div id="inputReplyWrap" >
							<textarea id="inputReplyBox_${commentId}" class="boxStyle" placeholder="답글을 입력해주세요."></textarea>
							<div id="replyBtnWrap">
								<p>0/1000자</p>
								<button type="button" class="inputBtn"
									onclick="alert(' 개인회원 전용 기능입니다. 개인 회원 전용으로 로그인해주세요.')">등록</button>
							</div>
						</div>
					</div>`;
				} else {
					replies.forEach(function (reply) {
						var date = new Date(reply.createAt);

						var year = date.getFullYear();
						var month = date.getMonth();
						var day = date.getDate();
						
						//.replyBox : 닫을 리댓 박스 리스트를 찾기 위함
						html += `
						<div class='replyBox'>
							<div id="reply_id" class="replyBoundary">
								<input type="hidden" id="reply_${reply.replyId}" value="${reply.replyId}">
								<div class="replylist replyBoxStyle">
									<p id='memName' style='margin-top: 0px'>${reply.nickname}</p>
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
							<textarea id="inputReplyBox_${commentId}" class="boxStyle" placeholder="답글을 입력해주세요."></textarea>
							<div id="replyBtnWrap">
								<p>0/1000자</p>
								<button type="button" id='inputReplyBtn' class="inputBtn"
									onclick="alert(' 개인회원 전용 기능입니다. 개인 회원 전용으로 로그인해주세요.')">등록</button>
							</div>
						</div>
					</div>
					`
				}

				$("#replyContainer_" + commentId).html(html);
			}
		});
	};
	

	
	//리댓 작성하기
	//commentId-저장할 댓글 위치, memberId-리댓 다는 사람 정보
	function uploadReply(commentId, memberId){
		alert("구현중인 기능입니다.");
		const urlStr = "/community/detail/replyUpload/"+commentId;//저장 컨트롤러 링크
		const contentReply = $('#inputReplyBox_'+commentId).val();
		

		//1. 로그인 중이 아닌 경우, alert(' 개인회원 전용 기능입니다. 개인 회원 전용으로 로그인해주세요.')
		
		if (!contentReply.trim()) {
		    alert("답글을 입력해주세요.");
		    return;
		}
		//2. 로그인 중인 경우, commentId,memberId,contentReply값 삽입
		$.ajax({
			url: urlStr,
			type: "POST",
			contentType: "application/json", //보내는 데이터 타입(데이터 보내는 쪽)
			data: JSON.stringify({ 
						commentId: commentId,
						contentReply: contentReply,
						memberId: memberId
			}),
			success: function (){
				loadCommentList();//목록 다시불러오기
				$('#inputReplyBox_'+commentId).val(''); //입력창 비우기
			},
			error: function(){
				alert("댓글 등록에 실패했습니다.");
			}
		});	
		loadReplyList(commentId);
	}
	
	
	
});
