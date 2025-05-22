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
						
						const createDate = new Date(reply.createAt);
						const year = createDate.getFullYear();
						const month = createDate.getMonth() + 1; // 월은 0부터 시작하므로 +1
						const day = createDate.getDate();

						html += `
							<div class='replyBox'>
								<div class="replyBoundary">
									
									<div class="replylist replyBoxStyle">
										<p id='memName' style='margin-top: 0px'>${reply.nickname}</p>
										<div id="replylistNo_${reply.replyId}">
											<p>${reply.commentContent}</p>
											<p class='replyBottom'>
												<span>${year}-${month}-${day} 작성</span>`;
						if(reply.modifiedAt!=null){
							
							const modifiedDate = new Date(reply.modifiedAt);
							const modifiedyear = modifiedDate.getFullYear();
							const modifiedmonth = modifiedDate.getMonth() + 1; // 월은 0부터 시작하므로 +1
							const modifiedday = modifiedDate.getDate();
							
							html += `<span> | ${modifiedyear}-${modifiedmonth}-${modifiedday} 수정</span>`;
						}						
						
						html += `	
												<a onclick='replyUpdateForm(${reply.replyId}, "${reply.commentContent}")'>수정</a>
												<a onclick='replyDelete(${reply.replyId})' >삭제</a>
											</p>
										</div>
									</div>
								</div>
							`;
					});
/*답글 작성란*/
					html += `
							<div id="inputReplyWrap">
								<textarea id="inputReplyBox_${commentId}" class="boxStyle" placeholder="답글을 입력해주세요."></textarea>
								<div id="replyBtnWrap">
									<p>0/1000자</p>
									<button type="button" class="inputReplyBtn inputBtn" data-comment-id="${commentId}">등록</button>
								</div>
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





//업데이트 폼
function replyUpdateForm(replyId, currentContent) {
    const replyObj = $("#replylistNo_" + replyId);
      
	
    const html = `

		<div id="inputModiReplyWrap">
			<textarea id="editReply_${replyId}" class="boxStyle" placeholder="답글을 입력해주세요.">${currentContent}</textarea>
			<div id="replyModiBtnWrap">
				<p>0/1000자</p>
				<button type="button" class="inputReplyBtn inputBtn" style="background-color:lightgray; color:black" 
						onclick="loadCommentList()">취소</button>
				<button type="button" class="inputReplyBtn inputBtn" onclick="replyUpdate(${replyId})">등록</button>
			</div>
		</div>
    `;
    replyObj.html(html);

}


//업데이트 데이터 전송
function replyUpdate(replyId){
//	const commentObj = $('#commentNo_'+postCommentId);
	const urlStr = "/community/detail/replyUpdate/" + replyId;
	
    const currentContent = $("#editReply_"+replyId).val();//작성한 글의 내용

	console.log(currentContent);
    if (!currentContent.trim()) {
        alert("수정 답글이 비었습니다!");
        return;
    }
    
	$.ajax({
		url: urlStr,
		type: "POST",
		contentType: "application/json", //보내는 데이터 타입(데이터 보내는 쪽)
		data: JSON.stringify({ 
				commentContent: currentContent
		}),
		success: function (){
			alert("답글이 수정되었습니다.");
			window.loadCommentList();
		},
		error: function(){
			alert("답글 등록에 실패했습니다.");
		}
	});
}





//삭제 로직
function replyDelete(replyId){
	let url = "/community/detail/replyDelete/"+replyId;

	if(confirm('댓글을 삭제하시겠습니까?')){
	
		fetch(url, {
			method: 'DELETE'
		}).then(
			function(response){
				if(response.ok){
					alert("답글이 삭제되었습니다.");
					loadCommentList();
					
				}
			}
		)

	}
}

