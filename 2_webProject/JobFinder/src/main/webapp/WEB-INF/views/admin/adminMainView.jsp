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
    
    <style>
        .admin-container {
            display: flex;
            min-height: 100vh;
        }
        .admin-sidebar {
            width: 280px;
            background-color: #f8f9fa;
            border-right: 1px solid #dee2e6;
        }
        .admin-main {
            flex: 1;
            padding: 30px;
            background-color: #ffffff;
        }
        .admin-welcome {
            background: linear-gradient(135deg, #F69800 0%, #ff9500 100%);
            color: white;
            padding: 40px;
            border-radius: 15px;
            margin-bottom: 30px;
            box-shadow: 0 4px 15px rgba(246, 152, 0, 0.2);
        }
        .admin-welcome h1 {
            margin: 0 0 10px 0;
            font-size: 2.5rem;
            font-weight: 700;
        }
        .admin-welcome p {
            margin: 0;
            font-size: 1.1rem;
            opacity: 0.9;
        }
        .admin-stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            border: 1px solid #f0f0f0;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        .stat-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.12);
        }
        .stat-card h3 {
            margin: 0 0 10px 0;
            color: #F69800;
            font-size: 1.3rem;
            font-weight: 600;
        }
        .stat-number {
            font-size: 2.5rem;
            font-weight: 700;
            color: #333;
            margin: 10px 0;
        }
        .stat-description {
            color: #666;
            font-size: 0.9rem;
        }
        .quick-actions {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            border: 1px solid #f0f0f0;
        }
        .quick-actions h3 {
            margin: 0 0 20px 0;
            color: #333;
            font-size: 1.3rem;
            font-weight: 600;
        }
        .action-buttons {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
        }
        .action-btn {
            padding: 15px 20px;
            border: 2px solid #F69800;
            background: white;
            color: #F69800;
            text-decoration: none;
            border-radius: 8px;
            text-align: center;
            font-weight: 600;
            transition: all 0.2s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }
        .action-btn:hover {
            background: #F69800;
            color: white;
            text-decoration: none;
        }
    </style>
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
                <div class="admin-welcome">
                    <h1>관리자 대시보드</h1>
                    <p>어디보잡 사이트 관리 시스템에 오신 것을 환영합니다.</p>
                </div>
                
                <!-- 통계 카드들 -->
                <div class="admin-stats">
                    <div class="stat-card">
                        <h3><i class="bi bi-people"></i> 전체 회원</h3>
                        <div class="stat-number">${memberCount != null ? memberCount : 0}</div>
                        <div class="stat-description">이번 달 +${memberMonthlyIncrease != null ? memberMonthlyIncrease : 0}명 증가</div>
                    </div>
                    <div class="stat-card">
                        <h3><i class="bi bi-building"></i> 기업 회원</h3>
                        <div class="stat-number">${companyCount != null ? companyCount : 0}</div>
                        <div class="stat-description">이번 달 +${companyMonthlyIncrease != null ? companyMonthlyIncrease : 0}개 기업 등록</div>
                    </div>
                    <div class="stat-card">
                        <h3><i class="bi bi-briefcase"></i> 채용 공고</h3>
                        <div class="stat-number">${recruitmentCount != null ? recruitmentCount : 0}</div>
                        <div class="stat-description">활성 공고 기준</div>
                    </div>
                    <div class="stat-card">
                        <h3><i class="bi bi-chat-dots"></i> 커뮤니티 글</h3>
                        <div class="stat-number">${communityCount != null ? communityCount : 0}</div>
                        <div class="stat-description">이번 주 +${communityWeeklyIncrease != null ? communityWeeklyIncrease : 0}개 게시물</div>
                    </div>
                </div>
                
                <!-- 빠른 작업 -->
                <div class="quick-actions">
                    <h3><i class="bi bi-lightning"></i> 빠른 작업</h3>
                    <div class="action-buttons">
                        <a href="/admin/member/individual" class="action-btn">
                            <i class="bi bi-person-check"></i>
                            회원 관리
                        </a>
                        <a href="/admin/recruitment" class="action-btn">
                            <i class="bi bi-briefcase-fill"></i>
                            공고 관리
                        </a>
                        <a href="/admin/community/post" class="action-btn">
                            <i class="bi bi-chat-left-text"></i>
                            게시물 관리
                        </a>
                        <a href="/admin/job/jobGroup" class="action-btn">
                            <i class="bi bi-diagram-3"></i>
                            직군/직무 관리
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

</body>
</html>