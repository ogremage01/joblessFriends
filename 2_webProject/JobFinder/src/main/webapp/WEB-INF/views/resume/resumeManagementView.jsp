<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이력서 관리</title>
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/resume/resumeManagementStyle.css" />
</head>
<body>

	<jsp:include page="../common/header.jsp" />

<%--<<<<<<< HEAD--%>
<%--	<!-- 테이블 헤더 -->--%>
<%--    <div class="table-header">--%>
<%--      <div>이력서</div>--%>
<%--      <div>관리</div>--%>
<%--    </div>--%>
<%--	--%>
<%--	<!-- 내용 분기처리 (이력서 없을때 또는 있을때) -->--%>
<%--    <c:choose>--%>
<%--    --%>
<%--	&lt;%&ndash; 이력서가 없을때 &ndash;%&gt;--%>
<%--      <c:when test="${fn:length(resumes) == 0}">--%>
<%--        <div class="resume-empty">--%>
<%--          아직 등록한 이력서가 없습니다.<br />--%>
<%--          <a href="/resume/write" class="resume-write-link">이력서 작성하러 가기 →</a>--%>
<%--        </div>--%>
<%--      </c:when>--%>
<%--      --%>
<%--      &lt;%&ndash; 이력서가 있을때 &ndash;%&gt;--%>
<%--      <c:otherwise>--%>
<%--        <c:forEach var="resume" items="${resumes}">--%>
<%--          <div class="resume-card">--%>
<%--            <div class="resume-content">--%>
<%--              <div class="resume-title">${resume.memberName}</div>--%>
<%--              <div class="resume-meta">--%>
<%--                <span>경력</span>--%>
<%--                <span>${resume.profile}</span>--%>
<%--              </div>--%>
<%--              <div class="resume-meta resume-skill">--%>
<%--                <span>스킬</span>--%>
<%--              </div>--%>
<%--              <div class="resume-date">최종수정일 ${resume.birthDate}</div>--%>
<%--            </div>--%>
<%--=======--%>
	<div id="container">
		<div id="containerWrap">
			<!-- 사이드바 -->
			<jsp:include page="../common/mypageSidebar.jsp" />


			<!-- 메인 -->
			<div class="main">
				<div class="content">
					<h1 class="mainTitle">이력서 관리</h1>
					
					<div class="item">

						<button class="write-btn" onclick="location.href='/resume/write'">
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
									<div class="resume-card">
										<div class="resume-content">
											<div class="resume-title">${resume.memberName}</div>
											<div class="resume-meta">
												<span>경력</span> <span>${resume.profile}</span>
											</div>
											<div class="resume-meta resume-skill">
												<span>스킬</span>
											</div>
											<div class="resume-date">최종수정일 ${resume.birthDate}</div>
										</div>

										<div class="button-wrapper">
											<%-- 삭제 버튼 --%>

											<form action="/resume/delete" method="post">
												<input type="hidden" name="resumeId"
													value="${resume.resumeId}" />
												<button type="submit" class="delete-btn">x</button>
											</form>


											<%-- 수정 버튼 --%>

											<a href="/resume/write?resumeId=${resume.resumeId}">
												<button class="edit-btn">수정하기</button>
											</a>

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
</html>