
var compServiceToggle = false;

function companyPopup() {

	companyServiceNav = !companyServiceNav;
	if(companyServiceNav){
		$("#companyServiceNav").css("display","block");
	}else{
		$("#companyServiceNav").css("display","none");
	}
	
}