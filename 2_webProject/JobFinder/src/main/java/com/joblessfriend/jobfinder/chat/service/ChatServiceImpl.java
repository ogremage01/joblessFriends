
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

		List<ChatRoomVo> rooms = new ArrayList<>(chatRooms.values());
		log.debug("전체 채팅방 조회 - 총 {}개", rooms.size());
		return rooms;

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

		if (roomId == null || roomId.trim().isEmpty()) {
			log.warn("Invalid roomId: null or empty");
			return null;
		}
		ChatRoomVo room = chatRooms.get(roomId.trim());
		if (room == null) {
			log.debug("Room not found for id: {}", roomId);
		} else {
			log.debug("Room found - id: {}, name: {}", room.getRoomId(), room.getName());
		}
		return room;
	}

	@Override
	public ChatRoomVo createRoom(String userEmail) {
		if (userEmail == null || userEmail.trim().isEmpty()) {
			log.error("Invalid email: null or empty");
			throw new IllegalArgumentException("유효하지 않은 이메일입니다.");
		}

		String cleanEmail = userEmail.replace("의 채팅방", "").trim();
		if (cleanEmail.isEmpty()) {
			log.error("Invalid email after cleaning: empty");
			throw new IllegalArgumentException("유효하지 않은 이메일입니다.");
		}
		
		ChatRoomVo room = findRoomById(cleanEmail);
		if (room != null) {
			log.debug("기존 채팅방 반환 - roomId: {}, name: {}", cleanEmail, room.getName());
			return room;
		}

		ChatRoomVo newRoom = ChatRoomVo.builder()
				.roomId(cleanEmail)
				.name(cleanEmail + "의 채팅방")
				.email(cleanEmail)
				.build();

		chatRooms.put(cleanEmail, newRoom);
		chatMessages.put(cleanEmail, new ArrayList<>());
		log.info("채팅방 생성됨 - roomId: {}, name: {}, email: {}", 
				cleanEmail, newRoom.getName(), cleanEmail);
		return newRoom;
	}

	@Override
	public ChatRoomVo companyCreateRoom(int companyId) {
		String key = String.valueOf(companyId);
		if (companyId <= 0) {
			log.error("Invalid companyId: {}", companyId);
			throw new IllegalArgumentException("유효하지 않은 기업 ID입니다.");
		}

		ChatRoomVo room = findRoomById(key);
		if (room != null) {
			log.debug("기존 기업 채팅방 반환 - roomId: {}, name: {}", key, room.getName());
			return room;
		}

		ChatRoomVo newRoom = ChatRoomVo.builder()
				.roomId(key)
				.name("기업 " + key + "의 채팅방")
				.build();

		chatRooms.put(key, newRoom);
		chatMessages.put(key, new ArrayList<>());
		log.info("기업 채팅방 생성됨 - roomId: {}, name: {}", key, newRoom.getName());
		return newRoom;
	}

	@Override
	public <T> void sendMessage(WebSocketSession session, T message) {

		try {
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(Message)));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}



	@Override
	public List<ChatRoomVo> findMemberRooms() {
		List<ChatRoomVo> rooms = chatRooms.values().stream()
				.filter(room -> room != null && room.getRoomId() != null)
				.filter(room -> !room.getRoomId().matches("\\d+"))
				.filter(room -> room.getName() != null)
				.collect(Collectors.toList());
		
		log.debug("회원 채팅방 조회 결과:");
		rooms.forEach(room -> 
			log.debug("- Room[id={}, name={}, email={}]", 
				room.getRoomId(), room.getName(), room.getEmail()));
		log.info("회원 채팅방 조회 완료 - 총 {}개", rooms.size());
		
		return rooms;
	}

	@Override
	public List<ChatRoomVo> findCompanyRooms() {
		List<ChatRoomVo> rooms = chatRooms.values().stream()
				.filter(room -> room != null && room.getRoomId() != null)
				.filter(room -> room.getRoomId().matches("\\d+"))
				.filter(room -> room.getName() != null)
				.collect(Collectors.toList());
		
		log.debug("기업 채팅방 조회 결과:");
		rooms.forEach(room -> 
			log.debug("- Room[id={}, name={}]", 
				room.getRoomId(), room.getName()));
		log.info("기업 채팅방 조회 완료 - 총 {}개", rooms.size());
		
		return rooms;
	}

	@Override
	public List<ChatMessageVo> findMessagesByRoomId(String roomId) {
		log.debug("채팅 메시지 조회 - roomId: {}", roomId);
		List<ChatMessageVo> messages = chatMessages.getOrDefault(roomId, new ArrayList<>());
		log.debug("조회된 메시지 수: {}", messages.size());
		return messages;
	}
>>>>>>> origin/owr
}
