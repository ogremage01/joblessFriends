<!-- 관리자 로그인 여부를 묻는 자바구문이 들어가야 할 부분 -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>게시글 관리</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" 
      crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js" 
	integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
	
	<link rel="stylesheet" href="/css/admin/common.css">
	<link rel="stylesheet" href="/css/admin/tableStyle.css">
	<link rel="stylesheet" href="/css/admin/notice/noticeStyle.css">
	
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    <style>
        /*기본값(default)이 이미 "text/css"로 되어 있어서 자동인식한다하여 뺐음 */
        #container{
        	margin: auto;
        }
    </style>
    
    <script type="text/javascript">
    
    </script>
</head>
<body>
<main class="d-flex flex-nowrap">
		<!-- 사이드바 영역 -->
		<jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>

		<!-- 사이드바 영역 -->
  
  
	  <!-- 본문영역  -->
		<div id="container">
			<h1 style="text-align: center;">공지 관리 목록</h1>
			<table class="table table-striped">
				<thead class="table-dark" style="margin: auto;">
					<tr>
						<td><button id="massDelCom">삭제</button></td>
						<td>ID</td>
						<td>카테고리</td>
						<td>제목</td>
						<td>작성 날짜</td>
						<td>조회수</td>
						<td>수정</td>
						<td>삭제</td>
					</tr>
				</thead>
				<tbody class="table-group-divider">
					<c:forEach var="noticeVo" items="${noticeList}">
						<tr>
							<td style="text-align: center;"><input type="checkbox"
								class="delPost" name="delete" value="${noticeVo.noticeId}"></td>
							<td>${noticeVo.noticeId}</td>
							<td>${noticeVo.noticeCategory.noticeCategoryContent}</td>
							<td><a href="#">${noticeVo.title}</a></td>
							<td><fmt:formatDate value="${noticeVo.createAt}" pattern="yyyy-MM-dd" /></td>
							<td>${noticeVo.views}</td>
							<td><button id="modi_${noticeVo.noticeId}" value="${noticeVo.noticeId}" onclick="moveUpdateNotice(${noticeVo.noticeId})">수정</button></td>
							<td><button class="delBtn" value="${noticeVo.noticeId}">삭제</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			


			<div id='pageNation'>
				<c:if test="${pagination.totalPageCount > 0}">
				<nav aria-label="...">
				
					<ul class="pagination justify-content-center">
						<li class="page-item ${searchVo.page==1?'disabled':''}">
							<a class="page-link" href="/admin/community/notice?page=${searchVo.page-1}&keyword=${searchVo.keyword}">Previous</a>
						</li>
						<c:forEach begin="${pagination.startPage}" var="i" 
							end="${pagination.endPage}">
							<li class="page-item ${searchVo.page==i?'active':''}">
							<a class="page-link" href="/admin/community/notice?page=${i}&keyword=${searchVo.keyword}">${i}</a></li>
						</c:forEach>

						<!-- <li class="page-item active" aria-current="page"><a
						class="page-link" href="#">2</a></li> -->
						<li class="page-item"><a
							class="page-link ${searchVo.page==pagination.totalPageCount? 'disabled':''}" href="/admin/community/notice?page=${searchVo.page+1}&keyword=${searchVo.keyword}">Next</a></li>
					</ul>
				
				</nav>
				</c:if>
			</div>
			<div id="search_upload">
				<div id="searchContainer">
					<input id="noticeKeyword" type="text" name="keyword" placeholder="제목 검색">
					<button id="noticeSearchBtn" class="btn btn-light">검색</button>
				</div>
				<button id="uploadNotice" onclick="uploadNotice()">+공지 추가</button>
			</div>
		</div>
			
		
      <!-- 본문영역  -->
      
    <form id="noticeSelectOneForm" action="./notice/update" method="get">
		<input type="hidden" id="noticeFormNo" name="no" value="">
	</form>
</main>

</body>

<script type="text/javascript">

	function moveUpdateNotice(noticeId){
	    let noticeBtn = $('#modi_' + noticeId); // ID 수정
	    let noticeNo = noticeBtn.val();         // value 값 추출

	    $('#noticeFormNo').val(noticeNo);       // 올바른 대입

	    document.getElementById('noticeSelectOneForm').submit();
		
	}
	
	function deleteNotices(noticeIdList) {
	    if (!confirm("삭제를 진행합니까?")) return;

	    fetch("/admin/community/notice/delete", {
	        method: "DELETE",
	        headers: {
	            "Content-Type": "application/json"
	        },
	        body: JSON.stringify(noticeIdList) // 배열 전달
	    })
	    .then(response => {
	        if (!response.ok) {
	            throw new Error("서버 오류: " + response.status);
	        }
	        return response.text();
	    })
	    .then(data => {
	        if (data == "삭제완료") {
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
	
	const delBtnArr = $(".delBtn");

	for (let i = 0; i < delBtnArr.length; i++) {//선택된 삭제 목록
	    delBtnArr[i].addEventListener("click", function (e) {
	        const noticeId = e.target.value;
	        deleteNotices([noticeId]); // 단일도 배열로
	    });
	}
	
	document.getElementById("massDelCom").addEventListener("click", function () {
	    const checked = document.querySelectorAll(".delPost:checked");
	    const noticeIdList = Array.from(checked).map(el => el.value);

	    if (noticeIdList.length === 0) {
	        alert("삭제할 항목을 선택하세요.");
	        return;
	    }

	    deleteNotices(noticeIdList);
	});
	
	
	const searchNoticeBtn = document.getElementById("noticeSearchBtn");

	searchNoticeBtn.addEventListener("click", function(e){
	    const noticeKeywordVal = document.getElementById("noticeKeyword").value;
	    
	    if(noticeKeywordVal != null || noticeKeywordVal != ""){
	        
	        location.href=`/admin/community/notice?keyword=\${noticeKeywordVal}`;
	        
	    } 
	    
	});
	
	
	function uploadNotice(){
		location.href="/admin/community/notice/upload";
	}


</script>
</html>