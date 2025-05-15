<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
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
	<link
		href="/css/admin/common.css"
		rel="stylesheet">


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
					<!-- 테이블 구조는 자바스크립트로 구현할 예정 -->
				</tbody>
			</table>
			

			<div id='pageNation'>
				<!-- 페이지네이션이 들어갈 위치 -->
			</div>

			<div id="searchContainer">
				<input id="companyKeyword" type="text" placeholder="직군명">
				<button id="companySearchBtn" class="btn btn-light">검색</button>
			
			</div>



		</div>
		<!-- 본문영역 -->
	</main>
</main>

</body>

<script type="text/javascript">

	const jobGroupContentObj = document.getElementById("jobGroupContent");

	
	function renderJobGroup(jobGroupList) {
		  let html = "";

		  jobGroupList.forEach(jobGroup => {
		    html += `
		      <tr>
		        <td style="text-align: center;">
		          <input type="checkbox" class="delCompany" name="delete" value="${jobGroup.jobGroupId}">
		        </td>
		        <td>${jobGroup.jobGroupId}</td>
		        <td><a>${jobGroup.jobGroupName}</a></td>
		      </tr>
		    `;
		  });

		  jobGroupContentObj.innerHTML = html;
		}
	
	fetch("/admin/job/jobGroup")
	  .then(response => response.json())
	  .then(data => {
	    renderJobGroup(data);
	  })
	  .catch(error => {
	    console.error("직군 목록 로딩 실패:", error);
	  });
	
	
	
	

</script>
</html>