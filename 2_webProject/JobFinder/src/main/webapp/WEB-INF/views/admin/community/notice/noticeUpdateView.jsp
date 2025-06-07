
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>게시글 관리-공지 추가</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css"
	rel="stylesheet" crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js"
	integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">
	<link rel="stylesheet" href="/css/community/toastPopup.css"> 

<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/admin/common.css">
<link rel="stylesheet" href="/css/admin/tableStyle.css">
<link rel="stylesheet" href="/css/admin/notice/noticeStyle.css">
<link rel="stylesheet" href="/css/community/communityCommonStyle.css"> 
<link rel="stylesheet" href="/css/community/communityUploadStyle.css"> 
<link rel="stylesheet" href="/css/community/notice/noticeUpStyle.css"> 

<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/5.5.1/github-markdown.min.css">

<!-- 에디터 및 jQuery -->
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- SweetAlert -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<style>
/*기본값(default)이 이미 "text/css"로 되어 있어서 자동인식한다하여 뺐음 */
#container {
	margin: auto;
}

#noticeUpdateContainer{
width: 1000px;

margin: auto;
}
</style>

<script type="text/javascript">

</script>
</head>
<body>
	<main class="d-flex flex-nowrap">
		<!-- 사이드바 영역 -->
		<jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>

		<!-- 사이드바 영역 -->


		<!-- 본문영역  -->
		<div id="noticeUpdateContainer">
			<form action="./update" method="post" enctype="multipart/form-data"  id="updateForm">
				<input type="hidden" name="noticeId" value="${notice.noticeId}">

				<!-- 제목 입력 -->
				<div id='titleWrap'>
					<p>공지 제목</p>
					<input id='title' name='title' type="text" class='boxStyle' value="${notice.title}"/>
				</div>
				
				<div id="noticeCategory">
					<label for="lang">유형</label>
					<select name="noticeCategoryId" id="lang">
					<c:forEach var="category" items="${noticeCategoryList}">
						  <option value="${category.noticeCategoryId}"
						  		<c:if test="${category.noticeCategoryId == notice.noticeCategoryId}">selected</c:if>
						  	>${category.noticeCategoryContent}</option>
					</c:forEach>
					</select>
				</div>

				<!-- 에디터 입력 -->
				<div id='contentWriteWrap'>
					<p>내용</p>
					<div id="noticeContent" class="contentBox"></div>
					<textarea name="content" id="hiddenContent" style="display: none;"></textarea>
				</div>

				<!-- 업로드된 파일 목록 표시 -->
				<div id="fileUpdateWrap">
					<p>첨부된 이미지 목록</p>
					<div id="updatedFileList"></div>
				</div>

				<!-- 등록/취소 버튼 -->
				<div id="btnWrap">
					<button id='cancleBtn' class='inputBtn' type="button"
						onclick="goBackToList()">취소</button>
					<button type="button" id='updateBtn' class='inputBtn'
						onclick="submitEditor()">등록</button>
				</div>
			</form>

			<!-- 에디터 초기화 및 이미지 업로드 처리 -->
			<script>
				const rawContent = `${notice.content}`;
				const formattedContent = rawContent.replace(/\n/g, '\n');
			
        		const editor = new toastui.Editor({
		            el: document.querySelector('#noticeContent'),
		            height: '500px',
		            initialEditType: 'wysiwyg',
		            initialValue: formattedContent,
		            previewStyle: 'vertical',
		            placeholder: '내용을 입력해 주세요.',
		            hooks: {
		                addImageBlobHook(blob, callback) {
		                    const formData = new FormData();
		                    formData.append('updateFile', blob);
		
		                    $.ajax({
		                        url: '/admin/community/notice/updateImage',
		                        type: 'POST',
		                        data: formData,
		                        processData: false,
		                        contentType: false,
		                        success: function(response) {
		                            const imageUrl = response.imageUrl;
		                            const fileName = response.fileName;
		                            const fileStoredName = response.fileStoredName;
		
		                            callback(imageUrl, fileName); // 에디터에 ![](url) 삽입
		
		                            // 이미지 목록에 표시
		                            const fileListContainer = document.getElementById('updatedFileList');
		                            const fileItem = document.createElement('div');
		                            fileItem.classList.add('fileItem');
		                            fileItem.setAttribute('data-url', imageUrl);
		                            fileItem.innerHTML = `
		                                <span>\${fileName}</span>
		                                <button type="button" onclick="deleteFile('\${fileName}','\${fileStoredName}', this)">삭제</button>
		                            `;
		                            fileListContainer.appendChild(fileItem);
		                        },
		                        error: function() {
		                            alert('이미지 업로드 실패');
		                        }
		                    });
		
		                    return false;
		                }
		            }
		        });
		
        		async function submitEditor() {
		
		        	
		            const markdown = editor.getMarkdown();
		            document.getElementById('hiddenContent').value = markdown;
		            
		        	if ($("#title").val().trim() === "") {
		        		askConfirm("제목을");
		        		return false;
		        	}
		        	if ($("#hiddenContent").val().trim() === "") {
		        		askConfirm("내용을");
		        		return false;
		        	}
		        	
		            const result = await Swal.fire({
		                title: "확인",
		                text: "공지 사항을 수정하시겠습니까?",
		                icon: "warning",
		                showCancelButton: true,
		                confirmButtonText: "수정",
		                cancelButtonText: '취소',
		                customClass: {
		        			confirmButton: "swalConfirmBtn",
		        			cancelButton: "swalCancelBtn",
		        		},
		        		reverseButtons: true, // 버튼 순서 거꾸로
		            });

		            if (result.isConfirmed) {
		                await Swal.fire({
		                    title: "수정 완료",
		                    text: "공지 사항이 정상적으로 저장되었습니다.",
		                    icon: "success",
		                    timer: 1500,
		                    showConfirmButton: false
		                });
		                $("#updateForm").submit(); // 수동 제출
		            } else {
		                Swal.fire({
		                    icon: "error",
		                    title: "수정이 취소되었습니다.",
		                    text: "다시 시도하거나 변경사항을 확인하세요."
		                });
		            }

		        }
		
		        function deleteFile(fileName,fileStoredName, btn) {
		            if (!confirm("정말 삭제하시겠습니까?")) return;
		
		            const fileItem = btn.parentElement;
		            const imageUrl = fileItem.getAttribute('data-url');
		
		            $.ajax({
		                url: `/admin/community/notice/deleteImage/\${fileStoredName}`,
		                type: 'DELETE',
		                success: function() {
		                    // 1. 에디터 마크다운에서 이미지 제거
		                    const markdown = editor.getMarkdown();
		                    const updatedMarkdown = markdown.replace(new RegExp(`!\\[\${fileName}\\]\\(\${imageUrl}\\)`, 'g'), '');
		                    editor.setMarkdown(updatedMarkdown);
		
		                    // 2. 첨부파일 목록에서 제거
		                    fileItem.remove();
		                },
		                error: function() {
		                    alert("파일 삭제 실패");
		                }
		            });
		        }
		    </script>`
		</div>
		<!-- 본문영역  -->
		
		<div id="askConfirm">
		</div>
		
		<script src="/js/community/toastPopup.js"></script>
	</main>

</body>
<script type="text/javascript">
//목록 페이지로 돌아가기
function goBackToList() {
	const prevUrl = sessionStorage.getItem("prevAdminNoticeListUrl");

	if (prevUrl || prevUrl!= prevUrl) {
		location.href = prevUrl;
	} else {
		location.href = "/admin/community/notice";
	}
}
</script>
</html>