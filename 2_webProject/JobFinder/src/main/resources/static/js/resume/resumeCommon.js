// 이력서 공통 기능 모듈
// 전역 변수들
let addressFullText = '';
let selectedSkills = new Set();
let ignoreNextInput = false;

// 전역 변수 기본값 설정
if (typeof window.uploadedImageUrl === 'undefined') window.uploadedImageUrl = '';
if (typeof window.isEditMode === 'undefined') window.isEditMode = false;
if (typeof window.currentResumeId === 'undefined') window.currentResumeId = null;

// ==================== 유틸리티 함수 ====================

// 다음 주소 API 함수
function execDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			var addr = '';
			var extraAddr = '';

			if (data.userSelectedType === 'R') {
				addr = data.roadAddress;
			} else {
				addr = data.jibunAddress;
			}

			if (data.userSelectedType === 'R') {
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}
				addr += extraAddr;
			}

			const postalCodeField = document.getElementById('postalCodeId') || document.getElementById('postalCode');
			if (postalCodeField) {
				postalCodeField.value = data.zonecode;
			}
			document.getElementById("roadAddress").value = addr;
			const detailAddressField = document.getElementById("detailAddress");
			if (detailAddressField) {
				detailAddressField.focus();
			}
		}
	}).open();
}

// ==================== 프로필 이미지 관련 ====================

// 프로필 이미지 업로드 및 미리보기 기능
window.initProfileImage = function() {
	const photoBox = document.getElementById("photoBox");
	const profileImageInput = document.getElementById("profileImageInput");

	if (photoBox && profileImageInput) {
		photoBox.addEventListener("click", function(e) {
			// 새 이미지 등록 확인 (이미지가 있든 없든 한 번만 확인)
			if (!confirm("새로운 이미지를 등록하시겠습니까?")) {
				return;
			}

			profileImageInput.click();
		});

		profileImageInput.addEventListener("change", function() {
			const file = this.files[0];
			if (!file) return;

			const reader = new FileReader();
			reader.onload = function(e) {
				const preview = document.getElementById("previewImage");
				preview.src = e.target.result;
				preview.style.display = "block";

				const photoText = document.querySelector(".photo-text");
				if (photoText) photoText.style.display = "none";
			};
			reader.readAsDataURL(file);

			// 기존 이미지가 있으면 먼저 삭제
			const deleteOldImage = window.uploadedImageUrl ?
				fetch(`/profile-temp/deleteImage/${window.uploadedImageUrl.split('/').pop()}`, {
					method: 'DELETE'
				}).catch(err => console.log("기존 이미지 삭제 실패:", err)) :
				Promise.resolve();

			// 기존 이미지 삭제 후 새 이미지 업로드
			deleteOldImage.then(() => {
				const formData = new FormData();
				formData.append('profileImage', file);

				return fetch('/resume/profile-temp/uploadImage', {
					method: 'POST',
					body: formData
				});
			})
				.then(res => res.json())
				.then(data => {
					if (data.success) {
						window.uploadedImageUrl = data.imageUrl;
						console.log("업로드 성공:", data.imageUrl);
					} else {
						console.error("업로드 실패:", data.error);
						alert("이미지 업로드에 실패했습니다.");
					}
				})
				.catch(err => {
					console.error("업로드 실패", err);
					alert("이미지 업로드 중 오류가 발생했습니다.");
				});
		});
	}
};

// 프로필 이미지 삭제 기능 제거됨

