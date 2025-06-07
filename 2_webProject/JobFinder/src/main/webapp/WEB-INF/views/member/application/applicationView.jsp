<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/member/memberMyPage.css" />
<link rel="stylesheet" href="/css/member/applicationView.css" />
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
                    				<th>지원일</th>
                    				<th>상태</th>
                    			</tr>
                    		</thead>
                    		<tbody>
                    			<c:choose>
                    				<c:when test="${not empty applicationList}">
		                    			<c:forEach var="application" items="${applicationList}">
		                    				<tr class="application">
		                    					<td>
		                    						<a href="/Recruitment/detail?companyId=${application.companyId}&jobPostId=${application.jobPostId}">
		                    							${application.title}
		                    						</a>
		                    					</td>
		                    					<td>${application.companyName}</td>
		                    					<td>
		                    						<fmt:formatDate value="${application.applyDate}" pattern="yyyy-MM-dd"/>
		                    					<td>${application.state}</td>
		                    				</tr>
		                    			</c:forEach>
                    				</c:when>
                    				<c:otherwise>
                    					<tr>
                    						<td class="emptyTd"colspan="4">
                    							<span>아직 지원한 내역이 없습니다.</span>
                    						</td>
                    					</tr>
                    				</c:otherwise>
                    			</c:choose>
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