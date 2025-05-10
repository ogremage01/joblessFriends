<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>채용공고 상세</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/recruitment/recruitmentDetail.css">

</head>





<body>
<jsp:include page="../common/header.jsp"/>

<div id="container">
    <div id="containerWrap">
        <div class="detail-wrapper">

            <!-- 왼쪽 본문 -->
            <div class="detail-main">
                <div class="detail-header">
                    <h2>공고제목 공고제목</h2>
                    <div class="company-name">기업명</div>
                </div>

                <div class="detail-info-grid">
                    <div><span class="detail-info-label">경력</span>신입·경력</div>
                    <div><span class="detail-info-label">근무시간</span>주5일(월~금)</div>
                    <div><span class="detail-info-label">접수 시작일</span>2025.04.23 (수) 17:00</div>
                    <div><span class="detail-info-label">접수 마감일</span>2025.05.03 (토) 23:00</div>
                    <div><span class="detail-info-label">학력</span>학력무관</div>
                    <div><span class="detail-info-label">급여</span>연봉 4,500 만원</div>
                </div>
            </div>

            <!-- 오른쪽 사이드 영역 -->
            <div class="detail-sidebar">
                <div class="dday">D-10</div>
                <div class="btn-group">
                    <button class="btn-bookmark">★ 공고 찜하기</button>
                    <button class="btn-apply">지원하기</button>
                </div>
            </div>

        </div>
        <div class="detail-body">
            <div class="detail-content-wrapper">

                <!-- 1. 스킬 -->
                <section class="detail-section">
                    <h3>요구 스킬</h3>
                    <div class="tag-list">
                        <span class="tag">Java</span>
                        <span class="tag">Spring</span>
                        <span class="tag">JPA</span>
                        <span class="tag">Oracle</span>
                    </div>
                </section>

                <!-- 2. 상세 내용 -->
                <section class="detail-section">
                    <h3>상세 내용</h3>
                    <p>
                        본 공고는 아모레퍼시픽 백엔드 개발팀의 인재 채용 공고입니다.<br>
                        Java 및 Spring 기반 웹 시스템의 유지보수와 신규 개발을 수행하며,<br>
                        팀 내 협업과 기술 공유가 활발하게 이루어집니다.
                    </p>
                    <ul>
                        <li>정규직 / 주 5일 근무 / 유연 출퇴근제</li>
                        <li>우대사항: JPA 실무 경험, 대용량 트래픽 서비스 운영 경험</li>
                        <li>복지: 사내 피트니스, 카페, 연 100만원 포인트 제공</li>
                    </ul>
                </section>

                <!-- 3. 전형 절차 -->
                <section class="detail-section">
                    <h3>전형 절차</h3>
                    <ol class="step-list">
                        <li>서류 전형</li>
                        <li>실무 면접</li>
                        <li>임원 면접</li>
                        <li>최종 합격</li>
                    </ol>
                </section>

                <!-- 4. 제출 서류 -->
                <section class="detail-section">
                    <h3>제출 서류</h3>
                    <ul>
                        <li>이력서</li>
                        <li>자기소개서 (자유 양식)</li>
                    </ul>
                </section>

                <!-- 5. 기업 정보 -->
                <section class="detail-section">
                    <h3>기업 정보</h3>
                    <ul>
                        <li><strong>기업명:</strong> 아모레퍼시픽</li>
                        <li><strong>담당자명:</strong> 김하윤</li>
                        <li><strong>연락처:</strong> 02-1234-5678</li>
                        <li><strong>주소:</strong> 서울특별시 용산구 이태원로 123</li>
                    </ul>
                </section>

            </div>
        </div>




    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script src="/js/recruitment/recruitmentDetail.js"></script>

</body>

</html>
