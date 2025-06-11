$(document).ready(function () {

	// ✅ 등록 버튼 클릭 (동적 요소 포함 → 이벤트 위임 사용)
	$(document).on("click", ".inputReplyBtn", function () {
		const commentId = $(this).data("comment-id");
		const memberId = $("#memberReply").val();

		console.log("memberId:", memberId);
		console.log("commentId:", commentId);

		if (!memberId) {
			askConfirmLogin();
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
							</div>`;
					if(userType=='member'){	
						html +=  `<div id="inputReplyWrap" class="maxStyle">
									<textarea id="inputReplyBox_${commentId}" class="boxStyle" placeholder="답글을 입력해주세요."></textarea>
									<div id="replyBtnWrap">
										<p class="countReply">0/300자</p>
										<button type="button" class="inputReplyBtn inputBtn" data-comment-id="${commentId}">등록</button>
									</div>`;
						}
					html +=  `</div>
						</div>`;
				} else {
					replies.forEach(function (reply) {
						
						const createDate = new Date(reply.createAt);
						const year = createDate.getFullYear();
						const month = createDate.getMonth() + 1; // 월은 0부터 시작하므로 +1
						const day = createDate.getDate();
						
						var hour = String(createDate.getHours()).padStart(2, "0");
						var min = String(createDate.getMinutes()).padStart(2, "0");
						var sec = String(createDate.getSeconds()).padStart(2, "0");

						html += `
							<div class='replyBox'>
								<div class="replyBoundary">
									
									<div class="replylist replyBoxStyle">
										<p id='memName' style='margin-top: 0px'>${reply.nickname}</p>
										<div id="replylistNo_${reply.replyId}">
											<p>${reply.commentContent}</p>
											<p class='replyBottom'>
												<span>${year}-${month}-${day} ${hour}:${min}:${sec} 작성</span>`;
						if(reply.modifiedAt!=null){
							
							const modifiedDate = new Date(reply.modifiedAt);
							const modifiedyear = modifiedDate.getFullYear();
							const modifiedmonth = modifiedDate.getMonth() + 1; // 월은 0부터 시작하므로 +1
							const modifiedday = modifiedDate.getDate();
							
							var hour = String(modifiedDate.getHours()).padStart(2, "0");
							var min = String(modifiedDate.getMinutes()).padStart(2, "0");
							var sec = String(modifiedDate.getSeconds()).padStart(2, "0");
							
							html += `<span> | ${modifiedyear}-${modifiedmonth}-${modifiedday} ${hour}:${min}:${sec} 수정</span>`;
						}						
						
						if(userType=='member' && memberId ==reply.memberId){
							html += `	
												<a onclick='replyUpdateForm(${reply.replyId}, "${reply.commentContent}",${commentId})'>수정</a>
												<a onclick='replyDelete(${reply.replyId},${commentId})' >삭제</a>`;
						}else if(userType=='admin'){
							html += `	
												<a onclick='replyDelete(${reply.replyId})' >삭제</a>`;
							
						}
						html +=`
											</p>
										</div>
									</div>
								</div>
							`;
					});
/*답글 작성란*/
					if(userType=='member'){
						html += `
								<div id="inputReplyWrap">
									<textarea id="inputReplyBox_${commentId}" class="boxStyle  class="maxStyle"" placeholder="답글을 입력해주세요."></textarea>
									<div id="replyBtnWrap">
										<p class="countReply">0/300자</p>
										<button type="button" class="inputReplyBtn inputBtn" data-comment-id="${commentId}">등록</button>
									</div>
								</div>`;
					}
								
						html += `</div>`;
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
			askConfirm("답글을");
			return;
		}
		
		if(contentReply.length>300){
			askConfirmMax("답글");
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
				alermPopup("답글이 저장되었습니다.");
				// 리댓 목록 새로 불러오기
				window.loadCommentList();
				setTimeout(function () {
					loadReplyList(commentId);
				}, 70); 

				$('#inputReplyBox_' + commentId).val(''); // 입력창 초기화
			},
			error: function () {
				alermPopup("답글 등록에 실패했습니다.");
			}
		});
		

	}
	
});





//업데이트 폼
function replyUpdateForm(replyId, currentContent, commentId) {
    const replyObj = $("#replylistNo_" + replyId);
    var replyLength = currentContent.length; 
	
    const html = `

		<div id="inputModiReplyWrap"  class="maxStyle">
			<textarea id="editReply_${replyId}" class="boxStyle" placeholder="답글을 입력해주세요.">${currentContent}</textarea>
			<div id="replyModiBtnWrap">
				<p class="countReply">${replyLength}/300자</p>
				<button type="button" class="inputReplyBtn inputBtn" style="background-color:lightgray; color:black" 
						onclick="loadCommentList()">취소</button>
				<button type="button" class="inputReplyBtn inputBtn" onclick="replyUpdate(${replyId},${commentId})">등록</button>
			</div>
		</div>
    `;
    replyObj.html(html);

}


//업데이트 데이터 전송
function replyUpdate(replyId, commentId){
//	const commentObj = $('#commentNo_'+postCommentId);
	const urlStr = "/community/detail/replyUpdate/" + replyId;
	
    const currentContent = $("#editReply_"+replyId).val();//작성한 글의 내용

	console.log(currentContent);
    if (!currentContent.trim()) {
		askConfirm("답글을");
		return;
	}
	if(currentContent.length>300){
		askConfirmMax("답글");
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
			alermPopup("답글이 수정되었습니다.");
			window.loadCommentList();
			setTimeout(function () {
				loadReplyList(commentId);
			}, 70); 
			
		},
		error: function(){
			alermPopup("답글 등록에 실패했습니다.");
		}
	});
}





//삭제 로직
function replyDelete(replyId, commentId){
	let url = "/community/detail/replyDelete/"+replyId;


		fetch(url, {
			method: 'DELETE'
		}).then(
			function(response){
				if(response.ok){
					alermPopup("답글이 삭제되었습니다.");
					window.loadCommentList();
					setTimeout(function () {
						loadReplyList(commentId);
					}, 70); 
					
				}
			}
		)

	
}


	
	//글자수 세기 로직-inputCommentBox/countComment
	$(document).on("input",  "[id^='inputReplyBox_']", function(){
			var inputValue= $(this).val();//공백 포함
			$(this).val(inputValue);
			
			var inputLength = inputValue.length;  // 현재 입력된 글자 수
			var maxLength = 300;  // 최대 글자 수
			
			
			 // 해당 textarea 주변에서 countComment 찾아서 업데이트
			 $(this).closest("#inputReplyWrap").find(".countReply").text(inputLength + "/" + maxLength + " 자");

			// 글자 수가 최대에 도달하면 색상 변경
			if (inputLength >= maxLength) {
			    $(this).closest("#inputReplyWrap").find(".countReply").addClass("maxReached");
			} else {
			    $(this).closest("#inputReplyWrap").find(".countReply").removeClass("maxReached");
			}
			
			// 글자 수 초과하면 전송버튼 함수 막고 알림 보내기


		});
	
	//수정용 글자세기 로직
	$(document).on("input",  "[id^='editReply_']", function(){
			var inputValue= $(this).val();//공백 포함
			$(this).val(inputValue);
			
			var inputLength = inputValue.length;  // 현재 입력된 글자 수
			var maxLength = 300;  // 최대 글자 수
			
			
			 // 해당 textarea 주변에서 countComment 찾아서 업데이트
			 $(this).closest("#inputModiReplyWrap").find(".countReply").text(inputLength + "/" + maxLength + " 자");

			// 글자 수가 최대에 도달하면 색상 변경
			if (inputLength >= maxLength) {
			    $(this).closest("#inputModiReplyWrap").find(".countReply").addClass("maxReached");
			} else {
			    $(this).closest("#inputModiReplyWrap").find(".countReply").removeClass("maxReached");
			}
			
			// 글자 수 초과하면 전송버튼 함수 막고 알림 보내기


		});
		
	

