$(document).ready(function () {
    const deadlineStr = $("#endDateRaw").data("deadline");

    if (!deadlineStr) return;

    const deadline = new Date(deadlineStr);

    function updateCountdown() {
        const now = new Date();
        const diff = deadline - now;

        if (diff <= 0) {
            $("#deadlineCountdown").text("마감되었습니다");
            return;
        }

        const days = Math.floor(diff / (1000 * 60 * 60 * 24));
        const hours = String(Math.floor((diff / (1000 * 60 * 60)) % 24)).padStart(2, '0');
        const minutes = String(Math.floor((diff / (1000 * 60)) % 60)).padStart(2, '0');
        const seconds = String(Math.floor((diff / 1000) % 60)).padStart(2, '0');

        $("#deadlineCountdown").html(`${days}일 ${hours}:${minutes}:${seconds}`);
    }

    updateCountdown();
    setInterval(updateCountdown, 1000);
});


$(document).on('click', '.btn-apply', function () {

    if (!resumeList || resumeList.length === 0) {
        Swal.fire('📭 등록된 이력서가 없습니다.');
        return;
    }

    const jobPostId = $(this).closest('.job').data('jobpostid');






    fetchMatchScores(jobPostId, () => {
        // ❗ 이 안에 사전질문 여부 확인까지 포함시켜도 됨
        $.ajax({
            url: '/resume/apply/questions',
            method: 'GET',
            data: { jobPostId },
            success: function (questionList) {
                if (questionList.length > 0) {
                    Swal.fire({
                        icon: 'info',
                        title: '사전질문 포함',
                        html: `<b>${questionList.length}개의 사전질문</b>이 등록된 공고입니다.<br>이력서 선택 후 답변을 입력해주세요.`,
                        confirmButtonText: '이력서 선택으로 이동',
				        customClass: {
				            confirmButton: 'swalConfirmBtn'
				        }
                    }).then(() => {
                        showResumeSelectModal(jobPostId);
                    });
                } else {
                    showResumeSelectModal(jobPostId);
                }
            },
            error: function () {
                Swal.fire({
                    title:"🚨 질문 조회 실패",
                    confirmButtonText: '확인',
                    customClass: {
                        confirmButton: "swalConfirmBtn",
                    },
                });
            }
        });
    });
});


function showResumeSelectModal(jobPostId) {



    const html = resumeList.map(r => `
    <label class="resume-item"  data-matchscore="${r.matchScore}">
        <div class="resume-radio-row">
            <div class="resume-left">
                <input type="radio" name="resumeRadio" value="${r.resumeId}">
                <div>
                    <div class="resume-title">${r.title}</div>
                    <div class="resume-meta">🗓 작성일: ${r.modifiedAt}</div>
                </div>
            </div>
            <div class="resume-match">적합도 ${r.matchScore != null ? r.matchScore + '%' : '-'}</div>
        </div>
    </label>
`).join('');


    Swal.fire({
        title: '📄 이력서를 선택하세요',
        html: `
            <div class="resume-list">${html}</div>
            <p style="font-size: 13px; color: red; margin-top: 10px;">
                ⚠️ 지원한 이력서는 <b>수정은 가능하지만 재지원은 불가능합니다.</b>
            </p>
        `,
        width: '650px',
        showCancelButton: true,
        confirmButtonText: '지원하기',
        cancelButtonText: '취소',
        customClass: {
            confirmButton: "swalConfirmBtn",
            cancelButton: "swalCancelBtn",
        },
        reverseButtons: true, // 버튼 순서 거꾸로
        preConfirm: () => {
            const selected = $('input[name="resumeRadio"]:checked').val();
            if (!selected) {
                Swal.showValidationMessage('이력서를 선택해주세요.');
                return false;
            }
            return selected;
        }
    }).then(result => {
        if (result.isConfirmed) {
            const selectedResumeId = result.value;
			const selectedRadio = $('input[name="resumeRadio"]:checked');
			   const selectedMatchScore = selectedRadio.closest('.resume-item').data('matchscore');
            $.ajax({
                url: '/resume/apply/questions',
                method: 'GET',
                data: { jobPostId },
                success: function (questionList) {
                    if (questionList.length > 0) {
                        openQuestionsModal(jobPostId).then(questionResult => {
                            if (questionResult.isConfirmed) {
                                applyResumeAjax(selectedResumeId, jobPostId,selectedMatchScore);
                            }
                        });
                    } else {
                        applyResumeAjax(selectedResumeId, jobPostId,selectedMatchScore);
                    }
                },
                error: function () {
                    Swal.fire({
                        title: "❌ 질문 조회 실패",
                        confirmButtonText: '확인',
                        customClass: {
                            confirmButton: "swalConfirmBtn",
                        },
                    });
                }
            });
        }
    });
}

