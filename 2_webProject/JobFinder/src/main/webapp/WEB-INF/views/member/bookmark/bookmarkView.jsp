<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지-내가 찜한 공고</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/member/memberMyPage.css" />
<link rel="stylesheet" href="/css/member/bookmarkView.css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

<style type="text/css">
</style>

</head>
<body>

	<jsp:include page="../../common/header.jsp" />

	<div class="container">

		<div id="containerWrap">
			<div id="sideBar">
				<jsp:include page="../../common/mypageSidebar.jsp" />
			</div>
			<div class="main">

				<div class="content">
					<h1 class="mainTitle">
						<i class="bi bi-star-fill" style="color: #F69800;"></i>
						내가 찜한 공고
					</h1>

					<!-- 채용공고 리스트 -->
					<div id="jobListings">
					<c:if test="${empty recruitmentList}">
                        <div class="no-bookmark">
                            <span>찜한 공고가 없습니다.</span>
                            <span>관심 있는 공고를 찜해보세요!</span>
                            <a href='/Recruitment/list'>찜 하러 가기 →</a>
                        </div>
                    </c:if>
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

										<span>🎓 ${item.education} </span>
										<span>🧑 ${item.careerType}</span>
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
									<button class="deleteBookmark" type="button" data-jobpostid="${item.jobPostId}"><i class="bi bi-trash"></i> 취소하기</button>
									<div class="deadline">
										~
										<fmt:formatDate value="${item.endDate}" pattern="MM/dd(E)" />
									</div>
									<c:if test="${item.isContinuous eq 1}">
										<span>마감</span>
									</c:if>
									<c:if test="${item.isContinuous eq 0}">
										<span>채용중</span>
									</c:if>
								</div>
							</div>
						</c:forEach>
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
			</div>

		</div>
	</div>


	<jsp:include page="../../common/footer.jsp" />

</body>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<!-- CDN 사용 -->
<script src="/js/member/bookmarkView.js"></script>



</html>