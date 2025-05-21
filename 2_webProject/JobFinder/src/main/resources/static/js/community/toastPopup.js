//로그인 실패 시 토스트 팝업
function loginFailPop(msg) {
	$('#askConfirm').html(msg);
	$('#askConfirm').attr('class', 'active');
	setTimeout(function() {
		$('#askConfirm').removeClass('active');
	}, 1500);
}
