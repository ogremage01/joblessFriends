<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>지원자 목록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../../common/header.jsp"/>

<div class="container mt-4">
    <h3>지원자 목록</h3>

    <c:choose>
        <c:when test="${not empty applyList}">
            <table class="table table-bordered mt-3">
                <thead class="table-light">
                <tr>
                    <th>지원자 이름</th>
                    <th>이력서 제목</th>
                    <th>지원일</th>
                    <th>적합도</th>
                    <th>관리</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="apply" items="${applyList}">
                    <tr>
                        <td>${apply.memberName}</td>
                        <td>${apply.resumeTitle}</td>
                        <td><fmt:formatDate value="${apply.applyDate}" pattern="yyyy-MM-dd" /></td>
                        <td>80%</td>
                        <td>${apply.stateName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning mt-4">지원자가 없습니다.</div>
        </c:otherwise>
    </c:choose>

    <!-- 페이지네이션 -->
    <nav aria-label="Page navigation" class="mt-3">
        <ul class="pagination justify-content-center">
            <c:if test="${pagination.existPrevPage}">
                <li class="page-item">
                    <a class="page-link" href="?page=${pagination.startPage - 1}">이전</a>
                </li>
            </c:if>

            <c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}">
                <li class="page-item ${i == pagination.page ? 'active' : ''}">
                    <a class="page-link" href="?page=${i}">${i}</a>
                </li>
            </c:forEach>

            <c:if test="${pagination.existNextPage}">
                <li class="page-item">
                    <a class="page-link" href="?page=${pagination.endPage + 1}">다음</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>

<jsp:include page="../../common/footer.jsp" />
</body>
</html>
