$(document).on('click', '.job', function(e) {
   const jobPostId = $(this).data('jobpostid');
   const companyId = $(this).data('companyid');
   window.location.href = `/Recruitment/detail?companyId=${companyId}&jobPostId=${jobPostId}`;
});

function loginFailPop(msg) {
    $('#askConfirm').html(msg);
    $('#askConfirm').attr('class', 'active');
    setTimeout(function() {
        $('#askConfirm').removeClass('active');
    }, 1500);
}

$(document).on('click', '.apply-btn', function () {
    console.log(resumeList);
    if (userType === 'company') {
        Swal.fire({ title: '기업회원은 지원 불가' });
        return;
    }

    if (!resumeList || resumeList.length === 0) {
        Swal.fire({
            title:'📭 등록된 이력서가 없습니다.',
            confirmButtonText: '확인',
            customClass: {
                confirmButton: "swalConfirmBtn",
            },
        });
        return;
    }

    const jobPostId = $(this).closest('.job').data('jobpostid');
    console.log("선택된 공고 ID:", jobPostId);

    // ✅ 적합도 점수 먼저 불러오기
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
							confirmButton: "swalConfirmBtn",
						},
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
    <label class="resume-item">
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

            $.ajax({
                url: '/resume/apply/questions',
                method: 'GET',
                data: { jobPostId },
                success: function (questionList) {
                    if (questionList.length > 0) {
                        openQuestionsModal(jobPostId).then(questionResult => {
                            if (questionResult.isConfirmed) {
                                applyResumeAjax(selectedResumeId, jobPostId);
                            }
                        });
                    } else {
                        applyResumeAjax(selectedResumeId, jobPostId);
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
            confirmButtonText: '지원하기',
            cancelButtonText: '취소',
            showCancelButton: true,
            allowOutsideClick: false,
            allowEscapeKey: false,
         customClass: {
            confirmButton: "swalConfirmBtn",
            cancelButton: "swalCancelBtn",
         },
         reverseButtons: true, // 버튼 순서 거꾸로
            width: 600
        });
    });
}

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

            // ✅ 여기서 matchScore 추출
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
                                applyResumeAjax(selectedResumeId, jobPostId,selectedMatchScore );
                            }
                        });
                    } else {
                        applyResumeAjax(selectedResumeId, jobPostId,selectedMatchScore );
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
            confirmButtonText: '지원하기',
            cancelButtonText: '취소',
            showCancelButton: true,
            allowOutsideClick: false,
            allowEscapeKey: false,
			customClass: {
				confirmButton: "swalConfirmBtn",
				cancelButton: "swalCancelBtn",
			},
			reverseButtons: true, // 버튼 순서 거꾸로
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
                        applyResumeAjax(resumeId, jobPostId, matchScore);  // 다시 확인하고 진행
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
                        applyResumeAjax(resumeId, jobPostId, matchScore);  // 답변 재확인 후 재진입
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
				confirmButtonText: '확인',
				customClass: {
								confirmButton: "swalConfirmBtn",
				},
            });
        },
        error: function () {
            Swal.fire({
                title: '이미 지원 하신 공고입니다.',
                icon: 'warning',
				confirmButtonText: '확인',
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

// ------------------------------------------------------------------------------

function renderPagination(pagination) {
    const $paginationContainer = $('#pagination');

    // 🧹 기존 버튼 제거 (핵심)
    $paginationContainer.empty();

    const currentPage = pagination.page;
    const totalPage = pagination.totalPageCount;

    // 📭 페이지가 없을 경우 메시지 출력
    if (totalPage === 0) {
        $paginationContainer.html('<span class="no-result">결과 없음</span>');
        return;
    }

    // ◀ 이전 버튼
    if (pagination.existPrevPage) {
        $paginationContainer.append(`
            <button class="page-btn" data-page="${pagination.startPage - 1}">«</button>
        `);
    }

    // 🔢 번호 버튼
    for (let i = pagination.startPage; i <= pagination.endPage; i++) {
        const isActive = i === currentPage ? 'active' : '';
        $paginationContainer.append(`
        <button class="page-btn ${isActive}" data-page="${i}">
            ${i}
        </button>
    `);
    }

    // ▶ 다음 버튼
    if (pagination.existNextPage) {
        $paginationContainer.append(`
            <button class="page-btn" data-page="${pagination.endPage + 1}">»</button>
        `);
    }
}


function renderJobList(recruitmentList, skillMap) {
    $('#jobListings').empty(); // 기존 리스트 지우기

    // 필터링된 공고를 렌더링
    recruitmentList.forEach(function (item) {
        const html = `
            <div class="job" data-jobpostid="${item.jobPostId}" data-companyid="${item.companyId}">
              <div class="company-name">${item.companyName}</div>
              <div class="job-info">
                <div class="job-title">${item.title}</div>
                <div class="job-meta">
                  <span>🎓 ${item.education}</span>
                  <span>🧑‍ ${item.careerType}</span>
                  <span>💼 ${item.jobName}</span>
                </div>
                <div class="job-meta-skill">
                  🧩 ${
            (skillMap[item.jobPostId] || [])
                .map(skill => `<div><span class="tag"> ${skill.tagName}</span></div>`)
                .join('')
        }
                </div>
              </div>
              <div class="job-action">
                <button class="apply-btn" type="button">지원하기</button>
                <div class="deadline">~${formatDateWithDay(item.endDate)}</div>
              </div>
            </div>
        `;
        $('#jobListings').append(html); // 필터링된 공고 리스트에 추가
    });
}


//날짜 포멧 //ajax용 ,
function formatDateWithDay(dateString) {
    const date = new Date(dateString);

    const month = String(date.getMonth() + 1).padStart(2, '0'); // 1월 → 01
    const day = String(date.getDate()).padStart(2, '0');

    const weekNames = ['일', '월', '화', '수', '목', '금', '토'];
    const weekDay = weekNames[date.getDay()]; // 0~6 → 요일

    return `${month}/${day}(${weekDay})`;
}


// -----------------------------------------------------------------------------------------------------------

let currentKeyword = '';



// 초기 URL 파라미터로부터 페이지/검색어 추출
$(document).ready(function () {
    const params = new URLSearchParams(window.location.search);
    const page = parseInt(params.get('page')) || 1;
    currentKeyword = params.get('keyword') || '';
    $('#keyword').val(currentKeyword); // input에 값 반영
    loadPage(page, currentKeyword);
});


// 페이지네이션 버튼 클릭 시
$(document).on('click', '.page-btn', function () {
    if ($(this).is(':disabled')) return;

    const page = $(this).data('page');
    updateUrl(page, currentKeyword); // 페이지 이동 시 URL 갱신
    loadPage(page, currentKeyword);
});

// URL을 갱신하는 함수 (주소만 바꿈, 새로고침 X)
function updateUrl(page, keyword) {
    const query = new URLSearchParams({ page, keyword }).toString();
    window.history.pushState({}, '', `/search?${query}`);
}


function loadPage(page, keyword) {
    $.ajax({
        url: '/search/json',
        type: 'GET',
        data: { page, keyword },
      success: function(response) {
          renderJobList(response.recruitmentList, response.skillMap);
          renderPagination(response.pagination);
          $('#searchSection span').html(
              `<b>'${keyword}'</b>에 대한 검색결과가 <b>총 ${response.totalCount}건</b> 있습니다.`
          );

        },
        error: function() {
            Swal.fire('데이터를 불러오지 못했습니다.');
        }
    });
}

