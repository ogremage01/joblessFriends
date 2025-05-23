<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/member/memberMyPage.css" />

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
<<<<<<< HEAD
=======
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js"></script>
>>>>>>> origin/jhs

</head>
<body>

<jsp:include page="../../common/header.jsp"/>

	<div class="container">
		<div id="containerWrap">
			<jsp:include page="../../common/mypageSidebar.jsp"/>
		    <div class="main">
		    
				<div id="memberChatContainer" style="display: flex; flex-direction: column; height: 600px; border: 1px solid #ccc;">
				  <div id="chatRoomTitle" style="padding: 15px; background-color: #f7f7f7; font-weight: bold;">
				    관리자와의 1:1 채팅
				  </div>
				
				  <div id="chatMessages" style="flex: 1; padding: 15px; overflow-y: auto; background: #fff; border-top: 1px solid #ddd; border-bottom: 1px solid #ddd;">
				    <!-- 메시지 표시 영역 -->
				  </div>
				
				  <form id="chatForm" style="display: flex; border-top: 1px solid #ddd; margin-top: auto;">
				    <input id="chatInput" type="text" placeholder="메시지를 입력하세요" style="flex: 1; padding: 10px; border: none;" autocomplete="off" />
				    <button type="submit" style="padding: 0 20px; background-color: #007bff; color: white; border: none; cursor: pointer;">전송</button>
				  </form>
				</div>
		    </div>
		</div>    
	</div>
	
<jsp:include page="../../common/footer.jsp" />

</body>

<script type="text/javascript">
<<<<<<< HEAD
const userId = "회원아이디"; // 세션, 페이지 렌더링 시 서버에서 넣어줘야 함
const roomId = userId; // 예를 들어, 회원 아이디로 방 구분 (서버 매핑 필요)

let ws;

function connectWebSocket() {
  ws = new WebSocket('ws://localhost:9090/ws/chat');

  ws.onopen = () => {
    console.log('WebSocket 연결됨');
    ws.send(JSON.stringify({
      type: 'ENTER',
      roomId: roomId,
      sender: userId,
      message: '입장합니다.'
    }));
  };

  ws.onmessage = (event) => {
    const data = JSON.parse(event.data);
    appendMessage(data);
  };

  ws.onclose = () => {
    console.log('WebSocket 연결 종료');
  };

  ws.onerror = (error) => {
    console.error('WebSocket 에러:', error);
  };
}

function sendMessage() {
  const input = document.getElementById('chatInput');
  const msg = input.value.trim();
  if (!msg) return false;

  if (ws && ws.readyState === WebSocket.OPEN) {
    ws.send(JSON.stringify({
      type: 'TALK',
      roomId: roomId,
      sender: userId,
      message: msg
    }));
  } else {
    alert('채팅방에 연결되어 있지 않습니다.');
  }

  input.value = '';
  return false;
}

function appendMessage(data) {
  const chatMessages = document.getElementById('chatMessages');
  const div = document.createElement('div');
  div.textContent = `${data.sender}: ${data.message}`;
  div.style.marginBottom = '5px';
  chatMessages.appendChild(div);
  chatMessages.scrollTop = chatMessages.scrollHeight;
}

// 페이지 로드 시 연결 시작
window.addEventListener('load', () => {
  connectWebSocket();
  document.getElementById('chatForm').onsubmit = (e) => {
    e.preventDefault();
    sendMessage();
  };
});


=======
const userId = "${sessionScope.userLogin.email}"; 
const roomId = userId; 

if (!userId) {
    alert('로그인이 필요한 서비스입니다.');
    window.location.href = '/auth/login';
}

let ws;
let lastActivityTime = Date.now();
const INACTIVITY_TIMEOUT = 60 * 1000; // 1분
let isConnectionExplicitlyClosedByInactivity = false;
let messageQueue = [];

function updateLastActivityTime() {
    lastActivityTime = Date.now();
    // 사용자가 활동을 재개하면, 비활성으로 인한 명시적 연결 종료 상태 해제
    if (isConnectionExplicitlyClosedByInactivity) {
        console.log("User activity detected, resetting inactivity close flag.");
        isConnectionExplicitlyClosedByInactivity = false;
    }
}

