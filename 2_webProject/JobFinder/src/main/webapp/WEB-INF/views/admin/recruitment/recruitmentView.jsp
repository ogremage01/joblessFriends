<!-- 관리자 로그인 여부를 묻는 자바구문이 들어가야 할 부분 -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>공고관리</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" 
      crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js" 
	integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        /*기본값(default)이 이미 "text/css"로 되어 있어서 자동인식한다하여 뺐음 */
        #container{
        	margin: auto;
        }
    </style>
    
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
			<h1 style="text-align: center;">공고 목록</h1>
			
			<table class="table table-striped">
				<thead class="table-dark" style="margin: auto;">
					<tr>
						<td><button id="massDelRecruitment">삭제</button></td>
						<td>ID</td>
						<td>회사명</td>
						<td>접수 시작일</td>
						<td>접수 마감일</td>
						<td>공고명</td>
						<td>조회수</td>
						<td>삭제</td>
						
					</tr>
				</thead>
				<tbody class="table-group-divider">
					<c:forEach var="recruitmentVo" items="${recruitmentList}">
						<tr>
							<td style="text-align: center;"><input type="checkbox"
								class="delPost" name="delete" value="${recruitmentVo.jobPostId}"></td>
							<td>${recruitmentVo.jobPostId}</td>
							<td><a href="/admin/member/company/${recruitmentVo.companyId}">${recruitmentVo.companyName}</a></td>
							<td><fmt:formatDate value="${recruitmentVo.startDate}" pattern="yyyy-MM-dd" /></td>
							<td><fmt:formatDate value="${recruitmentVo.endDate}" pattern="yyyy-MM-dd" /></td>
							<td><a href="/Recruitment/detail?companyId=${recruitmentVo.companyId}&jobPostId=${recruitmentVo.jobPostId}" 
								target="_blank" rel="noopener noreferrer">${recruitmentVo.title}</a></td>
							<td>${recruitmentVo.views}</td>
							<td><button class="delBtn" value="${recruitmentVo.jobPostId}">삭제</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<!-- 본문영역 -->

	</main>
</body>

<script type="text/javascript">
	

	
	function deleteRecruitments(jobPostIds) {
	    if (!confirm("삭제를 진행합니까?")) return;

	    fetch("/admin/recruitment", {
	        method: "DELETE",
	        headers: {
	            "Content-Type": "application/json"
	        },
	        body: JSON.stringify(jobPostIds) // 배열 전달
	    })
	    .then(response => {
	        if (!response.ok) {
	            throw new Error("서버 오류: " + response.status);
	        }
	        return response.text();
	    })
	    .then(data => {
	        if (data == "삭제완료") {
	            alert("삭제 성공");
	            location.reload();
	        } else {
	            alert("삭제 실패: 서버 응답 오류");
	        }
	    })
	    .catch(error => {
	        alert("삭제 실패");
	        console.error("에러 발생:", error);
	    });
	}
	
	const delBtnArr = document.getElementsByClassName("delBtn");

	for (let i = 0; i < delBtnArr.length; i++) {
	    delBtnArr[i].addEventListener("click", function (e) {
	        const jobPostId = e.target.value;
	        deleteRecruitments([jobPostId]); // 단일도 배열로
	    });
	}
	
	document.getElementById("massDelRecruitment").addEventListener("click", function () {
	    const checked = document.querySelectorAll(".delPost:checked");
	    const jobPostIds = Array.from(checked).map(el => el.value);

	    if (jobPostIds.length === 0) {
	        alert("삭제할 항목을 선택하세요.");
	        return;
	    }

	    deleteRecruitments(jobPostIds);
	});
	
	
	


</script>
</html>
