<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="/css/community/commueCommonStyle.css">

<style type="text/css">
.wrap {
	display: flex;
	justify-content: space-between;
	
	margin-top: 100px;
	
}

.contentBox {
	width: 860px;
	padding: 30px;
	min-height: 200px;
	margin-bottom: 20px;
}

.contentBox h2 {
	max-width: 870px;
	margin: 0px;
	margin-bottom: 13px;
}

#contentText {
	margin-top: 20px;
	white-space: pre-line;
	overflow-wrap: break-word;
}

#bottomSide {
	display: flex;
	margin-top: 50px;
	color: #6D707B;
}

#aTags {
	margin-left: auto;
}

#aTags a {
	margin-left: 20px;
}


</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div>
	<div class='wrap'>
		<div>
			<jsp:include page="/WEB-INF/views/commue/list/commueSideBar.jsp" />
		</div>
		<div>
			<div class='boxStyle contentBox'>
				<h2>제목</h2>
				<div id='infoContent'>
					<div>
						<svg xmlns="http://www.w3.org/2000/svg" width="80" height="24"
							fill="#BABECA" class="bi bi-eye" viewBox="0 0 75 16">
				  	<path
								d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z" />
				  	<path
								d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0" />
					<text x="22" y="13" font-size="12">(조회수)</text>
					<text x="67" y="12" font-size="12">|</text>
				</svg>
					</div>
					<div>
						<svg xmlns="http://www.w3.org/2000/svg" width="110" height="24"
							fill="#BABECA" class="bi bi-chat-left" viewBox="0 -2 100 16">
				  	<text x="0" y="12" font-size="12">(날짜) 작성</text>
				</svg>
					</div>
				</div>

				<div id='contentText'>게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는
					곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는
					곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글
					들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는
					곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글
					들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는
					곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글 들어가는 곳게시글
					
					</div>

				<div id='bottomSide'>
					<p style="margin: 0px;">작성자 (작성자명)</p>
					<div id='aTags'>
						<a>수정</a> 
						<a>삭제</a>
					</div>
				</div>
			</div>
			<div>
				<jsp:include page="/WEB-INF/views/commue/detail/commueReply.jsp" />
			</div>

		</div>

	</div>
</div>	
</body>
</html>