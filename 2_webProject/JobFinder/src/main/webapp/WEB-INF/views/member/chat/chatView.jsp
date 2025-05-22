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


</script>
</html>