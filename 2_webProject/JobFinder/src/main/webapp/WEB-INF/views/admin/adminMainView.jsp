
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
        <div class="admin-container">
            <!-- 사이드바 영역 -->
            <div class="admin-sidebar">
                <jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
            </div>
            
            <!-- 메인 컨텐츠 영역 -->
            <div class="admin-main">
                <div class="admin-header">
                    <h1>
                        <i class="bi bi-speedometer2" style="color: #F69800;"></i>
                        관리자 대시보드
                    </h1>
                </div>
                
                <div class="admin-content">
                    <div class="admin-welcome">
                        <p>어디보잡 사이트 관리 시스템에 오신 것을 환영합니다.</p>
                    </div>
                    
                    <!-- 통계 카드들 -->
                    <div class="admin-main-stats">
<!--                         <div class="admin-main-stat-card"> -->
<!--                             <h3><i class="bi bi-people"></i> 전체 회원</h3> -->
<%--                             <div class="admin-main-stat-number">${memberCount != null ? memberCount : 0}</div> --%>
<%--                             <div class="admin-main-stat-description">이번 달 +${memberMonthlyIncrease != null ? memberMonthlyIncrease : 0}명 증가</div> --%>
<!--                         </div> -->
<!--                         <div class="admin-main-stat-card"> -->
<!--                             <h3><i class="bi bi-building"></i> 기업 회원</h3> -->
<%--                             <div class="admin-main-stat-number">${companyCount != null ? companyCount : 0}</div> --%>
<%--                             <div class="admin-main-stat-description">이번 달 +${companyMonthlyIncrease != null ? companyMonthlyIncrease : 0}개 기업 등록</div> --%>
<!--                         </div> -->
<!--                         <div class="admin-main-stat-card"> -->
<!--                             <h3><i class="bi bi-briefcase"></i> 채용 공고</h3> -->
<%--                             <div class="admin-main-stat-number">${recruitmentCount != null ? recruitmentCount : 0}</div> --%>
<!--                             <div class="admin-main-stat-description">활성 공고 기준</div> -->
<!--                         </div> -->
<!--                         <div class="admin-main-stat-card"> -->
<!--                             <h3><i class="bi bi-chat-dots"></i> 커뮤니티 글</h3> -->
<%--                             <div class="admin-main-stat-number">${communityCount != null ? communityCount : 0}</div> --%>
<%--                             <div class="admin-main-stat-description">이번 주 +${communityWeeklyIncrease != null ? communityWeeklyIncrease : 0}개 게시물</div> --%>
<!--                         </div> -->

						<div class="admin-main-stat-card canvasWrap">
                            <h3><i class="bi bi-people"></i>개인회원</h3>
							<canvas id="memberChart"></canvas>
						</div>
                        
                        <div class="admin-main-stat-card canvasWrap">
                            <h3><i class="bi bi-building"></i>기업회원</h3>
							<canvas id="companyChart"></canvas>
						</div>
						
						<div class="admin-main-stat-card canvasWrap">
                            <h3><i class="bi bi-briefcase"></i>채용공고</h3>
							<canvas id="recruitmentChart"></canvas>
						</div>
						
						<div class="admin-main-stat-card canvasWrap">
                            <h3><i class="bi bi-chat-dots"></i>커뮤니티</h3>
							<canvas id="communityChart"></canvas>
						</div>
						
						<div class="admin-main-stat-card canvasWrap">
                            <h3><i class="bi bi-file-earmark-text"></i>이력서</h3>
							<canvas id="applyChart"></canvas>
						</div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.0/chart.umd.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>
<script>

const memberIncrease = [
    ${memberIncrease.previousResult},
    ${memberIncrease.currentResult}
];
const companyIncrease = [
    ${companyIncrease.previousResult},
    ${companyIncrease.currentResult}
];
const recruitmentRegistCount = [
    ${recruitmentRegistCount.previousResult},
    ${recruitmentRegistCount.currentResult}
];
const communityIncrease = [
    ${communityIncrease.previousResult},
    ${communityIncrease.currentResult}
];
const applyCount = [
    ${applyCount.previousResult},
    ${applyCount.currentResult}
];

