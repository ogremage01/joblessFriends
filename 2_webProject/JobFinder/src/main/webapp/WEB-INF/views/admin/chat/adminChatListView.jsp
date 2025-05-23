<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>



<!DOCTYPE html>
<html>
<head>
    <title>adminMain</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" 
      crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js" 
	integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js"></script>

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
	  fetch('/admin/chat/rooms?type=member')
	    .then(response => {
	      if (!response.ok) {
	        throw new Error('Network response was not ok');
	      }
	      return response.json();
	    })
	    .then(data => {
	      const memberRoomList = document.getElementById('memberRoomList');
	      memberRoomList.innerHTML = ''; // 초기화
	      if(!data || data.length === 0) {
	        memberRoomList.innerHTML = '<li class="list-group-item">채팅방이 없습니다.</li>';
	      } else {
	        data.forEach(room => {
	          const li = document.createElement('li');
	          li.className = 'list-group-item list-group-item-action d-flex justify-content-between align-items-center';
	          const unreadCount = parseInt(room.unreadCount || '0');
	          
	          // email이 있으면 email을 표시, 없으면 roomId를 표시
	          const displayName = room.email || room.roomId || '익명';
	          
	          li.innerHTML = 
	            '<span>' + displayName + '</span>' +
	            (unreadCount ? '<span class="badge bg-primary rounded-pill">' + unreadCount + '</span>' : '');
	          li.dataset.roomId = room.roomId;
	          li.onclick = () => selectRoom(room);
	          memberRoomList.appendChild(li);
	        });
	      }
	    })
	    .catch(err => {
	      console.error('회원 채팅방 목록 로드 실패:', err);
	      const memberRoomList = document.getElementById('memberRoomList');
	      memberRoomList.innerHTML = '<li class="list-group-item text-danger">채팅방 목록을 불러오는데 실패했습니다.</li>';
	    });

	  // 기업 채팅방 목록 호출
	  fetch('/admin/chat/rooms?type=company')
	    .then(response => {
	      if (!response.ok) {
	        throw new Error('Network response was not ok');
	      }
	      return response.json();
	    })
	    .then(data => {
	      const companyRoomList = document.getElementById('companyRoomList');
	      companyRoomList.innerHTML = '';
	      if(!data || data.length === 0) {
	        companyRoomList.innerHTML = '<li class="list-group-item">채팅방이 없습니다.</li>';
	      } else {
	        data.forEach(room => {
	          const li = document.createElement('li');
	          li.className = 'list-group-item list-group-item-action d-flex justify-content-between align-items-center';
	          const unreadCount = parseInt(room.unreadCount || '0');
	          li.innerHTML = 
	            '<span>' + (room.companyName || room.email || '익명') + '</span>' +
	            (unreadCount ? '<span class="badge bg-primary rounded-pill">' + unreadCount + '</span>' : '');
	          li.dataset.roomId = room.roomId;
	          li.onclick = () => selectRoom(room);
	          companyRoomList.appendChild(li);
	        });
	      }
	    })
	    .catch(err => {
	      console.error('기업 채팅방 목록 로드 실패:', err);
	      const companyRoomList = document.getElementById('companyRoomList');
	      companyRoomList.innerHTML = '<li class="list-group-item text-danger">채팅방 목록을 불러오는데 실패했습니다.</li>';
	    });
	}

	// 채팅방 선택 시 처리 함수 (임시)
function selectRoom(room) {
  currentRoomId = room.roomId;
  
  // 채팅방 제목 업데이트
  const title = room.userName || room.companyName || room.email;
  document.getElementById('chatRoomTitle').textContent = title + '님과의 대화';
  
  // 이전 메시지 초기화
  document.getElementById('chatMessages').innerHTML = '';
  
  // 이전 메시지 로드
  loadPreviousMessages(room.roomId);
  
  // WebSocket 연결
  connectWebSocket(room.roomId);
}

function loadPreviousMessages(roomId) {
  if (!roomId) {
    console.error('No roomId provided for loadPreviousMessages');
    return;
  }

  console.log('이전 메시지 로드 시도 - roomId:', roomId);
  fetch(`/admin/chat/messages/${roomId}`)
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(messages => {
      console.log('받은 이전 메시지:', messages);
      const chatMessages = document.getElementById('chatMessages');
      chatMessages.innerHTML = ''; // 메시지 영역 초기화
      
      if (Array.isArray(messages)) {
        messages.forEach(message => appendMessage(message));
      } else {
        console.warn('서버에서 받은 메시지가 배열이 아님:', messages);
      }
    })
    .catch(err => {
      console.error('이전 메시지 로드 실패:', err);
      const chatMessages = document.getElementById('chatMessages');
      chatMessages.innerHTML = '<div class="text-danger">이전 메시지를 불러오는데 실패했습니다.</div>';
    });
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
  const message = data.message || "(메시지 없음)";
  
  // 메시지 컨테이너 스타일 설정
  div.style.marginBottom = '10px';
  div.style.padding = '8px';
  div.style.borderRadius = '5px';
  div.style.maxWidth = '70%';
  
  // 관리자가 보낸 메시지와 받은 메시지를 구분
  if (sender === '관리자') {
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

function connectWebSocket(roomId) {
  if (!roomId) {
    console.error('No roomId provided for WebSocket connection');
    return;
  }

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
      sender: '관리자',
      message: ''
    }));
  };

  ws.onmessage = (event) => {
    try {
      console.log('원본 메시지:', event.data);
      const data = JSON.parse(event.data);
      console.log('파싱된 메시지:', data);
      
      // 현재 선택된 방의 메시지만 표시
      if (data.roomId === currentRoomId && data.message) {
        appendMessage(data);
      }
    } catch (err) {
      console.error('메시지 처리 중 오류:', err);
    }
  };

  ws.onclose = (event) => {
    console.log('WebSocket 연결 종료:', event.code, event.reason);
    // 3초 후 재연결 시도
    setTimeout(() => {
      if (currentRoomId) {
        console.log('WebSocket 재연결 시도...');
        connectWebSocket(currentRoomId);
      }
    }, 3000);
  };

  ws.onerror = (error) => {
    console.error('WebSocket 에러:', error);
  };
}

	// 페이지 로드 시 채팅방 목록 불러오기
	window.addEventListener('load', () => {
	  loadChatRooms();
	  
	  // 주기적으로 채팅방 목록 새로고침 (30초마다)
	  setInterval(loadChatRooms, 30000);
	});
	
	
	let ws = null;

</script>

</html>