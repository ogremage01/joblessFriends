// top버튼 애니메이션
$(".topBtn").click(function() {
	$("html, body").animate({ scrollTop: 0 }, 500);
	return false;
})

// ==== 무한 슬라이드용 복제 요소 ====
// 조회순 
$('#viewsList .jobInfoItem').slice(0, 4).each(function() {
	var cloned = $(this).clone();
	cloned.addClass('cloned-box'); // 복제본 구분용 클래스
	$('#viewsList .slideWrap').append(cloned);
});
// 주의: 순서 역전 방지하려면 역순으로 prepend 해야 함
$($('#viewsList .jobInfoItem').slice(4, 8).get().reverse()).each(function() {
	var cloned = $(this).clone();
	cloned.addClass('cloned-box');
	$('#viewsList .slideWrap').prepend(cloned);
});

// 마감임박순
$('#endDateList .jobInfoItem').slice(0, 4).each(function() {
	var cloned = $(this).clone();
	cloned.addClass('cloned-box'); // 복제본 구분용 클래스
	$('#endDateList .slideWrap').append(cloned);
});
// 주의: 순서 역전 방지하려면 역순으로 prepend 해야 함
$($('#endDateList .jobInfoItem').slice(4, 8).get().reverse()).each(function() {
	var cloned = $(this).clone();
	cloned.addClass('cloned-box');
	$('#endDateList .slideWrap').prepend(cloned);
});

// ==== 슬라이드 이동을 위한 길이 계산 ==== 
//슬라이드 한 줄 사이즈
var viewWidth = $('.jobInfoContent').width();
//flex gap 사이즈
var columnGap = $(".slideWrap").css("column-gap").slice(0, -2);
//이동길이 초기값
var defaltWidth = Number(viewWidth) + Number(columnGap); // 1240

// ==== 이동 카운트 ==== 
// 조회순
var viewMoveCount = 2;
// 마감임박순
var EndDateMoveCount = 2;

// 순서
//둘째복사1 초기 페이지2 둘째페이지3 초기복사4
// 이동 값
// 1:0px 2: -defult 3: -defult*2 4: -defult*3

// ====  슬라이드 왼쪽 버튼 ==== 
$(".left").click(function() {

	// 조회 순 카테고리
	// 클래스가 없으면 추가
	if (!$("#viewsList .slideWrap").hasClass('translateSlide')) {
		$("#viewsList .slideWrap").addClass('translateSlide');
	}
	// 이동 실행
	if ($(this).hasClass('views')) {
		if (viewMoveCount == 2) {
			$("#viewsList .slideWrap").css({ "transform": "translateX(0px)" });
			--viewMoveCount;
		}
		else if (viewMoveCount == 3) {
			$("#viewsList .slideWrap").css({ "transform": "translateX(-" + defaltWidth + "px)" });
			--viewMoveCount;
		}
		// 위치 초기화 : 위치 1로가면 3으로 초기화
		if (viewMoveCount == 1) {
			setTimeout(function() {
				$("#viewsList .slideWrap").removeClass('translateSlide');
				$("#viewsList .slideWrap").css({ "transform": "translateX(-" + defaltWidth * 2 + "px)" })
				viewMoveCount = 3;
			}, 300)
		}
	}

	// 마감임박 순 카테고리
	// 클래스가 없으면 추가
	if (!$("#endDateList .slideWrap").hasClass('translateSlide')) {
		$("#endDateList .slideWrap").addClass('translateSlide');
	}
	// 이동 실행
	if ($(this).hasClass('endDate')) {
		if (EndDateMoveCount == 2) {
			$("#endDateList .slideWrap").css({ "transform": "translateX(0px)" });
			--EndDateMoveCount;
		}
		else if (EndDateMoveCount == 3) {
			$("#endDateList .slideWrap").css({ "transform": "translateX(-" + defaltWidth + "px)" });
			--EndDateMoveCount;
		}
		// 위치 초기화 : 위치 1로가면 3으로 초기화
		if (EndDateMoveCount == 1) {
			setTimeout(function() {
				$("#endDateList .slideWrap").removeClass('translateSlide');
				$("#endDateList .slideWrap").css({ "transform": "translateX(-" + defaltWidth * 2 + "px)" })
				EndDateMoveCount = 3;
			}, 300)
		}
	}

});


