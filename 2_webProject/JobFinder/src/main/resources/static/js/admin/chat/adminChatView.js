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

// 마지막 사용자 활동 시간 (마지막 키 입력 등)
let lastActivityTime = Date.now();

// 비활성 상태 제한 시간 (1분)
const INACTIVITY_TIMEOUT = 60 * 1000;

// 사용자가 비활성 상태로 인해 연결 종료된 경우 true
let isConnectionExplicitlyClosedByInactivity = false;

// WebSocket이 끊긴 동안 전송 실패한 메시지를 저장할 큐
let messageQueue = [];

// 채팅방별 안 읽은 메시지 수 저장
const unreadCounts = {};

// WebSocket 연결 URL 구성 (SockJS는 HTTP 프로토콜 사용)
const protocol = window.location.protocol; // http: 또는 https:
const host = window.location.hostname;
const port = window.location.port ? `:${window.location.port}` : '';
const baseUrl = `${protocol}//${host}${port}`;
const wsUrl = `${baseUrl}/ws/chat`;

// =========================================
// 사용자 활동 감지 및 WebSocket 자동 재연결
// =========================================

/**
 * 마지막 활동 시간을 갱신하고
 * 비활성 상태로 끊긴 경우 자동으로 재연결 시도
 */
function updateLastActivityTime() {
	lastActivityTime = Date.now();

	if (isConnectionExplicitlyClosedByInactivity) {
		console.log("사용자 활동 감지됨, 비활성 상태 플래그 초기화");
		isConnectionExplicitlyClosedByInactivity = false;
		if (currentRoomId) {
			connectWebSocket(currentRoomId); // 재연결 시도
		}
	}
}

// =========================================
// WebSocket 연결 및 관리
// =========================================

/**
 * 주어진 채팅방 ID로 WebSocket 연결 시도
 * @param {string} roomId - 연결할 채팅방 ID
 */
function connectWebSocket(roomId) {
	if (!roomId) {
		console.error('WebSocket 연결을 위한 roomId가 제공되지 않았습니다');
		return;
	}

	// 중복 연결 방지
	if (ws && (ws.readyState === WebSocket.CONNECTING || ws.readyState === WebSocket.OPEN)) {
		console.log('WebSocket이 이미 연결중이거나 연결된 상태입니다.');
		return;
	}

	console.log('WebSocket 연결 시도:', wsUrl);

	try {
		// SockJS를 통한 WebSocket 설정
		const sockjsOptions = {
			transports: ['websocket', 'xhr-streaming', 'xhr-polling'],
			debug: true
		};

		ws = new SockJS(wsUrl, null, sockjsOptions);

		// 연결 성공 시
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

			// 연결 끊긴 동안 저장된 메시지 전송
			while (messageQueue.length > 0) {
				const msg = messageQueue.shift();
				ws.send(JSON.stringify(msg));
			}
		};

		// 수신 메시지 처리
		ws.onmessage = function(event) {
			try {
				const data = JSON.parse(event.data);
				console.log("[ws.onmessage] Received data:", data);

				if (data.error) {
					console.error('[ws.onmessage] Server error:', data.error);
					return;
				}

				if (data.roomId === currentRoomId) {
					console.log("[ws.onmessage] Message for current room. Appending.");
					appendMessage(data);
				} else {
					console.log("[ws.onmessage] Message for different room. Updating unread count.");
					// 현재 채팅방이 아닌 경우 -> 안읽은 메시지로 간주
					if (!unreadCounts[data.roomId]) {
						unreadCounts[data.roomId] = 0;
					}
					unreadCounts[data.roomId]++;
					console.log(`[ws.onmessage] Unread counts for room ${data.roomId}: ${unreadCounts[data.roomId]}`);
					// 채팅방 목록의 뱃지 즉시 업데이트
					updateRoomBadge(data.roomId, unreadCounts[data.roomId]);
					// 해당 채팅방을 목록의 맨 위로 이동
					moveRoomToTop(data.roomId);
				}
			} catch (err) {
				console.error('메시지 처리 중 오류:', err);
			}
		};

		// 연결 종료 시
		ws.onclose = function(event) {
			console.log('WebSocket 연결 종료:', event.code, event.reason);
			ws = null;

			// 비활성 상태로 끊긴 것이 아니라면 재연결 시도
			if (!isConnectionExplicitlyClosedByInactivity) {
				setTimeout(() => {
					if (currentRoomId) {
						console.log('재연결 시도...');
						connectWebSocket(currentRoomId);
					}
				}, 3000);
			}
		};

		// 연결 오류 처리
		ws.onerror = function(error) {
			console.error('WebSocket 에러:', error);
		};

	} catch (error) {
		console.error('WebSocket 연결 오류:', error);
	}
}

// =========================================
// 메시지 처리 관련
// =========================================

/**
 * 메시지 전송 버튼 또는 Enter 키 입력 시 실행
 */
