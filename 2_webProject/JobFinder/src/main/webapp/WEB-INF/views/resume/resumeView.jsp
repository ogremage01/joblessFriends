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
			
			<!-- 2행 -->
			<div class="field-block">
				<label>주소</label>
				<input type="text" id="roadAddress" placeholder="주소를 입력해주세요" readonly />
				<button type="button" class="address-search-btn" onclick="openJusoPopup()">🔍</button>
			</div>
			<div class="field-block">
				<label>상세주소</label>
				<input type="text" id="jibunAddress" placeholder="상세주소" readonly />
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
			
			<!-- 삭제버튼 -->
			<div class="add-education-btn">
				<button type="button" class="delete-btn">×</button>
			</div>
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
				<label>담당직군</label>
				<input type="text" placeholder="담당직군를 입력해주세요" />
			</div>
		</div>
		
		<div class="grid-2">
			<div class="field-block">
				<label>담당직무</label>
				<input type="text" placeholder="담당직무를 입력해주세요" />
			</div>
			<div class="field-block">
				<label>연봉 (만원)</label>
				<input type="text" placeholder="예시) 2400" />
			</div>
		</div>
		
		<div class="field-block">
			<label>담당업무</label>
			<textarea rows="5" placeholder="담당하신 업무와 성과에 대해 간단명료하게 적어주세요"></textarea>
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

let ignoreNextInput = false;

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

// 주소자동완성
function openJusoPopup() {
	  const confmKey = "${jusoApiKey}";  // properties에서 가져온 값 그대로 넣어도 됨
	  const returnUrl = "http://localhost:9090/addrCallback.jsp";  // 콜백 JSP의 경로
	  const resultType = "4"; // 도로명+지번+상세

	  const popUrl = "https://business.juso.go.kr/addrlink/addrLinkUrl.do"
	    + "?confmKey=" + encodeURIComponent(confmKey)
	    + "&returnUrl=" + encodeURIComponent(returnUrl)
	    + "&resultType=" + resultType;

	  window.open(popUrl, "_blank", "width=570,height=420,scrollbars=yes,resizable=yes");
}

// 팝업에서 주소 전달받는 함수
function handleJusoCallback(roadAddr, addrDetail) {
  document.getElementById("roadAddress").value = roadAddr || "";
  document.getElementById("jibunAddress").value = addrDetail || "";
}


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

