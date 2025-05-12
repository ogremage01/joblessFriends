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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/5.5.1/github-markdown.min.css">

<style type="text/css">
	
	#containerWrap{
		margin-bottom: 100px;
	}
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
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script
>
<script type="text/javascript">

	
</script>
</head>

<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div id='containerWrap' class="wrap">
<div class="markdown-body">		
		
		<h2>글쓰기</h2>
		<form action="/community/upload" method="post">
		<input type="hidden" name="writer" value="${sessionScope.loginUser.memberId}" />
			<div>
				<p>제목</p>
				<input name='title' type="text" class='boxStyle'
					placeholder="제목을 입력해주세요." />
			</div>
			<div>
				<p>내용</p>
				<div id="content" class="contentBox"></div>
				<!-- 에디터 내용을 담을 숨겨진 textarea -->
				<textarea name="content" id="hiddenContent" style="display: none;"></textarea>
			</div>
			<div id="btnWrap">
				<button id='cancleBtn' class='inputBtn'>취소</button>
				<button type="submit" id='uploadBtn' class='inputBtn' onclick="submitEditor()">등록</button>
			</div>
		</form>
		<!-- TUI 에디터 JS CDN -->

		<script>
			 const editor = new toastui.Editor({
	            el: document.querySelector('#content'), // 에디터를 적용할 요소 (컨테이너)
	            height: '500px',                        // 에디터 영역의 높이 값 (500px로 지정)
	            initialEditType: 'markdown',            // 최초로 보여줄 에디터 타입 (markdown || wysiwyg 중에 markdown버전으로 처음 보여짐)
	            initialValue: '',                       // 내용의 초기 값으로, 반드시 마크다운 문자열 형태여야 함(아무내용 없음)
	            previewStyle: 'vertical',               // 마크다운 프리뷰 스타일 (tab || vertical)
	            placeholder: '내용을 입력해 주세요.',
	        });
	        
	        function submitEditor() {
	            const markdown = editor.getMarkdown(); // 또는 getHTML()
	            document.getElementById('hiddenContent').value = markdown;
	            return true; // 폼 제출 계속 진행
	        }
    	</script>
		

</div>
</div>
</body>


</html>