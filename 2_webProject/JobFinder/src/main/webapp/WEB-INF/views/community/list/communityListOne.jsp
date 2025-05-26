<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>        
	<link rel="stylesheet" href="/css/community/communityCommonStyle.css">    
	<link rel="stylesheet" href="/css/community/communityListOneStyle.css">   
</head>

<style>
	


</style>

<script type="text/javascript">
	function moveDetail(divElement){
		let communityIdInput = divElement.querySelector("input[id='communityNo']");
		let communityId = communityIdInput.value;
		
		console.log(communityId);
		
		let communityFormNoObj = document.getElementById('communityFormNo');
		communityFormNoObj.value = communityId;
		
		let communitySelectOneFormObj = document.getElementById('communitySelectOneForm');
		communitySelectOneFormObj.submit();

		
	}
</script>

<c:if test="${empty communityList}">
<div id='noCommunityBox'>
	<span id='noCommunity'> 게시글이 존재하지 않습니다. </span>
</div>
</c:if>

<c:forEach var="community" items="${communityList}">
<div class='boxStyle boxListOne' onclick="moveDetail(this)">
<input type="hidden" id="communityNo" value="${community.communityId}">
	<div>
		<div>
			<h2>${community.title}</h2>
		</div>
		<div id='previewContent'>
			<p class="previewText"><c:out value="${community.content}" escapeXml="false"/> </p>
		</div>
	</div>
	<div id='infoContent'>
		<div style="display: flex; min-width: 60px; ">
			<svg xmlns="http://www.w3.org/2000/svg" width="20" height="24" fill="#a2a6b1" class="bi bi-eye" viewBox="0 2 20 14">
			  	<path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z"/>
			  	<path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"/>
			</svg>
			<span style="min-width: 20px; text-align: center; padding-right: 8px">${community.views}</span>
			<span>|</span>
		</div>

		<div id='commentCount' style="min-width: 60px;display: flex;">
			<svg xmlns="http://www.w3.org/2000/svg" width="20" height="24" fill="#a2a6b1" class="bi bi-chat-left" viewBox="-3 -1 20 18">
			  	<path d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
			</svg>
			<span style="min-width: 30px; text-align: center;">${community.commentCount}</span>
			<span>|</span>
		</div>
		<div style="display: flex; min-width: 100px; margin-left: 8px">
			  	<span><fmt:formatDate pattern="yyyy-MM-dd" value="${community.createAt}"/> 작성</span>

		</div>
	</div>
	
</div>

</c:forEach>

<form id="communitySelectOneForm" action="/community/detail" method="get">
	<input type="hidden" id="communityFormNo" name="no" value="">
</form>