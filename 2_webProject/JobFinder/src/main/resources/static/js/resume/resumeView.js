// 전역 변수들
let addressFullText = '';

// 선택된 스킬들을 추적하는 Set
let selectedSkills = new Set();

// 전역 변수 (JSP에서 설정되지 않았을 경우 기본값 사용)
let ignoreNextInput = false;
if (typeof window.uploadedImageUrl === 'undefined') window.uploadedImageUrl = '';
if (typeof window.isEditMode === 'undefined') window.isEditMode = false;
if (typeof window.currentResumeId === 'undefined') window.currentResumeId = null;

// DOM 로드 완료 후 실행
document.addEventListener("DOMContentLoaded", function() {

	// 직군, 직무 select와 스킬 컨테이너 요소 캐싱
	const jobGroupSelect = document.getElementById("jobGroupSelect");
	const jobSelect = document.getElementById("jobSelect");
	const skillContainer = document.getElementById("skillContainer");
	const selectedJobGroupLabel = document.getElementById("selectedJobGroupLabel");

	// 스킬 해시태그 UI 초기화
	renderSkillHashtagInput();

	// 작성완료 버튼 이벤트 등록
	const finishBtn = document.querySelector('.btn-finish');
	if (finishBtn) {
		finishBtn.addEventListener('click', saveResume);
	}


	const cancelBtn = document.querySelector('.btn-cancel');
	cancelBtn.addEventListener('click', function() {
		confirm("작성 중인 이력서가 있습니다. 정말로 취소하시겠습니까?") ? window.location.href = '/resume/management' : null;
	});


	// +추가 버튼들 이벤트 위임 방식으로 등록
	document.addEventListener("click", function(e) {
		// 학력 추가 - school 관련
		if (e.target.closest('.add-education-btn button')) {
			const schoolContainer = document.getElementById("school-container");
			const newSchool = createSchoolEntry();
			schoolContainer.appendChild(newSchool);
		}
		// 경력 추가
		if (e.target.closest('.add-career-btn button')) {
			const careerContainer = document.getElementById("career-container");
			const newCareer = createCareerEntry();
			careerContainer.appendChild(newCareer);
		}
		// 교육/훈련 추가
		if (e.target.closest('.add-training-btn button')) {
			const trainingContainer = document.getElementById("training-container");
			const newTraining = createTrainingEntry();
			trainingContainer.appendChild(newTraining);
		}
		// 자격증 추가
		if (e.target.closest('.add-license-btn button')) {
			const certificateContainer = document.getElementById("certificate-container");
			const newCertificate = createCertificateEntry();
			certificateContainer.appendChild(newCertificate);
		}
		// 포트폴리오 추가
		if (e.target.closest('.add-portfolio-btn button')) {
			const portfolioContainer = document.getElementById("portfolio-container");
			const newPortfolio = createPortfolioEntry();
			portfolioContainer.appendChild(newPortfolio);
		}
	});

	// 수정 모드에서 기존 삭제 버튼들에 이벤트 추가
	if (window.isEditMode) {
		// 기존 항목들의 삭제 버튼 이벤트 추가
		document.querySelectorAll('.delete-btn').forEach(btn => {
			btn.addEventListener('click', function() {
				const entry = this.closest('.school-entry, .career-entry, .training-entry, .certificate-entry, .portfolio-entry');
				if (entry) {
					entry.remove();
				}
			});
		});
	}

});

