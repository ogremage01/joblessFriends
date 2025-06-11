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
    <a href="#" class="chat-floating-btn" title="채팅하기" data-user-type="${sessionScope.userType}" onclick="openChat(event)">
        <i class="bi bi-chat-dots-fill"></i>
    </a>
</body>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/index.js"></script>
</html>