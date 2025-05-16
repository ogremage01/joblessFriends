<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기업 페이지-공고란</title>

<style type="text/css">
	table,tr,td{
	
		border: 1px solid black;
	
	}

</style>

</head>
<body>
<jsp:include page="../../common/header.jsp"/>



<div class="container">
    
	<!-- 메인 -->
  <div class="main">
    여기는 기업회원의 공고확인 페이지<br>
    <c:if test="${recruitmentList.size() eq 0}"><span>등록된 공고가 없습니다</span></c:if>
   	<table id="recruitmentList">
   		<thead>
   			<tr>
   				<td>공고</td>
   				<td>관리</td>
   			</tr>
   		</thead>
   		<tbody>
   			   	<tr>
   					<td><h1>공고 제목</h1></td>
   					<td rowspan="2">버튼</td>
   				</tr>
   				<tr>
   					<td>
   						<table>
   							<tr>
   								<td>지원자격</td>
   								<td>지원자격내용</td>
   								<td>채용부문</td>
   								<td>채용부문내용</td>
   								<td>공고마감</td>
   								<td>공고마감일</td>
   							</tr>
   							<tr>
   								<td>스킬</td>
   								<td>스킬내용</td>
   								<td>채용인원</td>
   								<td>인원수</td>
   								<td>지원자</td>
   								<td>지원자수</td>
   							</tr>
   						</table>
   					</td>
   				</tr>
   				
   			
   			<c:forEach var="recruitment" items="${recruitmentList}">
				<tr>
   					<td><h1>${recruitment.title}</h1></td>
   					<td rowspan="2">버튼</td>
   				</tr>
   				<tr>
   					<td>
   						<table>
   							<tr>
   								<td>지원자격</td>
   								<td>지원자격내용</td>
   								<td>채용부문</td>
   								<td>채용부문내용</td>
   								<td>공고마감</td>
   								<td>공고마감일</td>
   							</tr>
   							<tr>
   								<td>스킬</td>
   								<td>스킬내용</td>
   								<td>채용인원</td>
   								<td>인원수</td>
   								<td>지원자</td>
   								<td>지원자수</td>
   							</tr>
   						</table>
   					</td>
   				</tr>
   			</c:forEach>
   		</tbody>
   	
   	</table>
   
    </div>
    
    
    
  </div>
</div>
	
<jsp:include page="../../common/footer.jsp" />
</body>
</html>