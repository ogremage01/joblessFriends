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
	<c:if test="${sessionScope.userType == 'member'}">
		<div id="resumeHeader">
			<div class="headerTitle">이력서 미리보기</div>
			<div class="headerInfo">채용기업이 보는 이력서 열람화면입니다.</div>
		</div>
	</c:if>
	
	<!-- 	이력서 메인 		-->
	<div id="container">
		<h1>${resume.title}</h1>
		
		<!-- 	프로필 	 -->
		<div id="profile">
			<div id="profileTop">
				<!--	프로필 사진	-->
				<div id="profileImg">
					<c:if test="${not empty resume.profile}">
    					<img src="${resume.profile}" alt="프로필 이미지" />
					</c:if>
				</div>
				
				<!--	인적사항		-->
				<div id="infoTableBox">
					<span class="infoTableName">${resume.memberName}</span>
					
					<!-- 나이 표시를 위한 스크립트 -->
					<%@ page import="java.util.Date, java.time.*" %>
					<%
					    // 먼저 resume 객체를 꺼내서
					    Object resumeObj = request.getAttribute("resume");
					    
					    Date birthDate = null;
					    int age = 0;
					
					    if (resumeObj != null) {
					        // resume가 자바빈이라고 가정하고 getBirthDate() 호출
					        try {
					            java.lang.reflect.Method m = resumeObj.getClass().getMethod("getBirthDate");
					            Object birthDateObj = m.invoke(resumeObj);
					            if (birthDateObj != null && birthDateObj instanceof java.util.Date) {
					                birthDate = (Date) birthDateObj;
					                
					                LocalDate birth = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					                LocalDate today = LocalDate.now();
					                age = Period.between(birth, today).getYears();
					            }
					        } catch(Exception e) {
					            out.println("Error accessing birthDate: " + e.getMessage());
					        }
					    }
					
					    request.setAttribute("calculatedAge", age);
					%>
					<span class="infoTableAge">
					    <fmt:formatDate value="${resume.birthDate}" pattern="yyyy" />
					    (만 ${calculatedAge}세)
					</span>
					
					<table>
						<tr>
							<th class="infoTableTh">이메일</th>
							<td class="infoTableTd">${resume.email}</td>
						</tr>
						<tr>
							<th class="infoTableTh">전화번호</th>
							<td class="infoTableTd">${resume.phoneNumber}</td>
						</tr>
						<tr>
							<th class="infoTableTh">주소</th>
							<td class="infoTableTd">${resume.address}</td>
						</tr>
					</table>
				</div>
			</div>
			
			<!-- 	이력서 요약	 -->
			<div id="profileBottom" class="borderBox">
				<c:set var="visibleLimit" value="1" />
				<div class="resumeSum education">
			<span class="sumTitle">학력</span>
				<div class="sumContent">
					<c:choose>
						<c:when test="${empty resume.schoolList}">
							<span class="sumItem">-</span>
						</c:when>
						<c:otherwise>
							<!-- 최종 학력 변수 초기화 -->
							<c:set var="finalSchoolName" value="" />
							<c:set var="finalSortation" value="" />
							<c:set var="finalStatus" value="" />
							<c:set var="maxPriority" value="0" />
							<c:set var="finalMajorName" value="" />
							
							<!-- 최종 학력 찾기 -->
							<c:forEach var="school" items="${resume.schoolList}">
								<c:set var="priority" value="0" />
									<c:choose>
									    <c:when test="${school.sortation == 'doctor'}">
                                            <c:set var="priority" value="5" />
                                        </c:when>
										<c:when test="${school.sortation == 'master'}">
                                            <c:set var="priority" value="4" />
                                        </c:when>
										<c:when test="${school.sortation == 'univ4'}">
											<c:set var="priority" value="3" />
										</c:when>
										<c:when test="${school.sortation == 'univ2'}">
											<c:set var="priority" value="2" />
										</c:when>
										<c:when test="${school.sortation == 'high'}">
											<c:set var="priority" value="1" />
										</c:when>
									</c:choose>
								
								<!-- 더 높은 우선순위면 변수 갱신 -->
									<c:if test="${priority > maxPriority}">
										<c:set var="finalSchoolName" value="${school.schoolName}" />
										<c:set var="finalSortation" value="${school.sortation}" />
										<c:set var="finalStatus" value="${school.status}" />
										<c:set var="finalMajorName" value="${school.majorName}" />
										<c:set var="maxPriority" value="${priority}" />
									</c:if>
							</c:forEach>
							
							<!-- 출력 -->
								<span class="sumItem">${finalSchoolName}</span>
								<span class="sumAddEx">
									<c:choose>
										<c:when test="${finalSortation == 'high'}">고등학교</c:when>
										<c:when test="${finalSortation == 'univ2'}">대학교(2,3년)</c:when>
										<c:when test="${finalSortation == 'univ4'}">대학교(4년)</c:when>
										<c:when test="${finalSortation == 'master'}">석사</c:when>
										<c:when test="${finalSortation == 'doctor'}">박사</c:when>
									</c:choose>
								</span>
								
								<c:if test="${not empty finalMajorName}">
							        <span class="sumAddEx">${finalMajorName}</span>
							    </c:if>
							    
