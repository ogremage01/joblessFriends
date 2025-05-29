<!-- 관리자 로그인 여부를 묻는 자바구문이 들어가야 할 부분 -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>개인회원관리</title>
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
	
<script src="https://code.jquery.com/jquery-3.7.1.min.js" ></script>

<style>
/*기본값(default)이 이미 "text/css"로 되어 있어서 자동인식한다하여 뺐음 */
#container {
	margin: auto;
}
</style>

<script type="text/javascript">
	
</script>
</head>
<body>
	<main class="d-flex flex-nowrap">

		<jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>

		<!-- 본문영역 -->

		<div id="container">
			<h1 style="text-align: center;">개인 회원 목록</h1>
			<table class="table table-striped">
				<thead class="table-dark" style="margin: auto;">
					<tr>
						<td><button id="massDelMem">탈퇴</button></td>
						<td>ID</td>
						<td>이메일</td>
						<td>닉네임</td>
						<td>생성일</td>
						<td>수정일</td>
					</tr>
				</thead>
				<tbody class="table-group-divider">
					<c:forEach var="memberVo" items="${memberList}">
						<tr>
							<td style="text-align: center;"><input type="checkbox"
								class="delMember" name="delete" value="${memberVo.memberId}"></td>
							<td>${memberVo.memberId}</td>
							<td><a href="./individual/${memberVo.memberId}">${memberVo.email}</a></td>
							<td>${memberVo.nickname}</td>
							<td><fmt:formatDate value="${memberVo.createAt}" pattern="yyyy-MM-dd" /></td>
							<td><fmt:formatDate value="${memberVo.modifiedAt}" pattern="yyyy-MM-dd" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<div id='pageNation'>
				<c:if test="${totalPage > 0}">
					<nav aria-label="...">

						<ul class="pagination justify-content-center">
							<li class="page-item ${curPage==0?'disabled':''}"><a
								class="page-link" href="./individual?page=${curPage-1}&keyword=${param.keyword}">Previous</a>
							</li>
							<c:forEach begin="0" var="i" end="${totalPage-1}">
								<li class="page-item ${curPage==i?'active':''}"><a
									class="page-link" href="./individual?page=${i}&keyword=${param.keyword}">${i+1}</a></li>
							</c:forEach>

							<li class="page-item"><a
								class="page-link ${curPage==totalPage-1? 'disabled':''}"
								href="./individual?page=${curPage+1}&keyword=${param.keyword}">Next</a></li>
						</ul>

					</nav>
				</c:if>
			</div>

			<div id="searchContainer" class="d-flex justify-content-center my-4">
				<input id="memberKeyword" type="text" placeholder="이메일, 닉네임">
				<button id="memberSearchBtn" class="btn btn-light">검색</button>
			</div>
		</div>

		<!-- 본문영역 -->

	</main>
</body>
<script src="/js/admin/member/individual.js"></script>
</html>
