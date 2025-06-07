<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>기업회원 채팅</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
    <style>
        .chat-container {
            max-width: 800px;
            margin: 30px auto;
            padding: 20px;
        }
        
        .chat-messages {
            height: 400px;
            border: 1px solid #ddd;
            padding: 15px;
            overflow-y: auto;
            margin-bottom: 20px;
            background: #f9f9f9;
        }
        
        .message {
            margin-bottom: 10px;
            padding: 8px;
            border-radius: 5px;
            max-width: 70%;
        }
        
        .message-company {
            background-color: #007bff;
            color: white;
            margin-left: auto;
            margin-right: 10px;
        }
        
        .message-admin {
            background-color: #f1f1f1;
            margin-right: auto;
            margin-left: 10px;
        }
        
        .message-sender {
            font-weight: bold;
            font-size: 0.9em;
            margin-bottom: 3px;
        }
        
        .chat-input {
            display: flex;
            gap: 10px;
        }
        
        .chat-input input {
            flex-grow: 1;
        }
    </style>
</head>
<body>
    <div class="chat-container">
        <h3 class="mb-4">관리자와의 채팅</h3>
        <div id="chatMessages" class="chat-messages">
            <!-- 채팅 메시지들이 여기 표시됩니다 -->
        </div>
        <form id="chatForm" class="chat-input" onsubmit="return sendMessage();">
            <input type="text" id="chatInput" class="form-control" 
                placeholder="메시지를 입력하세요" autocomplete="off" />
            <button type="submit" class="btn btn-primary">전송</button>
        </form>
    </div>

    <!-- 세션 정보를 JavaScript 변수로 전달 -->
    <script>
        // 세션 정보를 전역 변수로 설정
        window.userType = '${sessionScope.userType}';
        window.companyInfo = '${sessionScope.userLogin}';
        window.companyId = '${sessionScope.userLogin.companyId}';
        window.companyName = '${sessionScope.userLogin.companyName}';
    </script>
    <script src="/js/chat/companyChat.js"></script>
</body>
</html> 