function sendMessage() {
	const input = document.getElementById('chatInput');
	const msg = input.value.trim();
	if (!msg) return false;
	if (msg.length > 500) {
		alert('메시지는 500자 이하로 입력해주세요.');
        return false;
    }

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
		console.log('WebSocket 연결 안됨. 메시지를 큐에 저장:', messageObj);
		messageQueue.push(messageObj);

		// 비활성으로 끊긴 게 아니면 재연결 시도
		if (!isConnectionExplicitlyClosedByInactivity) {
			connectWebSocket(currentRoomId);
		}
	}

	input.value = '';
	return false;
}

/**
 * 채팅창에 메시지 추가
 */
function appendMessage(data) {
	if (!data || !data.message) return;

	const chatMessages = document.getElementById('chatMessages');
	const div = document.createElement('div');
	let sender = data.sender || "익명";

	sender = (sender === '관리자' ? '관리자' : '사용자');


	div.className = 'message ' + (sender === '관리자' ? 'message-admin' : 'message-user');
	div.innerHTML =
		'<div class="message-sender">' + sender + '</div>' +
		'<div>' + data.message + '</div>';

	chatMessages.appendChild(div);
	chatMessages.scrollTop = chatMessages.scrollHeight;
}

// =========================================
// 채팅방 목록 로딩 및 관리
// =========================================

/**
 * 회원 및 기업 채팅방 목록 로드
 */
function loadChatRooms() {
	fetch('/admin/chat/rooms')
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
			if (typeof data === 'string') throw new Error(data);
			updateRoomList(data, 'allRoomList');
		})
		.catch(err => {
			console.error('채팅방 목록 로드 실패:', err);
			const roomList = document.getElementById('allRoomList');
			roomList.innerHTML = '<li class="list-group-item text-danger">' +
				(err.message === '로그인이 필요합니다.' ?
					'로그인이 필요합니다. 잠시 후 로그인 페이지로 이동합니다.' :
					'채팅방 목록을 불러오는데 실패했습니다.') +
				'</li>';
		});
}

/**
 * 채팅방 선택 시 처리 (UI 변경 및 이전 메시지 로드)
 */
function selectRoom(roomId, roomName) {
	if (!roomId || roomId.trim() === '') {
		alert('유효하지 않은 채팅방입니다.');
		return;
	}
	console.log(`[selectRoom] Selecting room: ${roomId}, Name: ${roomName}`);

	const sanitizedRoomId = roomId.trim();
	const sanitizedRoomName = (roomName || roomId).trim();

	if (currentRoomId === sanitizedRoomId) {
		console.log("[selectRoom] Room already selected.");
		return;
	}

	currentRoomId = sanitizedRoomId;
	updateRoomTitle(sanitizedRoomName);
	clearMessages();
	loadPreviousMessages(sanitizedRoomId);

	if (ws) {
		ws.close();
		ws = null;
	}

	connectWebSocket(sanitizedRoomId);

	// 채팅방 전환 시 해당 방의 안 읽은 메시지 수 초기화 및 뱃지 업데이트
	console.log(`[selectRoom] Resetting unread count for room ${roomId} to 0.`);
	unreadCounts[roomId] = 0;
	updateRoomBadge(roomId, 0); // 뱃지 즉시 업데이트
}

/**
 * 채팅방 목록 DOM에 반영
 */
function updateRoomList(rooms, listId) {
	console.log(`[updateRoomList] Updating room list for ${listId} with rooms:`, rooms);
	const roomList = document.getElementById(listId);
	if (!rooms || !Array.isArray(rooms) || rooms.length === 0) {
		roomList.innerHTML = '<li class="list-group-item">채팅방이 없습니다.</li>';
		console.log(`[updateRoomList] No rooms to display for ${listId}.`);
		return;
	}

	const validRooms = rooms.filter(room => room && room.roomId);

	if (validRooms.length === 0) {
		roomList.innerHTML = '<li class="list-group-item">유효한 채팅방이 없습니다.</li>';
		console.log(`[updateRoomList] No valid rooms to display for ${listId}.`);
		return;
	}

	roomList.innerHTML = validRooms.map(room => {
		const roomId = room.roomId.trim();
		let displayName = room.name ? room.name.trim() : roomId;

		// 서버에서 제공하는 unreadCount 사용, 없으면 0으로 초기화
		const unread = room.unreadCount !== undefined ? room.unreadCount : 0;
		unreadCounts[roomId] = unread; // 전역 unreadCounts도 서버 값으로 동기화

		console.log(`[updateRoomList] Room: ${roomId}, Server unreadCount: ${room.unreadCount}, Applied unread: ${unread}`);
		const unreadBadge = unread > 0
			? `<span class="badge bg-danger rounded-pill ms-2 unread-badge" data-room-id="${roomId}">${unread}</span>`
			: `<span class="badge bg-danger rounded-pill ms-2 unread-badge" data-room-id="${roomId}" style="display: none;">${unread}</span>`;

		return `<li class="list-group-item" style="cursor: pointer;"
	            data-room-id="${roomId}"
	            data-room-name="${displayName.replace(/'/g, "\\'")}"
	            onclick="selectRoom('${roomId}', '${displayName.replace(/'/g, "\\'")}')">
	            <div class="d-flex justify-content-between align-items-center">
	                <span class="displayName">${displayName}</span>
	                <span>
	                    ${unreadBadge}
	                </span>
	            </div>
	        </li>`;
	}).join('');
}

