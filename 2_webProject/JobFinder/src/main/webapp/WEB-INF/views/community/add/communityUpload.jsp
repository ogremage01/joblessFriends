<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
<link rel="stylesheet" href="/css/community/communityCommonStyle.css"> 
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

<style type="text/css">
	

	.boxStyle{
		width: 1190px;
		height: 45px;
		
		font-size: 14pt;
	
	}
	
	#cancleBtn, #uploadBtn{
		width: 100px;
		height: 45px;
		
		font-size: 14pt;
	}
	
	#cancleBtn{
		background-color: white;
		color: #6D707B;
		border: 2px solid #D1D5E3; 
	}
	
	#btnWrap{
		margin-top: 20px;
		
		float: right;
		margin-right: 10px;
		
	}
	
	.contentBox{
		width: 1200px;
	}
	
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div id='containerWrap' class="wrap">
	<h2>글쓰기</h2>
	<div>
		<p>제목</p>
		<input type="text" class='boxStyle' placeholder="제목을 입력해주세요." />
	</div>
	<div>
		<p>내용</p>
		<div id="content" class="contentBox"></div>
		<!-- TUI 에디터 JS CDN -->
		<script
			src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
		<script>
	        const editor = new toastui.Editor({
	            el: document.querySelector('#content'), // 에디터를 적용할 요소 (컨테이너)
	            height: '500px',                        // 에디터 영역의 높이 값 (OOOpx || auto)
	            initialEditType: 'markdown',            // 최초로 보여줄 에디터 타입 (markdown || wysiwyg)
	            initialValue: '내용을 입력해 주세요.',     // 내용의 초기 값으로, 반드시 마크다운 문자열 형태여야 함
	            previewStyle: 'vertical' 
	        });
    	</script>
	</div>

	<div id="btnWrap">
		<button id='cancleBtn' class='inputBtn'>취소</button>
		<button id='uploadBtn' class='inputBtn'>등록</button>
	</div>

	</div>
</body>


</html>