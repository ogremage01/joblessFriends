<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이력서 폼</title>
<link rel="stylesheet" href="/css/resume/resumeStyle.css" />
</head>
<body>
	<jsp:include page="../common/header.jsp"/>

    <section class="resume-wrapper">
	<h1 class="resume-title">이력서 제목을 입력하세요</h1>

      <!-- 인적사항 -->
	<div class="section-block">
		<h2>인적사항</h2>
		<div class="grid-4"> <!-- 총 4열 -->
			<!-- 1행 -->
			<div class="field-block">
				<label>이름</label>
				<input type="text" placeholder="예시) 홍길동" />
			</div>
			<div class="field-block">
				<label>생년월일</label>
				<input type="text" placeholder="예시) 2025-04-16" />
			</div>
			<div class="field-block">
				<label>전화번호</label>
				<input type="text" placeholder="예시) 01012349999" />
			</div>
			<div class="field-block">
				<label>주소</label>
				<input type="text" placeholder="주소를 입력해주세요" />
			</div>
			
			<!-- 2행 -->
			<div class="field-block">
				<label>상세주소</label>
				<input type="text" placeholder="상세주소" />
			</div>
				<div class="field-block">
				<label>메일</label>
				<input type="text" placeholder="예시) test@mail.com" />
			</div>
			<div class="photo-wrapper">
				<label>사진추가</label>
				<div class="photo-box">+</div>
			</div>
		</div>
	</div>


      <!-- 희망직무 -->
	<section class="section-block">
		<h2>희망직무</h2>
		<div class="grid-2">
			<div class="field-block">
				<label>직군</label>
				<input type="text" value="IT 개발·데이터" />
			</div>
				<div class="field-block">
				<label>직무</label>
				<input type="text" value="데이터 사이언티스트" />
			</div>
		</div>
	</section>
      
     <!-- 스킬 -->
	<section class="section-block">
		<h2 class="section-title">스킬</h2>
			<input type="text" class="tag-input" placeholder="스킬을 입력해주세요" />
			<br></br>
		<div class="tag-list">
			<span class="tag">Java</span>
			<span class="tag">Spring Boot</span>
			<span class="tag">MySQL</span>
			<span class="tag">React</span>
		</div>
	</section>

	<section class="section-block">
		<h2>학력</h2>
		<div class="education-entry">
			<div class="field-block">
				<label>구분</label>
				<select>
					<option>고등학교</option>
					<option>대학교(4년)</option>
				</select>
			</div>
		<div class="field-block">
			<label>학교명</label>
			<input type="text" placeholder="학교명을 입력해주세요" />
		</div>
		<div class="field-block">
			<label>졸업년도</label>
			<input type="text" placeholder="예시) 2025" />
		</div>
		<div class="field-block">
			<label>졸업상태</label>
				<select>
					<option>졸업예정</option>
					<option>졸업</option>
					<option>재학중</option>
				</select>
		</div>
			<button class="delete-btn">×</button>
		</div>
		
		<div class="add-education-btn">
			<button type="button">＋ 추가</button>
		</div>
	</section>
		
      <!-- 이후 다른 섹션들(스킬, 학력, 경력, 교육 등)은 동일한 패턴으로 복붙해서 확장하면 됩니다 -->

    </section>
	</div>
	
	<jsp:include page="../common/footer.jsp" />
</body>
</html>