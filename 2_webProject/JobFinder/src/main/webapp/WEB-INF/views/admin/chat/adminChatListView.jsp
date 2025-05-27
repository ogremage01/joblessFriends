<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>관리자 채팅</title>
    
    <!-- CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link href="/css/admin/chat/adminChatView.css" rel="stylesheet">
    
    <!-- JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js" 
        integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js" type="text/javascript"></script>
</head>

<body>
    <main class="d-flex flex-nowrap">
        <!-- 사이드바 영역 -->
        <jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
        
        <!-- 본문영역 -->
        <div id="container" class="d-flex">
            <!-- 좌측: 채팅방 리스트 -->
            <div class="chat-room-list">
                <ul id="allRoomList" class="list-group">
                    <li class="list-group-item">채팅방이 없습니다.</li>
                </ul>
            </div>

            <!-- 우측: 채팅 메시지 영역 -->
            <div class="chat-message-area">
                <h5 id="chatRoomTitle">채팅방을 선택하세요</h5>
                <div id="chatMessages" class="chat-messages">
                    <!-- 채팅 메시지들이 여기 들어감 -->
                </div>
                <form id="chatForm" class="d-flex" onsubmit="return false;">
                    <input type="text" id="chatInput" class="form-control" 
                        placeholder="메시지를 입력하세요" autocomplete="off" />
                    <button type="submit" class="btn btn-primary ms-2">전송</button>
                </form>
            </div>
        </div>
    </main>

    <!-- 채팅 관련 JavaScript -->
    <script src="/js/admin/chat/adminChatView.js"></script>
</body>
</html>