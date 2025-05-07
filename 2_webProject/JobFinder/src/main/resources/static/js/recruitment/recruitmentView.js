// $('.dropdown-toggle').on('click', function () {
//     $(this).next('.dropdown-content').css('display', 'flex');
// });


$('.dropdown-toggle').on('click', function () {
    const $content = $(this).next('.dropdown-content');
    if ($content.css('display') === 'none') {
        $content.css('display', 'flex'); // flex
    } else {
        $content.css('display', 'none'); // 닫을 때는 none
    }
});

$(document).on('click', '.job-group', function() {
    const jobGroupId = $(this).data('code'); // 숫자 그대로 사용 (String() 필요없음)

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
            <input type="checkbox" name="job" value="${item.jobName}">
            ${item.jobName}
          </label>
        `;
                $("#jobList").append(html);
            });
        },
        error: function(error) {
            console.error('에러 발생:', error);
        }
    });
});
