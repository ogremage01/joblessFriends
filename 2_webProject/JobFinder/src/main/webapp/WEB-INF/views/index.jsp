<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>인덱스</title>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
     integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js"
 integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+Cfch+3qOVUtJn3QNZOtciWLP4=" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/common/index.css">
    <link rel="stylesheet" href="/css/common/common.css">
    
    <!-- 채팅 관련 스크립트 -->
    <script type="text/javascript">
        function openChat(event) {
            event.preventDefault();
            
            var loginType = "${sessionScope.userType}";
            var isLoggedIn = "${not empty sessionScope.userLogin}";
            
            if (isLoggedIn === "false") {
                alert("로그인이 필요한 서비스입니다.");
                location.href = "auth/login";
                return;
            }
            
            var chatUrl = "";
            var popupWidth = 450;  // 약 12cm
            var popupHeight = 800; // 약 21cm
            var left = (window.innerWidth - popupWidth) / 2;
            var top = (window.innerHeight - popupHeight) / 2;
            
            switch(loginType) {
                case "member":
                    chatUrl = "member/chat/room";
                    break;
                case "company":
                    chatUrl = "company/chat/room";
                    break;
                default:
                    alert("잘못된 접근입니다.");
                    return;
            }
            
            var popupOptions = "width=" + popupWidth + 
                             ",height=" + popupHeight + 
                             ",left=" + left + 
                             ",top=" + top + 
                             ",scrollbars=yes" +
                             ",resizable=yes" +
                             ",status=no" +
                             ",location=no" +
                             ",toolbar=no" +
                             ",menubar=no";
            
            window.open(chatUrl, "채팅", popupOptions);
        }

        // 안읽은 메시지 확인 함수
        function checkUnreadMessages() {
            var loginType = "${sessionScope.userType}";
            var isLoggedIn = "${not empty sessionScope.userLogin}";
            
            // 로그인하지 않았거나 관리자인 경우 확인하지 않음
            if (isLoggedIn === "false" || loginType === "admin") {
                return;
            }
            
            var apiUrl = "";
            switch(loginType) {
                case "member":
                    apiUrl = "/member/chat/unread-count";
                    break;
                case "company":
                    apiUrl = "/company/chat/unread-count";
                    break;
                default:
                    return;
            }
            
            fetch(apiUrl, {
                method: 'GET',
                credentials: 'same-origin'
            })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error('Network response was not ok');
            })
            .then(unreadCount => {
                updateChatIcon(unreadCount);
            })
            .catch(error => {
                console.log('안읽은 메시지 확인 중 오류:', error);
            });
        }

        // 채팅 아이콘 업데이트 함수
        function updateChatIcon(unreadCount) {
            var chatBtn = document.querySelector('.chat-floating-btn');
            var existingBadge = chatBtn.querySelector('.unread-badge');
            
            if (unreadCount > 0) {
                // 안읽은 메시지가 있으면 느낌표 배지 표시
                if (!existingBadge) {
                    var badge = document.createElement('div');
                    badge.className = 'unread-badge';
                    badge.innerHTML = '!';
                    chatBtn.appendChild(badge);
                }
            } else {
                // 안읽은 메시지가 없으면 배지 제거
                if (existingBadge) {
                    existingBadge.remove();
                }
            }
        }

        // 페이지 로드 시 초기 확인 및 주기적 확인 설정
        document.addEventListener('DOMContentLoaded', function() {
            var loginType = "${sessionScope.userType}";
            var isLoggedIn = "${not empty sessionScope.userLogin}";
            
            if (isLoggedIn === "true" && (loginType === "member" || loginType === "company")) {
                // 초기 확인
                checkUnreadMessages();
                
                // 30초마다 확인
                setInterval(checkUnreadMessages, 30000);
            }
        });
    </script>
