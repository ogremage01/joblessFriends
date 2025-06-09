<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
	<title>직무관리 - 어디보잡 관리자</title>
	<meta charset="UTF-8">
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
		integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js" 
		integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
	
	<!-- 공통 스타일 적용 -->
	<link rel="stylesheet" href="/css/common/common.css">
	<link href="/css/admin/common.css" rel="stylesheet">
	<link href="/css/admin/adminStyle.css" rel="stylesheet">
	
</head>
<body>

<div id="container">
	<div id="containerWrap">
		<div class="admin-container">
			<!-- 사이드바 영역 -->
			<div class="admin-sidebar">
				<jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
			</div>
			
			<!-- 메인 컨텐츠 영역 -->
			<div class="admin-main">
				<div class="admin-header">
					<h1>
						<i class="bi bi-gear-fill" style="color: #F69800;"></i>
						직무 관리
					</h1>
				</div>
				
				<div class="admin-content">
					<div class="admin-table-header">
						<h2><i class="bi bi-list-ul"></i> 직무 목록</h2>
						<button id="massDelJob" class="mass-delete-btn">
							<i class="bi bi-trash"></i> 선택 삭제
						</button>
					</div>
					
					<!-- 직무 추가 섹션 -->
					<div class="add-section">
						<input type="text" name="newJobName" id="jobInsert" class="add-input" 
							   placeholder="새 직무명을 입력하세요">
						<select id="jobGroupSelect" class="add-input" style="max-width: 200px;">
							<option value="">직군 선택</option>
							<c:forEach var="jobGroup" items="${jobGroupList}">
								<option value="${jobGroup.jobGroupId}">${jobGroup.jobGroupName}</option>
							</c:forEach>
						</select>
						<button id="jobInsertBtn" class="add-btn">
							<i class="bi bi-plus-circle"></i> 추가
						</button>
					</div>
					
					<table class="table admin-table">
						<thead>
							<tr>
								<th scope="col"><button id="selectAll">전체 선택</button></th>
								<th scope="col">직무 ID</th>
								<th scope="col">직무명</th>
								<th scope="col">직군</th>
								<th scope="col">삭제</th>
							</tr>
						</thead>
						<tbody id="jobContent">
							<c:forEach var="job" items="${jobList}">
								<tr>
									<td class="checkbox-container">
										<input type="checkbox" class="delJob admin-checkbox" 
											   name="delete" value="${job.jobId}">
									</td>
									<td><strong>${job.jobId}</strong></td>
									<td class="job-name">${job.jobName}</td>
									<td class="job-group">${job.jobGroupName}</td>
									<td>
										<button class="delBtn delete-btn" value="${job.jobId}">
											삭제
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<!-- 페이지네이션 -->
					<div class="pagination-container">
						<c:if test="${pagination.totalPageCount > 0}">
							<nav aria-label="페이지 네비게이션">
								<ul class="pagination">
									<li class="page-item ${searchVo.page==1?'disabled':''}">
										<a class="page-link" href="/admin/job/singleJob?page=${searchVo.page-1}&keyword=${searchVo.keyword}">
											«
										</a>
									</li>
									<c:forEach begin="${pagination.startPage}" var="i" end="${pagination.endPage}">
										<li class="page-item ${searchVo.page==i?'active':''}">
											<a class="page-link" href="/admin/job/singleJob?page=${i}&keyword=${searchVo.keyword}">${i}</a>
										</li>
									</c:forEach>
									<li class="page-item ${searchVo.page==pagination.totalPageCount? 'disabled':''}">
										<a class="page-link" href="/admin/job/singleJob?page=${searchVo.page+1}&keyword=${searchVo.keyword}">
											»
										</a>
									</li>
								</ul>
							</nav>
						</c:if>
					</div>

					<!-- 검색 영역 -->
					<div class="search-container">
						<input id="jobKeyword" type="text" class="search-input" 
							   placeholder="직무명으로 검색" value="${searchVo.keyword}">
						<button id="jobSearchBtn" class="search-btn">
							<i class="bi bi-search"></i> 검색
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="/js/admin/job/jobManagement.js"></script>

</body>
</html> 