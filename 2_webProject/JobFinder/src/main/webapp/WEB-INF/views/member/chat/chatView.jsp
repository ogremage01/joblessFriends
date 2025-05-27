<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>개인회원 채팅</title>
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
        
        .message-member {
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
        const userType = '${sessionScope.userType}';
        const memberInfo = '${sessionScope.userLogin}';
        const memberId = '${sessionScope.userLogin.memberId}';
        const email = '${sessionScope.userLogin.email}';
        const roomId = email;  // 개인 이메일을 방 ID로 사용
        
        let ws = null;
        let reconnectAttempts = 0;
        const MAX_RECONNECT_ATTEMPTS = 5;

        // 현재 호스트 주소 가져오기
        const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
        const host = window.location.host;
        const baseUrl = `${protocol}//${host}`;
        
        // WebSocket 연결
        function connectWebSocket() {
            if (!userType || userType !== 'member' || !memberInfo) {
                alert('개인 회원 로그인이 필요한 서비스입니다.');
                window.location.href = '/auth/login';
                return;
            }

            if (ws && (ws.readyState === WebSocket.CONNECTING || ws.readyState === WebSocket.OPEN)) {
                console.log('WebSocket이 이미 연결중이거나 연결된 상태입니다.');
                return;
            }

            const wsUrl = `${baseUrl}/ws/chat`;
            console.log('WebSocket 연결 시도:', wsUrl);
            
            try {
                ws = new SockJS(wsUrl, null, {
                    transports: ['websocket', 'xhr-streaming', 'xhr-polling'],
                    debug: true
                });

                ws.onopen = function() {
                    console.log('WebSocket 연결됨');
                    reconnectAttempts = 0;
                    
                    // 입장 메시지 전송
                    const enterMsg = {
                        type: 'ENTER',
                        roomId: roomId,
                        sender: email,
                        message: ''
                    };
                    ws.send(JSON.stringify(enterMsg));
                };

                ws.onmessage = function(event) {
                    try {
                        console.log('원본 메시지:', event.data);
                        const data = JSON.parse(event.data);
                        console.log('파싱된 메시지:', data);
                        if (data.error) {
                            alert(data.error);
                            if (data.error.includes("세션")) {
                                window.location.href = '/auth/login';
                            }
                            return;
                        }
                        appendMessage(data);
                    } catch (err) {
                        console.error('메시지 처리 중 오류:', err);
                    }
                };

                ws.onclose = function(event) {
                    console.log('WebSocket 연결 종료:', event.code, event.reason);
                    ws = null;
                    
                    if (event.code === 1000 && event.reason === "Client inactive") {
                        console.log("비활성으로 인한 연결 종료. 재연결하지 않습니다.");
                        return;
                    }
                    
                    if (reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
                        reconnectAttempts++;
                        console.log(`재연결 시도 ${reconnectAttempts}/${MAX_RECONNECT_ATTEMPTS}`);
                        setTimeout(connectWebSocket, 3000);
                    } else {
                        console.log('최대 재연결 시도 횟수 초과');
                        alert('채팅 연결에 실패했습니다. 페이지를 새로고침해주세요.');
                    }
                };

                ws.onerror = function(error) {
                    console.error('WebSocket 오류:', error);
                };
            } catch (error) {
                console.error('WebSocket 연결 시도 중 오류:', error);
            }
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
                    sender: email,
                    message: message
                };
                
                ws.send(JSON.stringify(chatMessage));
                input.value = '';
            } else {
                console.log('WebSocket 상태:', ws ? ws.readyState : 'null');
                alert('채팅 연결이 끊어졌습니다. 페이지를 새로고침해주세요.');
            }
            
            return false;
        }

        // 메시지 표시
        function appendMessage(data) {
            if (!data || (!data.message && data.type !== 'ENTER')) return;
            if (data.type === 'ENTER' && !data.message) return;
            
            const chatMessages = document.getElementById('chatMessages');
            const div = document.createElement('div');
            const sender = data.senderDisplayName || "익명";
            
            div.className = 'message ' + (sender === email ? 'message-member' : 'message-admin');
            div.innerHTML = 
                '<div class="message-sender">' + sender + '</div>' +
                '<div>' + (data.message || '').replace(/\n/g, '<br>') + '</div>';
            
            chatMessages.appendChild(div);
            chatMessages.scrollTop = chatMessages.scrollHeight;
        }

        // 페이지 로드 시 WebSocket 연결
            window.addEventListener('load', () => {
            loadPreviousMessages(roomId); // 기존 connectWebSocket() 호출 전 또는 후 아무 때나
            connectWebSocket();
        });
        // 페이지 언로드 시 WebSocket 정리
        window.addEventListener('beforeunload', () => {
            if (ws && ws.readyState === WebSocket.OPEN) {
                ws.close(1000, "Page unloading");
            }
        });
        
        function loadPreviousMessages(roomId) {
            fetch('./messages/'+roomId)
                .then(res => {
                    if (!res.ok) throw new Error('메시지 로드 실패');
                    return res.json();
                })
                .then(messages => {
                    messages.forEach(msg => appendMessage(msg));
                    console.log(messages)
                })
                .catch(err => {
                    console.error('이전 메시지 불러오기 오류:', err);
                });
        }


    </script>
</body>
</html> 