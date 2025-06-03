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
		bookMarkPop("채용공고 ID를 찾을 수 없습니다.");
		console.log("채용공고ID :"+jobPostId);
		return;
	}
	console.log(userType);
	
	//개인 회원 아닐 시 북마크 로직 실행 불가.
	if(userType != 'member'){
		bookMarkPop("찜 기능은 개인 회원 전용 기능입니다. 개인 회원으로 로그인 해주세요.");
		return;
	}

	//북마크 저장 로직
	$.ajax({
		url: url,
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(jobPostId),
		success: function(response) {
			bookMarkPop("찜 하기가 완료되었습니다."); // 예: "찜저장"
			// 버튼 모양 변경 등 UI 반응 추가 가능
			// 예: $(this).text("★ 찜 완료");
			html = `<button class="btn-bookmark" data-jobPostId="${jobPostId}" data-usertype='${userType}'>
		   				★ 공고 찜하기
					</button>`;


			$("#bookmark-Container").html(html);
		},
		error: function(xhr) {
			if (xhr.status === 401) {
				bookMarkPop("로그인이 필요합니다.");
			} else {
				bookMarkPop("찜 등록 중 오류가 발생했습니다.");
			}
		}
	});
});

//찜 삭제 로직: .btn-bookmark 북마크 존재 클래스
$(document).on('click', '.btn-bookmark', function() {
	const jobPostId = $(this).data('jobpostid');
	const userType = $(this).data('usertype');
	let html = '';
	const url = "/member/bookmark";

	//jobPostId가 없을 경우 북마크 로직 실행 불가.
	if (!jobPostId) {
		bookMarkPop("채용공고 ID를 찾을 수 없습니다.");
		console.log("채용공고ID :" + jobPostId);
		return;
	}
	console.log(userType);

	//개인 회원 아닐 시 북마크 로직 실행 불가.
	if (userType != 'member') {
		bookMarkPop("북마크는 개인 회원 전용 기능입니다. 개인 회원으로 로그인 해주세요.");
		return;
	}

	
		//북마크 삭제 로직
		$.ajax({
			url: url,
			method: 'DELETE',
			contentType: 'application/json',
			data: JSON.stringify(jobPostId),
			success: function(response) {
				bookMarkPop("찜 하기가 취소되었습니다."); // 예: "찜저장"
				// 버튼 모양 변경 등 UI 반응 추가 가능
				// 예: $(this).text("★ 찜 완료");
				html = `<button class='btn-NonBookmark' data-jobPostId="${jobPostId}" data-usertype='${userType}'>
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

function bookMarkPop(msg) {
    $('#askConfirm').html(msg);
    $('#askConfirm').attr('class', 'active');
    setTimeout(function() {
        $('#askConfirm').removeClass('active');
    }, 1500);
}


//리스트쪽 찜 저장
$(document).on('click', '.noStar', function (e) {
	e.preventDefault(); // 기본 동작 막기
	e.stopPropagation(); // 이벤트 버블링 방지 (선택사항)
    const jobPostId = $(this).closest('.job').data('jobpostid');
	const userType = $(this).data('usertype');
	let html = '';
	const url = "/member/bookmarkCheck";
		
	//jobPostId가 없을 경우 북마크 로직 실행 불가.
		if (!jobPostId) {
			bookMarkPop("채용공고 ID를 찾을 수 없습니다.");
			console.log("채용공고ID :"+jobPostId);
			return;
		}
		console.log(userType);
		
		//개인 회원 아닐 시 북마크 로직 실행 불가.
		if(userType != 'member'){
			bookMarkPop("찜 기능은 개인 회원 전용 기능입니다. 개인 회원으로 로그인 해주세요.");
			return;
		}
	
	$.ajax({
		url: url,
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(jobPostId),
		success: function(response) {
			bookMarkPop("찜 하기가 완료되었습니다."); // 예: "찜저장"
			// 버튼 모양 변경 등 UI 반응 추가 가능
			// 예: $(this).text("★ 찜 완료");
			html = `<button class="star" type="button" data-usertype="${userType}">★</button>`;


			let bookmarkid="#bookmarkList-Container_"+jobPostId;
			$(bookmarkid).html(html);
		},
		error: function(xhr) {
			if (xhr.status === 401) {
				bookMarkPop("로그인이 필요합니다.");
			} else {
				bookMarkPop("찜 등록 중 오류가 발생했습니다.");
			}
		}
	});

	console.log("슻타:"+jobPostId);
});



//리스트 쪽 찜 삭제
$(document).on('click', '.star', function (e) {
	e.preventDefault(); // 기본 동작 막기
	e.stopPropagation(); // 이벤트 버블링 방지 (선택사항)
    const jobPostId = $(this).closest('.job').data('jobpostid');
	const userType = $(this).data('usertype');
	let html = '';
	const url = "/member/bookmark";
	
	//jobPostId가 없을 경우 북마크 로직 실행 불가.
		if (!jobPostId) {
			bookMarkPop("채용공고 ID를 찾을 수 없습니다.");
			console.log("채용공고ID :"+jobPostId);
			return;
		}
		console.log(userType);
		
		//개인 회원 아닐 시 북마크 로직 실행 불가.
		if(userType != 'member'){
			bookMarkPop("찜 기능은 개인 회원 전용 기능입니다. 개인 회원으로 로그인 해주세요.");
			return;
		}
	
	//북마크 삭제 로직
	$.ajax({
		url: url,
		method: 'DELETE',
		contentType: 'application/json',
		data: JSON.stringify(jobPostId),
		success: function(response) {
			bookMarkPop("찜 하기가 취소되었습니다."); 
			// 버튼 모양 변경 등 UI 반응 추가 가능
			// 예: $(this).text("★ 찜 완료");
			html = `<button class="noStar" type="button" data-usertype="${userType}">☆</button>`;
			

			let bookmarkid="#bookmarkList-Container_"+jobPostId;
			$(bookmarkid).html(html);
		},
		error: function(xhr) {
			if (xhr.status === 401) {
				alert("로그인이 필요합니다.");
			} else {
				alert("찜 등록 중 오류가 발생했습니다.");
			}
		}
	});

	console.log("슻타:"+jobPostId);
});



	
	
	