function openQuestionsModal(jobPostId) {
    return $.ajax({
        url: '/resume/apply/questions',
        method: 'GET',
        data: { jobPostId }
    }).then(questionList => {
        const questionHtml = questionList.map((q, idx) => `
            <div style="text-align: left; margin-bottom: 10px;">
                <b>Q${idx + 1}.</b> ${q.questionText}
             <textarea name="answer${idx + 1}" data-question-id="${q.questionId}" rows="4" style="width:100%; margin-top:5px;" placeholder="답변을 입력하세요."></textarea>

            </div>
        `).join('');

        return Swal.fire({
            title: '📋 사전질문 확인',
            html: `
                <div style="text-align: left;">
                       <p style="margin-bottom: 12px;">
                      아래 사전질문은 <strong style="color: #3366ff;">선택사항</strong>입니다.<br/>
                      답변을 작성하지 않아도 지원이 가능합니다.
                    </p>
                </div>
                <br>
                ${questionHtml}
            `,
            confirmButtonText: '확인',
            cancelButtonText: '지원 취소',
			customClass: {
	            confirmButton: "swalConfirmBtn",
	            cancelButton: "swalCancelBtn",
	        },
			reverseButtons: true,
            showCancelButton: true,
            allowOutsideClick: false,
            allowEscapeKey: false,
            width: 600
        });
    });
}

function applyResumeAjax(resumeId, jobPostId,matchScore) {
    const seen = new Set();
    const answerList = [];

    $('textarea[name^=answer]').each(function () {
        const questionId = $(this).data('question-id');
        const answerText = $(this).val().trim();

        if (!questionId || seen.has(questionId)) return; // 중복 방지
        if (answerText) {
            answerList.push({ questionId, answerText });
            seen.add(questionId);
        }
    });
    const totalQuestions = $('textarea[name^=answer]').length;
    const answeredCount = answerList.length;
    // 전체 미답변
    if (answeredCount === 0 && totalQuestions > 0) {
        Swal.fire({
            icon: 'question',
            title: '모든 질문이 미응답입니다',
            html: `답변을 작성하지 않아도 지원이 가능하지만,<br><strong>정말 그대로 지원하시겠습니까?</strong>`,
            showDenyButton: true,
            confirmButtonText: '지원하기',
            denyButtonText: '답변하러 가기',
			customClass: {
	            confirmButton: "swalConfirmBtn",
	            denyButton: "swalDenyBtn",
	        },
        }).then(result => {
            if (result.isConfirmed) {
                sendApplyAjax(resumeId, jobPostId, answerList,matchScore);
            } else if (result.isDenied) {
                // 👉 다시 질문 모달로
                openQuestionsModal(jobPostId).then(questionResult => {
                    if (questionResult.isConfirmed) {
                        applyResumeAjax(resumeId, jobPostId,matchScore);  // 다시 확인하고 진행
                    }
                });
            }
        });
        return;
    }


// 일부 미답변
    if (answeredCount > 0 && answeredCount < totalQuestions) {
        Swal.fire({
            icon: 'question',
            title: '일부 질문 미답변',
            html: `총 ${totalQuestions}개 중 ${answeredCount}개만 답변했습니다.<br>계속 진행하시겠습니까?`,
            showDenyButton: true,
            confirmButtonText: '지원하기',
            denyButtonText: '답변하러 가기',
			customClass: {
	            confirmButton: "swalConfirmBtn",
	            denyButton: "swalDenyBtn",
	        },
        }).then(result => {
            if (result.isConfirmed) {
                sendApplyAjax(resumeId, jobPostId, answerList,matchScore);
            } else if (result.isDenied) {
                // 👉 다시 질문 모달로
                openQuestionsModal(jobPostId).then(questionResult => {
                    if (questionResult.isConfirmed) {
                        applyResumeAjax(resumeId, jobPostId,matchScore);  // 답변 재확인 후 재진입
                    }
                });
            }
        });
        return;
    }

// 모두 답변했을 경우엔 바로 진행
    sendApplyAjax(resumeId, jobPostId, answerList,matchScore);
}
//분기처리 ,미응답 답변 함수 //
function sendApplyAjax(resumeId, jobPostId, answerList,matchScore) {
    $.ajax({
        url: "/resume/apply",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            resumeId,
            jobPostId,
            answerList,
			matchScore
        }),
        success: function () {
            Swal.fire({
                title: '지원 완료 🎉',
                html: `입사지원 완료<br><span style="font-size: 13px; color: #555;">(지원내역은 마이페이지에서 확인 가능합니다)</span>`,
                icon: 'success',
				customClass: {
		            confirmButton: "swalConfirmBtn",
		        },
            });
        },
        error: function () {
            Swal.fire({
                title: '이미 지원 하신 공고입니다.',
                icon: 'warning',
				confirmButtonText: "확인",
				customClass: {
		            confirmButton: "swalConfirmBtn",
		        },
            });
        }
    });
}



function fetchMatchScores(jobPostId, callback) {
    const resumeIds = resumeList.map(r => r.resumeId);

    if (resumeIds.length === 0) {
        callback(); // 이력서 없음
        return;
    }

    $.ajax({
        url: '/resume/matchScore',
        method: 'GET',
        traditional: true, // 배열 전송을 위해 필수
        data: {
            jobPostId,
            resumeIds
        },
        success: function (scoreMap) {
            resumeList.forEach(r => {
                r.matchScore = scoreMap[r.resumeId] ?? null;
            });
            callback(); // 점수 적용 후 후속 처리 실행
        },
        error: function () {
            console.error("❌ 적합도 점수 불러오기 실패");
            callback(); // 실패해도 흐름은 이어감
        }
    });
}


