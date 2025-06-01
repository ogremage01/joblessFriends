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

	//찜 저장 및 찜 확인
	$(document).on('click', '.btn-NonBookmark' , function() {
		const jobPostId = $(this).data('jobpostid');
		const userType = $(this).data('usertype');
		let html = '';
		const url = "/member/bookmarkCheck";

		//jobPostId가 없을 경우 북마크 로직 실행 불가.
		if (!jobPostId) {
			alert("채용공고 ID를 찾을 수 없습니다.");
			console.log("채용공고ID :"+jobPostId);
			return;
		}
		console.log(userType);
		
		//개인 회원 아닐 시 북마크 로직 실행 불가.
		if(userType != 'member'){
			alert("북마크는 개인 회원 전용 기능입니다. 개인 회원으로 로그인 해주세요.");
			return;
		}

		//북마크 저장 로직
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
					alert("북마크 등록 중 오류가 발생했습니다.");
				}
			}
		});
	});
