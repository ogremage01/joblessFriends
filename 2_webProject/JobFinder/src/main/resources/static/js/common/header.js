
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