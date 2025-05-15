$(document).ready(function () {
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
    if (!validateFormInputs()) return;
    let titleValue = $(".InsertTitle > input").val();

    let openEnded = $('.InsertDate > input[name="openEnded"]').is(':checked');
    let careerTypeValue = $("select[name='careerType']").val();
    let educationValue = $("select[name='education']").val();
    let jobGroupIdValue = $("select[name='jobGroupId']").val();
    let workHoursValue = $("select[name='workHours']").val();
    let salaryValue = $(".InsertJob  input[name='salary']").val();
    let contentValue = editor.getHTML();

    let startDate = $('.InsertDate input[name="startDate"]').val();
    let endDate = $('.InsertDate input[name="endDate"]').val();
    const selectedTags = $('input[name="tagId"]:checked')
        .map(function () {
            return $(this).parent().text().trim();
        })
        .get()
        .slice(0, 5); //스킬리스트수집
    console.log({ startDate, endDate, salaryValue }); // 디버깅
    const tagHtml = selectedTags.map(tag => `<span class="tag">${tag}</span>`).join('');

    const html = `
<link rel="stylesheet" href="/css/recruitment/recruitmentDetail.css" />
<div class="detail-wrapper">

    <!-- 왼쪽 본문 -->
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

    <!-- 오른쪽 사이드 영역 -->
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

        <!-- 스킬 -->
        <section class="detail-section">
            <h3>요구 스킬</h3>
            <div class="tag-list">
                ${tagHtml}
            </div>
        </section>

        <!-- 상세 내용 -->
        <section class="detail-section">
            <h3>상세 내용</h3>
            <div>${contentValue}</div>
            
                <ul>
                        <li>정규직 / 주 5일 근무 / 유연 출퇴근제</li>
                        <li>우대사항: JPA 실무 경험, 대용량 트래픽 서비스 운영 경험</li>
                        <li>복지: 사내 피트니스, 카페, 연 100만원 포인트 제공   수정예정</li>
                   </ul>
        </section>

        <!-- 전형 절차, 제출 서류 등은 필요에 따라 하드코딩 or 조건부 생성 -->
    </div>
</div>
           <!-- 2. 상세 내용 -->
         

                <!-- 3. 전형 절차 -->
                <section class="detail-section">
                    <h3>전형 절차</h3>
                    <ol class="step-list">
                        <li>서류 전형</li>
                        <li>실무 면접</li>
                        <li>임원 면접</li>
                        <li>최종 합격</li>
                    </ol>
                </section>

                <!-- 4. 제출 서류 -->
                <section class="detail-section">
                    <h3>제출 서류</h3>
                    <ul>
                        <li>이력서</li>
                        <li>자기소개서 (자유 양식)</li>
                    </ul>
                </section>

                <!-- 5. 기업 정보 -->
                <section class="detail-section">
                    <h3>기업 정보</h3>
                    <ul>
                        <li><strong>기업명:</strong>  기업</li>
                        <li><strong>담당자명:</strong> 담당자</li>
                        <li><strong>연락처:</strong> 02-805-2311</li>
                        <li><strong>주소:</strong> 서울 금천구 시흥동 </li>
                    </ul>
                </section>

`;

    $("#templatePreview").html(html);

})



function validateFormInputs() {
    let titleValue = $(".InsertTitle > input").val();
    let openEnded = $('.InsertDate input[name="openEnded"]').is(':checked');
    let careerTypeValue = $("select[name='careerType']").val();
    let educationValue = $("select[name='education']").val();
    let jobGroupIdValue = $("select[name='jobGroupId']").val();
    let workHoursValue = $("select[name='workHours']").val();
    let salaryValue = $(".InsertJob input[name='salary']").val();
    let contentValue = editor.getHTML();
    let startDate = $('.InsertDate input[name="startDate"]').val();
    let endDate = $('.InsertDate input[name="endDate"]').val();
    let skillValue = $('.InsertDate input[name="tagId"]').is(':checked');
    if (!titleValue) {
        $(".InsertTitle > input").focus();
        loginFailPop("제목을 입력해주세요.");
        return false;
    }

    if (!startDate || !endDate) {
        $('.InsertDate input[name="startDate"]').focus();
        loginFailPop("접수 기간을 입력해주세요.");
        return false;
    }

    if (new Date(startDate) > new Date(endDate)) {
        $('.InsertDate input[name="startDate"]').focus();
        loginFailPop("접수 시작일은 마감일보다 앞서야 합니다.");
        return false;
    }

    if (!salaryValue || isNaN(salaryValue)) {
        $(".InsertJob input[name='salary']").focus();
        loginFailPop("급여는 숫자로 입력해주세요.");

        return false;
    }

    if (!contentValue || contentValue === '<p><br></p>') {
        $(".ProseMirror").focus();
        loginFailPop("상세 내용을 입력해주세요.");
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

