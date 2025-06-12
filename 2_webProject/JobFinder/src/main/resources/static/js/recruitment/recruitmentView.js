// 드롭다운 열고 닫기
$('.dropdown-toggle').on('click', function (e) {
    e.stopPropagation();
    const $content = $(this).next('.dropdown-content');
    if ($content.css('display') === 'none') {
        $('.dropdown-content').css('display', 'none');
        $content.css('display', 'flex');
    } else {
        $content.css('display', 'none');
    }
});

// 외부 클릭 시 닫기
$(document).on('click', function () {
    $('.dropdown-content').css('display', 'none');
});

// 내부 label 클릭 시 닫힘 방지
$(document).on('click', '.job-list label, .dropdown-content', function (e) {
    e.stopPropagation();
});

// 전역 변수
let checkedJobs = {}; // { jobId: { name: '', group: '' } }

// 직군 클릭 시 Ajax로 직무 가져오기 (복원 X)
$(document).on('click', '.job-group', function (e) {
    e.stopPropagation();
    const jobGroupId = $(this).data('code');
    const jobGroupName = $(this).data('name');

    // ✅ 기존에 선택된 직군 해제
    $('.job-group').removeClass('selected');

    // ✅ 선택한 직군만 active 표시
    $(this).addClass('selected');

    // ✅ 기존 직무 선택 초기화
    $('input[name="job"]:checked').prop('checked', false);
    $('#divSelectedCon').empty();
    checkedJobs = {}; // 전역 변수 초기화

    // ✅ 직무 + 스킬 Ajax 호출
    $.ajax({
        url: '/Recruitment/searchJob',
        method: 'GET',
        data: { jobGroupId },
        success: function (response) {
            $('#jobList').empty();
            if (Array.isArray(response.jobList)) {
                response.jobList.forEach(function (item) {
                    const html = `
                        <label>
                            <input class="chk" type="checkbox"
                                   name="job"
                                   value="${item.jobName}"
                                   data-id="${item.jobId}"
                                   data-group="${jobGroupName}">
                            ${item.jobName}&nbsp; <span>(${item.postCount})</span>
                        </label>
                    `;
                    $('#jobList').append(html);
                });
            }
            $('.skillList').empty();
            if (Array.isArray(response.skillList)) {
                response.skillList.forEach(function (item) {
                    const skillArr = `
                        <label>
                            <input class="chk" type="checkbox"
                                   name="skill"
                                   value="${item.tagName}"
                                   data-id="${item.tagId}"
                                   data-group="${item.jobGroupId}">
                            ${item.tagName}
                        </label>
                    `;
                    $('.skillList').append(skillArr);
                });
            }
        },
        error: function (error) {
            console.error('에러 발생:', error);
        }
    });
});


// 체크박스 선택/해제 처리 및 하단 리스트 출력
$(document).on('change', '.chk', function () {
    const jobName = $(this).val();
    const jobId = $(this).data('id');
    const jobGroupName = $(this).data('group');

    // 스킬은 click 이벤트에서만 처리
    if ($(this).attr('name') === 'skill') {
        return; // 아래 로직은 스킬 태그에는 적용하지 않음
    }

    if ($(this).is(':checked')) {
        if ($(`#occ_detail_list_${jobId}`).length > 0) return;

        checkedJobs[jobId] = { name: jobName, group: jobGroupName };

        $('#divSelectedCon').append(`
            <li id="occ_detail_list_${jobId}" data-id="${jobId}">
                <em>${jobGroupName} > ${jobName}</em>
                <button type="button" class="shb-btn-del" onclick="removeDetail(this)">X</button>
            </li>
        `);
    } else {
        delete checkedJobs[jobId];
        $(`#occ_detail_list_${jobId}`).remove();
    }
});

// 경력 필터 체크박스 변경 시 선택된 항목 표시
$(document).on('change', 'input[name="career"]', function () {
    updateCareerFilterDisplay();
});

// 학력 필터 체크박스 변경 시 선택된 항목 표시
$(document).on('change', 'input[name="education"]', function () {
    updateEducationFilterDisplay();
});

// 전문분야(스킬) 필터 표시 업데이트
function updateSkillFilterDisplay() {
    const selectedSkills = $('input[name="skill"]:checked');
    $('#divSelectedSkills').empty();
    
    selectedSkills.each(function() {
        const skillName = $(this).val();
        const skillId = $(this).data('id');
        
        $('#divSelectedSkills').append(`
            <li id="skill_item_${skillId}" data-skill-id="${skillId}">
                <em>${skillName}</em>
                <button type="button" class="shb-btn-del" onclick="removeSkillFilter(this)">X</button>
            </li>
        `);
    });
}

