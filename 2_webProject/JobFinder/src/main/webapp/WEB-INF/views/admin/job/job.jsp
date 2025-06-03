<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>직무관리 - 어디보잡 관리자</title>
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
    <link href="/css/admin/common.css" rel="stylesheet">
    
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
        .admin-header {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            border: 1px solid #f0f0f0;
            margin-bottom: 25px;
        }
        .admin-header h1 {
            margin: 0;
            color: #333;
            font-size: 1.8rem;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 12px;
        }
        .coming-soon-card {
            background: white;
            padding: 60px 40px;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            border: 1px solid #f0f0f0;
            text-align: center;
        }
        .coming-soon-icon {
            font-size: 4rem;
            color: #F69800;
            margin-bottom: 30px;
        }
        .coming-soon-title {
            font-size: 2rem;
            font-weight: 700;
            color: #333;
            margin-bottom: 15px;
        }
        .coming-soon-description {
            font-size: 1.1rem;
            color: #666;
            margin-bottom: 30px;
            line-height: 1.6;
        }
        .feature-list {
            background: #f8f9fa;
            padding: 30px;
            border-radius: 8px;
            margin-top: 30px;
        }
        .feature-list h3 {
            color: #F69800;
            font-size: 1.3rem;
            font-weight: 600;
            margin-bottom: 20px;
            text-align: center;
        }
        .feature-item {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 10px 0;
            color: #555;
        }
        .feature-item i {
            color: #F69800;
            font-size: 1.1rem;
        }
        .back-btn {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 12px 24px;
            background: #F69800;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.2s ease;
            margin-top: 20px;
        }
        .back-btn:hover {
            background: #e5890a;
            color: white;
            text-decoration: none;
            transform: translateY(-1px);
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
                <div class="admin-header">
                    <h1>
                        <i class="bi bi-gear-fill" style="color: #F69800;"></i>
                        직무 관리
                    </h1>
                </div>
                
                <div class="coming-soon-card">
                    <div class="coming-soon-icon">
                        <i class="bi bi-tools"></i>
                    </div>
                    <h2 class="coming-soon-title">직무 관리 기능 준비 중</h2>
                    <p class="coming-soon-description">
                        보다 나은 직무 관리 시스템을 준비하고 있습니다.<br>
                        곧 더욱 편리하고 강력한 기능으로 만나보실 수 있습니다.
                    </p>
                    
                    <div class="feature-list">
                        <h3><i class="bi bi-star"></i> 준비 중인 기능들</h3>
                        <div class="feature-item">
                            <i class="bi bi-plus-circle"></i>
                            <span>직무 추가 및 편집</span>
                        </div>
                        <div class="feature-item">
                            <i class="bi bi-pencil-square"></i>
                            <span>직무 상세 정보 관리</span>
                        </div>
                        <div class="feature-item">
                            <i class="bi bi-diagram-2"></i>
                            <span>직군과 직무 연결 관리</span>
                        </div>
                        <div class="feature-item">
                            <i class="bi bi-search"></i>
                            <span>고급 검색 및 필터 기능</span>
                        </div>
                        <div class="feature-item">
                            <i class="bi bi-graph-up"></i>
                            <span>직무별 통계 및 분석</span>
                        </div>
                    </div>
                    
                    <a href="/admin/job/jobGroup" class="back-btn">
                        <i class="bi bi-arrow-left"></i>
                        직군 관리로 이동
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>