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
		        success: function (comments) {
		            console.log("받은 댓글 목록:", comments);
		
		            let html = '';
		
		            if (comments.length === 0) {
		                html += `<p>댓글이 없습니다.</p>`;
		            } else {
	
		                html += `<input type="hidden" name="no" value="${communityId}">`;
	         
		                comments.forEach(function (comment) {	
		                	var date = new Date(comment.createAt);
		                	
		                	var year = date.getFullYear();
		                	var month = date.getMonth();
		                	var day = date.getDate();
		                	
							html += `
								<div id="commentList">        
									<div id="commentNo_${comment.postCommentId}">
			                        	<input type="hidden" id="commentNo" value="${comment.postCommentId}">
				                        <div class="commentlist">

			                	        	<p id='memName' class="commentBoxStyle">${comment.nickname}</p>
			                	        	<div id="commentlistNo_${comment.postCommentId}">
				                	            <p class="commentBoxStyle">${comment.content}</p>  
				                	            <p class='commentBottom'>
				            	            		<span>${year}-${month}-${day}  작성</span>
				                	            	<a onclick='commentUpdateForm(${comment.postCommentId}, "${comment.content}")'>수정</a> 
				                	            	<a onclick='commentDelete(${comment.postCommentId})'>삭제</a>
				                	            </p>
						                	     <p><button onclick="loadReplyList(${comment.postCommentId})">답글</button></p>
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
			
		    const content = $("#inputCommentBox").val();//작성항 글의 내용
		
		    if (!content.trim()) {
		        alert("댓글을 입력해주세요.");
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
					loadCommentList();//목록 다시불러오기
					$("#inputCommentBox").val(''); //입력창 비우기
				},
				error: function(){
					alert("댓글 등록에 실패했습니다.");
				}
			});	
		}
	
		loadCommentList();	
		
	});
	
	//업데이트 폼
	function commentUpdateForm(commentId, currentContent) {
	    const commentObj = $("#commentlistNo_" + commentId);
	      
	    const html = `
	    	<div id='modifiedWrap'>
		        <textarea id="editCommentContent_${commentId}" class="boxStyle">${currentContent}</textarea>
		        <div id="commentBtnWrap">
		        	<p>0/1000자</p>
		            <button class="inputBtn" onclick="commentUpdate(${commentId})">저장</button>
		            <button class="inputBtn" onclick="window.loadCommentList()" style="background-color:lightgray; color:black">취소</button>
				</div>
		    </div>
	    `;
	    commentObj.html(html);
	
	}
	
	
	//업데이트 데이터 전송
	function commentUpdate(postCommentId,content){
	//	const commentObj = $('#commentNo_'+postCommentId);
		const urlStr = "/community/detail/commentUpdate/" + postCommentId;
		
	    content = $("#editCommentContent_"+postCommentId).val();//작성항 글의 내용
	
	    if (!content.trim()) {
	        alert("수정 댓글이 비었습니다!");
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
				alert("댓글이 수정되었습니다.");
				window.loadCommentList();
			},
			error: function(){
				alert("댓글 등록에 실패했습니다.");
			}
		});
	}
	
	//삭제 로직
	function commentDelete(postCommentId){
		let url = "/community/detail/delete/"+postCommentId;
	
		if(confirm('댓글을 삭제하시겠습니까?')){
		
			fetch(url, {
				method: 'DELETE'
			}).then(
				function(response){
					if(response.ok){
						alert("댓글이 삭제되었습니다.");
						
						window.loadCommentList();//전역용 댓글리스트 함수 불러오기
					}
				}
			)
		}
	}
	
	