// 스킬 해시태그+자동완성 UI 렌더링 함수
function renderSkillHashtagInput() {
	const skillContainer = document.getElementById("skillContainer");
	skillContainer.innerHTML = `
    <div class="skill-hashtag-box">
      <div id="selectedSkillTags" class="selected-skill-tags"></div>
      <input type="text" id="skillInput" placeholder="#스킬 입력" autocomplete="off" />
      <ul id="skillAutocompleteList" class="autocomplete-list" style="display:none;"></ul>
    </div>
  `;

	const skillInput = document.getElementById("skillInput");
	const autocompleteList = document.getElementById("skillAutocompleteList");
	const selectedSkillTags = document.getElementById("selectedSkillTags");

	// 입력 시 자동완성
	let timer;
	skillInput.addEventListener("input", function() {
		const keyword = this.value.trim();
		clearTimeout(timer);
		if (keyword.length < 2) {
			autocompleteList.style.display = "none";
			return;
		}
		timer = setTimeout(() => {
			fetch("/skill/autocomplete?keyword=" + encodeURIComponent(keyword))
				.then(res => res.json())
				.then(data => {
					autocompleteList.innerHTML = "";
					if (data.length > 0) {
						autocompleteList.style.display = "block";
						data.forEach(tag => {
							// 이미 선택된 태그는 표시하지 않음
							if (selectedSkills.has(String(tag.tagId))) return;
							const li = document.createElement("li");
							li.textContent = tag.tagName;
							li.dataset.tagId = tag.tagId;
							li.addEventListener("mousedown", function() {
								addSkillTag(tag.tagId, tag.tagName);
								skillInput.value = "";
								autocompleteList.style.display = "none";
							});
							autocompleteList.appendChild(li);
						});
					} else {
						autocompleteList.style.display = "none";
					}
				});
		}, 150);
	});

	// 엔터/쉼표 입력 시 자동완성 첫번째 선택
	skillInput.addEventListener("keydown", function(e) {
		if ((e.key === "Enter" || e.key === ",") && autocompleteList.style.display === "block") {
			const first = autocompleteList.querySelector("li");
			if (first) {
				first.dispatchEvent(new Event("mousedown"));
				e.preventDefault();
			}
		}
	});

	// 태그 추가 함수
	function addSkillTag(tagId, tagName) {
		if (selectedSkills.has(String(tagId))) return;
		selectedSkills.add(String(tagId));
		const tagElem = document.createElement("span");
		tagElem.className = "skill-hashtag";
		tagElem.textContent = `#${tagName}`;
		tagElem.dataset.tagId = tagId;
		// X버튼
		const xBtn = document.createElement("button");
		xBtn.type = "button";
		xBtn.className = "remove-skill-tag";
		xBtn.textContent = "×";
		xBtn.addEventListener("click", function() {
			selectedSkills.delete(String(tagId));
			tagElem.remove();
		});
		tagElem.appendChild(xBtn);
		selectedSkillTags.appendChild(tagElem);
	}
}

// 학력 엔트리 생성 함수 (이름 변경: createEducationEntry -> createSchoolEntry)
function createSchoolEntry() {
	const wrapper = document.createElement("div");
	wrapper.className = "school-entry";
	wrapper.innerHTML = `
    <button type="button" class="delete-btn">×</button>
    
    <div class="field-block">
      <label>구분</label>
      <select name="sortation" class="school-type-select">
        <option value="">선택</option>
        <option value="high">고등학교</option>
        <option value="univ4">대학교(4년)</option>
        <option value="univ2">대학교(2,3년)</option>
		<option value="master">석사</option>
		<option value="doctor">박사</option>
      </select>
    </div>
    
    <div class="school-fields-container" style="display: none;">
      <!-- 선택한 구분에 따라 동적으로 필드가 추가됨 -->
    </div>
  `;

	const sortationSelect = wrapper.querySelector('select[name="sortation"]');
	const fieldsContainer = wrapper.querySelector('.school-fields-container');

	// 구분 선택 시 필드 생성
	sortationSelect.addEventListener('change', function() {
		const type = this.value;
		fieldsContainer.style.display = type ? 'block' : 'none';

		if (!type) {
			fieldsContainer.innerHTML = '';
			return;
		}

		if (type === 'high') {
			fieldsContainer.innerHTML = `
        <div class="grid-3">
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
              <option value="졸업예정">졸업예정</option>
              <option value="졸업">졸업</option>
              <option value="재학중">재학중</option>
            </select>
          </div>
        </div>
      `;
		} else {
			fieldsContainer.innerHTML = `
        <div class="grid-2">
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
        </div>
        <div class="grid-3">
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
              <option value="졸업예정">졸업예정</option>
              <option value="졸업">졸업</option>
              <option value="재학중">재학중</option>
            </select>
          </div>
        </div>
      `;
		}

		// 자동완성 기능 연결
		attachAutocomplete(fieldsContainer, type);
	});

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
        <label>담당직군</label>
        <select name="careerJobGroupSelect">
          <option value="">직군 선택</option>
        </select>
      </div>
	  <div class="field-block">
	    <label>담당직무</label>
	    <select name="careerJobSelect">
	      <option value="">직무 선택</option>
	    </select>
	  </div>
    </div>
    
    <div class="grid-2">
	<div class="field-block">
	  <label>직급/직책</label>
	  <input type="text" name="position" placeholder="직급/직책을 입력해주세요" />
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

	// 삭제 버튼 기능
	wrapper.querySelector(".delete-btn").addEventListener("click", () => wrapper.remove());

	// 담당직군/직무 셀렉트 박스 동적 로딩
	const jobGroupSelect = wrapper.querySelector('select[name="careerJobGroupSelect"]');
	const jobSelect = wrapper.querySelector('select[name="careerJobSelect"]');

	// 직군 목록 불러오기
	fetch("/jobGroup/list")
		.then(res => res.json())
		.then(data => {
			data.forEach(group => {
				const option = document.createElement("option");
				option.value = group.jobGroupId;
				option.textContent = group.jobGroupName;
				jobGroupSelect.appendChild(option);
			});
		});

	// 직군 선택 시 직무 목록 갱신
	jobGroupSelect.addEventListener("change", function() {
		const jobGroupId = this.value;
		jobSelect.innerHTML = '<option value="">직무 선택</option>';
		if (!jobGroupId) return;
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
	});

	return wrapper;
}

