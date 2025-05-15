<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/community/communityCommonStyle.css">
<link rel="stylesheet" href="/css/community/comment/commentStyle.css">
<link rel="stylesheet"
	href="/css/community/comment/commentWrapStyle.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/js/community/communityDetail.js"></script>
<script src="/js/community/postComment.js"></script>

</head>

<body>
	<div id="replyWrap" class="boxStyle contentBox">
		<form id="commentForm" method="post" action="">
			<div id="inputReplyWrap">
				<p>댓글</p>
				<textarea id="inputReplyBox" class="boxStyle" placeholder="댓글을 입력해주세요."></textarea>
				<div id="replyBtnWrap">
					<p>0/1000자</p>
					<button type="button" id="inputCommentBtn" class="inputBtn"
						>등록</button>
				</div>
			</div>
		</form>
		<input type="hidden" id="communityNo" value="${community.communityId}" />
		<script type="text/javascript">
			const memberId = ${sessionScope.userLogin.memberId};
		</script>
		
		
		<!-- 댓글 리스트가 여기에 들어옴 -->
		<div id="commentContainer"></div>
	</div>


<script type="text/javascript">
$(document).ready(function () {
	const communityId = $("#communityNo").val();

	  // 폼 기본 submit 막기
    $("#commentForm").submit(function(e) {
        e.preventDefault();
    });
	
	$("#inputCommentBtn").click(function() {
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

	                html += `<input type="hidden" name="no" value="\${communityId}">`;
         
	                comments.forEach(function (comment) {	
	                	var date = new Date(${comment.createAt});
	                	
	                	var year = date.getFullYear();
	                	var month = date.getMonth();
	                	var day = date.getDate();

	                	
	                	console.log("변환전: "+comment.createAt);
	                	console.log("변환후: "+date);
	                	
	                	date= date.getFullYear();
	                	console.log("변환후22: "+date);
	                	
						html += `
							<div id="commentList">        
	                        <input type="hidden" id="communityNo_\${comment.postCommentId}" value="\${comment.postCommentId}">
	                        <div id="commentlistNo_\${comment.postCommentId}" class="commentlist">
	                        	<div>
	                	        	<p id='memName' class="commentBoxStyle">\${comment.nickname}</p>
	                	            <p class="commentBoxStyle">\${comment.content}</p>  
	                	            <p class='commentBottom'>
	            	            	<span>\${year}/\${month}/\${day}  작성</span>
	                	            	<a onclick=''>수정</a> 
	                	            	<a onclick=''>삭제</a>
	                	            </p>
	                			</div>
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
	
	//댓글 등록 버튼 클릭 시 댓글 작성 요청
	function uploadComment(communityId, memberId){
		const urlStr = "/community/detail/commentUpload/" + communityId;
		
	    const content = $("#inputReplyBox").val();//작성항 글의 내용
	
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
				$("#inputReplyBox").val(''); //입력창 비우기
			},
			error: function(){
				alert("댓글 등록에 실패했습니다.");
			}
		});
	
	
	}

	
	loadCommentList();
});

	

</script>

</body>
</html>
