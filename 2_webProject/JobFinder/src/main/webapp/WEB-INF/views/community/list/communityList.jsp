<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 목록</title>

<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/community/communityNav.css"> 
<link rel="stylesheet" href="/css/community/page.css"> 
<style type="text/css">	
	.wrap {
		display: flex;
		justify-content: space-between;
	}

	#communityList {
		margin-bottom: 15px;
	}
</style>
</head>

<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>

	<div id='containerWrap' class="wrap">
		<div id='communityList'>
			<jsp:include page="/WEB-INF/views/community/list/communitySideBar.jsp"/>
		</div>	

		<div>
			<jsp:include page="/WEB-INF/views/community/list/communityListOne.jsp"/>
		</div>
		
		<jsp:include page="/WEB-INF/views/community/topBar.jsp"/>
	</div>
	<div id="pageWrap">
		<div id="pagination">
			<!-- 이전 페이지 -->
			<button class="page-btn" 
			        ${searchVo.page == 1 ? 'disabled' : ''}
			        onclick="goToPage(${searchVo.page - 1}, '${searchVo.keyword}')">«</button>
	
			<!-- 페이지 번호 -->
			<c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="i">
				<button class="page-btn ${searchVo.page == i ? 'active' : ''}"
				        ${searchVo.page == i ? 'disabled' : ''}
				        onclick="goToPage(${i}, '${searchVo.keyword}')">${i}</button>
			</c:forEach>
	
			<!-- 다음 페이지 -->
			<button class="page-btn"
			        ${searchVo.page == pagination.totalPageCount ? 'disabled' : ''}
			        onclick="goToPage(${searchVo.page + 1}, '${searchVo.keyword}')">»</button>

		</div>
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	
	
	<form id='pagingForm' action="/community" method='get'>
		<input type="hidden" name="page" id="pageInput">
		<input type="hidden" name="keyword" id="keywordInput">
	</form>
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

	function goToPage(page, keyWord){
		$('#pageInput').val(page);
		$('#keywordInput').val(keyWord);
		$('#pagingForm').submit();
		
		
/* 		.location.href='./community?page=${page}&keyword=${keyWord}'; */
	}
</script>

</html>
