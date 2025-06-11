<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>

<!-- 스타일 시트 -->
<link rel="stylesheet" href="/css/community/communityCommonStyle.css"> 
<link rel="stylesheet" href="/css/community/communityUploadStyle.css"> 
<link rel="stylesheet" href="/css/community/communityNav.css"> 
<link rel="stylesheet" href="/css/community/toastPopup.css"> 
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/5.5.1/github-markdown.min.css">

<!-- 에디터 및 jQuery -->
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- SweetAlert -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<style>
    #uploadedFileList {
        margin-top: 10px;
    }

    .fileItem {
        margin-bottom: 5px;
        padding: 5px;
        background-color: #f9f9f9;
        border: 1px solid #ddd;
        display: flex;
        justify-content: space-between;
        alignItems: center;
    }

    .fileItem button {
        margin-left: 10px;
        background-color: #ff6666;
        color: white;
        border: none;
        padding: 3px 7px;
        cursor: pointer;
    }
</style>

</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div id='containerWrap' class="wrap">
    <h2>글쓰기</h2>

    <form action="/community/upload" method="post" enctype="multipart/form-data"  id="uploadForm">
        <input type="hidden" name="writer" value="${sessionScope.userLogin.memberId}" />

        <!-- 제목 입력 -->
        <div id='titleWrap'>
            <p>제목</p>
            <input  id='title' name='title' type="text" class='boxStyle' placeholder="제목을 입력해주세요." />
        </div>

        <!-- 에디터 입력 -->
        <div id='contentWriteWrap'>
            <p>내용</p>
            <div id="content" class="contentBox"></div>
            <textarea name="content" id="hiddenContent" style="display: none;"></textarea>
        </div>

        <!-- 업로드된 파일 목록 표시 -->
        <div id="fileUploadWrap">
            <p>첨부된 이미지 목록</p>
            <div id="uploadedFileList"></div>
        </div>

        <!-- 등록/취소 버튼 -->
        <div id="btnWrap">
            <button id='cancleBtn' class='inputBtn' type="button" onclick="goBackToList()">취소</button>
            <button type="button" id='uploadBtn' class='inputBtn' onclick="submitEditor()">등록</button>
        </div>
    </form>

    <!-- 에디터 초기화 및 이미지 업로드 처리 -->
    <script>
        const editor = new toastui.Editor({
            el: document.querySelector('#content'),
            height: '500px',
            initialEditType: 'wysiwyg',
            initialValue: '',
            previewStyle: 'vertical',
            placeholder: '내용을 입력해 주세요.',
            hooks: {
                addImageBlobHook(blob, callback) {
                    const formData = new FormData();
                    formData.append('uploadFile', blob);

                    $.ajax({
                        url: '/community/uploadImage',
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
                            const fileListContainer = document.getElementById('uploadedFileList');
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
                        	alermPopup('이미지 업로드 실패');
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
                return;
            }

            if ($("#hiddenContent").val().trim() === "") {
                askConfirm("내용을");
                return;
            }

            const result = await Swal.fire({
                title: "확인",
                text: "게시물을 저장하시겠습니까?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonText: "저장",
                cancelButtonText: '취소',
                customClass: {
        			confirmButton: "swalConfirmBtn",
        			cancelButton: "swalCancelBtn",
        		},
        		reverseButtons: true, // 버튼 순서 거꾸로
            });

            if (result.isConfirmed) {
                await Swal.fire({
                    title: "업로드 완료",
                    text: "게시물이 정상적으로 저장되었습니다.",
                    icon: "success",
                    timer: 1500,
                    showConfirmButton: false
                });
                $("#uploadForm").submit(); // 수동 제출
            } else {
                Swal.fire({
                    icon: "error",
                    title: "등록이 취소되었습니다.",
                    text: "다시 시도하거나 변경사항을 확인하세요.",
                    confirmButtonText: "확인",
                    customClass: {
            			confirmButton: "swalConfirmBtn",
            		},
                    
                });
            }
        }

        function deleteFile(fileName,fileStoredName, btn) {

            const fileItem = btn.parentElement;
            const imageUrl = fileItem.getAttribute('data-url');

            $.ajax({
                url: `/community/deleteImage/\${fileStoredName}`,
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
                	alermPopup("파일 삭제 실패");
                }
            });
        }
    </script>
</div>


<div id="askConfirm">
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
<script src="/js/community/toastPopup.js"></script>
</body>
<script type="text/javascript">
//목록 페이지로 돌아가기
function goBackToList() {
	const prevUrl = sessionStorage.getItem("prevCommunityListUrl");

	if (prevUrl || prevUrl!= prevUrl) {
		location.href = prevUrl;
	} else {
		location.href = "/community";
	}
}
</script>
</html>
