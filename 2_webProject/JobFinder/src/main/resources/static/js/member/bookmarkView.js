$(document).on('click', '.job', function(e) {
	if ($(e.target).hasClass('deleteBookmark')) return;
	if ($(e.target).hasClass('apply-btn')) return;
	const jobPostId = $(this).data('jobpostid');
	const companyId = $(this).data('companyid');
	window.location.href = `/Recruitment/detail?companyId=${companyId}&jobPostId=${jobPostId}`;
});

$(document).on('click', '.apply-btn', function() {
	alert('처리예정입니다');
});

$(document).on('click', '.deleteBookmark', function(e) {
	e.stopPropagation();
	const jobPostId = $(this).data('jobpostid');
	cancelBookmark(jobPostId);

});


function goToPage(page, keyWord) {
	$('#pageInput').val(page);
	$('#keywordInput').val(keyWord);
	$('#pagingForm').submit();


}

function cancelBookmark(jobPostId) {
	Swal.fire({
		title: "찜 삭제",
		text: "정말로 찜을 삭제하시겠습니까?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: '찜 삭제',
	    cancelButtonText: '취소',
		customClass: {
			confirmButton: "swalConfirmBtn",
			cancelButton: "swalCancelBtn",
		},
		reverseButtons: true, // 버튼 순서 거꾸로
		
	}).then((result) => {
		if (result.isConfirmed) {
			// 찜 취소 요청
			fetch("/member/bookmark", {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(jobPostId)
			})
				.then(response => {
					if (!response.ok) {
						throw new Error("서버 오류: " + response.status);
					}
					return response.text();
				})
				.then(data => {
					if (data == "찜삭제") {
						Swal.fire({
						  title: "취소 완료",
						  text: "찜이 정상적으로 취소되었습니다",
						  icon: "success",
						  timer: 1500,
						  showConfirmButton: false
						}).then(() => {
						  location.reload();
						});
					} else {
						Swal.fire({
						  icon: "error",
						  title: "삭제가 실패했습니다",
						  text: "잠시 후 다시 시도해 주세요.",
						  confirmButtonText: '확인',
				          customClass: {
				              confirmButton: 'swalConfirmBtn'
				          }
						});
					}
				})
				.catch(error => {
					Swal.fire({
					  icon: "error",
					  title: "Oops...",
					  text: "서버와의 통신에 실패했습니다.",
					  confirmButtonText: '확인',
			          customClass: {
			              confirmButton: 'swalConfirmBtn'
			          }
					});
				});

		}
	});

}