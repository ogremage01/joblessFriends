package com.joblessfriend.jobfinder.chat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

	private final ObjectMapper objectMapper;
	private final CompanyService companyService;
	private Map<String, ChatRoomVo> chatRooms;
	private Map<String, List<ChatMessageVo>> chatMessages;

	@PostConstruct
	private void init() {
		chatRooms = new LinkedHashMap<>();
		chatMessages = new LinkedHashMap<>();
	}

	@Override
	public List<ChatRoomVo> findAllRoom() {
		List<ChatRoomVo> rooms = new ArrayList<>(chatRooms.values());
		log.debug("전체 채팅방 조회 - 총 {}개", rooms.size());
		return rooms;
	}

	@Override
	public ChatRoomVo findRoomById(String roomId) {
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

		// 회사 정보 조회
		CompanyVo company = companyService.companySelectOne(companyId);
		String roomName = company != null ? 
			String.format("[%s] %s", key, company.getCompanyName()) : 
			String.format("[%s] 알 수 없는 기업", key);

		ChatRoomVo newRoom = ChatRoomVo.builder()
				.roomId(key)
				.name(roomName)
				.build();

		chatRooms.put(key, newRoom);
		chatMessages.put(key, new ArrayList<>());
		log.info("기업 채팅방 생성됨 - roomId: {}, name: {}", key, newRoom.getName());
		return newRoom;
	}

	@Override
	public <T> void sendMessage(WebSocketSession session, T message) {
		try {
			if (session == null || !session.isOpen()) {
				log.warn("Attempted to send message to null or closed session");
				return;
			}

			String messageString = objectMapper.writeValueAsString(message);
			session.sendMessage(new TextMessage(messageString));
			
			// 메시지를 저장소에 저장
			if (message instanceof ChatMessageVo) {
				ChatMessageVo chatMessage = (ChatMessageVo) message;
				String roomId = chatMessage.getRoomId();
				
				if (roomId != null) {
					chatMessages.computeIfAbsent(roomId, k -> new ArrayList<>()).add(chatMessage);
					log.debug("메시지 저장됨 - roomId: {}, sender: {}, message: {}", 
							roomId, chatMessage.getSender(), chatMessage.getMessage());
				}
			}
		} catch (IOException e) {
			log.error("Failed to send message: {}", e.getMessage());
			try {
				session.close();
			} catch (IOException ex) {
				log.error("Error closing problematic session", ex);
			}
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
			log.debug("- Room[id={}, name={}]", room.getRoomId(), room.getName()));
		log.info("기업 채팅방 조회 완료 - 총 {}개", rooms.size());
		
		return rooms;
	}

	@Override
	public List<ChatMessageVo> findMessagesByRoomId(String roomId) {
		if (roomId == null || roomId.trim().isEmpty()) {
			log.warn("Invalid roomId for message lookup: null or empty");
			return new ArrayList<>();
		}

		String cleanRoomId = roomId.trim();
		List<ChatMessageVo> messages = chatMessages.get(cleanRoomId);
		
		if (messages == null) {
			log.debug("No messages found for roomId: {}", cleanRoomId);
			return new ArrayList<>();
		}
		
		log.debug("Found {} messages for roomId: {}", messages.size(), cleanRoomId);
		return new ArrayList<>(messages);
	}
}
