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
    <!-- SweetAlert2 CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">

    <!-- SweetAlert2 JS -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>


<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

<body>
<jsp:include page="../common/insertHeader.jsp"/>

<div id="container">
    <div id="containerWrap">


        <form id="insertForm" action="${pageContext.request.contextPath}/Recruitment/insert" method="post" enctype="multipart/form-data">
        <div class="InsertMain">

                <!-- 제목 -->
                <div class="InsertTitle box-section">
                    <label class="section-title">채용공고 타이틀</label>
                    <input type="text" style="width: 100%" name="title" placeholder="공고 제목을 입력하세요" required />
                </div>
            <div class="InsertTitle box-section">
                <label class="section-title">채용공고 대표 이미지 업로드</label>

                <input type="file" name="jobImgFile" accept="image/*" id="jobImgFile" />
                <button type="button" id="generateTitle" class="template-btn">이미지 미리보기</button>

                <div id="fileInfoBox" style="margin-top: 8px; display: none;">
                    <span id="fileNameText" style="margin-right: 6px;"></span>
                    <button type="button" id="removeFileBtn" style="background: none; border: none; color: red; font-size: 14px; cursor: pointer;">✕</button>
                </div>
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
<%--                            <button type="button" class="remove-job">x</button>--%>
                        </div>
                    </div>

<%--                    <button type="button" class="add-job">+ 추가</button>--%>
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
            <div class="InsertWelfare box-section">
                <label class="section-title">복리후생</label>

                <div class="flex-row">
                    <input type="text" id="welfareInput" class="form-control welfare-text"
                           placeholder="예) 사내 피트니스, 카페, 연 100만원 포인트 제공" />
                    <button type="button" id="addWelfareBtn" class="add-job">+ 추가</button>
                </div>

                <div id="welfareList" class="job-set">
                    <!-- 아래와 같은 div가 동적으로 추가됨 -->
                    <!--
                    <div class="flex-row welfare-item">
                        <span>사내 피트니스</span>
                        <button type="button" class="remove-welfare">X</button>
                    </div>
                    -->
                </div>

                <input type="hidden" name="welfareList" />
            </div>

            <div class="box-section">
                <label class="section-title">사전질문 등록하기</label>
                <div style="display: flex;">
                    <button id="btnAddQuestion" class="template-btn Question" type="button">사전질문 등록</button>
                    <ul class="Question" >

                    </ul>
                </div>
                <!-- 질문 값 전송용 hidden input -->
                <input type="hidden" name="question1" id="questionHidden1">
                <input type="hidden" name="question2" id="questionHidden2">
                <input type="hidden" name="question3" id="questionHidden3">

            </div>
                <div class="InsertTemplate box-section">
                    <label class="section-title">🧩 템플릿 생성 도우미</label>


                        <label class="section-title">📐 상세 템플릿 유형</label>
                        <select name="templateType" class="templateType" required>
                            <option value="default">기본형</option>
                            <option value="self">직접작성 및 이미지 공고</option>

                        </select>
                    <button type="button" id="generateTemplate" class="template-btn">📄 템플릿 미리보기</button>
                    <div id="templatePreview" class="template-preview-box">조건을 선택하고 미리보기를 눌러주세요.</div>

                </div>

                <!-- 버튼 -->
                <div class="form-footer">

                    <button type="button" class="cancel-btn">작성취소</button>
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
    window.tempKey = crypto.randomUUID().trim().replaceAll(",", "");
    const editor = new toastui.Editor({
        el: document.querySelector('#content'),
        height: '500px',
        initialEditType: 'wysiwyg',
        previewStyle: 'vertical',
        initialValue: '',
        placeholder: '내용을 입력해 주세요.',
        hooks: {
            addImageBlobHook: function (blob, callback) {
                const formData = new FormData();
                console.log("🔥 이미지 업로드 시작됨");
                console.log("📦 tempKey = ", tempKey);
                formData.append('image', blob);
                formData.append('tempKey', tempKey);

                $.ajax({
                    url: '/Recruitment/uploadImage',
                    method: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        console.log("업로드 응답:", res);
                        if (res && res.file && res.file.url) {
                            callback(res.file.url, '업로드 이미지');
                        } else {
                            alert("⚠️ 서버 응답에 이미지 URL이 없습니다.");
                        }
                    },
                    error: function () {
                        alert('이미지 업로드 실패');
                    }
                });

                return false;
            }
        }
    });



    $('#insertForm').on('submit', function () {
        const htmlContent = editor.getHTML();
        $('#hiddenContent').val(htmlContent);

        const selectedSkillIds = $('input[name="tagId"]:checked')
            .map(function () { return $(this).val(); })
            .get().slice(0, 5);
        $('input[name="skills"]').val(selectedSkillIds.join(','));

        return true;
    });

    window.editor = editor;
</script>


</body>

</html>
