<!-- 관리자 로그인 여부를 묻는 자바구문이 들어가야 할 부분 -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>adminMain</title>
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


<style>
/*기본값(default)이 이미 "text/css"로 되어 있어서 자동인식한다하여 뺐음 */
#container {
	margin: auto;
}

#pageNation {
	margin: auto;
}
</style>

<script type="text/javascript">
	
</script>
</head>
<body>
	<main class="d-flex flex-nowrap">
		<!-- 사이드바 영역 -->
		<div class="flex-shrink-0 p-3"
			style="width: 280px; height: 100vh; border-right: 1px solid black;">
			<a href="#"
				class="d-flex align-items-center pb-3 mb-3 link-body-emphasis text-decoration-none border-bottom">
				<svg class="bi pe-none me-2" width="30" height="24"
					aria-hidden="true">
					<use xlink:href="#bootstrap" /></svg> <span class="fs-5 fw-semibold">관리자
					화면</span>
			</a>
			<ul class="list-unstyled ps-0">
				<li class="mb-1"><a
					class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
					href="/admin/main"> Home </a></li>
				<li class="mb-1">
					<button
						class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
						data-bs-toggle="collapse" data-bs-target="#member-collapse"
						aria-expanded="false">회원관리</button>
					<div class="collapse" id="member-collapse">
						<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
							<li><a href="/admin/member/individual"
								class="link-body-emphasis d-inline-flex text-decoration-none rounded">일반회원</a></li>
							<li><a href="/admin/member/company"
								class="link-body-emphasis d-inline-flex text-decoration-none rounded">기업회원</a></li>
							<!-- <li><a href="/admin/admin" class="link-body-emphasis d-inline-flex text-decoration-none rounded">관리자</a></li> -->
						</ul>
					</div>
				</li>
				<li class="mb-1"><a href="/admin/recruitment"
					class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
						공고관리 </a></li>
				<li class="mb-1">
					<button
						class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
						data-bs-toggle="collapse" data-bs-target="#community-collapse"
						aria-expanded="false">커뮤니티관리</button>
					<div class="collapse" id="community-collapse">
						<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
							<li><a href="/admin/community"
								class="link-body-emphasis d-inline-flex text-decoration-none rounded">게시판
									관리</a></li>
						</ul>
					</div>
				</li>
				<li class="mb-1">
					<button
						class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
						data-bs-toggle="collapse" data-bs-target="#job-collapse"
						aria-expanded="false">직군/직무관리</button>
					<div class="collapse" id="job-collapse">
						<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
							<li><a href="/admin/job/jobGroup"
								class="link-body-emphasis d-inline-flex text-decoration-none rounded">직군관리</a></li>
							<li><a href="/admin/job/job"
								class="link-body-emphasis d-inline-flex text-decoration-none rounded">직무관리</a></li>
						</ul>
					</div>
				</li>
				<li class="mb-1"><a href="/admin/skill"
					class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
						스킬관리 </a></li>
				<li class="mb-1"><a href="/admin/chat"
					class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
						채팅관리 </a></li>
				<li class="border-top my-3"></li>
			</ul>
			<a href="/admin/logout"
				class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">로그아웃</a>
		</div>
		<!-- 사이드바 영역 -->
		<!-- 본문영역 -->
		
		
		
		<div id="container">
		
			<h1 style="text-align: center;">기업 회원 목록</h1>
			<table class="table table-striped">
				<thead class="table-dark" style="margin: auto;">
					<tr>
						<td><button id="massDelCom">탈퇴</button></td>
						<td>ID</td>
						<td>회사명</td>
						<td>Email</td>
						<td>연락처</td>
					</tr>
				</thead>
				<tbody class="table-group-divider">
					<c:forEach var="companyVo" items="${companyList}">
						<tr>
							<td style="text-align: center;"><input type="checkbox" class="delCompany" name="delete" value="${companyVo.companyId}"></td>
							<td>${companyVo.companyId}</td>
							<td><a href="./company/detail?companyId=${companyVo.companyId}">${companyVo.companyName}</a></td>
							<td>${companyVo.email}</td>
							<td>${companyVo.tel}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<div id='pageNation'>

				<nav aria-label="...">
					<ul class="pagination justify-content-center">
						<li class="page-item ${curPage==0?'disabled':''}">
							<a class="page-link" href="./company?page=${curPage-1}">Previous</a>
						</li>
						<c:forEach begin="0" var="i" end="${totalPage-1}">
							<li class="page-item ${curPage==i?'active':''}">
							<a class="page-link" href="./company?page=${i}">${i+1}</a></li>
						</c:forEach>

						<!-- <li class="page-item active" aria-current="page"><a
						class="page-link" href="#">2</a></li> -->
						<li class="page-item"><a
							class="page-link ${curPage==totalPage-1? 'disabled':''}" href="./company?page=${curPage+1}">Next</a></li>
					</ul>
				</nav>
			</div>



		</div>
		<!-- 본문영역 -->
	</main>
</body>

<script type="text/javascript">

	
	const massDelComBtn = document.getElementById("massDelCom");
	
	massDelComBtn.addEventListener("click", function(e) {
		const delCompanyArr = document.querySelectorAll(".delCompany:checked");
		const confirmed = confirm("삭제하시겠습니까?");
		
		if(confirmed&&delCompanyArr.length>0){
			
			const jsonData = [];
			
			delCompanyArr.forEach(cb=>{
	
				jsonData.push(cb.value);
			});
		
			console.log(jsonData);
			
			fetch('/admin/member/company/massDelete',{
	
				method: 'POST',
				headers: {
					'content-type': 'application/json'
				
				},
				body: JSON.stringify(jsonData)
		
			})
			.then(response => response.json())
			.then(data => {

				if(data===delCompanyArr.length){
				    console.log('Success:', data);
				    alert("삭제 완료");
				    location.reload();
				}else{

					alert("삭제 실패");
				    location.reload();
				
				}
			})
			.catch(error => {
			    console.error('Error:', error);
			    alert("삭제 실패");
			});
		
		}else{

			alert("선택된 기업이 없습니다.");
		}
	});
	


</script>

	


</html>
