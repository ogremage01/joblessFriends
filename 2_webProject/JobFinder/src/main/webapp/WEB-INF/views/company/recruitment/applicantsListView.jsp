<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기업 페이지-공고란</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css"
	rel="stylesheet" crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js"
	integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">


<style type="text/css">

	
	.main{
		width: 800px;
		margin: auto;
	
	}
	
	#recruitmentList{
		margin: auto;
	}
	
	.emptyList{
	}
	
	.buttonPlace {
    text-align: center;
    vertical-align: middle;
	}
	
	#recruitmentList>thead{
	
	background: gray;
	text-align: center;
	}
	
	.buttonPlace>a{
		width: 130px;
	
	}
	
	.innerTable{
		font-size: small;
		width: 600px;
	
	}
	
	.info{
		text-align: justify;
		color: gray;
	}
	.name{
		text-align: left;
	}
	
	.jobPostTitle{
		  text-decoration: none;  
  		  color: inherit;         
	}

</style>

</head>
<body>
<jsp:include page="../../common/header.jsp"/>



<div class="container">
    
	<!-- 메인 -->
  <div class="main">
  <h1>지원자관리</h1>
    

    </div>
    
    
    

</div>
	
<jsp:include page="../../common/footer.jsp" />
</body>

	<script type="text/javascript">

function deleteRecruitments(jobPostIds) {
    if (!confirm("삭제를 진행합니까?")) return;

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
            alert("삭제 성공");
            location.reload();
        } else {
            alert("삭제 실패: 서버 응답 오류");
        }
    })
    .catch(error => {
        alert("삭제 실패");
        console.error("에러 발생:", error);
    });
}

const delBtnArr = document.getElementsByClassName("delBtn");

for (let i = 0; i < delBtnArr.length; i++) {
    delBtnArr[i].addEventListener("click", function (e) {
        const jobPostId = e.currentTarget.value;
        deleteRecruitments([jobPostId]); // 단일도 배열로
    });
}
    </script>


</html>