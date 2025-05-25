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
				<label class="photo-box" id="photoBox">
					<div class="photo-text">
						사진추가
						<span class="plus-icon">+</span>
					</div>
					<img id="previewImage" src="#" alt="미리보기" style="display: none;" />
				</label>
					<input type="file" id="profileImageInput" style="display: none;" onchange="previewImage(event)" />
			</div>

		</div>
	</div>


      <!-- 희망직무 -->
	<section class="section-block">
		<h2>희망직무</h2>
		<div class="grid-2">
			<div class="field-block">
				<label>직군</label>
				<select id="jobGroupSelect">
					<option value="">직군 선택</option>
				</select>
			</div>
				<div class="field-block">
				<label>직무</label>
				<select id="jobSelect">
					<option value="">직무 선택</option>
				</select>
			</div>
		</div>
	</section>
      
     <!-- 스킬 -->
	<section class="section-block">
		<h2 class="section-title">스킬</h2>
			<p id="selectedJobGroupLabel" class="selected-job-group-label" style="display: none;"></p>
			
		<div id="skillContainer" class="tag-select">
			
		</div>
	</section>

	<section class="section-block">
		<h2>학력</h2>
		<div class="edu-row-combined">		   
			   <!-- 학교 구분 선택 -->
			<div class="field-block">
				<label>구분</label>
				<select id="schoolTypeSelect">
					<option value="high">고등학교</option>
					<option value="univ4">대학교(4년)</option>
					<option value="univ2">대학교(2,3년)</option>
				</select>
			</div>
			
			<!-- 여기에 동적으로 필드가 채워짐 -->
			<div id="edu-dynamic-fields" class="edu-dynamic-fields" style="display: contents;"></div>
			
			<!-- 삭제버튼 -->
			<button type="button" class="delete-btn">×</button>
			
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
		
    </section>
</div>
	<jsp:include page="../common/footer.jsp" />
	
<script>

document.getElementById("photoBox").addEventListener("click", function () {
    document.getElementById("profileImageInput").click();
});

document.getElementById("profileImageInput").addEventListener("change", function () {
    const file = this.files[0];
    if (!file) return;

    // 미리보기
    const reader = new FileReader();
    reader.onload = function (e) {
        const preview = document.getElementById("previewImage");
        preview.src = e.target.result;
        preview.style.display = "block";
        
        //텍스트 숨기기
        const photoText = document.querySelector(".photo-text");
        if (photoText) photoText.style.display = "none";
    };
    reader.readAsDataURL(file);

    // 서버로 비동기 전송
    const formData = new FormData();
    formData.append('profileImage', file);

    fetch('/profile-temp/uploadImage', {
        method: 'POST',
        body: formData
    })
    .then(res => res.text())
    .then(data => {
        console.log("업로드 결과:", data);
    })
    .catch(err => {
        console.error("업로드 실패", err);
    });
});

  document.addEventListener("DOMContentLoaded", function () {
  const jobGroupSelect = document.getElementById("jobGroupSelect");
  const jobSelect = document.getElementById("jobSelect");
  const skillContainer = document.getElementById("skillContainer");
  const selectedJobGroupLabel = document.getElementById("selectedJobGroupLabel");
  const selectedSkills = new Set(); // 선택된 스킬 저장용

  // 직군 목록 불러오기
  fetch("/jobGroup/list")
    .then((res) => res.json())
    .then((data) => {
      data.forEach((group) => {
        const option = document.createElement("option");
        option.value = group.jobGroupId;
        option.textContent = group.jobGroupName;
        jobGroupSelect.appendChild(option);
      });
    });

  // 직군 선택 시 직무 + 스킬 처리
  jobGroupSelect.addEventListener("change", function () {
    const jobGroupId = this.value;
    const selectedGroupName = this.options[this.selectedIndex].textContent;

    // 직군 이름 표시
    if (jobGroupId) {
      selectedJobGroupLabel.style.display = "block";
      selectedJobGroupLabel.textContent = selectedGroupName;
    } else {
      selectedJobGroupLabel.style.display = "none";
    }

    // 직무 초기화 및 가져오기
    jobSelect.innerHTML = '<option value="">직무 선택</option>';
    fetch('/job/list?jobGroupId=' + jobGroupId)
      .then(res => res.json())
      .then(data => {
        data.forEach(job => {
          const option = document.createElement("option");
          option.value = job.jobId;
          option.textContent = job.jobName;
          jobSelect.appendChild(option);
        });
      });

    // 스킬 초기화 및 가져오기
    skillContainer.innerHTML = "";
    fetch('/skill/list?jobGroupId=' + jobGroupId)
      .then(res => res.json())
      .then(tags => {
        renderSkillTags(tags);
      })
      .catch(err => {
        console.error("스킬 요청 실패:", err);
      });
  });
  // 스킬 태그 버튼 생성 및 선택 토글 기능
  function renderSkillTags(tags) {
    skillContainer.innerHTML = "";
    selectedSkills.clear();

    tags.forEach(tag => {
      const btn = document.createElement("button");
      btn.className = "tag-button";
      btn.textContent = tag.tagName;
      btn.dataset.tagId = tag.tagId;

      // 버튼 클릭 시 선택/해제 토글
      btn.addEventListener("click", function () {
        const tagId = this.dataset.tagId;

        if (selectedSkills.has(tagId)) {
          selectedSkills.delete(tagId);
          this.classList.remove("selected");
        } else {
          selectedSkills.add(tagId);
          this.classList.add("selected");
        }
      });

      skillContainer.appendChild(btn);
    });
  }
});
  
//학력 구분(select) 선택에 따라 동적 입력 필드를 생성하는 로직

