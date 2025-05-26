$(document).ready(function () {
    //파일 이름 //
    $('#jobImgFile').on('change', function () {
        const file = this.files[0];
        if (file) {
            $('#fileNameText').text(file.name);
            $('#fileInfoBox').show();
        } else {
            $('#fileNameText').text('');
            $('#fileInfoBox').hide();
        }
    });

    $('#removeFileBtn').on('click', function () {
        $('#jobImgFile').val('');
        $('#fileNameText').text('');
        $('#fileInfoBox').hide();
    });
    //복리후생리스트
    const initialWelfare = $('input[name="welfareList"]').val();
    if (initialWelfare) {
        welfareTags = initialWelfare.split('|');
        welfareTags.forEach(tag => {
            $('#welfareList').append(`
                <div class="flex-row welfare-item">
                    <span>${tag}</span>
                    <button type="button" class="remove-welfare" data-val="${tag}">X</button>
                </div>
            `);
        });
    }

    // 직군 선택 변경 시
    $('select[name="jobGroupId"]').on('change', function () {
        const jobGroupId = $(this).val();

        if (!jobGroupId) return;

        $.ajax({
            url: '/Recruitment/searchJob',
            method: 'GET',
            data: { jobGroupId },
            success: function (response) {
                // 직무 처리
                const $jobSelect = $('select[name="jobId"]');
                $jobSelect.empty().append('<option value="">직무 선택</option>');
                response.jobList.forEach(job => {
                    $jobSelect.append(`<option value="${job.jobId}">${job.jobName}</option>`);
                });

                // 스킬 처리
                const $tagList = $('#tag-list'); //
                $tagList.empty();
                response.skillList.forEach(tag => {
                    $tagList.append(`
                    <label class="skill-tag">
                        <input type="checkbox" name="tagId" value="${tag.tagId}"> ${tag.tagName}
                    </label>
                `);
                });
            },
            error: function () {
                alert('직무 및 스킬 목록을 불러오는 데 실패했습니다.');
            }
        });
    });

    $(document).on('change', 'input[name="tagId"]', function () {
        const maxChecked = 5;
        const checkedCount = $('input[name="tagId"]:checked').length;

        if (checkedCount > maxChecked) {

            loginFailPop(`최대 ${maxChecked}개까지만 선택할 수 있습니다.`);
            $(this).prop('checked', false);
        }
    });

});




