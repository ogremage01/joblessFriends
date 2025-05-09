<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=keyboard_arrow_down" />
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=account_circle" />
<style type="text/css">

	
	#header {
		min-width: 1280px;
		width: 100%;
		border-bottom: 1px solid #E0E0E0;
	}
	
	#headerWrap {
		width: 1210px;
		height: 140px;
		margin: auto;
		margin-top: 20px;
		
	}
	
	
	/* 	헤더 위쪽 영역 */

	#headInner {
		height: 75px;
		display: flex;
		align-items:center;
	}
	
	
	
	/*		검색란		*/
		
	#searchDiv {
		width: 426px;
		height: 46px;
		border: 2px solid #F69800;
		border-radius: 25px;
		display: flex;
		align-items:center;
		margin-left: 25px;
	}
	
	#searchForm{
		flex-grow: 1;
	}
	
	#searchForm fieldset {
		border: 0px;
			padding: 0;
	}
	
	#searchForm legend {
		display: none;
	}
	
	#keyword {
		width: 350px;
		padding: 0px;
		padding-left: 20px;
		border: none;
		outline: none;
		font-size: 16px;
		background: none;
	}
	
	#btnMainSearch {
		background: none ;
		border:none;
		box-shadow:none;
		border-radius:0;
		padding:0;
		cursor:pointer;
		float: right;
		margin-right: 15px;
	}
	
	
	
	/*		로그인 상태 영역		*/	
	#userDiv{
		margin-left: auto
	}
	
	#userNav {
		list-style:none;
		display: flex;
	    align-items: center;
	    column-gap: 18px;
	    padding: 0px;
	}
	
	.userNavItem a {
		text-decoration: none;
		color: #373F57;
		font-size: 15px;
	}
	
	.corpNav {
		width: 120px;
		height: 40px;
		border: 2px solid #DEDEDE;
		border-radius: 25px;
		text-align: center;
		line-height: 40px;
	}
	
	.corpNav a {
		margin-left: 8px;
	}
	
	.corpMgr {
		pointer-events: none;
	}



	/*		헤더 아래영역 네비게이션 바		*/		
	
	#serviceNav {
		list-style:none;
		display: flex;
	    align-items: center;
	    column-gap: 20px;
	    padding: 0px;
	    
	}
	
	.serviceNavItem a {
		text-decoration: none;
		color: #2B2B2B;
		font-size: 18px;
	}
	
	.partitionNav {
		font-size: 15px;
		color: #D4D4D4;
		user-select: none;
	}
	
	
	/*		헤더 안에 왼쪽 여백		*/
	
	.headBlankLeft {
		margin-left: 14px;
	}
	
	
	/*		구글 아이콘 스타일		*/
	
	.material-symbols-outlined {
	  font-variation-settings:
	    'FILL' 0,
	    'wght' 400,
	    'GRAD' 0,
	    'opsz' 24;
	  vertical-align: middle;
	  font-size: 22px;
	  color: #BDBDBD;
	}

	.material-symbols-rounded {
	  font-variation-settings:
	  'FILL' 0,
	  'wght' 400,
	  'GRAD' -25,
	  'opsz' 20;
	  vertical-align: middle;
	  font-size: 22px;
	}
	
</style>

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
					                        <a href="">
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
					                        <a href="">기업이름 담당자님</a>
					                    </li>
					                    <li class="userNavItem logout">
					                        <a href="/auth/logout">로그아웃</a>
					                    </li>
					                    <li class="userNavItem corpNav">
					                        <a href="">
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
					                <a href="">
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
	</div>

</body>

</html>