<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>검색결과</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/common/mainSearchView.css">
<link rel="stylesheet" href="/css/recruitment/recruitmentView.css">
<link rel="stylesheet" href="/css/recruitment/recruitmentNav.css">

<style type="text/css">
body {
	overflow-y: scroll;
}
</style>
</head>


<script
	src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script
	src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

<body>
	<jsp:include page="./common/header.jsp" />

	<div id="container">
		<div id="containerWrap">

			<div id="searchSection" class="resultInfo">
				<h2>검색결과</h2>
<%-- 				<span>(총 ${totalCount}건)</span> --%>
<%-- 				<span>'${keyword}'에 대한 검색결과입니다.</span> --%>
					<span><b>'${keyword}'</b>에 대한 검색결과가 <b>총 ${totalCount}건</b> 있습니다.</span>
			</div>

			<!--		조건: 검색결과 있을 때		-->
			<c:if test="${not empty recruitmentList}">

				<!-- 채용공고 리스트 -->
				<div id="jobListings">
					<c:forEach var="item" items="${recruitmentList}">
						<div class="job" data-jobpostid="${item.jobPostId}"
							data-companyid="${item.companyId}">

							<!-- 왼쪽: 회사명 -->
							<div class="company-name">${item.companyName}</div>

							<!-- 가운데: 공고 정보 -->
							<div class="job-info">
								<div class="job-title">
									${item.title} <span class="star">★</span>
								</div>
								<div class="job-meta">
									<span>‍🎓 ${item.education} </span> <span>🧑 ${item.careerType}</span>
	    	                        <span>💼 ${item.jobName}</span>
								</div>
								<div class="job-meta-skill">
									🧩 
									<c:forEach var="skill" items="${skillMap[item.jobPostId]}">
										<div><span class="tag">${skill.tagName}</span></div>
									</c:forEach>
								</div>
							</div>

							<!-- 오른쪽: 버튼 및 마감일 -->
							<div class="job-action">
								
								
								<div class="deadline">
									~
									<fmt:formatDate value="${item.endDate}" pattern="MM/dd(E)" />
								</div>
							</div>

						</div>
					</c:forEach>
				</div>


				<!-- 기존 코드의 페이지네이션 부분을 아래와 같이 교체하세요 -->
				<div id="pagination">
				    <!-- 이전 페이지 -->
				    <c:if test="${pagination.existPrevPage}">
				        <button class="page-btn" type="button"
				            data-page="${pagination.page - 1}"
				            data-keyword="${keyword}">«</button>
				    </c:if>
				
				    <!-- 페이지 번호 버튼 -->
				    <c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="i">
				        <button class="page-btn ${pagination.page == i ? 'active' : ''}"
				            type="button"
				            data-page="${i}"
				            data-keyword="${keyword}"
				            ${pagination.page == i ? 'disabled' : ''}>${i}</button>
				    </c:forEach>
				
				    <!-- 다음 페이지 -->
				    <c:if test="${pagination.existNextPage}">
				        <button class="page-btn" type="button"
				            data-page="${pagination.page + 1}"
				            data-keyword="${keyword}">»</button>
				    </c:if>
				</div>
					
				</c:if>
			
			<c:if test="${totalCount eq 0}">
				<div id="noResult">
					<span>
						입력하신 키워드에 맞는 검색결과가 없습니다.<br>
						다른 키워드로 검색해보세요.<br>
						( 예시 - 개발자, 신입, 기획자 )
					</span>
				</div>
			</c:if>

		</div>
	</div>



	<jsp:include page="./common/footer.jsp" />

	<script src="/js/recruitment/recruitmentView.js"></script>
	<script src="/js/search/searchRecruitment.js"></script>

	<div id="askConfirm" class="toast-popup"></div>

</body>

</html>
