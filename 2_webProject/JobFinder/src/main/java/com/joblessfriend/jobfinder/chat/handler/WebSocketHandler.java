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
			log.warn("존재하지 않는 roomId: {}, 새로 생성합니다", chatMessage.getRoomId());
			room = chatService.createRoom(chatMessage.getRoomId());
		}
		room.handleActions(session, chatMessage, chatService);
	}

}
