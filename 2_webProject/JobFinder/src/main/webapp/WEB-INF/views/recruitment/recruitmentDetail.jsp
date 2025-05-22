<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>

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
                    <h2>${recruitmentDetailVo.recruitment.title}</h2>
                    <div class="company-name">${recruitmentDetailVo.recruitment.companyName}</div>
                </div>

                <div class="detail-info-grid">
                    <div><span class="detail-info-label">경력</span>${recruitmentDetailVo.recruitment.careerType}
                    </div>
                    <div><span class="detail-info-label">근무시간</span>${recruitmentDetailVo.recruitment.workHours}</div>
                    <div><span class="detail-info-label">접수 시작일</span><fmt:formatDate value="${recruitmentDetailVo.recruitment.startDate}" pattern="YY/MM/dd(E)" /></div>
                    <div><span class="detail-info-label">접수 마감일</span><fmt:formatDate value="${recruitmentDetailVo.recruitment.endDate}" pattern="YY/MM/dd(E)" /></div>
                    <div><span class="detail-info-label">학력</span>${recruitmentDetailVo.recruitment.education}</div>
                    <div><span class="detail-info-label">급여</span>연봉 ${recruitmentDetailVo.recruitment.salary}만원</div>
                </div>
            </div>

            <!-- 오른쪽 사이드 영역 -->
            <div class="detail-sidebar">
                <fmt:formatDate value="${recruitmentDetailVo.recruitment.endDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="formattedEndDate"/>
                <span id="endDateRaw" data-deadline="${formattedEndDate}" style="display:none;"></span>

            <%--               JS데이터수집용 // NONE처리 --%>
                <div class="dday">접수 마감일까지 남은 시간<br><span id="deadlineCountdown">.</span></div>
                <div class="btn-group">
                    <button class="btn-bookmark">★ 공고 찜하기</button>
                    <button class="btn-apply">지원하기</button>
                </div>
            </div>

        </div>

        <div class="detail-body">
            <div class="detail-content-wrapper">
<%--                템플릿 타입에 따른 별도처리 --%>
    <c:choose>
        <c:when test="${recruitmentDetailVo.recruitment.templateType eq 'self'}">
            <!-- self 템플릿 -->
            <section class="detail-section">
                <h3>공고 내용</h3>
                <div><c:out value="${recruitmentDetailVo.recruitment.content}" escapeXml="false" /></div>
            </section>
        </c:when>

        <c:otherwise>
                    <!-- default 템플릿 -->

                    <!-- 1. 스킬 -->
                    <section class="detail-section">
                        <h3>요구 스킬</h3>
                        <div class="tag-list">
                            <c:forEach var="tag" items="${recruitmentDetailVo.skill}">
                                <span class="tag">${tag.tagName}</span>
                            </c:forEach>
                        </div>
                    </section>

                    <!-- 2. 상세 내용 -->
                    <section class="detail-section">
                        <h3>상세 내용</h3>
                        <p><c:out value="${recruitmentDetailVo.recruitment.content}" escapeXml="false" /></p>
                        <h3>복리후생</h3>
                        <ul>
                            <c:forEach var="welfare" items="${recruitmentDetailVo.welfare}">
                                <li>${welfare.benefitText}</li>
                            </c:forEach>
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
                            <li><strong>기업명:</strong>  ${recruitmentDetailVo.company.companyName}</li>
                            <li><strong>담당자명:</strong> ${recruitmentDetailVo.company.representative}</li>
                            <li><strong>연락처:</strong> ${recruitmentDetailVo.company.tel}</li>
                            <li><strong>주소:</strong> ${recruitmentDetailVo.company.address}</li>
                        </ul>
                    </section>
                </c:otherwise>
            </c:choose>

            </div>
        </div>




    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script src="/js/recruitment/recruitmentDetail.js"></script>

</body>

</html>
