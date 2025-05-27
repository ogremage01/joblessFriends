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
		<jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>

		<!-- 사이드바 영역 -->
  
  
	  <!-- 본문영역  -->
		<div id="container">
			<h1 style="text-align: center;">커뮤니티 관리 목록</h1>
			<table class="table table-striped">
				<thead class="table-dark" style="margin: auto;">
					<tr>
						<td><button id="massDelCom">삭제</button></td>
						<td>ID</td>
						<td>제목</td>
						<td>작성자</td>
						<td>작성 날짜</td>
						<td>조회수</td>
						<td>삭제</td>
					</tr>
				</thead>
				<tbody class="table-group-divider">
					<c:forEach var="communityVo" items="${communityList}">
						<tr>
							<td style="text-align: center;"><input type="checkbox" class="delPost" name="delete" value="${communityVo.communityId}"></td>
							<td>${communityVo.communityId}</td>
							<td><a href="http://localhost:9090/community/detail?no=${communityVo.communityId}">${communityVo.title}</a></td>
							<td>${communityVo.nickname}</td>
							<td><fmt:formatDate value="${communityVo.createAt}" pattern="yyyy-MM-dd" /></td>
							<td class='view'>${communityVo.views}</td>
							<td><button class="delBtn" value="${communityVo.communityId}">삭제</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			


			<div id='pageNation'>
				<c:if test="${pagination.totalPageCount > 0}">
				<nav aria-label="...">
				
					<ul class="pagination justify-content-center">
						<li class="page-item ${searchVo.page==1?'disabled':''}">
							<a class="page-link" href="/admin/community/post?page=${searchVo.page-1}&keyword=${searchVo.keyword}">Previous</a>
						</li>
						<c:forEach begin="${pagination.startPage}" var="i" 
							end="${pagination.endPage}">
							<li class="page-item ${searchVo.page==i?'active':''}">
							<a class="page-link" href="/admin/community/post?page=${i}&keyword=${searchVo.keyword}">${i}</a></li>
						</c:forEach>

						<!-- <li class="page-item active" aria-current="page"><a
						class="page-link" href="#">2</a></li> -->
						<li class="page-item"><a
							class="page-link ${searchVo.page==pagination.totalPageCount? 'disabled':''}" href="/admin/community/post?page=${searchVo.page+1}&keyword=${searchVo.keyword}">Next</a></li>
					</ul>
				
				</nav>
				</c:if>
			</div>

			<div id="searchContainer">
				<input id="communityKeyword" type="text" name="keyword" placeholder="제목 검색">
				<button id="communitySearchBtn" class="btn btn-light">검색</button>
			</div>
		</div>
			
		
      <!-- 본문영역  -->
</main>

</body>

<script type="text/javascript">

	
	function deleteCommunities(communityIdList) {
	    if (!confirm("삭제를 진행합니까?")) return;

	    fetch("/admin/community/post/delete", {
	        method: "DELETE",
	        headers: {
	            "Content-Type": "application/json"
	        },
	        body: JSON.stringify(communityIdList) // 배열 전달
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
	        const communityId = e.target.value;
	        deleteCommunities([communityId]); // 단일도 배열로
	    });
	}
	
	document.getElementById("massDelCom").addEventListener("click", function () {
	    const checked = document.querySelectorAll(".delPost:checked");
	    const communityIdList = Array.from(checked).map(el => el.value);

	    if (communityIdList.length === 0) {
	        alert("삭제할 항목을 선택하세요.");
	        return;
	    }

	    deleteCommunities(communityIdList);
	});
	
	
	const searchCommunityBtn = document.getElementById("communitySearchBtn");

	searchCommunityBtn.addEventListener("click", function(e){
	    const communityKeywordVal = document.getElementById("communityKeyword").value;
	    
	    if(communityKeywordVal != null || communityKeywordVal != ""){
	        
	        location.href=`/admin/community/post?keyword=\${communityKeywordVal}`;
	        
	    }
	    
	    
	    
	    
	});


</script>
</html>