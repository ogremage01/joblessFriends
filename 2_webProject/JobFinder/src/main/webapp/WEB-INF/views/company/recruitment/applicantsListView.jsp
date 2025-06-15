<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>지원자 목록</title>
<!--     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet"> -->
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/member/memberMyPage.css" />
    <link rel="stylesheet" href="/css/company/applicantsListView.css">

</head>
<body>
<jsp:include page="../../common/header.jsp"/>

<div class="container">
    <div id="containerWrap">
		<jsp:include page="../companyPage/companyPageSidebar.jsp" />
        <div class="main">
        	<div class="content">
				<h1 class="mainTitle">지원자 목록</h1>
				<div class="tableWrap">
		            <table class="applicantsTable">
		                <thead>
		                <tr>
		                    <th>지원자 명</th>
		                    <th>이력서 제목</th>
		                    <th>지원일</th>
		                    <th>이력서</th>
		                    <th>사전질문</th>
		                    <th>적합도</th>
		                    <th>상태</th>
		                    <th>관리</th>
		                </tr>
		                </thead>
		                <tbody>
						    <c:choose>
						        <c:when test="${not empty applyList}">
						                <c:forEach var="apply" items="${applyList}">
						                    <tr class="applyTr">
						                        <td>${apply.memberName}</td>
						                        <td>${apply.resumeTitle}</td>
						
						                        <td><fmt:formatDate value="${apply.applyDate}" pattern="yyyy-MM-dd" /></td>
						                        <td><button class="btn-resume btn-styles" data-resume-id="${apply.resumeId}" onclick="">이력서 열람</button></td>
						                        <td>
						                            <button class="btn-question btn-styles"
						                                    data-member-id="${apply.memberId}"
						                                    data-jobpost-id="${jobPostId}">
						                                질문보기
						                            </button>
						                        </td>
 						                        	<!-- 적합도에 따른 스타일 넣기 -->
						                        <td
						                        	<c:choose>
							                        	<c:when test="${apply.matchScore < 30}">
							                        		class="scoreRow"
							                        	</c:when>
							                        	<c:when test="${apply.matchScore >= 30 && apply.matchScore <= 59}">
							                        		class="scoreMiddle"
							                        	</c:when>
							                        	<c:when test="${apply.matchScore >= 60}">
							                        		class="scoreHigh"
							                        	</c:when>
							                        </c:choose>
						                        >${apply.matchScore}%
						                        </td>
						                        	<!-- 상태에 따른 스타일 넣기 -->
						                        <td
							                        <c:choose>
							                        	<c:when test="${apply.stateName == '지원'}">
							                        		class="status-apply"
							                        	</c:when>
							                        	<c:when test="${apply.stateName == '서류합격'}">
							                        		class="status-documentPass"
							                        	</c:when>
							                        	<c:when test="${apply.stateName == '최종합격'}">
							                        		class="status-finalPass"
							                        	</c:when>
							                        	<c:when test="${apply.stateName == '불합격'}">
							                        		class="status-fail"
							                        	</c:when>
							                        </c:choose>
						                        >${apply.stateName}</td>
						                        <td>
						                        	<div class="mgrBtnWrap">
							                        	<button class="btn-state btn-styles" onclick="openStateChangeModal(${jobPostId}, ${apply.memberId})">상태 변경</button>
						                        	</div>
						                        </td>
						                    </tr>
						                </c:forEach>
						        </c:when>
						        <c:otherwise>
						            <tr>
						            	<td class="emptyTd" colspan="8"><span>지원자가 없습니다.</span></td>
						            </tr>
						        </c:otherwise>
						    </c:choose>
		                </tbody>
		            </table>
				</div>
				
			    <!-- 페이지네이션 -->
			    <c:if test="${not empty applyList}">
				<div id="pageWrap" >
					<div id="pagination">
					
					<!-- 이전 페이지 -->
					<c:if test="${pagination.existPrevPage}">
		                <button class="page-btn" onclick="goToPage(${pagination.startPage - 1},${jobPostId})">«</button>
            		</c:if>

					<!-- 페이지 번호 -->
					<c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="i">
						<button class="page-btn" data-page="${i}" onclick="goToPage(${i},${jobPostId})">${i}</button>
					</c:forEach>

					<!-- 다음 페이지 -->
					<c:if test="${pagination.existNextPage}">
						<button class="page-btn" onclick="goToPage(${searchVo.page + 1},${jobPostId})">»</button>
					</c:if>

					</div>
				</div>
				</c:if>
				
			</div>
		</div>
	</div>
</div>
<script src="/js/company/applicantsListView.js"></script>
<jsp:include page="../../common/footer.jsp" />
</body>
</html>
