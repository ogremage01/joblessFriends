<!-- 관리자 로그인 여부를 묻는 자바구문이 들어가야 할 부분 -->
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

</html>