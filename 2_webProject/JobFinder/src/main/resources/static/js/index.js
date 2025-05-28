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

	
// ====  슬라이드 왼쪽 버튼 ==== 
$(".left").click(function(){
	console.log("왼쪽 클릭");
	
	// 조회순 이동길이
	var viewtranslateXWidth = defaltWidth*viewMoveCount;
	// 마감임박순 이동길이
	var EndtranslateXWidth = defaltWidth*EndDateMoveCount; 

	// 조회 순 카테고리
		// 클래스가 없으면 추가
		if(!$("#viewsList .slideWrap").hasClass('translateSlide')){
			$("#viewsList .slideWrap").addClass('translateSlide');
		}
		// 이동 실행
		if ($(this).hasClass('views')) {
			$("#viewsList .slideWrap").css({"transform": "translateX("+viewtranslateXWidth+"px)"})
			console.log("이동 후: "+viewMoveCount);
			viewMoveCount--;
		}
		// 위치 초기화
		if(viewMoveCount == 1 ){
			setTimeout(function(){
				viewMoveCount = 2;
				$("#viewsList .slideWrap").removeClass('translateSlide');
				$("#viewsList .slideWrap").css({"transform": "translateX(-"+defaltWidth*2+"px)"})
			},300)
		}
	
	
	
	
	// 조회 순
	if ($(this).hasClass('views')) {
		$("#viewsList .slideWrap").css({"transform": "translateX(0px)"})
	}
	// 마감임박 순
	else if ($(this).hasClass('endDate')) {
		$("#endDateList .slideWrap").css({"transform": "translateX(0px)"})
	}
	
});


// ==== 슬라이드 오른쪽 버튼==== 
$(".right").click(function(){
	
	// 조회순 이동길이
	var viewtranslateXWidth = defaltWidth*viewMoveCount;
	// 마감임박순 이동길이
	var EndtranslateXWidth = defaltWidth*EndDateMoveCount; 
	
	// 조회 순 카테고리
		// 클래스가 없으면 추가
		if(!$("#viewsList .slideWrap").hasClass('translateSlide')){
			$("#viewsList .slideWrap").addClass('translateSlide');
		}
		// 이동 실행
		if ($(this).hasClass('views')) {
			$("#viewsList .slideWrap").css({"transform": "translateX(-"+viewtranslateXWidth+"px)"})
			console.log("이동 후: "+viewMoveCount);
			viewMoveCount++;
		}
		// 위치 초기화
		if(viewMoveCount == 4 ){
			setTimeout(function(){
				viewMoveCount = 2;
				$("#viewsList .slideWrap").removeClass('translateSlide');
				$("#viewsList .slideWrap").css({"transform": "translateX(-"+defaltWidth+"px)"})
			},300)
		}
	
	// 마감임박 순 카테고리
		// 클래스가 없으면 추가
		if(!$("#endDateList .slideWrap").hasClass('translateSlide')){
			$("#endDateList .slideWrap").addClass('translateSlide');
		}
		// 이동 실행
		if ($(this).hasClass('endDate')) {
			$("#endDateList .slideWrap").css({"transform": "translateX(-"+EndtranslateXWidth+"px)"})
			console.log("이동 후: "+EndDateMoveCount);
			EndDateMoveCount++;
		}
		// 위치 초기화
		if(EndDateMoveCount == 4 ){
			setTimeout(function(){
				EndDateMoveCount = 2;
				$("#endDateList .slideWrap").removeClass('translateSlide');
				$("#endDateList .slideWrap").css({"transform": "translateX(-"+defaltWidth+"px)"})
			},300)
		}
		
});