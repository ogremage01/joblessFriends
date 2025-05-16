<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기업 페이지</title>


<script src="https://code.jquery.com/jquery-3.7.1.min.js" ></script>

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



</head>
<body>
<jsp:include page="../common/header.jsp"/>



<div class="container">
    
	<!-- 메인 -->
  <div class="main">
    
    <div id="container">
			<h1 style="text-align: center;">기업 상세 정보</h1>
			<div id="companyData" data-company-id="${companyVo.companyId}"></div>
			<form class="container mt-4" id="companyInforSubmitForm" style="width: 800px;">
			    <div class="row mb-3">
			        <label for="companyId" class="col-sm-3 col-form-label text-end">ID</label>
			        <div class="col-sm-9">
			            <input id="companyId" name="companyId" class="form-control-plaintext" value="${companyVo.companyId}" readonly>
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="companyName" class="col-sm-3 col-form-label text-end">기업명</label>
			        <div class="col-sm-9">
			            <input id="companyName" name="companyName" class="form-control" value="${companyVo.companyName}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="email" class="col-sm-3 col-form-label text-end">이메일</label>
			        <div class="col-sm-9">
			            <input id="email" name="email" type="email" class="form-control" value="${companyVo.email}">
			        </div>
			    </div>
			
				<div class="row mb-3">
			        <label for="password" class="col-sm-3 col-form-label text-end">비밀번호</label>
			        <div class="col-sm-9">
			            <input type="password" id="password" name="password" class="form-control"  onblur="valiCheckPwd();" onkeyup="sameCheckPwd();">
			            <div id="pwdStatus" class="valiCheckText"></div> <!-- 비밀번호 확인 메시지가 표시될 곳 -->
			        </div>
			    </div>
				<div class="row mb-3">
			        <label for="passwordCheck" class="col-sm-3 col-form-label text-end">비밀번호확인</label>
			        <div class="col-sm-9">
			            <input type="password" id="passwordCheck" name="passwordCheck" class="form-control" onblur="sameCheckPwd();">
			            <div id="pwdStatus2" class="valiCheckText"></div> <!-- 비밀번호 확인 메시지가 표시될 곳 -->
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="brn" class="col-sm-3 col-form-label text-end">사업자번호</label>
			        <div class="col-sm-9">
			            <input id="brn" name="brn" class="form-control" value="${companyVo.brn}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="representative" class="col-sm-3 col-form-label text-end">담당자</label>
			        <div class="col-sm-9">
			            <input id="representative" name="representative" class="form-control" value="${companyVo.representative}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="tel" class="col-sm-3 col-form-label text-end">전화번호</label>
			        <div class="col-sm-9">
			            <input id="tel" name="tel" class="form-control" value="${companyVo.tel}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="postalCodeId" class="col-sm-3 col-form-label text-end">우편번호</label>
			        <div class="col-sm-9">
			            <input id="postalCodeId" name="postalCodeId" class="form-control" value="${companyVo.postalCodeId}">
			            <input type="button" class="btn btn-secondary" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"
			            style="margin-top: 2px;"><br>
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="arenaName" class="col-sm-3 col-form-label text-end">지역</label>
			        <div class="col-sm-9">
			            <input id="arenaName" name="arenaName" class="form-control" value="${companyVo.arenaName}">
			        </div>
			    </div>
			
			    <div class="row mb-3">
			        <label for="address" class="col-sm-3 col-form-label text-end">상세주소</label>
			        <div class="col-sm-9">
			            <input id="address" name="address" class="form-control" value="${companyVo.address}">
			        </div>
			    </div>
			
			
			    <div class="row">
			        <div class="offset-sm-2 col-sm-9 d-flex gap-2">
			            <input type="submit" id="submitBtn" class="btn btn-primary" value="수정">
			            <input type="reset" class="btn btn-secondary" value="초기화">
			            <button type="button" class="btn btn-danger" id="delete">탈퇴</button>
			            
			            
			        </div>
			    </div>
			</form>


		</div>
  </div>
</div>
	
<jsp:include page="../common/footer.jsp" />
</body>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/js/company/companyDetail.js"></script>


</script>
</html>