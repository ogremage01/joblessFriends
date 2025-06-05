$(document).on('click', '.job', function(e) {
	const jobPostId = $(this).data('jobpostid');
	const companyId = $(this).data('companyid');
	window.location.href = `/Recruitment/detail?companyId=${companyId}&jobPostId=${jobPostId}`;
});


$(document).on('click', '.page-btn', function() {
    if ($(this).is(':disabled')) return;
    var page = $(this).data('page');
    var keyword = $(this).data('keyword') || '';
    location.href = '?page=' + page + '&keyword=' + encodeURIComponent(keyword);
});

