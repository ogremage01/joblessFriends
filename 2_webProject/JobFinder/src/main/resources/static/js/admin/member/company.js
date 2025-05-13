/**
 * 
 */


//------------------------------------기업 목록 JS

//기업 대량 탈퇴
const massDelComBtn = document.getElementById("massDelCom");

massDelComBtn.addEventListener("click", function(e) {
	const delCompanyArr = document.querySelectorAll(".delCompany:checked");
	const confirmed = confirm("삭제하시겠습니까?");
	
	if(confirmed&&delCompanyArr.length>0){
		
		const jsonData = [];
		
		delCompanyArr.forEach(cb=>{

			jsonData.push(cb.value);
		});
	
		console.log(jsonData);
		
		fetch('/admin/member/company/massDelete',{

			method: 'POST',
			headers: {
				'content-type': 'application/json'
			
			},
			body: JSON.stringify(jsonData)
	
		})
		.then(response => response.json())
		.then(data => {

			if(data===delCompanyArr.length){
			    console.log('Success:', data);
			    alert("삭제 완료");
			    location.reload();
			}else{

				alert("삭제 실패");
			    location.reload();
			
			}
		})
		.catch(error => {
		    console.error('Error:', error);
		    alert("삭제 실패");
		});
	
	}else{

		alert("선택된 기업이 없습니다.");
	}
});


//------------------------------------기업 상세 정보 JS

//기업 정보 수정
let companyInforSubmitFormObj = document.getElementById("companyInforSubmitForm");

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

    	fetch('/admin/member/company/detail', {
    	    method: 'POST',
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
				history.back();
    	    }else{
				alert("수정실패");
    	    }
    	})
    	.catch(error => {
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

    	fetch('/admin/member/company/detail/delete', {
    	    method: 'POST',
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
				history.back();
    	    }else{
				alert("수정실패");
    	    }
    	})
    	.catch(error => {
    	    console.error("에러 발생:", error);
    	});
	
	
	}
	
});

