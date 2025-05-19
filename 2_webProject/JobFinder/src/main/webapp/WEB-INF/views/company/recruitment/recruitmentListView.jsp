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


<style type="text/css">

	
	.main{
		width: 800px;
		margin: auto;
	
	}
	
	#recruitmentList{
		margin: auto;
	}
	
	.emptyList{
	}
	
	.buttonPlace {
    text-align: center;
    vertical-align: middle;
	}
	
	#recruitmentList>thead{
	
	background: gray;
	text-align: center;
	}
	
	.buttonPlace>a,.buttonPlace>button{
		width: 100px;
		font-size: 10px;
		vertical-align: middle;
	
	}
	
	.innerTable{
		font-size: small;
		width: 600px;
	
	}
	
	.info{
		text-align: justify;
		color: gray;
	}
	.name{
		text-align: left;
	}
	
	.jobPostTitle{
		  text-decoration: none;  
  		  color: inherit;         
	}
	
	.stoppedPost{
		font-size:small;
		color: red;
		font-weight: bold;
	}
	.sideBar{
	
		float: left;
	}

</style>

</head>
<body>
<jsp:include page="../../common/header.jsp"/>



<div class="container" style="margin-top: 20px;">
    <div class="sideBar">
		<jsp:include page="../companyPage/companyPageSidebar.jsp"/>
    </div>
	<!-- 메인 -->
  <div class="main">
  
  <h1>공고 관리</h1>
  
	<div class="d-flex justify-content-end mb-2">
	    <a href="/Recruitment/insert" class="btn btn-warning">
	        <i class="bi bi-plus-circle"></i> 신규 등록
	    </a>
	</div>

   	<table id="recruitmentList" class="table table-border">
   		<thead class="table-group-divider">
   			<tr>
   				<th>공고</th>
   				<th colspan="2">관리</th>
   			</tr>
   		</thead>
   		<tbody>
   			   	
   			   	<c:if test="${recruitmentList.size() eq 0}">
   			   	<tr>
   			   		<td colspan="3"><span id="emptyList">등록된 공고가 없습니다</span></td>
   			   	</tr>
   			   	</c:if>
   
   			<c:forEach var="recruitment" items="${recruitmentList}">
				<tr>
   					<td><h5><a class="jobPostTitle" href="/Recruitment/detail?companyId=${recruitment.companyId}&jobPostId=${recruitment.jobPostId}">
   						${recruitment.title}</a>
   						<c:if test="${recruitment.isContinuous eq true}"><span class="stoppedPost">마감</span></c:if></h5></td>
   					<td class="buttonPlace" rowspan="2">
   						<a href="/" class="btn btn-light mb-2"><i class="bi bi-pencil-square"></i>수정하기</a><br>
   						<a href="./recruitment/${recruitment.jobPostId}/applicants" class="btn btn-light mb-2"><i class="bi bi-file-person"></i>지원자 보기</a><br>
   						<c:if test="${recruitment.isContinuous eq false}"><button class="btn btn-light mb-2 stopBtn" value="${recruitment.jobPostId}"><i class="bi bi-sign-stop"></i>마감하기</button></c:if>
   					</td>
   					<td rowspan="2"><button class="btn delBtn" value="${recruitment.jobPostId}"><i class="bi bi-x"></i></button></td>
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
   								<td class="info"><fmt:formatDate value="${recruitment.endDate}"/></td>
   							</tr>
   							<tr>
   								<td class="name">스킬</td>
   								<td class="info">
   								<!-- 최대 2개까지 뜨도록 설정 -->
	   								<c:forEach var="skill" items="${recruitment.skillList}" varStatus="status" begin="0" end="1">
								    ${skill.tagName}
								    <c:if test="${!status.last}">, </c:if>
									</c:forEach>
								</td>
   								<td class="name">채용인원</td>
   								<td class="info">${recruitment.maxApplicants} 명</td>
   								<td class="name">지원자</td>
   								<td class="info">${recruitment.applicantCount}</td>
   							</tr>
   							<tr>
   								<td class="name">최종 수정일 
   								<td class="info" colspan="5">
   									<fmt:formatDate value="${recruitment.modifiedDate eq null?recruitment.endDate:recruitment.modifiedDate}"/>
   								</td>
   							</tr>
   						</table>
   						
   					</td>
   					<!-- <td></td> -->
   				</tr>
   			</c:forEach>
   		</tbody>
   	
   	</table>
   
    </div>
    
    
    

</div>
	
<jsp:include page="../../common/footer.jsp" />
</body>

	<script type="text/javascript">

function deleteRecruitments(jobPostIds) {
    if (!confirm("삭제를 진행합니까?")) return;

    fetch("/company/recruitment", {
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

function stopRecruitments(jobPostIds) {
    if (!confirm("공고를 마감합니까?")) return;

    fetch("/company/recruitment/stop", {
        method: "PATCH",
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
        if (data == "마감완료") {
            alert("마감 성공");
            location.reload();
        } else {
            alert("마감 실패: 서버 응답 오류");
        }
    })
    .catch(error => {
        alert("마감 실패");
        console.error("에러 발생:", error);
    });
}


const delBtnArr = document.getElementsByClassName("delBtn");

for (let i = 0; i < delBtnArr.length; i++) {
    delBtnArr[i].addEventListener("click", function (e) {
        const jobPostId = e.currentTarget.value;
        deleteRecruitments([jobPostId]); // 단일도 배열로
    });
}

const stopBtnArr = document.getElementsByClassName("stopBtn");

for (let i=0;i<stopBtnArr.length;i++){
	stopBtnArr[i].addEventListener("click", function(e) {
		const jobPostId = e.currentTarget.value;
		stopRecruitments([jobPostId]);
	})
	
}


    </script>


</html>