</head>
<body>
	<jsp:include page="common/header.jsp"/>
	
	<div id="container">
		<div id="containerWrap">
		
			<!-- 		조회 많은 순		-->
			<div class="jobInfoSection section">
				<button class="slideBtn left views"><div></div></button>
				<button class="slideBtn right views"><div></div></button>
				<h2>조회수 많은 공고 👀</h2>
				<div id="viewsList" class="jobInfoContent">
					<div class="slideWrap translateSlide">
					
					<c:forEach var="rec" items="${recruitmentListViews}">
                    <c:if test="${now < rec.endDate}"> <%-- 마감기한이 지나면 출력되지 않음 --%>
							<div class="jobInfoItem" onclick="location.href='Recruitment/detail?companyId=${rec.companyId}&jobPostId=${rec.jobPostId}'">
								<div class="jobImg">
									<img alt="공고 이미지" src="${rec.jobImg}"/>
									<div class="jobOverImage">
										<div class="jobDeadline">
											<span>
						                        <c:set var="diffRaw" value="${(rec.endDate.time - now.time) / (1000 * 60 * 60 * 24.0)}" />
												<c:set var="diffInDays" value="${fn:substringBefore(diffRaw, '.')}" />
						                        <c:choose>
						                            <c:when test="${diffInDays == 0}">
						                                오늘마감
						                            </c:when>
						
						                            <%-- D-Day 처리: 7일 이하 --%>
						                            <c:when test="${diffInDays > 0 && diffInDays <= 7}">
						                                D-${diffInDays}
						                            </c:when>
						
						                            <%-- 7일 이상일 경우, MM.dd(요일) 형식으로 출력 --%>
						                            <c:otherwise>
						                               ~<fmt:formatDate value="${rec.endDate}" pattern="MM.dd(E)" />
						                            </c:otherwise>
						                        </c:choose>
												<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star" viewBox="0 0 15 15" width="15px" height="15px">
				 									 <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.56.56 0 0 0-.163-.505L1.71 6.745l4.052-.576a.53.53 0 0 0 .393-.288L8 2.223l1.847 3.658a.53.53 0 0 0 .393.288l4.052.575-2.906 2.77a.56.56 0 0 0-.163.506l.694 3.957-3.686-1.894a.5.5 0 0 0-.461 0z"/>
												</svg>
											</span>
										</div>
										<div class="skillTagsWrap">
											<c:forEach var="tag" items="${skillMapViews[rec.jobPostId]}" varStatus="status" begin="0" end="2">
												<div class="jobSkillTag">
													<span>${tag.tagName}</span>
												</div>
											</c:forEach>
										</div>
									</div>
								</div>
								<!-- 			jobImg end 			-->
								<span class="jobTitle">${rec.title}</span>
								<span class="jobRegionCareer">
									${rec.education} · ${rec.careerType}
								</span>
								<span class="jobCompanyName">${rec.companyName}</span>
							</div>
					</c:if>
					</c:forEach>
					
					</div>
				</div>
			</div>
			
			
			<!-- 		마감임박 순		-->
			<div class="jobInfoSection section">
				<button class="slideBtn left endDate"><div></div></button>
				<button class="slideBtn right endDate"><div></div></button>
				<h2>곧 마감되는 공고🔥</h2>
				<div id="endDateList" class="jobInfoContent">
					<div class="slideWrap translateSlide">
					
					<c:forEach var="rec" items="${recruitmentListEndDate}">
                    <c:if test="${now < rec.endDate}"> <%-- 마감기한이 지나면 출력되지 않음 --%>
							<div class="jobInfoItem" onclick="location.href='Recruitment/detail?companyId=${rec.companyId}&jobPostId=${rec.jobPostId}'">
								<div class="jobImg">
									<img alt="공고 이미지" src="${rec.jobImg}"/>
									<div class="jobOverImage">
										<div class="jobDeadline">
											<span>
						                        <c:set var="diffRaw" value="${(rec.endDate.time - now.time) / (1000 * 60 * 60 * 24.0)}" />
												<c:set var="diffInDays" value="${fn:substringBefore(diffRaw, '.')}" />
						                        <c:choose>
						                            <c:when test="${diffInDays == 0}">
						                                오늘마감
						                            </c:when>
						
						                            <%-- D-Day 처리: 7일 이하 --%>
						                            <c:when test="${diffInDays > 0 && diffInDays <= 7}">
						                                D-${diffInDays}
						                            </c:when>
						
						                            <%-- 7일 이상일 경우, MM.dd(요일) 형식으로 출력 --%>
						                            <c:otherwise>
						                               ~<fmt:formatDate value="${rec.endDate}" pattern="MM.dd(E)" />
						                            </c:otherwise>
						                        </c:choose>
												<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star" viewBox="0 0 15 15" width="15px" height="15px">
				 									 <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.56.56 0 0 0-.163-.505L1.71 6.745l4.052-.576a.53.53 0 0 0 .393-.288L8 2.223l1.847 3.658a.53.53 0 0 0 .393.288l4.052.575-2.906 2.77a.56.56 0 0 0-.163.506l.694 3.957-3.686-1.894a.5.5 0 0 0-.461 0z"/>
												</svg>
											</span>
										</div>
										<div class="skillTagsWrap">
											<c:forEach var="tag" items="${skillMapEndDate[rec.jobPostId]}" varStatus="status" begin="0" end="2">
												<div class="jobSkillTag">
													<span>${tag.tagName}</span>
												</div>
											</c:forEach>
										</div>
									</div>
								</div>
								<!-- 			jobImg end 			-->
								<span class="jobTitle">${rec.title}</span>
								<span class="jobRegionCareer">
									${rec.education} · ${rec.careerType}
								</span>
								<span class="jobCompanyName">${rec.companyName}</span>
							</div>
					</c:if>
					</c:forEach>
					
					</div>
				</div>
			</div>
			
			<!-- 			최신공고 			-->
			<div class="jobInfoSection section">
				<h2>최신 등록된 공고 📌</h2>
				<div class="jobInfoContent">
					<c:forEach var="rec" items="${recruitmentListLatest}">
                    <c:if test="${now < rec.endDate}"> <%-- 마감기한이 지나면 출력되지 않음 --%>
                    
						<div class="jobInfoItem" onclick="location.href='Recruitment/detail?companyId=${rec.companyId}&jobPostId=${rec.jobPostId}'">
							<div class="jobImg">
								<img alt="공고 이미지" src="${rec.jobImg}"/>
								<div class="jobOverImage">
									<div class="jobDeadline">
										<span>
					                        <c:set var="diffRaw" value="${(rec.endDate.time - now.time) / (1000 * 60 * 60 * 24.0)}" />
											<c:set var="diffInDays" value="${fn:substringBefore(diffRaw, '.')}" />
					                        <c:choose>
					                            <c:when test="${diffInDays == 0}">
					                                오늘마감
					                            </c:when>
					
					                            <%-- D-Day 처리: 7일 이하 --%>
					                            <c:when test="${diffInDays > 0 && diffInDays <= 7}">
					                                D-${diffInDays}
					                            </c:when>
					
					                            <%-- 7일 이상일 경우, MM.dd(요일) 형식으로 출력 --%>
					                            <c:otherwise>
					                               ~<fmt:formatDate value="${rec.endDate}" pattern="MM.dd(E)" />
					                            </c:otherwise>
					                        </c:choose>
											<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star" viewBox="0 0 15 15" width="15px" height="15px">
			 									 <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.56.56 0 0 0-.163-.505L1.71 6.745l4.052-.576a.53.53 0 0 0 .393-.288L8 2.223l1.847 3.658a.53.53 0 0 0 .393.288l4.052.575-2.906 2.77a.56.56 0 0 0-.163.506l.694 3.957-3.686-1.894a.5.5 0 0 0-.461 0z"/>
											</svg>
										</span>
									</div>
									<div class="skillTagsWrap">
										<c:forEach var="tag" items="${skillMapLatest[rec.jobPostId]}" varStatus="status" begin="0" end="2">
											<div class="jobSkillTag">
												<span>${tag.tagName}</span>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
							
							<span class="jobTitle">${rec.title}</span>
							<span class="jobRegionCareer">
								${rec.education} · ${rec.careerType}
							</span>
							<span class="jobCompanyName">${rec.companyName}</span>
							
						</div>
					</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>	


	<jsp:include page="common/footer.jsp"/>
	
	<button class="topBtn" title="맨 위로 가기">TOP</button>
	
    <!-- 채팅 플로팅 버튼 -->
    <a href="#" class="chat-floating-btn" title="채팅하기" onclick="openChat(event)">
        <i class="bi bi-chat-dots-fill"></i>
    </a>
</body>
<script src="/js/index.js"></script>
</html>