// 경력 필터 표시 업데이트
function updateCareerFilterDisplay() {
    const selectedCareers = $('input[name="career"]:checked');
    $('#divSelectedCareer').empty();
    
    selectedCareers.each(function() {
        const careerName = $(this).data('display');
        const careerValue = $(this).val();
        
        $('#divSelectedCareer').append(`
            <li id="career_item_${careerValue}" data-career-value="${careerValue}">
                <em>${careerName}</em>
                <button type="button" class="shb-btn-del" onclick="removeCareerFilter(this)">X</button>
            </li>
        `);
    });
}

// 학력 필터 표시 업데이트
function updateEducationFilterDisplay() {
    const selectedEducations = $('input[name="education"]:checked');
    $('#divSelectedEducation').empty();
    
    selectedEducations.each(function() {
        const educationName = $(this).data('display');
        const educationValue = $(this).val();
        
        $('#divSelectedEducation').append(`
            <li id="education_item_${educationValue}" data-education-value="${educationValue}">
                <em>${educationName}</em>
                <button type="button" class="shb-btn-del" onclick="removeEducationFilter(this)">X</button>
            </li>
        `);
    });
}

$(document).on('click', 'input[name="skill"]', function (e) {
    const maxSkillCount = 3; // 최대 3개까지 허용 (4개가 되면 막기)
    
    // 클릭 전의 원래 상태를 확인 (클릭 후 상태와 반대)
    const wasChecked = !$(this).is(':checked');
    const currentChecked = $('input[name="skill"]:checked').length;
    
    // 1. 사용자가 클릭을 한다.
    if (wasChecked) {
        // 1-1. 이미 있는 전문분야라면 선택을 취소한다 (아이템삭제) - 허용
        return; // 체크 해제는 항상 허용
    } else {
        // 1-2. 새로운 전문분야라면, 이 전문분야를 추가했을 때 전문분야가 4개가 되는지 평가한다.
        if (currentChecked >= maxSkillCount+1) {
            // 1-3-2. 4개가 된다면 추가하지 않고 경고 토스트 팝업을 띄우고 종료
            loginFailPop(`스킬은 최대 ${maxSkillCount}개까지만 선택할 수 있습니다.`);
            e.preventDefault();
            return false;
        }
        // 1-3-1. 4개가 되지 않는다면 추가하고 종료 - 허용
    }
});
// 리스트 X버튼 누르면 삭제 및 체크 해제
function removeDetail(button) {
    const $li = $(button).closest('li');
    const jobId = $li.data('id');

    delete checkedJobs[jobId];
    $li.remove();
    $(`.chk[data-id='${jobId}']`).prop('checked', false);
}

// 스킬 필터 항목 제거
function removeSkillFilter(button) {
    const $li = $(button).closest('li');
    const skillId = $li.data('skill-id');
    
    $li.remove();
    $(`input[name="skill"][data-id="${skillId}"]`).prop('checked', false);
    
    // 스킬 카운트 업데이트
    const currentChecked = $('input[name="skill"]:checked').length;
    $('#skill-count').text(currentChecked);
    
    updateFilteredCount();
}

// 경력 필터 항목 제거
function removeCareerFilter(button) {
    const $li = $(button).closest('li');
    const careerValue = $li.data('career-value');
    
    $li.remove();
    $(`input[name="career"][value="${careerValue}"]`).prop('checked', false);
    
    updateFilteredCount();
}

// 학력 필터 항목 제거
function removeEducationFilter(button) {
    const $li = $(button).closest('li');
    const educationValue = $li.data('education-value');
    
    $li.remove();
    $(`input[name="education"][value="${educationValue}"]`).prop('checked', false);
    
    updateFilteredCount();
}






function getFilterParams() {
    const jobIds = $('input[name="job"]:checked').map(function () {
        return $(this).data('id');
    }).get();

    const careers = $('input[name="career"]:checked').map(function () {
        return $(this).val();
    }).get();
    console.log("스킬 값은 :"+careers );
    const educations = $('input[name="education"]:checked').map(function () {
        return $(this).val();
    }).get();

    const skillTags = $('input[name="skill"]:checked').map(function () {

        return $(this).data('id');

    }).get();

    return {
        jobIds, careers, educations, skillTags
    };
}

