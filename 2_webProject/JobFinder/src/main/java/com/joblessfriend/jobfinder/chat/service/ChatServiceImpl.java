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
