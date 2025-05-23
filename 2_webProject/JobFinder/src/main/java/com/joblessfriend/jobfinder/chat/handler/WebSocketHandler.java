
package com.joblessfriend.jobfinder.chat.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;
    private final ChatService chatService;

    // 웹소켓 클라이언트로부터 채팅 메시지를 전달받아 채팅 메시지 객체로 변환
    // 전달받은 메시지에 담긴 채팅방 id로 발송 대상 채팅방 정보를 조회
    // 해당 채팅방에 입장해있는 모든 클라이언트들에게 타입에 따른 메시지 발송
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload: {}", payload);
        ChatMessageVo chatMessage = objectMapper.readValue(payload, ChatMessageVo.class);

        // 방 ID가 없는 경우(채팅방 생성 요청 같은 경우)도 고려
        if (chatMessage.getRoomId() == null || chatMessage.getRoomId().isEmpty()) {
            // 예: 채팅방 생성 요청 메시지라면 createRoom 호출
            if (chatMessage.getType() == ChatMessageVo.MessageType.ENTER) {
                ChatRoomVo newRoom = chatService.createRoom(chatMessage.getSender() + "의 방");
                // 클라이언트에 새로 생성된 방 정보 전달
                chatService.sendMessage(session, newRoom);
                return;
            } else {
                log.warn("roomId가 없고 처리할 수 없는 메시지 타입입니다.");
                return;
            }
        }

        ChatRoomVo room = chatService.findRoomById(chatMessage.getRoomId());
        if (room == null) {
            log.warn("존재하지 않는 roomId: {}", chatMessage.getRoomId());
            return;
        }
        room.handleActions(session, chatMessage, chatService);
    }

	

	// 세션 활동 시간을 추적하는 Map
	private final Map<String, Long> sessionLastActivityMap = new ConcurrentHashMap<>();
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private static final long INACTIVITY_TIMEOUT = 60000; // 1분 (밀리초)

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		log.info("WebSocket connection established. Session ID: {}, Attributes: {}", session.getId(), session.getAttributes());
		
		// 사용자 타입 확인
		String userType = (String) session.getAttributes().get("userType");
		if ("admin".equals(userType)) {
			log.info("Admin connected to WebSocket.");
			updateSessionActivity(session);
			startInactivityChecker();
			return;
		}
		
		// 기업회원인 경우
		if ("company".equals(userType)) {
			Object userLogin = session.getAttributes().get("userLogin");
			if (userLogin != null) {
				log.info("Company '{}' connected to WebSocket.", userLogin);
				updateSessionActivity(session);
				startInactivityChecker();
				return;
			}
		}
		
		// 일반 사용자의 경우 userLogin 확인
		if ("member".equals(userType)) {
			MemberVo memberVo = (MemberVo) session.getAttributes().get("userLogin");
			if (memberVo != null) {
				log.info("User '{}' connected to WebSocket.", memberVo.getEmail());
				updateSessionActivity(session);
				startInactivityChecker();
				return;
			}
		}
		
		// 인증되지 않은 사용자 처리
		log.warn("User connected to WebSocket, but no valid login attribute found in session.");
		session.close(CloseStatus.POLICY_VIOLATION.withReason("User not authenticated"));
	}

	// 웹소켓 클라이언트로부터 채팅 메시지를 전달받아 채팅 메시지 객체로 변환
	// 전달받은 메시지에 담긴 채팅방 id로 발송 대상 채팅방 정보를 조회
	// 해당 채팅방에 입장해있는 모든 클라이언트들에게 타입에 따른 메시지 발송

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("payload: {}", payload);
		
		try {
			updateSessionActivity(session);
			ChatMessageVo chatMessage = objectMapper.readValue(payload, ChatMessageVo.class);
			
			// 사용자 타입 확인
			String userType = (String) session.getAttributes().get("userType");
			if ("admin".equals(userType)) {
				// 관리자는 추가 검증 없이 처리
				handleChatMessage(session, chatMessage);
				return;
			}
			
			// 기업회원인 경우
			if ("company".equals(userType)) {
				CompanyVo companyVo = (CompanyVo) session.getAttributes().get("userLogin");
				if (companyVo == null) {
					log.warn("Session does not contain company information for session ID: {}", session.getId());
					session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
						Map.of("error", "세션이 만료되었습니다. 다시 로그인해주세요.")
					)));
					session.close(CloseStatus.POLICY_VIOLATION.withReason("Company not authenticated"));
					return;
				}
				handleChatMessage(session, chatMessage);
				return;
			}
			
			// 일반 사용자의 경우 userLogin 확인
			if ("member".equals(userType)) {
				MemberVo memberVo = (MemberVo) session.getAttributes().get("userLogin");
				if (memberVo == null) {
					log.warn("Session does not contain user information for session ID: {}", session.getId());
					session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
						Map.of("error", "세션이 만료되었습니다. 다시 로그인해주세요.")
					)));
					session.close(CloseStatus.POLICY_VIOLATION.withReason("User not authenticated"));
					return;
				}
				handleChatMessage(session, chatMessage);
				return;
			}
			
			// 인증되지 않은 사용자 처리
			log.warn("Invalid user type: {}", userType);
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
				Map.of("error", "잘못된 사용자 유형입니다.")
			)));
			session.close(CloseStatus.POLICY_VIOLATION.withReason("Invalid user type"));
			
		} catch (Exception e) {
			log.error("메시지 처리 중 오류 발생", e);
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
				Map.of("error", "메시지 처리 중 오류가 발생했습니다.")
			)));
		}
	}

	private void handleChatMessage(WebSocketSession session, ChatMessageVo chatMessage) throws Exception {
		// sender가 비어있으면 세션의 사용자 정보로 설정
		if (chatMessage.getSender() == null || chatMessage.getSender().isEmpty()) {
			String userType = (String) session.getAttributes().get("userType");
			if ("admin".equals(userType)) {
				chatMessage.setSender("관리자");
			} else if ("company".equals(userType)) {
				CompanyVo companyVo = (CompanyVo) session.getAttributes().get("userLogin");
				chatMessage.setSender(companyVo.getCompanyName());
			} else {
				MemberVo memberVo = (MemberVo) session.getAttributes().get("userLogin");
				chatMessage.setSender(memberVo.getEmail());
			}
		}

		// roomId 처리
		if (chatMessage.getRoomId() == null || chatMessage.getRoomId().isEmpty()) {
			String userType = (String) session.getAttributes().get("userType");
			
			if (chatMessage.getType() == ChatMessageVo.MessageType.ENTER) {
				if ("admin".equals(userType)) {
					log.warn("관리자의 ENTER 메시지에 roomId가 없습니다");
					session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
						Map.of("error", "채팅방을 먼저 선택해주세요.")
					)));
					return;
				} else if ("company".equals(userType)) {
					CompanyVo companyVo = (CompanyVo) session.getAttributes().get("userLogin");
					ChatRoomVo newRoom = chatService.companyCreateRoom(companyVo.getCompanyId());
					chatMessage.setRoomId(String.valueOf(companyVo.getCompanyId()));
					chatService.sendMessage(session, newRoom);
					return;
				} else {
					MemberVo memberVo = (MemberVo) session.getAttributes().get("userLogin");
					String roomId = memberVo.getEmail();
					ChatRoomVo newRoom = chatService.createRoom(roomId);
					chatMessage.setRoomId(roomId);
					chatService.sendMessage(session, newRoom);
					return;
				}
			} else {
				if ("admin".equals(userType)) {
					log.warn("관리자의 TALK 메시지에 roomId가 없습니다");
					session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
						Map.of("error", "채팅방을 먼저 선택해주세요.")
					)));
					return;
				} else if ("company".equals(userType)) {
					CompanyVo companyVo = (CompanyVo) session.getAttributes().get("userLogin");
					chatMessage.setRoomId(String.valueOf(companyVo.getCompanyId()));
				} else {
					MemberVo memberVo = (MemberVo) session.getAttributes().get("userLogin");
					chatMessage.setRoomId(memberVo.getEmail());
				}
			}
		}

		// roomId가 설정되었는지 다시 확인
		if (chatMessage.getRoomId() == null || chatMessage.getRoomId().isEmpty()) {
			log.error("메시지에 roomId가 없습니다: {}", chatMessage);
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
				Map.of("error", "채팅방 ID가 없습니다.")
			)));
			return;
		}

		ChatRoomVo room = chatService.findRoomById(chatMessage.getRoomId());
		if (room == null) {
			log.warn("존재하지 않는 roomId: {}, 새로 생성합니다", chatMessage.getRoomId());
			String userType = (String) session.getAttributes().get("userType");
			if ("admin".equals(userType)) {
				log.error("관리자가 존재하지 않는 채팅방에 메시지를 보내려고 했습니다: {}", chatMessage.getRoomId());
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
					Map.of("error", "존재하지 않는 채팅방입니다.")
				)));
				return;
			} else if ("company".equals(userType)) {
				CompanyVo companyVo = (CompanyVo) session.getAttributes().get("userLogin");
				room = chatService.companyCreateRoom(companyVo.getCompanyId());
			} else {
				room = chatService.createRoom(chatMessage.getRoomId());
			}
		}

		log.debug("메시지 처리: roomId={}, type={}, sender={}, message={}", 
			chatMessage.getRoomId(), chatMessage.getType(), chatMessage.getSender(), chatMessage.getMessage());
		room.handleActions(session, chatMessage, chatService);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		// 세션 종료 시 활동 시간 기록 제거
		sessionLastActivityMap.remove(session.getId());
	}

	private void updateSessionActivity(WebSocketSession session) {
		sessionLastActivityMap.put(session.getId(), System.currentTimeMillis());
	}

	private void startInactivityChecker() {
		scheduler.scheduleAtFixedRate(() -> {
			long currentTime = System.currentTimeMillis();
			sessionLastActivityMap.forEach((sessionId, lastActivity) -> {
				if (currentTime - lastActivity > INACTIVITY_TIMEOUT) {
					try {
						WebSocketSession session = findSession(sessionId);
						if (session != null && session.isOpen()) {
							log.info("Closing inactive session: {}", sessionId);
							session.close(CloseStatus.NORMAL.withReason("Client inactive"));
							sessionLastActivityMap.remove(sessionId);
						}
					} catch (Exception e) {
						log.error("Error closing inactive session: {}", sessionId, e);
					}
				}
			});
		}, 30, 30, TimeUnit.SECONDS); // 30초마다 체크
	}

	private WebSocketSession findSession(String sessionId) {
		// ChatRoomVo의 sessions에서 해당 세션 찾기
		return chatService.findAllRoom().stream()
			.flatMap(room -> room.getSessions().stream())
			.filter(session -> session.getId().equals(sessionId))
			.findFirst()
			.orElse(null);
	}

}