<%-- 								<span class="sumAddEx">${finalStatus}</span> --%>
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
							<c:set var="totalSize" value="${fn:length(resume.careerList)}" />
							
								<c:forEach var="career" items="${resume.careerList}" varStatus="status">
									<c:if test="${status.index lt visibleLimit}">
										<div class="career-summary-line">
											<span class="sumItem">${career.companyName}</span>
											<c:choose>
												<c:when test="${not empty career.hireYm and not empty career.resignYm}">
													<c:set var="hire" value="${career.hireYm.time}" />
													<c:set var="resign" value="${career.resignYm.time}" />
													<c:set var="months" value="${(resign - hire) / (1000*60*60*24*30)}" />
													<c:set var="years" value="${months / 12}" />
													<span class="sumAddEx">
														(<c:out value="${fn:split(years, '.')[0]}" />년)
													</span>
												</c:when>
												<c:otherwise>
													<span class="sumAddEx">
														(재직중)
													</span>
												</c:otherwise>
											</c:choose>
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
		
		<!-------------------- 추가 내용 -------------------->
        <div id="resumeContent">

		<c:if test="${not empty resume.skillList}">
		<!-- 스킬 섹션 -->
		<div class="borderBox">
			<p class="contentTitle">스킬</p>
			<div class="skillBox">
				<c:forEach var="skill" items="${resume.skillList}">
					<div class="skillWrap"><span class="contentItem skill">${skill.tagName}</span></div>
				</c:forEach>
			</div>
		</div>
		<!-- 스킬 섹션 end -->
		</c:if>
			
		<c:if test="${not empty resume.schoolList}">
		<!-- 학력 섹션 -->
		<div class="borderBox">
			<p class="contentTitle">학력</p>
		    <c:forEach var="school" items="${resume.schoolList}">
				<div class="contentBox">
					<span class="contentText textWeak">
					<c:choose>
						<c:when test="${school.sortation == 'high'}">고등학교</c:when>
						<c:when test="${school.sortation == 'univ4'}">대학교(4년)</c:when>
						<c:when test="${school.sortation == 'univ2'}">대학교(2,3년)</c:when>
						<c:when test="${school.sortation == 'master'}">석사</c:when>
						<c:when test="${school.sortation == 'doctor'}">박사</c:when>
					</c:choose>
					</span>
					
					<span class="contentText textStrong">${school.schoolName}</span>
					
					<c:choose>
						<c:when test="${school.sortation == 'high'}">
                            <span class="contentText textWeak">${school.yearOfGraduation}</span>
						</c:when>
						
                        <c:when test="${school.sortation == 'univ4' || school.sortation == 'univ2' || school.sortation == 'master' || school.sortation == 'doctor' }">
							<span class="contentText textWeak"> ${school.majorName}</span>
								<span class="contentText textWeak">
									<fmt:formatDate value="${school.startDate}" pattern="yyyy.MM" /> ~
									<fmt:formatDate value="${school.endDate}" pattern="yyyy.MM" />
								</span>
						</c:when>
					</c:choose>
					<span class="contentText textWeak ">${school.status}</span>
				</div>
			</c:forEach>
		</div>
		<!-- 학력 섹션 end -->
		</c:if>
		
		<c:if test="${not empty resume.careerList}">
		<!-- 경력 섹션 -->	
		<div class="borderBox">
			<p class="contentTitle">경력</p>
			<c:forEach var="career" items="${resume.careerList}" varStatus="loop">
				<div class="contentBox">
					<span class="contentText textWeak marginRight">
						<fmt:formatDate value="${career.hireYm}" pattern="yyyy.MM" /> ~
						<c:choose>
							<c:when test="${career.resignYm != null}">
								<fmt:formatDate value="${career.resignYm}" pattern="yyyy.MM" />
							</c:when>
								<c:otherwise>재직중</c:otherwise>
						</c:choose>
					</span>
					<span class="contentText textStrong companyName">${career.companyName}</span>
					<span class="contentText textWeak">|</span>
					<span class="contentText textWeak">${career.departmentName},</span>
					<span class="contentText textWeak">${career.position}</span>
