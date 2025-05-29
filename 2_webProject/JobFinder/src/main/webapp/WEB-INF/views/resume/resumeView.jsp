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
	  <button id="btn-finish" class="btn-finish">작성완료</button>
	  <button class="btn-preview">미리보기</button>
	  <button class="btn-cancel">작성취소</button>
	</div>
	
</div>

    <section class="resume-wrapper">
    	<div class="resumeTitle" style="margin: 1px;">
		    <h1>제목</h1>
			<input type="text" id="title" placeholder="이력서 제목을 입력하세요" />
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
				<input type="text" id="phoneNumber" placeholder="예시) 01012349999" />
			</div>
			
			<!-- 2행 -->
			<div class="field-block">
				<label>우편번호</label>
				<input type="text" id="postalCode" placeholder="우편번호" readonly />
				<button type="button" class="address-search-btn" onclick="execDaumPostcode()">🔍</button>
			</div>
			<div class="field-block">
				<label>주소</label>
				<input type="text" id="roadAddress" placeholder="주소를 입력해주세요" readonly />
			</div>
			<div class="field-block">
				<label>상세주소</label>
				<input type="text" id="detailAddress" placeholder="상세주소" />
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
					<img id="previewImage" src="#" alt="미리보기" style="display: none;" />
				</label>
					<input type="file" id="profileImageInput" style="display: none;" onchange="previewImage(event)" />
			</div>

		</div>
	</div>


      <!-- 희망직무 -->
	<section class="section-block" id="section-job">
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
	<section class="section-block" id="section-skill">
		<h2 class="section-title">스킬</h2>
			<p id="selectedJobGroupLabel" class="selected-job-group-label" style="display: none;"></p>
			
		<div id="skillContainer" class="tag-select">
			
		</div>
	</section>

	<section class="section-block" id="section-edu">
		<h2>학력</h2>
		<div class="edu-row-combined">		   
			   <!-- 학교 구분 선택 -->
			<div class="field-block">
				<label>구분</label>
				<select id="schoolTypeSelect">
					<option value="">선택</option>
					<option value="high">고등학교</option>
					<option value="univ4">대학교(4년)</option>
					<option value="univ2">대학교(2,3년)</option>
				</select>
			</div>
			
			<!-- 여기에 동적으로 필드가 채워짐 -->
			<div id="edu-dynamic-fields"style="display: contents;"></div>
			
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
		<div id="education-container">
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
	
<script>

// 전역 변수
let ignoreNextInput = false;
window.uploadedImageUrl = '';
let isEditMode = false; // 수정 모드 여부
let currentResumeId = null; // 현재 수정 중인 이력서 ID

