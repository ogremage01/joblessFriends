$(document).on('click', '.job', function(e) {
	const jobPostId = $(this).data('jobpostid');
	const companyId = $(this).data('companyid');
	window.location.href = `/Recruitment/detail?companyId=${companyId}&jobPostId=${jobPostId}`;
});

$(document).ready(function() {
    $('.page-btn').click(function() {
        // 비활성화된 버튼은 동작하지 않게
        if ($(this).is(':disabled')) return;

        var page = $(this).data('page');
        var keyword = '${keyword}';
        // GET 방식으로 페이지 이동
        location.href = '?page=' + page + '&keyword=' + encodeURIComponent(keyword);
    });
});

function goToPage(page, keyWord){
		$('#pageInput').val(page);
		$('#keywordInput').val(keyWord);
		$('#pagingForm').submit();
		
		
/* 		.location.href='./community?page=${page}&keyword=${keyWord}'; */
	}