const writeBtn = document.getElementsByClassName("write-btn")[0];
writeBtn.addEventListener("click", function() {
   const resumeCount = document.getElementsByClassName("resume-card").length;
	if (resumeCount >= 5) {
        alert("이력서는 최대 5개까지 작성할 수 있습니다.");
        return;
    }
	window.location.href = "/resume/write";
});

const editBtns = document.querySelectorAll(".edit-btn");
editBtns.forEach(btn => {
    btn.addEventListener("click", function () {
        const resumeId = this.dataset.resumeId;
        window.location.href = '/resume/write?resumeId='+ resumeId;
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const deleteBtns = document.querySelectorAll(".delete-btn");

    deleteBtns.forEach(btn => {
        btn.addEventListener("click", function (e) {
            e.preventDefault(); // 버튼의 기본 동작 방지
            const resumeId = this.dataset.resumeId;

            if (!confirm("정말 이력서를 삭제하시겠습니까?")) return;

            fetch("/resume/delete", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `resumeId=${resumeId}`
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("삭제 실패");
                }
                // 삭제 성공 시 DOM에서 제거하거나 새로고침
                alert("이력서가 삭제되었습니다.");
                location.reload(); // 또는 해당 요소 제거
            })
            .catch(error => {
                console.error("삭제 중 오류 발생:", error);
                alert("삭제 중 오류가 발생했습니다.");
            });
        });
    });
});

// 이력서 미리보기 팝업창 열기
function openResumePreview(resumeId) {
    const url = '/resume/view/' + resumeId;
    const windowName = 'resumePreview';
    const windowFeatures = 'width=900,height=700,scrollbars=yes,resizable=yes,status=no,toolbar=no,menubar=no,location=no';
    
    window.open(url, windowName, windowFeatures);
}
