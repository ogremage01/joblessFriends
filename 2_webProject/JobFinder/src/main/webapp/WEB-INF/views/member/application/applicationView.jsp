<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/member/memberMyPage.css" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>

<jsp:include page="../../common/header.jsp"/>

	<div class="container">
		<div id="containerWrap">
			<jsp:include page="../../common/mypageSidebar.jsp"/>
		    <div class="main">
		    
		    	<div class="content">
			    	<h1 class="mainTitle">
			    		<i class="bi bi-briefcase" style="color: #F69800;"></i>
			    		구직활동 내역
			    	</h1>
			    	<div class="tableWrap">
				    	<table class="applicationTable">
                    		<thead>
                    			<tr>
                    				<th>공고명</th>
                    				<th>회사명</th>
                    				<th>상태</th>
                    			</tr>
                    		</thead>
                    		<tbody>
                    			<c:forEach var="application" items="${applicationList}">
                    				<tr>
                    					<td><a href="/job/detail?jobId=${application.jobId}">${application.jobTitle}</a></td>
                    					<td>${application.companyName}</td>
                    					<td>${application.status}</td>
                    				</tr>
                    			</c:forEach>
                    		</tbody>
                    	</table>
			    	</div>
		    	</div>
		      
		    </div>
		</div> 
		
							<!-- 페이지네이션 배치 -->
					<c:if test="${pagination.totalPageCount > 1}">
						<div id="pageWrap">
							<div id="pagination">
								<!-- 이전 페이지 -->
								<button class="page-btn" ${searchVo.page == 1 ? 'disabled' : ''}
									onclick="goToPage(${searchVo.page - 1}, '${searchVo.keyword}')">«</button>

								<!-- 페이지 번호 -->
								<c:forEach begin="${pagination.startPage}"
									end="${pagination.endPage}" var="i">
									<button class="page-btn ${searchVo.page == i ? 'active' : ''}"
										${searchVo.page == i ? 'disabled' : ''}
										onclick="goToPage(${i},  '${searchVo.keyword}')">${i}</button>
								</c:forEach>

								<!-- 다음 페이지 -->
								<button class="page-btn"
									${searchVo.page == pagination.totalPageCount ? 'disabled' : ''}
									onclick="goToPage(${searchVo.page + 1}, '${searchVo.keyword}')">»</button>

							</div>

							<form id='pagingForm' action="/member/bookmark" method='get'>
								<input type="hidden" name="page" id="pageInput"> <input
									type="hidden" name="keyword" id="keywordInput">
							</form>
						</div>
					</c:if>   
	</div>
	
<jsp:include page="../../common/footer.jsp" />

</body>
</html>