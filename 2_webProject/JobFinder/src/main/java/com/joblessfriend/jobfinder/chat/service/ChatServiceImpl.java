<<<<<<< HEAD
package com.joblessfriend.jobfinder.chat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

	private final ObjectMapper objectMapper;
	private Map<String, ChatRoomVo> chatRooms;

	@PostConstruct
	public void init() {
		// TODO Auto-generated method stub
		chatRooms = new LinkedHashMap<>();

	}

	@Override
	public List<ChatRoomVo> findAllRoom() {
		// TODO Auto-generated method stub
		return new ArrayList<>(chatRooms.values());
	}

	@Override
	public ChatRoomVo findRoomById(String roomId) {
		// TODO Auto-generated method stub
		return chatRooms.get(roomId);
	}

	@Override
	public ChatRoomVo createRoom(String name) {
		// TODO Auto-generated method stub
		String randomId = UUID.randomUUID().toString();
		ChatRoomVo chatRoom = ChatRoomVo.builder().roomId(randomId) // 채팅방 구별을 위한 UUID
				.name(name).build();
		chatRooms.put(randomId, chatRoom);
		log.info("채팅방 생성됨 - roomId: {}, name: {}", randomId, name); // 추가
		System.out.println("채팅방 생성됨 - roomId: " + randomId + ", name: " + name);
		return chatRoom;
	}

	@Override
	public <T> void sendMessage(WebSocketSession session, T Message) {
		// TODO Auto-generated method stub
		try {
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(Message)));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

}
=======
//package com.joblessfriend.jobfinder.chat.service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.TextMessage;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
//import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class ChatServiceImpl implements ChatService {
//
//	private final ObjectMapper objectMapper;
//	private Map<String, ChatRoomVo> chatRooms;
//	private Map<String, List<ChatMessageVo>> chatMessages;
//
//	@PostConstruct
//	private void init() {
//		chatRooms = new LinkedHashMap<>();
//		chatMessages = new LinkedHashMap<>();
//	}
//
//	@Override
//	public List<ChatRoomVo> findAllRoom() {
//		return new ArrayList<>(chatRooms.values());
//	}
//
//	@Override
//	public ChatRoomVo findRoomById(String userEmail) {
//		return chatRooms.get(userEmail);
//	}
//
//	@Override
//	public ChatRoomVo createRoom(String userEmail) {
//		// 이메일에서 채팅방 이름 부분 제거
//		String cleanEmail = userEmail.replace("의 채팅방", "");
//
//		ChatRoomVo room = findRoomById(cleanEmail);
//		if (room != null) return room;
//
//		ChatRoomVo newRoom = ChatRoomVo.builder()
//				.roomId(cleanEmail)
//				.name(cleanEmail)
//				.email(cleanEmail)
//				.build();
//
//		chatRooms.put(cleanEmail, newRoom);
//		chatMessages.put(cleanEmail, new ArrayList<>());
//		log.info("채팅방 생성됨 - roomId: {}, name: {}, email: {}",
//				cleanEmail, newRoom.getName(), cleanEmail);
//		return newRoom;
//	}
//
//	@Override
//	public ChatRoomVo companyCreateRoom(int companyId) {
//		String key = String.valueOf(companyId);
//		ChatRoomVo room = findRoomById(key);
//		if (room != null) return room;
//
//		ChatRoomVo newRoom = ChatRoomVo.builder()
//				.roomId(key)
//				.name("기업 " + key + "의 채팅방")
//				.build();
//
//		chatRooms.put(key, newRoom);
//		chatMessages.put(key, new ArrayList<>());
//		log.info("채팅방 생성됨 - roomId: {}, name: {}", key, newRoom.getName());
//		return newRoom;
//	}
//
//	@Override
//	public <T> void sendMessage(WebSocketSession session, T message) {
//		try {
//			if (session == null || !session.isOpen()) {
//				log.warn("Attempted to send message to null or closed session");
//				return;
//			}
//
//			String messageString = objectMapper.writeValueAsString(message);
//			session.sendMessage(new TextMessage(messageString));
//
//			// 메시지를 저장소에 저장
//			if (message instanceof ChatMessageVo) {
//				ChatMessageVo chatMessage = (ChatMessageVo) message;
//				String roomId = chatMessage.getRoomId();
//
//				if (roomId != null) {
//					chatMessages.computeIfAbsent(roomId, k -> new ArrayList<>()).add(chatMessage);
//					log.debug("메시지 저장됨 - roomId: {}, sender: {}, message: {}",
//							roomId, chatMessage.getSender(), chatMessage.getMessage());
//				}
//			}
//		} catch (IOException e) {
//			log.error("Failed to send message: {}", e.getMessage());
//			try {
//				session.close();
//			} catch (IOException ex) {
//				log.error("Error closing problematic session", ex);
//			}
//		}
//	}
//
//	@Override
//	public List<ChatRoomVo> findMemberRooms() {
//		return chatRooms.values().stream()
//				.filter(room -> !room.getRoomId().matches("\\d+"))
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public List<ChatRoomVo> findCompanyRooms() {
//		return chatRooms.values().stream()
//				.filter(room -> room.getRoomId().matches("\\d+"))
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public List<ChatMessageVo> findMessagesByRoomId(String roomId) {
//		log.debug("채팅 메시지 조회 - roomId: {}", roomId);
//		List<ChatMessageVo> messages = chatMessages.getOrDefault(roomId, new ArrayList<>());
//		log.debug("조회된 메시지 수: {}", messages.size());
//		return messages;
//	}
//}
>>>>>>> origin/jhs
