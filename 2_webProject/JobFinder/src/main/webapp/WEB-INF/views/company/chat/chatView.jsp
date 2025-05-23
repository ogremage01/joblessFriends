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

    <script>
        const roomId = '${room.roomId}';
        const companyName = '${companyName}';
        let ws = null;
        let reconnectAttempts = 0;
        const MAX_RECONNECT_ATTEMPTS = 5;
        
        // WebSocket 연결
        function connectWebSocket() {
            if (ws && (ws.readyState === WebSocket.CONNECTING || ws.readyState === WebSocket.OPEN)) {
                console.log('WebSocket이 이미 연결중이거나 연결된 상태입니다.');
                return;
            }

            const wsUrl = '/ws/chat';
            console.log('WebSocket 연결 시도:', wsUrl);
            ws = new SockJS(wsUrl);

            ws.onopen = function() {
                console.log('WebSocket 연결됨');
                reconnectAttempts = 0;
                
                // 입장 메시지 전송
                const enterMsg = {
                    type: 'ENTER',
                    roomId: roomId,
                    sender: companyName
                };
                ws.send(JSON.stringify(enterMsg));
                
                // 이전 메시지 로드
                loadPreviousMessages();
            };

            ws.onmessage = function(event) {
                const data = JSON.parse(event.data);
                if (data.error) {
                    alert(data.error);
                    return;
                }
                appendMessage(data);
            };

            ws.onclose = function() {
                console.log('WebSocket 연결 종료');
                ws = null;
                
                if (reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
                    reconnectAttempts++;
                    console.log(`재연결 시도 ${reconnectAttempts}/${MAX_RECONNECT_ATTEMPTS}`);
                    setTimeout(connectWebSocket, 3000);
                }
            };

            ws.onerror = function(error) {
                console.error('WebSocket 오류:', error);
            };
        }

        // 이전 메시지 로드
        function loadPreviousMessages() {
            fetch('/admin/chat/messages/' + roomId)
                .then(response => response.json())
                .then(messages => {
                    const chatMessages = document.getElementById('chatMessages');
                    chatMessages.innerHTML = '';
                    messages.forEach(message => appendMessage(message));
                })
                .catch(error => console.error('이전 메시지 로드 실패:', error));
        }

        // 메시지 전송
        function sendMessage() {
            const input = document.getElementById('chatInput');
            const message = input.value.trim();
            
            if (!message) return false;
            
            if (ws && ws.readyState === WebSocket.OPEN) {
                const chatMessage = {
                    type: 'TALK',
                    roomId: roomId,
                    sender: companyName,
                    message: message
                };
                
                ws.send(JSON.stringify(chatMessage));
                input.value = '';
            } else {
                alert('채팅 연결이 끊어졌습니다. 페이지를 새로고침해주세요.');
            }
            
            return false;
        }

        // 메시지 표시
        function appendMessage(data) {
            if (!data || !data.message) return;
            
            const chatMessages = document.getElementById('chatMessages');
            const div = document.createElement('div');
            const sender = data.sender || "익명";
            
            div.className = 'message ' + (sender === companyName ? 'message-company' : 'message-admin');
            div.innerHTML = 
                '<div class="message-sender">' + sender + '</div>' +
                '<div>' + data.message + '</div>';
            
            chatMessages.appendChild(div);
            chatMessages.scrollTop = chatMessages.scrollHeight;
        }

        // 페이지 로드 시 WebSocket 연결
        window.onload = connectWebSocket;
    </script>
</body>
</html> 