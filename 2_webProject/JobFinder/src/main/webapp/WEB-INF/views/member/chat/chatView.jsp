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
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js"></script>

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
const userId = "${sessionScope.userLogin.email}"; // 세션, 페이지 렌더링 시 서버에서 넣어줘야 함
const roomId = userId; // 예를 들어, 회원 아이디로 방 구분 (서버 매핑 필요)

let ws;

function connectWebSocket() {
  if (ws) {
    ws.close();
  }

  // SockJS를 사용하여 WebSocket 연결
  const wsUrl = '/ws/chat';
  console.log('Connecting to WebSocket URL:', wsUrl);
  ws = new SockJS(wsUrl);

  ws.onopen = () => {
    console.log('WebSocket 연결됨');
    // 조용히 입장
    ws.send(JSON.stringify({
      type: 'ENTER',
      roomId: roomId,
      sender: userId,
      message: ''
    }));
  };

  ws.onmessage = (event) => {
    try {
      console.log('원본 메시지:', event.data);
      const data = JSON.parse(event.data);
      console.log('파싱된 메시지:', data);
      appendMessage(data);
    } catch (err) {
      console.error('메시지 처리 중 오류:', err);
    }
  };

  ws.onclose = (event) => {
    console.log('WebSocket 연결 종료:', event.code, event.reason);
    // 3초 후 재연결 시도
    setTimeout(() => {
      console.log('WebSocket 재연결 시도...');
      connectWebSocket();
    }, 3000);
  };

  ws.onerror = (error) => {
    console.error('WebSocket 에러:', error);
  };
}

function sendMessage() {
  const input = document.getElementById('chatInput');
  const msg = input.value.trim();
  if (!msg) return false;

  const messageObj = {
    type: 'TALK',
    roomId: roomId,
    sender: userId,
    message: msg
  };

  if (ws && ws.readyState === WebSocket.OPEN) {
    ws.send(JSON.stringify(messageObj));
    // 서버에서 에코되어 돌아올 때 표시되므로, 여기서는 즉시 표시하지 않음
  } else {
    alert('채팅방에 연결되어 있지 않습니다.');
  }

  input.value = '';
  return false;
}

function appendMessage(data) {
  if (!data || !data.message) {
    console.warn('Invalid message data:', data);
    return;
  }

  const chatMessages = document.getElementById('chatMessages');
  const div = document.createElement('div');
  const sender = data.sender || "익명";
  const message = data.message || "(메시지 없음)";
  
  // 메시지 컨테이너 스타일 설정
  div.style.marginBottom = '10px';
  div.style.padding = '8px';
  div.style.borderRadius = '5px';
  div.style.maxWidth = '70%';
  
  // 본인이 보낸 메시지와 받은 메시지를 구분
  if (sender === userId) {
    div.style.backgroundColor = '#007bff';
    div.style.color = 'white';
    div.style.marginLeft = 'auto';
    div.style.marginRight = '10px';
  } else {
    div.style.backgroundColor = '#f1f1f1';
    div.style.marginRight = 'auto';
    div.style.marginLeft = '10px';
  }
  
  // 발신자 이름과 메시지 내용 추가
  div.innerHTML = 
    '<div style="font-weight: bold; font-size: 0.9em; margin-bottom: 3px;">' + sender + '</div>' +
    '<div>' + message + '</div>';
  
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
</script>
</html>