// ==================== 스킬 관련 ====================

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

	skillInput.addEventListener("keydown", function(e) {
		if ((e.key === "Enter" || e.key === ",") && autocompleteList.style.display === "block") {
			const first = autocompleteList.querySelector("li");
			if (first) {
				first.dispatchEvent(new Event("mousedown"));
				e.preventDefault();
			}
		}
	});

	function addSkillTag(tagId, tagName) {
		if (selectedSkills.has(String(tagId))) return;
		selectedSkills.add(String(tagId));
		const tagElem = document.createElement("span");
		tagElem.className = "skill-hashtag";
		tagElem.textContent = `#${tagName}`;
		tagElem.dataset.tagId = tagId;
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

// ==================== 엔트리 생성 함수들 ====================

// 학력 엔트리 생성 함수
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
						<input type="text" name="schoolName" placeholder="학교명을 입력해주세요" autocomplete="off"  />
						<ul class="autocomplete-list" style="display: none;"></ul>
					</div>
					<div class="field-block">
						<label>졸업년도</label>
						<input type="text" name="yearOfGraduation" placeholder="예시) 2025.02"  />
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
						<input type="text" name="schoolName" placeholder="대학교명을 입력해주세요" autocomplete="off"  />
						<ul class="autocomplete-list" style="display: none;"></ul>
					</div>
					<div class="field-block">
						<label>전공명</label>
						<input type="text" name="majorName" placeholder="전공명을 입력해주세요" autocomplete="off"  />
						<ul class="autocomplete-list" style="display: none;"></ul>
					</div>
				</div>
				<div class="grid-3">
					<div class="field-block">
						<label>입학년월</label>
						<input type="text" name="startDate" placeholder="예시) 2020.03"  />
					</div>
					<div class="field-block">
						<label>졸업년월</label>
						<input type="text" name="endDate" placeholder="예시) 2024.02"  />
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

		attachAutocomplete(fieldsContainer, type);
	});

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
				<input type="text" name="companyName" placeholder="회사명을 입력해주세요"  />
			</div>
			<div class="field-block">
				<label>부서명</label>
				<input type="text" name="departmentName" placeholder="부서명을 입력해주세요"  />
			</div>
			<div class="field-block">
				<label>입사년월</label>
				<input type="text" name="hireYm" placeholder="예시) 2025.04"  />
			</div>
		</div>
		
		<div class="grid-3">
			<div class="field-block">
				<label>퇴사년월</label>
				<input type="text" name="resignYm" placeholder="예시) 2025.04"  />
			</div>
			<div class="field-block">
				<label>담당직군</label>
				<select name="careerJobGroupSelect"   >
					<option value="">직군 선택</option>
				</select>
			</div>
			<div class="field-block">
				<label>담당직무</label>
				<select name="careerJobSelect" >
					<option value="">직무 선택</option>
				</select>
			</div>
		</div>
		
		<div class="grid-2">
			<div class="field-block">
				<label>직급/직책</label>
				<input type="text" name="position" placeholder="직급/직책을 입력해주세요"  />
			</div>
			<div class="field-block">
				<label>연봉 (만원)</label>
				<input type="text" name="salary" placeholder="예시) 2400"  />
			</div>
		</div>
		
		<div class="field-block">
			<label>담당업무</label>
			<textarea rows="5" name="workDescription" placeholder="담당하신 업무와 성과에 대해 간단명료하게 적어주세요"></textarea>
		</div>
	`;

	wrapper.querySelector(".delete-btn").addEventListener("click", () => wrapper.remove());

	const jobGroupSelect = wrapper.querySelector('select[name="careerJobGroupSelect"]');
	const jobSelect = wrapper.querySelector('select[name="careerJobSelect"]');

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

// 교육/훈련 엔트리 생성 함수
function createTrainingEntry() {
	const wrapper = document.createElement("div");
	wrapper.className = "training-entry";
	wrapper.innerHTML = `
		<button type="button" class="delete-btn">×</button>
	
		<div class="grid-4">
			<div class="field-block">
				<label>교육명</label>
				<input type="text" name="eduName" placeholder="교육명을 입력해주세요"   />
			</div>
			<div class="field-block">
				<label>교육기관</label>
				<input type="text" name="eduInstitution" placeholder="교육기관을 입력해주세요"  />
			</div>
			<div class="field-block">
				<label>시작년월</label>
				<input type="text" name="startDate" placeholder="예시) 2025.04"  />
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
				<input type="text" name="certificateName" placeholder="자격증명을 입력해주세요"  />
			</div>
			<div class="field-block">
				<label>발행처</label>
				<input type="text" name="issuingAuthority" placeholder="발행처를 입력해주세요"  />
			</div>
			<div class="field-block">
				<label>취득날짜</label>
				<input type="text" name="acquisitionDate" placeholder="예시) 2025.04"  />
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
			// 파일 크기 체크 (20MB)
			if (file.size > 20 * 1024 * 1024) {
				alert('파일 크기는 20MB를 초과할 수 없습니다.');
				this.value = '';
				return;
			}

			// 서버로 파일 업로드
			const formData = new FormData();
			formData.append('file', file);

			fetch('/resume/uploadFile', {
				method: 'POST',
				body: formData
			})
				.then(res => res.json())
				.then(data => {
					if (data.success) {
						fileNameInput.value = data.fileName;
						storedFileNameInput.value = data.storedFileName;
						fileExtensionInput.value = data.fileExtension;

						label.innerHTML = `
						<span class="plus-icon">✓</span>
						파일: ${data.fileName}
					`;
						console.log("포트폴리오 파일 업로드 성공:", data.fileName);
					} else {
						alert('파일 업로드에 실패했습니다.');
						this.value = '';
					}
				})
				.catch(err => {
					console.error('파일 업로드 실패:', err);
					alert('파일 업로드 중 오류가 발생했습니다.');
					this.value = '';
				});
		}
	});

	wrapper.querySelector(".delete-btn").addEventListener("click", () => {
		const storedFileName = wrapper.querySelector('input[name="storedFileName"]').value;
		if (storedFileName && window.currentResumeId) {
			console.log('포트폴리오 파일 삭제:', storedFileName);
		}
		wrapper.remove();
	});

	return wrapper;
}

