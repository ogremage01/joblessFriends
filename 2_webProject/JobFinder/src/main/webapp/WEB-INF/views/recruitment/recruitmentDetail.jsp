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
    <link rel="stylesheet" href="/css/recruitment/recruitmentNav.css">


    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>


    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>



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
<%--           중간영역 --%>

<!--             <div class="detail-middle"> -->
<!--                 <div class="detail-middle-body"> -->
<!--                     <div class="detail-middle-title"> -->
<!--                         <span class="applyCount">지원자수</span> -->
<%--                         <div>${recruitmentDetailVo.recruitment.isContinuous}명</div> --%>
<!--                     </div> -->
<!--                 </div> -->
<!--             </div> -->

            <!-- 오른쪽 사이드 영역 -->
            <div class="detail-sidebar">
                <fmt:formatDate value="${recruitmentDetailVo.recruitment.endDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="formattedEndDate"/>
                <span id="endDateRaw" data-deadline="${formattedEndDate}" style="display:none;"></span>

            <%--               JS데이터수집용 // NONE처리 --%>
                 <div class="dday">
	                <span>접수 마감까지 남은 시간</span>
	                <span> <span id="deadlineCountdown"></span></span>
	                <span> 지원자수 ${recruitmentDetailVo.recruitment.applicantCount}명</span>
                </div>

                <div class="btn-group">
                    <div class="job" data-jobpostid="${recruitmentDetailVo.recruitment.jobPostId}">
                        <c:choose>
                            <c:when test="${recruitmentDetailVo.recruitment.isContinuous == 0}">
                                <c:choose>
                                    <c:when test="${userType eq 'member'}">
                                        <c:choose>
                                            <c:when test="${bookMarked_JobPostId == null}">
                                                <div id="bookmark-Container">
                                                    <button class="btn-NonBookmark"
                                                            data-jobpostid="${recruitmentDetailVo.recruitment.jobPostId}" data-usertype="${sessionScope.userType}">
                                                        ★ 공고 찜하기
                                                    </button>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div id="bookmark-Container">
                                                    <button class="btn-bookmark"
                                                            data-jobpostid="${recruitmentDetailVo.recruitment.jobPostId}" data-usertype="${sessionScope.userType}">
                                                        ★ 공고 찜하기
                                                    </button>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <button class="btn-apply">지원하기</button>
                                    </c:when>
                                    <c:otherwise>
                                        <div id="bookmark-Container">
                                            <button type="button" disabled style="cursor: not-allowed; background: #eee;">
                                                개인회원만 가능합니다
                                            </button>
                                        </div>
                                        <button type="button" disabled style="cursor: not-allowed; background: #eee;">
                                            개인회원만 가능합니다
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <div id="bookmark-Container">
                                    <button type="button" disabled style="cursor: not-allowed; background: #eee;">
                                        마감됨
                                    </button>
                                </div>
                                <button class="apply-btn" type="button" disabled style="background: #eee; cursor: not-allowed;">
                                    마감됨
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>



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


		<div id="askConfirm">
		</div>

    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
<%-- 이후 js에서 처리  --%>
<script>
    const userType = '${userType}';

    const resumeList =
            <c:choose>
            <c:when test="${not empty resumeList}">
            [
                <c:forEach var="r" items="${resumeList}" varStatus="i">
                {
                    resumeId: '${r.resumeId}',
                    title: '${r.title}',
                    modifiedAt: '<fmt:formatDate value="${r.modifyDate}" pattern="MM/dd(E)" />',
                    matchScore: ${r.matchScore}
                }<c:if test="${!i.last}">,</c:if>
                </c:forEach>
            ]
                </c:when>
                <c:otherwise>
                []
        </c:otherwise>
        </c:choose>;
</script>

<script src="/js/recruitment/recruitmentDetail.js"></script>
<script src="/js/recruitment/recruitmentBookMark.js"></script>

</body>

</html>
