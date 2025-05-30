<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="resume" value="${resume}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이력서 미리보기</title>
<link rel="stylesheet" href="/css/resume/resumePreview.css" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-3fp9tS8p9A2Mq7Qz+S8jfwD+xdgu9T+O+NRZz8N5eA8=" crossorigin="anonymous"></script>
</head>
<body>
	
	<!-- 	이력서 헤더	 -->
	<div id="resumeHeader">
		<div class="headerTitle">이력서 미리보기</div>
		<div class="headerInfo">채용기업이 보는 이력서 열람화면입니다.</div>
	</div>
	
	<!-- 	이력서 메인 		-->
	<div id="container">
		<h1>${resume.title}</h1>
		
		<!-- 	프로필 	 -->
		<div id="profile">
			<div id="profileTop">
				<!--	프로필 사진	-->
				<div id="profileImg">
					<c:if test="${not empty resume.profile}">
    					<img src="${resume.profile}" alt="프로필 이미지" style="width:120px;" />
					</c:if>
				</div>
				
				<!--	인적사항		-->
				<div id="infoTableBox">
					<span class="infoTableName">${resume.memberName}</span>
					<span class="infoTableAge" colspan="3">${resume.birthDate}</span>
					<table>
						<tr>
							<th class="infoTableTh">이메일</th>
							<td class="infoTableTd">${resume.email}</td>
							<th class="infoTableTh">전화번호</th>
							<td class="infoTableTd">${resume.phoneNumber}</td>
						</tr>
						<tr>
							<th class="infoTableTh">주소</th>
							<td class="infoTableTd" colspan="3">${resume.address}</td>
						</tr>
						<tr>
							<th class="infoTableTh">희망직군</th>
							<td class="infoTableTd" colspan="3">${jobGroupName}</td>
							<th class="infoTableTh">희망직무</th>
							<td class="infoTableTd" colspan="3">${jobName}</td>
						</tr>
					</table>
				</div>
			</div>
			
			<!-- 	이력서 요약	 -->
			<div id="profileBottom" class="borderBox">
				<div class="resumeSum education">
					<span class="sumTitle">학력</span>
					<div class="sumContent">
						<span class="sumItem">○○대학교</span>
						<span class="sumAddEx">대학교(4년)</span>
						<span class="sumAddEx">졸업</span>
					</div>
				</div>
				<div class="resumeSum career">
					<span class="sumTitle">경력</span>
					<div class="sumContent">
						<span class="sumItem">6년</span>
					</div>
				</div>
				<div class="resumeSum training">
					<span class="sumTitle">교육</span>
					<div class="sumContent">
						<span class="sumItem">-</span>
					</div>
				</div>
				<div class="resumeSum license">
					<span class="sumTitle">자격증</span>
					<div class="sumContent">
						<span class="sumItem">GTQ 1급</span>
						<span class="sumAddEx">외 n개</span>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 	추가 내용		-->
		<div id="resumeContent">
			<div class="borderBox">
				<p>title</p>
				<span>content<br>content</span>
			</div>
			
			<div class="borderBox">
			</div>
			
		</div>
		
	</div>

</body>
<script src="/js/resume/resumePreview.js"></script>
</html>