// DOM 로드 완료 후 실행
document.addEventListener("DOMContentLoaded", function () {
  
  // URL에서 resumeId 파라미터 확인 (수정 모드인지 판단)
  const urlParams = new URLSearchParams(window.location.search);
  currentResumeId = urlParams.get('resumeId');
  
  if (currentResumeId) {
    isEditMode = true;
    console.log('수정 모드:', currentResumeId);
    loadResumeData(currentResumeId);
    
    // 작성완료 버튼 텍스트 변경
    document.querySelector('.btn-finish').textContent = '수정완료';
  }
  
  // 직군, 직무 select와 스킬 컨테이너 요소 캐싱
  const jobGroupSelect = document.getElementById("jobGroupSelect");
  const jobSelect = document.getElementById("jobSelect");
  const skillContainer = document.getElementById("skillContainer");
  const selectedJobGroupLabel = document.getElementById("selectedJobGroupLabel");
  const selectedSkills = new Set(); // 사용자 선택한 스킬 ID 저장

  // 직군 목록 비동기 요청 후 select 옵션 채우기
  fetch("/jobGroup/list")
    .then((res) => res.json())
    .then((data) => {
      data.forEach((group) => {
        const option = document.createElement("option");
        option.value = group.jobGroupId;
        option.textContent = group.jobGroupName;
        jobGroupSelect.appendChild(option);
      });
      
      // 수정 모드일 때 이력서 데이터 로드
      if (isEditMode && currentResumeId) {
        // 직군 옵션이 로드된 후에 이력서 데이터 로드
        // loadResumeData는 직군/직무 로드 후에 호출되어야 함
      }
    });

  // 작성완료 버튼 이벤트 등록
  document.querySelector('.btn-finish').addEventListener('click', saveResume);

  // 직군 선택 시 직무 목록 + 스킬 태그 동시 갱신
  jobGroupSelect.addEventListener("change", function () {
    const jobGroupId = this.value;
    const selectedGroupName = this.options[this.selectedIndex].textContent;

    // 직군명 표시
    if (jobGroupId) {
      selectedJobGroupLabel.style.display = "block";
      selectedJobGroupLabel.textContent = selectedGroupName;
    } else {
      selectedJobGroupLabel.style.display = "none";
    }

    // 직무 목록 초기화 후 다시 불러오기
    jobSelect.innerHTML = '<option value="">직무 선택</option>';
    fetch("/job/list?jobGroupId=" + jobGroupId)
      .then(res => res.json())
      .then(data => {
        data.forEach(job => {
          const option = document.createElement("option");
          option.value = job.jobId;
          option.textContent = job.jobName;
          jobSelect.appendChild(option);
        });
      });

    // 스킬 목록 초기화 후 다시 불러오기
    skillContainer.innerHTML = "";
    fetch("/skill/list?jobGroupId=" + jobGroupId)
      .then(res => res.json())
      .then(tags => {
        renderSkillTags(tags);
      })
      .catch(err => {
        console.error("스킬 요청 실패:", err);
      });
  });

  // 스킬 태그 버튼들을 생성하고 클릭시 선택/해제 처리
  function renderSkillTags(tags) {
    skillContainer.innerHTML = "";
    selectedSkills.clear(); // 이전 선택 제거

    tags.forEach(tag => {
      const btn = document.createElement("button");
      btn.className = "tag-button";
      btn.textContent = tag.tagName;
      btn.dataset.tagId = tag.tagId;

      // 클릭 시 선택 토글 처리
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
  
  // 학력 관련 기능
  const eduContainer = document.getElementById("edu-dynamic-fields");
  const schoolTypeSelect = document.getElementById("schoolTypeSelect");

  // 학력 입력 필드 타입 선택 시 초기화 + 필드 생성
  schoolTypeSelect.addEventListener("change", () => {
    eduContainer.innerHTML = "";
    const type = schoolTypeSelect.value;
    if (!type) return;

    const firstEntry = createSchoolEntry(type);
    eduContainer.appendChild(firstEntry);
    attachAutocomplete(firstEntry, type);
  });

  // +추가 버튼 클릭 시 입력 항목 추가
  document.querySelector(".add-education-btn button").addEventListener("click", () => {
    const selectedType = schoolTypeSelect.value;
    if (!selectedType) {
      alert("구분을 먼저 선택해주세요.");
      return;
    }
    const newEntry = createSchoolEntry(selectedType);
    eduContainer.appendChild(newEntry);
    attachAutocomplete(newEntry, selectedType);
  });

  // 경력 추가 버튼
  document.querySelector(".add-career-btn button").addEventListener("click", () => {
    const careerContainer = document.getElementById("career-container");
    const newCareer = createCareerEntry();
    careerContainer.appendChild(newCareer);
  });

  // 교육 추가 버튼
  document.querySelector(".add-training-btn button").addEventListener("click", () => {
    const educationContainer = document.getElementById("education-container");
    const newEducation = createEducationEntry();
    educationContainer.appendChild(newEducation);
  });

  // 자격증 추가 버튼
  document.querySelector(".add-license-btn button").addEventListener("click", () => {
    const certificateContainer = document.getElementById("certificate-container");
    const newCertificate = createCertificateEntry();
    certificateContainer.appendChild(newCertificate);
  });

  // 포트폴리오 추가 버튼
  document.querySelector(".add-portfolio-btn button").addEventListener("click", () => {
    const portfolioContainer = document.getElementById("portfolio-container");
    const newPortfolio = createPortfolioEntry();
    portfolioContainer.appendChild(newPortfolio);
  });
  
  // 학력 입력 DOM 템플릿 생성 함수
  function createSchoolEntry(type) {
    const wrapper = document.createElement("div");
    wrapper.className = "edu-row-combined school-entry";

    // 고등학교
    if (type === "high") {
      wrapper.innerHTML = `
        <div class="field-block school-autocomplete-block">
          <label>학교명</label>
          <input type="text" name="schoolName" placeholder="학교명을 입력해주세요" autocomplete="off" />
          <ul class="autocomplete-list" style="display: none;"></ul>
        </div>
        <div class="field-block">
          <label>졸업년도</label>
          <input type="text" name="yearOfGraduation" placeholder="예시) 2025" />
        </div>
        <div class="field-block">
          <label>졸업상태</label>
          <select name="status">
            <option>졸업예정</option>
            <option>졸업</option>
            <option>재학중</option>
          </select>
        </div>
        <input type="hidden" name="sortation" value="high" />
        <button type="button" class="delete-btn">×</button>
      `;
    } else {
      // 대학교 (2년/4년)
      wrapper.innerHTML = `
        <div class="field-block school-autocomplete-block">
          <label>학교명</label>
          <input type="text" name="schoolName" placeholder="대학교명을 입력해주세요" autocomplete="off" />
          <ul class="autocomplete-list" style="display: none;"></ul>
        </div>
        <div class="field-block">
          <label>전공명</label>
          <input type="text" name="majorName" placeholder="전공명을 입력해주세요" autocomplete="off" />
          <ul class="autocomplete-list" style="display: none;"></ul>
        </div>
        <div class="field-block">
          <label>입학년월</label>
          <input type="text" name="startDate" placeholder="예시) 2020.03" />
        </div>
        <div class="field-block">
          <label>졸업년월</label>
          <input type="text" name="endDate" placeholder="예시) 2024.02" />
        </div>
        <div class="field-block">
          <label>졸업상태</label>
          <select name="status">
            <option>졸업예정</option>
            <option>졸업</option>
            <option>재학중</option>
          </select>
        </div>
        <input type="hidden" name="sortation" value="${type}" />
        <button type="button" class="delete-btn">×</button>
      `;
    }

    // 삭제 버튼 기능
    wrapper.querySelector(".delete-btn").addEventListener("click", () => wrapper.remove());
    return wrapper;
  }

  // 경력 엔트리 생성 함수
  function createCareerEntry() {
    const wrapper = document.createElement("div");
    wrapper.className = "career-entry";
    wrapper.innerHTML = `
      <button class="delete-btn">×</button>
      
      <div class="grid-3">
        <div class="field-block">
          <label>회사명</label>
          <input type="text" name="companyName" placeholder="회사명을 입력해주세요" />
        </div>
        <div class="field-block">
          <label>부서명</label>
          <input type="text" name="departmentName" placeholder="부서명을 입력해주세요" />
        </div>
        <div class="field-block">
          <label>입사년월</label>
          <input type="text" name="hireYm" placeholder="예시) 2025.04" />
        </div>
      </div>
      
      <div class="grid-3">
        <div class="field-block">
          <label>퇴사년월</label>
          <input type="text" name="resignYm" placeholder="예시) 2025.04" />
        </div>
        <div class="field-block">
          <label>직급/직책</label>
          <input type="text" name="position" placeholder="직급/직책을 입력해주세요" />
        </div>
        <div class="field-block">
          <label>담당직군</label>
          <input type="text" name="jobTitle" placeholder="담당직군을 입력해주세요" />
        </div>
      </div>
      
      <div class="grid-2">
        <div class="field-block">
          <label>담당직무</label>
          <input type="text" name="taskRole" placeholder="담당직무를 입력해주세요" />
        </div>
        <div class="field-block">
          <label>연봉 (만원)</label>
          <input type="text" name="salary" placeholder="예시) 2400" />
        </div>
      </div>
      
      <div class="field-block">
        <label>담당업무</label>
        <textarea rows="5" name="workDescription" placeholder="담당하신 업무와 성과에 대해 간단명료하게 적어주세요"></textarea>
      </div>
    `;
    
    wrapper.querySelector(".delete-btn").addEventListener("click", () => wrapper.remove());
    return wrapper;
  }

  // 교육 엔트리 생성 함수
  function createEducationEntry() {
    const wrapper = document.createElement("div");
    wrapper.className = "education-entry";
    wrapper.innerHTML = `
      <button class="delete-btn">×</button>
    
      <div class="grid-4">
        <div class="field-block">
          <label>교육명</label>
          <input type="text" name="eduName" placeholder="교육명을 입력해주세요" />
        </div>
        <div class="field-block">
          <label>교육기관</label>
          <input type="text" name="eduInstitution" placeholder="교육기관을 입력해주세요" />
        </div>
        <div class="field-block">
          <label>시작년월</label>
          <input type="text" name="startDate" placeholder="예시) 2025.04" />
        </div>
        <div class="field-block">
          <label>종료년월</label>
          <input type="text" name="endDate" placeholder="예시) 2025.04" />
        </div>
      </div>
      
      <div class="field-block">
        <label>내용</label>
        <textarea rows="3" name="content" placeholder="이수하신 교육과정에 대해 적어주세요"></textarea>
      </div>
    `;
    
    wrapper.querySelector(".delete-btn").addEventListener("click", () => wrapper.remove());
    return wrapper;
  }

  // 자격증 엔트리 생성 함수
  function createCertificateEntry() {
    const wrapper = document.createElement("div");
    wrapper.className = "certificate-entry";
    wrapper.innerHTML = `
      <button class="delete-btn">×</button>
    
      <div class="grid-3">
        <div class="field-block">
          <label>자격증명</label>
          <input type="text" name="certificateName" placeholder="자격증명을 입력해주세요" />
        </div>
        <div class="field-block">
          <label>발행처</label>
          <input type="text" name="issuingAuthority" placeholder="발행처를 입력해주세요" />
        </div>
        <div class="field-block">
          <label>취득날짜</label>
          <input type="text" name="acquisitionDate" placeholder="예시) 2025.04" />
        </div>
      </div>
      <input type="hidden" name="certificateId" value="0" />
    `;
    
    wrapper.querySelector(".delete-btn").addEventListener("click", () => wrapper.remove());
    return wrapper;
  }

  // 포트폴리오 엔트리 생성 함수
  function createPortfolioEntry() {
    const wrapper = document.createElement("div");
    wrapper.className = "portfolio-entry";
    wrapper.innerHTML = `
      <button class="delete-btn">×</button>
      <div class="portfolio-upload-box">
        <label>
          <span class="plus-icon">＋</span>
          파일추가(최대20MB)
          <input type="file" name="portfolioFile" style="display: none;" />
        </label>
        <input type="hidden" name="fileName" value="" />
        <input type="hidden" name="storedFileName" value="" />
        <input type="hidden" name="fileExtension" value="" />
      </div>
    `;
    
    const fileInput = wrapper.querySelector('input[type="file"]');
    const fileNameInput = wrapper.querySelector('input[name="fileName"]');
    const storedFileNameInput = wrapper.querySelector('input[name="storedFileName"]');
    const fileExtensionInput = wrapper.querySelector('input[name="fileExtension"]');
    
    fileInput.addEventListener('change', function(e) {
      const file = e.target.files[0];
      if (file) {
        fileNameInput.value = file.name;
        storedFileNameInput.value = 'stored_' + Date.now() + '_' + file.name;
        fileExtensionInput.value = file.name.split('.').pop().toLowerCase();
        
        // 여기서 실제 파일 업로드 로직을 구현할 수 있습니다
        console.log('파일 업로드:', file.name);
      }
    });
    
    wrapper.querySelector(".delete-btn").addEventListener("click", () => wrapper.remove());
    return wrapper;
  }

  // 자동완성 이벤트 연결 (학교명/전공명)
  function attachAutocomplete(wrapper, type) {
    // 학교명
    const schoolInput = wrapper.querySelector('input[name="schoolName"]');
    const schoolList = wrapper.querySelector('ul.autocomplete-list');

    if (schoolInput && schoolList) {
      let timer;
      schoolInput.addEventListener("input", function () {
        const keyword = this.value.trim();
        clearTimeout(timer);
        if (keyword.length < 2) {
          schoolList.style.display = "none";
          return;
        }
        timer = setTimeout(() => {
          const url = type === "high"
            ? `/api/school/search?keyword=` + encodeURIComponent(keyword)
            : `/api/university/search?keyword=` + encodeURIComponent(keyword) + "&schoolType=" + encodeURIComponent(type);

          fetch(url)
            .then(res => res.json())
            .then(data => {
              schoolList.innerHTML = "";
              if (data.length > 0) {
                schoolList.style.display = "block";
                data.forEach(item => {
                  const li = document.createElement("li");
                  li.textContent = item.schoolName;
                  li.addEventListener("mousedown", () => {
                    schoolInput.value = item.schoolName;
                    schoolList.style.display = "none";
                  });
                  schoolList.appendChild(li);
                });
              } else {
                schoolList.style.display = "none";
              }
            });
        }, 150);
      });
    }

    // 전공명 (대학교만)
    if (type !== "high") {
      const majorInput = wrapper.querySelector('input[name="majorName"]');
      const majorList = majorInput?.nextElementSibling;
      if (majorInput && majorList) {
        let timer;
        majorInput.addEventListener("input", function () {
          const keyword = this.value.trim();
          clearTimeout(timer);
          if (keyword.length < 2) {
            majorList.style.display = "none";
            return;
          }
          timer = setTimeout(() => {
            fetch("/api/major/search?keyword=" + encodeURIComponent(keyword))
              .then(res => res.json())
              .then(data => {
                majorList.innerHTML = "";
                if (data.length > 0) {
                  majorList.style.display = "block";
                  data.forEach(item => {
                    const li = document.createElement("li");
                    li.textContent = item.majorName;
                    li.addEventListener("mousedown", () => {
                      majorInput.value = item.majorName;
                      majorList.style.display = "none";
                    });
                    majorList.appendChild(li);
                  });
                } else {
                  majorList.style.display = "none";
                }
              });
          }, 150);
        });
      }
    }
  }

});

// 프로필 이미지 업로드 및 미리보기 기능
// 사진 박스를 클릭하면 숨겨진 input[type="file"] 요소를 클릭해서 파일 선택창을 열어줌
document.getElementById("photoBox").addEventListener("click", function () {
  document.getElementById("profileImageInput").click();
});

// 사용자가 프로필 이미지를 선택했을 때
document.getElementById("profileImageInput").addEventListener("change", function () {
  const file = this.files[0];
  if (!file) return;

  // FileReader를 사용해서 사용자가 선택한 이미지를 미리보기로 보여줌
  const reader = new FileReader();
  reader.onload = function (e) {
    const preview = document.getElementById("previewImage");
    preview.src = e.target.result;
    preview.style.display = "block";
    
    window.uploadedImageUrl = e.target.result;

    // "사진추가" 텍스트는 숨김 처리
    const photoText = document.querySelector(".photo-text");
    if (photoText) photoText.style.display = "none";
  };
  reader.readAsDataURL(file);

  // 서버로 이미지 전송 (FormData 사용)
  const formData = new FormData();
  formData.append('profileImage', file);

  fetch('/profile-temp/uploadImage', {
    method: 'POST',
    body: formData
  })
    .then(res => res.text()) // 서버 응답 텍스트 출력
    .then(data => {
      uploadedImageUrl = data;  // 여기서 저장
      console.log("업로드 결과:", data);
    })
    .catch(err => {
      console.error("업로드 실패", err);
    });
});

// daum 주소 API 함수
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                addr += extraAddr;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postalCode').value = data.zonecode;
            document.getElementById("roadAddress").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

// 이력서 저장 함수
async function saveResume() {
  console.log("이력서 저장 함수 실행");

  try {
    // 이력서 데이터 수집
    const resumeData = collectResumeData();
    
    // 수정 모드인 경우 resumeId 추가
    if (isEditMode && currentResumeId) {
      resumeData.resumeId = parseInt(currentResumeId);
    }
    
    console.log("수집된 이력서 데이터:", resumeData);

    // 서버로 전송 (신규/수정 모두 save API 사용)
    const response = await fetch('/api/resume/save', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(resumeData),
      credentials: 'include'
    });

    const responseText = await response.text();

    if (response.ok) {
      console.log("이력서 저장 성공:", responseText);
      alert(isEditMode ? "이력서가 성공적으로 수정되었습니다!" : "이력서가 성공적으로 저장되었습니다!");
      
      // 목록 페이지로 이동
      window.location.href = '/resume/management';
    } else {
      console.error("이력서 저장 실패:", responseText);
      alert("이력서 저장에 실패했습니다: " + responseText);
    }
  } catch (error) {
    console.error("이력서 저장 중 오류:", error);
    alert("이력서 저장 중 오류가 발생했습니다: " + error.message);
  }
}

// 이력서 데이터 수집 함수 (통합)
function collectResumeData() {
  return {
    title: document.querySelector('#title')?.value || '제목없는 이력서',
    name: document.querySelector('#name')?.value || '',
    birthdate: document.querySelector('#birthdate')?.value || '',
    phoneNumber: document.querySelector('#phoneNumber')?.value || '',
    email: document.querySelector('#email')?.value || '',
    address: (document.querySelector('#roadAddress')?.value || '') + ' ' + (document.querySelector('#detailAddress')?.value || ''),
    selfIntroduction: document.querySelector('#selfIntroduction')?.value || '',
    profile: window.uploadedImageUrl || '',
    postalCodeId: parseInt(document.querySelector('#postalCode')?.value) || 0,

    jobGroupId: parseInt(document.querySelector('#jobGroupSelect')?.value) || 0,
    jobId: parseInt(document.querySelector('#jobSelect')?.value) || 0,

    schools: collectSchools(),
    careers: collectCareers(),
    educations: collectEducations(),
    certificateIds: collectCertificates(),
    portfolios: collectPortfolios()
  };
}

// 입력한 정보를 배열로 수집하는 함수들 (ResumeParser와 호환)

// 모든 학력 정보를 배열로 수집
function collectSchools() {
  const result = [];
  document.querySelectorAll('.school-entry').forEach(entry => {
    const sortation = entry.querySelector('input[name="sortation"]')?.value;
    const schoolName = entry.querySelector('input[name="schoolName"]')?.value;
    const status = entry.querySelector('select[name="status"]')?.value;

    if (sortation === "high") {
      const yearOfGraduation = entry.querySelector('input[name="yearOfGraduation"]')?.value;
      result.push({ 
        sortation, 
        schoolName, 
        yearOfGraduation, 
        status,
        majorName: null,
        startDate: null,
        endDate: null
      });
    } else {
      const majorName = entry.querySelector('input[name="majorName"]')?.value;
      const startDate = entry.querySelector('input[name="startDate"]')?.value;
      const endDate = entry.querySelector('input[name="endDate"]')?.value;
      result.push({
        sortation,
        schoolName,
        majorName,
        startDate,
        endDate,
        status,
        yearOfGraduation: null
      });
    }
  });
  return result;
}

// 모든 경력 정보를 배열로 수집
function collectCareers() {
  const result = [];
  document.querySelectorAll('.career-entry').forEach(entry => {
    result.push({
      companyName: entry.querySelector('input[name="companyName"]')?.value || '',
      departmentName: entry.querySelector('input[name="departmentName"]')?.value || '',
      hireYm: entry.querySelector('input[name="hireYm"]')?.value || '',
      resignYm: entry.querySelector('input[name="resignYm"]')?.value || '',
      position: entry.querySelector('input[name="position"]')?.value || '',
      jobTitle: entry.querySelector('input[name="jobTitle"]')?.value || '',
      taskRole: entry.querySelector('input[name="taskRole"]')?.value || '',
      workDescription: entry.querySelector('textarea[name="workDescription"]')?.value || '',
      salary: entry.querySelector('input[name="salary"]')?.value || ''
    });
  });
  return result;
}

// 모든 교육 정보를 배열로 수집
function collectEducations() {
  const result = [];
  document.querySelectorAll('.education-entry').forEach(entry => {
    result.push({
      eduName: entry.querySelector('input[name="eduName"]')?.value || '',
      eduInstitution: entry.querySelector('input[name="eduInstitution"]')?.value || '',
      startDate: entry.querySelector('input[name="startDate"]')?.value || '',
      endDate: entry.querySelector('input[name="endDate"]')?.value || '',
      content: entry.querySelector('textarea[name="content"]')?.value || ''
    });
  });
  return result;
}

// 모든 자격증 정보를 배열로 수집
function collectCertificates() {
  const result = [];
  document.querySelectorAll('.certificate-entry').forEach(entry => {
    const certificateId = entry.querySelector('input[name="certificateId"]')?.value;
    if (certificateId && parseInt(certificateId) > 0) {
      result.push(parseInt(certificateId));
    }
  });
  return result;
}

// 모든 포트폴리오 정보를 배열로 수집
function collectPortfolios() {
  const result = [];
  document.querySelectorAll('.portfolio-entry').forEach(entry => {
    const fileName = entry.querySelector('input[name="fileName"]')?.value;
    if (fileName && fileName.trim() !== '') {
      result.push({
        fileName: fileName || '',
        storedFileName: entry.querySelector('input[name="storedFileName"]')?.value || '',
        fileExtension: entry.querySelector('input[name="fileExtension"]')?.value || ''
      });
    }
  });
  return result;
}

// 이력서 데이터 로드 함수 (수정 모드용)
async function loadResumeData(resumeId) {
  try {
    console.log('이력서 데이터 로드 시작:', resumeId);
    
    const response = await fetch(`/api/resume/detail/${resumeId}`, {
      method: 'GET',
      credentials: 'include'
    });
    
    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`이력서 조회 실패: ${errorText}`);
    }
    
    const resumeData = await response.json();
    console.log('로드된 이력서 데이터:', resumeData);
    
    // 폼에 데이터 채우기
    fillFormWithData(resumeData);
    
  } catch (error) {
    console.error('이력서 데이터 로드 실패:', error);
    alert('이력서 데이터를 불러오는 중 오류가 발생했습니다: ' + error.message);
  }
}

