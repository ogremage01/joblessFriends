<<<<<<< HEAD
<!-- 관리자 로그인 여부를 묻는 자바구문이 들어가야 할 부분 -->
=======
>>>>>>> origin/jhs
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<<<<<<< HEAD
    <title>adminMain</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" 
      crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js" 
	integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        /*기본값(default)이 이미 "text/css"로 되어 있어서 자동인식한다하여 뺐음 */
        #container{
        	margin: auto;
        }
    </style>
    
    <script type="text/javascript">
    
    </script>
</head>
<body>
<main class="d-flex flex-nowrap">
	<!-- 사이드바 영역 -->
<jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
  <!-- 사이드바 영역 -->
	  <!-- 본문영역  -->
  		  <div id="container" class="d-flex" style="width: 1000px; height: 600px; margin-top: 20px;">

  <!-- 좌측: 채팅방 리스트 -->
  <div style="width: 300px; border-right: 1px solid #ddd; padding: 10px;">
    <ul class="nav nav-tabs" id="chatRoomTabs" role="tablist">
      <li class="nav-item" role="presentation">
        <button class="nav-link active" id="member-tab" data-bs-toggle="tab" data-bs-target="#memberRooms" type="button" role="tab" aria-controls="memberRooms" aria-selected="true">회원 채팅방</button>
      </li>
      <li class="nav-item" role="presentation">
        <button class="nav-link" id="company-tab" data-bs-toggle="tab" data-bs-target="#companyRooms" type="button" role="tab" aria-controls="companyRooms" aria-selected="false">기업 채팅방</button>
      </li>
    </ul>
    <div class="tab-content" id="chatRoomTabsContent" style="height: 530px; overflow-y: auto; margin-top: 10px;">
      <!-- 회원 채팅방 리스트 -->
      <div class="tab-pane fade show active" id="memberRooms" role="tabpanel" aria-labelledby="member-tab">
        <ul id="memberRoomList" class="list-group">
          <!-- AJAX로 채팅방 목록 로드 후 li 채워질 예정 -->
          <li class="list-group-item">채팅방이 없습니다.</li>
        </ul>
      </div>
      <!-- 기업 채팅방 리스트 -->
      <div class="tab-pane fade" id="companyRooms" role="tabpanel" aria-labelledby="company-tab">
        <ul id="companyRoomList" class="list-group">
          <li class="list-group-item">채팅방이 없습니다.</li>
        </ul>
      </div>
    </div>
  </div>

  <!-- 우측: 채팅 메시지 영역 -->
  <div style="flex-grow: 1; padding: 10px; display: flex; flex-direction: column; height: 600px;">
    <h5 id="chatRoomTitle">채팅방을 선택하세요</h5>
    <div id="chatMessages" style="flex-grow: 1; border: 1px solid #ccc; padding: 10px; overflow-y: auto; margin-bottom: 10px; background: #f9f9f9;">
      <!-- 채팅 메시지들이 여기 들어감 -->
    </div>
    <form id="chatForm" style="display: flex;" onsubmit="return sendMessage();">
      <input type="text" id="chatInput" class="form-control" placeholder="메시지를 입력하세요" autocomplete="off" />
      <button type="submit" class="btn btn-primary ms-2">전송</button>
    </form>
  </div>

</div>
      <!-- 본문영역  -->
</main>

</body>

