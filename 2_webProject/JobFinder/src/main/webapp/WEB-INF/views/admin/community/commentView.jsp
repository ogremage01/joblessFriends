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
  <div class="flex-shrink-0 p-3" style="width: 280px; height:100vh; border-right: 1px solid black;">
    <a href="#" class="d-flex align-items-center pb-3 mb-3 link-body-emphasis text-decoration-none border-bottom">
      <span class="fs-5 fw-semibold">관리자 화면</span>
    </a>
    <ul class="list-unstyled ps-0">
      <li class="mb-1">
        <a class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" href="/admin/main">
          Home
        </a>
      
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#member-collapse" aria-expanded="false">
          회원관리
        </button>
        <div class="collapse" id="member-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="/admin/member/individual" class="link-body-emphasis d-inline-flex text-decoration-none rounded">일반회원</a></li>
            <li><a href="/admin/member/company" class="link-body-emphasis d-inline-flex text-decoration-none rounded">기업회원</a></li>
            <!-- <li><a href="/admin/admin" class="link-body-emphasis d-inline-flex text-decoration-none rounded">관리자</a></li> -->
          </ul>
        </div>
      </li>
      <li class="mb-1">
        <a href="/admin/recruitment" class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
          공고관리
        </a>
      </li>
            <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#community-collapse" aria-expanded="false">
          커뮤니티관리
        </button>
        <div class="collapse" id="community-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="/admin/community/post" class="link-body-emphasis d-inline-flex text-decoration-none rounded">게시판 관리</a></li>
            <li><a href="/admin/community/comment" class="link-body-emphasis d-inline-flex text-decoration-none rounded">댓글 관리</a></li>
            
          </ul>
        </div>
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#job-collapse" aria-expanded="false">
          직군/직무관리
        </button>
        <div class="collapse" id="job-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="/admin/job/jobGroup" class="link-body-emphasis d-inline-flex text-decoration-none rounded">직군관리</a></li>
            <li><a href="/admin/job/job" class="link-body-emphasis d-inline-flex text-decoration-none rounded">직무관리</a></li>
          </ul>
        </div>
      </li>
      <li class="mb-1">
        <a href="/admin/skill" class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
          스킬관리
        </a>
      </li>
      <li class="mb-1">
        <a href="/admin/chat" class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
          채팅관리
        </a>
      </li>
      <li class="border-top my-3"></li>
    </ul>
    <a href="/admin/logout" class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">로그아웃</a>
  </div>
  <!-- 사이드바 영역 -->
  
  
	  <!-- 본문영역  -->
		<div id="container">
			<h1 style="text-align: center;">댓글 관리 목록</h1>
			<table class="table table-striped">
				<thead class="table-dark" style="margin: auto;">
					<tr>
						<td><button id="massDelCom">삭제</button></td>
						<td>ID</td>
						<td>내용</td>
						<td>작성자</td>
						<td>작성 날짜</td>
						<td>관련 포스트</td>
						<td>삭제</td>
					</tr>
				</thead>
				<tbody class="table-group-divider">
					<c:forEach var="commentVo" items="${commentList}">
						<tr>
							<td style="text-align: center;"><input type="checkbox" class="delPost" name="delete" value="${commentVo.postCommentId}"></td>
							<td>${commentVo.postCommentId}</td>
							<td><a href="http://localhost:9090/community/detail?no=${commentVo.postCommentId}">${commentVo.content}</a></td>
							<td>${commentVo.nickname}</td>
							<td><fmt:formatDate value="${commentVo.createAt}" pattern="yyyy-MM-dd" /></td>
							<td>${commentVo.postCommentId}</td> <!-- 종류부분: 댓글, 대댓글+번호 -->
							<td><button class="delBtn" value="${commentVo.postCommentId}">삭제</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			


			<div id='pageNation'>
				<c:if test="${pagination.totalPageCount > 0}">
				<nav aria-label="...">
				
					<ul class="pagination justify-content-center">
						<li class="page-item ${searchVo.page==1?'disabled':''}">
							<a class="page-link" href="/admin/community/comment?page=${searchVo.page-1}&keyword=${searchVo.keyword}">Previous</a>
						</li>
						<c:forEach begin="${pagination.startPage}" var="i" 
							end="${pagination.endPage}">
							<li class="page-item ${searchVo.page==i?'active':''}">
							<a class="page-link" href="/admin/community/comment?page=${i}&keyword=${searchVo.keyword}">${i}</a></li>
						</c:forEach>

						<!-- <li class="page-item active" aria-current="page"><a
						class="page-link" href="#">2</a></li> -->
						<li class="page-item"><a
							class="page-link ${searchVo.page==pagination.totalPageCount? 'disabled':''}" href="/admin/community/comment?page=${searchVo.page+1}&keyword=${searchVo.keyword}">Next</a></li>
					</ul>
				
				</nav>
				</c:if>
			</div>

			<div id="searchContainer">
				<input id="commentKeyword" type="text" name="keyword" placeholder="제목 검색">
				<button id="commentSearchBtn" class="btn btn-light">검색</button>
			</div>
		</div>
			
		
      <!-- 본문영역  -->
</main>

</body>

<script type="text/javascript">

	
	function deleteComments(commentIdList) {
	    if (!confirm("삭제를 진행합니까?")) return;

	    fetch("/admin/community/comment/delete", {
	        method: "DELETE",
	        headers: {
	            "Content-Type": "application/json"
	        },
	        body: JSON.stringify(commentIdList) // 배열 전달
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
	
	const delBtnArr = $(".delBtn");

	for (let i = 0; i < delBtnArr.length; i++) {//선택된 삭제 목록
	    delBtnArr[i].addEventListener("click", function (e) {
	        const commentId = e.target.value;
	        deleteComments([commentId]); // 단일도 배열로
	    });
	}
	
	document.getElementById("massDelCom").addEventListener("click", function () {
	    const checked = document.querySelectorAll(".delPost:checked");
	    const commentIdList = Array.from(checked).map(el => el.value);

	    if (commentIdList.length === 0) {
	        alert("삭제할 항목을 선택하세요.");
	        return;
	    }

	    deleteComments(commentIdList);
	});
	
	
	const searchCommentBtn = document.getElementById("commentSearchBtn");

	searchCommentBtn.addEventListener("click", function(e){
	    const commentKeywordVal = document.getElementById("commentKeyword").value;
	    
	    if(commentKeywordVal != null || commentKeywordVal != ""){
	        
	        location.href=`/admin/community/comment?keyword=\${commentKeywordVal}`;
	        
	    }
	    
	    
	    
	    
	});


</script>
</html>