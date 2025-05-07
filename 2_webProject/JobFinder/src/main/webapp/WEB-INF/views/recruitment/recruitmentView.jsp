<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>채용정보 메인</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/recruitment/recruitmentView.css">

</head>





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
                                <div class="job-group" data-code="${item.jobGroupId}">
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
                    <div class="dropdown-content">
                        <label><input type="checkbox" name="career" value="신입"> 신입</label>
                        <label><input type="checkbox" name="career" value="경력직"> 경력직</label>
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
                    <button type="button" class="dropdown-toggle">스킬 ▼</button>
                    <div class="dropdown-content">
                        <label><input type="checkbox" name="skill" value="Java"> Java</label>
                        <label><input type="checkbox" name="skill" value="Python"> Python</label>
                        <label><input type="checkbox" name="skill" value="React"> React</label>
                    </div>
                </div>

                <!-- 적용, 초기화 버튼 -->
                <div id="filterActions">
                    <button type="button">적용</button>
                    <button type="button">초기화</button>
                </div>
            </div>
        </div>

        <!-- 채용공고 리스트 -->
        <div id="jobListings">

            <div class="job">
                <div class="job-info">
                    <h3>회사명</h3>
                    <p>직무: 개발자</p>
                    <p>부서: IT팀</p>
                    <p>마감일: 04/24(화)</p>
                </div>
                <button>지원하기</button>
            </div>

            <div class="job">
                <div class="job-info">
                    <h3>회사명</h3>
                    <p>직무: 디자이너</p>
                    <p>부서: 디자인팀</p>
                    <p>마감일: 04/24(화)</p>
                </div>
                <button>지원하기</button>
            </div>
            <div class="job">
                <div class="job-info">
                    <h3>회사명</h3>
                    <p>직무: 디자이너</p>
                    <p>부서: 디자인팀</p>
                    <p>마감일: 04/24(화)</p>
                </div>
                <button>지원하기</button>
            </div>
            <div class="job">
                <div class="job-info">
                    <h3>회사명</h3>
                    <p>직무: 디자이너</p>
                    <p>부서: 디자인팀</p>
                    <p>마감일: 04/24(화)</p>
                </div>
                <button>지원하기</button>
            </div>

        </div>

    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script src="/js/recruitment/recruitmentView.js"></script>

</body>

</html>
