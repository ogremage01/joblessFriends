// 이력서 미리보기 팝업창 열기
$(".btn-preview").click(function(){
	
	//window.open('문서 주소', '윈도우 이름', '옵션=값, 옵션=값, 옵션=값, …');
	
	var url = "/resume/preview";
	var windowName = "resumePreview"
	
	// 팝업 가로 사이즈
	const popupWidth = 980;
	// 팝업 세로사이즈 : 스크린 높이 사이즈 가져옴
	const screenHeight = window.screen.availHeight;
	
	// 위치 가로 중앙 값 계산
	const screenLeft = window.screenLeft !== undefined ? window.screenLeft : screen.left;
	const screenWidth = window.innerWidth || document.documentElement.clientWidth || screen.width;
	const left = screenLeft + (screenWidth - popupWidth) / 2;


	var popupOption = `width=${popupWidth}, height=${screenHeight},left=${left}`;
	
	// 팝업 열기	
	var preview = window.open(url, windowName, popupOption);
	
	/*
	// 자식 창에 데이터 보내기
	preview.onload = function() {
	    var data = {
	        name: $("#name").val(),
	        age: $("#age").val()
	    };
		
		//postMessage()는 한 창에서 다른 창으로 데이터를 보낼 때 사용하는 메서드
	    preview.postMessage(data, url);
	};
	*/
	
});