// ==== 슬라이드 오른쪽 버튼==== 
$(".right").click(function() {

	// 조회순 이동길이
	var viewtranslateXWidth = defaltWidth * viewMoveCount;
	// 마감임박순 이동길이
	var EndtranslateXWidth = defaltWidth * EndDateMoveCount;

	// 조회 순 카테고리
	// 클래스가 없으면 추가
	if (!$("#viewsList .slideWrap").hasClass('translateSlide')) {
		$("#viewsList .slideWrap").addClass('translateSlide');
	}
	// 이동 실행
	if ($(this).hasClass('views')) {
		$("#viewsList .slideWrap").css({ "transform": "translateX(-" + viewtranslateXWidth + "px)" })
		viewMoveCount++;
	}
	// 위치 초기화 : 위치 4로가면 2로 초기화
	if (viewMoveCount == 4) {
		setTimeout(function() {
			viewMoveCount = 2;
			$("#viewsList .slideWrap").removeClass('translateSlide');
			$("#viewsList .slideWrap").css({ "transform": "translateX(-" + defaltWidth + "px)" })
		}, 300)
	}

	// 마감임박 순 카테고리
	// 클래스가 없으면 추가
	if (!$("#endDateList .slideWrap").hasClass('translateSlide')) {
		$("#endDateList .slideWrap").addClass('translateSlide');
	}
	// 이동 실행
	if ($(this).hasClass('endDate')) {
		$("#endDateList .slideWrap").css({ "transform": "translateX(-" + EndtranslateXWidth + "px)" })
		EndDateMoveCount++;
	}
	// 위치 초기화
	if (EndDateMoveCount == 4) {
		setTimeout(function() {
			EndDateMoveCount = 2;
			$("#endDateList .slideWrap").removeClass('translateSlide');
			$("#endDateList .slideWrap").css({ "transform": "translateX(-" + defaltWidth + "px)" })
		}, 300)
	}
});


/*채팅 이벤트 관련*/
function openChat(event) {
	event.preventDefault();

	var loginType = event.currentTarget.dataset.userType;


	var chatUrl = "";
	var popupWidth = 450;  // 약 12cm
	var popupHeight = 800; // 약 21cm
	var left = (window.innerWidth - popupWidth) / 2;
	var top = (window.innerHeight - popupHeight) / 2;

	switch (loginType) {
		case "member":
			chatUrl = "member/chat/room";
			break;
		case "company":
			chatUrl = "company/chat/room";
			break;
		default:
			showLoginAlert();
			return;
	}

	var popupOptions = "width=" + popupWidth +
		",height=" + popupHeight +
		",left=" + left +
		",top=" + top +
		",scrollbars=yes" +
		",resizable=yes" +
		",status=no" +
		",location=no" +
		",toolbar=no" +
		",menubar=no";

	window.open(chatUrl, "채팅", popupOptions);
}

// 안읽은 메시지 확인 함수
function checkUnreadMessages() {
	var loginType = "${sessionScope.userType}";
	var isLoggedIn = "${not empty sessionScope.userLogin}";

	// 로그인하지 않았거나 관리자인 경우 확인하지 않음
	if (isLoggedIn === "false" || loginType === "admin") {
		return;
	}

	var apiUrl = "";
	switch (loginType) {
		case "member":
			apiUrl = "/member/chat/unread-count";
			break;
		case "company":
			apiUrl = "/company/chat/unread-count";
			break;
		default:
			return;
	}

	fetch(apiUrl, {
		method: 'GET',
		credentials: 'same-origin'
	})
		.then(response => {
			if (response.ok) {
				return response.json();
			}
			throw new Error('Network response was not ok');
		})
		.then(unreadCount => {
			updateChatIcon(unreadCount);
		})
		.catch(error => {
			console.log('안읽은 메시지 확인 중 오류:', error);
		});
}

// 채팅 아이콘 업데이트 함수
function updateChatIcon(unreadCount) {
	var chatBtn = document.querySelector('.chat-floating-btn');
	var existingBadge = chatBtn.querySelector('.unread-badge');

	if (unreadCount > 0) {
		// 안읽은 메시지가 있으면 느낌표 배지 표시
		if (!existingBadge) {
			var badge = document.createElement('div');
			badge.className = 'unread-badge';
			badge.innerHTML = '!';
			chatBtn.appendChild(badge);
		}
	} else {
		// 안읽은 메시지가 없으면 배지 제거
		if (existingBadge) {
			existingBadge.remove();
		}
	}
}

// 로그인 안한 사용자일 경우 알림창 표시

function showLoginAlert() {
	Swal.fire({
		icon: "error",
		title: "죄송합니다.",
		text: "로그인 후 이용해주세요.",
		footer: '<a href="/auth/login">지금 로그인하시겠어요?</a>',
		confirmButtonText: '확인',
		customClass: {
			confirmButton: "swalConfirmBtn",
		},
	});
	

}
// 페이지 로드 시 초기 확인 및 주기적 확인 설정
document.addEventListener('DOMContentLoaded', function() {
	var loginType = "${sessionScope.userType}";
	var isLoggedIn = "${not empty sessionScope.userLogin}";

	if (isLoggedIn === "true" && (loginType === "member" || loginType === "company")) {
		// 초기 확인
		checkUnreadMessages();

		// 30초마다 확인
		setInterval(checkUnreadMessages, 30000);
	}
});