// bar 컬러 변수

	// 개인 회원
	var backgroundColorA = 'rgba(255, 0, 0, 0.5)';
	var backgroundColorHoverA = 'rgba(255, 0, 0, 0.6)';

	// 기업 회원
	var backgroundColorB = 'rgba(0, 0, 255, 0.5)';
	var backgroundColorHoverB = 'rgba(0, 0, 255, 0.6)';

	// 커뮤니티
	var backgroundColorC = 'rgba(255, 208, 0, 0.5)';
	var backgroundColorHoverC = 'rgba(255, 208, 0, 0.6)';

	// 채용 공고
	var backgroundColorD = 'rgba(6, 214, 183, 0.5)';
	var backgroundColorHoverD = 'rgba(6, 214, 183, 0.6)';

	// 이력서
	var backgroundColorE = 'rgba(197, 116, 227, 0.5)';
	var backgroundColorHoverE = 'rgba(197, 116, 227, 0.6)';

// 차트 전역 설정
Chart.defaults.set({
  scales: {
    y: {
      suggestedMax: 100,
      ticks: {
          stepSize: 10,
          autoSkip: false,
      }
    }
  }
});


// 범례(legend)간격 조절
const spacerPlugin = {
  id: 'legendSpacer',
  beforeInit(chart) {
    const originalFit = chart.legend.fit;
    chart.legend.fit = function fit() {
      originalFit.bind(chart.legend)();
      this.height += 10; // 범례 아래 여백 추가
    };
  }
};
		
// 라벨 플러그인 세팅
Chart.defaults.set('plugins.datalabels', {
 font: {
        size: 16,
        weight: 'bold'
      }
});

// bar max 너비
var maxBarThickness = 120;
	
// 개인회원 증가 수
const memberChartObj = document.getElementById('memberChart').getContext('2d');
const memberChart = new Chart(memberChartObj, {
    type: 'bar',
    data: {
        labels: ['지난 달', '이번 달'],
        datasets: [{
            label: '회원 증가 수',
            data: memberIncrease,
            backgroundColor: backgroundColorA,
            hoverBackgroundColor: backgroundColorHoverA,
            maxBarThickness: maxBarThickness,
            datalabels: {
               align: 'end',
               anchor: 'end',
             },
        }]
    },
    plugins: [ChartDataLabels, spacerPlugin],
});

//기업회원 증가 수
const companyChartObj = document.getElementById('companyChart').getContext('2d');
const companyChart = new Chart(companyChartObj, {
    type: 'bar',
    data: {
        labels: ['지난 달', '이번 달'],
        datasets: [{
            label: '회원 증가 수',
            data: companyIncrease,
            backgroundColor: backgroundColorB,
            hoverBackgroundColor: backgroundColorHoverB,
            maxBarThickness: maxBarThickness,
            datalabels: {
                align: 'end',
                anchor: 'end',
              },
        }]
    },
    plugins: [ChartDataLabels, spacerPlugin],
});

//채용공고 증가 수
const recruitmentChartObj = document.getElementById('recruitmentChart').getContext('2d');
const recruitmentChart = new Chart(recruitmentChartObj, {
    type: 'bar',
    data: {
        labels: ['지난 달', '이번 달'],
        datasets: [{
            label: '공고 증가 수',
            data: recruitmentRegistCount,
            backgroundColor: backgroundColorD,
            hoverBackgroundColor: backgroundColorHoverD,
            maxBarThickness: maxBarThickness,
            datalabels: {
                align: 'end',
                anchor: 'end',
              },
        }]
    },
    plugins: [ChartDataLabels, spacerPlugin],
    
});

//커뮤니티 게시글 증가 수
const communityChartObj = document.getElementById('communityChart').getContext('2d');
const communityChart = new Chart(communityChartObj, {
    type: 'bar',
    data: {
        labels: ['지난 달', '이번 달'],
        datasets: [{
            label: '게시글 증가 수',
            data: communityIncrease,
            backgroundColor: backgroundColorC,
            hoverBackgroundColor: backgroundColorHoverC,
            maxBarThickness: maxBarThickness,
            datalabels: {
                align: 'end',
                anchor: 'end',
              },
        }]
    },
    plugins: [ChartDataLabels, spacerPlugin],
    
});

// 이력서 지원 수
const applyChartObj = document.getElementById('applyChart').getContext('2d');
const applyChart = new Chart(applyChartObj, {
    type: 'bar',
    data: {
        labels: ['지난 달', '이번 달'],
        datasets: [{
            label: '이력서 지원 수',
            data: applyCount,
            backgroundColor: backgroundColorE,
            hoverBackgroundColor: backgroundColorHoverE,
            maxBarThickness: maxBarThickness,
            datalabels: {
                align: 'end',
                anchor: 'end',
              },
        }]
    },
    plugins: [ChartDataLabels, spacerPlugin],
});

</script>


</body>
</html>