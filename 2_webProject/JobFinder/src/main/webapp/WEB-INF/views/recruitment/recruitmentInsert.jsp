<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>기업 채용공고 추가</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/recruitment/recruitmentInsert.css">
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/5.5.1/github-markdown.min.css">
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

</head>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>


<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

<body>
<jsp:include page="../common/header.jsp"/>

<div id="container">
    <div id="containerWrap">


        <form id="insertForm" action="${pageContext.request.contextPath}/Recruitment/insert" method="post" onsubmit="return submitEditor();">
        <div class="InsertMain">

                <!-- 제목 -->
                <div class="InsertTitle box-section">
                    <input type="text" style="width: 100%" name="title" placeholder="공고 제목을 입력하세요" required />
                </div>

                <!-- 접수기간 & 지원자격 -->
                <div class="flex-wrap">
                    <div class="InsertDate box-section" style="flex: 1;">
                        <label class="section-title">접수기간</label>
                        <div class="flex-row">
                            <input type="date" name="startDate" class="CreDate" required />
                            <span>~</span>
                            <input type="date" name="endDate" class="endDate" required />
                            <label class="inline-checkbox">
                                <input type="checkbox" name="openEnded" /> 상시채용
<%--                                컬럼추가필요--%>
                            </label>
                        </div>
                    </div>

                    <div class="InsertQualifications box-section" style="flex: 1;">
                        <label class="section-title">지원자격</label>
                        <div class="flex-row">

                            <select name="careerType" class="careerType" required>
                                <option value="">경력 선택</option>
                                <option value="신입">신입</option>
                                <option value="1~3년">1~3년</option>
                                <option value="3~5년">3~5년</option>
                                <option value="5년이상">5년이상</option>

                            </select>
                            <select name="education" class="education" required>
                                <option value="">학력 선택</option>
                                <option value="학력무관">학력무관</option>
                                <option value="대학교 졸업(4년)">대학교 졸업(4년)</option>
                                <option value="대학 졸업(2,3년)">대학 졸업(2,3년)</option>
                                <option value="대학원 석사졸업">대학원 석사졸업</option>
                                <option value="대학원 박사졸업">대학원 박사졸업</option>
                                <option value="고등학교 졸업">고등학교 졸업</option>
                            </select>
                        </div>
                    </div>
                </div>

                <!-- 채용부문 -->
                <div class="InsertJob box-section">
                    <label class="section-title">채용부문</label>

                    <div class="job-set">
                        <div class="flex-row">
                            <select name="jobGroupId" class="jobGroupName select-box">
                                <option value="">직군 선택</option>
                                <c:forEach var="group" items="${jobGroupList}">
                                    <option value="${group.jobGroupId}">${group.jobGroupName}</option>
                                </c:forEach>
                            </select>


                            <select name="jobId" class="jobName select-box">
                                <option value="">직무 선택</option>
                            </select>
                            <button type="button" class="remove-job">x</button>
                        </div>
                    </div>

                    <button type="button" class="add-job">+ 추가</button>
                </div>

                <!-- 스킬 태그 -->
                <div class="InsertTag box-section">
                    <label class="section-title">스킬 (5개까지 선택가능)</label>

                    <div id="tag-list">
                        <!-- 자동 생성될 태그들 -->
                    </div>
                    <input type="hidden" name="skills" />
                </div>

                <!-- 근무조건 -->
                <div class="InsertJob box-section">
                    <label class="section-title">근무조건</label>
                    <div class="flex-row">
                        <select name="workHours" class="workHours">
                            <option value="">근무시간</option>
                            <option value="주5일(월~금)">주5일(월~금)</option>
                            <option value="주3일(격일제)">주3일(격일제)</option>
                            <option value="주5일(월~금)">유연근무제</option>
                            <option value="주5일(월~금)">면접 후 결정</option>
                        </select>
                        <select name="salaryType">
                            <option value="연봉">연봉</option>
                            <option value="월급">월급</option>
                        </select>
                        <input type="text" name="salary" class="salary" placeholder="금액입력" />
                        <span>만원</span>
                    </div>
                </div>

                <!-- 상세내용 -->
                <div class="InsertContent box-section">
                    <label class="section-title">상세내용</label>
                    <div id="content" class="contentBox" style="width: 100%"></div>



                    <input type="hidden" name="content" id="hiddenContent" />
                </div>
                <div class="InsertTemplate box-section">
                    <label class="section-title">🧩 템플릿 생성 도우미</label>
                    <button type="button" id="generateTemplate" class="template-btn">📄 템플릿 미리보기</button>
                    <div id="templatePreview" class="template-preview-box">조건을 선택하고 미리보기를 눌러주세요.</div>

                </div>

                <!-- 버튼 -->
                <div class="form-footer">
                    <button type="submit" class="submit-btn">작성완료</button>
                </div>
            </div>
        </form>



    </div>
</div>





<jsp:include page="../common/footer.jsp"/>

<script src="/js/recruitment/recruitmentInsert.js"></script>
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
</body>

</html>
