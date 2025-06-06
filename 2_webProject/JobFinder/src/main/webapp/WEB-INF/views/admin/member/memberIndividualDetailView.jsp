
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>개인회원 상세 정보 - 어디보잡 관리자</title>
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
                	<h1 class="mainTitle">개인회원 상세 정보</h1>
            	</div>
                <div class="admin-content">
                    <div id="memberData" data-member-id="${memberVo.memberId}"></div>
                    <form id="memberInforSubmitForm" style="padding: 30px;">
                        
                        <div class="row mb-3">
                            <label for="memberId" class="col-sm-3 col-form-label text-end fw-bold">회원 ID</label>
                            <div class="col-sm-9">
                                <input id="memberId" name="memberId" class="form-control-plaintext" value="${memberVo.memberId}" readonly>
                            </div>
                        </div>
                    
                        <div class="row mb-3">
                            <label for="email" class="col-sm-3 col-form-label text-end fw-bold">이메일</label>
                            <div class="col-sm-9">
                                <input id="email" name="email" class="form-control" value="${memberVo.email}">
                            </div>
                        </div>
                    


                        
                            <div class="row mb-3">
                                <label for="password" class="col-sm-3 col-form-label text-end fw-bold">비밀번호</label>
                                <div class="col-sm-9">
                                    <input type="password" id="password" name="password" class="form-control" placeholder="변경할 비밀번호를 입력하세요" onblur="sameCheckPwd()">
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
                            <label for="nickname" class="col-sm-3 col-form-label text-end fw-bold">닉네임</label>
                            <div class="col-sm-9">
                                <input id="nickname" name="nickname" class="form-control" value="${memberVo.nickname}">

                            </div>
                        </div>
                    
                        <div class="row mb-3">
                            <label for="resumeMax" class="col-sm-3 col-form-label text-end fw-bold">이력서 한도</label>
                            <div class="col-sm-9">
                                <input id="resumeMax" name="resumeMax" class="form-control" value="${memberVo.resumeMax}" type="number">

                            </div>
                        </div>

                        <div class="row mb-3">
                            <label for="createAt" class="col-sm-3 col-form-label text-end fw-bold">가입일</label>
                            <div class="col-sm-9">
                                <input id="createAt" name="createAt" class="form-control-plaintext"
                                    value="<fmt:formatDate value="${memberVo.createAt}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <label for="modifiedAt" class="col-sm-3 col-form-label text-end fw-bold">수정일</label>
                            <div class="col-sm-9">
                                <input id="modifiedAt" name="modifiedAt" class="form-control-plaintext"
                                    value="<fmt:formatDate value="${memberVo.modifiedAt}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <label for="provider" class="col-sm-3 col-form-label text-end fw-bold">가입 유형</label>
                            <div class="col-sm-9">
                                <input id="provider" name="provider" class="form-control-plaintext" value="${memberVo.provider}" readonly>

                        </div>
                    
                        <div class="row mt-4">
                            <div class="offset-sm-3 col-sm-9 d-flex gap-2">
                                <button type="submit" id="submitBtn" class="add-btn">
                                    <i class="bi bi-check-circle"></i> 수정
                                </button>
                                <button type="reset" class="btnStyle">
                                    <i class="bi bi-arrow-clockwise"></i> 초기화
                                </button>
                                <button type="button" class="delete-btn" id="delete">
                                    <i class="bi bi-person-x"></i> 탈퇴
                                </button>
                                <a href="/admin/member/individual?page=${param.page}&keyword=${param.keyword}" class="back-btn">
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


<script src="/js/admin/member/individualDetail.js"></script>

</body>
</html>