// 폼에 데이터 채우는 함수
function fillFormWithData(data) {
  console.log('폼 데이터 채우기 시작');
  
  // 기본 정보 채우기
  if (data.memberName) document.getElementById('name').value = data.memberName;
  if (data.birthDate) {
    // Date를 YYYY-MM-DD 형식으로 변환
    const birthDate = new Date(data.birthDate);
    const formattedDate = birthDate.toISOString().split('T')[0];
    document.getElementById('birthdate').value = formattedDate;
  }
  if (data.phoneNumber) document.getElementById('phoneNumber').value = data.phoneNumber;
  if (data.email) document.getElementById('email').value = data.email;
  if (data.address) {
    // 주소를 상세주소와 기본주소로 분리 (간단한 방법)
    document.getElementById('roadAddress').value = data.address;
  }
  if (data.selfIntroduction) document.getElementById('selfIntroduction').value = data.selfIntroduction;
  
  // 프로필 이미지
  if (data.profile) {
    window.uploadedImageUrl = data.profile;
    const preview = document.getElementById("previewImage");
    preview.src = data.profile;
    preview.style.display = "block";
    
    const photoText = document.querySelector(".photo-text");
    if (photoText) photoText.style.display = "none";
  }
  
  // 직군/직무 선택
  if (data.jobGroupId) {
    document.getElementById('jobGroupSelect').value = data.jobGroupId;
    // 직군 변경 이벤트 트리거하여 직무 목록 로드
    document.getElementById('jobGroupSelect').dispatchEvent(new Event('change'));
    
    // 직무는 잠시 후에 설정 (비동기)
    setTimeout(() => {
      if (data.jobId) {
        document.getElementById('jobSelect').value = data.jobId;
      }
    }, 500);
  }
  
  // 학력 정보 채우기
  if (data.schoolList && data.schoolList.length > 0) {
    fillSchoolData(data.schoolList);
  }
  
  // 경력 정보 채우기
  if (data.careerList && data.careerList.length > 0) {
    fillCareerData(data.careerList);
  }
  
  // 교육 정보 채우기
  if (data.educationList && data.educationList.length > 0) {
    fillEducationData(data.educationList);
  }
  
  // 자격증 정보 채우기
  if (data.certificateList && data.certificateList.length > 0) {
    fillCertificateData(data.certificateList);
  }
  
  // 포트폴리오 정보 채우기
  if (data.portfolioList && data.portfolioList.length > 0) {
    fillPortfolioData(data.portfolioList);
  }
  
  console.log('폼 데이터 채우기 완료');
}

