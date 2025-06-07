<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이력서 수정</title>
<link rel="stylesheet" href="/css/resume/resumeStyle.css" />
</head>
<body>
	<jsp:include page="../common/header.jsp"/>

<div class="wrapper-container">	
	<div class="sidebar">
	<!-- 사이드바 메뉴 -->
	<div class="sidebar-menu">
	  <div class="sidebar-title">▲ TOP</div>
	  <ul class="sidebar-links">
	    <li><a href="#section-personal">인적사항</a></li>
	    <li><a href="#section-skill">스킬</a></li>
	    <li><a href="#section-edu">학력</a></li>
	    <li><a href="#section-career">경력</a></li>
	    <li><a href="#section-training">교육</a></li>
	    <li><a href="#section-intro">자기소개서</a></li>
	    <li><a href="#section-license">자격증</a></li>
	    <li><a href="#section-portfolio">포트폴리오</a></li>
	  </ul>
	</div>
	
	<!-- 사이드바 버튼 -->
	<div class="sidebar-buttons">
	  <button id="btn-finish" class="btn-finish">수정완료</button>
	  <button class="btn-preview">미리보기</button>
	  <button class="btn-cancel">작성취소</button>
	</div>
	
</div>

    <section class="resume-wrapper">
    	<div class="resumeTitle resume-title-margin">
		    <h1>제목</h1>
			<input type="text" id="title" placeholder="이력서 제목을 입력하세요" value="${resumeData.title}" />
			<div id="title-error-container" class="title-error-container"></div>
		</div>		
      <!-- 인적사항 -->
	<div class="section-block" id="section-personal">
		<h2>인적사항</h2>
		<div class="grid-4"> <!-- 총 4열 -->
			<!-- 1행 -->
			<div class="field-block">
				<label>이름</label>
				<input type="text" id="name" placeholder="예시) 홍길동" value="${resumeData.memberName}" />
			</div>
			<div class="field-block">
				<label>생년월일</label>
				<input type="text" id="birthdate" placeholder="예시) 2025-04-16" value="<fmt:formatDate value='${resumeData.birthDate}' pattern='yyyy-MM-dd'/>" />
			</div>
			<div class="field-block">
				<label>전화번호</label>
				<input type="text" id="phoneNumber" placeholder="예시) 010-1234-9999" value="${resumeData.phoneNumber}" />
			</div>
			
			<!-- 2행 -->
			<div class="field-block">
				<label>우편번호</label>
				<input type="text" id="postalCodeId" placeholder="우편번호"  value="${resumeData.postalCodeId}"/>
				<button type="button" class="address-search-btn" onclick="execDaumPostcode()">🔍</button>
			</div>
			<div class="field-block">
				<label>주소</label>
				<input type="text" id="roadAddress" placeholder="주소를 입력해주세요"  value="${resumeData.address}" />
			</div>
			<div class="field-block">
				<label>메일</label>
				<input type="text" id="email" placeholder="예시) test@mail.com" value="${resumeData.email}" />
			</div>
			<div class="photo-wrapper">
				<label class="photo-box" id="photoBox">
					<div class="photo-text" <c:if test="${not empty resumeData.profile}">class="hidden"</c:if>>
						사진추가
						<span class="plus-icon">+</span>
					</div>
									<img id="previewImage" src="${resumeData.profile}" alt="미리보기" 
				     <c:choose>
				         <c:when test="${not empty resumeData.profile}">class="visible-block"</c:when>
				         <c:otherwise>class="hidden"</c:otherwise>
				     </c:choose> />
			</label>
			<input type="file" id="profileImageInput" class="hidden" />
			</div>

		</div>
	</div>



      
     <!-- 스킬 -->
	<section class="section-block" id="section-skill">
		<h2 class="section-title">스킬</h2>
		<p id="selectedJobGroupLabel" class="selected-job-group-label hidden"></p>
		<div id="skillContainer" class="tag-select"></div>
	</section>

	<section class="section-block" id="section-edu">
		<h2>학력</h2>
		<div id="school-container">
			<!-- 기존 학력 데이터 표시 -->
			<c:forEach var="school" items="${resumeData.schoolList}" varStatus="status">
				<div class="school-entry">
					<button type="button" class="delete-btn">×</button>
					
					<div class="field-block">
						<label>구분</label>
						<select name="sortation">
							<option value="high" <c:if test="${school.sortation == 'high'}">selected</c:if>>고등학교</option>
							<option value="univ4" <c:if test="${school.sortation == 'univ4'}">selected</c:if>>대학교(4년)</option>
							<option value="univ2" <c:if test="${school.sortation == 'univ2'}">selected</c:if>>대학교(2,3년)</option>
							<option value="master" <c:if test="${school.sortation == 'master'}">selected</c:if>>석사</option>
							<option value="doctor" <c:if test="${school.sortation == 'doctor'}">selected</c:if>>박사</option>
							
						</select>
					</div>
					
					<c:if test="${school.sortation == 'high'}">
						<div class="grid-3">
							<div class="field-block school-autocomplete-block">
								<label>학교명</label>
								<input type="text" name="schoolName" placeholder="학교명을 입력해주세요" value="${school.schoolName}" autocomplete="off" />
								<ul class="autocomplete-list hidden"></ul>
							</div>
							<div class="field-block">
								<label>졸업년도</label>
								<input type="text" name="yearOfGraduation" placeholder="예시) 2025" value="${school.yearOfGraduation}" />
							</div>
							<div class="field-block">
								<label>졸업상태</label>
								<select name="status">
									<option value="졸업예정" <c:if test="${school.status == '졸업예정'}">selected</c:if>>졸업예정</option>
									<option value="졸업" <c:if test="${school.status == '졸업'}">selected</c:if>>졸업</option>
									<option value="재학중" <c:if test="${school.status == '재학중'}">selected</c:if>>재학중</option>
								</select>
							</div>
						</div>
					</c:if>
					<c:if test="${school.sortation != 'high'}">
						<div class="grid-2">
							<div class="field-block school-autocomplete-block">
								<label>학교명</label>
								<input type="text" name="schoolName" placeholder="대학교명을 입력해주세요" value="${school.schoolName}" autocomplete="off" />
								<ul class="autocomplete-list hidden"></ul>
							</div>
							<div class="field-block">
								<label>전공명</label>
								<input type="text" name="majorName" placeholder="전공명을 입력해주세요" value="${school.majorName}" autocomplete="off" />
								<ul class="autocomplete-list hidden"></ul>
							</div>
						</div>
						<div class="grid-3">
							<div class="field-block">
								<label>입학년월</label>
								<input type="text" name="startDate" placeholder="예시) 2020.03" value="<fmt:formatDate value="${school.startDate}" pattern='yyyy.MM'/>"/>
							</div>
							<div class="field-block">
								<label>졸업년월</label>
								<input type="text" name="endDate" placeholder="예시) 2024.02" value="<fmt:formatDate value="${school.endDate}" pattern='yyyy.MM'/>" />
							</div>
							<div class="field-block">
								<label>졸업상태</label>
								<select name="status">
									<option value="졸업예정" <c:if test="${school.status == '졸업예정'}">selected</c:if>>졸업예정</option>
									<option value="졸업" <c:if test="${school.status == '졸업'}">selected</c:if>>졸업</option>
									<option value="재학중" <c:if test="${school.status == '재학중'}">selected</c:if>>재학중</option>
								</select>
							</div>
						</div>
					</c:if>
				</div>
			</c:forEach>
		</div>
		
		<div class="add-education-btn">
			<button type="button">＋ 추가</button>
		</div>
	</section>
	
	<section class="section-block" id="section-career">
		<h2>경력</h2>
		<div id="career-container">
			<!-- 기존 경력 데이터 표시 -->
			<c:forEach var="career" items="${resumeData.careerList}">
				<div class="career-entry">
					<button class="delete-btn">×</button>
					
					<div class="grid-3">
						<div class="field-block">
							<label>회사명</label>
							<input type="text" name="companyName" placeholder="회사명을 입력해주세요" value="${career.companyName}" />
						</div>
						<div class="field-block">
							<label>부서명</label>
							<input type="text" name="departmentName" placeholder="부서명을 입력해주세요" value="${career.departmentName}" />
						</div>
						<div class="field-block">
							<label>입사년월</label>
							<input type="text" name="hireYm" placeholder="예시) 2025.04" value="<fmt:formatDate value="${career.hireYm}" pattern='yyyy.MM'/>" />
						</div>
					</div>
					
					<div class="grid-3">
						<div class="field-block">
							<label>퇴사년월</label>
							<input type="text" name="resignYm" placeholder="예시) 2025.04" value="<fmt:formatDate value="${career.resignYm}" pattern='yyyy.MM'/>" />
						</div>
						
						<div class="field-block">
							<label>담당직군</label>
							<select name="careerJobGroupSelect">
								<option value="">직군 선택</option>
								<c:forEach var="group" items="${jobGroupList}">
									<option value="${group.jobGroupId}" <c:if test="${career.jobGroupId == group.jobGroupId}">selected</c:if>>${group.jobGroupName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="field-block">
							<label>담당직무</label>
							<select name="careerJobSelect">
								<option value="">직무 선택</option>
								<c:forEach var="job" items="${jobList}">
									<option value="${job.jobId}" <c:if test="${career.jobId == job.jobId}">selected</c:if>>${job.jobName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="grid-2">
					<div class="field-block">
							<label>직급/직책</label>
							<input type="text" name="position" placeholder="직급/직책을 입력해주세요" value="${career.position}" />
						</div>
						<div class="field-block">
							<label>연봉 (만원)</label>
							<input type="text" name="salary" placeholder="예시) 2400" value="${career.salary}" />
						</div>
					</div>
					
					<div class="field-block">
						<label>담당업무</label>
						<textarea rows="5" name="workDescription" placeholder="담당하신 업무와 성과에 대해 간단명료하게 적어주세요">${career.workDescription}</textarea>
					</div>
				</div>
			</c:forEach>
		</div>
		
		<div class="add-career-btn">
			<button type="button">＋ 추가</button>
		</div>
	</section>
	
	<section class="section-block" id="section-training">
		<h2>교육</h2>
		<div id="training-container">
			<!-- 기존 교육 데이터 표시 -->
			<c:forEach var="education" items="${resumeData.educationList}">
				<div class="training-entry">
					<button class="delete-btn">×</button>
				
					<div class="grid-4">
						<div class="field-block">
							<label>교육명</label>
							<input type="text" name="eduName" placeholder="교육명을 입력해주세요" value="${education.eduName}" />
						</div>
						<div class="field-block">
							<label>교육기관</label>
							<input type="text" name="eduInstitution" placeholder="교육기관을 입력해주세요" value="${education.eduInstitution}" />
						</div>
						<div class="field-block">
							<label>시작년월</label>
							<input type="text" name="startDate" placeholder="예시) 2025.04" value="<fmt:formatDate value='${education.startDate}' pattern='yyyy.MM'/>" />
						</div>
						<div class="field-block">
							<label>종료년월</label>
							<input type="text" name="endDate" placeholder="예시) 2025.04" value="<fmt:formatDate value='${education.endDate}' pattern='yyyy.MM'/>" />
						</div>
					</div>
					
					<div class="field-block">
						<label>내용</label>
						<textarea rows="3" name="content" placeholder="이수하신 교육과정에 대해 적어주세요">${education.content}</textarea>
					</div>
				</div>
			</c:forEach>
		</div>
		
		<div class="add-training-btn">
			<button type="button">＋ 추가</button>
		</div>
	</section>
	
	<section class="section-block" id="section-intro">
		<h2>자기소개서</h2>
		<div class="field-block">
			<textarea rows="10" id="selfIntroduction" placeholder="자기소개서 내용을 입력하세요">${resumeData.selfIntroduction}</textarea>
		</div>
	</section>
	
	<section class="section-block" id="section-license">
		<h2>자격증</h2>
		<div id="certificate-container">
			<!-- 기존 자격증 데이터 표시 -->
			<c:forEach var="certificate" items="${resumeData.certificateList}">
				<div class="certificate-entry">
					<button class="delete-btn">×</button>
				
					<div class="grid-3">
						<div class="field-block">
							<label>자격증명</label>
							<input type="text" name="certificateName" placeholder="자격증명을 입력해주세요" value="${certificate.certificateName}" />
						</div>
						<div class="field-block">
							<label>발행처</label>
							<input type="text" name="issuingAuthority" placeholder="발행처를 입력해주세요" value="${certificate.issuingAuthority}" />
						</div>
						<div class="field-block">
							<label>취득날짜</label>
							<input type="text" name="acquisitionDate" placeholder="예시) 2025.04" value="<fmt:formatDate value='${certificate.acquisitionDate}' pattern='yyyy.MM'/>" />
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		
		<div class="add-license-btn">
			<button type="button">＋ 추가</button>
		</div>
	</section>
		
	<section class="section-block" id="section-portfolio">
		<h2>포트폴리오</h2>
		<div id="portfolio-container">
			<!-- 기존 포트폴리오 데이터 표시 -->
			<c:forEach var="portfolio" items="${resumeData.portfolioList}">
				<div class="portfolio-entry">
					<button type="button" class="delete-btn">×</button>
					<div class="portfolio-upload-box">
						<label>
							<span class="plus-icon">✓</span>
							파일: ${portfolio.fileName}
							<input type="file" name="portfolioFile" class="hidden" />
						</label>
						<input type="hidden" name="fileName" value="${portfolio.fileName}" />
						<input type="hidden" name="storedFileName" value="${portfolio.storedFileName}" />
						<input type="hidden" name="fileExtension" value="${portfolio.fileExtension}" />
					</div>
				</div>
			</c:forEach>
		</div>
		
		<div class="add-portfolio-btn">
			<button type="button">＋ 추가</button>
		</div>
	</section>
		
    </section>
</div>
	<jsp:include page="../common/footer.jsp" />
	
<!-- 서버 데이터를 JavaScript로 전달 -->
<c:set var="jsResumeId" value="${resumeData.resumeId != null ? resumeData.resumeId : 0}"/>
<c:set var="jsProfile" value="${resumeData.profile != null ? resumeData.profile : ''}"/>

<script>
// 이력서 수정 모드 설정
window.isEditMode = true;
window.currentResumeId = <c:out value="${jsResumeId}"/>;
window.uploadedImageUrl = "<c:out value="${jsProfile}"/>";

// 기존 스킬 데이터 초기화
window.existingSkills = [];
</script>

<!-- 기존 스킬 데이터를 JavaScript로 안전하게 추가 -->
<c:if test="${not empty skillList}">
<c:forEach var="skill" items="${skillList}">
<script>
if (window.existingSkills) {
  window.existingSkills.push({
    tagId: <c:out value="${skill.tagId}"/>,
    tagName: '<c:out value="${fn:replace(skill.tagName, \"'\", \"\\\\'\")}"/>'
  });
}
</script>
</c:forEach>
</c:if>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-3fp9tS8p9A2Mq7Qz+S8jfwD+xdgu9T+O+NRZz8N5eA8=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/resume/resumeCommon.js"></script>
<script src="/js/resume/resumeUpdate.js"></script>
</body>
</html> 