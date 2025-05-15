
var compServiceToggle = false;

function companyPopup() {

	compServiceToggle = !compServiceToggle;
	if(compServiceToggle){
		$(".companyServiceNav").css("display","block");
	}else{
		$(".companyServiceNav").css("display","none");
	}
	
}