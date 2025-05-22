function askConfirm(check) {
	var Str = check + " 입력하세요."
	$('#askConfirm').html(Str)
	$('#askConfirm').attr('class', 'active');
	setTimeout(function() {
		$('#askConfirm').removeClass("active");
	}, 1500);
}