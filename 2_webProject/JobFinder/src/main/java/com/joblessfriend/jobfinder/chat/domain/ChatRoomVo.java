package com.joblessfriend.jobfinder.chat.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joblessfriend.jobfinder.chat.service.ChatService;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomVo {

	private String roomId;
	private String name;
	private String email;
	
	@JsonIgnore  // JSON 직렬화에서 제외
	private Set<WebSocketSession> sessions = new HashSet<>();

	@Builder
	public ChatRoomVo(String roomId, String name, String email) {
		this.roomId = roomId;
		this.name = name;
		this.email = email;
	}

	// 채팅방 입장, 채팅에 대한 분기 처리
	// 입장 시에는 session 정보에 클라이언트 session 추가
	// 입장 후 채팅 시에는 채팅방의 모든 session에 메시지 발송
	public void handleActions(WebSocketSession session, ChatMessageVo chatMessage, ChatService chatService) {
		if (chatMessage.getType().equals(ChatMessageVo.MessageType.ENTER)) {
			sessions.add(session);
			chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
		}
		sendMessage(chatMessage, chatService);
	}

	public <T> void sendMessage(T message, ChatService chatService) {
		// 닫힌 세션 제거
		sessions.removeIf(session -> !session.isOpen());
		
		// 열린 세션에만 메시지 전송
		sessions.stream()
			   .filter(WebSocketSession::isOpen)
			   .forEach(session -> chatService.sendMessage(session, message));
	}

}
