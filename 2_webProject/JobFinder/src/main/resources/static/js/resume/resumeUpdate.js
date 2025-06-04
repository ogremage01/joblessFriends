// 이력서 수정 전용 스크립트

// DOM 로드 완료 후 실행
document.addEventListener("DOMContentLoaded", function() {
	// 스킬 해시태그 UI 초기화
	renderSkillHashtagInput();
	
	// 기존 스킬 데이터 복원
	if (window.existingSkills && window.existingSkills.length > 0) {
		const selectedSkillTags = document.getElementById("selectedSkillTags");
		window.existingSkills.forEach(skill => {
			selectedSkills.add(String(skill.tagId));
			const tagElem = document.createElement("span");
			tagElem.className = "skill-hashtag";
			tagElem.textContent = `#${skill.tagName}`;
			tagElem.dataset.tagId = skill.tagId;
			const xBtn = document.createElement("button");
			xBtn.type = "button";
			xBtn.className = "remove-skill-tag";
			xBtn.textContent = "×";
			xBtn.addEventListener("click", function() {
				selectedSkills.delete(String(skill.tagId));
				tagElem.remove();
			});
			tagElem.appendChild(xBtn);
			selectedSkillTags.appendChild(tagElem);
		});
	}

	// 수정완료 버튼 이벤트 등록
	const finishBtn = document.querySelector('.btn-finish');
	if (finishBtn) {
		finishBtn.addEventListener('click', saveResume);
	}

	// 작성취소 버튼 이벤트 등록
	const cancelBtn = document.querySelector('.btn-cancel');
	if (cancelBtn) {
		cancelBtn.addEventListener('click', function() {
			if (confirm("수정 중인 이력서가 있습니다. 정말로 취소하시겠습니까?")) {
				window.location.href = '/resume/management';
			}
		});
	}

	// +추가 버튼들 이벤트 위임 방식으로 등록
	document.addEventListener("click", function(e) {
		// 학력 추가
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

	// 프로필 이미지 초기화
	if (typeof window.initProfileImage === 'function') {
		window.initProfileImage();
	}

	// 기존 학력 항목들에 이벤트 연결
	if (typeof window.attachSchoolEvents === 'function') {
		const existingSchools = document.querySelectorAll('#school-container .school-entry');
		existingSchools.forEach(function(entry) {
			window.attachSchoolEvents(entry);
		});
	}

	// 기존 경력 항목들에 이벤트 연결
	if (typeof window.attachCareerEvents === 'function') {
		const existingCareers = document.querySelectorAll('#career-container .career-entry');
		existingCareers.forEach(function(entry) {
			window.attachCareerEvents(entry);
		});
	}

	// 기존 교육 항목들에 이벤트 연결
	if (typeof window.attachTrainingEvents === 'function') {
		const existingTrainings = document.querySelectorAll('#training-container .training-entry');
		existingTrainings.forEach(function(entry) {
			window.attachTrainingEvents(entry);
		});
	}

	// 기존 자격증 항목들에 이벤트 연결
	if (typeof window.attachCertificateEvents === 'function') {
		const existingCertificates = document.querySelectorAll('#certificate-container .certificate-entry');
		existingCertificates.forEach(function(entry) {
			window.attachCertificateEvents(entry);
		});
	}

	// 기존 포트폴리오 항목들에 이벤트 연결
	if (typeof window.attachPortfolioEvents === 'function') {
		const existingPortfolios = document.querySelectorAll('#portfolio-container .portfolio-entry');
		existingPortfolios.forEach(function(entry) {
			window.attachPortfolioEvents(entry);
		});
	}

	// 기존 학력 데이터에 자동완성 기능 연결
	document.querySelectorAll('.school-entry').forEach(function(entry) {
		const sortationSelect = entry.querySelector('select[name="sortation"]');
		const fieldsContainer = entry.querySelector('.grid-3, .grid-2');
		
		if (sortationSelect && fieldsContainer) {
			const sortation = sortationSelect.value;
			if (sortation) {
				attachAutocomplete(fieldsContainer.parentElement, sortation);
			}
		}
	});
});

// ==================== 기존 항목들의 이벤트 연결을 위한 헬퍼 함수들 ====================

window.attachSchoolEvents = function(wrapper) {
	// 삭제 버튼 이벤트
	const deleteBtn = wrapper.querySelector('.delete-btn');
	if (deleteBtn) {
		deleteBtn.addEventListener('click', () => wrapper.remove());
	}
	
	// 구분 select 변경 이벤트
	const sortationSelect = wrapper.querySelector('select[name="sortation"]');
	const fieldsContainer = wrapper.querySelector('.school-fields-container');
	
	if (sortationSelect && fieldsContainer) {
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

			attachAutocomplete(wrapper, type);
		});
	}
	
	// 기존 데이터가 있는 경우 자동완성 이벤트 연결
	const hiddenSortation = wrapper.querySelector('input[name="sortation"]');
	const dataType = wrapper.getAttribute('data-type');
	const currentValue = sortationSelect ? sortationSelect.value : (hiddenSortation ? hiddenSortation.value : dataType);
	
	if (currentValue && fieldsContainer) {
		attachAutocomplete(wrapper, currentValue);
	}
};

window.attachCareerEvents = function(wrapper) {
	// 삭제 버튼 이벤트
	const deleteBtn = wrapper.querySelector('.delete-btn');
	if (deleteBtn) {
		deleteBtn.addEventListener('click', () => wrapper.remove());
	}
	
	// 직군/직무 연동 이벤트
	const jobGroupSelect = wrapper.querySelector('select[name="careerJobGroupSelect"]');
	const jobSelect = wrapper.querySelector('select[name="careerJobSelect"]');
	
	if (jobGroupSelect && jobSelect) {
		jobGroupSelect.addEventListener('change', function() {
			const selectedGroupId = this.value;
			updateJobSelectForCareer(jobSelect, selectedGroupId);
		});
	}
};

window.attachTrainingEvents = function(wrapper) {
	// 삭제 버튼 이벤트
	const deleteBtn = wrapper.querySelector('.delete-btn');
	if (deleteBtn) {
		deleteBtn.addEventListener('click', () => wrapper.remove());
	}
};

window.attachCertificateEvents = function(wrapper) {
	// 삭제 버튼 이벤트
	const deleteBtn = wrapper.querySelector('.delete-btn');
	if (deleteBtn) {
		deleteBtn.addEventListener('click', () => wrapper.remove());
	}
};

window.attachPortfolioEvents = function(wrapper) {
	// 삭제 버튼 이벤트
	const deleteBtn = wrapper.querySelector('.delete-btn');
	if (deleteBtn) {
		deleteBtn.addEventListener('click', () => wrapper.remove());
	}
	
	// 파일 업로드 이벤트
	const fileInput = wrapper.querySelector('input[type="file"]');
	const fileNameInput = wrapper.querySelector('input[name="fileName"]');
	const storedFileNameInput = wrapper.querySelector('input[name="storedFileName"]');
	const fileExtensionInput = wrapper.querySelector('input[name="fileExtension"]');
	const label = wrapper.querySelector('label');

	if (fileInput && label) {
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
			}
		});
	}
};

// 경력의 직군/직무 연동을 위한 헬퍼 함수
function updateJobSelectForCareer(jobSelect, jobGroupId) {
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
}