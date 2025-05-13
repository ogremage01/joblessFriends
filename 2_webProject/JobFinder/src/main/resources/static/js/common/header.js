
var compServiceToggle = false;

function companyPopup() {

	companyServiceNav = !companyServiceNav;
	console.log("companyServiceNav:"+companyServiceNav);
	if(companyServiceNav){
		console.log("켜짐")
		$("#companyServiceNav").css("display","block");
	}else{
		console.log("꺼짐")
		$("#companyServiceNav").css("display","none");
	}
	
}