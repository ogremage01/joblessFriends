<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>직군관리화면</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css"
	rel="stylesheet" crossorigin="anonymous">
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
<link href="/css/admin/common.css" rel="stylesheet">


<style>
/*기본값(default)이 이미 "text/css"로 되어 있어서 자동인식한다하여 뺐음 */
</style>

<script type="text/javascript">
    
    </script>
</head>
<body>
	<main class="d-flex flex-nowrap">
		<!-- 사이드바 영역 -->
		<jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
		<!-- 사이드바 영역 -->
		<!-- 본문영역  -->
		<div id="container">

			<h1 style="text-align: center;">직군 목록</h1>
			<div>
				<input type="text" name="newJobGroup" id="jobGroupInsert">
				<button id="jobGroupInsertBtn" class="btn btn-light">추가</button>
			</div>

			<table class="table table-striped">
				<thead class="table-dark" style="margin: auto;">
					<tr>
						<td><button id="">삭제</button></td>
						<td>ID</td>
						<td>직군명</td>

					</tr>
				</thead>
				<tbody class="table-group-divider" id="jobGroupContent">
					<c:forEach var="jobGroup" items="${jobGroupList}">

						<tr>
							<td style="text-align: center;">
							<input type="checkbox"
								class="delCompany" name="delete" value="${jobGroup.jobGroupId}">
							</td>
							<td>${jobGroup.jobGroupId}</td>
							<td><a>${jobGroup.jobGroupName}</a></td>
						</tr>


					</c:forEach>
				</tbody>
			</table>


			<div id='pageNation'>
				<c:if test="${pagination.totalPageCount > 0}">
				<nav aria-label="...">
				
					<ul class="pagination justify-content-center">
						<li class="page-item ${searchVo.page==1?'disabled':''}">
							<a class="page-link" href="./jobGroup?page=${searchVo.page-1}&keyword=${searchVo.keyword}">Previous</a>
						</li>
						<c:forEach begin="${pagination.startPage}" var="i" 
							end="${pagination.endPage}">
							<li class="page-item ${searchVo.page==i?'active':''}">
							<a class="page-link" href="./jobGroup?page=${i}&keyword=${searchVo.keyword}">${i}</a></li>
						</c:forEach>

						<li class="page-item">
						<a class="page-link ${searchVo.page==pagination.totalPageCount? 'disabled':''}" href="./jobGroup?page=${searchVo.page+1}&keyword=${searchVo.keyword}">Next</a></li>
					</ul>
				
				</nav>
				</c:if>
			</div>

			<div id="searchContainer" class="d-flex justify-content-center my-4">
				<input id="jobGroupKeyword" type="text" placeholder="직군명" value="${searchVo.keyword}">
				<button id="jobGroupSearchBtn" class="btn btn-light">검색</button>
			
			</div>



		</div>
		<!-- 본문영역 -->
	</main>
	</main>

</body>

<script type="text/javascript">


	
	const jobGroupInsertBtn = document.getElementById("jobGroupInsertBtn");
	
	jobGroupInsertBtn.addEventListener('click', function(e) {
		const jobGroupInsertVal = document.getElementById("jobGroupInsert").value;
		
		
	});
	
	
	const searchjobGroupBtn = document.getElementById("jobGroupSearchBtn");
	const jobGroupKeywordInput = document.getElementById("jobGroupKeyword");

	function performSearch() {
	    const jobGroupKeywordVal = jobGroupKeywordInput.value.trim();
	    
	    // 검색어가 있을 때와 없을 때 모두 처리
	    if (jobGroupKeywordVal !== "") {
	    	let url = '/admin/job/jobGroup?page=1&keyword=' + encodeURIComponent(jobGroupKeywordVal);
	        location.href = url;
	    } else {
	        // 검색어가 없으면 전체 목록으로
	        location.href = `/admin/job/jobGroup?page=1`;
	    }
	}

	searchjobGroupBtn.addEventListener("click", performSearch);
	
	// Enter 키 이벤트 추가
	jobGroupKeywordInput.addEventListener("keypress", function(e) {
	    if (e.key === "Enter") {
	        performSearch();
	    }
	});
	

</script>
</html>