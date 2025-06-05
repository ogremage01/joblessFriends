$(document).ready(function () {
		const communityId = $("#communityNo").val();
	
		  // 폼 기본 submit 막기
	    $("#commentForm").submit(function(e) {
	        e.preventDefault();
	    });
		
		$("#inputCommentBtn").off("click").click(function() {
		    uploadComment(communityId, memberId);
		});
		
		//댓글 리스트가 보이는 부분
	
		function loadCommentList(){
			const urlStr = "/community/detail/comments/" + communityId;
		
		    $.ajax({
		        url: urlStr,
		        type: "GET",
		        dataType: "json",//서버에서 받는쪽
		        success: function (comments) {//comments : Controller에서의 commentList메서드(urlStr경로) 반환값.
		            console.log("받은 댓글 목록:", comments);
		
		            let html = '';
		
		            if (comments.length === 0) {
		                html += `
						<div id="nonCommentList">        
							<span>작성 된 댓글이 없습니다.</span>
						</div>
						`;
		            } else {
	
		                html += `<input type="hidden" name="no" value="${communityId}">`;
	         
		                comments.forEach(function (comment) {	
		                	var date = new Date(comment.createAt);
		                	
		                	var year = date.getFullYear();
		                	var month = date.getMonth()+1;
		                	var day = date.getDate();
							
							var hour = String(date.getHours()).padStart(2, "0");
							var min = String(date.getMinutes()).padStart(2, "0");
							var sec = String(date.getSeconds()).padStart(2, "0");						
		                	
							//.replyBox : 닫을 리댓 박스 리스트를 찾기 위함
							html += `
								<div id="commentList">        
									<div id="commentNo_${comment.postCommentId}">
			                        	<input type="hidden" id="commentNo" value="${comment.postCommentId}">
				                        <div class="commentlist">

			                	        	<p id='memName' class="commentBoxStyle">${comment.nickname}</p>
			                	        	<div id="commentlistNo_${comment.postCommentId}">
				                	            <p class="commentBoxStyle">${comment.content}</p>  
				                	            <p class='commentBottom'>
												<span>${year}-${month}-${day} ${hour}:${min}:${sec} 작성 </span>`;

							if(comment.modifiedAt!=null){
								
								const modifiedDate = new Date(comment.modifiedAt);
								const modifiedyear = modifiedDate.getFullYear();
								const modifiedmonth = modifiedDate.getMonth() + 1; // 월은 0부터 시작하므로 +1
								const modifiedday = modifiedDate.getDate();
								
								var modifiedHour = String(modifiedDate.getHours()).padStart(2, "0");
								var modifiedMin = String(modifiedDate.getMinutes()).padStart(2, "0");
								var modifiedSec = String(modifiedDate.getSeconds()).padStart(2, "0");	

								html += `<span>  | </span>
										<span>${modifiedyear}-${modifiedmonth}-${modifiedday} ${modifiedHour}:${modifiedMin}:${modifiedSec} 수정</span>`;
							}	
					
							if(userType=='member' && memberId ==comment.memberId){
								html += `	<a onclick='commentUpdateForm(${comment.postCommentId}, "${comment.content}")'>수정</a> 
						                	<a onclick='commentDelete(${comment.postCommentId})'>삭제</a>`;
							}else if(userType=='admin'){
								html += `	<a onclick='commentDelete(${comment.postCommentId})'>삭제</a>`;
							}	
				                html +=`</p>
						                	<p><button class='repCntBtn' onclick="loadReplyList(${comment.postCommentId})">답글 ${comment.replyCount}</button></p>
			                	          </div>
				                        </div>
										<div id="replyContainer_${comment.postCommentId}" style="background-color: #F8F8F9"></div>
									</div>
		                   	 	</div>
							`;
						});
		                
		            }
		            $("#commentContainer").html(html);
		        },
		        error: function (xhr, status, error) {
		            console.error("댓글 불러오기 실패:", error); // 오류 확인
		        }
		    });
		}
	
		window.loadCommentList = loadCommentList;//전역 참조용으로 생성(댓글 리스트 로드)
		
		//댓글 등록 버튼 클릭 시 댓글 작성 요청
		function uploadComment(communityId, memberId){
			const urlStr = "/community/detail/commentUpload/" + communityId;
	
		    const content = $("#inputCommentBox").val();//작성한 글의 내용
		
			console.log(userType);
			if(userType != 'member'){
				askConfirmLogin();
				return;
			}
				
		    if (!content.trim()) {
		        askConfirm("댓글을");
		        return;
		    }
			
			if (content.length > 300) {
			       askConfirmMax("댓글");
			       return;
			}

			$.ajax({
				url: urlStr,
				type: "POST",
				contentType: "application/json", //보내는 데이터 타입(데이터 보내는 쪽)
				data: JSON.stringify({ 
							communityId: communityId,
							content: content,
							memberId: memberId
				}),
				success: function (){
					alermPopup("댓글이 등록되었습니다.");
					loadCommentList();//목록 다시불러오기
					$("#inputCommentBox").val(''); //입력창 비우기
				},
				error: function(){
					console.log("댓글 등록에 실패했습니다.");
				}
			});	
		}
	
		loadCommentList();	
		
	});
	
	//업데이트 폼
	function commentUpdateForm(commentId, currentContent) {
	    const commentObj = $("#commentlistNo_" + commentId);
		var commentLength = currentContent.length;
		
	    const html = `
	    	<div id='modifiedWrap' class="modifiedWrap">
		        <textarea id="editCommentContent_${commentId}" class="boxStyle">${currentContent}</textarea>
		        <div id="commentBtnWrap">
		        	<p id='modifiedComment' class='countComment maxStyle'>${commentLength}/300자</p>
		            <button class="inputBtn " onclick="commentUpdate(${commentId})">저장</button>
		            <button class="inputBtn" onclick="window.loadCommentList()" style="background-color:lightgray; color:black">취소</button>
				</div>
		    </div>
	    `;
	    commentObj.html(html);
		
		if (commentLength >= 300) {
		    $("#modifiedWrap").find(".countComment").addClass("maxReached");
		} else {
		    $("#modifiedWrap").find(".countComment").removeClass("maxReached");
		}
	
	}
	
	
	//업데이트 데이터 전송
	function commentUpdate(postCommentId){
	//	const commentObj = $('#commentNo_'+postCommentId);
		const urlStr = "/community/detail/commentUpdate/" + postCommentId;
		
	    const content = $("#editCommentContent_"+postCommentId).val();//작성항 글의 내용
	
		if (!content.trim()) {
		    askConfirm("수정 댓글을");
		    return;
		}

		if (content.length > 300) {
		       askConfirmMax("수정 댓글");
		       return;
		}

		
		$.ajax({
			url: urlStr,
			type: "POST",
			contentType: "application/json", //보내는 데이터 타입(데이터 보내는 쪽)
			data: JSON.stringify({ 
						content: content
			}),
			success: function (){
				alermPopup("댓글이 수정되었습니다.");
				window.loadCommentList();
			},
			error: function(){
				alermPopup("댓글 등록에 실패했습니다.");
			}
		});
	}
	
	//삭제 로직
	function commentDelete(postCommentId){
		let url = "/community/detail/delete/"+postCommentId;
	

		
			fetch(url, {
				method: 'DELETE'
			}).then(
				function(response){
					if(response.ok){
						alermPopup("댓글이 삭제되었습니다.");
						
						window.loadCommentList();//전역용 댓글리스트 함수 불러오기
					}
				}
			)
		
	}
	
	
	
	//글자수 세기 로직-inputCommentBox/countComment
	$("#inputCommentBox").on("input", function(){
		var inputValue= $(this).val();//공백 포함
		$(this).val(inputValue);
		
		var inputLength = inputValue.length;  // 현재 입력된 글자 수
		var maxLength = 300;  // 최대 글자 수
		
		
		// 글자 수 표시
		$(".countComment").text(inputLength + "/" + maxLength + " 자");
		
/*		// 비활성화 + 경고
		if (inputLength > maxLength) {
		    $("#inputCommentBtn").prop("disabled", true);
		    if (!alertShown) {
		        alertShown = true;
		    }
		} else {
		    $("#inputCommentBtn").prop("disabled", false);
		    alertShown = false;
		}*/

		// 글자 수가 최대에 도달하면 색상 변경
		if (inputLength >= maxLength) {
			$(".countComment").addClass("maxReached");  // 빨간색으로 변경
		} else {
			$(".countComment").removeClass("maxReached");  // 색상 원래대로
		}
		
		// 글자 수 초과하면 전송버튼 함수 막고 알림 보내기


	});
	
	//수정용 글자세기 로직
	$(document).on("input",  "[id^='editCommentContent_']", function(){
			var inputValue= $(this).val();//공백 포함
			$(this).val(inputValue);
			
			var inputLength = inputValue.length;  // 현재 입력된 글자 수
			var maxLength = 300;  // 최대 글자 수
			
			
			 // 해당 textarea 주변에서 countComment 찾아서 업데이트
			 $(this).closest("#modifiedWrap").find(".countComment").text(inputLength + "/" + maxLength + " 자");

			// 글자 수가 최대에 도달하면 색상 변경
			if (inputLength >= maxLength) {
			    $(this).closest("#modifiedWrap").find(".countComment").addClass("maxReached");
			} else {
			    $(this).closest("#modifiedWrap").find(".countComment").removeClass("maxReached");
			}
			
			// 글자 수 초과하면 전송버튼 함수 막고 알림 보내기


		});
		
	
	
	

