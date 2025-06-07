<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>채용정보 메인</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/recruitment/recruitmentView.css">
    <link rel="stylesheet" href="/css/recruitment/recruitmentNav.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>



<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>


<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>


<body>
<jsp:include page="../common/header.jsp"/>

<div id="container">
    <div id="containerWrap">

        <!-- 검색 필터 영역 -->
        <div id="searchSection">
            <h2>검색조건</h2>
            <div id="filters">
                <!-- 드롭다운 버튼  -->
                <div class="dropdown">
                    <button type="button" class="dropdown-toggle">직군·직무 ▼</button>
                    <div class="dropdown-content">
                        <!-- 왼쪽: 직군 리스트 -->
                        <div id="jobGroupList" class="job-group-list">
                            <c:forEach var="item" items="${jobGroupList}">
                                <div
                                        class="job-group"
                                        data-code="${item.jobGroupId}"
                                        data-name="${item.jobGroupName}">
                                        ${item.jobGroupName}
                                        <span>  (${item.postCount})</span>
                                </div>
                            </c:forEach>
                        </div>



                        <!-- 오른쪽: 직무 리스트 -->
                        <div id="jobList" class="job-list">
                            <!-- 클릭 후 동적 생성 -->


                        </div>
                    </div>

                </div>

                <div class="dropdown">
                    <button type="button" class="dropdown-toggle">경력 ▼</button>
                    <div class="dropdown-content education-dropdown-content">
                        <label><input type="checkbox" name="career" value="신입" data-display="신입"> 신입</label>
                        <label><input type="checkbox" name="career" value="1~3년" data-display="1~3년"> 1~3년</label>
                        <label><input type="checkbox" name="career" value="3~5년" data-display="3~5년"> 3~5년</label>
                        <label><input type="checkbox" name="career" value="5년이상" data-display="5년이상"> 5년이상</label>
                    </div>
                </div>

                <div class="dropdown education-dropdown">
                    <button type="button" class="dropdown-toggle">학력 ▼</button>
                    <div class="dropdown-content education-dropdown-content">
                        <label><input type="checkbox" name="education" value="학력무관" data-display="학력무관"> 학력무관</label>
                        <label><input type="checkbox" name="education" value="대학교 졸업(4년)" data-display="대학교 졸업(4년)"> 대학교 졸업(4년)</label>
                        <label><input type="checkbox" name="education" value="대학 졸업(2,3년)" data-display="대학 졸업(2,3년)"> 대학 졸업(2,3년)</label>
                        <label><input type="checkbox" name="education" value="대학원 석사졸업" data-display="대학원 석사졸업"> 대학원 석사졸업</label>
                        <label><input type="checkbox" name="education" value="대학원 박사졸업" data-display="대학원 박사졸업"> 대학원 박사졸업</label>
                        <label><input type="checkbox" name="education" value="고등학교 졸업" data-display="고등학교 졸업"> 고등학교 졸업</label>
                    </div>
                </div>

                <div class="dropdown">
                    <button type="button" class="dropdown-toggle">전문분야 ▼</button>
                        <div class="dropdown-content">
                            <div class="skillList">

                            </div>
                        </div>
                </div>

                <!-- 적용, 초기화 버튼 -->
                <div id="filterActions">
                    <div id="filterSummary">
                        <button id="btnResetFilter">선택초기화 🔄</button>
                        <button id="btnSearchFiltered">선택된 <span id="filteredCount">0</span>건 검색하기</button>
                    </div>
                </div>
            </div>

            <!-- 선택된 필터들 표시 영역 -->
            <div id="selectedFilters">
                <!-- 직군·직무 -->
                <div id="searchJobGroup">
                    <div class="filter-section-title">직군·직무</div>
                    <ul id="divSelectedCon">
                        <!-- 선택된 직군·직무가 여기에 표시됩니다 -->
                    </ul>
                </div>
                
                <!-- 경력 -->
                <div id="selectedCareer">
                    <div class="filter-section-title">경력</div>
                    <ul id="divSelectedCareer">
                        <!-- 선택된 경력이 여기에 표시됩니다 -->
                    </ul>
                </div>
                
                <!-- 학력 -->
                <div id="selectedEducation">
                    <div class="filter-section-title">학력</div>
                    <ul id="divSelectedEducation">
                        <!-- 선택된 학력이 여기에 표시됩니다 -->
                    </ul>
                </div>
                
                <!-- 전문분야 -->
                <div id="selectedSkills">
                    <div class="filter-section-title">전문분야</div>
                    <ul id="divSelectedSkills">
                        <!-- 선택된 전문분야가 여기에 표시됩니다 -->
                    </ul>
                </div>
            </div>

        </div>

        <!-- 채용공고 리스트 -->
        <div id="jobListings">
            <c:forEach var="item" items="${recruitmentList}">
                <div class="job" data-jobpostid="${item.jobPostId}"
                                data-companyid="${item.companyId}">

                    <!-- 왼쪽: 회사명 -->
                    <div class="company-name">
                            ${item.companyName}
                    </div>

                    <!-- 가운데: 공고 정보 -->
                    <div class="job-info">
                        <div class="job-title">
                                ${item.title}
                        </div>
                        <div class="job-meta">

                            <span>‍🎓 ${item.education} </span> <span>🧑 ${item.careerType}</span>
                            <span>💼 ${item.jobName}</span>

                        </div>
                        <div class="job-meta-skill">
                            🧩
                            <c:forEach var="skill" items="${skillMap[item.jobPostId]}">
                                <div><span class="tag">${skill.tagName}</span></div>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- 오른쪽: 버튼 및 마감일 -->
                    <div class="job-action">
                        <c:choose>
                            <c:when test="${item.isContinuous == 0}">
                                <button class="apply-btn" type="button" onclick="">지원하기</button>
                            </c:when>
                            <c:otherwise>
                                <button class="apply-btn" type="button" disabled style="background: #eee; cursor: not-allowed;">마감됨</button>
                            </c:otherwise>
                        </c:choose>

                        <div class="deadline">
                            ~<fmt:formatDate value="${item.endDate}" pattern="MM/dd(E)" />
                        </div>
                    </div>

                </div>
            </c:forEach>
        </div>
        <div id="pagination">
            <c:if test="${pagination.existPrevPage}">
                <button class="page-btn" data-page="${pagination.startPage - 1}">«</button>
            </c:if>

            <c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}">
                <button class="page-btn" data-page="${i}" ${i == page ? 'disabled' : ''}>${i}</button>
            </c:forEach>

            <c:if test="${pagination.existNextPage}">
                <button class="page-btn" data-page="${pagination.endPage + 1}">»</button>
            </c:if>
        </div>

    </div>
</div>



<jsp:include page="../common/footer.jsp"/>
<div id="askConfirm">
</div>
<script>



    $('#insertForm').on('submit', function () {
        const markdown = editor.getHTML();
        console.log("🔥 submitEditor called");
        $('#hiddenContent').val(markdown);

        const selectedSkillIds = $('input[name="tagId"]:checked')
            .map(function () {
                return $(this).val();
            }).get().slice(0, 5);
        console.log("✅ selected skills:", selectedSkillIds);
        $('input[name="skills"]').val(selectedSkillIds.join(','));

        return true;
    });



</script>

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



<script src="/js/recruitment/recruitmentView.js"></script>
<script src="/js/recruitment/recruitmentBookMark.js"></script>

<div id="askConfirm" class="toast-popup"></div>

</body>

</html>
