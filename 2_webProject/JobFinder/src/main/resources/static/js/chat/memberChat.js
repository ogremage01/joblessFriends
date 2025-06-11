// 개인회원용 채팅 JavaScript
// JSP에서 전달받은 세션 정보를 사용
const userType = window.userType || '';
const memberInfo = window.memberInfo || '';
const memberId = window.memberId || '';
const email = window.email || '';
const roomId = email;  // 개인 이메일을 방 ID로 사용

let ws = null;
let reconnectAttempts = 0;
const MAX_RECONNECT_ATTEMPTS = 5;

// 현재 호스트 주소 가져오기 (SockJS는 HTTP 프로토콜 사용)
const protocol = window.location.protocol; // http: 또는 https:
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
    loadPreviousMessages(roomId);
    connectWebSocket();
});

// Enter 키 이벤트 추가
document.addEventListener('DOMContentLoaded', function() {
    const chatInput = document.getElementById('chatInput');
    if (chatInput) {
        chatInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                sendMessage();
            }
        });
    }
});

// 페이지 언로드 시 WebSocket 정리
window.addEventListener('beforeunload', () => {
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.close(1000, "Page unloading");
    }
});

function loadPreviousMessages(roomId) {
    fetch('./messages/' + roomId)
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