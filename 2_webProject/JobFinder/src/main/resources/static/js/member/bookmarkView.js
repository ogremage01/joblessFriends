$(document).on('click', '.job', function (e) {
    if ($(e.target).hasClass('deleteBookmark')) return;
	if ($(e.target).hasClass('apply-btn')) return;
    const jobPostId = $(this).data('jobpostid');
    const companyId = $(this).data('companyid');
    window.location.href = `/Recruitment/detail?companyId=${companyId}&jobPostId=${jobPostId}`;
});

$(document).on('click', '.apply-btn', function () {
    alert('처리예정입니다');
});

$(document).on('click', '.deleteBookmark', function(e){
	e.stopPropagation();
	confirm('찜을 취소하시겠습니까?');
});