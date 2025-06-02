

// 버튼 클릭시 호출 함수
$(".btn-preview").click(async function(){
  const resumeData = collectResumeData();

  // 학력 요약 반영
  renderEducationSummary(resumeData.schools);
  // 경력 요약 반영
  renderCareerSummary(resumeData.careers);

  try {
    const response = await fetch("/resume/preview", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(resumeData)
    });

    const result = await response.text();
    if (result === "success") {
      const popupWidth = 980;
      const screenHeight = window.screen.availHeight;
      const screenLeft = window.screenLeft || screen.left;
      const screenWidth = window.innerWidth || document.documentElement.clientWidth || screen.width;
      const left = screenLeft + (screenWidth - popupWidth) / 2;
      const popupOption = `width=${popupWidth}, height=${screenHeight},left=${left}`;
      window.open("/resume/viewPreview", "resumePreview", popupOption);
    } else {
      alert("미리보기 실패: 서버 오류");
    }
  } catch (err) {
    console.error("미리보기 오류:", err);
    alert("미리보기 중 오류 발생: " + err.message);
  }
});


// 학력미리보기
function renderEducationSummary(schools) {
  const educationBox = document.querySelector("#profileBottom .resumeSum.education .sumContent");
  if (!educationBox) return;

  // 초기화
  educationBox.innerHTML = "";

  if (!schools || schools.length === 0) {
    educationBox.innerHTML = `<span class="sumItem">-</span>`;
    return;
  }

  schools.forEach(school => {
    const schoolName = school.schoolName || '';
    const sortationText = {
      high: '고등학교',
      univ4: '대학교(4년)',
      univ2: '대학교(2,3년)'
    }[school.sortation] || '기타';
    const status = school.status || '';

    const line = document.createElement("div");
    line.className = "edu-summary-line";
    line.innerHTML = `
      <span class="sumItem">${schoolName}</span>
      <span class="sumAddEx">${sortationText}</span>
      <span class="sumAddEx">${status}</span>
    `;
    educationBox.appendChild(line);
  });
}

