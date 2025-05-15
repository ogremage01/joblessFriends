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

<div class="wrapper-container">	
	<div class="sidebar">
	<!-- 사이드바 메뉴 -->
	<div class="sidebar-menu">
	  <div class="sidebar-title">▲ TOP</div>
	  <ul class="sidebar-links">
	    <li><a href="#section-personal">인적사항</a></li>
	    <li><a href="#section-job">희망직무</a></li>
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
	  <button class="btn-finish">작성완료</button>
	  <button class="btn-preview">미리보기</button>
	  <button class="btn-cancel">작성취소</button>
	</div>
	
</div>

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
				<div class="photo-box" id="photoBox">
					<img id="profilePreview" src="/img/default-profile.png" alt="미리보기"
						style="max-width:100%; max-height:100%; display: none;" />
					<span id="photoPlaceholder">+</span>
					<input type="file" id="profileImageInput" style="display: none;" accept="image/*"  />
				</div>
				
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
	
	<section class="section-block">
	<h2>경력</h2>
	<div class="career-entry">
		<button class="delete-btn">×</button>
		
		<div class="grid-3">
			<div class="field-block">
				<label>회사명</label>
				<input type="text" placeholder="회사명을 입력해주세요" />
			</div>
			<div class="field-block">
				<label>부서명</label>
				<input type="text" placeholder="부서명을 입력해주세요" />
			</div>
			<div class="field-block">
				<label>입사년월</label>
				<input type="text" placeholder="예시) 2025.04" />
			</div>
		</div>
		
		<div class="grid-3">
			<div class="field-block">
				<label>퇴사년월</label>
				<input type="text" placeholder="예시) 2025.04" />
			</div>
			<div class="field-block">
				<label>직급/직책</label>
				<input type="text" placeholder="직급/직책을 입력해주세요" />
			</div>
			<div class="field-block">
				<label>담당직무</label>
				<input type="text" placeholder="담당직무를 입력해주세요" />
			</div>
		</div>
		
			<div class="field-block">
				<label>연봉 (만원)</label>
				<input type="text" placeholder="예시) 2400" />
			</div>
		
		<div class="field-block">
			<label>담당업무</label>
			<textarea rows="4" placeholder="담당하신 업무와 성과에 대해 간단명료하게 적어주세요"></textarea>
		</div>
				
	</div>
	
	<div class="add-career-btn">
		<button type="button">＋ 추가</button>
	</div>
	</section>
	
	<section class="section-block">
		<h2>교육</h2>
	
		<div class="training-entry">
			<button class="delete-btn">×</button>
		
		<div class="grid-4">
			<div class="field-block">
				<label>교육명</label>
				<input type="text" placeholder="교육명을 입력해주세요" />
			</div>
			<div class="field-block">
				<label>교육기관</label>
				<input type="text" placeholder="교육기관을 입력해주세요" />
			</div>
			<div class="field-block">
				<label>시작년월</label>
				<input type="text" placeholder="예시) 2025.04" />
			</div>
			<div class="field-block">
				<label>종료년월</label>
				<input type="text" placeholder="예시) 2025.04" />
			</div>
		</div>
		
			<div class="field-block">
			<label>내용</label>
				<textarea rows="3" placeholder="이수하신 교육과정에 대해 적어주세요"></textarea>
			</div>
		</div>
		
		<div class="add-education-btn">
			<button type="button">＋ 추가</button>
		</div>
	</section>
	
	<section class="section-block">
		<h2>자기소개서</h2>
		<div class="field-block">
			<textarea rows="10" placeholder="자기소개서 내용을 입력하세요"></textarea>
		</div>
	</section>
	
	<section class="section-block">
		<h2>자격증</h2>
		
		<div class="license-entry">
			<button class="delete-btn">×</button>
		
			<div class="grid-3">
				<div class="field-block">
					<label>자격증명</label>
					<input type="text" placeholder="자격증명을 입력해주세요" />
				</div>
				<div class="field-block">
					<label>발행처</label>
					<input type="text" placeholder="발행처를 입력해주세요" />
				</div>
				<div class="field-block">
					<label>취득날짜</label>
					<input type="text" placeholder="예시) 2025.04" />
				</div>
			</div>
		</div>
		
		<div class="add-license-btn">
			<button type="button">＋ 추가</button>
		</div>
	</section>
		
	<section class="section-block">
		<h2>포트폴리오</h2>
		
		<div class="portfolio-upload-box">
			<label for="portfolioFile">
				<span class="plus-icon">＋</span>
					파일추가(최대20MB)
			</label>
			<input type="file" id="portfolioFile" style="display: none;" />
		</div>
	</section>
		
		
      <!-- 이후 다른 섹션들(스킬, 학력, 경력, 교육 등)은 동일한 패턴으로 복붙해서 확장하면 됩니다 -->

    </section>
</div>
	<jsp:include page="../common/footer.jsp" />
	
<script>

document.getElementById("photoBox").addEventListener("click", function () {
    document.getElementById("profileImageInput").click();
});

document.getElementById("profileImageInput").addEventListener("change", function () {
    const fileInput = this;
    const file = fileInput.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("profileImage", file);
    formData.append("resumeId", 1); // ✔️ 실제 resumeId 값으로 교체 필요

    fetch("/resume/uploadProfileImage", {
        method: "POST",
        body: formData
    })
    .then(resp => resp.text())
    .then(fileName => {
        if (fileName !== "error") {
            // 미리보기 업데이트
            const imgPreview = document.getElementById("profilePreview");
            imgPreview.src = "/upload/profile/" + fileName;
            imgPreview.style.display = "block";
            document.getElementById("photoPlaceholder").style.display = "none";
        } else {
            alert("업로드 실패");
        }
    })
    .catch(err => {
        console.error(err);
        alert("에러 발생");
    });
});


</script>
</body>
</html>