<%-- 					<span class="contentText">${jobTitles[loop.index].jobGroupName} / ${jobTitles[loop.index].jobName}</span> --%>
					<span class="contentText textWeak">|</span>
					<span class="contentText textWeak">연봉
					<fmt:formatNumber value="${career.salary}" type="number" groupingUsed="true" />
					만원</span>
					<br/>
					<span>${career.workDescription}</span>
				</div>
			</c:forEach>
		</div>
		<!-- 경력 섹션 end -->
		</c:if>
			
		<c:if test="${not empty resume.educationList}">
		<!-- 교육 섹션 -->
		<div class="borderBox">
			<p class="contentTitle">교육</p>
			<c:forEach var="edu" items="${resume.educationList}">
				<div class="contentBox">
					<span class="contentText textWeak marginRight">
						<fmt:formatDate value="${edu.startDate}" pattern="yyyy.MM" />
						~
						<fmt:formatDate value="${edu.endDate}" pattern="yyyy.MM" />
					</span>
					<span class="contentText textStrong">${edu.eduName}</span>
					<span class="contentText textWeak">${edu.eduInstitution}</span>
					<br/>
<!-- 					<span class="contentText textWeak">내용 </span> -->
<%-- 					<span>${edu.content}</span> --%>
				</div>
			</c:forEach>
		</div>
		<!-- 교육 섹션 end -->
		</c:if>
		
		<c:if test="${not empty resume.certificateList}">
		<!-- 자격증 섹션 -->
		<div class="borderBox">
			<p class="contentTitle">자격증</p>
			<c:forEach var="cert" items="${resume.certificateList}">
				<div class="contentBox">
					<span class="contentText textWeak marginRight">
						<fmt:formatDate value="${cert.acquisitionDate}" pattern="yyyy.MM.dd" />
					</span>
					<span class="contentText textStrong">${cert.certificateName}</span>
					<span class="contentText textWeak">${cert.issuingAuthority}</span>
				</div>
			</c:forEach>
		</div>
		<!-- 자격증 섹션 end -->
		</c:if>
		
		<c:if test="${not empty resume.selfIntroduction}">
		<!-- 자기소개서 섹션 -->	
		<div class="borderBox">
		<p class="contentTitle">자기소개서</p>
			<div class="contentBox">
				<pre class="selfIntro">${resume.selfIntroduction}</pre>
			</div>
		</div>
		<!-- 자기소개서 섹션 end -->
		</c:if>
		
		

		
		<c:if test="${not empty resume.portfolioList}">
		<!-- 포트폴리오 섹션 -->
		<div class="borderBox">
			<p class="contentTitle">포트폴리오</p>
			<div class="contentBox">
				<ul class="portfolio-list">
					<c:forEach var="file" items="${resume.portfolioList}">
						<li>
							<div class="portfolio-item">
								<span class="fileName">${file.fileName}</span>
								<div class="file-actions">
									<a href="/resume/download/${file.storedFileName}" class="download-btn" title="다운로드">
										📥 다운로드
									</a>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<!-- 포트폴리오 섹션 end-->
		</c:if>
		

		
	</div>
</div>

</body>
<script src="/js/resume/resumePreview.js"></script>
</html>