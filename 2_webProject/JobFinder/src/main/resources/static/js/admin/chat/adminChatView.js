/**
 * 관리자 채팅 뷰 JavaScript
 * WebSocket을 통한 실시간 채팅 기능 구현
 */

// =========================================
// 전역 변수 선언
// =========================================

// WebSocket 연결 객체
let ws = null;
// 현재 선택된 채팅방 ID
let currentRoomId = null;
// 마지막 활동 시간 기록
let lastActivityTime = Date.now();
// 비활성 시간 제한 (1분)
const INACTIVITY_TIMEOUT = 60 * 1000;
// 비활성으로 인한 연결 종료 여부 플래그
let isConnectionExplicitlyClosedByInactivity = false;
// 메시지 큐 (연결이 끊겼을 때 임시 저장)
let messageQueue = [];

// WebSocket 연결 URL 설정
const protocol = window.location.protocol; // 'http:' or 'https:'
const host = window.location.hostname;
const port = window.location.port ? `:${window.location.port}` : '';
const baseUrl = `${protocol}//${host}${port}`;  // http://localhost:9090 or https://...

const wsUrl = `${baseUrl}/ws/chat`;

// =========================================
// 활동 감지 및 WebSocket 연결 관리
// =========================================

/**
 * 사용자 활동 시간 업데이트 및 연결 상태 관리
 * 비활성 상태에서 활동 감지 시 재연결 시도
 */
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

// =========================================
// WebSocket 연결 관리
// =========================================

/**
 * WebSocket 연결 설정
 * @param {string} roomId - 연결할 채팅방 ID
 */
function connectWebSocket(roomId) {
    if (!roomId) {
        console.error('WebSocket 연결을 위한 roomId가 제공되지 않았습니다');
        return;
    }

    // 이미 연결된 경우 중복 연결 방지
    if (ws && (ws.readyState === WebSocket.CONNECTING || ws.readyState === WebSocket.OPEN)) {
        console.log('WebSocket이 이미 연결중이거나 연결된 상태입니다.');
        return;
    }

    const wsUrl = `${baseUrl}/ws/chat`;
    console.log('WebSocket 연결 시도:', wsUrl);
    
    try {
        // SockJS 설정
        const sockjsOptions = {
            transports: ['websocket', 'xhr-streaming', 'xhr-polling'],
            debug: true
        };
        
        ws = new SockJS(wsUrl, null, sockjsOptions);
        
        // WebSocket 이벤트 핸들러 설정
        ws.onopen = function() {
            console.log('WebSocket 연결됨, roomId:', roomId);
            currentRoomId = roomId;
            
            // 입장 메시지 전송
            const enterMessage = {
                type: 'ENTER',
                roomId: roomId,
                sender: '관리자',
                message: ''
            };
            ws.send(JSON.stringify(enterMessage));
            
            // 연결이 끊어진 동안 큐에 쌓인 메시지 전송
            while (messageQueue.length > 0) {
                const msg = messageQueue.shift();
                ws.send(JSON.stringify(msg));
            }
        };
        
        // 메시지 수신 처리
        ws.onmessage = function(event) {
            try {
                console.log('원본 메시지:', event.data);
                const data = JSON.parse(event.data);
                console.log('파싱된 메시지:', data);
                
                if (data.error) {
                    console.error('서버 에러:', data.error);
                    return;
                }
                
                // 현재 채팅방의 메시지만 표시
                if (data.roomId === currentRoomId) {
                    appendMessage(data);
                }
                
                // 새 메시지가 오면 채팅방 목록 갱신
                if (data.type === 'TALK') {
                    loadChatRooms();
                }
            } catch (err) {
                console.error('메시지 처리 중 오류:', err);
            }
        };
        
        // 연결 종료 처리
        ws.onclose = function(event) {
            console.log('WebSocket 연결 종료:', event.code, event.reason);
            ws = null;
            // 비활성으로 인한 종료가 아닌 경우 재연결 시도
            if (!isConnectionExplicitlyClosedByInactivity) {
                setTimeout(() => {
                    if (currentRoomId) {
                        console.log('재연결 시도...');
                        connectWebSocket(currentRoomId);
                    }
                }, 3000);
            }
        };
        
        ws.onerror = function(error) {
            console.error('WebSocket 에러:', error);
        };
        
    } catch (error) {
        console.error('WebSocket 연결 생성 중 오류:', error);
    }
}

// =========================================
// 메시지 처리 함수들
// =========================================

