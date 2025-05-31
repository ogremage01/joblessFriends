<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기업 페이지</title>

<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/member/memberMyPage.css" />
<link rel="stylesheet" href="/css/company/companyInfo.css" />
<style type="text/css">
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>

	<jsp:include page="../common/header.jsp" />

	<div class="container">
		<div id="containerWrap">
			<jsp:include page="./companyPage/companyPageSidebar.jsp" />
			<!-- 메인 -->
			<div class="main">
				<div class="content">
					<h1 class="mainTitle">기업 정보 관리</h1>
					<div class="accountInfo">
						<h2>계정 정보</h2>
						<input type="hidden" id="companyData" value="${companyVo.companyId}">
						<form class="formStyle mt-4" id="companyInforSubmitForm">
							<input id="companyId" name="companyId" type="hidden" class="form-control-plaintext" value="${companyVo.companyId}" readonly>
	
							<div class="row">
								<label for="companyName"
									class="col-form-label">기업명</label>
								<div class="col-sm-9">
									<input id="companyName" name="companyName" class="form-control" value="${companyVo.companyName}">
								</div>
							</div>
	
							<div class="row">
								<label for="email" class="col-form-label">이메일</label>
								<div class="col-sm-9">
									<input id="email" name="email" type="email" class="form-control valiInput" oninput="valiCheckEmail();" value="${companyVo.email}">
									<div id="emailStatus" class="valiCheckText"></div> <!-- 이메일 중복 여부 메시지가 표시될 곳 -->
								</div>
							</div>
	
							<div class="row">
								<label for="password" class="col-form-label">비밀번호</label>
								<div class="col-sm-9">
									<input type="password" id="password" name="password" class="form-control valiInput" onblur="valiCheckPwd();" onkeyup="sameCheckPwd();">
									<div id="pwdStatus" class="valiCheckText"></div>
									<!-- 비밀번호 확인 메시지가 표시될 곳 -->
								</div>
							</div>
							
							<div class="row">
								<label for="passwordCheck" class="col-form-label">비밀번호확인</label>
								<div class="col-sm-9">
									<input type="password" id="passwordCheck" name="passwordCheck" class="form-control valiInput" onblur="sameCheckPwd();">
									<div id="pwdStatus2" class="valiCheckText"></div>
									<!-- 비밀번호 확인 메시지가 표시될 곳 -->
								</div>
							</div>
	
							<div class="row">
								<label for="brn" class="col-form-label">사업자번호</label>
								<div class="col-sm-9">
									<input id="brn" name="brn" class="form-control valiInput" onblur="brnCheckFnc();" value="${companyVo.brn}">
									<div id="brnStatus" class="valiCheckText"></div>
								</div>
							</div>
	
							<div class="row">
								<label for="representative" class="col-form-label">담당자</label>
								<div class="col-sm-9">
									<input id="representative" name="representative" class="form-control" value="${companyVo.representative}">
								</div>
							</div>
	
							<div class="row">
								<label for="tel" class="col-form-label">전화번호</label>
								<div class="col-sm-9">
									<input id="tel" name="tel" class="form-control" value="${companyVo.tel}">
								</div>
							</div>
	
							<div class="row">
								<label for="postalCodeId" class="col-form-label">우편번호</label>
								<div class="col-sm-9">
									<input id="postalCodeId" name="postalCodeId" class="form-control" value="${companyVo.postalCodeId}">
									<button type="button" class="btn btn-secondary" onclick="sample6_execDaumPostcode()">우편번호 찾기</button><br>
								</div>
							</div>
	
							<div class="row">
								<label for="arenaName" class="col-form-label">지역</label>
								<div class="col-sm-9">
									<input id="arenaName" name="arenaName" class="form-control" value="${companyVo.arenaName}">
								</div>
							</div>
	
							<div class="row">
								<label for="address" class="col-form-label">상세주소</label>
								<div class="col-sm-9">
									<input id="address" name="address" class="form-control" value="${companyVo.address}">
								</div>
							</div>
	
	
							<div class="row">
								<div class="buttonWrap">
									<button type="submit" id="submitBtn" class="btn btn-primary">수정</button>
									<button type="reset" onclick="resetStatus();" class="btn btn-secondary">초기화</button>
									<button type="button" class="btn btn-danger" id="delete">탈퇴</button>
								</div>
							</div>
							
						</form>
						
					</div>
				</div>
			</div>
		</div>
	</div>

		<jsp:include page="../common/footer.jsp" />
</body>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/js/company/companyDetail.js"></script>


</script>
</html>