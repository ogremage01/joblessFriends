<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmf" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이력서 관리</title>
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/resume/resumeManagementStyle.css" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">
</head>
<body>

	<jsp:include page="../common/header.jsp" />


	<div id="container">
		<div id="containerWrap">
			<!-- 사이드바 -->
			<jsp:include page="../common/mypageSidebar.jsp" />


			<!-- 메인 -->
			<div class="main">
				<div class="content">
					<h1 class="mainTitle">
						<i class="bi bi-file-text icon-orange"></i>
						이력서 관리
					</h1>
					
					<div class="item">

						<button class="write-btn">
							이력서 작성하기
						</button>

						<!-- 테이블 헤더 -->
						<div class="table-header">
							<div>이력서</div>
							<div>관리</div>
						</div>

						<!-- 내용 분기처리 (이력서 없을때 또는 있을때) -->
						<c:choose>

							<%-- 이력서가 없을때 --%>
							<c:when test="${fn:length(resumes) == 0}">
								<div class="resume-empty">
									아직 등록한 이력서가 없습니다.<br /> <a href="/resume/write"
										class="resume-write-link">이력서 작성하러 가기 →</a>
								</div>
							</c:when>

			<%-- 이력서가 있을때 --%>
            <c:otherwise>
              <c:forEach var="resume" items="${resumes}">
                <div class="resume-card" onclick="openResumePreview(${resume.resumeId})">
                  <div class="resume-content">
                    <div class="resume-title">${resume.title}</div>

                    <%-- 경력 요약 --%>
                    <c:if test="${not empty resume.careerList}">
                      <div class="resume-career">
                        <c:set var="career" value="${resume.careerList[0]}" />
                        <span>경력: ${career.companyName} / ${career.departmentName} / ${career.position}</span>
                      </div>
                    </c:if>

                    <%-- 학력 요약 --%>
					<c:if test="${not empty resume.schoolList}">
						<c:set var="lastIndex" value="${fn:length(resume.schoolList) - 1}" />
						<c:set var="school" value="${resume.schoolList[lastIndex]}" />
						
						<c:if test="${school.status eq '졸업'}">
							<div class="resume-education">
								<span>
									학력: ${school.schoolName}
								<c:if test="${not empty school.majorName}"> / ${school.majorName}</c:if>
									/ ${school.status}
							</span>
							</div>
						</c:if>
					</c:if>

                    <%-- 스킬 요약 --%>
                    <div class="resume-skills">
						<c:forEach var="tag" items="${resume.skillList}" varStatus="status">
							<c:if test="${status.index < 5}">
								<span class="tag">${tag.tagName}</span>
							</c:if>
						</c:forEach>
					</div>

                    <%-- 최종수정일 --%>
                    <div class="resume-date">최종수정일 <fmf:formatDate value="${resume.modifyDate}" /></div>
                  </div>

                  <div class="button-wrapper">
                  	<%-- 삭제 버튼 --%>
                    <button class="delete-btn" data-resume-id="${resume.resumeId}" onclick="event.stopPropagation()">
						<i class="bi bi-x"></i>
					</button>
                    <%-- 수정 버튼 --%>
                    <button class="edit-btn" data-resume-id="${resume.resumeId}" onclick="event.stopPropagation()">수정하기</button>
                  </div>
                </div>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
</div>

	<jsp:include page="../common/footer.jsp" />
</body>

<script src="/js/resume/resumeManagement.js"></script>
</html>