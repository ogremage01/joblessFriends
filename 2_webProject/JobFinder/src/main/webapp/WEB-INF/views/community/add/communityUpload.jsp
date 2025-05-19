<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>

<!-- 스타일 시트 -->
<link rel="stylesheet" href="/css/community/communityCommonStyle.css"> 
<link rel="stylesheet" href="/css/community/communityUploadStyle.css"> 
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/5.5.1/github-markdown.min.css">

<!-- 에디터 및 jQuery -->
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

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

    <form action="/community/upload" method="post" enctype="multipart/form-data">
        <input type="hidden" name="writer" value="${sessionScope.userLogin.memberId}" />

        <!-- 제목 입력 -->
        <div id='titleWrap'>
            <p>제목</p>
            <input name='title' type="text" class='boxStyle' placeholder="제목을 입력해주세요." />
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
            <button id='cancleBtn' class='inputBtn' type="button" onclick="history.back()">취소</button>
            <button type="submit" id='uploadBtn' class='inputBtn' onclick="submitEditor()">등록</button>
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
                            const fileId = response.fileId;

                            callback(imageUrl, fileName); // 에디터에 ![](url) 삽입

                            // 이미지 목록에 표시
                            const fileListContainer = document.getElementById('uploadedFileList');
                            const fileItem = document.createElement('div');
                            fileItem.classList.add('fileItem');
                            fileItem.setAttribute('data-url', imageUrl);
                            fileItem.innerHTML = `
                                <span>\${fileName}</span>
                                <button type="button" onclick="deleteFile('\${fileId}', this)">삭제</button>
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

        function submitEditor() {
            const markdown = editor.getMarkdown();
            document.getElementById('hiddenContent').value = markdown;
            return true;
        }

        function deleteFile(fileId, btn) {
            if (!confirm("정말 삭제하시겠습니까?")) return;

            const fileItem = btn.parentElement;
            const imageUrl = fileItem.getAttribute('data-url');

            $.ajax({
                url: `/community/deleteImage/\${fileId}`,
                type: 'DELETE',
                success: function() {
                    // 1. 에디터 마크다운에서 이미지 제거
                    const markdown = editor.getMarkdown();
                    const updatedMarkdown = markdown.replace(new RegExp(`!\\[.*?\\]\\(\${imageUrl}\\)`, 'g'), '');
                    editor.setMarkdown(updatedMarkdown);

                    // 2. 첨부파일 목록에서 제거
                    fileItem.remove();
                },
                error: function() {
                    alert("파일 삭제 실패");
                }
            });
        }
    </script>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