function updateFilteredCount() {
    const params = getFilterParams();

    const caseNum = validateFilterCase();
    if (caseNum === false || caseNum === 12) {
        $('#filteredCount').text('0');
        $('#btnSearchFiltered').html('선택된 <span id="filteredCount">0</span>건 검색하기');
        return;
    }

    $.ajax({
        url: '/Recruitment/filter/count',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(params),
        success: function (count) {
            $('#filteredCount').text(count.toLocaleString());
            $('#selectedCountBtn').text(`선택된 ${count}건 검색하기`); // ✅ 버튼 텍스트 업데이트
        },
        error: function () {
            $('#filteredCount').text('0');
            $('#selectedCountBtn').text('선택된 0건 검색하기'); // 실패 시에도 0건 표시
        }
    });
}


function loginFailPop(msg) {
    $('#askConfirm').html(msg);
    $('#askConfirm').attr('class', 'active');
    setTimeout(function() {
        $('#askConfirm').removeClass('active');
    }, 1500);
}




/**
 * 필터 조합 유효성 검사 + 케이스 번호 반환
 * @returns {number|false} - 유효하면 1~12 케이스 번호, 유효하지 않으면 false 반환
 */
function validateFilterCase() {
    const jobIds = $('input[name="job"]:checked').length;
    const jobGroupSelected = $('.job-group.selected').length > 0;
    const careers = $('input[name="career"]:checked').length;
    const educations = $('input[name="education"]:checked').length;
    const skills = $('input[name="skill"]:checked').length;

    const hasJob = jobIds > 0;
    const hasGroup = jobGroupSelected > 0;
    const hasCareer = careers > 0;
    const hasEdu = educations > 0;
    const hasSkill = skills > 0;

    // Case 2: 경력 단독 선택 → 유효하지 않음
    if (hasCareer && !hasJob) {
        loginFailPop("경력 조건은 직군·직무 선택 후 사용 가능합니다.");
        return false;
    }

    //  Case 11: 직무만 단독 선택 → 유효하지 않음
    if (hasJob && !hasGroup) {
        loginFailPop("직군을 먼저 선택해주세요.");
        return false;
    }

    //  Case 10: 경력+학력+스킬, 직군/직무 없음 → 유효하지 않음
    if (hasCareer && hasEdu && hasSkill && !hasJob) {
        loginFailPop("직군/직무를 선택해주세요.");
        return false;
    }

    // ✅ 유효한 조합: 케이스 번호 판단
    if (hasGroup && hasJob && !hasCareer && !hasEdu && !hasSkill) return 1;
    if (!hasJob && hasEdu && !hasCareer && !hasSkill) return 3;
    if (!hasJob && hasSkill && !hasCareer && !hasEdu) return 4;
    if (hasGroup && hasJob && hasCareer && !hasEdu && !hasSkill) return 5;
    if (hasGroup && hasJob && hasCareer && hasEdu && !hasSkill) return 6;
    if (hasGroup && hasJob && hasCareer && hasEdu && hasSkill) return 7;
    if (hasGroup && hasJob && !hasCareer && hasEdu && !hasSkill) return 8;
    if (!hasGroup && !hasJob && !hasCareer && hasEdu && hasSkill) return 9;
    if (!hasGroup && !hasJob && hasCareer && hasEdu && hasSkill) return 10;
    if ((hasGroup && !hasJob) || (!hasGroup && hasJob)) return 11;
    if (!hasGroup && !hasJob && !hasCareer && !hasEdu && !hasSkill) return 12;

    // 예외 상황
    return -1;
}

$(document).on('change', '.chk, input[name="career"], input[name="education"]', function () {
    updateFilteredCount();
});

// 스킬 체크박스 변경 시 UI 업데이트 (별도 처리)
$(document).on('change', 'input[name="skill"]', function () {
    const newCount = $('input[name="skill"]:checked').length;
    $('#skill-count').text(newCount);
    updateSkillFilterDisplay();
    updateFilteredCount();
});

// 경력 체크 시 → 직군·직무 없으면 체크 막기 (중복선택 허용)
$(document).on('click', 'input[name="career"]', function (e) {
    const jobChecked = $('input[name="job"]:checked').length > 0;
    if (!jobChecked) {
        loginFailPop("경력 조건은 직군·직무 선택 후 사용 가능합니다.");
        e.preventDefault(); // ✅ 체크 자체를 막음
        return;
    }
    // 중복선택 허용을 위해 다른 체크박스 해제 코드 제거
});

