<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>채용정보 메인</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-3fp9tS8p9A2Mq7Qz+S8jfwD+xdgu9T+O+NRZz8N5eA8=" crossorigin="anonymous"></script>
</head>
<link rel="stylesheet" href="/css/common/common.css">
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f9f9f9;
    }

    #container {
        width: 80%;
        margin: auto;
    }

    #containerWrap {
        display: flex;
        flex-direction: column;
        gap: 20px;
    }

    /* Search Section */
    #searchSection {
        display: flex;
        justify-content: left;
        gap: 10px;
        padding: 10px;
        background-color: white;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    input[type="text"] {
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    button {
        padding: 8px 15px;
        background-color: #0073e6;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    button:hover {
        background-color: #005bb5;
    }

    /* Filters */
    #filters {
        display: flex;
        justify-content: center;
        gap: 10px;
    }

    select {
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    /* Job Listings */
    #jobListings {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
        gap: 20px;
    }

    .job {
        background-color: white;
        padding: 15px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .job h3 {
        margin-bottom: 5px;
        color: #333;
    }

    .job p {
        font-size: 14px;
        color: #555;
    }

</style>
<body>

<jsp:include page="../common/header.jsp"/>
<div id="container">
    <div id="containerWrap">

        <div id="searchSection">
            <div id="filters">
                <select>
                    <option>직무</option>
                    <option>개발</option>
                    <option>디자인</option>
                </select>
                <select>
                    <option>경력</option>
                    <option>신입</option>
                    <option>경력직</option>
                </select>
                <select>
                    <option>학력</option>
                    <option>신입</option>
                    <option>경력직</option>
                </select>
                <select>
                    <option>스킬</option>
                    <option>신입</option>
                    <option>경력직</option>
                </select>


            </div>
            <button>적용</button>
        </div>



        <div id="jobListings">
            <div class="job">
                <h3>회사명</h3>
                <p>직무: 개발자</p>
                <p>부서: IT팀</p>
                <p>마감일: 04/24(화)</p>
                <button>지원하기</button>
            </div>
            <div class="job">
                <h3>회사명</h3>
                <p>직무: 디자이너</p>
                <p>부서: 디자인팀</p>
                <p>마감일: 04/24(화)</p>
                <button>지원하기</button>
            </div>
        </div>

    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

</body>
</html>