// 학력 데이터 채우기
function fillSchoolData(schoolList) {
  const eduContainer = document.getElementById('edu-dynamic-fields');
  const schoolTypeSelect = document.getElementById('schoolTypeSelect');
  
  // 첫 번째 학력의 구분으로 타입 설정
  if (schoolList.length > 0) {
    const firstSchool = schoolList[0];
    schoolTypeSelect.value = firstSchool.sortation;
    schoolTypeSelect.dispatchEvent(new Event('change'));
    
    // 기존에 생성된 첫 번째 항목에 데이터 채우기
    setTimeout(() => {
      schoolList.forEach((school, index) => {
        if (index === 0) {
          // 첫 번째 항목은 이미 생성됨
          const firstEntry = eduContainer.querySelector('.school-entry');
          fillSchoolEntry(firstEntry, school);
        } else {
          // 추가 항목 생성
          const newEntry = createSchoolEntry(school.sortation);
          eduContainer.appendChild(newEntry);
          attachAutocomplete(newEntry, school.sortation);
          fillSchoolEntry(newEntry, school);
        }
      });
    }, 100);
  }
}

// 개별 학력 항목에 데이터 채우기
function fillSchoolEntry(entry, schoolData) {
  if (schoolData.schoolName) {
    entry.querySelector('input[name="schoolName"]').value = schoolData.schoolName;
  }
  if (schoolData.status) {
    entry.querySelector('select[name="status"]').value = schoolData.status;
  }
  
  if (schoolData.sortation === 'high') {
    if (schoolData.yearOfGraduation) {
      entry.querySelector('input[name="yearOfGraduation"]').value = schoolData.yearOfGraduation;
    }
  } else {
    if (schoolData.majorName) {
      entry.querySelector('input[name="majorName"]').value = schoolData.majorName;
    }
    if (schoolData.startDate) {
      entry.querySelector('input[name="startDate"]').value = schoolData.startDate;
    }
    if (schoolData.endDate) {
      entry.querySelector('input[name="endDate"]').value = schoolData.endDate;
    }
  }
}

