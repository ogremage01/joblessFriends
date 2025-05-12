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
            $('#jobList').empty();
            response.forEach(function (item) {
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

// 리스트 X버튼 누르면 삭제 및 체크 해제
function removeDetail(button) {
    const $li = $(button).closest('li');
    const jobId = $li.data('id');

    delete checkedJobs[jobId];
    $li.remove();
    $(`.chk[data-id='${jobId}']`).prop('checked', false);
}
