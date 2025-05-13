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

</head>





<body>
<jsp:include page="../common/header.jsp"/>

<div id="container">
    <div id="containerWrap">


        <form action="/recruitment/insert" method="post">
            <div class="InsertMain">

                <!-- 제목 -->
                <div class="InsertTitle box-section">
                    <input type="text" name="title" placeholder="공고 제목을 입력하세요" required />
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
                            </label>
                        </div>
                    </div>

                    <div class="InsertQualifications box-section" style="flex: 1;">
                        <label class="section-title">지원자격</label>
                        <div class="flex-row">

                            <select name="careerType" class="careerType" required>
                                <option value="">경력 선택</option>
                                <option value="신입">신입</option>
                                <option value="경력">경력</option>
                                <option value="신입·경력">신입·경력</option>
                            </select>
                            <select name="education" class="education" required>
                                <option value="">학력 선택</option>
                                <option value="고졸">고졸</option>
                                <option value="대졸(4년)">대학교(4년)</option>
                            </select>
                        </div>
                    </div>
                </div>

                <!-- 채용부문 -->
                <div class="InsertJob box-section">
                    <label class="section-title">채용부문</label>

                    <div class="job-set">
                        <div class="flex-row">
                            <select name="jobGroups" class="jobGroupName">
                                <option value="">직군 선택</option>
                            </select>
                            <select name="jobs" class="jobName">
                                <option value="">직무 선택</option>
                            </select>
                            <button type="button" class="remove-job">x</button>
                        </div>
                    </div>

                    <button type="button" class="add-job">+ 추가</button>
                </div>

                <!-- 스킬 태그 -->
                <div class="InsertTag box-section">
                    <label class="section-title">스킬 (3/20)</label>
                    <input type="text" class="tag-input" placeholder="스킬을 입력해주세요" />
                    <div class="tag-list">
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
                    <textarea name="content" rows="10" placeholder="내용을 입력하세요" required></textarea>
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

</body>

</html>
