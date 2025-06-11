<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기업 페이지-공고란</title>

<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous"> -->
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js"
	integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">

<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/member/memberMyPage.css" />
<link rel="stylesheet" href="/css/company/recruitmentList.css">
</head>
<body>
<jsp:include page="../../common/header.jsp"/>

	<div class="container">
		<div id="containerWrap">
			<jsp:include page="../companyPage/companyPageSidebar.jsp" />
			<!-- 메인 -->
			<div class="main">
				<div class="content">
					<h1 class="mainTitle">공고 관리</h1>
					<div class="tableWrap">
						<button onclick="location.href='/Recruitment/insert'" id="newPost" class="btn">
							<i class="bi bi-plus-circle"></i> 공고 작성하기
						</button>
	
						<table id="recruitmentList" class="table table-border">
							<thead id="table-header" class="table-group-divider">
								<tr>
									<th>공고</th>
									<th colspan="2">관리</th>
								</tr>
							</thead>
		
							<c:if test="${recruitmentList.size() eq 0}">
								<tbody>
									<tr>
										<td colspan="3"><span id="emptyList">등록된 공고가 없습니다</span></td>
									</tr>
								</tbody>
							</c:if>
		
							<c:forEach var="recruitment" items="${recruitmentList}">
								<tbody id="recruitment${recruitment.jobPostId}" class="recruitmentRow">
									<tr>
										<td>
											<h5 id="title${recruitment.jobPostId}" class="jobPostTitle">
												<a href="/Recruitment/detail?companyId=${recruitment.companyId}&jobPostId=${recruitment.jobPostId}">
													${recruitment.title}
												</a>
												<c:if test="${recruitment.isContinuous eq true}">
													<span class="stoppedPost">마감</span>
												</c:if>
											</h5>
										</td>
										<td class="buttonPlace" rowspan="2">
											<button onclick="location.href='/Recruitment/update?jobPostId=${recruitment.jobPostId}'" class="btn btn-light mb-2">
												<i class="bi bi-pencil-square"></i> 수정하기
											</button>
											<button onclick="location.href='/company/apply/${recruitment.jobPostId}/applicants'" class="btn btn-light mb-2">
												<i class="bi bi-file-person"></i> 지원자 보기
											</button>
											<button id="stopBtn${recruitment.jobPostId}" class="btn btn-light mb-2 stopBtn" value="${recruitment.jobPostId}" <c:if test="${recruitment.isContinuous eq true}">disabled</c:if>>
												<i class="bi bi-sign-stop"></i> 마감하기
											</button>
										</td>
										<td rowspan="2" class="deletePlace">
											<button class="btn delBtn" value="${recruitment.jobPostId}">
												<i class="bi bi-x"></i>
											</button>
										</td>
									</tr>
									<tr>
										<td>
											<table class="innerTable">
												<tr>
													<td class="name">지원자격</td>
													<td class="info">${recruitment.education}</td>
													<td class="name">채용부문</td>
													<td class="info">${recruitment.jobName}</td>
													<td class="name">공고마감</td>
													<td class="info"><fmt:formatDate
															value="${recruitment.endDate}" /></td>
												</tr>
												<tr>
													<td class="name">스킬</td>
													<td class="info"><c:forEach var="skill"
															items="${recruitment.skillList}" varStatus="status"
															begin="0" end="1">
		                  ${skill.tagName}
		                  <c:if test="${!status.last}">, </c:if>
														</c:forEach></td>
													<td class="name">채용인원</td>
													<td class="info">${recruitment.maxApplicants}명</td>
													<td class="name">지원자</td>
													<td class="info">${recruitment.applicantCount}</td>
												</tr>
												<tr>
													<td class="name">최종 수정일</td>
													<td class="info" colspan="5"><fmt:formatDate
															value="${recruitment.modifiedDate eq null ? recruitment.endDate : recruitment.modifiedDate}" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</tbody>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../../common/footer.jsp" />
</body>

	<script src="/js/company/recruitmentList.js"></script>

</html>