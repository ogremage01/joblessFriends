// 이력서 신규 작성 전용 스크립트

// DOM 로드 완료 후 실행
document.addEventListener("DOMContentLoaded", function() {
	// 스킬 해시태그 UI 초기화
	renderSkillHashtagInput();

	// 작성완료 버튼 이벤트 등록
	const finishBtn = document.querySelector('.btn-finish');
	if (finishBtn) {
		finishBtn.addEventListener('click', saveResume);
	}

	// 작성취소 버튼 이벤트 등록
	const cancelBtn = document.querySelector('.btn-cancel');
	if (cancelBtn) {
		cancelBtn.addEventListener('click', function() {
			if (confirm("작성 중인 이력서가 있습니다. 정말로 취소하시겠습니까?")) {
				window.location.href = '/resume/management';
			}
		});
	}
	
	// 사이드바 top버튼
	document.getElementById("btnTop").addEventListener("click", () => {
	  window.scrollTo({ top: 0, behavior: "smooth" });
	});


	// +추가 버튼들 이벤트 위임 방식으로 등록
	document.addEventListener("click", function(e) {
		// 학력 추가
		if (e.target.closest('.add-education-btn button')) {
			const schoolContainer = document.getElementById("school-container");
			const newSchool = createSchoolEntry();
			schoolContainer.appendChild(newSchool);
			
			// 달력 재적용
			const select = newSchool.querySelector('select[name="sortation"]');
				if (select) {
					select.addEventListener('change', () => {
						setTimeout(() => {
							applyMonthPickerByName("yearOfGraduation");
							applyMonthPickerByName("startDate");
							applyMonthPickerByName("endDate");
						}, 10); // 10ms 뒤에 적용 → DOM 생성 완료 후 실행됨
					});
				}
		}
		// 경력 추가
		if (e.target.closest('.add-career-btn button')) {
			const careerContainer = document.getElementById("career-container");
			const newCareer = createCareerEntry();
			careerContainer.appendChild(newCareer);
			
			applyMonthPickerByName("hireYm");
			applyMonthPickerByName("resignYm");
		}
		// 교육/훈련 추가
		if (e.target.closest('.add-training-btn button')) {
			const trainingContainer = document.getElementById("training-container");
			const newTraining = createTrainingEntry();
			trainingContainer.appendChild(newTraining);
			
			applyMonthPickerByName("startDate");
			applyMonthPickerByName("endDate");
		}
		// 자격증 추가
		if (e.target.closest('.add-license-btn button')) {
			const certificateContainer = document.getElementById("certificate-container");
			const newCertificate = createCertificateEntry();
			certificateContainer.appendChild(newCertificate);
			
			applyMonthPickerByName("acquisitionDate");
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
	
	// 달력
	  flatpickr("#birthdate", {
	    dateFormat: "Y-m-d",
	    locale: "ko",
	    maxDate: "today"
	});
	
	// 달력 년/월만
	function applyMonthPickerByName(name) {
	    flatpickr(`input[name="${name}"]`, {
	      plugins: [
	        new monthSelectPlugin({
	          shorthand: false,
	          dateFormat: "Y.m",
	          altFormat: "Y년 m월"
	        })
	      ],
	      locale: "ko",
	      maxDate: "today"
	    });
	  }

	  // 이름 기반으로 적용
	  applyMonthPickerByName("startDate");   // 교육, 경력, 학력 등
	  applyMonthPickerByName("endDate");
	  applyMonthPickerByName("hireYm");
	  applyMonthPickerByName("resignYm");
	  applyMonthPickerByName("acquisitionDate");
}); 