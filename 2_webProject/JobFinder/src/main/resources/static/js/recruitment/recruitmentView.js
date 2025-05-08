// $('.dropdown-toggle').on('click', function () {
//     $(this).next('.dropdown-content').css('display', 'flex');
// });


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

// 아무곳이나 클릭시 직무직군 닫힘
$(document).on('click', function () {
    $('.dropdown-content').css('display', 'none'); //다른곳클릭시 동일한부분
});
//체크박스 클릭시 닫히는거 방지
$(document).on('click', '.job-list label', function(e) {
    e.stopPropagation();
});
//직군페이지 빈공간클릭시 닫힘방지
$(document).on('click', '.dropdown-content', function(e) {
    e.stopPropagation();
});


$(document).on('click', '.job-group', function(e) {
    const jobGroupId = $(this).data('code');
    const jobGroupName = $(this).data('name');
    e.stopPropagation();

    $(this).siblings().removeClass('selected'); //기존css


    $(this).addClass('selected');//클릭시 클래스추가 //

    $.ajax({
        url: '/Recruitment/searchJob',
        method: 'GET',
        data: { jobGroupId: jobGroupId }, // 숫자 그대로
        success: function(response) {
            console.log(response);
            $("#jobList").empty();  //ajax전 비우기 //
            response.forEach(function(item) {
                const html = `
          <label>
            <input class="chk" type="checkbox" name="job" value="${item.jobName}">
            ${item.jobName}
          </label>
        `;

                $("#jobList").append(html);

            });
            //직군명 searchjob에 넣기 /

        },
        error: function(error) {
            console.error('에러 발생:', error);
        }
    });
});

//직군리스트 검색리스트 생성
let searchSpanTag = $('.searchSpan');
let selectedJobs = [];

$('.chk:checked').each(function() {
    selectedJobs.push($(this).val());
});

// 스팬으로 추가
selectedJobs.forEach(function(job) {
    $('#searchJobGroup').append(`
        <span class="searchSpan" data-value="${jobName}">
            ${jobName}
        </span>
    `);
});

