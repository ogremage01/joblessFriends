<!-- 관리자 로그인 여부를 묻는 자바구문이 들어가야 할 부분 -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>기업회원관리</title>
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
<script
  src="https://code.jquery.com/jquery-3.7.1.js"
  integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
  crossorigin="anonymous"></script>
<link
	href="/css/admin/common.css"
	rel="stylesheet">

<script type="text/javascript">
	
</script>
</head>
<body>
	<main class="d-flex flex-nowrap">
		<!-- 사이드바 영역 -->
		<jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
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
							<td><a href="./company/${companyVo.companyId}">${companyVo.companyName}</a></td>
							<td>${companyVo.email}</td>
							<td>${companyVo.tel}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			


			<div id='pageNation'>
				<c:if test="${totalPage > 0}">
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
				</c:if>
			</div>

			<div id="searchContainer">
				<input id="companyKeyword" type="text" placeholder="기업명">
				<button id="companySearchBtn" class="btn btn-light">검색</button>
			
			</div>

		</div>
		<!-- 본문영역 -->
	</main>
</body>

<script src="/js/admin/member/company.js"></script>

	


</html>