<script>

	let currentRoomId = null;
  // 예시: 채팅방 선택 시 타이틀 변경 및 메시지 초기화
  document.querySelectorAll('#memberRoomList, #companyRoomList').forEach(list => {
    list.addEventListener('click', function(e) {
      if(e.target && e.target.nodeName === "LI"){
        const roomName = e.target.textContent;
        document.getElementById('chatRoomTitle').textContent = roomName;
        document.getElementById('chatMessages').innerHTML = ''; // 메시지 초기화
        // TODO: 선택된 방에 맞게 WebSocket 연결, 메시지 로딩 등 구현
      }
    });
  });

  function sendMessage(){
	  const input = document.getElementById('chatInput');
	  const msg = input.value.trim();
	  if(!msg) return false;

	  if(ws && ws.readyState === WebSocket.OPEN){
	    ws.send(JSON.stringify({
	      type: 'TALK',
	      roomId: currentRoomId,  // 이 변수를 전역에 선언해 selectRoom에서 할당하세요
	      sender: '관리자',
	      message: msg
	    }));
	  } else {
	    alert('채팅방에 연결되어 있지 않습니다.');
	  }

	  input.value = '';
	  return false; // form submit 막기
	}

  
  function loadChatRooms() {
	  // 회원 채팅방 목록 호출
	  fetch('/rooms?type=member')
	    .then(response => response.json())
	    .then(data => {
	      const memberRoomList = document.getElementById('memberRoomList');
	      memberRoomList.innerHTML = ''; // 초기화
	      if(data.length === 0) {
	        memberRoomList.innerHTML = '<li class="list-group-item">채팅방이 없습니다.</li>';
	      } else {
	        data.forEach(room => {
	          const li = document.createElement('li');
	          li.className = 'list-group-item list-group-item-action';
	          li.textContent = room.name;
	          li.dataset.roomId = room.roomId;
	          li.onclick = () => selectRoom(room);
	          memberRoomList.appendChild(li);
	        });
	      }
	    })
	    .catch(err => {
	      console.error('회원 채팅방 목록 로드 실패', err);
	    });

	  // 기업 채팅방 목록 호출
	  fetch('/rooms?type=company')
	    .then(response => response.json())
	    .then(data => {
	      const companyRoomList = document.getElementById('companyRoomList');
	      companyRoomList.innerHTML = '';
	      if(data.length === 0) {
	        companyRoomList.innerHTML = '<li class="list-group-item">채팅방이 없습니다.</li>';
	      } else {
	        data.forEach(room => {
	          const li = document.createElement('li');
	          li.className = 'list-group-item list-group-item-action';
	          li.textContent = room.name;
	          li.dataset.roomId = room.roomId;
	          li.onclick = () => selectRoom(room);
	          companyRoomList.appendChild(li);
	        });
	      }
	    })
	    .catch(err => {
	      console.error('기업 채팅방 목록 로드 실패', err);
	    });
	}

	// 채팅방 선택 시 처리 함수 (임시)
function selectRoom(room) {
  currentRoomId = room.roomId;
  document.getElementById('chatRoomTitle').textContent = room.name;
  document.getElementById('chatMessages').innerHTML = '';
  
  // WebSocket 연결
  connectWebSocket(room.roomId);
  console.log('선택된 방:', room);
}



  ws = new WebSocket('ws://localhost:9090/ws/chat');

  ws.onopen = () => {
    console.log('WebSocket 연결됨');
    // 방 입장 메시지 전송
    ws.send(JSON.stringify({
      type: 'ENTER',
      roomId: roomId,
      sender: '관리자',
      message: '입장합니다.'
    }));
  };

  ws.onmessage = (event) => {
    const data = JSON.parse(event.data);
    console.log('받은 메시지:', data);
    appendMessage(data);
  };

  ws.onclose = () => {
    console.log('WebSocket 연결 종료');
  };

  ws.onerror = (error) => {
    console.error('WebSocket 에러:', error);
  };
}

// 메시지 출력 함수 추가
function appendMessage(data) {
  const chatMessages = document.getElementById('chatMessages');
  const div = document.createElement('div');
  div.textContent = `${data.sender}: ${data.message}`;
  div.style.marginBottom = '5px';
  chatMessages.appendChild(div);
  chatMessages.scrollTop = chatMessages.scrollHeight;
}


	// 페이지 로드 시 채팅방 목록 불러오기
	window.addEventListener('load', () => {
	  loadChatRooms();
	});
	
	
	let ws;

	function connectWebSocket(roomId) {
	  if(ws) {
	    ws.close();  // 기존 연결 닫기
	  }

	  ws = new WebSocket('ws://localhost:9090/ws/chat');

	  ws.onopen = () => {
	    console.log('WebSocket 연결됨');
	    // 방 입장 메시지 전송 (예)
	    ws.send(JSON.stringify({
	      type: 'ENTER',
	      roomId: roomId,
	      sender: '관리자',
	      message: '입장합니다.'
	    }));
	  };

	  ws.onmessage = (event) => {
	    const data = JSON.parse(event.data);
	    console.log('받은 메시지:', data);
	    // TODO: 받은 메시지를 채팅창에 추가하는 코드 작성
	  };

	  ws.onclose = () => {
	    console.log('WebSocket 연결 종료');
	  };

	  ws.onerror = (error) => {
	    console.error('WebSocket 에러:', error);
	  };
	}

