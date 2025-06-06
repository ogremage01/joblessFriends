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
	display: flex;
	justify-content: space-between;
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
	<!-- 헤더부분 끝 -->
	<div id="container">
		<div id='containerWrap'>
			<div class="wrap">
				<div id='communityList'>
					<jsp:include
						page="/WEB-INF/views/community/list/communitySideBar.jsp" />
				</div>



				<!-- 	게시글 리스트 부분 -->
				<div id="SwapContainer">

					<c:if test="${empty noticeList}">
						<div id='noCommunityBox'>
							<span id='noCommunity'> 공지글이 존재하지 않습니다. </span>
						</div>
					</c:if>

					<c:forEach var="notice" items="${noticeList}">
						<div class='boxStyle boxListOne' onclick="moveDetail(this)">
							<input type="hidden" id="noticeNo" value="${notice.noticeId}">
							<div>
								<div class='listTitle'>
									<span class='noticeType'>
										${notice.noticeCategory.noticeCategoryContent} </span>
									<h2>${notice.title}</h2>


								</div>
								<div id='previewContent'>
									<p class="previewText">
										<c:out value="${notice.content}" escapeXml="false" />
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
										style="min-width: 20px; text-align: center; padding-right: 8px">${notice.views}</span>
									<span>|</span>
								</div>

								<div style="display: flex; min-width: 100px; margin-left: 8px">
									<span><fmt:formatDate pattern="yyyy-MM-dd"
											value="${notice.createAt}" /> 작성</span>

								</div>
							</div>

						</div>

					</c:forEach>
				</div>

			</div>
			<div id="pageWrap">
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
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />


	<form id='pagingForm' action="/community/notice" method='get'>
		<input type="hidden" name="page" id="pageInput"> 
		<input type="hidden" name="keyword" id="keywordInput">
	</form>

	<form id="noticeSelectOneForm" action="/community/notice/detail" method="get">
		<input type="hidden" id="noticeFormNo" name="no" value="">
	</form>
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
window.addEventListener('DOMContentLoaded', function () {
	// 현재 URL을 sessionStorage에 저장
	sessionStorage.setItem("prevNoticeListUrl", location.href);
});

	function goToPage(page, keyWord){
		$('#pageInput').val(page);
		$('#keywordInput').val(keyWord);
		$('#pagingForm').submit();
		
		
/* 		.location.href='./community/notice?page=${page}&keyword=${keyWord}'; */
	}
	
	function moveDetail(divElement){
		let noticeIdInput = divElement.querySelector("input[id='noticeNo']");
		let noticeId = noticeIdInput.value;
		
		console.log(noticeId);
		
		let noticeFormNoObj = document.getElementById('noticeFormNo');
		noticeFormNoObj.value = noticeId;
		
		let noticeSelectOneFormObj = document.getElementById('noticeSelectOneForm');
		noticeSelectOneFormObj.submit();

		
	}
	

</script>

</html>
