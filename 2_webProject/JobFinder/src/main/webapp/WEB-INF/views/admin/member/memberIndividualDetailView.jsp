<!-- 관리자 로그인 여부를 묻는 자바구문이 들어가야 할 부분 -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>개인회원관리-세부</title>
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

#memberInforSubmitForm{
	width: 800px;

}

</style>

<script type="text/javascript">
	
</script>
</head>
<body>
	<main class="d-flex flex-nowrap">
		<jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
		<!-- 본문영역 -->

		<div id="container">
			<h1>회원 상세 정보</h1>
			<div id="memberData" data-member-id="${memberVo.memberId}"></div>
			<form class="container mt-4" id="memberInforSubmitForm">
			
			    <div class="row mb-3">
			        <label for="memberId" class="col-sm-2 col-form-label text-end">ID</label>
			        <div class="col-sm-10">
			            <input id="memberId" name="memberId" class="form-control-plaintext" value="${memberVo.memberId}" readonly>
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="email" class="col-sm-2 col-form-label text-end">이메일</label>
			        <div class="col-sm-10">
			            <input id="email" name="email" class="form-control" value="${memberVo.email}">
			        </div>
			    </div>
			
				<div class="row mb-3">
			        <label for="password" class="col-sm-2 col-form-label text-end">비밀번호</label>
			        <div class="col-sm-10">
			            <input id="password" name="password" class="form-control">
			        </div>
			    </div>
			
			
			    <div class="row mb-3">
			        <label for="nickname" class="col-sm-2 col-form-label text-end">닉네임</label>
			        <div class="col-sm-10">
			            <input id="nickname" name="nickname" class="form-control" value="${memberVo.nickname}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="resumeMax" class="col-sm-2 col-form-label text-end">이력서 한도</label>
			        <div class="col-sm-10">
			            <input id="resumeMax" name="resumeMax" class="form-control" value="${memberVo.resumeMax}">
			        </div>
			    </div>

			    <div class="row mb-3">
			        <label for="createAt" class="col-sm-2 col-form-label text-end">생성일</label>
			        <div class="col-sm-10">
			            <input id="createAt" name="createAt" class="form-control-plaintext"
						value="<fmt:formatDate value="${memberVo.createAt}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly>
			        </div>
			    </div>

			    <div class="row mb-3">
			        <label for="modifiedAt" class="col-sm-2 col-form-label text-end">수정일</label>
			        <div class="col-sm-10">
			            <input id="modifiedAt" name="modifiedAt" class="form-control-plaintext"
						value="<fmt:formatDate value="${memberVo.modifiedAt}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly>
			        </div>
			    </div>

			    <div class="row mb-3">
			        <label for="provider" class="col-sm-2 col-form-label text-end">provider</label>
			        <div class="col-sm-10">
			            <input id="provider" name="provider" class="form-control-plaintext" value="${memberVo.provider}" readonly>
			        </div>
			    </div>
			
			    <div class="row">
			        <div class="offset-sm-2 col-sm-10 d-flex gap-2">
			            <input type="submit" id="submitBtn" class="btn btn-primary" value="수정">
			            <input type="reset" class="btn btn-secondary" value="초기화">
			            <button type="button" class="btn btn-danger" id="delete">탈퇴</button>
			            <a href="/admin/member/individual" class="btn btn-light">목록보기</a>
			            
			        </div>
			    </div>
			</form>




		</div>
		<!-- 본문영역 -->

	</main>
</body>

<script src="/js/admin/member/individualDetail.js"></script>

</html>