// 경력 데이터 채우기
function fillCareerData(careerList) {
  const careerContainer = document.getElementById('career-container');
  
  careerList.forEach(career => {
    const newCareer = createCareerEntry();
    fillCareerEntry(newCareer, career);
    careerContainer.appendChild(newCareer);
  });
}

function fillCareerEntry(entry, careerData) {
  const fields = ['companyName', 'departmentName', 'hireYm', 'resignYm', 'position', 'jobTitle', 'taskRole', 'salary'];
  fields.forEach(field => {
    if (careerData[field]) {
      const input = entry.querySelector(`input[name="${field}"]`);
      if (input) input.value = careerData[field];
    }
  });
  
  if (careerData.workDescription) {
    entry.querySelector('textarea[name="workDescription"]').value = careerData.workDescription;
  }
}

// 교육 데이터 채우기
function fillEducationData(educationList) {
  const educationContainer = document.getElementById('education-container');
  
  educationList.forEach(education => {
    const newEducation = createEducationEntry();
    fillEducationEntry(newEducation, education);
    educationContainer.appendChild(newEducation);
  });
}

function fillEducationEntry(entry, educationData) {
  const fields = ['eduName', 'eduInstitution', 'startDate', 'endDate'];
  fields.forEach(field => {
    if (educationData[field]) {
      const input = entry.querySelector(`input[name="${field}"]`);
      if (input) {
        if (field.includes('Date') && educationData[field]) {
          // Date 객체를 문자열로 변환
          const date = new Date(educationData[field]);
          const year = date.getFullYear();
          const month = String(date.getMonth() + 1).padStart(2, '0');
          input.value = `${year}.${month}`;
        } else {
          input.value = educationData[field];
        }
      }
    }
  });
  
  if (educationData.content) {
    entry.querySelector('textarea[name="content"]').value = educationData.content;
  }
}

// 자격증 데이터 채우기
function fillCertificateData(certificateList) {
  const certificateContainer = document.getElementById('certificate-container');
  
  certificateList.forEach(certificate => {
    const newCertificate = createCertificateEntry();
    newCertificate.querySelector('input[name="certificateId"]').value = certificate.certificateId;
    certificateContainer.appendChild(newCertificate);
  });
}

// 포트폴리오 데이터 채우기
function fillPortfolioData(portfolioList) {
  const portfolioContainer = document.getElementById('portfolio-container');
  
  portfolioList.forEach(portfolio => {
    const newPortfolio = createPortfolioEntry();
    newPortfolio.querySelector('input[name="fileName"]').value = portfolio.fileName || '';
    newPortfolio.querySelector('input[name="storedFileName"]').value = portfolio.storedFileName || '';
    newPortfolio.querySelector('input[name="fileExtension"]').value = portfolio.fileExtension || '';
    portfolioContainer.appendChild(newPortfolio);
  });
}

</script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-3fp9tS8p9A2Mq7Qz+S8jfwD+xdgu9T+O+NRZz8N5eA8=" crossorigin="anonymous"></script>
<script src="/js/resume/resumeView.js"></script>
</body>
</html>