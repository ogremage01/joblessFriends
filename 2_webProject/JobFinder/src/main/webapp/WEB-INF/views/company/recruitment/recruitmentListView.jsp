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
	
	.buttonPlace>button{
		width: 130px;
	
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

</style>

</head>
<body>
<jsp:include page="../../common/header.jsp"/>



<div class="container">
    
	<!-- 메인 -->
  <div class="main">
  <h1>공고 관리</h1>
    
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
   					<td><h3>${recruitment.title}</h3></td>
   					<td class="buttonPlace" rowspan="2">
   						<button class="btn btn-light mb-2"><i class="bi bi-pencil-square"></i>수정 하기</button><br>
   						<button class="btn btn-light mb-2"><i class="bi bi-file-person"></i>지원자 보기</button><br>
   					</td>
   					<td rowspan="2"><i class="bi bi-x"></i></td>
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
   								<td class="info">${recruitment.skillList.size()>0?recruitment.skillList.get(0).getTagName():""}</td>
   								<td class="name">채용인원</td>
   								<td class="info">정보없음</td>
   								<td class="name">지원자</td>
   								<td class="info">정보없음</td>
   							</tr>
   							<tr>
   								<td colspan="3">최종 수정일 
   								<fmt:formatDate value="${recruitment.modifiedDate eq null?recruitment.endDate:recruitment.modifiedDate}"/></td>
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
</html>