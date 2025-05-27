package com.joblessfriend.jobfinder.chat.domain;

import java.util.HashSet;
import java.util.Set;
// import java.util.UUID; // 현재 UUID 직접 사용 부분은 보이지 않아 주석 처리 또는 삭제 가능
import java.util.stream.Collectors; // 현재 직접 사용 부분은 보이지 않지만, 유틸리티성으로 남겨둘 수 있음
import java.util.Date;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.context.ApplicationEventPublisher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joblessfriend.jobfinder.chat.event.ChatMessageSavedEvent;
import com.joblessfriend.jobfinder.chat.service.ChatService;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 채팅방의 정보를 표현하는 Value Object(VO) 클래스입니다.
 * 채팅방의 고유 ID, 이름, 참여자 정보, 마지막 메시지 시간, 읽지 않은 메시지 수,
 * 그리고 현재 연결된 웹소켓 세션 목록 등을 관리합니다.
 */
@Slf4j // Lombok: SLF4J 로거를 자동으로 생성합니다. (log 변수 사용 가능)
@Getter // Lombok: 모든 필드에 대한 getter 메소드를 자동으로 생성합니다.
@Setter // Lombok: 모든 필드에 대한 setter 메소드를 자동으로 생성합니다.
public class ChatRoomVo {

	// 채팅방의 고유 식별자입니다. 사용자의 이메일 또는 기업 ID 등이 사용될 수 있습니다.
	private String roomId;
	// 채팅방의 이름입니다. (예: "test@example.com의 채팅방", "[123] 기업명")
	private String name;
	// 회원 채팅방의 경우, 해당 회원의 이메일 주소를 저장합니다.
	private String email;
	// 해당 채팅방의 마지막 메시지가 전송된 시간입니다. 채팅방 목록 정렬 등에 사용됩니다.
	private Date lastMessageTime;
	// 현재 사용자가 해당 채팅방에서 읽지 않은 메시지의 수를 나타냅니다. (주로 관리자 화면에서 사용)
	private int unreadCount;
	
	// 이 채팅방에 현재 연결되어 있는 웹소켓 세션들의 집합입니다.
	// @JsonIgnore 어노테이션을 사용하여, 이 필드는 JSON으로 변환될 때 제외됩니다.
	// (클라이언트에게 세션 내부 정보를 직접 노출하지 않기 위함)
	@JsonIgnore
	private Set<WebSocketSession> sessions = new HashSet<>();

    // ApplicationEventPublisher를 ChatRoomVo에서 직접 사용하기 위해 필드 추가 (생성자나 setter로 주입 필요)
    // 다만, ChatRoomVo는 주로 데이터 전달 객체(DTO/VO)로 사용되므로, 여기에 직접 ApplicationEventPublisher를 주입하는 것은
    // 설계상 조금 어색할 수 있습니다. 이벤트 발행은 ChatService나 WebSocketHandler에서 수행하는 것이 더 일반적입니다.
    // 여기서는 요청에 따라 ChatRoomVo에서 이벤트를 발행하도록 수정합니다.
    @JsonIgnore
    private transient ApplicationEventPublisher eventPublisher; // transient: 직렬화 제외

	/**
	 * ChatRoomVo 객체를 생성하는 빌더 패턴 기반의 생성자입니다.
	 * roomId, name, email을 파라미터로 받아 채팅방 객체를 초기화합니다.
	 * lastMessageTime은 객체 생성 시 현재 시간으로 초기화됩니다.
	 *
	 * @param roomId 채팅방의 고유 ID
	 * @param name 채팅방의 이름
	 * @param email 채팅방과 연관된 사용자의 이메일 (회원 채팅방의 경우)
	 */
	@Builder // Lombok: 빌더 패턴 코드를 자동으로 생성합니다.
	public ChatRoomVo(String roomId, String name, String email /*, ApplicationEventPublisher eventPublisher - 빌더에 직접 넣기보다 setter 사용 권장 */) {
		this.roomId = roomId;
		this.name = name;
		this.email = email;
		this.lastMessageTime = new Date();
		// this.eventPublisher = eventPublisher; // 빌더를 통해 주입받는다면 여기에 추가
	}

    // ApplicationEventPublisher를 설정하기 위한 Setter 메소드
    // ChatServiceImpl에서 ChatRoomVo 객체 생성 후 이 메소드를 통해 eventPublisher를 주입합니다.
    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

