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

    $(this).siblings().removeClass('selected');
    $(this).addClass('selected');

    $.ajax({
        url: '/Recruitment/searchJob',
        method: 'GET',
        data: { jobGroupId: jobGroupId },
        success: function (response) {
            console.log("응답:", response);
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
                            ${item.jobName}
                        </label>
                    `;
                    $('#jobList').append(html);
                });
            } else {
                console.warn("jobList가 배열이 아닙니다.");
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

    // 스킬은 따로 제한 처리만, 아래는 job 전용 처리
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

$(document).on('change', 'input[name="skill"]', function () {
    const maxSkillCount = 3;
    const currentChecked = $('input[name="skill"]:checked').length;

    if (currentChecked > maxSkillCount) {
        loginFailPop(`스킬은 최대 ${maxSkillCount}개까지만 선택할 수 있습니다.`);

        $(this).prop('checked', false);
        return;
    }

    $('#skill-count').text(currentChecked);
});
// 리스트 X버튼 누르면 삭제 및 체크 해제
function removeDetail(button) {
    const $li = $(button).closest('li');
    const jobId = $li.data('id');

    delete checkedJobs[jobId];
    $li.remove();
    $(`.chk[data-id='${jobId}']`).prop('checked', false);
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

$(document).on('change', '.chk, input[name="career"], input[name="education"], input[name="skill"]', function () {
    updateFilteredCount();
});

// 경력 체크 시 → 직군·직무 없으면 체크 막기 , 1개이상 체크불가
$(document).on('click', 'input[name="career"]', function (e) {
    const jobChecked = $('input[name="job"]:checked').length > 0;
    if (!jobChecked) {
        loginFailPop("경력 조건은 직군·직무 선택 후 사용 가능합니다.");
        e.preventDefault(); // ✅ 체크 자체를 막음
    }
    $('input[name="career"]').not(this).prop('checked', false);
});
//학력도 1개만
$(document).on('click', 'input[name="education"]', function () {
    $('input[name="education"]').not(this).prop('checked', false);
});
//버튼초기화 //


$(document).on('click', '#btnResetFilter', function (e) {
    e.stopPropagation();

    // 1. 모든 체크박스 해제
    $('input.chk, input[name="career"], input[name="education"], input[name="skill"]').prop('checked', false);

    // 2. 선택된 직무 리스트 초기화
    $('#divSelectedCon').empty();

    // 3. 전역 변수 초기화
    checkedJobs = {};

    // 4. 카운터 초기화 (직접 UI 초기화)
    $('#skill-count').text('0');
    $('#filteredCount').text('0');
    $('#btnSearchFiltered').html('선택된 <span id="filteredCount">0</span>건 검색하기');

    // 5. AJAX 호출 대신 UI만 초기화 (굳이 updateFilteredCount 호출 안 해도 됨)
});

$('#btnSearchFiltered').on('click', function () {

    const params = getFilterParams();
    console.log("🔍 필터 파라미터:", params);
    $.ajax({
        url: '/Recruitment/filter/list',
        type: 'GET',
        data: {
            jobIds: params.jobIds,
            careers: params.careers,
            educations: params.educations,
            skillTags: params.skillTags
        },
        traditional: true, // 배열 전송시 반드시 필요!
        success: function (data) {
            console.log('채용공고:', data.recruitmentList);
            console.log('스킬맵:', data.skillMap);
            console.log('직군:', data.jobGroupList);
            $('#jobListings').empty(); // 기존 리스트 지우기

            data.recruitmentList.forEach(function (item) {
                const html = `
    <div class="job" data-jobpostid="${item.jobPostId}" data-companyid="${item.companyId}">
      <div class="company-name">${item.companyName}</div>
      <div class="job-info">
        <div class="job-title">${item.title} <span class="star">★</span></div>
        <div class="job-meta">
          <span>🧑‍💻 지원자격: ${item.education} </span>
          <span>🎓 경력: ${item.careerType}</span>
          <span>💼 채용직: ${item.jobName}</span>
        </div>
        <div class="job-meta-skill">
          🧩 스킬: ${
                    (data.skillMap[item.jobPostId] || [])
                        .map(skill => `<span class="tag">${skill.tagName}</span>`)
                        .join('')
                }
        </div>
      </div>
    </div>
  `;
                $('#jobListings').append(html);
            });


        },
        error: function () {
            alert('검색 중 오류가 발생했습니다.');
        }
    });
});

$(document).on('click', '.job', function (e) {
    if ($(e.target).hasClass('apply-btn')) return;

    const jobPostId = $(this).data('jobpostid');
    const companyId = $(this).data('companyid');
    window.location.href = `detail?companyId=${companyId}&jobPostId=${jobPostId}`;
});

$(document).on('click', '.apply-btn', function () {
    alert('처리예정입니다');
});
