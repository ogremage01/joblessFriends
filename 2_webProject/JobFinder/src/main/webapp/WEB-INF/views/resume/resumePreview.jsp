<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="resume" value="${resume}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이력서 미리보기</title>
<link rel="stylesheet" href="/css/resume/resumePreview.css" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	
	<!-- 	이력서 헤더	 -->
	<div id="resumeHeader">
		<div class="headerTitle">이력서 미리보기</div>
		<div class="headerInfo">채용기업이 보는 이력서 열람화면입니다.</div>
	</div>
	
	<!-- 	이력서 메인 		-->
	<div id="container">
		<h1>${resume.title}</h1>
		
		<!-- 	프로필 	 -->
		<div id="profile">
			<div id="profileTop">
				<!--	프로필 사진	-->
				<div id="profileImg">
					<c:if test="${not empty resume.profile}">
    					<img src="${resume.profile}" alt="프로필 이미지" style="width:120px;" />
					</c:if>
				</div>
				
				<!--	인적사항		-->
				<div id="infoTableBox">
					<span class="infoTableName">${resume.memberName}</span>
					<span class="infoTableAge" colspan="3">${resume.birthDate}</span>
					<table>
						<tr>
							<th class="infoTableTh">이메일</th>
							<td class="infoTableTd">${resume.email}</td>
							<th class="infoTableTh">전화번호</th>
							<td class="infoTableTd">${resume.phoneNumber}</td>
						</tr>
						<tr>
							<th class="infoTableTh">주소</th>
							<td class="infoTableTd" colspan="3">${resume.address}</td>
						</tr>
					</table>
				</div>
			</div>
			
			<!-- 	이력서 요약	 -->
			<div id="profileBottom" class="borderBox">
				<div class="resumeSum education">
				<span class="sumTitle">학력</span>
				<div class="sumContent">
					<c:choose>
						<c:when test="${empty resume.schoolList}">
							<span class="sumItem">-</span>
						</c:when>
						<c:otherwise>
							<c:forEach var="school" items="${resume.schoolList}">
								<div class="edu-summary-line">
									<span class="sumItem">${school.schoolName}</span>
									<span class="sumContent">
										<c:choose>
											<c:when test="${school.sortation == 'high'}">고등학교</c:when>
											<c:when test="${school.sortation == 'univ4'}">대학교(4년)</c:when>
											<c:when test="${school.sortation == 'univ2'}">대학교(2,3년)</c:when>
											<c:otherwise>기타</c:otherwise>
										</c:choose>
									</span>
									<span class="sumAddEx">${school.status}</span>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
				</div>
		<div class="resumeSum career">
			<span class="sumTitle">경력</span>
			<div class="sumContent">
				<c:choose>
					<c:when test="${empty resume.careerList}">
						<span class="sumItem">신입</span>
					</c:when>
						<c:otherwise>
							<c:set var="visibleLimit" value="2" />
							<c:set var="totalSize" value="${fn:length(resume.careerList)}" />
							
								<c:forEach var="career" items="${resume.careerList}" varStatus="status">
									<c:if test="${status.index lt visibleLimit}">
										<div class="career-summary-line">
											<span class="sumItem">${career.companyName}</span>
											<c:if test="${not empty career.hireYm and not empty career.resignYm}">
												<c:set var="hire" value="${career.hireYm.time}" />
												<c:set var="resign" value="${career.resignYm.time}" />
												<c:set var="months" value="${(resign - hire) / (1000*60*60*24*30)}" />
												<c:set var="years" value="${months / 12}" />
												<span class="sumAddEx">
													(<c:out value="${fn:split(years, '.')[0]}" />년)
												</span>
											</c:if>
										</div>
									</c:if>
								</c:forEach>
							
							<c:if test="${totalSize > visibleLimit}">
							<span class="sumAddEx">외 ${totalSize - visibleLimit}건</span>
							</c:if>
						</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="resumeSum training">
		<span class="sumTitle">교육</span>
			<div class="sumContent">
				<c:choose>
					<c:when test="${empty resume.educationList}">
						<span class="sumItem">-</span>
					</c:when>
					<c:otherwise>
						<c:set var="visibleLimit" value="2" />
						<c:set var="totalSize" value="${fn:length(resume.educationList)}" />
						
						<c:forEach var="edu" items="${resume.educationList}" varStatus="status">
							<c:if test="${status.index lt visibleLimit}">
							<div class="edu-summary-line">
							<span class="sumItem">${edu.eduName}</span>
							</div>
							</c:if>
						</c:forEach>
						
						<c:if test="${totalSize > visibleLimit}">
						<span class="sumAddEx">외 ${totalSize - visibleLimit}건</span>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="resumeSum license">
			<span class="sumTitle">자격증</span>
			<div class="sumContent">
				<c:choose>
					<c:when test="${empty resume.certificateList}">
						<span class="sumItem">-</span>
					</c:when>
					<c:otherwise>
						<c:set var="visibleLimit" value="3" />
						<c:set var="totalSize" value="${fn:length(resume.certificateList)}" />
						
						<c:forEach var="cert" items="${resume.certificateList}" varStatus="status">
							<c:if test="${status.index lt visibleLimit}">
							<span class="sumItem">${cert.certificateName}</span>
							</c:if>
						</c:forEach>
						
						<c:if test="${totalSize > visibleLimit}">
							<span class="sumAddEx">외 ${totalSize - visibleLimit}개</span>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
			</div>
		</div>
		
		<!-- 	추가 내용		-->
		<!-- 스킬 섹션 -->
		<div class="borderBox">
			<p>스킬</p>
				<c:choose>
					<c:when test="${not empty resume.skillList}">
						<c:forEach var="skill" items="${resume.skillList}">
						<span class="sumItem">${skill.tagName}</span>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<span class="sumItem">선택된 스킬 없음</span>
					</c:otherwise>
				</c:choose>
		</div>
			
		<div class="borderBox">
		<p>학력</p>
			<c:choose>
				<c:when test="${empty resume.schoolList}">
					<span class="sumItem">-</span>
				</c:when>
					<c:otherwise>
						<div class="detailSection">
						    <c:forEach var="school" items="${resume.schoolList}">
								<div class="detailRow">
									<div>
										<strong>${school.schoolName}</strong>
											(
										<c:choose>
											<c:when test="${school.sortation == 'high'}">고등학교</c:when>
											<c:when test="${school.sortation == 'univ4'}">대학교(4년)</c:when>
											<c:when test="${school.sortation == 'univ2'}">대학교(2,3년)</c:when>
											<c:otherwise>기타</c:otherwise>
										</c:choose>
											)
									</div>
									<div>
										<c:choose>
											<c:when test="${school.sortation == 'high'}">
												졸업년도: ${school.yearOfGraduation}
											</c:when>
											
											<c:when test="${school.sortation == 'univ4' || school.sortation == 'univ2'}">
												전공: ${school.majorName} <br />
												입학: <fmt:formatDate value="${school.startDate}" pattern="yyyy.MM" /> ~
												졸업: <fmt:formatDate value="${school.endDate}" pattern="yyyy.MM" />
											</c:when>
										</c:choose>
									</div>
									<div>상태: ${school.status}</div>
								</div>
							</c:forEach>
						</div>
					</c:otherwise>
			</c:choose>
		</div>
			
		<div class="borderBox">
		<p>경력</p>
			<c:choose>
				<c:when test="${empty resume.careerList}">
					<span class="sumItem">신입</span>
				</c:when>
					<c:otherwise>
						<div class="detailSection">
							<c:forEach var="career" items="${resume.careerList}" varStatus="loop">
								<div class="detailRow">
									<div>
										<strong>${career.companyName}</strong> | ${career.departmentName} | ${career.position}
									</div>
									<div>
										<fmt:formatDate value="${career.hireYm}" pattern="yyyy.MM" /> ~
										<c:choose>
											<c:when test="${career.resignYm != null}">
												<fmt:formatDate value="${career.resignYm}" pattern="yyyy.MM" />
											</c:when>
												<c:otherwise>재직중</c:otherwise>
										</c:choose>
									</div>
									
									<!-- ✅ 직군/직무명 -->
									<div>
										직군/직무: ${jobTitles[loop.index].jobGroupName} / ${jobTitles[loop.index].jobName}
									</div>
									
									<div>연봉: ${career.salary} 만원</div>
									<div>담당업무: ${career.workDescription}</div>
								</div>
							</c:forEach>
						</div>
					</c:otherwise>
			</c:choose>
		</div>
			
		<div class="borderBox">
		<p>교육</p>
			<c:choose>
				<c:when test="${empty resume.educationList}">
					<span class="sumItem">-</span>
				</c:when>
					<c:otherwise>
						<div class="detailSection">
							<c:forEach var="edu" items="${resume.educationList}">
								<div class="detailRow">
									<div><strong>${edu.eduName}</strong></div>
									<div>기관: ${edu.eduInstitution}</div>
									<div>
										기간:
										<fmt:formatDate value="${edu.startDate}" pattern="yyyy.MM" />
										~
										<fmt:formatDate value="${edu.endDate}" pattern="yyyy.MM" />
									</div>
									<div>내용: ${edu.content}</div>
								</div>
							</c:forEach>
						</div>
					</c:otherwise>
			</c:choose>
		</div>
			
		<div class="borderBox">
		<p>자기소개서</p>
			<div class="detailSection">
				<div class="detailRow">
					<pre style="white-space: pre-wrap; font-size: 14px;">${resume.selfIntroduction}</pre>
				</div>
			</div>
		</div>
			
		<div class="borderBox">
		<p>자격증</p>
			<c:choose>
				<c:when test="${empty resume.certificateList}">
					<span class="sumItem">-</span>
				</c:when>
				<c:otherwise>
					<div class="detailSection">
						<c:forEach var="cert" items="${resume.certificateList}">
							<div class="detailRow">
								<div><strong>${cert.certificateName}</strong></div>
								<div>발행처: ${cert.issuingAuthority}</div>
								<div>
									취득일자:
									<fmt:formatDate value="${cert.acquisitionDate}" pattern="yyyy.MM.dd" />
								</div>
							</div>
						</c:forEach>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
			
		<div class="borderBox">
		<p>포트폴리오</p>
			<c:choose>
				<c:when test="${empty resume.portfolioList}">
					<span class="sumItem">-</span>
				</c:when>
				<c:otherwise>
					<div class="detailSection">
						<ul style="padding-left: 20px;">
							<c:forEach var="file" items="${resume.portfolioList}">
								<li style="margin-bottom: 6px;">
									<a href="/uploads/portfolio/${file.storedFileName}" target="_blank">
										${file.fileName}
									</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
			
	</div>
		
</div>

</body>
<script src="/js/resume/resumePreview.js"></script>
</html>