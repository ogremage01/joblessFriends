package com.joblessfriend.jobfinder.chat.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joblessfriend.jobfinder.chat.service.ChatService;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 채팅방 정보를 담는 Value Object
 * 채팅방의 기본 정보와 웹소켓 세션 관리를 담당
 */
@Slf4j
@Getter
@Setter
public class ChatRoomVo {

	// 채팅방 고유 식별자
	private String roomId;
	// 채팅방 이름
	private String name;
	// 회원 이메일 (회원 채팅방의 경우)
	private String email;
	// 마지막 메시지 전송 시간 (채팅방 정렬에 사용)
	private LocalDateTime lastMessageTime;
	
	// 채팅방에 연결된 웹소켓 세션들
	// JSON 직렬화에서 제외하여 클라이언트로 전송되지 않도록 함
	@JsonIgnore
	private Set<WebSocketSession> sessions = new HashSet<>();

	/**
	 * 채팅방 생성자
	 * @param roomId 채팅방 ID
	 * @param name 채팅방 이름
	 * @param email 회원 이메일
	 */
	@Builder
	public ChatRoomVo(String roomId, String name, String email) {
		this.roomId = roomId;
		this.name = name;
		this.email = email;
		this.lastMessageTime = LocalDateTime.now();
	}

	/**
	 * 채팅방 메시지 처리 메서드
	 * 메시지 타입에 따라 입장/대화를 처리하고 세션을 관리
	 * @param session 웹소켓 세션
	 * @param chatMessage 채팅 메시지
	 * @param chatService 채팅 서비스
	 */
	public void handleActions(WebSocketSession session, ChatMessageVo chatMessage, ChatService chatService) {
		if (chatMessage.getType().equals(ChatMessageVo.MessageType.ENTER)) {
			// 채팅방 입장 처리
			// 중복 세션 검사 후 새로운 세션만 추가
			boolean isNewSession = sessions.stream()
				.noneMatch(existingSession -> 
					existingSession.getId().equals(session.getId()));
			
			if (isNewSession) {
				sessions.add(session);
				log.info("새로운 세션이 채팅방에 입장했습니다. 방 ID: {}, 세션 ID: {}", roomId, session.getId());
			}
		} else if (chatMessage.getType().equals(ChatMessageVo.MessageType.TALK)) {
			// 일반 대화 메시지 처리
			// 메시지 전송 시간 업데이트
			this.lastMessageTime = LocalDateTime.now();
		}
		
		// 닫힌 세션 제거
		sessions.removeIf(s -> !s.isOpen());
		
		// 채팅방의 모든 세션에 메시지 전송
		sendMessage(chatMessage, chatService);
	}

	/**
	 * 채팅방의 모든 참여자에게 메시지 전송
	 * @param message 전송할 메시지
	 * @param chatService 채팅 서비스
	 */
	public <T> void sendMessage(T message, ChatService chatService) {
		// 닫힌 세션 제거
		sessions.removeIf(session -> !session.isOpen());
		
		// 열린 세션에만 메시지 전송
		sessions.stream()
			.filter(WebSocketSession::isOpen)
			.forEach(session -> {
				try {
					chatService.sendMessage(session, message);
					log.debug("메시지 전송 완료. 세션 ID: {}", session.getId());
				} catch (Exception e) {
					log.error("메시지 전송 실패. 세션 ID: {}, 에러: {}", 
						session.getId(), e.getMessage());
				}
			});
	}

}
