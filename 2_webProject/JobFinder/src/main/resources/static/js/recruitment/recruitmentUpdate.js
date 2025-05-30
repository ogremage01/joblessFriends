$(document).ready(function () {
    //복리후생 코드 //
    syncWelfareTagsFromDOM();
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



//미리보기//
$("#generateTemplate").on('click',function () {

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
    const welfareHtml = $(".welfare-item > span")
        .map(function () {
            return `<li>${$(this).text().trim()}</li>`;
        })
        .get()
        .join('');

    const selectedTags = $('input[name="tagId"]:checked')
        .map(function () {
            return $(this).val(); // tagId만 수집
        })
        .get()
        .slice(0, 5);

// tagId → tagName 매핑용
    const tagMap = {};
    $('#tag-list input[name="tagId"]').each(function () {
        const tagId = $(this).val();
        const tagName = $(this).parent().text().trim();
        tagMap[tagId] = tagName;
    });

    const tagHtml = selectedTags.map(id => `<span class="tag">${tagMap[id]}</span>`).join('');

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

//질문 모달 js //
$('#btnAddQuestion').on('click', function () {
    Swal.fire({
        title: '사전질문 수정하기',
        html: `
            <div class="question-modal-form">
                <div class="swal2-form-group">
                    <label for="question1" class="swal2-form-label">질문 1</label>
                    <input id="question1" class="swal2-input" placeholder="예: 지원 동기를 말씀해주세요" />
                </div>
                <div class="swal2-form-group">
                    <label for="question2" class="swal2-form-label">질문 2</label>
                    <input id="question2" class="swal2-input" placeholder="예: 자신의 강점은 무엇인가요?" />
                </div>
                <div class="swal2-form-group">
                    <label for="question3" class="swal2-form-label">질문 3</label>
                    <input id="question3" class="swal2-input" placeholder="예: 입사 후 포부를 알려주세요" />
                </div>
            </div>
        `,
        showCancelButton: true,
        confirmButtonText: '질문 수정',
        cancelButtonText: '취소',
        customClass: {
            confirmButton: 'swal2-confirm swal2-styled swal2-blue-button',
            cancelButton: 'swal2-cancel swal2-styled swal2-gray-button'
        },
        preConfirm: () => {
            return {
                q1: $('#question1').val()?.trim(),
                q2: $('#question2').val()?.trim(),
                q3: $('#question3').val()?.trim()
            };
        }
    }).then((result) => {
        if (result.isConfirmed) {
            const { q1, q2, q3 } = result.value;

            // 리스트 출력
            const $list = $('ul.Question');
            $list.empty();
            if (q1) $list.append(`<li>${q1}</li>`);
            if (q2) $list.append(`<li>${q2}</li>`);
            if (q3) $list.append(`<li>${q3}</li>`);

            // form에 hidden input 삽입 (중복 방지 후 추가)
            const form = $('#updateForm'); // ← 실제 form id 사용
            form.find('input[name="question1"]').remove();
            form.find('input[name="question2"]').remove();
            form.find('input[name="question3"]').remove();

            form.append(`<input type="hidden" name="question1" value="${q1}"/>`);
            form.append(`<input type="hidden" name="question2" value="${q2}"/>`);
            form.append(`<input type="hidden" name="question3" value="${q3}"/>`);
        }
    });
});


function validateFormInputs() {
    if (typeof editor === 'undefined' || !editor) {
        alert("에디터 초기화가 아직 안 됐습니다!");
        return false;
    }
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


//
// function updateWelfareInput() {
//     const tags = [];
//
//     $('#welfareList .welfare-item span').each(function () {
//         const text = $(this).text().trim();
//         const isValid = text && !text.includes(',') && /^[가-힣a-zA-Z0-9\s]+$/.test(text);
//         if (isValid && !tags.includes(text)) {
//             tags.push(text);
//         }
//     });
//     console.log(tags);
//     $('input[name="welfareList"]').val(tags.length ? tags.join('|') : '');
// }

function updateWelfareInput() {
    const tags = [];

    $('#welfareList .welfare-item span').each(function () {
        const text = $(this).text().trim();
        const isValid = text && !text.includes(',') && /^[가-힣a-zA-Z0-9\s]+$/.test(text);
        if (isValid && !tags.includes(text)) {
            tags.push(text);
        }
    });
    console.log(tags);
    $('input[name="welfareList"]').val(tags.length ? tags.join('|') : '');
}




function syncWelfareTagsFromDOM() {
    const seen = new Set();
    welfareTags = [];

    $('#welfareList .welfare-item span').each(function () {
        const text = $(this).text().trim();

        // ✅ 유효하지 않으면 return
        const isValid = text && !text.includes(',') && /^[가-힣a-zA-Z0-9\s]+$/.test(text);

        if (isValid && !seen.has(text)) {
            seen.add(text);
            welfareTags.push(text);
        }
    });

    // ✅ 빈 배열이면 input 비움
    if (welfareTags.length === 0) {
        $('input[name="welfareList"]').val('');
    } else {
        updateWelfareInput();
    }
}





function addWelfareItem() {
    const value = $('#welfareInput').val().trim();

    let isDuplicate = false;
    $('#welfareList .welfare-item span').each(function () {
        if ($(this).text().trim() === value) {
            isDuplicate = true;
        }
    });
    if (isDuplicate) return;

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

//복리후생 x표시처리업데이트 //
$(document).on('click', '.remove-welfare', function () {
    $(this).closest('.welfare-item').remove(); // DOM에서 삭제
    syncWelfareTagsFromDOM(); // ✅ DOM 기반으로 welfareTags 다시 구성
});

// submit 전에 무조건 한 번 더 동기화
$('#updateForm').on('submit', function (e) {
    e.preventDefault();


    const markdown = editor.getHTML();
    $('#hiddenContent').val(markdown);

    if (!validateFormInputs()) return;

    const selectedSkillIds = $('input[name="tagId"]:checked')
        .map(function () { return $(this).val(); })
        .get()
        .slice(0, 5);
    $('input[name="skills"]').val(selectedSkillIds.join(','));
    console.log($('#welfareList').val());
    Swal.fire({
        title: '수정하시겠습니까?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: '확인',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            e.target.submit();
        }
    });
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

$(document).ready(function () {
    $(".cancel-btn").on("click", function () {
        Swal.fire({
            title: '작성을 취소하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: '확인',
            cancelButtonText: '닫기'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = "/company/recruitment";
            }
        });
    });



});