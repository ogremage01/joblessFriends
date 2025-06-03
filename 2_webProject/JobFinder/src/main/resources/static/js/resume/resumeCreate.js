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
}); 