/**
 * 메시지 전송 처리
 * @returns {boolean} 전송 성공 여부
 */
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

/**
 * 메시지를 채팅창에 추가
 * @param {Object} data - 메시지 데이터
 */
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

/**
 * 전체 채팅방 목록 로드
 */
function loadChatRooms() {
    loadMemberRooms();
    loadCompanyRooms();
}

/**
 * 회원 채팅방 목록 로드
 */
function loadMemberRooms() {
    fetchRooms('member', 'memberRoomList');
}

/**
 * 기업 채팅방 목록 로드
 */
function loadCompanyRooms() {
    fetchRooms('company', 'companyRoomList');
}

/**
 * 채팅방 목록 가져오기
 * @param {string} type - 채팅방 타입 (member/company)
 * @param {string} listId - 목록을 표시할 DOM 요소 ID
 */
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

/**
 * 채팅방 선택 처리
 * @param {string} roomId - 채팅방 ID
 * @param {string} roomName - 채팅방 이름
 */
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
    updateRoomTitle(sanitizedRoomName);
    clearMessages();
    loadPreviousMessages(sanitizedRoomId);
    
    // WebSocket 연결 관리
    if (ws) {
        ws.close();
        ws = null;
    }
    connectWebSocket(sanitizedRoomId);
}

/**
 * 채팅방 목록 UI 업데이트
 * @param {Array} rooms - 채팅방 목록 데이터
 * @param {string} listId - 목록을 표시할 DOM 요소 ID
 */
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
            displayName = '기업 ' + displayName;
        } else if (listId === 'memberRoomList' && !displayName.includes('채팅방')) {
            displayName = displayName + '의 채팅방';
        }

        return '<li class="list-group-item" style="cursor: pointer;"' +
            ' data-room-id="' + roomId + '"' +
            ' data-room-name="' + displayName.replace(/'/g, "\\'") + '"' +
            ' onclick="selectRoom(\'' + roomId + '\', \'' + displayName.replace(/'/g, "\\'") + '\')">' +
            '<div class="d-flex justify-content-between align-items-center">' +
            '<span>' + displayName + '</span>' +
            '<span class="badge bg-primary rounded-pill">' +
            (room.sessions ? room.sessions.length : 0) +
            '</span>' +
            '</div>' +
            '</li>';
    }).join('');
}

/**
 * 채팅방 제목 업데이트
 * @param {string} roomName - 채팅방 이름
 */
function updateRoomTitle(roomName) {
    const titleElement = document.getElementById('chatRoomTitle');
    titleElement.textContent = roomName;
}

/**
 * 채팅 메시지 영역 초기화
 */
function clearMessages() {
    const chatMessages = document.getElementById('chatMessages');
    chatMessages.innerHTML = '';
}

/**
 * 이전 메시지 로드
 * @param {string} roomId - 채팅방 ID
 */
function loadPreviousMessages(roomId) {
    if (!roomId || roomId.trim() === '') {
        console.error('채팅방 ID가 없습니다.');
        return;
    }

    const sanitizedRoomId = roomId.toString().trim();
    console.log('이전 메시지 로드 시도:', sanitizedRoomId);

    fetch(`/admin/chat/messages/${sanitizedRoomId}`)
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
            errorDiv.textContent = error.message === '로그인이 필요합니다.' ?
                '로그인이 필요합니다. 잠시 후 로그인 페이지로 이동합니다.' :
                '이전 메시지를 불러오는데 실패했습니다.';
            chatMessages.appendChild(errorDiv);
        });
}

// =========================================
// 유틸리티 함수들
// =========================================

/**
 * 비활성 상태 메시지 표시
 */
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

/**
 * 비활성 상태 메시지 제거
 */
function removeInactivityMessage() {
    const inactiveMsgElement = document.getElementById('inactive-disconnect-msg');
    if (inactiveMsgElement) {
        inactiveMsgElement.remove();
    }
}

// =========================================
// 초기화 및 이벤트 리스너
// =========================================

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', () => {
    // 채팅방 목록 로드
    loadChatRooms();
    
    // 사용자 입력 이벤트에 활동 감지 함수 연결
    document.getElementById('chatInput').addEventListener('keypress', updateLastActivityTime);
    document.getElementById('chatInput').addEventListener('input', updateLastActivityTime);
    document.getElementById('chatForm').addEventListener('submit', (e) => {
        e.preventDefault();
        updateLastActivityTime();
        sendMessage();
    });
}); 