</script>

=======
    <title>관리자 채팅</title>
    
    <!-- CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    
    <!-- JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js" 
        integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js"></script>

    <style>
        /* 레이아웃 스타일 */
        #container {
            margin: auto;
        }
        
        /* 채팅방 목록 스타일 */
        .chat-room-list {
            width: 300px;
            border-right: 1px solid #ddd;
            padding: 10px;
        }
        
        .chat-room-tabs {
            height: 530px;
            overflow-y: auto;
            margin-top: 10px;
        }
        
        /* 채팅 메시지 영역 스타일 */
        .chat-message-area {
            flex-grow: 1;
            padding: 10px;
            display: flex;
            flex-direction: column;
            height: 600px;
        }
        
        .chat-messages {
            flex-grow: 1;
            border: 1px solid #ccc;
            padding: 10px;
            overflow-y: auto;
            margin-bottom: 10px;
            background: #f9f9f9;
        }
        
        /* 메시지 스타일 */
        .message {
            margin-bottom: 10px;
            padding: 8px;
            border-radius: 5px;
            max-width: 70%;
        }
        
        .message-admin {
            background-color: #007bff;
            color: white;
            margin-left: auto;
            margin-right: 10px;
        }
        
        .message-user {
            background-color: #f1f1f1;
            margin-right: auto;
            margin-left: 10px;
        }
        
        .message-sender {
            font-weight: bold;
            font-size: 0.9em;
            margin-bottom: 3px;
        }
    </style>
</head>

