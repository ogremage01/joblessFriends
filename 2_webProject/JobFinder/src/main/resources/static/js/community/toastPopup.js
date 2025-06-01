function askConfirm(check) {
	var Str = check + " 입력하세요."
	$('#askConfirm').html(Str)
	$('#askConfirm').attr('class', 'active');
	setTimeout(function() {
		$('#askConfirm').removeClass("active");
	}, 1500);
}


function askConfirmMax(check) {
	var Str = check+"은 최대 300자까지 입력할 수 있습니다.";
	$('#askConfirm').html(Str)
	$('#askConfirm').attr('class', 'active');
	setTimeout(function() {
		$('#askConfirm').removeClass("active");
	}, 1500);
}

function askConfirmLogin() {
	var Str = "개인회원 전용 기능입니다. 개인 회원 전용으로 로그인해주세요.";
	$('#askConfirm').html(Str)
	$('#askConfirm').attr('class', 'active');
	setTimeout(function() {
		$('#askConfirm').removeClass("active");
	}, 1500);
}

function alermPopup(Str) {

	$('#askConfirm').html(Str)
	$('#askConfirm').attr('class', 'active');
	setTimeout(function() {
		$('#askConfirm').removeClass("active");
	}, 1500);
}

/*
if ($("#title").val().trim() === "") {
        		askConfirm("제목을");
        		return false;
        	}
        	if ($("#hiddenContent").val().trim() === "") {
        		askConfirm("내용을");
        		return false;
        	}*/