/**
 * 채팅방 제목 업데이트
 */
function updateRoomTitle(roomName) {
	const titleElement = document.getElementById('chatRoomTitle');
	titleElement.textContent = roomName;
}

/**
 * 채팅 메시지 영역 초기화
 */
function clearMessages() {
	document.getElementById('chatMessages').innerHTML = '';
}

/**
 * DB에서 이전 채팅 메시지 로드
 */
function loadPreviousMessages(roomId) {
	if (!roomId || roomId.trim() === '') return;

	fetch(`/admin/chat/messages/${roomId.trim()}`)
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
			if (Array.isArray(data)) {
				// 메시지를 timestamp 기준으로 오름차순 정렬 (오래된 메시지가 먼저)
				data.sort((a, b) => {
					if (!a.timestamp || !b.timestamp) return 0;
					return new Date(a.timestamp) - new Date(b.timestamp);
				});
				console.log("[loadPreviousMessages] Sorted messages:", data);
				data.forEach(message => appendMessage(message));
			}
		})
		.catch(error => {
			console.error('이전 메시지 로드 실패:', error);
			const chatMessages = document.getElementById('chatMessages');
			const div = document.createElement('div');
			div.className = 'text-center text-danger';
			div.textContent = error.message === '로그인이 필요합니다.' ?
				'로그인이 필요합니다. 잠시 후 로그인 페이지로 이동합니다.' :
				'이전 메시지를 불러오는데 실패했습니다.';
			chatMessages.appendChild(div);
		});
}

/**
 * 특정 채팅방의 안 읽은 메시지 뱃지 업데이트
 * @param {string} roomId - 업데이트할 채팅방 ID
 * @param {number} count - 안 읽은 메시지 수
 */
function updateRoomBadge(roomId, count) {
    console.log(`[updateRoomBadge] Attempting to update badge for room ${roomId} to count ${count}.`);
    const badge = document.querySelector(`.unread-badge[data-room-id="${roomId}"]`);
    if (badge) {
        badge.textContent = count;
        if (count > 0) {
            badge.style.display = '';
            console.log(`[updateRoomBadge] Badge for room ${roomId} updated to ${count} and shown.`);
        } else {
            badge.style.display = 'none';
            console.log(`[updateRoomBadge] Badge for room ${roomId} updated to ${count} and hidden.`);
        }
    } else {
        console.warn(`[updateRoomBadge] Badge for room ${roomId} not found. Current unreadCounts:`, unreadCounts);
    }
}

/**
 * 특정 채팅방을 해당 목록의 최상단으로 이동
 * @param {string} roomId - 이동할 채팅방 ID
 */
function moveRoomToTop(roomId) {
    const roomElement = document.querySelector(`li[data-room-id="${roomId}"]`);
    if (roomElement) {
        const roomListElement = roomElement.parentElement;
        if (roomListElement) {
            roomListElement.prepend(roomElement);
        }
    } else {
        console.warn(`Room element for roomId ${roomId} not found. Cannot move to top.`);
    }
}

// =========================================
// 기타 유틸리티
// =========================================

/**
 * 비활성 상태 안내 메시지 표시
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
 * 비활성 안내 메시지 제거
 */
function removeInactivityMessage() {
	const el = document.getElementById('inactive-disconnect-msg');
	if (el) el.remove();
}

// =========================================
// 디버깅 및 상태 확인 함수들
// =========================================

/**
 * WebSocket 연결 상태 확인 (디버깅용)
 */
function checkWebSocketStatus() {
	console.log('=== WebSocket 연결 상태 ===');
	console.log('채팅 WebSocket:', ws ? ws.readyState : 'null');
	console.log('현재 선택된 채팅방:', currentRoomId);
	console.log('안읽은 메시지 카운트:', unreadCounts);
	
	if (ws) {
		const states = ['CONNECTING', 'OPEN', 'CLOSING', 'CLOSED'];
		console.log('채팅 WebSocket 상태:', states[ws.readyState] || 'UNKNOWN');
	}
	console.log('==========================');
}

// 전역 함수로 등록 (브라우저 콘솔에서 호출 가능)
window.checkWebSocketStatus = checkWebSocketStatus;

// =========================================
// 초기 실행 설정
// =========================================

document.addEventListener('DOMContentLoaded', () => {
	// 채팅방 목록 초기 로드
	loadChatRooms();

	// 사용자 입력 감지 -> 활동 시간 갱신 및 Enter 키 처리
	document.getElementById('chatInput').addEventListener('keypress', function(e) {
		updateLastActivityTime();
		if (e.key === 'Enter') {
			e.preventDefault();
			sendMessage();
		}
	});
	document.getElementById('chatInput').addEventListener('input', updateLastActivityTime);

	// 채팅 메시지 전송 폼 이벤트
	document.getElementById('chatForm').addEventListener('submit', (e) => {
		e.preventDefault();
		updateLastActivityTime();
		sendMessage();
	});
});

// 페이지 언로드 시 정리
window.addEventListener('beforeunload', () => {
	if (ws) {
		ws.close();
	}
});



