/**
 * 
 */


//------------------------------------기업 목록 JS

//기업 검색

const searchComBtn = document.getElementById("companySearchBtn");

searchComBtn.addEventListener("click", function(e){
    const companyKeywordVal = document.getElementById("companyKeyword").value;
    
    if(companyKeywordVal!=null||companyKeywordVal!=""){
        
        location.href=`/admin/member/company?keyword=${companyKeywordVal}`
        
    }
    
    
    
    
});



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
        
        fetch('/admin/member/company/',{

            method: 'DELETE',
            headers: {
                'content-type': 'application/json'
            
            },
            body: JSON.stringify(jsonData)  // JSON.stringify를 통해 body에 데이터 포함
    
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



