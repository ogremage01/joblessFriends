<!-- 관리자 로그인 여부를 묻는 자바구문이 들어가야 할 부분 -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>관리자 페이지 - 어디보잡</title>
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
    <link rel="stylesheet" href="/css/admin/common.css">
    <link rel="stylesheet" href="/css/admin/adminStyle.css">
</head>
<body>

<div id="container">
    <div id="containerWrap">
        <div class="admin-main-container">
            <!-- 사이드바 영역 -->
            <div class="admin-main-sidebar">
                <jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
            </div>
            
            <!-- 메인 컨텐츠 영역 -->
            <div class="admin-main-content">
                <div class="admin-welcome">
                    <h1>관리자 대시보드</h1>
                    <p>어디보잡 사이트 관리 시스템에 오신 것을 환영합니다.</p>
                </div>
                
                <!-- 통계 카드들 -->
                <div class="admin-main-stats">
                    <div class="admin-main-stat-card">
                        <h3><i class="bi bi-people"></i> 전체 회원</h3>
                        <div class="admin-main-stat-number">${memberCount != null ? memberCount : 0}</div>
                        <div class="admin-main-stat-description">이번 달 +${memberMonthlyIncrease != null ? memberMonthlyIncrease : 0}명 증가</div>
                    </div>
                    <div class="admin-main-stat-card">
                        <h3><i class="bi bi-building"></i> 기업 회원</h3>
                        <div class="admin-main-stat-number">${companyCount != null ? companyCount : 0}</div>
                        <div class="admin-main-stat-description">이번 달 +${companyMonthlyIncrease != null ? companyMonthlyIncrease : 0}개 기업 등록</div>
                    </div>
                    <div class="admin-main-stat-card">
                        <h3><i class="bi bi-briefcase"></i> 채용 공고</h3>
                        <div class="admin-main-stat-number">${recruitmentCount != null ? recruitmentCount : 0}</div>
                        <div class="admin-main-stat-description">활성 공고 기준</div>
                    </div>
                    <div class="admin-main-stat-card">
                        <h3><i class="bi bi-chat-dots"></i> 커뮤니티 글</h3>
                        <div class="admin-main-stat-number">${communityCount != null ? communityCount : 0}</div>
                        <div class="admin-main-stat-description">이번 주 +${communityWeeklyIncrease != null ? communityWeeklyIncrease : 0}개 게시물</div>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

</body>
</html>