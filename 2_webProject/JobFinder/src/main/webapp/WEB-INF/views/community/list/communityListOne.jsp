<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>        
	<link rel="stylesheet" href="/css/community/communityCommonStyle.css">    
</head>

<style>
	.boxListOne{
		width: 860px;
		height: 150px;
		padding: 30px;
		
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}
	
	.boxListOne div{
		width: 870px;
		margin-bottom: 18px;

	}	
	
	
	h2{
		margin-top: 0px;
		margin-bottom: 0px;
		
		overflow: hidden;
  		text-overflow: ellipsis;
	}
	
	
	.previewText {
		margin: 0px;
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
		
		overflow: hidden;

		white-space: normal;
		word-break: break-all;
		
		line-height: 1.5em;
		height: calc(1.5em * 2);
	}
		

	
	#infoContent div{
		width: 80px;

		margin: 0px;

		font-size: 10pt;
	}


</style>

<script type="text/javascript">
	function moveDetail(){
		location.href="community/detail";
		
	}
</script>

<div class='boxStyle boxListOne' onclick="moveDetail()">
	<div>
		<div>
			<h2>제목 들어가는 부분</h2>
		</div>
		<div id='previewContent'>
			<p class="previewText">
				게시물 내용 들어갈 부분(길이체크)00000000000000000000000000000000000000000000000000000000000000000000000000000000000aedaddd00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)
			</p>
		</div>
	</div>
	<div id='infoContent'>
		<div>
			<svg xmlns="http://www.w3.org/2000/svg" width="70" height="24" fill="#a2a6b1" class="bi bi-eye" viewBox="0 0 75 16">
			  	<path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z"/>
			  	<path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"/>
				<text x="22" y="13" font-size="12">(조회수)</text>
				<text x="70" y="12" font-size="12">|</text>
			</svg>
		</div>

		<div id='commentCount' style="min-width: 100px">
			<svg xmlns="http://www.w3.org/2000/svg" width="100" height="24" fill="#a2a6b1" class="bi bi-chat-left" viewBox="0 -2 110 16">
			  	<path d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
				<text x="22" y="12" font-size="12">(달린 댓글 수)</text>
				<text x="100" y="11" font-size="12">|</text>
			</svg>
		</div>
		<div>
			<svg xmlns="http://www.w3.org/2000/svg" width="90" height="24" fill="#a2a6b1" class="bi bi-chat-left" viewBox="0 -2 100 16">
			  	<text x="0" y="12" font-size="12">(날짜) 작성</text>
			</svg>
		</div>
	</div>
	
</div>