// ==================== 자동완성 관련 ====================

// 자동완성 이벤트 연결 (학교명/전공명)
function attachAutocomplete(wrapper, type) {
	const schoolInput = wrapper.querySelector('input[name="schoolName"]');
	// 학교명 input이 있는 field-block 내에서 ul.autocomplete-list 찾기
	const schoolFieldBlock = schoolInput?.closest('.field-block');
	const schoolList = schoolFieldBlock?.querySelector('ul.autocomplete-list');

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

	if (type !== "high") {
		const majorInput = wrapper.querySelector('input[name="majorName"]');
		// 전공명 input이 있는 field-block 내에서 ul.autocomplete-list 찾기
		const majorFieldBlock = majorInput?.closest('.field-block');
		const majorList = majorFieldBlock?.querySelector('ul.autocomplete-list');

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

// ==================== 데이터 수집 함수들 ====================

// 전체 이력서 데이터 수집 함수
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

		tagIds: skills,

		schools: collectSchools(),
		careers: collectCareers(),
		educations: collectEducations(),
		certificates: collectCertificates(),
		portfolios: collectPortfolios()
	};
}

// 모든 학력 정보를 배열로 수집
function collectSchools() {
	const result = [];
	document.querySelectorAll('.school-entry').forEach(entry => {
		const sortation = entry.querySelector('select[name="sortation"]')?.value;
		const schoolName = entry.querySelector('input[name="schoolName"]')?.value;
		const status = entry.querySelector('select[name="status"]')?.value;

		if (!sortation || !schoolName) return;

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
		if (eduName && eduName.trim() !== '') {
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

// ==================== 검증 함수 ====================

// 검증 유틸리티 함수들
let validationResult = true;
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
	validationResult = false;
}

// 제목 전용 에러 표시 함수
function showTitleError(field, message) {
	field.style.borderColor = "red";
	const errorContainer = document.getElementById("title-error-container");
	if (errorContainer) {
		errorContainer.innerHTML = `<span class="error-msg">${message}</span>`;
	}
	errorMessages.push(message);
	validationResult = false;
}

// 제목 에러 클리어 함수
function clearTitleError() {
	const titleField = document.getElementById("title");
	const errorContainer = document.getElementById("title-error-container");
	if (titleField) {
		titleField.style.borderColor = "";
	}
	if (errorContainer) {
		errorContainer.innerHTML = "";
	}
}

function showSuccess(field, message) {
	field.style.borderColor = "green";
	const success = document.createElement("span");
	success.className = "success-msg";
	success.style.color = "green";
	success.style.fontSize = "12px";
	success.textContent = " " + message;
	field.after(success);
}

// 입력 필드 검증 헬퍼 함수
function validateInputFields(entry, fieldName, errorMessage, validationFn = null) {
	const input = entry.querySelector(`input[name="${fieldName}"]`);
	if (input) {
		let value = input.value.trim();
		let isValid = validationFn ? validationFn(value) : value !== "";
		
		if (!isValid) {
			showError(input, errorMessage);
		} else {
			showSuccess(input, "올바른 형식입니다.");
		}
	}
}

// 셀렉트 필드 검증 헬퍼 함수
function validateSelectFields(entry, fieldName, errorMessage) {
	const select = entry.querySelector(`select[name="${fieldName}"]`);
	if (select) {
		let value = select.value;
		
		if (!value || value === "") {
			showError(select, errorMessage);
		} else {
			showSuccess(select, "올바른 형식입니다.");
		}
	}
}

// 텍스트에어리어 검증 헬퍼 함수
function validateTextareaFields(entry, fieldName, errorMessage) {
	const textarea = entry.querySelector(`textarea[name="${fieldName}"]`);
	if (textarea) {
		let value = textarea.value.trim();
		
		if (value === "") {
			showError(textarea, errorMessage);
		} else {
			showSuccess(textarea, "올바른 형식입니다.");
		}
	}
}

// 기본 필드 검증 함수
function validateBasicFields() {
	const basicFieldValidations = [
		{ id: "title", message: "제목은 필수 입력 항목입니다.", validator: (val) => val !== "" },
		{ id: "name", message: "이력서 작성 시 이름은 필수 입력 항목입니다.", validator: (val) => val !== "" },
		{ id: "birthdate", message: "생년월일은 YYYY-MM-DD 형식으로 입력해주세요.", validator: (val) => /^\d{4}-\d{2}-\d{2}$/.test(val) },
		{ id: "phoneNumber", message: "전화번호는 XXX-XXXX-XXXX 형식으로 입력해주세요.", validator: (val) => /^\d{2,3}-\d{3,4}-\d{4}$/.test(val) },
		{ id: "email", message: "이메일 형식이 올바르지 않습니다.", validator: (val) => /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(val) },
		{ id: "roadAddress", message: "도로명 주소는 4자 이상 입력해주세요.", validator: (val) => val.length >= 4 },
		{ id: "postalCodeId", message: "우편번호는 올바른 숫자여야 합니다.", validator: (val) => !isNaN(val) && val > 0 }
	];

	basicFieldValidations.forEach(validation => {
		const field = document.getElementById(validation.id);
		const value = field.value.trim();
		
		if (!validation.validator(value)) {
			if (validation.id === "title") {
				// 제목 필드는 전용 컨테이너에 에러 메시지 표시
				showTitleError(field, validation.message);
			} else {
				showError(field, validation.message);
			}
		} else {
			if (validation.id === "title") {
				// 제목 필드는 전용 컨테이너 클리어
				clearTitleError();
			} else {
				showSuccess(field, "올바른 형식입니다.");
			}
		}
	});

	// 프로필 이미지 검증 - 신규 작성 시에만 필수
	const profileImageInput = document.getElementById("profileImageInput");
	const photoBox = document.getElementById("photoBox");
	if (!window.isEditMode && !profileImageInput.files.length && !window.uploadedImageUrl) {
		photoBox.style.borderColor = "red";
		const error = document.createElement("span");
		error.className = "error-msg";
		error.style.color = "red";
		error.style.fontSize = "12px";
		error.textContent = " 프로필 이미지는 필수 입력 항목입니다.";
		photoBox.insertAdjacentElement("afterend", error);
		errorMessages.push("프로필 이미지는 필수 입력 항목입니다.");
		validationResult = false;
	}
}

// 교육/훈련 검증 함수
function validateTrainingEntries() {
	document.querySelectorAll('.training-entry').forEach(entry => {
		validateInputFields(entry, "eduName", "교육명은 필수 입력 항목입니다.");
		validateInputFields(entry, "eduInstitution", "교육기관은 필수 입력 항목입니다.");
		validateInputFields(entry, "startDate", "시작년월은 YYYY.MM 형식으로 입력해주세요.", (val) => /^\d{4}\.\d{2}$/.test(val));
		validateInputFields(entry, "endDate", "종료년월은 YYYY.MM 형식으로 입력해주세요.", (val) => /^\d{4}\.\d{2}$/.test(val));
	});
}

// 자격증 검증 함수
function validateCertificateEntries() {
	document.querySelectorAll('.certificate-entry').forEach(entry => {
		validateInputFields(entry, "certificateName", "자격증명은 필수 입력 항목입니다.");
		validateInputFields(entry, "issuingAuthority", "발행처는 필수 입력 항목입니다.");
		validateInputFields(entry, "acquisitionDate", "취득년월은 YYYY.MM 형식으로 입력해주세요.", (val) => /^\d{4}\.\d{2}$/.test(val));
	});
}

// 경력 검증 함수
function validateCareerEntries() {
	document.querySelectorAll('.career-entry').forEach(entry => {
		validateInputFields(entry, "companyName", "회사명은 필수 입력 항목입니다.");
		validateInputFields(entry, "departmentName", "부서명은 필수 입력 항목입니다.");
		validateInputFields(entry, "hireYm", "입사년월은 YYYY.MM 형식으로 입력해주세요.", (val) => /^\d{4}\.\d{2}$/.test(val));
		validateInputFields(entry, "resignYm", "퇴사년월은 YYYY.MM 형식으로 입력해주세요.", (val) => /^\d{4}\.\d{2}$/.test(val));
		validateInputFields(entry, "position", "직급/직책은 필수 입력 항목입니다.");
		validateInputFields(entry, "salary", "연봉은 숫자로 입력해주세요.", (val) => /^\d+$/.test(val));
		
		validateSelectFields(entry, "careerJobGroupSelect", "담당직군은 필수 선택 항목입니다.");
		validateSelectFields(entry, "careerJobSelect", "담당직무는 필수 선택 항목입니다.");
		
		validateTextareaFields(entry, "workDescription", "업무내용은 필수 입력 항목입니다.");
	});
}

// 학력 검증 함수
function validateSchoolEntries() {
	document.querySelectorAll('.school-entry').forEach(entry => {
		validateSelectFields(entry, "sortation", "구분은 필수 선택 항목입니다.");
		validateInputFields(entry, "schoolName", "학교명은 필수 입력 항목입니다.");

		const sortationSelect = entry.querySelector('select[name="sortation"]');
		const sortationValue = sortationSelect ? sortationSelect.value : "";
		
		if (sortationValue === "high") {
			validateInputFields(entry, "yearOfGraduation", "졸업년도는 YYYY.MM 형식으로 입력해주세요.", (val) => /^\d{4}\.\d{2}$/.test(val));
		} else if (sortationValue && sortationValue !== "") {
			validateInputFields(entry, "majorName", "전공명은 필수 입력 항목입니다.");
			validateInputFields(entry, "startDate", "입학년월은 YYYY.MM 형식으로 입력해주세요.", (val) => /^\d{4}\.\d{2}$/.test(val));
			validateInputFields(entry, "endDate", "졸업년월은 YYYY.MM 형식으로 입력해주세요.", (val) => /^\d{4}\.\d{2}$/.test(val));
		}
	});
}

// 검증 초기화 함수
function initializeValidation() {
	// 기존 메시지 제거
	document.querySelectorAll(".error-msg").forEach(e => e.remove());
	document.querySelectorAll(".success-msg").forEach(e => e.remove());
	
	// 제목 에러 컨테이너 클리어
	clearTitleError();
	
	// 필드 초기화
	const fields = ["title", "name", "birthdate", "phoneNumber", "email", "roadAddress", "postalCodeId"];
	fields.forEach(id => {
		const field = document.getElementById(id);
		if (field) field.style.borderColor = "";
	});
	
	const photoBox = document.getElementById("photoBox");
	if (photoBox) photoBox.style.borderColor = "";
	
	// 전역 변수 초기화
	validationResult = true;
	errorMessages = [];
}

// 메인 이력서 밸리데이션 함수
function validateResume() {
	initializeValidation();//초기화
	
	validateBasicFields();// 기본 필드 검증
	validateTrainingEntries();// 교육/훈련 검증
	validateCertificateEntries();// 자격증 검증
	validateCareerEntries();// 경력 검증
	validateSchoolEntries();// 학력 검증

	if (!validationResult) {
		// 오류 메시지 출력
		Swal.fire({
			icon: 'error',
			title: '입력 정보를 확인해주세요',
			html: '입력정보에 오류가 있습니다. 확인 후 다시 시도해주세요.',
			confirmButtonText: '확인',
			customClass: {
				confirmButton: "swalConfirmBtn",
			},
		});
	}

	return validationResult;
}

// ==================== 이력서 저장 함수 ====================

// 이력서 저장 함수
async function saveResume() {
			console.log("이력서 저장 함수 실행");
			if (validateResume() === false) {
				return;
			}
			try {
				const resumeData = collectResumeData();

				if (window.isEditMode && window.currentResumeId) {
					resumeData.resumeId = parseInt(window.currentResumeId);
				}

				console.log("수집된 이력서 데이터:", resumeData);

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

// ==================== 미리보기 함수 ====================

// 이력서 미리보기 팝업창 열기
$(".btn-preview").click(async function() {
			const resumeData = collectResumeData();

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
					const popupWidth = 980;
					const screenHeight = window.screen.availHeight;
					const screenLeft = window.screenLeft !== undefined ? window.screenLeft : screen.left;
					const screenWidth = window.innerWidth || document.documentElement.clientWidth || screen.width;
					const left = screenLeft + (screenWidth - popupWidth) / 2;
					var popupOption = `width=${popupWidth}, height=${screenHeight},left=${left}`;
					window.open(url, windowName, popupOption);
				} else {
					alert("미리보기 실패: 서버 오류");
				}
			} catch (err) {
				console.error("미리보기 오류:", err);
				alert("미리보기 중 오류 발생: " + err.message);
			}
		});


