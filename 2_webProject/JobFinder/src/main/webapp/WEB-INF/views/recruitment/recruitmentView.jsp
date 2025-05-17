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
                        <label><input type="checkbox" name="career" value="신입"> 신입</label>
                        <label><input type="checkbox" name="career" value="경력직"> 1~3년</label>
                        <label><input type="checkbox" name="career" value="경력직"> 3~5년</label>
                        <label><input type="checkbox" name="career" value="경력직"> 5년이상</label>
                    </div>
                </div>

                <div class="dropdown education-dropdown">
                    <button type="button" class="dropdown-toggle">학력 ▼</button>
                    <div class="dropdown-content education-dropdown-content">
                        <label><input type="checkbox" name="education" value="학력무관"> 학력무관</label>
                        <label><input type="checkbox" name="education" value="대학 졸업(4년)"> 대학교 졸업(4년)</label>
                        <label><input type="checkbox" name="education" value="대학 졸업(2,3년)"> 대학 졸업(2,3년)</label>
                        <label><input type="checkbox" name="education" value="대학원 석사졸업"> 대학원 석사졸업</label>
                        <label><input type="checkbox" name="education" value="대학원 박사졸업"> 대학원 박사졸업</label>
                        <label><input type="checkbox" name="education" value="고등학교 졸업"> 고등학교 졸업</label>
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
                        <button id="btnResetFilters">선택초기화 🔄</button>
                        <button id="btnSearchFiltered">선택된 <span id="filteredCount">0</span>건 검색하기</button>
                    </div>
                </div>
            </div>

            <div id="searchJobGroup">
                <ul id="divSelectedCon">

                </ul>
            </div>


            <label>스킬 선택 (<span id="skill-count">0</span>/3)</label>
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
                                ${item.title} <span class="star">★</span>
                        </div>
                        <div class="job-meta">

                            <span>🧑‍💻 지원자격:${item.education} </span> <span>🎓 경력: ${item.careerType}</span>
                            <span>💼 채용직: ${item.jobName}</span>

                        </div>
                        <div class="job-meta-skill">
                            🧩 스킬:
                            <c:forEach var="skill" items="${skillMap[item.jobPostId]}">
                                <span class="tag">${skill.tagName}</span>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- 오른쪽: 버튼 및 마감일 -->
                    <div class="job-action">
                        <button class="apply-btn" type="button" onclick="">지원하기</button>
                        <div class="deadline">~<fmt:formatDate value="${item.endDate}" pattern="MM/dd(E)" /></div>
                    </div>
                </div>
            </c:forEach>
        </div>


    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
<div id="askConfirm">
</div>
<script>
    const editor = new toastui.Editor({
        el: document.querySelector('#content'), // 에디터를 적용할 요소 (컨테이너)
        height: '500px',                        // 에디터 영역의 높이 값 (500px로 지정)
        initialEditType: 'markdown',            // 최초로 보여줄 에디터 타입 (markdown || wysiwyg 중에 markdown버전으로 처음 보여짐)
        initialValue: '',                       // 내용의 초기 값으로, 반드시 마크다운 문자열 형태여야 함(아무내용 없음)
        previewStyle: 'vertical',               // 마크다운 프리뷰 스타일 (tab || vertical)
        placeholder: '내용을 입력해 주세요.',
    });



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
<script src="/js/recruitment/recruitmentView.js"></script>


</body>

</html>
