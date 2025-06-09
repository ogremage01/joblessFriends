document.addEventListener("DOMContentLoaded", () => {
	
	


	function deleteRecruitments(jobPostIds) {
	    if (!confirm("삭제를 진행합니까?"))
	    	return;
	    fetch("/company/recruitment", {
	        method: "DELETE",
	        headers: {
	            "Content-Type": "application/json"
	        },
	        body: JSON.stringify(jobPostIds) // 배열 전달
	    })
	    .then(response => {
	        if (!response.ok) {
	            throw new Error("서버 오류: " + response.status);
	        }
	        return response.text();
	    })
	    .then(data => {
	        if (data == "삭제완료") {
	            alert("공고가 성공적으로 삭제되었습니다.");
	            removeRecruitmentRows(jobPostIds);
	        } else {
	            alert("삭제 실패: 서버 응답 오류");
	        }
	    })
	    .catch(error => {
	        alert("삭제 실패");
	        console.error("에러 발생:", error);
	    });
	}
	
	function stopRecruitments(jobPostIds) {
	    if (!confirm("공고를 마감합니까?")) return;
	
	    fetch("/company/recruitment/stop", {
	        method: "PATCH",
	        headers: {
	            "Content-Type": "application/json"
	        },
	        body: JSON.stringify(jobPostIds) // 배열 전달
	    })
	    .then(response => {
	        if (!response.ok) {
	            throw new Error("서버 오류: " + response.status);
	        }
	        return response.text();
	    })
	    .then(data => {
	        if (data == "마감완료") {
	            alert("마감 성공");
	            stopRecruitmentRows(jobPostIds);
	        } else {
	            alert("마감 실패: 서버 응답 오류");
	        }
	    })
	    .catch(error => {
	        alert("마감 실패");
	        console.error("에러 발생:", error);
	    });
	}
	
	function removeRecruitmentRows(jobPostIds) {
		  jobPostIds.forEach(id => {
			  console.log(id);
		    const rowGroupObj = document.getElementById("recruitment"+id);
		    console.log(rowGroupObj);
		    if (rowGroupObj) {
		      rowGroupObj.remove();
		    }
		  });
		}

	function stopRecruitmentRows(jobPostIds) {
		  jobPostIds.forEach(id => {
			  console.log(id);
		    const headObj = document.getElementById("title"+id);
		    console.log(headObj);
		    if (headObj) {
		    	const stopStr = `<span class="stoppedPost">마감</span>`;
		      	headObj.innerHTML+=stopStr;
		    }
		    const stopBtnObj = document.getElementById("stopBtn" + id);
		    if (stopBtnObj) stopBtnObj.disabled = true;
		  });
		}
	
	const delBtnArr = document.getElementsByClassName("delBtn");
	
	for (let i = 0; i < delBtnArr.length; i++) {
	    delBtnArr[i].addEventListener("click", function (e) {
	        const jobPostId = e.currentTarget.value;
	        deleteRecruitments([jobPostId]); // 단일도 배열로
	    });
	}
	
	const stopBtnArr = document.getElementsByClassName("stopBtn");
	
	for (let i=0;i<stopBtnArr.length;i++){
		stopBtnArr[i].addEventListener("click", function(e) {
			const jobPostId = e.currentTarget.value;
			stopRecruitments([jobPostId]);
		})
		
	}
});