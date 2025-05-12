<!-- 관리자 로그인 여부를 묻는 자바구문이 들어가야 할 부분 -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>adminMain</title>
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


<style>
/*기본값(default)이 이미 "text/css"로 되어 있어서 자동인식한다하여 뺐음 */
#container {
	margin: auto;
}

#pageNation {
	margin: auto;
}

#companyInforSubmitForm{
	width: 800px;

}

</style>

<script type="text/javascript">
	
</script>
</head>
<body>
	<main class="d-flex flex-nowrap">
		<!-- 사이드바 영역 -->
		<div class="flex-shrink-0 p-3"
			style="width: 280px; height: 100vh; border-right: 1px solid black;">
			<a href="#"
				class="d-flex align-items-center pb-3 mb-3 link-body-emphasis text-decoration-none border-bottom">
				<svg class="bi pe-none me-2" width="30" height="24"
					aria-hidden="true">
					<use xlink:href="#bootstrap" /></svg> <span class="fs-5 fw-semibold">관리자
					화면</span>
			</a>
			<ul class="list-unstyled ps-0">
				<li class="mb-1"><a
					class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
					href="/admin/main"> Home </a></li>
				<li class="mb-1">
					<button
						class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
						data-bs-toggle="collapse" data-bs-target="#member-collapse"
						aria-expanded="false">회원관리</button>
					<div class="collapse" id="member-collapse">
						<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
							<li><a href="/admin/member/individual"
								class="link-body-emphasis d-inline-flex text-decoration-none rounded">일반회원</a></li>
							<li><a href="/admin/member/company"
								class="link-body-emphasis d-inline-flex text-decoration-none rounded">기업회원</a></li>
							<!-- <li><a href="/admin/admin" class="link-body-emphasis d-inline-flex text-decoration-none rounded">관리자</a></li> -->
						</ul>
					</div>
				</li>
				<li class="mb-1"><a href="/admin/recruitment"
					class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
						공고관리 </a></li>
				<li class="mb-1">
					<button
						class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
						data-bs-toggle="collapse" data-bs-target="#community-collapse"
						aria-expanded="false">커뮤니티관리</button>
					<div class="collapse" id="community-collapse">
						<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
							<li><a href="/admin/community"
								class="link-body-emphasis d-inline-flex text-decoration-none rounded">게시판
									관리</a></li>
						</ul>
					</div>
				</li>
				<li class="mb-1">
					<button
						class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
						data-bs-toggle="collapse" data-bs-target="#job-collapse"
						aria-expanded="false">직군/직무관리</button>
					<div class="collapse" id="job-collapse">
						<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
							<li><a href="/admin/job/jobGroup"
								class="link-body-emphasis d-inline-flex text-decoration-none rounded">직군관리</a></li>
							<li><a href="/admin/job/job"
								class="link-body-emphasis d-inline-flex text-decoration-none rounded">직무관리</a></li>
						</ul>
					</div>
				</li>
				<li class="mb-1"><a href="/admin/skill"
					class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
						스킬관리 </a></li>
				<li class="mb-1"><a href="/admin/chat"
					class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
						채팅관리 </a></li>
				<li class="border-top my-3"></li>
			</ul>
			<a href="/admin/logout"
				class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">로그아웃</a>
		</div>

		<!-- 사이드바 영역 -->
		<!-- 본문영역 -->



		<div id="container">
			<h1>기업 상세 정보</h1>

			<form class="container mt-4" id="companyInforSubmitForm">

			
			    <div class="row mb-3">
			        <label for="companyId" class="col-sm-2 col-form-label text-end">ID</label>
			        <div class="col-sm-10">
			            <input id="companyId" name="companyId" class="form-control-plaintext" value="${companyVo.companyId}" readonly>
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="companyName" class="col-sm-2 col-form-label text-end">기업명</label>
			        <div class="col-sm-10">
			            <input id="companyName" name="companyName" class="form-control" value="${companyVo.companyName}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="email" class="col-sm-2 col-form-label text-end">이메일</label>
			        <div class="col-sm-10">
			            <input id="email" name="email" class="form-control" value="${companyVo.email}">
			        </div>
			    </div>
			
				<div class="row mb-3">
			        <label for="password" class="col-sm-2 col-form-label text-end">비밀번호</label>
			        <div class="col-sm-10">
			            <input id="password" name="password" class="form-control">
			        </div>
			    </div>
			
			
			    <div class="row mb-3">
			        <label for="brn" class="col-sm-2 col-form-label text-end">사업자번호</label>
			        <div class="col-sm-10">
			            <input id="brn" name="brn" class="form-control" value="${companyVo.brn}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="representative" class="col-sm-2 col-form-label text-end">담당자</label>
			        <div class="col-sm-10">
			            <input id="representative" name="representative" class="form-control" value="${companyVo.representative}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="tel" class="col-sm-2 col-form-label text-end">전화번호</label>
			        <div class="col-sm-10">
			            <input id="tel" name="tel" class="form-control" value="${companyVo.tel}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="postalCodeId" class="col-sm-2 col-form-label text-end">우편번호</label>
			        <div class="col-sm-10">
			            <input id="postalCodeId" name="postalCodeId" class="form-control" value="${companyVo.postalCodeId}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="arenaName" class="col-sm-2 col-form-label text-end">지역</label>
			        <div class="col-sm-10">
			            <input id="arenaName" name="arenaName" class="form-control" value="${companyVo.arenaName}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="address" class="col-sm-2 col-form-label text-end">상세주소</label>
			        <div class="col-sm-10">
			            <input id="address" name="address" class="form-control" value="${companyVo.address}">
			        </div>
			    </div>
			
			    <div class="row">
			        <div class="offset-sm-2 col-sm-10 d-flex gap-2">
			            <input type="submit" id="submitBtn" class="btn btn-primary" value="수정">
			            <input type="reset" class="btn btn-secondary" value="초기화">
			            <button type="button" class="btn btn-danger" id="delete">탈퇴</button>
			            <a href="/admin/member/company" class="btn btn-light">목록보기</a>
			            
			        </div>
			    </div>
			</form>




		</div>
		<!-- 본문영역 -->

	</main>
</body>

<script type="text/javascript">
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


</script>

</html>