document.addEventListener("DOMContentLoaded", function () {
  // 학력 입력 영역 컨테이너
  const container = document.getElementById("edu-dynamic-fields");

  // '구분' 셀렉트 박스
  const select = document.getElementById("schoolTypeSelect");

  /**
   * [기능 1] 구분 select 변경 시 첫 번째 입력 필드 생성
   */
  select.addEventListener("change", () => {
    container.innerHTML = ""; // 이전 필드 제거
    const type = select.value;
    if (!type) return;
    const entry = createSchoolEntry(type); // 필드 생성
    container.appendChild(entry); // 추가
    attachAutocomplete(entry, type); // 자동완성 연결
  });

  /**
   * [기능 2] + 추가 버튼 클릭 시 새로운 학력 항목 추가
   */
  document.querySelector(".add-education-btn button").addEventListener("click", function () {
    const selectedType = select.value;
    if (!selectedType) {
      alert("구분을 먼저 선택해주세요.");
      return;
    }
    const newEntry = createSchoolEntry(selectedType);
    container.appendChild(newEntry);
    attachAutocomplete(newEntry, selectedType);
  });

  /**
   * [기능 3] 학력 입력 항목 HTML 템플릿 생성 함수
   * @param {string} type - "high" | "univ4" | "univ2"
   */
  function createSchoolEntry(type) {
    const wrapper = document.createElement("div");
    wrapper.className = "edu-row-combined school-entry";

    if (type === "high") {
      // 고등학교용 입력 필드
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
      // 대학교용 입력 필드
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

    // 삭제 버튼 이벤트 바인딩
    wrapper.querySelector(".delete-btn").addEventListener("click", () => wrapper.remove());
    return wrapper;
  }

  /**
   * [기능 4] 자동완성 기능 연결 함수 (학교명/전공명)
   * @param {HTMLElement} wrapper - 입력 항목 루트
   * @param {string} type - "high", "univ4", "univ2"
   */
  function attachAutocomplete(wrapper, type) {
    // 학교명 자동완성
    const schoolInput = wrapper.querySelector('input[name="schoolName"]');
    const schoolList = wrapper.querySelector('.autocomplete-list');
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
            ? `/api/school/search?keyword=${encodeURIComponent(keyword)}`
            : `/api/university/search?keyword=${encodeURIComponent(keyword)}&schoolType=${type}`;

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

    // 전공명 자동완성 (대학교만)
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
            fetch(`/api/major/search?keyword=${encodeURIComponent(keyword)}`)
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



// 이력서 저장 함수
document.querySelector('.btn-finish').addEventListener('click', async function () {

  // 기본 인적사항 등 단일 필드 수집
  const resumeData = {
    name: document.querySelector('#name').value,
    birthdate: document.querySelector('#birthdate').value,
    phoneNumber: document.querySelector('#phoneNumber').value,
    email: document.querySelector('#email').value,
    address: document.querySelector('#address').value,
    selfIntroduction: document.querySelector('#selfIntroduction').value,
    profile: uploadedImageUrl || "", // 프로필 이미지
    jobGroupId: selectedJobGroupId,  // 선택된 직군
    jobId: selectedJobId,            // 선택된 직무
  };

  // 반복 가능한 항목들 수집 (각각 별도 함수로 정의됨)
  resumeData.schools = collectSchools();
  resumeData.educations = collectEducations();
  resumeData.careers = collectCareers();
  resumeData.certificateIds = collectCertificateIds(); // 자격증 (id 배열)
  resumeData.tagIds = collectTagIds();                 // 태그 (id 배열)
  resumeData.portfolios = collectPortfolios();         // 포트폴리오 파일 정보들

  // 서버로 비동기 전송
  const response = await fetch("/api/resume/save", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(resumeData)
  });

  if (response.ok) {
    alert("이력서가 저장되었습니다.");
    location.href = "/resume/management";
  } else {
    alert("저장 중 오류가 발생했습니다.");
  }
});

 // 입력한 정보를 배열로 수집 추가기능 함수
 
 // 모든 학력 정보를 배열로 수집
function collectSchools() {
    const schools = [];
    document.querySelectorAll('.school-entry').forEach(entry => {
        schools.push({
            sortation: entry.querySelector('select[name="sortation"]').value,
            schoolName: entry.querySelector('input[name="schoolName"]').value,
            yearOfGraduation: entry.querySelector('input[name="yearOfGraduation"]').value,
            status: entry.querySelector('select[name="status"]').value
        });
    });
    return schools;
}
	//모든 교육 정보를 배열로 수집
function collectEducations() {
    const educations = [];
    document.querySelectorAll('.education-entry').forEach(entry => {
        educations.push({
            eduInstitution: entry.querySelector('input[name="eduInstitution"]').value,
            eduName: entry.querySelector('input[name="eduName"]').value,
            startDate: entry.querySelector('input[name="startDate"]').value,
            endDate: entry.querySelector('input[name="endDate"]').value
        });
    });
    return educations;
}
	// 모든 경력 정보를 배열로 수집
function collectCareers() {
    const careers = [];
    document.querySelectorAll('.career-entry').forEach(entry => {
        careers.push({
            companyName: entry.querySelector('input[name="companyName"]').value,
            departmentName: entry.querySelector('input[name="departmentName"]').value,
            hireYm: entry.querySelector('input[name="hireYm"]').value,
            resignYm: entry.querySelector('input[name="resignYm"]').value,
            position: entry.querySelector('input[name="position"]').value,
            jobTitle: entry.querySelector('input[name="jobTitle"]').value,
            taskRole: entry.querySelector('input[name="taskRole"]').value,
            workDescription: entry.querySelector('textarea[name="workDescription"]').value,
            salary: entry.querySelector('input[name="salary"]').value
        });
    });
    return careers;
}
	
	//모든 자격증 정보를 배열로 수집
function collectCertificates() {
    return Array.from(document.querySelectorAll('input[name="certificateId"]'))
        .map(el => parseInt(el.value));
}

	//모든 스킬 정보를 배열로 수집
function collectTags() {
    return Array.from(document.querySelectorAll('input[name="tagId"]'))
        .map(el => parseInt(el.value));
}

	//모든 포트폴리오 정보를 배열로 수집
function collectPortfolios() {
    const portfolios = [];
    document.querySelectorAll('.portfolio-entry').forEach(entry => {
        portfolios.push({
            fileName: entry.querySelector('input[name="fileName"]').value,
            storedFileName: entry.querySelector('input[name="storedFileName"]').value,
            fileExtension: entry.querySelector('input[name="fileExtension"]').value
        });
    });
    return portfolios;
}



</script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-3fp9tS8p9A2Mq7Qz+S8jfwD+xdgu9T+O+NRZz8N5eA8=" crossorigin="anonymous"></script>
<script src="/js/resume/resumeView.js"></script>
</body>
</html>