<body>
    <main class="d-flex flex-nowrap">
        <!-- 사이드바 영역 -->
        <jsp:include page="/WEB-INF/views/admin/sideBar.jsp"></jsp:include>
        
        <!-- 본문영역 -->
        <div id="container" class="d-flex" style="width: 1000px; height: 600px; margin-top: 20px;">
            <!-- 좌측: 채팅방 리스트 -->
            <div class="chat-room-list">
                <ul class="nav nav-tabs" id="chatRoomTabs" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="member-tab" data-bs-toggle="tab" 
                            data-bs-target="#memberRooms" type="button" role="tab" 
                            aria-controls="memberRooms" aria-selected="true">회원 채팅방</button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="company-tab" data-bs-toggle="tab" 
                            data-bs-target="#companyRooms" type="button" role="tab" 
                            aria-controls="companyRooms" aria-selected="false">기업 채팅방</button>
                    </li>
                </ul>
                
                <div class="tab-content chat-room-tabs" id="chatRoomTabsContent">
                    <!-- 회원 채팅방 리스트 -->
                    <div class="tab-pane fade show active" id="memberRooms" role="tabpanel" aria-labelledby="member-tab">
                        <ul id="memberRoomList" class="list-group">
                            <li class="list-group-item">채팅방이 없습니다.</li>
                        </ul>
                    </div>
                    
                    <!-- 기업 채팅방 리스트 -->
                    <div class="tab-pane fade" id="companyRooms" role="tabpanel" aria-labelledby="company-tab">
                        <ul id="companyRoomList" class="list-group">
                            <li class="list-group-item">채팅방이 없습니다.</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- 우측: 채팅 메시지 영역 -->
            <div class="chat-message-area">
                <h5 id="chatRoomTitle">채팅방을 선택하세요</h5>
                <div id="chatMessages" class="chat-messages">
                    <!-- 채팅 메시지들이 여기 들어감 -->
                </div>
                <form id="chatForm" style="display: flex;" onsubmit="return sendMessage();">
                    <input type="text" id="chatInput" class="form-control" 
                        placeholder="메시지를 입력하세요" autocomplete="off" />
                    <button type="submit" class="btn btn-primary ms-2">전송</button>
                </form>
            </div>
        </div>
    </main>

    <!-- 채팅 관련 JavaScript -->
    <script>
        // WebSocket 관련 변수
        let ws = null;
        let currentRoomId = null;
        let lastActivityTime = Date.now();
        const INACTIVITY_TIMEOUT = 60 * 1000; // 1분
        let isConnectionExplicitlyClosedByInactivity = false;
        let messageQueue = [];

        // =========================================
        // 활동 감지 및 WebSocket 연결 관리
        // =========================================
        
        function updateLastActivityTime() {
            lastActivityTime = Date.now();
            if (isConnectionExplicitlyClosedByInactivity) {
                console.log("사용자 활동 감지됨, 비활성 상태 플래그 초기화");
                isConnectionExplicitlyClosedByInactivity = false;
                if (currentRoomId) {
                    connectWebSocket(currentRoomId);
                }
            }
        }

        // 사용자 입력 이벤트 리스너
        document.getElementById('chatInput').addEventListener('keypress', updateLastActivityTime);
        document.getElementById('chatInput').addEventListener('input', updateLastActivityTime);
        document.getElementById('chatForm').addEventListener('submit', updateLastActivityTime);

        // =========================================
        // WebSocket 연결 관리
        // =========================================
        
        function connectWebSocket(roomId) {
            if (!roomId) {
                console.error('WebSocket 연결을 위한 roomId가 제공되지 않았습니다');
                return;
            }

            if (ws && (ws.readyState === WebSocket.CONNECTING || ws.readyState === WebSocket.OPEN)) {
                console.log('WebSocket이 이미 연결중이거나 연결된 상태입니다.');
                return;
            }

            const wsUrl = window.location.protocol === 'https:' ? 'wss://' : 'ws://';
            const host = window.location.host;
            const fullUrl = wsUrl + host + '/ws/chat';
            
            console.log('WebSocket 연결 시도:', fullUrl, '방 ID:', roomId);
            ws = new SockJS(fullUrl);

            // WebSocket 이벤트 핸들러 설정
            setupWebSocketHandlers(ws, roomId);
        }

        function setupWebSocketHandlers(ws, roomId) {
            // 연결 성공 시
            ws.onopen = () => handleWebSocketOpen(ws, roomId);
            
            // 메시지 수신 시
            ws.onmessage = handleWebSocketMessage;
            
            // 연결 종료 시
            ws.onclose = handleWebSocketClose;
            
            // 에러 발생 시
            ws.onerror = (error) => console.error('WebSocket 오류:', error);
        }

        function handleWebSocketOpen(ws, roomId) {
            console.log('WebSocket 연결됨, roomId:', roomId);
            updateLastActivityTime();
            isConnectionExplicitlyClosedByInactivity = false;

            removeInactivityMessage();
            
            // 입장 메시지 전송
            if (roomId && roomId.trim() !== '') {
                const sanitizedRoomId = roomId.toString().trim();
                const enterMessage = {
                    type: 'ENTER',
                    roomId: sanitizedRoomId,
                    sender: '관리자',
                    message: '관리자가 입장했습니다.'
                };
                console.log('입장 메시지 전송:', enterMessage);
                ws.send(JSON.stringify(enterMessage));
            } else {
                console.warn('입장 메시지 전송 실패: 유효하지 않은 roomId');
            }
            
            // 큐에 있는 메시지 전송
            processMessageQueue(ws);
        }

        function handleWebSocketMessage(event) {
            try {
                console.log('수신된 메시지:', event.data);
                const data = JSON.parse(event.data);
                
                if (data.error) {
                    handleError(data.error);
                    return;
                }
                
                if (data.roomId === currentRoomId && data.message) {
                    appendMessage(data);
                }
                
                updateLastActivityTime();
            } catch (err) {
                console.error('메시지 처리 중 오류:', err);
            }
        }

        function handleWebSocketClose(event) {
            console.log('WebSocket 연결 종료:', event.code, event.reason);
            const oldWs = ws;
            ws = null;

            if (handleSpecialCloseConditions(event)) return;
            
            attemptReconnection();
        }

        // =========================================
        // 메시지 처리 함수들
        // =========================================
        
        function sendMessage() {
            const input = document.getElementById('chatInput');
            const msg = input.value.trim();
            if (!msg) return false;

            updateLastActivityTime();

            if (!currentRoomId) {
                alert('채팅방을 먼저 선택해주세요.');
                return false;
            }

            const messageObj = {
                type: 'TALK',
                roomId: currentRoomId,
                sender: '관리자',
                message: msg
            };

            if (ws && ws.readyState === WebSocket.OPEN) {
                ws.send(JSON.stringify(messageObj));
            } else {
                console.log('WebSocket이 연결되어 있지 않음. 메시지를 큐에 저장:', messageObj);
                messageQueue.push(messageObj);
                
                if (!isConnectionExplicitlyClosedByInactivity) {
                    connectWebSocket(currentRoomId);
                }
            }

            input.value = '';
            return false;
        }

        function appendMessage(data) {
            if (!data || !data.message) {
                console.warn('Invalid message data:', data);
                return;
            }

            console.log('메시지 추가:', data);
            const chatMessages = document.getElementById('chatMessages');
            const div = document.createElement('div');
            const sender = data.sender || "익명";
            
            div.className = 'message ' + (sender === '관리자' ? 'message-admin' : 'message-user');
            div.innerHTML = 
                '<div class="message-sender">' + sender + '</div>' +
                '<div>' + data.message + '</div>';
            
            chatMessages.appendChild(div);
            chatMessages.scrollTop = chatMessages.scrollHeight;
        }

        // =========================================
        // 채팅방 관리 함수들
        // =========================================
        
        function loadChatRooms() {
            loadMemberRooms();
            loadCompanyRooms();
        }

        function loadMemberRooms() {
            fetchRooms('member', 'memberRoomList');
        }

        function loadCompanyRooms() {
            fetchRooms('company', 'companyRoomList');
        }

        function fetchRooms(type, listId) {
            fetch('/admin/chat/rooms?type=' + type)
                .then(response => {
                    if (!response.ok) {
                        if (response.status === 401) {
                            window.location.href = '/auth/login';
                            throw new Error('로그인이 필요합니다.');
                        }
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    if (typeof data === 'string') {
                        throw new Error(data);
                    }
                    updateRoomList(data, listId);
                })
                .catch(err => {
                    console.error('채팅방 목록 로드 실패:', err);
                    const roomList = document.getElementById(listId);
                    if (err.message === '로그인이 필요합니다.') {
                        roomList.innerHTML = '<li class="list-group-item text-danger">로그인이 필요합니다. 잠시 후 로그인 페이지로 이동합니다.</li>';
                    } else {
                        roomList.innerHTML = '<li class="list-group-item text-danger">채팅방 목록을 불러오는데 실패했습니다.</li>';
                    }
                });
        }

        function selectRoom(roomId, roomName) {
            if (!roomId || roomId === 'undefined' || roomId === 'null' || roomId.trim() === '') {
                console.error('유효하지 않은 채팅방 ID:', roomId);
                alert('유효하지 않은 채팅방입니다.');
                return;
            }

            const sanitizedRoomId = roomId.trim();
            const sanitizedRoomName = (roomName || roomId).trim();

            console.log('채팅방 선택:', { roomId: sanitizedRoomId, roomName: sanitizedRoomName });
            
            if (currentRoomId === sanitizedRoomId) {
                console.log('이미 선택된 채팅방입니다.');
                return;
            }
            
            currentRoomId = sanitizedRoomId;
            updateRoomTitle({ name: sanitizedRoomName });
            clearMessages();
            loadPreviousMessages(sanitizedRoomId);
            
            // WebSocket 연결 관리
            cleanupExistingConnection();
            initializeNewConnection(sanitizedRoomId);
        }

        function updateRoomList(rooms, listId) {
            const roomList = document.getElementById(listId);
            if (!rooms || !Array.isArray(rooms) || rooms.length === 0) {
                roomList.innerHTML = '<li class="list-group-item">채팅방이 없습니다.</li>';
                return;
            }

            console.log(`채팅방 목록 업데이트 (${listId}):`, rooms);

            const validRooms = rooms.filter(room => {
                if (!room || !room.roomId) {
                    console.warn('유효하지 않은 방 정보:', room);
                    return false;
                }
                return true;
            });

            if (validRooms.length === 0) {
                roomList.innerHTML = '<li class="list-group-item">유효한 채팅방이 없습니다.</li>';
                return;
            }

            roomList.innerHTML = validRooms.map(room => {
                const roomId = room.roomId.trim();
                let displayName = room.name ? room.name.trim() : roomId;

                // 채팅방 타입에 따른 이름 형식 확인
                if (listId === 'companyRoomList' && !displayName.includes('기업')) {
                    displayName = `기업 ${displayName}`;
                } else if (listId === 'memberRoomList' && !displayName.includes('채팅방')) {
                    displayName = `${displayName}의 채팅방`;
                }

                console.log(`채팅방 정보 - ID: ${roomId}, 이름: ${displayName}`);

                return `
                    <li class="list-group-item" style="cursor: pointer;" 
                        data-room-id="${roomId}"
                        data-room-name="${displayName.replace(/'/g, "\\'")}"
                        onclick="selectRoom('${roomId}', '${displayName.replace(/'/g, "\\'")}')">
                        <div class="d-flex justify-content-between align-items-center">
                            <span>${displayName}</span>
                            <span class="badge bg-primary rounded-pill">
                                ${room.sessions ? room.sessions.length : 0}
                            </span>
                        </div>
                    </li>
                `;
            }).join('');
        }

        function handleRoomLoadError(error, listId) {
            console.error('채팅방 목록 로드 실패:', error);
            const roomList = document.getElementById(listId);
            roomList.innerHTML = '<li class="list-group-item text-danger">채팅방 목록을 불러오는데 실패했습니다.</li>';
        }

        function updateRoomTitle(room) {
            const titleElement = document.getElementById('chatRoomTitle');
            titleElement.textContent = room.name;
        }

        function clearMessages() {
            const chatMessages = document.getElementById('chatMessages');
            chatMessages.innerHTML = '';
        }

        function cleanupExistingConnection() {
            if (ws) {
                ws.close();
                ws = null;
            }
        }

        function initializeNewConnection(roomId) {
            if (!roomId) {
                console.error('채팅방 ID가 없습니다.');
                return;
            }
            connectWebSocket(roomId);
        }

        // =========================================
        // 이전 메시지 로드 함수
        // =========================================
        
        function loadPreviousMessages(roomId) {
            if (!roomId || roomId.trim() === '') {
                console.error('채팅방 ID가 없습니다.');
                return;
            }

            const sanitizedRoomId = roomId.toString().trim();
            console.log('이전 메시지 로드 시도:', sanitizedRoomId);

            fetch(`/admin/chat/messages/${sanitizedRoomId}`.replace(/\/+$/, ''))
                .then(response => {
                    if (!response.ok) {
                        if (response.status === 401) {
                            window.location.href = '/auth/login';
                            throw new Error('로그인이 필요합니다.');
                        }
                        throw new Error('메시지 로드 실패');
                    }
                    return response.json();
                })
                .then(data => {
                    if (typeof data === 'string') {
                        throw new Error(data);
                    }
                    console.log('로드된 메시지:', data);
                    if (Array.isArray(data)) {
                        data.forEach(message => appendMessage(message));
                    }
                })
                .catch(error => {
                    console.error('이전 메시지 로드 중 오류:', error);
                    const chatMessages = document.getElementById('chatMessages');
                    const errorDiv = document.createElement('div');
                    errorDiv.className = 'text-center text-danger';
                    if (error.message === '로그인이 필요합니다.') {
                        errorDiv.textContent = '로그인이 필요합니다. 잠시 후 로그인 페이지로 이동합니다.';
                    } else {
                        errorDiv.textContent = '이전 메시지를 불러오는데 실패했습니다.';
                    }
                    chatMessages.appendChild(errorDiv);
                });
        }

        // =========================================
        // 유틸리티 함수들
        // =========================================
        
        function showInactivityMessage() {
            const chatMessages = document.getElementById('chatMessages');
            if (!document.getElementById('inactive-disconnect-msg')) {
                const div = document.createElement('div');
                div.id = 'inactive-disconnect-msg';
                div.style.textAlign = 'center';
                div.style.color = 'gray';
                div.style.padding = '10px';
                div.textContent = '채팅 연결이 중단되었습니다 (비활성 상태). 메시지를 입력하면 다시 연결됩니다.';
                chatMessages.appendChild(div);
                chatMessages.scrollTop = chatMessages.scrollHeight;
            }
        }

        function removeInactivityMessage() {
            const inactiveMsgElement = document.getElementById('inactive-disconnect-msg');
            if (inactiveMsgElement) {
                inactiveMsgElement.remove();
            }
        }

        // =========================================
        // 초기화 및 이벤트 리스너
        // =========================================
        
        window.addEventListener('load', () => {
            loadChatRooms();
            setInterval(loadChatRooms, 30000); // 30초마다 채팅방 목록 새로고침
        });
    </script>
</body>
>>>>>>> origin/jhs
</html>