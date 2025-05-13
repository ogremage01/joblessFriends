<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.min.js" ></script>

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=keyboard_arrow_down" />
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=account_circle" />
<link rel="stylesheet" href="/css/common/header.css">
</head>

<body>

	<div id="header">
		<div id="headerWrap">
			<div id="headInner">
				<div id="logoDiv" class="headBlankLeft">
					<a href="/"><img alt="어디보잡 로고" src="/img/logo.svg" /></a>
				</div>
				<div id="searchDiv">
				
					<form id="searchForm" name="searchForm" method="" action="">
						<fieldset>
							<legend>검색</legend>
							<input id="keyword" name="keyword" type="text" value="" placeholder="자신에게 맞는 채용정보를 찾아보세요" autocomplete="off">
							<button type="submit" id="btnMainSearch" class="btnSearch">
								<svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="#F69800" class="bi bi-search" viewBox="0 0 16 16">
 								<path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
								</svg>
							</button>
						</fieldset>
					</form>
					
				</div>
				<div id="userDiv">
					<ul id="userNav">
					    <c:choose>
					        <c:when test="${sessionScope.userLogin ne null}">
					            <c:choose>
					                <c:when test="${sessionScope.userType eq 'member'}">
					                    <li class="userNavItem myPage">
					                        <a href="/member/mypage">
					                            <span class="material-symbols-rounded account_circle">account_circle</span>
					                            마이페이지
					                        </a>
					                    </li>
					                    <li class="userNavItem logout">
					                        <a href="/auth/logout">로그아웃</a>
					                    </li>
					                </c:when>
					                <c:when test="${sessionScope.userType eq 'company'}">
					                    <li class="userNavItem corpMgr">
					                        <a href="">${sessionScope.userLogin.companyName} 담당자님</a>
					                    </li>
					                    <li class="userNavItem logout">
					                        <a href="/auth/logout">로그아웃</a>
					                    </li>
					                    <li class="userNavItem corpNav">
					                        <a href="javascript:void(0);" onclick="companyPopup();">
					                            기업서비스<span class="material-symbols-outlined keyboard_arrow_down">keyboard_arrow_down</span>
					                        </a>
					                    </li>
					                </c:when>
					            </c:choose>
					        </c:when>
					        <c:otherwise>
					            <li class="userNavItem login">
					                <a href="/auth/login">로그인</a>
					            </li>
					            <li class="userNavItem join">
					                <a href="/auth/signup">회원가입</a>
					            </li>
					            <li class="userNavItem corpNav">
					                <a href="javascript:void(0);" onclick="companyPopup();">
					                    기업서비스<span class="material-symbols-outlined keyboard_arrow_down">keyboard_arrow_down</span>
					                </a>
					            </li>
					        </c:otherwise>
					    </c:choose>
					</ul>
				</div>
			</div>
			<div id="headNavBar">
				<ul id="serviceNav" class="headBlankLeft">
					<li class="serviceNavItem jobInfoNav">
						<a href="/Recruitment/list">채용정보</a>
					</li>
					<li class="serviceNavItem partitionNav">
						|
					</li>
					<li class="serviceNavItem communityNav">
						<a href="/community">커뮤니티</a>
					</li>
				</ul>
			</div>
		</div>
	
		<div id="companyServiceNav">
			
			<div>기업서비스</div>
			
			<ul>
<%-- 				<c:if test="${sessionScope.userLogin eq null}"> --%>
<!-- 					<li> -->
<!-- 						<a href="/auth/login">공고 등록</a> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<a href="/auth/login">공고 관리</a> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<a href="/auth/login">지원자 관리</a> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<a href="/auth/login">기업정보 관리</a> -->
<!-- 					</li> -->
<%-- 				</c:if> --%>
<%-- 				<c:if test="${sessionScope.userLogin ne null}"> --%>
					<li>
						<a href="">공고 등록</a>
					</li>
					<li>
						<a href="">공고 관리</a>
					</li>
					<li>
						<a href="">지원자 관리</a>
					</li>
					<li>
						<a href="">기업정보 관리</a>
					</li>
<%-- 				</c:if> --%>
				
			</ul>
		</div>
	
	</div>
	

</body>

<script src="/js/common/header.js"></script>

</html>