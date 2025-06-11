
var compServiceToggle = false;

$('.corpNav').on('click', function(e){
	
	e.stopPropagation();
	
	compServiceToggle = !compServiceToggle;
	
	if(compServiceToggle){
		$(".companyServiceNav").css("display","block");
	}else{
		$(".companyServiceNav").css("display","none");
	}
	
});

//외부 클릭 시 닫기
$(document).on('click', function () {
    $('.companyServiceNav').css('display', 'none');
});


$(function() {
  $('#searchForm').on('submit', function(e) {
    // 입력값 앞뒤 공백 제거 후 체크
    var keyword = $('#keyword').val().trim();
    if (keyword === '' || keyword === null) {
      // 폼 제출(백엔드 요청) 막기
      e.preventDefault();
	  
	  $('#keyword').focus();
      // 프론트 변화 없음 (경고창 등도 띄우지 않음)
      return;
    }
    // 입력값이 있으면 폼 제출 허용 (기존 동작)
  });
});