// 교육/훈련 엔트리 생성 함수 (기존 이름 변경)
function createTrainingEntry() {
	const wrapper = document.createElement("div");
	wrapper.className = "training-entry";
	wrapper.innerHTML = `
    <button type="button" class="delete-btn">×</button>
  
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
    <button type="button" class="delete-btn">×</button>
  
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
  `;

	wrapper.querySelector(".delete-btn").addEventListener("click", () => wrapper.remove());
	return wrapper;
}

// 포트폴리오 엔트리 생성 함수
function createPortfolioEntry() {
	const wrapper = document.createElement("div");
	wrapper.className = "portfolio-entry";
	wrapper.innerHTML = `
    <button type="button" class="delete-btn">×</button>
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
	const label = wrapper.querySelector('label');

	fileInput.addEventListener('change', function(e) {
		const file = e.target.files[0];
		if (file) {
			fileNameInput.value = file.name;
			storedFileNameInput.value = 'stored_' + Date.now() + '_' + file.name;
			fileExtensionInput.value = file.name.split('.').pop().toLowerCase();

			// 라벨 텍스트 업데이트
			label.innerHTML = `
        <span class="plus-icon">✓</span>
        파일: ${file.name}
      `;

			console.log('파일 업로드:', file.name);
		}
	});

	wrapper.querySelector(".delete-btn").addEventListener("click", () => {
		// 포트폴리오 파일 삭제 처리
		const storedFileName = wrapper.querySelector('input[name="storedFileName"]').value;
		if (storedFileName && window.currentResumeId) {
			// 서버에서 파일 삭제 요청 (선택적)
			console.log('포트폴리오 파일 삭제:', storedFileName);
		}
		wrapper.remove();
	});

	return wrapper;
}

// 자동완성 이벤트 연결 (학교명/전공명)
function attachAutocomplete(wrapper, type) {
	// 학교명
	const schoolInput = wrapper.querySelector('input[name="schoolName"]');
	const schoolList = wrapper.querySelector('ul.autocomplete-list');

	if (schoolInput && schoolList) {
		let timer;
		schoolInput.addEventListener("input", function() {
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
			majorInput.addEventListener("input", function() {
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

// 프로필 이미지 업로드 및 미리보기 기능
window.initProfileImage = function() {
	// 사진 박스를 클릭하면 숨겨진 input[type="file"] 요소를 클릭해서 파일 선택창을 열어줌
	const photoBox = document.getElementById("photoBox");
	const profileImageInput = document.getElementById("profileImageInput");

	if (photoBox && profileImageInput) {
		photoBox.addEventListener("click", function() {
			profileImageInput.click();
		});

		// 사용자가 프로필 이미지를 선택했을 때
		profileImageInput.addEventListener("change", function() {
			const file = this.files[0];
			if (!file) return;

			// FileReader를 사용해서 사용자가 선택한 이미지를 미리보기로 보여줌
			const reader = new FileReader();
			reader.onload = function(e) {
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

			fetch('/resume/profile-temp/uploadImage', {
				method: 'POST',
				body: formData
			})
				.then(res => res.text()) // 서버 응답 텍스트 출력
				.then(data => {
					window.uploadedImageUrl = data;  // 여기서 저장
					console.log("업로드 결과:", data);
				})
				.catch(err => {
					console.error("업로드 실패", err);
				});
		});
	}
};

// 이력서 저장 함수
async function saveResume() {
	console.log("이력서 저장 함수 실행");
	// 입력 필드 유효성 검사
	if (validateResume() === false) {
		return;
	}
	try {
		// 이력서 데이터 수집
		const resumeData = collectResumeData();

		// 수정 모드일 때 resumeId 추가
		if (window.isEditMode && window.currentResumeId) {
			resumeData.resumeId = parseInt(window.currentResumeId);
		}

		console.log("수집된 이력서 데이터:", resumeData);

		// 서버로 전송 (신규/수정 모두 save API 사용)
		const response = await fetch('/resume/save', {
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
			const message = window.isEditMode ? "이력서가 성공적으로 수정되었습니다!" : "이력서가 성공적으로 저장되었습니다!";
			alert(message);

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

// 전체 이력서 데이터 수집 및 전송 함수
function collectResumeData() {
	const skills = Array.from(selectedSkills);

	return {
		resumeId: window.currentResumeId || 0,
		title: document.getElementById("title")?.value || '',
		name: document.getElementById("name")?.value || '',
		birthdate: document.getElementById("birthdate")?.value || '',
		phoneNumber: document.getElementById("phoneNumber")?.value || '',
		email: document.getElementById("email")?.value || '',
		address: document.getElementById("roadAddress")?.value || '',
		postalCodeId: parseInt(document.getElementById("postalCodeId")?.value) || 0,
		selfIntroduction: document.getElementById("selfIntroduction")?.value || '',
		profile: window.uploadedImageUrl || '',

		// 스킬 ID 배열
		tagIds: skills,

		// 각 섹션별 데이터 수집
		schools: collectSchools(),
		careers: collectCareers(),
		educations: collectEducations(),
		certificates: collectCertificates(),  // certificateIds에서 certificates로 변경
		portfolios: collectPortfolios()
	};
}

// 입력한 정보를 배열로 수집하는 함수들 (ResumeParser와 호환)

// 모든 학력 정보를 배열로 수집
function collectSchools() {
	const result = [];
	document.querySelectorAll('.school-entry').forEach(entry => {
		const sortation = entry.querySelector('select[name="sortation"]')?.value;
		const schoolName = entry.querySelector('input[name="schoolName"]')?.value;
		const status = entry.querySelector('select[name="status"]')?.value;

		if (!sortation || !schoolName) return; // 필수 필드가 없으면 건너뛰기

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
			jobGroupId: parseInt(entry.querySelector('select[name="careerJobGroupSelect"]')?.value) || 0,
			jobId: parseInt(entry.querySelector('select[name="careerJobSelect"]')?.value) || 0,
			workDescription: entry.querySelector('textarea[name="workDescription"]')?.value || '',
			salary: entry.querySelector('input[name="salary"]')?.value || ''
		});
	});
	return result;
}

// 모든 교육/훈련 정보를 배열로 수집
function collectEducations() {
	const result = [];
	document.querySelectorAll('.training-entry').forEach(entry => {
		const eduName = entry.querySelector('input[name="eduName"]')?.value;
		if (eduName && eduName.trim() !== '') { // 교육명이 있는 경우만 수집
			result.push({
				eduName: eduName || '',
				eduInstitution: entry.querySelector('input[name="eduInstitution"]')?.value || '',
				startDate: entry.querySelector('input[name="startDate"]')?.value || '',
				endDate: entry.querySelector('input[name="endDate"]')?.value || '',
				content: entry.querySelector('textarea[name="content"]')?.value || ''
			});
		}
	});
	return result;
}

// 모든 자격증 정보를 배열로 수집
function collectCertificates() {
	const result = [];
	document.querySelectorAll('.certificate-entry').forEach(entry => {
		result.push({
			certificateName: entry.querySelector('input[name="certificateName"]')?.value || '',
			issuingAuthority: entry.querySelector('input[name="issuingAuthority"]')?.value || '',
			acquisitionDate: entry.querySelector('input[name="acquisitionDate"]')?.value || ''
		});
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

// 이력서 미리보기 팝업창 열기
$(".btn-preview").click(async function() {
	const resumeData = collectResumeData();

	//window.open('문서 주소', '윈도우 이름', '옵션=값, 옵션=값, 옵션=값, …');
	try {
		const response = await fetch("/resume/preview", {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(resumeData)
		});

		var url = "/resume/viewPreview";
		var windowName = "resumePreview"

		const result = await response.text();
		if (result === "success") {
			// 팝업 가로 사이즈
			const popupWidth = 980;
			// 팝업 세로사이즈 : 스크린 높이 사이즈 가져옴
			const screenHeight = window.screen.availHeight;

			// 위치 가로 중앙 값 계산
			const screenLeft = window.screenLeft !== undefined ? window.screenLeft : screen.left;
			const screenWidth = window.innerWidth || document.documentElement.clientWidth || screen.width;
			const left = screenLeft + (screenWidth - popupWidth) / 2;


			var popupOption = `width=${popupWidth}, height=${screenHeight},left=${left}`;

			// 팝업 열기	
			window.open(url, windowName, popupOption);
		} else {
			alert("미리보기 실패: 서버 오류");
		}
	} catch (err) {
		console.error("미리보기 오류:", err);
		alert("미리보기 중 오류 발생: " + err.message);
	}

	/*
	// 자식 창에 데이터 보내기
	preview.onload = function() {
		var data = {
			name: $("#name").val(),
			age: $("#age").val()
		};
		
		//postMessage()는 한 창에서 다른 창으로 데이터를 보낼 때 사용하는 메서드
		preview.postMessage(data, url);
	};
	*/

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
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}
				// 조합된 참고항목을 해당 필드에 넣는다.
				addr += extraAddr;
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			const postalCodeField = document.getElementById('postalCodeId') || document.getElementById('postalCode');
			if (postalCodeField) {
				postalCodeField.value = data.zonecode;
			}
			document.getElementById("roadAddress").value = addr;
			// 커서를 다음 필드로 이동 (상세주소 필드가 있다면)
			const detailAddressField = document.getElementById("detailAddress");
			if (detailAddressField) {
				detailAddressField.focus();
			}
		}
	}).open();
}

//이력서 밸리데이션 함수

function validateResume() {
    const fields = ["title", "name", "birthdate", "phoneNumber", "email", "roadAddress", "postalCodeId"];
    
    // 모든 기존 에러 메시지 제거
    document.querySelectorAll(".error-msg").forEach(e => e.remove());
    
    fields.forEach(id => {
        const field = document.getElementById(id);
        field.style.borderColor = "";
    });

    const photoBox = document.getElementById("photoBox");
    photoBox.style.borderColor = "";

    let result = true;
    let errorMessages = [];

    function showError(field, message) {
        field.style.borderColor = "red";
        const error = document.createElement("span");
        error.className = "error-msg";
        error.style.color = "red";
        error.style.fontSize = "12px";
        error.textContent = " " + message;
        field.after(error);
        errorMessages.push(message);
    }

    const title = document.getElementById("title").value.trim();
    const name = document.getElementById("name").value.trim();
    const birthdate = document.getElementById("birthdate").value.trim();
    const phoneNumber = document.getElementById("phoneNumber").value.trim();
    const email = document.getElementById("email").value.trim();
    const roadAddress = document.getElementById("roadAddress").value.trim();
    const postalCodeId = document.getElementById("postalCodeId").value.trim();
    const profileImageInput = document.getElementById("profileImageInput");

    if (!title) {
        showError(document.getElementById("title"), "제목은 필수 입력 항목입니다.");
        result = false;
    }
    if (!name) {
        showError(document.getElementById("name"), "이력서 작성 시 이름은 필수 입력 항목입니다.");
        result = false;
    }
    if (!birthdate || !/^\d{4}-\d{2}-\d{2}$/.test(birthdate)) {
        showError(document.getElementById("birthdate"), "생년월일은 YYYY-MM-DD 형식으로 입력해주세요.");
        result = false;
    }
    if (!phoneNumber || !/^\d{3}-\d{3,4}-\d{4}$/.test(phoneNumber)) {
        showError(document.getElementById("phoneNumber"), "전화번호는 XXXXXXXXXXX 형식으로 입력해주세요.");
        result = false;
    }
    if (!email || !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
        showError(document.getElementById("email"), "이메일 형식이 올바르지 않습니다.");
        result = false;
    }
    if (!roadAddress || roadAddress.length < 5) {
        showError(document.getElementById("roadAddress"), "도로명 주소는 5자 이상 입력해주세요.");
        result = false;
    }
    if (!postalCodeId || isNaN(postalCodeId) || postalCodeId <= 0) {
        showError(document.getElementById("postalCodeId"), "우편번호는 올바른 숫자여야 합니다.");
        result = false;
    }
    if (!profileImageInput.files.length) {
        photoBox.style.borderColor = "red";
        const error = document.createElement("span");
        error.className = "error-msg";
        error.style.color = "red";
        error.style.fontSize = "12px";
        error.textContent = " 프로필 이미지는 필수 입력 항목입니다.";
        photoBox.after(error);
        errorMessages.push("프로필 이미지는 필수 입력 항목입니다.");
        result = false;
    }

    // 밸리데이션 실패 시 SweetAlert 모달 표시
    if (!result) {
        Swal.fire({
            icon: 'error',
            title: '입력 정보를 확인해주세요',
            html: errorMessages.map(msg => `• ${msg}`).join('<br>'),
            confirmButtonText: '확인',
            confirmButtonColor: '#F69800'
        });
    }

    return result;
}
