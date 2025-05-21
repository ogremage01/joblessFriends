<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지-내가 찜한 공고</title>
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<link rel="stylesheet" href="/css/common/common.css">
	<link rel="stylesheet" href="/css/member/memberMyPage.css" />
	<link rel="stylesheet" href="/css/member/bookmarkView.css" />

</head>
<body>

<jsp:include page="../../common/header.jsp"/>

	<div class="container">
			
		<div id="containerWrap">
			<div id="sideBar">
				<jsp:include page="../../common/mypageSidebar.jsp"/>
			</div>
		    <div class="main">
		    
		    	<div class="content">
			    	<h1 class="mainTitle">내가 찜한 공고</h1>
			    	
				    	        <!-- 채용공고 리스트 -->
				        <div id="jobListings">
				            <c:forEach var="item" items="${recruitmentList}">
				                <div class="job" data-jobpostid="${item.jobPostId}"
				                                data-companyid="${item.companyId}">
				
				                    <!-- 왼쪽: 회사명 -->
				                    <div class="company-name">
				                            ${item.companyName}
				                    </div>
				
				                    <!-- 가운데: 공고 정보 -->
				                    <div class="job-info">
				                        <div class="job-title">
				                                ${item.title} <span class="star">★</span>
				                        </div>
				                        <div class="job-meta">
				
				                            <span>🧑‍💻 지원자격:${item.education} </span> <span>🎓 경력: ${item.careerType}</span>
				                            <span>💼 채용직: ${item.jobName}</span>
				
				                        </div>
				                        <div class="job-meta-skill">
				                            🧩 스킬:
				                            <c:forEach var="skill" items="${skillMap[item.jobPostId]}">
				                                <span class="tag">${skill.tagName}</span>
				                            </c:forEach>
				                        </div>
				                    </div>
				
				                    <!-- 오른쪽: 버튼 및 마감일 -->
				                    <span class="deleteBookmark"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
											  <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
											  <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
											</svg></span>
				                    <div class="job-action">
				                        <button class="apply-btn" type="button" onclick="">지원하기</button>
				                        <div class="deadline">~<fmt:formatDate value="${item.endDate}" pattern="MM/dd(E)" /></div>
				                    </div>
				                </div>
				            </c:forEach>
				        </div>

			    	
		    	</div>
		      
		    </div>
		</div>    
	</div>
	
<jsp:include page="../../common/footer.jsp" />

</body>

<script src="/js/member/bookmarkView.js"></script>
</html>