//페이지가 로드되면 실행
document.addEventListener("DOMContentLoaded", function () {
 const container = document.getElementById("edu-dynamic-fields"); // 입력 필드 삽입 대상 영역
 const select = document.getElementById("schoolTypeSelect"); // 고등학교/대학교(2,3년/4년) 선택 박스
 
 // 요소가 없으면 중단
 if (!container || !select) {
   console.error("DOM 요소를 찾을 수 없습니다.");
   return;
 }

 console.log("초기값:", select.value); // ✅ 여기는 이제 실행됨
 renderFields(select.value); // ✅ 기본 필드 생성

 select.addEventListener("change", () => {
   console.log("선택 변경됨:", select.value);
   renderFields(select.value);
 });

 // 학력 구분 선택에 따른 필드 렌더링 함수
 function renderFields(type) {
   container.innerHTML = ""; // 기존 입력 영역 초기화

   if (type === "high") {
     // 고등학교 선택 시 표시할 필드들
     container.innerHTML = `
    	 <div class="field-block">
    	    <label>학교명</label>
    	    <input type="text" id="schoolNameInput" placeholder="학교명을 입력해주세요" />
    	    <ul id="schoolSuggestions" class="autocomplete-list" style="display: none;"></ul>
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
    	`;
     attachHighschoolAutocomplete(); // 자동완성 기능 부착

   } else {
     // 대학교 선택 시 표시할 필드들 (입학/졸업/전공 포함)
     container.innerHTML = `
    	 <div class="edu-fields-wrapper">
    	    <div class="field-block full-width">
    	      <label>학교명</label>
    	      <input type="text" id="univNameInput" placeholder="대학교명을 입력해주세요" autocomplete="off" />
    	      <ul id="univSuggestions" class="autocomplete-list" style="display: none;"></ul>
    	    </div>
    	    <div class="field-block">
    	      <label>입학년월</label>
    	      <input type="text" placeholder="예시) 2022.03" />
    	    </div>
    	    <div class="field-block">
    	      <label>졸업년월</label>
    	      <input type="text" placeholder="예시) 2025.02" />
    	    </div>
    	    <div class="field-block">
    	      <label>졸업상태</label>
    	      <select>
    	        <option>졸업예정</option>
    	        <option>졸업</option>
    	        <option>재학중</option>
    	      </select>
    	    </div>
    	    <div class="field-block full-width">
    	      <label>전공명</label>
    	      <input type="text" placeholder="예시) 컴퓨터공학과" />
    	    </div>
    	  </div>
     `;
     attachUniversityAutocomplete(type); // 대학교 자동완성 기능 부착
   }
 };

 // 셀렉트박스 변경 시 해당 학력 유형의 필드 렌더링
 select.addEventListener("change", () => {
	 console.log("초기 렌더링:", select.value);
   renderFields(select.value);
 });

 // 초기 렌더링 (페이지 처음 로드될 때 default 선택값 기준으로)
 console.log("초기 렌더링:", select.value);
 renderFields(select.value);
});

//고등학교 자동완성 함수 정의 (기존 자동완성 로직을 이곳에 넣음)
function attachHighschoolAutocomplete() {
 const schoolInput = document.getElementById("schoolNameInput");
 const suggestionList = document.getElementById("schoolSuggestions");
 const schoolTypeSelect = document.getElementById("schoolTypeSelect");
 let debounceTimer;

 schoolInput.addEventListener("input", function () {
   const keyword = this.value.trim();
   if (schoolTypeSelect.value !== "high") return; // 고등학교 아닐 시 무시
   clearTimeout(debounceTimer);
   if (keyword.length < 2) return;

   debounceTimer = setTimeout(() => {
     fetch("/api/school/search?keyword=" + encodeURIComponent(keyword))
       .then(res => res.json())
       .then(data => {
         suggestionList.innerHTML = "";
         if (data.length > 0) {
           suggestionList.style.display = "block";
           data.forEach(item => {
             const li = document.createElement("li");
             li.textContent = item.schoolName;
             li.addEventListener("click", () => {
               schoolInput.value = item.schoolName;
               suggestionList.innerHTML = "";
               suggestionList.style.display = "none";
             });
             suggestionList.appendChild(li);
           });
         } else {
           suggestionList.style.display = "none";
         }
       });
   }, 150);
 });

 // 외부 클릭 시 자동완성 숨기기
 document.addEventListener("click", function (e) {
   if (!schoolInput.contains(e.target) && !suggestionList.contains(e.target)) {
     suggestionList.style.display = "none";
   }
 });
}

//대학교 자동완성 함수 정의
function attachUniversityAutocomplete(type) {
 const input = document.getElementById("univNameInput");
 const list = document.getElementById("univSuggestions");
 let debounce;

 input.addEventListener("input", function () {
   const keyword = this.value.trim();
   clearTimeout(debounce);
   if (keyword.length < 2) {
     list.style.display = "none";
     return;
   }

   debounce = setTimeout(() => {
	   fetch("/api/university/search?keyword=" + encodeURIComponent(keyword) + "&schoolType=" + type)
       .then(res => res.json())
       .then(data => {
         list.innerHTML = "";
         if (data.length) {
           list.style.display = "block";
           data.forEach(u => {
             const li = document.createElement("li");
             li.textContent = u.schoolName;
             li.addEventListener("click", () => {
               input.value = u.schoolName;
               list.innerHTML = "";
               list.style.display = "none";
             });
             list.appendChild(li);
           });
         } else {
           list.style.display = "none";
         }
       });
   }, 150);
 });

 document.addEventListener("click", (e) => {
   if (!input.contains(e.target) && !list.contains(e.target)) {
     list.style.display = "none";
   }
 });
}

</script>
</body>
</html>