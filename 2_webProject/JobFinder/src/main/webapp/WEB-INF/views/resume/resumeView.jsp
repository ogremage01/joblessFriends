<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>이력서 폼</title>
	<link rel="stylesheet" href="/css/resume/resumeStyle.css" />
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/plugins/monthSelect/style.css">
  	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
  	<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ko.js"></script>
  	<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/plugins/monthSelect/index.js"></script>
  	

  	
</head>
<body>
<jsp:include page="../common/header.jsp"/>

<div class="wrapper-container">
	<div class="sidebar">
		<!-- 사이드바 메뉴 -->
		<div class="sidebar-menu">
			<div class="sidebar-title" id="btnTop">▲ TOP</div>
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
			<button id="btn-finish" class="btn-finish">작성완료</button>
			<button class="btn-preview">미리보기</button>
			<button class="btn-cancel">작성취소</button>
		</div>

	</div>

	<section class="resume-wrapper">
		<div class="resumeTitle resume-title-margin">
			<h1>제목</h1>
			<input type="text" id="title" placeholder="이력서 제목을 입력하세요" />
			<div id="title-error-container" class="title-error-container"></div>
		</div>
		<!-- 인적사항 -->
		<div class="section-block" id="section-personal">
			<h2>인적사항</h2>
			<div class="grid-4"> <!-- 총 4열 -->
				<!-- 1행 -->
				<div class="field-block">
					<label>이름</label>
					<input type="text" id="name" placeholder="예시) 홍길동" />
					
				</div>
				<div class="field-block">
					<label>생년월일</label>
					<input type="text" id="birthdate" placeholder="예시) 2025-04-16" />
				</div>
				<div class="field-block">
					<label>전화번호</label>
					<input type="text" id="phoneNumber" placeholder="예시) 010-1234-9999" />
				</div>

				<!-- 2행 -->
				<div class="field-block">
					<label>우편번호</label>
					<input type="text" id="postalCodeId" placeholder="우편번호" readonly />
					<button type="button" class="address-search-btn" onclick="execDaumPostcode()"><i class="bi bi-search"></i></button>
				</div>
				<div class="field-block">
					<label>주소</label>
					<input type="text" id="roadAddress" placeholder="주소를 입력해주세요"  />
				</div>
				<div class="field-block">
					<label>메일</label>
					<input type="text" id="email" placeholder="예시) test@mail.com" />
				</div>
				<div class="photo-wrapper">
					<label class="photo-box" id="photoBox">
						<div class="photo-text">
							사진추가
							<span class="plus-icon">+</span>
						</div>
											<img id="previewImage" src="#" alt="미리보기" class="hidden" />
				</label>
				<input type="file" id="profileImageInput" class="hidden" />
				</div>

			</div>
		</div>

		<!-- 스킬 -->
		<section class="section-block" id="section-skill">
			<h2 class="section-title">스킬</h2>
			<p id="selectedJobGroupLabel" class="selected-job-group-label hidden"></p>

			<div id="skillContainer" class="tag-select">

			</div>
		</section>

		<section class="section-block" id="section-edu">
			<h2>학력</h2>
			<div id="school-container">
				<!-- 동적으로 학력 항목이 추가됨 -->
			</div>

			<div class="add-education-btn">
				<button type="button">＋ 추가</button>
			</div>
		</section>

		<section class="section-block" id="section-career">
			<h2>경력</h2>
			<div id="career-container">
				<!-- 동적으로 경력 항목이 추가됨 -->
			</div>

			<div class="add-career-btn">
				<button type="button">＋ 추가</button>
			</div>
		</section>

		<section class="section-block" id="section-training">
			<h2>교육</h2>
			<div id="training-container">
				<!-- 동적으로 교육 항목이 추가됨 -->
			</div>

			<div class="add-training-btn">
				<button type="button">＋ 추가</button>
			</div>
		</section>

		<section class="section-block" id="section-intro">
			<h2>자기소개서</h2>
			<div class="field-block">
				<textarea rows="10" id="selfIntroduction" placeholder="자기소개서 내용을 입력하세요"></textarea>
			</div>
		</section>

		<section class="section-block" id="section-license">
			<h2>자격증</h2>
			<div id="certificate-container">
				<!-- 동적으로 자격증 항목이 추가됨 -->
			</div>

			<div class="add-license-btn">
				<button type="button">＋ 추가</button>
			</div>
		</section>

		<section class="section-block" id="section-portfolio">
			<h2>포트폴리오</h2>
			<div id="portfolio-container">
				<!-- 동적으로 포트폴리오 항목이 추가됨 -->
			</div>

			<div class="add-portfolio-btn">
				<button type="button">＋ 추가</button>
			</div>
		</section>

	</section>
</div>
<jsp:include page="../common/footer.jsp" />

<!-- 서버 데이터를 JavaScript로 전달 -->
<script>
	// 에러 메시지 표시
	<c:if test="${not empty errorMessage}">
	alert('${errorMessage}');
	</c:if>

	// 신규 작성 모드 설정
	window.isEditMode = false;
	window.currentResumeId = null;
	window.uploadedImageUrl = '';
</script>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-3fp9tS8p9A2Mq7Qz+S8jfwD+xdgu9T+O+NRZz8N5eA8=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/resume/resumeCommon.js"></script>
<script src="/js/resume/resumeCreate.js"></script>
</body>
</html>