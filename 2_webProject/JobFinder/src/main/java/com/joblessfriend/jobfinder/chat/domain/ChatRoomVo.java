<<<<<<< HEAD
package com.joblessfriend.jobfinder.chat.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import com.joblessfriend.jobfinder.chat.service.ChatService;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomVo {

	private String roomId;
	private String name;
	private Set<WebSocketSession> sessions = new HashSet<>();

	@Builder
	    public ChatRoomVo(String roomId, String name) {
	       this.roomId = roomId;
	       this.name = name;
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
		sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
	}

}
=======
//package com.joblessfriend.jobfinder.chat.domain;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import org.springframework.web.socket.WebSocketSession;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.joblessfriend.jobfinder.chat.service.ChatService;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Getter
//@Setter
//public class ChatRoomVo {
//
//	private String roomId;
//	private String name;
//	private String email;
//
//	@JsonIgnore  // JSON 직렬화에서 제외
//	private Set<WebSocketSession> sessions = new HashSet<>();
//
//	@Builder
//	public ChatRoomVo(String roomId, String name, String email) {
//		this.roomId = roomId;
//		this.name = name;
//		this.email = email;
//	}
//
//	// 채팅방 입장, 채팅에 대한 분기 처리
//	public void handleActions(WebSocketSession session, ChatMessageVo chatMessage, ChatService chatService) {
//		if (chatMessage.getType().equals(ChatMessageVo.MessageType.ENTER)) {
//			// 이미 존재하는 세션인지 확인
//			boolean isNewSession = sessions.stream()
//				.noneMatch(existingSession ->
//					existingSession.getId().equals(session.getId()));
//
//			if (isNewSession) {
//				sessions.add(session);
//				log.info("New session added to room {}: {}", roomId, session.getId());
//			}
//		}
//
//		// 메시지 전송 전에 닫힌 세션 제거
//		sessions.removeIf(s -> !s.isOpen());
//
//		// 채팅방의 모든 세션에 메시지 전송
//		sendMessage(chatMessage, chatService);
//	}
//
//	public <T> void sendMessage(T message, ChatService chatService) {
//		// 닫힌 세션 제거
//		sessions.removeIf(session -> !session.isOpen());
//
//		// 열린 세션에만 메시지 전송
//		sessions.stream()
//			.filter(WebSocketSession::isOpen)
//			.forEach(session -> {
//				try {
//					chatService.sendMessage(session, message);
//					log.debug("Message sent to session {}", session.getId());
//				} catch (Exception e) {
//					log.error("Failed to send message to session {}: {}",
//						session.getId(), e.getMessage());
//				}
//			});
//	}
//
//}
>>>>>>> origin/jhs
