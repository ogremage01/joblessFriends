
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>채용공고 관리 - 어디보잡 관리자</title>
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js" 
        integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    
    <!-- 공통 스타일 적용 -->
    <link rel="stylesheet" href="/css/common/common.css">
    <link href="/css/admin/common.css" rel="stylesheet">
    <link href="/css/admin/adminStyle.css" rel="stylesheet">
    
</head>
<body>

<div id="container">
    <div id="containerWrap">
        <div class="admin-container">
            <!-- 사이드바 영역 -->
            <div class="admin-sidebar">
                <jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
            </div>
            
            <!-- 메인 컨텐츠 영역 -->
            <div class="admin-main">
                <div class="admin-header">
                    <h1>
                        <i class="bi bi-briefcase-fill" style="color: #F69800;"></i>
                        채용공고 관리
                    </h1>
                </div>
                
                <div class="admin-content recruitment-table">
                    <div class="admin-table-header">
                        <h2><i class="bi bi-list-ul"></i> 공고 목록</h2>
                        <button id="massDelRecruitment" class="mass-delete-btn">
                            <i class="bi bi-trash"></i> 선택 삭제
                        </button>
                    </div>
                    
                    <table class="table admin-table">
                        <thead>
                            <tr>
                                <th scope="col"><button id="selectAll">전체 선택</button></th>
                                <th scope="col">공고 ID</th>
                                <th scope="col">회사 ID</th>
                                <th scope="col">회사명</th>
                                <th scope="col">접수 시작일</th>
                                <th scope="col">접수 마감일</th>
                                <th scope="col">공고명</th>
                                <th scope="col">조회수</th>
                                <th scope="col">삭제</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="recruitmentVo" items="${recruitmentList}">
                                <tr>
                                    <td class="checkbox-container">
                                        <input type="checkbox" class="delPost admin-checkbox" 
                                               name="delete" value="${recruitmentVo.jobPostId}">
                                    </td>
                                    <td>${recruitmentVo.jobPostId}</td>
                                    <td>${recruitmentVo.companyId}</td>
                                    <td>
                                        <a href="/admin/member/company/${recruitmentVo.companyId}" class="company-link">
                                            ${recruitmentVo.companyName}
                                        </a>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${recruitmentVo.startDate}" pattern="yyyy-MM-dd HH:MM:SS" />
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${recruitmentVo.endDate}" pattern="yyyy-MM-dd HH:MM:SS" />
                                    </td>
                                    <td>
                                        <a href="/Recruitment/detail?companyId=${recruitmentVo.companyId}&jobPostId=${recruitmentVo.jobPostId}" 
                                           target="_blank" rel="noopener noreferrer" class="recruitment-link">
                                            ${recruitmentVo.title}
                                        </a>
                                    </td>
                                    <td class="views-count">${recruitmentVo.views}</td>
                                    <td>
                                        <button class="delBtn delete-btn" value="${recruitmentVo.jobPostId}">
                                            삭제
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- 페이지네이션 -->
                    <div class="pagination-container">
                        <c:if test="${pagination.totalPageCount > 0}">
                            <nav aria-label="페이지 네비게이션">
                                <ul class="pagination">
                                    <li class="page-item ${searchVo.page==1?'disabled':''}">
                                        <a class="page-link" href="./recruitment?page=${searchVo.page-1}&keyword=${searchVo.keyword}">
                                            «
                                        </a>
                                    </li>
                                    <c:forEach begin="${pagination.startPage}" var="i" end="${pagination.endPage}">
                                        <li class="page-item ${searchVo.page==i?'active':''}">
                                            <a class="page-link" href="./recruitment?page=${i}&keyword=${searchVo.keyword}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item ${searchVo.page==pagination.totalPageCount? 'disabled':''}">
                                        <a class="page-link" href="./recruitment?page=${searchVo.page+1}&keyword=${searchVo.keyword}">
                                            »
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>
                    </div>

                    <!-- 검색 영역 -->
                    <div class="search-container">
                        <input id="recruitmentKeyword" type="text" class="search-input" 
                               placeholder="공고제목으로 검색" value="${searchVo.keyword}">
                        <button id="recruitmentSearchBtn" class="search-btn">
                            <i class="bi bi-search"></i> 검색
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../common/footer.jsp"/>



<script src="/js/admin/recruitment/recruitment.js"></script>





</body>

</html>
