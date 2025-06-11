<!-- csrf 활성화 시 적용 예정 -->
<%-- <meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" /> --%>


<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>기업회원 상세 정보 - 어디보잡 관리자</title>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
    integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js" 
    integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

<!-- 공통 스타일 적용 -->
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/admin/adminStyle.css">

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

<div id="container">
    <div id="containerWrap">
        <div class="admin-container">
            
            
            <!-- 사이드바 영역 -->
            <div class="admin-sidebar">
                <jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
            </div>
            
            <!-- 메인 컨텐츠 영역 -->
            <div class="admin-main">
	            <div class="admin-header">
	                <h1 class="mainTitle">기업회원 상세 정보</h1>
	            </div>
                <div class="admin-content">
                    <div id="companyData" data-company-id="${companyVo.companyId}"></div>
                    <form id="companyInforSubmitForm" style="padding: 30px;">
                        <div class="row mb-3">
                            <label for="companyId" class="col-sm-3 col-form-label text-end fw-bold">회사 ID</label>
                            <div class="col-sm-9">
                                <input id="companyId" name="companyId" class="form-control-plaintext" value="${companyVo.companyId}" readonly>
                            </div>
                        </div>
                    
                        <div class="row mb-3">
                            <label for="companyName" class="col-sm-3 col-form-label text-end fw-bold">기업명</label>
                            <div class="col-sm-9">
                                <input id="companyName" name="companyName" class="form-control" value="${companyVo.companyName}">
                            </div>
                        </div>
                    
                        <div class="row mb-3">
                            <label for="email" class="col-sm-3 col-form-label text-end fw-bold">이메일</label>
                            <div class="col-sm-9">
                                <input id="email" name="email" type="email" class="form-control" value="${companyVo.email}">
                            </div>
                        </div>

                        <div class="row mb-3">
                            <label for="brn" class="col-sm-3 col-form-label text-end fw-bold">사업자번호</label>
                            <div class="col-sm-9">
                                <input id="brn" name="brn" class="form-control" value="${companyVo.brn}">
                            </div>
						</div>
                        
                            <div class="row mb-3">
                                <label for="password" class="col-sm-3 col-form-label text-end fw-bold">비밀번호</label>
                                <div class="col-sm-9">
                                    <input type="password" id="password" name="password" class="form-control" placeholder="변경할 비밀번호를 입력하세요" onblur="sameCheckPwd();">
                                    <div id="pwdStatus" class="valiCheckText form-text text-danger"></div>
                                </div>
                            </div>
                            
                            <div class="row mb-3">
                                <label for="passwordCheck" class="col-sm-3 col-form-label text-end fw-bold">비밀번호 확인</label>
                                <div class="col-sm-9">
                                    <input type="password" id="passwordCheck" name="passwordCheck" class="form-control" placeholder="비밀번호를 다시 입력하세요" onblur="sameCheckPwd();">
                                    <div id="pwdCheckStatus" class="valiCheckText form-text text-danger"></div>
                                </div>

                             </div>
                    
                        <div class="row mb-3">
                            <label for="representative" class="col-sm-3 col-form-label text-end fw-bold">담당자</label>
                            <div class="col-sm-9">
                                <input id="representative" name="representative" class="form-control" value="${companyVo.representative}">
                            </div>
                        </div>
                    
                        <div class="row mb-3">
                            <label for="tel" class="col-sm-3 col-form-label text-end fw-bold">전화번호</label>
                            <div class="col-sm-9">
                                <input id="tel" name="tel" class="form-control" value="${companyVo.tel}">

                            </div>
                        </div>
                    
                        <div class="row mb-3">
                            <label for="postalCodeId" class="col-sm-3 col-form-label text-end fw-bold">우편번호</label>
                            <div class="col-sm-9">
                                <div class="input-group">
                                    <input id="postalCodeId" name="postalCodeId" class="form-control" value="${companyVo.postalCodeId}" readonly>
                                    <button type="button" class="btn btn-outline-secondary" onclick="sample6_execDaumPostcode()">
                                        <i class="bi bi-search"></i> 우편번호 찾기
                                    </button>
                                </div>
                            </div>
                        </div>
                    
                        <div class="row mb-3">
                            <label for="arenaName" class="col-sm-3 col-form-label text-end fw-bold">지역</label>
                            <div class="col-sm-9">
                                <input id="arenaName" name="arenaName" class="form-control" value="${companyVo.arenaName}" readonly>
                            </div>
                        </div>
                    
                        <div class="row mb-3">
                            <label for="address" class="col-sm-3 col-form-label text-end fw-bold">상세주소</label>
                            <div class="col-sm-9">
                                <input id="address" name="address" class="form-control" value="${companyVo.address}">
                            </div>
                        </div>
                    
                        <div class="row mt-4">
                            <div class="offset-sm-3 col-sm-9 d-flex gap-2">
                                <button type="submit" id="submitBtn" class="add-btn">
                                    <i class="bi bi-check-circle"></i> 수정
                                </button>
                                <button type="reset" class="btnStyle" onclick="resetFun()">
                                    <i class="bi bi-arrow-clockwise"></i> 초기화
                                </button>
                                <button type="button" class="delete-btn" id="delete">
                                    <i class="bi bi-building-x"></i> 탈퇴
                                </button>
                                <a href="/admin/member/company?page=${param.page}&keyword=${param.keyword}" class="back-btn">
                                    <i class="bi bi-list"></i> 목록보기
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/js/admin/member/companyDetail.js"></script>

</body>
</html>