function connectWebSocket(isRetry = false) {
    if (ws && (ws.readyState === WebSocket.CONNECTING || ws.readyState === WebSocket.OPEN)) {
        console.log('WebSocket is already connecting or open.');
        return;
    }

    if (ws) {
        // 이전 onclose 핸들러가 새 연결 시도에 영향 주지 않도록 정리
        ws.onclose = null; 
        ws.close();
        ws = null; 
    }

    const wsUrl = '/ws/chat';
    console.log('Connecting to WebSocket URL:', wsUrl);
    ws = new SockJS(wsUrl, null, {
        transports: ['websocket', 'xhr-streaming', 'xhr-polling']
    });

    ws.onopen = () => {
        console.log('WebSocket 연결됨');
        updateLastActivityTime(); // 연결 자체도 활동으로 간주
        isConnectionExplicitlyClosedByInactivity = false; // 연결 성공 시 비활성 플래그 리셋

        const inactiveMsgElement = document.getElementById('inactive-disconnect-msg');
        if (inactiveMsgElement) {
            inactiveMsgElement.remove();
        }

        ws.send(JSON.stringify({
            type: 'ENTER',
            roomId: roomId,
            sender: userId,
            message: ''
        }));

        // 큐에 있던 메시지 전송
        console.log("Processing message queue. Queue size:", messageQueue.length);
        while (messageQueue.length > 0) {
            const queuedMsgObj = messageQueue.shift();
            if (ws && ws.readyState === WebSocket.OPEN) {
                ws.send(JSON.stringify(queuedMsgObj));
                console.log("Sent queued message:", queuedMsgObj);
            } else {
                console.warn("Failed to send queued message, WebSocket not open. Re-queuing.");
                messageQueue.unshift(queuedMsgObj); // 다시 큐의 맨 앞에 추가
                break; // 연결이 안 되어 있으면 중단
            }
        }
    };

    ws.onmessage = (event) => {
        try {
            console.log('원본 메시지:', event.data);
            const data = JSON.parse(event.data);
            console.log('파싱된 메시지:', data);
            if (data.error) {
                alert("채팅 서버 오류: " + data.error);
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

    ws.onclose = (event) => {
        console.log('WebSocket 연결 종료:', event.code, event.reason);
        const oldWs = ws; // 클로저를 위해 현재 ws 참조 저장
        ws = null; // ws 참조 제거

        if (event.code === 1000 && event.reason === "Client inactive") {
             console.log("Connection closed by client due to inactivity. No retry.");
             isConnectionExplicitlyClosedByInactivity = true; // 명시적 설정
             return;
        }
        
        if (event.code === 1000) { // 정상 종료 (예: 페이지 이동)
            return;
        }

        if (isConnectionExplicitlyClosedByInactivity) {
            console.log("재연결 시도 안함 (isConnectionExplicitlyClosedByInactivity is true)");
            return;
        }

        if (Date.now() - lastActivityTime > INACTIVITY_TIMEOUT) {
            console.log(`1분 이상 활동(${INACTIVITY_TIMEOUT / 1000}초)이 없어 WebSocket 재연결을 중지합니다. 메시지를 보내거나 페이지를 새로고침하여 다시 연결하세요.`);
            isConnectionExplicitlyClosedByInactivity = true;
            const chatMessagesContainer = document.getElementById('chatMessages');
            if (!document.getElementById('inactive-disconnect-msg')) {
                const div = document.createElement('div');
                div.id = 'inactive-disconnect-msg';
                div.style.textAlign = 'center';
                div.style.color = 'gray';
                div.style.padding = '10px';
                div.textContent = '채팅 연결이 중단되었습니다 (비활성 상태). 메시지를 입력하면 다시 연결됩니다.';
                chatMessagesContainer.appendChild(div);
                chatMessagesContainer.scrollTop = chatMessagesContainer.scrollHeight;
            }
            return; 
        }

        setTimeout(() => {
            // setTimeout 콜백 시점에는 isConnectionExplicitlyClosedByInactivity가 true로 바뀌었을 수 있으므로 다시 확인
            if (userId && !isConnectionExplicitlyClosedByInactivity) {
                console.log('WebSocket 재연결 시도...');
                connectWebSocket(true);
            } else {
                 console.log("재연결 조건 미충족 (userId 없거나 비활성으로 인한 종료 상태)");
            }
        }, 3000);
    };

    ws.onerror = (error) => {
        console.error('WebSocket 에러:', error);
        // onerror 후 onclose가 보통 호출되므로, onclose에서 ws=null 처리
    };
}

function sendMessage() {
    const input = document.getElementById('chatInput');
    const msg = input.value.trim();
    if (!msg) return false;

    updateLastActivityTime(); // 메시지 전송 시 활동 시간 갱신

    const inactiveMsgElement = document.getElementById('inactive-disconnect-msg');
    if (inactiveMsgElement) {
        inactiveMsgElement.remove();
    }

    const messageObj = {
        type: 'TALK',
        roomId: roomId,
        sender: userId,
        message: msg
    };

    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.send(JSON.stringify(messageObj));
    } else {
        console.log('WebSocket이 연결되지 않았습니다. 메시지를 큐에 추가하고 연결 시도...');
        messageQueue.push(messageObj);
        // 연결이 없거나 닫힌 상태일 때만 새로 연결 시도
        if (!ws || ws.readyState === WebSocket.CLOSED) {
            isConnectionExplicitlyClosedByInactivity = false; // 메시지 보내려고 하므로 비활성 상태 해제
            connectWebSocket();
        } else if (ws.readyState === WebSocket.CONNECTING) {
            console.log("이미 연결 시도 중입니다. 메시지가 큐에 추가되었습니다.");
        }
    }

    input.value = '';
    return false;
}

function appendMessage(data) {
    if (!data || (!data.message && data.type !== 'ENTER')) { // ENTER 타입은 메시지 없어도 로그만 (현재 서버에서 ENTER 메시지 안보냄)
      if (data.type !== 'ENTER') { // ENTER 타입은 빈 메시지일 수 있으므로 제외
        console.warn('Invalid message data or empty message:', data);
      }
      return;
    }
    if (data.type === 'ENTER' && !data.message) return; // 내용 없는 ENTER 메시지는 표시 안 함


    const chatMessages = document.getElementById('chatMessages');
    const div = document.createElement('div');
    const sender = data.sender || "익명";
    const messageContent = data.message || ""; // 메시지 없는 경우 대비

    div.style.marginBottom = '10px';
    div.style.padding = '8px';
    div.style.borderRadius = '5px';
    div.style.maxWidth = '70%';
    div.style.wordBreak = 'break-word'; // 긴 메시지 줄바꿈

    if (sender === userId) {
        div.style.backgroundColor = '#007bff';
        div.style.color = 'white';
        div.style.marginLeft = 'auto';
        div.style.marginRight = '10px';
    } else {
        div.style.backgroundColor = '#f1f1f1';
        div.style.color = 'black';
        div.style.marginRight = 'auto';
        div.style.marginLeft = '10px';
    }

    div.innerHTML =
        '<div style="font-weight: bold; font-size: 0.9em; margin-bottom: 3px;">' + sender + '</div>' +
        '<div>' + messageContent.replace(/\n/g, '<br>') + '</div>'; // 개행문자 처리

    chatMessages.appendChild(div);
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

window.addEventListener('load', () => {
    if (userId) { // userId가 있을 때만 초기 연결 시도
        connectWebSocket();
        document.getElementById('chatInput').addEventListener('input', updateLastActivityTime);
        document.getElementById('chatForm').onsubmit = (e) => {
            e.preventDefault();
            sendMessage();
        };
    }
});

window.addEventListener('beforeunload', () => {
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.close(1000, "Page unloading"); // 정상 종료 코드와 이유 명시
    }
});
>>>>>>> origin/jhs
</script>
</html>