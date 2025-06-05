<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 목록</title>

<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/community/communityNav.css">
<link rel="stylesheet" href="/css/community/page.css">
<link rel="stylesheet" href="/css/community/communityCommonStyle.css">
<link rel="stylesheet" href="/css/community/communityListOneStyle.css">

<style type="text/css">
.wrap {
	width: 1210px; display : flex;
	justify-content: space-between;
	display: flex;
}

#communityList {
	margin-bottom: 15px;
	padding: 0px;
}
</style>

</head>

<body>
	<!-- 헤더부분 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<!-- 헤더부분 끝ㄴ -->
	<div id="container">
		<div id='containerWrap'>
			<div class="wrap">
				<div id='communityList'>
					<jsp:include
						page="/WEB-INF/views/community/list/communitySideBar.jsp" />
				</div>



				<!-- 	게시글 리스트 부분 -->
				<div>


					<!-- 공지사항 항목 뜨는 부분 -->
					<!-- 			<div id='notice' class='boxStyle ' onclick="moveNoticePage(this)">
				
				<div id='noticeType'>
					공지
				</div>
			</div> -->
					<!-- 공지사항 항목 뜨는 부분 끝 -->



					<c:if test="${empty communityList}">
						<div id='noCommunityBox'>
							<span id='noCommunity'> 게시글이 존재하지 않습니다. </span>
						</div>
					</c:if>

					<c:forEach var="community" items="${communityList}">
						<div class='boxStyle boxListOne' onclick="moveDetail(this)">
							<input type="hidden" id="communityNo"
								value="${community.communityId}">
							<div>
								<div>
									<h2 class='titleBox'>${community.title}</h2>
								</div>
								<div id='previewContent'>
									<p class="previewText">
										<c:out value="${community.content}" escapeXml="false" />
									</p>
								</div>
							</div>
							<div id='infoContent'>
								<div style="display: flex; min-width: 60px;">
									<svg xmlns="http://www.w3.org/2000/svg" width="20" height="24"
										fill="#a2a6b1" class="bi bi-eye" viewBox="0 2 20 14">
							  	<path
											d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z" />
							  	<path
											d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0" />
							</svg>
									<span
										style="min-width: 20px; text-align: center; padding-right: 8px">${community.views}</span>
									<span>|</span>
								</div>

								<div id='commentCount' style="min-width: 60px; display: flex;">
									<svg xmlns="http://www.w3.org/2000/svg" width="20" height="24"
										fill="#a2a6b1" class="bi bi-chat-left" viewBox="-3 -1 20 18">
							  	<path
											d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z" />
							</svg>
									<span style="min-width: 30px; text-align: center;">${community.commentCount}</span>
									<span>|</span>
								</div>
								<div
									style="display: flex; gap: 10px; width: 120px; margin-left: 8px">
									<span><fmt:formatDate pattern="yyyy-MM-dd"
											value="${community.createAt}" /> 작성</span> <span>|</span>
								</div>

								<span class="infoWriter" style="width: 300px;">작성자:${community.nickname}</span>
							</div>

						</div>

					</c:forEach>
				</div>
			</div>
			<div id="pageWrap" >
				<div id="pagination">

					<!-- 이전 페이지 -->
					<c:if test="${pagination.existPrevPage}">
		                <button class="page-btn" ${searchVo.page == 1 ? 'disabled' : ''}
								onclick="goToPage(${searchVo.page - 1}, '${searchVo.keyword}')">«</button>
            		</c:if>

					<!-- 페이지 번호 -->
					<c:forEach begin="${pagination.startPage}"
						end="${pagination.endPage}" var="i">
						<button class="page-btn ${searchVo.page == i ? 'active' : ''}"
							${searchVo.page == i ? 'disabled' : ''}
							onclick="goToPage(${i}, '${searchVo.keyword}')">${i}</button>
					</c:forEach>

					<!-- 다음 페이지 -->
					<c:if test="${pagination.existNextPage}">
						<button class="page-btn"
							${searchVo.page == pagination.totalPageCount ? 'disabled' : ''}
							onclick="goToPage(${searchVo.page + 1}, '${searchVo.keyword}')">»</button>
					</c:if>

				</div>
			</div>
		</div>

		<jsp:include page="/WEB-INF/views/common/footer.jsp" />


		<form id='pagingForm' action="/community" method='get'>
			<input type="hidden" name="page" id="pageInput"> <input
				type="hidden" name="keyword" id="keywordInput">
		</form>

		<form id="communitySelectOneForm" action="/community/detail"
			method="get">
			<input type="hidden" id="communityFormNo" name="no" value="">
		</form>
	</div>
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

window.addEventListener('DOMContentLoaded', function () {
	// 현재 URL을 sessionStorage에 저장
	sessionStorage.setItem("prevCommunityListUrl", location.href);
});

	function goToPage(page, keyWord){
		$('#pageInput').val(page);
		$('#keywordInput').val(keyWord);
		$('#pagingForm').submit();
		
		
/* 		.location.href='./community?page=${page}&keyword=${keyWord}'; */
	}
	
	function moveDetail(divElement){
		let communityIdInput = divElement.querySelector("input[id='communityNo']");
		let communityId = communityIdInput.value;
		
		let communityFormNoObj = document.getElementById('communityFormNo');
		communityFormNoObj.value = communityId;
		
		let communitySelectOneFormObj = document.getElementById('communitySelectOneForm');
		communitySelectOneFormObj.submit();

		
	}
	

</script>

</html>
