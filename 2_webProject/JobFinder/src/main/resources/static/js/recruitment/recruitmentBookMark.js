/**
 * 
 */


	//비동기 사용

	//북마크 이미 체크 안된 상태일 경우, 등록.
	//	$('.btn-bookmark')

	//북마크 이미 체크된 상태일 경우, 삭제.
	//	if(){

	//		ajax

	//	}

	$(document).on('click', '.btn-NonBookmark' , function() {
		const jobPostId = $(this).data('jobpostid');
		let html = '';
		const url = "/member/bookmarkCheck";

		if (!jobPostId) {
			alert("채용공고 ID를 찾을 수 없습니다.");
			console.log("채용공고ID :"+jobPostId);
			return;
		}

		$.ajax({
			url: url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(jobPostId),
			success: function(response) {
				alert(response); // 예: "찜저장"
				// 버튼 모양 변경 등 UI 반응 추가 가능
				// 예: $(this).text("★ 찜 완료");
				html = `<button class="btn-bookmark" data-jobPostId="${jobPostId}">
			   				★ 공고 찜하기
						</button>`;


				$("#bookmark-Container").html(html);
			},
			error: function(xhr) {
				if (xhr.status === 401) {
					alert("로그인이 필요합니다.");
				} else {
					alert("찜 등록 중 오류가 발생했습니다.");
				}
			}
		});
	});