// 학력 중복선택 허용 (기존 단일선택 제한 제거)
$(document).on('click', 'input[name="education"]', function () {
    // 중복선택 허용을 위해 다른 체크박스 해제 코드 제거
});
//버튼초기화 //


$(document).on('click', '#btnResetFilter', function (e) {
    e.stopPropagation();

    // 1. 모든 체크박스 해제
    $('input.chk, input[name="career"], input[name="education"], input[name="skill"]').prop('checked', false);

    // 2. 선택된 직군·직무 리스트 초기화
    $('#divSelectedCon').empty();

    // 3. 선택된 경력 리스트 초기화
    $('#divSelectedCareer').empty();

    // 4. 선택된 학력 리스트 초기화
    $('#divSelectedEducation').empty();

    // 5. 선택된 전문분야 리스트 초기화
    $('#divSelectedSkills').empty();

    // 6. 선택된 직군 해제
    $('.job-group').removeClass('selected');

    // 7. 전역 변수 초기화
    checkedJobs = {};

    // 8. 카운터 초기화 (직접 UI 초기화)
    $('#skill-count').text('0');
    $('#filteredCount').text('0');
    $('#btnSearchFiltered').html('선택된 <span id="filteredCount">0</span>건 검색하기');

    // 9. AJAX 호출 대신 UI만 초기화 (굳이 updateFilteredCount 호출 안 해도 됨)
});


$(document).on('click', '.job', function (e) {
    if ($(e.target).hasClass('apply-btn')) return;

    const jobPostId = $(this).data('jobpostid');
    const companyId = $(this).data('companyid');
    window.location.href = `detail?companyId=${companyId}&jobPostId=${jobPostId}`;
});

$
//날짜 포멧 //ajax용 ,
function formatDateWithDay(dateString) {
    const date = new Date(dateString);

    const month = String(date.getMonth() + 1).padStart(2, '0'); // 1월 → 01
    const day = String(date.getDate()).padStart(2, '0');

    const weekNames = ['일', '월', '화', '수', '목', '금', '토'];
    const weekDay = weekNames[date.getDay()]; // 0~6 → 요일

    return `${month}/${day}(${weekDay})`;
}

$('.page-btn[data-page="1"]').addClass('active');
$(document).on('click', '.page-btn', function () {
    const page = $(this).data('page');
    const params = getFilterParams();
    params.page = page; // ✅ 페이지 정보 추가
    $('.page-btn').removeClass('active');
    $(this).addClass('active');

    const caseNum = validateFilterCase();

    const url = (caseNum === false || caseNum === 12)
        ? '/Recruitment/list/json'
        : '/Recruitment/filter/list';

    $.ajax({
        url: url,
        type: 'GET',
        data: params,
        traditional: true,
        success: function (data) {
            renderJobList(data.recruitmentList, data.skillMap);
            renderPagination(data.pagination); // ✅ 버튼 다시 그림
        },
        error: function () {
            alert('페이지 이동 중 오류 발생');
        }
    });
});



function renderJobList(recruitmentList, skillMap) {
    $('#jobListings').empty();

    recruitmentList.forEach(function (item) {
        const isClosed = item.isContinuous !== 0;

        const applyBtnHtml = isClosed
            ? `<button class="apply-btn closed" type="button" data-status="closed" disabled style="background: #ccc; cursor: not-allowed; ">마감됨</button>`
            : `<button class="apply-btn" type="button" data-status="open">지원하기</button>`;

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
                ${applyBtnHtml}
                <div class="deadline">~${formatDateWithDay(item.endDate)}</div>
              </div>
            </div>
        `;
        $('#jobListings').append(html);
    });
}



$('#btnSearchFiltered').on('click', function () {
    const params = getFilterParams();
    console.log("🔍 필터 파라미터:", params);

    $.ajax({
        url: '/Recruitment/filter/list',
        type: 'GET',
        data: params,
        traditional: true,
        success: function (data) {
            renderJobList(data.recruitmentList, data.skillMap); // 필터링된 공고 렌더링
            renderPagination(data.pagination); // 페이지 버튼 다시 그리기
        },
        error: function () {
            alert('검색 중 오류가 발생했습니다.');
        }
    });
});
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

$(document).on('click', '.apply-btn', function () {
    console.log(resumeList);
    if (userType === 'company') {
        Swal.fire({ title: '기업회원은 지원 불가' });
        return;
    }

    if (!resumeList || resumeList.length === 0) {
        Swal.fire({
            title: (userType === 'member')
                ? '📭 등록된 이력서가 없습니다.'
                : '로그인 후 이용 가능합니다.',
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


