package com.joblessfriend.jobfinder.chat.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async; // 비동기 처리를 위한 어노테이션
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 관리를 위한 어노테이션

import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.entity.ChatMessageEntity;
import com.joblessfriend.jobfinder.chat.entity.ChatRoomEntity; // ChatRoomEntity 임포트 추가
import com.joblessfriend.jobfinder.chat.repository.ChatMessageRepository;
import com.joblessfriend.jobfinder.chat.repository.ChatRoomRepository; // ChatRoomRepository 임포트 추가
import com.joblessfriend.jobfinder.chat.service.ChatService; // ChatService 주입을 위해 import

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date; // Date 임포트 추가

/**
 * {@link ChatMessageSavedEvent}를 수신하여 채팅 메시지를 데이터베이스에 저장하는 이벤트 리스너 클래스입니다.
 * 메시지 저장 작업은 비동기적으로 처리될 수 있으며, 관련 채팅방의 마지막 메시지 시간도 업데이트합니다.
 */
@Slf4j
@Component // 이 클래스를 Spring의 컴포넌트로 등록하여 빈으로 관리되도록 합니다.
@RequiredArgsConstructor // Lombok: final 필드에 대한 생성자를 자동으로 생성합니다.
public class ChatMessageSavedListener {

    private final ChatMessageRepository chatMessageRepository; // 채팅 메시지 DB 작업을 위한 리포지토리
    private final ChatRoomRepository chatRoomRepository; // 채팅방 DB 작업을 위한 리포지토리
    private final ChatService chatService; // ChatService 의존성 추가

