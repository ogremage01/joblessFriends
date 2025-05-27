package com.joblessfriend.jobfinder.chat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.entity.ChatMessageEntity;
import com.joblessfriend.jobfinder.chat.entity.ChatRoomEntity;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;

import com.joblessfriend.jobfinder.chat.repository.ChatMessageRepository;
import com.joblessfriend.jobfinder.chat.repository.ChatRoomRepository;
import com.joblessfriend.jobfinder.chat.repository.UserChatRoomLastReadRepository;
import com.joblessfriend.jobfinder.chat.domain.UserChatRoomLastRead;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link ChatService} 인터페이스의 구현 클래스입니다.
 * 실제 채팅 관련 비즈니스 로직을 수행하며, 데이터 영속성 처리, 메시지 발송 등을 담당합니다.
 * 채팅방 정보는 데이터베이스를 통해 관리됩니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

	private final ObjectMapper objectMapper;
	private final CompanyService companyService;
	private final ApplicationEventPublisher eventPublisher;
	private final ChatMessageRepository chatMessageRepository;
	private final UserChatRoomLastReadRepository userChatRoomLastReadRepository;
	private final ChatRoomRepository chatRoomRepository;

	// 활성 채팅방의 런타임 정보(특히 웹소켓 세션)를 관리하기 위한 맵을 다시 도입합니다.
	// Key: roomId, Value: ChatRoomVo (세션 정보 포함)
	private final Map<String, ChatRoomVo> activeChatRooms = new ConcurrentHashMap<>();

	/**
	 * 서비스 빈(Bean)이 생성되고 의존성 주입이 완료된 후 실행되는 초기화 메소드입니다.
	 * (인메모리 저장소 초기화 로직은 DB 기반으로 변경되면서 제거됨)
	 */
	@PostConstruct
	private void init() {
		log.info("[ChatServiceImpl] 초기화 완료. (DB 기반 채팅방 정보 + 런타임 세션 관리)");
		// 필요하다면 서버 시작 시 DB에서 기존 채팅방 정보를 읽어 activeChatRooms를 일부 채울 수 있으나,
		// 일반적으로는 사용자가 채팅방에 접근하거나 메시지를 보낼 때 동적으로 로드/생성됩니다.
	}

	/**
	 * ChatRoomEntity를 ChatRoomVo로 변환합니다.
	 * @param entity 변환할 ChatRoomEntity 객체
	 * @return 변환된 ChatRoomVo 객체, entity가 null이면 null 반환
	 */
	private ChatRoomVo convertToVo(ChatRoomEntity entity) {
	    if (entity == null) return null;
	    ChatRoomVo vo = ChatRoomVo.builder()
	            .roomId(entity.getRoomId())
	            .name(entity.getName())
	            .email(entity.getEmail())
	            .build();
	    vo.setLastMessageTime(entity.getLastMessageTime());
        // ChatServiceImpl에 주입된 eventPublisher를 ChatRoomVo에 설정
        vo.setEventPublisher(this.eventPublisher); 
	    return vo;
	}

    /**
     * ChatRoomVo를 ChatRoomEntity로 변환합니다.
     * @param vo 변환할 ChatRoomVo 객체
     * @return 변환된 ChatRoomEntity 객체, vo가 null이면 null 반환
     */
    private ChatRoomEntity convertToEntity(ChatRoomVo vo) {
        if (vo == null) return null;
        return ChatRoomEntity.builder()
                .roomId(vo.getRoomId())
                .name(vo.getName())
                .email(vo.getEmail())
                .lastMessageTime(vo.getLastMessageTime() != null ? vo.getLastMessageTime() : new Date())
                .build();
    }

	@Override
	@Transactional(readOnly = true)
	public List<ChatRoomVo> findAllRoom(String adminId) {
        List<ChatRoomEntity> roomEntities = chatRoomRepository.findAllByOrderByLastMessageTimeDesc();
        log.debug("[ChatService] findAllRoom DB 조회. 관리자 ID: {}, 조회된 방 개수: {}", adminId, roomEntities.size());

        List<ChatRoomVo> roomVos = roomEntities.stream()
            .map(this::convertToVo)
            .collect(Collectors.toList());

        for (ChatRoomVo room : roomVos) {
            Optional<UserChatRoomLastRead> lastReadOpt = userChatRoomLastReadRepository.findByUserIdAndRoomId(adminId, room.getRoomId());
            Date lastReadTime = lastReadOpt.map(UserChatRoomLastRead::getLastReadTimestamp).orElse(new Date(0));
            long unreadMessages = chatMessageRepository.countByRoomIdAndSenderNotAndSendTimeAfter(room.getRoomId(), adminId, lastReadTime);
            room.setUnreadCount((int) unreadMessages);

            // 채팅방 이름 가공
            if (room.getEmail() != null) {
                // 회원 채팅방
                room.setName("회원 " + room.getEmail());
            } else {
                // 기업 채팅방
                try {
                    int companyId = Integer.parseInt(room.getRoomId());
                    CompanyVo company = companyService.companySelectOne(companyId);
                    if (company != null && company.getCompanyName() != null && !company.getCompanyName().isEmpty()) {
                        room.setName("기업 " + "[" + room.getRoomId() + "] " + company.getCompanyName());
                    } else {
                        room.setName("기업 " + room.getRoomId());
                    }
                } catch (NumberFormatException e) {
                    room.setName("기업 " + room.getRoomId());
                }
            }
        }
        return roomVos;
	}

	@Override
    @Transactional // 엔티티 조회 후 activeChatRooms에 추가할 수 있으므로 readOnly=false
	public ChatRoomVo findRoomById(String roomId) {
		if (roomId == null || roomId.trim().isEmpty()) {
			log.warn("[ChatService] findRoomById 호출 시 roomId가 null이거나 비어있습니다.");
			return null;
		}
		String trimmedRoomId = roomId.trim();

        // 1. activeChatRooms에서 먼저 찾아본다 (런타임 세션 정보 포함 가능성)
        ChatRoomVo roomVo = activeChatRooms.get(trimmedRoomId);
        if (roomVo != null) {
            log.debug("[ChatService] ID '{}' 채팅방을 activeChatRooms에서 찾음: 이름 '{}'", trimmedRoomId, roomVo.getName());
            // eventPublisher는 이미 설정되어 있을 것임 (activeChatRooms에 추가될 때 설정)
            return roomVo;
        }

        // 2. activeChatRooms에 없으면 DB에서 조회
		Optional<ChatRoomEntity> roomEntityOpt = chatRoomRepository.findById(trimmedRoomId);
		if (roomEntityOpt.isEmpty()) {
			log.debug("[ChatService] ID '{}'에 해당하는 채팅방을 DB에서 찾을 수 없습니다.", trimmedRoomId);
			return null; // DB에도 없으면 null 반환
		}
        
        // 3. DB에 있으면 ChatRoomVo로 변환하고 activeChatRooms에 추가
        ChatRoomEntity entity = roomEntityOpt.get();
        ChatRoomVo newRoomVoFromDb = convertToVo(entity); // convertToVo 내부에서 eventPublisher 설정됨
        if (newRoomVoFromDb != null) {
            // 중요: 새로운 WebSocket 세션 Set을 할당하여 ChatRoomVo가 세션들을 관리할 수 있도록 함
            newRoomVoFromDb.setSessions(new java.util.HashSet<>()); 
            activeChatRooms.put(trimmedRoomId, newRoomVoFromDb); // 맵에 추가
            log.debug("[ChatService] ID '{}' 채팅방을 DB에서 찾아 activeChatRooms에 추가. 이름: '{}'", trimmedRoomId, newRoomVoFromDb.getName());
            return newRoomVoFromDb;
        }
		return null; // 변환 실패 등 예외적 상황
	}

    // createRoom, companyCreateRoom은 DB에 저장 후, activeChatRooms에도 추가하는 로직으로 변경합니다.
	@Override
	@Transactional
	public ChatRoomVo createRoom(String userIdentifier) {
		if (userIdentifier == null || userIdentifier.trim().isEmpty()) {
			log.error("[ChatService] createRoom 호출 시 userIdentifier가 null이거나 비어있습니다.");
			throw new IllegalArgumentException("사용자 식별자(이름 또는 이메일)는 비워둘 수 없습니다.");
		}
		String cleanRoomId = userIdentifier.replace("의 채팅방", "").trim();
		if (cleanRoomId.isEmpty()) {
			log.error("[ChatService] userIdentifier '{}' 정리 후 roomId가 비어있습니다.", userIdentifier);
			throw new IllegalArgumentException("정리된 사용자 식별자가 비어있어 채팅방을 생성할 수 없습니다.");
		}
		
        // DB와 activeChatRooms 모두 확인
        ChatRoomVo existingRoom = findRoomById(cleanRoomId); // 이 메소드는 activeChatRooms도 확인
        if (existingRoom != null) {
            log.info("[ChatService] ID '{}'에 대한 기존 채팅방(active/DB) 반환. 이름: {}", cleanRoomId, existingRoom.getName());
			return existingRoom;
        }

		ChatRoomEntity newRoomEntity = ChatRoomEntity.builder()
				.roomId(cleanRoomId)
				.name(cleanRoomId + "의 채팅방")
				.email(cleanRoomId)
				.lastMessageTime(new Date())
				.build();

		ChatRoomEntity savedEntity = chatRoomRepository.save(newRoomEntity);
        ChatRoomVo newRoomVo = convertToVo(savedEntity); // convertToVo 내부에서 eventPublisher 설정됨
        if (newRoomVo != null) {
            newRoomVo.setSessions(new java.util.HashSet<>()); // 새 세션 Set 할당
            activeChatRooms.put(cleanRoomId, newRoomVo); // activeChatRooms에도 추가
            log.info("[ChatService] 새로운 회원 채팅방 DB 저장 및 activeRooms 추가 완료. ID: {}", cleanRoomId);
        }
		return newRoomVo;
	}

	@Override
	@Transactional
	public ChatRoomVo companyCreateRoom(int companyId) {
		String roomId = String.valueOf(companyId);
		if (companyId <= 0) {
			log.error("[ChatService] companyCreateRoom 호출 시 companyId가 유효하지 않습니다: {}", companyId);
			throw new IllegalArgumentException("유효하지 않은 기업 ID입니다.");
		}

        ChatRoomVo existingRoom = findRoomById(roomId); // activeChatRooms 확인 포함
		if (existingRoom != null) {
            log.info("[ChatService] 기업 ID '{}'에 대한 기존 채팅방(active/DB) 반환. 이름: {}", roomId, existingRoom.getName());
			return existingRoom;
		}

		CompanyVo company = companyService.companySelectOne(companyId);
		String roomName = (company != null && company.getCompanyName() != null && !company.getCompanyName().isEmpty()) ? 
			String.format("[%s] %s", roomId, company.getCompanyName()) :
			String.format("[%s] (기업 정보 없음)", roomId);

		ChatRoomEntity newRoomEntity = ChatRoomEntity.builder()
				.roomId(roomId)
				.name(roomName)
                .lastMessageTime(new Date())
				.build();

		ChatRoomEntity savedEntity = chatRoomRepository.save(newRoomEntity);
        ChatRoomVo newRoomVo = convertToVo(savedEntity); // convertToVo 내부에서 eventPublisher 설정됨
        if (newRoomVo != null) {
            newRoomVo.setSessions(new java.util.HashSet<>()); // 새 세션 Set 할당
            activeChatRooms.put(roomId, newRoomVo); // activeChatRooms에도 추가
            log.info("[ChatService] 새로운 기업 채팅방 DB 저장 및 activeRooms 추가 완료. ID: {}", roomId);
        }
		return newRoomVo;
	}

	/**
	 * 특정 웹소켓 세션으로 메시지를 전송합니다.
	 * 메시지는 JSON 형식으로 변환되어 전송됩니다.
	 * 이 메소드는 순수하게 메시지를 웹소켓을 통해 전달하는 역할만 하며,
	 * DB 저장 로직(이벤트 발행)은 호출하는 쪽(예: ChatRoomVo)에서 담당합니다.
	 *
	 * @param session 메시지를 전송할 대상 웹소켓 세션.
	 * @param message 전송할 메시지 객체. 타입 T는 ChatMessageVo 등 다양할 수 있습니다.
	 * @param <T> 전송할 메시지의 타입.
	 */
	@Override
	public <T> void sendMessage(WebSocketSession session, T message) {
	    try {
	        if (session != null && session.isOpen()) {
	            String json = objectMapper.writeValueAsString(message); // 메시지를 JSON 문자열로 변환합니다.
	            session.sendMessage(new TextMessage(json)); // 변환된 JSON 메시지를 TextMessage 형태로 웹소켓 세션에 전송합니다.
	            log.debug("[ChatService] 메시지 전송 완료. 세션 ID: {}, 메시지 일부: {}", session.getId(), json.length() > 100 ? json.substring(0, 100) + "..." : json);

	            // DB 저장 이벤트 발행 로직은 ChatRoomVo.handleActions로 이동.
	            // 여기서 중복 발행하면 DB에 메시지가 2번 저장될 수 있음.

	        } else {
	        	log.warn("[ChatService] sendMessage 호출 시 세션이 null이거나 닫혀있어 메시지를 전송할 수 없습니다. 세션 ID: {}", session != null ? session.getId() : "null");
	        }
	    } catch (IOException e) {
	        log.error("[ChatService] 메시지 전송 실패 (세션 ID: {}): {}. 세션을 닫습니다.", session != null ? session.getId() : "N/A", e.getMessage(), e);
	        if (session != null) {
	        	try {
		            session.close(); // 메시지 전송 실패 시 웹소켓 세션을 닫습니다.
		        } catch (IOException ex) {
		            log.error("[ChatService] 메시지 전송 실패 후 세션 종료 중 추가 오류 발생 (세션 ID: {}): {}", session.getId(), ex.getMessage(), ex);
		        }
	        }
	    }
	}

	/**
	 * 관리자용으로, 일반 회원 채팅방 목록을 DB에서 조회합니다.
	 * 각 채팅방에는 관리자의 읽지 않은 메시지 수가 계산되어 포함됩니다.
	 * 채팅방은 마지막 메시지 시간(`lastMessageTime`)을 기준으로 내림차순 정렬됩니다.
	 *
	 * @param adminId 조회하는 관리자의 ID. 읽지 않은 메시지 수를 계산하는 데 사용됩니다.
	 * @return 조건에 따라 필터링되고 정렬된 일반 회원 {@link ChatRoomVo} 리스트.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ChatRoomVo> findMemberRooms(String adminId) {
		// DB에서 email 필드가 null이 아닌 (즉, 회원 채팅방인) 방들을 마지막 메시지 시간 내림차순으로 조회합니다.
		List<ChatRoomEntity> memberRoomEntities = chatRoomRepository.findByEmailIsNotNullOrderByLastMessageTimeDesc();
		log.debug("[ChatService] findMemberRooms DB 조회. 관리자 ID: {}, 조회된 회원 채팅방 수: {}", adminId, memberRoomEntities.size());

		List<ChatRoomVo> memberRoomVos = memberRoomEntities.stream()
				.map(this::convertToVo)
				.collect(Collectors.toList());

		// 각 회원 채팅방에 대해 읽지 않은 메시지 수를 계산하고, 채팅방 이름을 가공
		for (ChatRoomVo room : memberRoomVos) {
			Optional<UserChatRoomLastRead> lastReadOpt = userChatRoomLastReadRepository.findByUserIdAndRoomId(adminId, room.getRoomId());
			Date lastReadTime = lastReadOpt.map(UserChatRoomLastRead::getLastReadTimestamp).orElse(new Date(0));
			long unreadMessages = chatMessageRepository.countByRoomIdAndSenderNotAndSendTimeAfter(room.getRoomId(), adminId, lastReadTime);
			room.setUnreadCount((int) unreadMessages);

			// 채팅방 이름 가공: '회원 [이메일]' (추후 MemberService 연동 시 이름으로 대체 가능)
			String email = room.getEmail();
			room.setName("회원 " + email);
			// TODO: MemberService에서 회원 이름을 조회할 수 있다면 아래처럼 표시
			// String memberName = memberService.findNameByEmail(email);
			// room.setName(memberName != null ? "회원 " + memberName : "회원 " + email);
		}
		return memberRoomVos;
	}

	/**
	 * 관리자용으로, 기업 회원 채팅방 목록을 DB에서 조회합니다.
	 * 각 채팅방에는 관리자의 읽지 않은 메시지 수가 계산되어 포함됩니다.
	 * 채팅방은 마지막 메시지 시간(`lastMessageTime`)을 기준으로 내림차순 정렬됩니다.
	 *
	 * @param adminId 조회하는 관리자의 ID. 읽지 않은 메시지 수를 계산하는 데 사용됩니다.
	 * @return 조건에 따라 필터링되고 정렬된 기업 회원 {@link ChatRoomVo} 리스트.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ChatRoomVo> findCompanyRooms(String adminId) {
		// DB에서 email 필드가 null인 (즉, 기업 채팅방인) 방들을 마지막 메시지 시간 내림차순으로 조회합니다.
		List<ChatRoomEntity> companyRoomEntities = chatRoomRepository.findByEmailIsNullOrderByLastMessageTimeDesc();
		log.debug("[ChatService] findCompanyRooms DB 조회. 관리자 ID: {}, 조회된 기업 채팅방 수: {}", adminId, companyRoomEntities.size());

		List<ChatRoomVo> companyRoomVos = companyRoomEntities.stream()
				.map(this::convertToVo)
				.collect(Collectors.toList());

		// 각 기업 채팅방에 대해 읽지 않은 메시지 수를 계산합니다.
		for (ChatRoomVo room : companyRoomVos) {
			Optional<UserChatRoomLastRead> lastReadOpt = userChatRoomLastReadRepository.findByUserIdAndRoomId(adminId, room.getRoomId());
			Date lastReadTime = lastReadOpt.map(UserChatRoomLastRead::getLastReadTimestamp).orElse(new Date(0));
			long unreadMessages = chatMessageRepository.countByRoomIdAndSenderNotAndSendTimeAfter(room.getRoomId(), adminId, lastReadTime);
			room.setUnreadCount((int) unreadMessages);
			log.trace("[ChatService] 기업 채팅방 ID '{}'의 읽지 않은 메시지 수: {}", room.getRoomId(), unreadMessages);
		}
		return companyRoomVos;
	}

	/**
	 * 특정 채팅방의 모든 메시지 목록을 DB에서 조회합니다. (시간순 정렬)
	 * 이 메소드는 단순히 메시지를 조회하며, 읽음 처리(lastReadTimestamp 업데이트)는 하지 않습니다.
	 * 읽음 처리가 필요한 경우는 {@link #getMessagesByRoomId(String, String)} 메소드를 사용해야 합니다.
	 *
	 * @param roomId 조회할 채팅방의 ID.
	 * @return 해당 채팅방의 {@link ChatMessageVo} 목록. 메시지가 없으면 빈 리스트를 반환합니다.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ChatMessageVo> findMessagesByRoomId(String roomId) {
	    if (roomId == null || roomId.trim().isEmpty()) {
	        log.warn("[ChatService] findMessagesByRoomId 호출 시 roomId가 null이거나 비어있습니다.");
	        return new ArrayList<>(); // 빈 리스트 반환
	    }

	    String trimmedRoomId = roomId.trim();
	    List<ChatMessageEntity> entities = chatMessageRepository.findByRoomIdOrderBySendTimeAsc(trimmedRoomId);
	    if (entities.isEmpty()) {
	        log.debug("[ChatService] DB에서 roomId '{}'에 해당하는 메시지를 찾을 수 없습니다.", trimmedRoomId);
	        return new ArrayList<>(); // 빈 리스트 반환
	    }

	    // ChatMessageEntity 리스트를 ChatMessageVo로 변환합니다.
	    List<ChatMessageVo> messages = entities.stream()
	        .map(entity -> {
	            ChatMessageVo vo = new ChatMessageVo( // ChatMessageVo 생성자 직접 호출 (타입은 TALK로 가정)
	                    ChatMessageVo.MessageType.TALK, // DB에 저장된 메시지는 TALK 타입으로 간주합니다.
	                    entity.getRoomId(),
	                    entity.getSender(),
	                    entity.getMessage(),
	                    entity.getSendTime()
	            );
	            // senderDisplayName 설정: sender 값에 따라 적절한 표시 이름을 설정
	            vo.setSenderDisplayName(getSenderDisplayName(entity.getSender()));
	            return vo;
	        })
	        .collect(Collectors.toList());

	    log.debug("[ChatService] DB에서 roomId '{}'에 대한 메시지 {}건 조회 완료.", trimmedRoomId, messages.size());
	    return messages;
	}

	/**
	 * 특정 채팅방의 메시지 목록을 DB에서 조회하고, 동시에 해당 사용자의 마지막 읽은 시간을 업데이트합니다.
	 * 메시지는 전송 시간(sendTime) 오름차순으로 정렬됩니다.
	 *
	 * @param roomId 조회할 채팅방의 ID.
	 * @param userId 메시지를 조회하는 사용자(관리자, 회원, 기업 등)의 ID. 이 ID를 기준으로 읽음 처리됩니다.
	 * @return 정렬된 {@link ChatMessageVo} 목록. 메시지가 없으면 빈 리스트를 반환합니다.
	 */
	@Override
	@Transactional // lastReadTimestamp 업데이트가 있으므로 트랜잭션 처리
	public List<ChatMessageVo> getMessagesByRoomId(String roomId, String userId) {
	    if (roomId == null || roomId.trim().isEmpty() || userId == null || userId.trim().isEmpty()) {
	        log.warn("[ChatService] getMessagesByRoomId 호출 시 roomId 또는 userId가 null이거나 비어있습니다. roomId: {}, userId: {}", roomId, userId);
	        return new ArrayList<>();
	    }
	    String trimmedRoomId = roomId.trim();
	    String trimmedUserId = userId.trim();

	    // 먼저 메시지 목록을 시간순으로 조회합니다.
	    List<ChatMessageEntity> entities = chatMessageRepository.findByRoomIdOrderBySendTimeAsc(trimmedRoomId);
	    log.debug("[ChatService] getMessagesByRoomId DB 조회. roomId: {}, userId: {}, 조회된 메시지 수: {}", trimmedRoomId, trimmedUserId, entities.size());

	    // 사용자가 메시지를 읽었으므로, 해당 사용자의 이 채팅방에 대한 마지막 읽은 시간을 현재 시간으로 업데이트/저장합니다.
	    UserChatRoomLastRead lastRead = userChatRoomLastReadRepository.findByUserIdAndRoomId(trimmedUserId, trimmedRoomId)
	            .orElse(UserChatRoomLastRead.builder()
	                    .userId(trimmedUserId)
	                    .roomId(trimmedRoomId)
	                    .build());
	    lastRead.setLastReadTimestamp(new Date());
	    userChatRoomLastReadRepository.save(lastRead); // DB에 저장/업데이트
	    log.info("[ChatService] 사용자 '{}'의 채팅방 '{}'에 대한 lastReadTimestamp 업데이트 완료.", trimmedUserId, trimmedRoomId);

	    // ChatMessageEntity 리스트를 ChatMessageVo 리스트로 변환합니다.
	    return entities.stream()
	            .map(entity -> {
	                ChatMessageVo vo = new ChatMessageVo(
	                        ChatMessageVo.MessageType.TALK, // DB에 저장된 메시지는 TALK 타입으로 간주
	                        entity.getRoomId(),
	                        entity.getSender(),
	                        entity.getMessage(),
	                        entity.getSendTime()
	                );
	                // senderDisplayName 설정: sender 값에 따라 적절한 표시 이름을 설정
	                vo.setSenderDisplayName(getSenderDisplayName(entity.getSender()));
	                return vo;
	            })
	            .collect(Collectors.toList());
	}

    /**
     * sender 값에 따라 적절한 senderDisplayName을 반환하는 헬퍼 메소드입니다.
     * @param sender 메시지 발신자 (관리자: "관리자", 기업회원: companyId, 개인회원: memberEmail)
     * @return 표시할 발신자 이름
     */
    private String getSenderDisplayName(String sender) {
        if (sender == null || sender.trim().isEmpty()) {
            return "알 수 없음";
        }
        
        String trimmedSender = sender.trim();
        
        // 1. 관리자인 경우
        if ("관리자".equals(trimmedSender)) {
            return "관리자";
        }
        
        // 2. 기업회원인 경우 (sender가 숫자로만 구성된 경우 companyId로 간주)
        if (trimmedSender.matches("\\d+")) {
            try {
                int companyId = Integer.parseInt(trimmedSender);
                CompanyVo company = companyService.companySelectOne(companyId);
                if (company != null && company.getCompanyName() != null && !company.getCompanyName().trim().isEmpty()) {
                    return company.getCompanyName();
                } else {
                    log.debug("[ChatService] companyId '{}'에 해당하는 기업 정보를 찾을 수 없거나 기업명이 비어있습니다.", companyId);
                    return "기업회원 " + trimmedSender; // 기업 정보를 찾을 수 없는 경우 fallback
                }
            } catch (NumberFormatException e) {
                log.warn("[ChatService] sender '{}'를 companyId로 파싱하는 중 오류 발생: {}", trimmedSender, e.getMessage());
                return trimmedSender; // 파싱 실패 시 원본 sender 반환
            }
        }
        
        // 3. 개인회원인 경우 (이메일 형식으로 간주)
        // 현재는 이메일을 그대로 표시하지만, 필요시 MemberService를 통해 회원 이름을 조회할 수 있습니다.
        if (trimmedSender.contains("@")) {
            // TODO: 필요시 MemberService를 주입하여 이메일로 회원 정보를 조회하고 실제 이름을 반환
            return trimmedSender; // 현재는 이메일을 그대로 표시
        }
        
        // 4. 기타 경우 (알 수 없는 형식)
        return trimmedSender;
    }

    // ChatMessageSavedListener에서 lastMessageTime 업데이트 시 activeChatRooms의 Vo도 업데이트 필요할 수 있음:
    /**
     * (ChatMessageSavedListener에서 호출될 수 있는 메소드 예시)
     * 특정 채팅방의 마지막 메시지 시간을 업데이트합니다 (DB 및 activeChatRooms 맵 모두).
     * @param roomId 업데이트할 채팅방 ID
     * @param lastMessageTime 새로운 마지막 메시지 시간
     */
    @Transactional
    public void updateRoomLastMessageTime(String roomId, Date lastMessageTime) {
        chatRoomRepository.findById(roomId).ifPresent(entity -> {
            entity.setLastMessageTime(lastMessageTime);
            chatRoomRepository.save(entity);
            log.debug("[ChatService] 채팅방 ID '{}'의 lastMessageTime DB 업데이트: {}", roomId, lastMessageTime);
        });
        ChatRoomVo activeRoom = activeChatRooms.get(roomId);
        if (activeRoom != null) {
            activeRoom.setLastMessageTime(lastMessageTime);
            log.debug("[ChatService] activeChatRoom ID '{}'의 lastMessageTime 메모리 업데이트: {}", roomId, lastMessageTime);
        }
    }

}
