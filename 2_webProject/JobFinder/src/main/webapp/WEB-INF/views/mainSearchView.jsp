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

									<span>🧑‍💻 지원자격:${item.education} </span> <span>🎓 경력:
										${item.careerType}</span> <span>💼 채용직: ${item.jobName}</span>

								</div>
								<div class="job-meta-skill">
									🧩 스킬:
									<c:forEach var="skill" items="${skillMap[item.jobPostId]}">
										<span class="tag">${skill.tagName}</span>
									</c:forEach>
								</div>
							</div>

							<!-- 오른쪽: 버튼 및 마감일 -->
							<div class="job-action">
								<c:choose>
									<c:when test="${item.isContinuous == 0}">
										<button class="apply-btn" type="button" onclick="">지원하기</button>
									</c:when>
									<c:otherwise>
										<button class="apply-btn" type="button" disabled
											style="background: #ccc; cursor: not-allowed;">마감됨</button>
									</c:otherwise>
								</c:choose>

								<div class="deadline">
									~
									<fmt:formatDate value="${item.endDate}" pattern="MM/dd(E)" />
								</div>
							</div>

						</div>
					</c:forEach>
				</div>


				<div id="pagination">
					<c:if test="${pagination.existPrevPage}">
						<button class="page-btn" data-page="${pagination.startPage - 1}">«</button>
					</c:if>

					<c:forEach var="i" begin="${pagination.startPage}"
						end="${pagination.endPage}">
						<button class="page-btn" data-page="${i}"
							${i == page ? 'disabled' : ''}>${i}</button>
					</c:forEach>

					<c:if test="${pagination.existNextPage}">
						<button class="page-btn" data-page="${pagination.endPage + 1}">»</button>
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
	<div id="askConfirm"></div>
	<script>
		
	</script>
	<script src="/js/recruitment/recruitmentView.js"></script>

	<div id="askConfirm" class="toast-popup"></div>

</body>

</html>
