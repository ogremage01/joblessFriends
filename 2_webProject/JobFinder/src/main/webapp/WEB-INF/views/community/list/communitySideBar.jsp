<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
	crossorigin="anonymous">
	
</script>

<head>
<link rel="stylesheet" href="/css/community/communityCommonStyle.css">
<link rel="stylesheet" href="/css/community/communitySideStyle.css">
</head>

<script type="text/javascript">
	function moveUpload() {
		location.href = "/community/upload";
	}
</script>

<style>
.moveBtn{
	width: 210px;
	height: 40px;
	
	margin-bottom: 5px;
	
	text-align: center;
}
</style>

<c:choose>
	<c:when test="${sessionScope.userLogin ne null}">
		<c:choose>
			<c:when test="${sessionScope.userType eq 'member'}">
				<div id='boxWrap' class="boxStyle sideBarBoxStyle"  style="height: 120px">
					<div id='boxTextWrap' >
						<div style="height: 50px">
							<p class="nameBox"><span class="nickName">${sessionScope.userLogin.nickname}</span></p>
						</div>
						<a style="height: 50px" href="./member/info">프로필 변경</a>
					</div>
					<button id='writeBtn' onclick="moveUpload()">
						<svg xmlns="http://www.w3.org/2000/svg" width="100" height="50"
							fill="currentColor" class="bi bi-pencil-square"
							viewBox="0 0 70 16">
						  <path
								d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
						  <path fill-rule="evenodd"
								d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z" />
						  <text x="20" y="13" font-size="14">글쓰기</text>
						</svg>
					</button>
				</div>
			</c:when>
			<c:when test="${sessionScope.userType eq 'company'}">
				<div id='boxWrap' class="boxStyle sideBarBoxStyle" style="height: 120px">
					<div id='boxTextWrap'>
						<div style="height: 50px">
							<p class="nameBox"><span class="nickName">${sessionScope.userLogin.companyName}</span> 님</p>
							<p style="margin-top: 30px; color: #6D707B;">커뮤니티 작성은 개인 회원 전용 기능입니다.</p>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div id='boxWrap' class="boxStyle sideBarBoxStyle">
					<div id='boxTextWrap'>
						<div>
							<p>관리자님 환영합니다.</p>
							<button class='moveBtn' onclick="moveAdminPost()">
								게시판 관리 페이지
							</button>
							<button class='moveBtn' onclick="moveAdminNotice()">
								공지 관리 페이지
							</button>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div id='boxWrap' class="boxStyle sideBarBoxStyle">
			<div id='boxTextWrap'>
				<div style="margin-top: 15px;">
					<p style="margin-top: 0px">커뮤니티 작성은 개인 회원 전용 기능입니다.</p>
					<p style="margin-bottom: 0px">로그인을 진행해주세요.</p>
					<button id='writeBtn' onclick="moveLogin()">
						<svg xmlns="http://www.w3.org/2000/svg" width="150" height="50"
							fill="currentColor" class="bi bi-pencil-square"
							viewBox="0 0 100 16">
						  <text x="5" y="13" font-size="14">로그인 하러가기</text>
						</svg>
					</button>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>

<form name="searchContent" onsubmit="sendInfo(); return false;"
	action="/community">
	<div id='searchBoxPadding' class="boxStyle searchBox">
		<input id='searchStr' class="searchBox" type="text" name='keyword'
			placeholder="게시글을 검색해보세요" value="${searchVo.keyword}"/>
		<button type="submit">
			<!-- 여기서 함수로 검색버튼전송 할건지 form으로 서버 보낼지 고민 -->
			<svg xmlns="http://www.w3.org/2000/svg" width="30" height="40"
				fill="currentColor" class="bi bi-search" viewBox="-4 0 23 23">
			  	<path
					d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0" />
			</svg>
		</button>
	</div>
</form>

<c:set var="uri" value="${pageContext.request.requestURI}" />
<c:choose>
	<c:when test="${fn:contains(uri, '/notice')}">
		<div class="switchNotiComm">
			<!-- 공지사항이 현재 페이지일 때 -->
			<div onclick="moveCommunity()">자유 게시판</div>
			<span style="color: lightgray">|</span>
			<div class="selectedPage">공지 사항</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="switchNotiComm">
			<!-- 커뮤니티가 현재 페이지일 때 -->
			<div class="selectedPage">자유 게시판</div>
			<span style="color: lightgray">|</span>
			<div onclick="moveNotice()">공지 사항</div>
		</div>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
	function sendInfo()
	{
		const keyword = $('#searchStr').val();

		if(keyword==null || keyword==""){
			location.href="/community";
			return;
		}
		
		$('#pageInput').val(1); // 검색 시 첫 페이지로 초기화
		$('#keywordInput').val(keyword);
		$('#pagingForm').submit();
		

	}
	function moveLogin(){
		location.href="/auth/login";
	}
	function moveAdminPost(){
		location.href="/admin/community/post";
	}
	function moveAdminNotice(){
		location.href="/admin/community/notice";
	}
	
	function moveNotice(){
		location.href='/community/notice';
	}
	
	function moveCommunity(){
		location.href='/community';
	}
</script>



