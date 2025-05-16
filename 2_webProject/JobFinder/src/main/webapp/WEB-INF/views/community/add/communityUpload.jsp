<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
<link rel="stylesheet" href="/css/community/communityCommonStyle.css"> 
<link rel="stylesheet" href="/css/community/communityUploadStyle.css"> 
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/5.5.1/github-markdown.min.css">

<style type="text/css">

	
</style>
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>


</head>

<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div id='containerWrap' class="wrap">
		
			
			<h2>글쓰기</h2>

			<form action="/community/upload" method="post" enctype="multipart/form-data">
			<input type="hidden" name="writer" value="${sessionScope.userLogin.memberId}" />
				<div id='titleWrap'>
					<p>제목</p>
					<input name='title' type="text" class='boxStyle'
						placeholder="제목을 입력해주세요." />
				</div>
				<div id='contentWriteWrap'>
					<p>내용</p>
					<div id="content" class="contentBox"></div>
					<!-- 에디터 내용을 담을 숨겨진 textarea -->
					<textarea name="content" id="hiddenContent" style="display: none;"></textarea>
				</div>
				<div>
				 <!-- 🔽 여기에 파일 첨부 input 추가 -->
			    <div id="fileUploadWrap">
			        <p>첨부파일</p>
			        <input type="file" name="uploadFile" multiple>
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
		            /* hooks로직 추가(이미지명 변경) */
		            hooks: {
		            	addImageBlobHook(blob, callback) {  // 이미지 업로드 로직 커스텀
			                console.log(blob);// blob: 파일 객체. 실질적으로 업로드 된 파일
			                console.log(callback);//callback(url, string)은 파일 경로와 대체텍스트를 인자로 받음.응답으로 받은 서버에 저장된 경로와 객체의 파일명을 대체 텍스트로 전달
	            			
			                /*이미지 삽입 로직*/
	            			const formData = new FormData();//서버로 전송할 데이터를 담는 객체
			                formData.append('uploadFile', blob);//'uploadFile': 서버로 전송할 파라미터명
			            	
			                //아래 코드로 서버로 이미지 저장 후 JSON으로 응답.
			                $.ajax({
								url: '/community/uploadImage',//이미지 업로드 처리용 서버 주소
								type:'POST',
								data: formData, //전송 데이터(이미지 포함)
			                	enctype: 'multipart/form-data', //파일 전송을 위한 enctype
			                	processData: false, //formData를 문자열로 처리하지 않도록 설정
			                	contentType: false, //content-type헤더를 multipart/form-data로 자동 설정
			                	success: function(response){
								//서버로부터 정상 응답 받았을 떄 실행
								callback(response.imageUrl,'업로드된 이미지');//response.imageUrl: 서버가 반환한 이미지 접근 URL
								
			                	},
			                	error: function(error){
									console.error('이미지 업로드 실패: ',error);
									alert('이미지 업로드 중 오류 발생');
			                	}
								
			                });
	            			return false;//기본 이미지 업로드 동작 막기
		            	}
        			}
		            
		        });
		        
		        function submitEditor() {
		            const markdown = editor.getMarkdown(); // 또는 getHTML()
		            document.getElementById('hiddenContent').value = markdown;
		            return true; // 폼 제출 계속 진행
		        }

	    	</script>
			
	

</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>


</html>