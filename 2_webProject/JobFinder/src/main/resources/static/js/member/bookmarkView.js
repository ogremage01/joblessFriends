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
	const jobPostId = $(this).data('jobpostid');
	if (!window.confirm('찜을 취소하시겠습니까?')) {
		return;
	}
	fetch("/member/bookmark",{
		method: "DELETE",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(jobPostId)
	})
	.then(response => {
		if(!response.ok){
			throw new Error("서버 오류: " + response.status);
		}
		return response.text();
	})	
	.then(data=>{
		if(data == "찜삭제"){
			alert("찜 취소가 완료되었습니다.");
			location.reload();
		}else{
			alert("실패")
		}
	})
	.catch(error => {
	        alert("삭제 실패");
	        console.error("에러 발생:", error);
	    });
});


function goToPage(page, keyWord){
	$('#pageInput').val(page);
	$('#keywordInput').val(keyWord);
	$('#pagingForm').submit();
	

}