	/**
	 * 웹소켓 세션으로부터 수신된 메시지를 처리하는 핵심 로직입니다.
	 * 메시지 타입(ENTER, TALK)에 따라 적절한 동작을 수행합니다.
	 * ENTER: 새로운 사용자가 채팅방에 입장했음을 처리하고 세션 목록에 추가합니다.
	 * TALK: 일반 대화 메시지를 수신하여 마지막 메시지 시간을 갱신합니다.
	 * 모든 메시지 처리 후, 해당 채팅방의 모든 활성 세션에 메시지를 브로드캐스트합니다.
	 *
	 * @param session 메시지를 수신한 웹소켓 세션
	 * @param chatMessage 수신된 채팅 메시지 객체 (ChatMessageVo)
	 * @param chatService 메시지 전송 등 채팅 관련 서비스를 제공하는 객체
	 */
	public void handleActions(WebSocketSession session, ChatMessageVo chatMessage, ChatService chatService) {
		if (chatMessage.getType().equals(ChatMessageVo.MessageType.ENTER)) {
			// 사용자가 채팅방에 입장한 경우의 처리입니다.
			// 기존 세션 목록에 중복되지 않는 경우에만 새로운 세션을 추가합니다.
			boolean isNewSession = sessions.stream()
				.noneMatch(existingSession -> 
					existingSession.getId().equals(session.getId()));
			
			if (isNewSession) {
				sessions.add(session); // 새로운 세션을 현재 채팅방의 세션 목록에 추가합니다.
				log.info("[ChatRoom '{}'] 새로운 세션 입장. 세션 ID: {}", roomId, session.getId());
			}
			// 입장 메시지에 대해서는 별도의 메시지 브로드캐스팅이 필요하면 여기서 처리 가능
			// 현재는 TALK 메시지만 sendMessage를 통해 브로드캐스팅됩니다.
		} else if (chatMessage.getType().equals(ChatMessageVo.MessageType.TALK)) {
			// 일반 대화 메시지를 수신한 경우의 처리입니다.
			// 채팅방의 마지막 메시지 시간을 현재 시간으로 업데이트합니다.
			this.lastMessageTime = new Date();
			log.debug("[ChatRoom '{}'] TALK 메시지 수신. 마지막 메시지 시간 업데이트.", roomId);

            // ChatMessageSavedEvent 발행 로직 추가
            // 이벤트 발행은 ChatRoomVo 생성 시 주입된 eventPublisher를 사용
            if (eventPublisher == null) {
                log.warn("[ChatRoom '{}'] TALK 메시지 처리 중 eventPublisher가 null입니다. ChatMessageSavedEvent를 발행할 수 없습니다.", roomId);
            } else if (!chatMessage.isSaved()) { // 아직 저장되지 않은 메시지만 이벤트 발행
                // 중요: 이벤트 발행 전에 isSaved를 true로 설정하면 리스너에서 다시 이벤트를 발행하는 것을 방지할 수 있지만,
                // 리스너가 실패했을 경우를 고려하면, 리스너에서 성공적으로 저장 후 isSaved를 설정하는 것이 더 안전할 수 있습니다.
                // 여기서는 일단 메시지 자체의 상태를 변경하고 이벤트를 발행합니다.
                // 또는, ChatMessageSavedEvent 생성자에 "원본 ChatMessageVo"를 전달하고, 
                // 리스너가 성공 시 원본 객체의 isSaved를 true로 설정하게 할 수도 있습니다.
                // 현재 ChatMessageVo의 saved 기본값은 false이고, 리스너에서 별도 설정하지 않으므로, 여기서 상태 변경 없이 발행합니다.
                // 리스너가 처리 후 상태를 변경하거나, 중복 방지 로직이 리스너 쪽에 있어야 합니다.
                // ChatServiceImpl의 sendMessage에서 이벤트 발행 로직을 제거했으므로, 여기서 발행해야 DB에 저장됩니다.

                eventPublisher.publishEvent(new ChatMessageSavedEvent(this, chatMessage));
                log.debug("[ChatRoom '{}'] ChatMessageSavedEvent 발행. 발신자: {}, 메시지: {}", roomId, chatMessage.getSender(), chatMessage.getMessage());
                // chatMessage.setSaved(true); // 이벤트 발행 후 saved 상태 변경 (주의: 리스너 성공 보장 안됨)
                                          // ChatMessageSavedListener에서 이미 lastMessageTime을 DB에 업데이트하고 있으므로,
                                          // 이중 업데이트 방지를 위해 여기서는 lastMessageTime 필드만 업데이트.
            }
			log.debug("[ChatRoom '{}'] TALK 메시지 수신. 발신자: {}, 메시지: {}", roomId, chatMessage.getSender(), chatMessage.getMessage());
		}
		
		// 비활성(닫힌) 세션들을 목록에서 제거합니다.
		sessions.removeIf(s -> !s.isOpen());
		
		// 수신된 메시지(ENTER 또는 TALK)를 해당 채팅방의 모든 활성 세션에 전송합니다.
		// ENTER 메시지도 모든 참여자에게 알릴 필요가 있다면, 이 로직이 적절합니다.
		// 만약 ENTER 메시지는 특정 대상에게만 보내야 한다면 조건부 로직이 필요합니다.
		sendMessage(chatMessage, chatService);
	}

	/**
	 * 현재 채팅방에 연결된 모든 활성 웹소켓 세션에 주어진 메시지를 전송(브로드캐스트)합니다.
	 * 메시지 전송 전에 비활성 세션을 정리합니다.
	 *
	 * @param message 전송할 메시지 객체 (타입 T는 ChatMessageVo 등 다양할 수 있음)
	 * @param chatService 메시지 전송 기능을 제공하는 ChatService의 인스턴스
	 * @param <T> 전송할 메시지의 타입
	 */
	public <T> void sendMessage(T message, ChatService chatService) {
		// 메시지 전송 전, 현재 열려있지 않은(비활성) 세션들을 목록에서 제거합니다.
		sessions.removeIf(s -> !s.isOpen());
		
		// 현재 열려있는 모든 세션에 대해 반복 작업을 수행합니다.
		sessions.stream()
			.filter(WebSocketSession::isOpen) // 세션이 열려있는지 다시 한번 확인합니다.
			.forEach(session -> { // 각 활성 세션에 대해 다음 작업을 수행합니다.
				try {
					// ChatService를 통해 해당 세션으로 메시지를 전송합니다.
					chatService.sendMessage(session, message);
					log.debug("[ChatRoom '{}'] 세션 ID '{}'(으)로 메시지 전송 완료.", roomId, session.getId());
				} catch (Exception e) {
					// 메시지 전송 중 예외 발생 시 에러 로그를 기록합니다.
					log.error("[ChatRoom '{}'] 세션 ID '{}'(으)로 메시지 전송 실패. 에러: {}", 
						roomId, session.getId(), e.getMessage(), e); // 예외 스택 트레이스도 포함하면 디버깅에 용이
				}
			});
	}

}
