package com.joblessfriend.jobfinder.chat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.entity.ChatMessageEntity;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;

import com.joblessfriend.jobfinder.chat.event.ChatMessageSavedEvent;
import com.joblessfriend.jobfinder.chat.repository.ChatMessageRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

	private final ObjectMapper objectMapper; // 객체를 JSON 문자열로 변환하기 위한 Jackson ObjectMapper
	private final CompanyService companyService; // 기업 정보를 조회하기 위한 서비스
	private Map<String, ChatRoomVo> chatRooms; // 채팅방 목록 (roomId 기준으로 저장)
	private Map<String, List<ChatMessageVo>> chatMessages; // 채팅 메시지 저장소 (roomId 기준)
	private final ApplicationEventPublisher eventPublisher;
	private final ChatMessageRepository chatMessageRepository;

	@PostConstruct
	private void init() {
		// 서비스 초기화 시점에 Map 객체 초기화
		chatRooms = new LinkedHashMap<>();
		chatMessages = new LinkedHashMap<>();
	}

	// 전체 채팅방 목록을 최근 메시지 시간 기준으로 정렬하여 반환
	@Override
	public List<ChatRoomVo> findAllRoom() {
		return chatRooms.values().stream()
			.sorted(Comparator.comparing(ChatRoomVo::getLastMessageTime).reversed())
			.collect(Collectors.toList());
	}

	// roomId로 채팅방 검색
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

	// 사용자 이메일을 기반으로 채팅방 생성
	@Override
	public ChatRoomVo createRoom(String userEmail) {
		if (userEmail == null || userEmail.trim().isEmpty()) {
			log.error("Invalid email: null or empty");
			throw new IllegalArgumentException("유효하지 않은 이메일입니다.");
		}

		// "의 채팅방" 같은 텍스트 제거 후 이메일 정제
		String cleanEmail = userEmail.replace("의 채팅방", "").trim();
		if (cleanEmail.isEmpty()) {
			log.error("Invalid email after cleaning: empty");
			throw new IllegalArgumentException("유효하지 않은 이메일입니다.");
		}
		
		// 기존에 존재하는 채팅방이면 반환
		ChatRoomVo room = findRoomById(cleanEmail);
		if (room != null) {
			log.debug("기존 채팅방 반환 - roomId: {}, name: {}", cleanEmail, room.getName());
			return room;
		}

		// 새로운 채팅방 생성
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

	// 기업 ID를 기반으로 채팅방 생성
	@Override
	public ChatRoomVo companyCreateRoom(int companyId) {
		String key = String.valueOf(companyId);
		if (companyId <= 0) {
			log.error("Invalid companyId: {}", companyId);
			throw new IllegalArgumentException("유효하지 않은 기업 ID입니다.");
		}

		// 기존 채팅방이 있다면 반환
		ChatRoomVo room = findRoomById(key);
		if (room != null) {
			log.debug("기존 기업 채팅방 반환 - roomId: {}, name: {}", key, room.getName());
			return room;
		}

		// 기업 정보 조회
		CompanyVo company = companyService.companySelectOne(companyId);
		String roomName = company != null ? 
			String.format("[%s] %s", key, company.getCompanyName()) : 
			String.format("[%s] 알 수 없는 기업", key);

		// 새로운 채팅방 생성
		ChatRoomVo newRoom = ChatRoomVo.builder()
				.roomId(key)
				.name(roomName)
				.build();

		chatRooms.put(key, newRoom);
		chatMessages.put(key, new ArrayList<>());
		log.info("기업 채팅방 생성됨 - roomId: {}, name: {}", key, newRoom.getName());
		return newRoom;
	}

	// WebSocket 세션에 메시지를 전송하고, 메시지를 저장소에 기록
	@Override
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            if (session != null && session.isOpen()) {
                String json = objectMapper.writeValueAsString(message);
                session.sendMessage(new TextMessage(json));

                // 후킹 대신 이벤트 발행 방식
                if (message instanceof ChatMessageVo chatMessage) {
                    eventPublisher.publishEvent(new ChatMessageSavedEvent(chatMessage));
                }
            }
        } catch (IOException e) {
            log.error("메시지 전송 실패: {}", e.getMessage());
            try {
                session.close();
            } catch (IOException ex) {
                log.error("세션 종료 실패", ex);
            }
        }
    }

	// 일반 사용자용 채팅방 목록 조회 (숫자가 아닌 roomId 필터링)
	@Override
	public List<ChatRoomVo> findMemberRooms() {
		return chatRooms.values().stream()
				.filter(room -> room != null && room.getRoomId() != null)
				.filter(room -> !room.getRoomId().matches("\\d+")) // 숫자로만 이루어진 roomId는 제외
				.filter(room -> room.getName() != null)
				.sorted(Comparator.comparing(ChatRoomVo::getLastMessageTime).reversed())
				.collect(Collectors.toList());
	}

	// 기업용 채팅방 목록 조회 (숫자로 된 roomId 필터링)
	@Override
	public List<ChatRoomVo> findCompanyRooms() {
		return chatRooms.values().stream()
				.filter(room -> room != null && room.getRoomId() != null)
				.filter(room -> room.getRoomId().matches("\\d+")) // 숫자로만 이루어진 roomId만 포함
				.filter(room -> room.getName() != null)
				.sorted(Comparator.comparing(ChatRoomVo::getLastMessageTime).reversed())
				.collect(Collectors.toList());
	}

	// 특정 채팅방의 메시지 목록 반환
	@Override
	public List<ChatMessageVo> findMessagesByRoomId(String roomId) {
	    if (roomId == null || roomId.trim().isEmpty()) {
	        log.warn("Invalid roomId for message lookup: null or empty");
	        return new ArrayList<>();
	    }

	    String cleanRoomId = roomId.trim();

	    // DB에서 직접 조회
	    List<ChatMessageEntity> entities = chatMessageRepository.findByRoomId(cleanRoomId);
	    if (entities == null || entities.isEmpty()) {
	        log.debug("No messages found in DB for roomId: {}", cleanRoomId);
	        return new ArrayList<>();
	    }

	    // Entity → VO 변환
	    List<ChatMessageVo> messages = entities.stream()
	        .map(entity -> new ChatMessageVo(null, entity.getRoomId(), entity.getSender(), entity.getMessage(), entity.getSendTime()))
	        .collect(Collectors.toList());

	    log.debug("Found {} messages from DB for roomId: {}", messages.size(), cleanRoomId);
	    return messages;
	}

}