$("#generateTemplate").on('click',function () {
    updateWelfareInput();
    if (!validateFormInputs()) return;
    let titleValue = $(".InsertTitle > input").val();

    let openEnded = $('.InsertDate > input[name="openEnded"]').is(':checked');
    let careerTypeValue = $("select[name='careerType']").val();
    let educationValue = $("select[name='education']").val();
    let jobGroupIdValue = $("select[name='jobGroupId']").val();
    let workHoursValue = $("select[name='workHours']").val();
    let salaryValue = $(".InsertJob  input[name='salary']").val();
    let contentValue = editor.getHTML();
    let templateType = $("select[name='templateType']").val();    //템플릿 타입  //
    let startDate = $('.InsertDate input[name="startDate"]').val();
    let endDate = $('.InsertDate input[name="endDate"]').val();
    const welfareHtml = welfareTags.map(text => `<li>${text}</li>`).join('');
    const selectedTags = $('input[name="tagId"]:checked')
        .map(function () {
            return $(this).parent().text().trim();
        })
        .get()
        .slice(0, 5); //스킬리스트수집
    console.log({ startDate, endDate, salaryValue }); // 디버깅
    const tagHtml = selectedTags.map(tag => `<span class="tag">${tag}</span>`).join('');

    let html = '';

    if (templateType === 'self') {
        // 🧩 직접작성형
        html = `
           <link rel="stylesheet" href="/css/recruitment/recruitmentDetail.css" />
            <div class="detail-wrapper">
                <div class="detail-main">
                    <div class="detail-header">
                        <h2>${titleValue}</h2>
                        <div class="company-name">기업명</div>
                    </div>
                    <div class="detail-info-grid">
                        <div><span class="detail-info-label">경력</span>${careerTypeValue}</div>
                        <div><span class="detail-info-label">근무시간</span>${workHoursValue}</div>
                        <div><span class="detail-info-label">접수 시작일</span>${startDate}</div>
                        <div><span class="detail-info-label">접수 마감일</span>${endDate}</div>
                        <div><span class="detail-info-label">학력</span>${educationValue}</div>
                        <div><span class="detail-info-label">급여</span>${salaryValue}만원</div>
                    </div>
                </div>
                <div class="detail-sidebar">
                    <div class="dday">접수 마감일까지 남은 시간<br><span>-- 계산 예정 --</span></div>
                    <div class="btn-group">
                        <button class="btn-bookmark">★ 공고 찜하기</button>
                        <button class="btn-apply">지원하기</button>
                    </div>
                </div>
            </div>
            <div class="detail-body">
        <div class="detail-content-wrapper">
       
            <section class="detail-section">
                <h3>공고 내용</h3>
                <div>${contentValue}</div>
            </section>
           
        </div>
         </div>
    `;
    } else {
        // 🧩 기본형
        html = `
            <link rel="stylesheet" href="/css/recruitment/recruitmentDetail.css" />
            <div class="detail-wrapper">
                <div class="detail-main">
                    <div class="detail-header">
                        <h2>${titleValue}</h2>
                        <div class="company-name">기업명</div>
                    </div>
                    <div class="detail-info-grid">
                        <div><span class="detail-info-label">경력</span>${careerTypeValue}</div>
                        <div><span class="detail-info-label">근무시간</span>${workHoursValue}</div>
                        <div><span class="detail-info-label">접수 시작일</span>${startDate}</div>
                        <div><span class="detail-info-label">접수 마감일</span>${endDate}</div>
                        <div><span class="detail-info-label">학력</span>${educationValue}</div>
                        <div><span class="detail-info-label">급여</span>${salaryValue}만원</div>
                    </div>
                </div>
                <div class="detail-sidebar">
                    <div class="dday">접수 마감일까지 남은 시간<br><span>-- 계산 예정 --</span></div>
                    <div class="btn-group">
                        <button class="btn-bookmark">★ 공고 찜하기</button>
                        <button class="btn-apply">지원하기</button>
                    </div>
                </div>
            </div>

            <div class="detail-body">
                <div class="detail-content-wrapper">
                    <section class="detail-section">
                        <h3>요구 스킬</h3>
                        <div class="tag-list">${tagHtml}</div>
                    </section>
                    <section class="detail-section">
                        <h3>상세 내용</h3>
                        <div>${contentValue}</div>
                        <h3>복리후생</h3>
                        <ul>${welfareHtml}</ul>
                    </section>
                    <section class="detail-section">
                        <h3>전형 절차</h3>
                        <ol class="step-list">
                            <li>서류 전형</li>
                            <li>실무 면접</li>
                            <li>임원 면접</li>
                            <li>최종 합격</li>
                        </ol>
                    </section>
                    <section class="detail-section">
                        <h3>제출 서류</h3>
                        <ul>
                            <li>이력서</li>
                            <li>자기소개서 (자유 양식)</li>
                        </ul>
                    </section>
                    <section class="detail-section">
                        <h3>기업 정보</h3>
                        <ul>
                            <li><strong>기업명:</strong> 기업</li>
                            <li><strong>담당자명:</strong> 담당자</li>
                            <li><strong>연락처:</strong> 02-805-2311</li>
                            <li><strong>주소:</strong> 서울 금천구 시흥동</li>
                        </ul>
                    </section>
                </div>
            </div>
        `;
    }

    $("#templatePreview").html(html);

})