    /**
     * {@link ChatMessageSavedEvent} 발생 시 호출되는 메소드입니다.
     * 전달받은 {@link ChatMessageVo}를 {@link ChatMessageEntity}로 변환하여 DB에 저장합니다.
     * 또한, 해당 메시지가 속한 채팅방의 {@code lastMessageTime}을 현재 시간으로 업데이트하고,
     * {@link ChatService}를 통해 메모리에 있는 {@code ChatRoomVo}의 정보도 업데이트합니다.
     * 이 작업은 비동기(@Async) 및 트랜잭션(@Transactional)으로 처리될 수 있습니다.
     *
     * @param event 수신된 {@link ChatMessageSavedEvent} 객체. 저장할 메시지 정보를 포함합니다.
     */
    @EventListener // 이 메소드가 ApplicationEvent를 처리하는 리스너임을 나타냅니다.
    @Transactional // 이 메소드 내의 DB 작업들이 하나의 트랜잭션으로 묶여 처리됩니다.
    // @Async // 이 어노테이션을 활성화하면 메시지 저장 작업이 별도의 스레드에서 비동기적으로 처리됩니다. (TaskExecutor 설정 필요)
    public void handleChatMessageSavedEvent(ChatMessageSavedEvent event) {
        ChatMessageVo chatMessageVo = event.getChatMessage();
        if (chatMessageVo == null) {
            log.warn("[ChatMessageSavedListener] 수신된 ChatMessageVo가 null입니다. 처리를 건너뜁니다.");
            return;
        }

        // 서비스에서 메시지 저장 후 이벤트를 발행하므로, 리스너는 항상 후속 처리를 진행한다고 가정합니다.
        // 따라서 isSavedByListener 또는 isSaved 와 같은 플래그를 여기서 확인할 필요가 없습니다.
        // 문제가 되었던 if (chatMessageVo.isSavedByListener()) { ... } 블록을 제거합니다.

        log.debug("[ChatMessageSavedListener] ChatMessageSavedEvent 수신 (메시지 저장은 서비스에서 이미 수행됨). 메시지: {}, 발신자: {}, 방 ID: {}", 
                chatMessageVo.getMessage(), chatMessageVo.getSender(), chatMessageVo.getRoomId());

                    Date messageSendTime = chatMessageVo.getSendTime() != null ? chatMessageVo.getSendTime() : new Date();

        // ChatMessageEntity 빌드는 필요시 유지 (예: 다른 정보 참조), 하지만 save는 하지 않음.
        // ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
        //         .roomId(chatMessageVo.getRoomId())
        //         .sender(chatMessageVo.getSender())
        //         .message(chatMessageVo.getMessage())
        //         .sendTime(messageSendTime)
        //         .build(); // ID는 서비스에서 저장 시 이미 할당되었을 것임.

        try {
            // 1. 채팅 메시지 DB 저장 로직 제거
            // chatMessageRepository.save(chatMessageEntity); // 이 줄을 삭제/주석 처리
            // log.info("[ChatMessageSavedListener] 채팅 메시지 DB 저장 완료. ID: {}, 방 ID: {}", chatMessageEntity.getId(), chatMessageEntity.getRoomId()); // 관련 로그도 삭제/주석

            // 2. 원본 ChatMessageVo의 saved 상태 변경 로직 제거 (서비스에서 담당)
            // chatMessageVo.setSaved(true); // 이 줄을 삭제/주석 처리
            // log.debug("[ChatMessageSavedListener] ChatMessageVo (roomId: {}, sender: {})의 saved 상태를 true로 변경.", chatMessageVo.getRoomId(), chatMessageVo.getSender());

            // 이제 이 리스너는 주로 채팅방의 lastMessageTime 업데이트 등을 담당합니다.
            // 이 작업은 ChatMessageVo에 있는 정보 (roomId, sendTime)를 기반으로 수행됩니다.
            // 서비스에서 메시지를 저장한 후, 해당 정보(특히 정확한 sendTime)가 ChatMessageVo에 반영되어 이벤트로 전달되어야 합니다.

            if (chatMessageVo.getRoomId() != null && !chatMessageVo.getRoomId().isEmpty()) {
                // DB의 ChatRoomEntity 업데이트
                // chatMessageVo에서 roomId와 messageSendTime (서비스에서 저장 시 확정된 시간)을 사용
                ChatRoomEntity roomEntityOptional = chatRoomRepository.findById(chatMessageVo.getRoomId()).orElse(null);
                if (roomEntityOptional != null) {
                    roomEntityOptional.setLastMessageTime(messageSendTime);
                    chatRoomRepository.save(roomEntityOptional); // 채팅방 정보 업데이트는 유지
                    log.info("[ChatMessageSavedListener] 채팅방 ID '{}'의 lastMessageTime DB 업데이트 완료 (이벤트 기반): {}", roomEntityOptional.getRoomId(), roomEntityOptional.getLastMessageTime());
                    
                    // ChatServiceImpl의 activeChatRooms에 있는 ChatRoomVo도 업데이트
                    chatService.updateRoomLastMessageTime(roomEntityOptional.getRoomId(), roomEntityOptional.getLastMessageTime());
                } else {
                    log.warn("[ChatMessageSavedListener] ID '{}'에 해당하는 채팅방을 DB에서 찾을 수 없어 lastMessageTime을 업데이트할 수 없습니다.", chatMessageVo.getRoomId());
                }
            } else {
                 log.warn("[ChatMessageSavedListener] 메시지에 roomId가 없어 채팅방의 lastMessageTime을 업데이트할 수 없습니다. 발신자: {}, 메시지: {}", 
                    chatMessageVo.getSender(), chatMessageVo.getMessage());
            }
            
            // 리스너가 특정 작업을 완료했음을 표시하는 플래그 (필요하다면)
            // chatMessageVo.setSavedByListener(true); // 예시: 리스너 처리 완료 플래그

        } catch (Exception e) {
            log.error("[ChatMessageSavedListener] 채팅방 업데이트 중 오류 발생 (메시지 저장은 서비스에서 이미 처리됨). 메시지: {}, 에러: {}", 
                    chatMessageVo.getMessage(), e.getMessage(), e);
        }
    }
} 