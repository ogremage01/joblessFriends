<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
	crossorigin="anonymous">
	
</script>
<head>
<link rel="stylesheet" href="/css/community/communityCommonStyle.css">
<link rel="stylesheet" href="/css/community/communitySideStyle.css">
</head>

<style type="text/css">
#boxWrap {
	width: 220px;
	height: 180px;
	padding: 15px;
	margin-bottom: 15px;
}

#boxTextWrap {
	display: flex;
}

#boxWrap>div p {
	color: #6D707B;
	font-size: 12pt;
	margin-top: 8px;
	margin-bottom: 8px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	word-break: break-all;
}

#boxWrap a {
	height: 20px;
	color: #6D707B;
	font-size: 12pt;
	font-weight: bold;
	margin-top: 8px;
	margin-left: auto;
}

#writeBtn {
	color: white;
	background-color: #F69800;
	width: 215px;
	height: 50px;
	border-radius: 5px;
	border: none;
	outline: none;
	box-shadow: none;
	margin-top: 20px;
}

#writeBtn:hover {
	color: #F69800;
	background-color: white;
	border: 1px solid #F69800;
}


.searchBox {
	height: 35px;
	padding-left: 8px;
	padding-bottom: 5px;
	padding-top: 5px;
	width: 241px;
}

#searchBoxPadding {
	display: flex;
}

#searchBoxPadding .searchBox {
	padding: 0px;
	border: none;
	margin-right: 2px;
	width: 210px;
	font-size: 16px;
}

#searchBoxPadding button {
	border: 0;
	background-color: transparent;
	margin-bottom: 2px;
	color: #F69800;
}

#searchBoxPadding button:hover {
	border: 0;
	border-radius: 50px;
	background-color: #F69800;
	color: white;
}
</style>

<script type="text/javascript">
	function moveUpload(){
		location.href = "/community/upload";
	}
</script>

<c:if test="${sessionScope.userLogin ne null}">
	<div id='boxWrap' class="boxStyle">
		<div id='boxTextWrap'>
			<div>
				<p>${sessionScope.userLogin.nickname} 님</p>
				<p>작성한 글 (-)개</p>
				<p>작성한 댓글 (-)개</p>
			</div>
			<a>프로필 변경</a>
		</div>
	
		<button id='writeBtn' onclick="moveUpload()">
			<svg xmlns="http://www.w3.org/2000/svg" width="100" height="50"
				fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 70 16">
			  <path
					d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
			  <path fill-rule="evenodd"
					d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z" />
			  <text x="20" y="13" font-size="14">글쓰기</text>
			</svg>
		</button>
	</div>
</c:if>
<form name="searchContent" method="post" action="">
	<div id='searchBoxPadding' class="boxStyle searchBox">
		<input class="searchBox" type="text" placeholder="게시글을 검색해보세요" />
		<button onclick="sendInfo()"> <!-- 여기서 함수로 검색버튼전송 할건지 form으로 서버 보낼지 고민 -->
			<svg xmlns="http://www.w3.org/2000/svg" width="30" height="40"
				fill="currentColor" class="bi bi-search" viewBox="-4 0 23 23">
			  	<path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0" />
			</svg>
		</button>
	</div>
</form>