function validateFormInputs() {
    const title = $('input[name="title"]').val().trim();
    const startDate = $('input[name="startDate"]').val();
    const endDate = $('input[name="endDate"]').val();
    const careerType = $('select[name="careerType"]').val();
    const education = $('select[name="education"]').val();
    const jobGroupId = $('select[name="jobGroupId"]').val();
    const jobId = $('select[name="jobId"]').val();
    const salary = $('input[name="salary"]').val().trim();
    const content = editor.getHTML().trim();
    const selectedSkillIds = $('input[name="tagId"]:checked').map(function () {
        return $(this).val();
    }).get();

    // 제목
    if (!title) {
        loginFailPop("공고 제목을 입력해주세요.");
        $('input[name="title"]').focus();
        return false;
    }

    // 접수기간
    if (!startDate || !endDate) {
        loginFailPop("접수 기간을 모두 입력해주세요.");
        $('input[name="startDate"]').focus();
        return false;
    }
    if (new Date(startDate) > new Date(endDate)) {
        loginFailPop("접수 시작일은 마감일보다 앞서야 합니다.");
        $('input[name="startDate"]').focus();
        return false;
    }

    // 경력
    if (!careerType) {
        loginFailPop("경력 사항을 선택해주세요.");
        $('select[name="careerType"]').focus();
        return false;
    }

    // 학력
    if (!education) {
        loginFailPop("학력을 선택해주세요.");
        $('select[name="education"]').focus();
        return false;
    }

    // 직군
    if (!jobGroupId) {
        loginFailPop("직군을 선택해주세요.");
        $('select[name="jobGroupId"]').focus();
        return false;
    }

    // 직무
    if (!jobId) {
        loginFailPop("직무를 선택해주세요.");
        $('select[name="jobId"]').focus();
        return false;
    }

    // 스킬
    if (selectedSkillIds.length === 0) {
        loginFailPop("스킬 태그를 최소 1개 이상 선택해주세요.");
        return false;
    }

    if (selectedSkillIds.length > 5) {
        loginFailPop("스킬은 최대 5개까지만 선택할 수 있습니다.");
        return false;
    }

    if (!salary) {
        loginFailPop("급여를 입력해주세요.");
        $('input[name="salary"]').focus();
        return false;
    }

    if (!/^\d+$/.test(salary)) {
        loginFailPop("급여는 숫자만 입력해주세요.");
        $('input[name="salary"]').focus();
        return false;
    }

    // 상세내용
    if (!content || content === "<p><br></p>") {
        loginFailPop("상세 내용을 입력해주세요.");
        window.editor.focus(); //전역 객체로 접근
        return false;
    }

    return true;
}


//로그인 실패 시 토스트 팝업
function loginFailPop(msg) {
    $('#askConfirm').html(msg);
    $('#askConfirm').attr('class', 'active');
    setTimeout(function() {
        $('#askConfirm').removeClass('active');
    }, 1500);
}

let welfareTags = [];

function updateWelfareInput() {
    $('input[name="welfareList"]').val(welfareTags.join('|'));
}

function addWelfareItem() {
    const value = $('#welfareInput').val().trim();
    if (!value || welfareTags.includes(value)) return;

    welfareTags.push(value);
    $('#welfareList').append(`
        <div class="flex-row welfare-item">
            <span>${value}</span>
            <button type="button" class="remove-welfare" data-val="${value}">X</button>
        </div>
    `);
    $('#welfareInput').val('');
    updateWelfareInput();
}

$('#addWelfareBtn').on('click', addWelfareItem);
$('#welfareInput').on('keypress', function (e) {
    if (e.key === 'Enter') {
        e.preventDefault();
        addWelfareItem();
    }
});

$(document).on('click', '.remove-welfare', function () {
    const val = $(this).data('val');
    welfareTags = welfareTags.filter(tag => tag !== val);
    $(this).closest('.welfare-item').remove();
    updateWelfareInput();
});

$('#updateForm').on('submit', function () {
    const markdown = editor.getHTML();
    $('#hiddenContent').val(markdown);

    // 유효성 검사 먼저 수행
    if (!validateFormInputs())
    {
        return false;
    }

    const selectedSkillIds = $('input[name="tagId"]:checked')
        .map(function () {
            return $(this).val();
        }).get().slice(0, 5);
    $('input[name="skills"]').val(selectedSkillIds.join(','));

    updateWelfareInput(); // 복리후생 hidden 처리
    if (!confirm("등록하시겠습니까?")) return false;      //컨펌창 수정예정//
    return true; // ✅ 모든 유효성 통과 시 전송
});


$(function () {
    $('#jobImgFile').on('change', function () {
        const file = this.files[0];
        if (file) {
            $('#fileNameText').text(file.name);
            $('#fileInfoBox').show();
        } else {
            $('#fileInfoBox').hide();
            $('#fileNameText').text('');
        }
    });

    $('#removeFileBtn').on('click', function () {
        $('#jobImgFile').val('');
        $('#fileInfoBox').hide();
        $('#fileNameText').text('');
    });
});
