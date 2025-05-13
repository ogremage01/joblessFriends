
let companyInforSubmitFormObj = document.getElementById("companyInforSubmitForm");
const companyId = document.getElementById("companyData").dataset.companyId;

companyInforSubmitFormObj.addEventListener("submit", function(e) {
    e.preventDefault(); // 폼 제출 막기

    const formData = new FormData(companyInforSubmitFormObj);
    const jsonData = {};
    formData.forEach((value, key) => {
        if (value.trim() !== "") {
            jsonData[key] = value;
        }
    });
    
    	console.log(jsonData);

    	fetch(`/admin/member/company/${companyId}`, {
    	    method: 'PUT',
    	    headers: {
    	        'Content-Type': 'application/json'
    	    },
    	    body: JSON.stringify(jsonData)
    	})
    	.then(response => {
    	    if (!response.ok) {
    	        throw new Error("서버 오류: " + response.status);
    	    }
    	    return response.text();
    	})
    	.then(data => {
    	    if(data === "1"){
				alert("수정성공");
				location.href = "/admin/member/company";
    	    }else{
				alert("수정실패");
    	    }
    	})
    	.catch(error => {
    		alert("수정실패");
    	    console.error("에러 발생:", error);
    	});
});



//기업 강퇴
deleteBtn = document.getElementById("delete");

deleteBtn.addEventListener("click", function(e) {
	
	
	companyIdVal = document.getElementById("companyId").value;
	
	console.log(companyIdVal);
	const jsonData = {
		"companyId":companyIdVal				
	};
	
	const confirmed = confirm("강제탈퇴를 진행합니까?");
	if(confirmed){

    	fetch(`/admin/member/company/${companyId}`, {
    	    method: 'DELETE',
    	    headers: {
    	        'Content-Type': 'application/json'
    	    },
    	    body: JSON.stringify(jsonData)
    	})
    	.then(response => {
    	    if (!response.ok) {
    	        throw new Error("서버 오류: " + response.status);
    	    }
    	    return response.text();
    	})
    	.then(data => {
    	    if(data === "1"){
				alert("수정성공");
				location.href = "/admin/member/company";
    	    }else{
				alert("수정실패");
    	    }
    	})
    	.catch(error => {
    		alert("수정실패");
    	    console.error("에러 발생:", error);
    	});
	
	
	}
	
});