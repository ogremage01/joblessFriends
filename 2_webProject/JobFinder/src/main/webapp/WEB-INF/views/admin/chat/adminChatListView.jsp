<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>관리자 채팅 - 어디보잡 관리자</title>
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js" 
        integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
    
    <!-- 공통 스타일 적용 -->
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/admin/adminStyle.css">
    <link href="/css/admin/chat/adminChatView.css" rel="stylesheet">
</head>

<body>

<div id="container">
    <div id="containerWrap">
        <div class="admin-container">
            <div class="admin-header">
                <h1 class="mainTitle">채팅 관리</h1>
            </div>
            
            <!-- 사이드바 영역 -->
            <div class="admin-sidebar">
                <jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
            </div>
            
            <!-- 메인 컨텐츠 영역 -->
            <div class="admin-main">
                <div class="admin-content" style="display: flex; height: 600px;">
                    <!-- 좌측: 채팅방 리스트 -->
                    <div class="chat-room-list" style="width: 300px; border-right: 1px solid #dee2e6; padding: 20px;">
                        <h5 class="mb-3"><i class="bi bi-chat-dots"></i> 채팅방 목록</h5>
                        <ul id="allRoomList" class="list-group list-group-flush">
                            <li class="list-group-item text-center text-muted py-4">
                                <i class="bi bi-chat-square-text fs-1 mb-3 d-block"></i>
                                채팅방이 없습니다.
                            </li>
                        </ul>
                    </div>

                    <!-- 우측: 채팅 메시지 영역 -->
                    <div class="chat-message-area" style="flex: 1; display: flex; flex-direction: column;">
                        <!-- 채팅방 헤더 -->
                        <div class="chat-header" style="padding: 15px 20px; border-bottom: 1px solid #dee2e6; background-color: #f8f9fa;">
                            <h5 id="chatRoomTitle" class="mb-0">
                                <i class="bi bi-chat-left-text"></i> 채팅방을 선택하세요
                            </h5>
                        </div>
                        
                        <!-- 채팅 메시지들 -->
                        <div id="chatMessages" class="chat-messages" style="flex: 1; overflow-y: auto; padding: 20px;">
                            <div class="text-center text-muted py-5">
                                <i class="bi bi-arrow-left-circle fs-1 mb-3 d-block"></i>
                                좌측 목록에서 채팅방을 선택해주세요.
                            </div>
                        </div>
                        
                        <!-- 메시지 입력 폼 -->
                        <div class="chat-input-form" style="padding: 15px 20px; border-top: 1px solid #dee2e6; background-color: #f8f9fa;">
                            <form id="chatForm" class="d-flex gap-2" onsubmit="return false;">
                                <input type="text" id="chatInput" class="form-control" 
                                    placeholder="메시지를 입력하세요" autocomplete="off" />
                                <button type="submit" class="add-btn">
                                    전송
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 채팅 관련 JavaScript -->
<script src="/js/admin/chat/adminChatView.js"></script>
</body>
</html>