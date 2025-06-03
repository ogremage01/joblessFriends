package com.joblessfriend.jobfinder.chat.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.entity.ChatRoomEntity;
import com.joblessfriend.jobfinder.chat.repository.ChatRoomRepository;
import com.joblessfriend.jobfinder.chat.service.ChatService;
import com.joblessfriend.jobfinder.chat.repository.UserChatRoomLastReadRepository;
import com.joblessfriend.jobfinder.chat.domain.UserChatRoomLastRead;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * {@link ChatMessageSavedEvent}를 수신하여 채팅방 정보를 업데이트하는 이벤트 리스너 클래스입니다.
 * 관련 채팅방의 마지막 메시지 시간을 업데이트하고 관리자 메시지에 대한 읽음 처리를 수행합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageSavedListener {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final UserChatRoomLastReadRepository userChatRoomLastReadRepository;

    /**
     * {@link ChatMessageSavedEvent} 발생 시 호출되는 메소드입니다.
     * 채팅방의 lastMessageTime을 업데이트하고 관리자 메시지인 경우 읽음 처리를 수행합니다.
     *
     * @param event 수신된 {@link ChatMessageSavedEvent} 객체. 저장할 메시지 정보를 포함합니다.
     */
    @EventListener
    @Transactional
    public void handleChatMessageSavedEvent(ChatMessageSavedEvent event) {
        ChatMessageVo chatMessageVo = event.getChatMessage();
        if (chatMessageVo == null) {
            log.warn("[ChatMessageSavedListener] 수신된 ChatMessageVo가 null입니다. 처리를 건너뜁니다.");
            return;
        }

        log.debug("[ChatMessageSavedListener] ChatMessageSavedEvent 수신. 메시지: {}, 발신자: {}, 방 ID: {}", 
                chatMessageVo.getMessage(), chatMessageVo.getSender(), chatMessageVo.getRoomId());

        Date messageSendTime = chatMessageVo.getSendTime() != null ? chatMessageVo.getSendTime() : new Date();

        try {
            // 1. 채팅방의 lastMessageTime 업데이트
            if (chatMessageVo.getRoomId() != null && !chatMessageVo.getRoomId().isEmpty()) {
                ChatRoomEntity roomEntityOptional = chatRoomRepository.findById(chatMessageVo.getRoomId()).orElse(null);
                if (roomEntityOptional != null) {
                    roomEntityOptional.setLastMessageTime(messageSendTime);
                    chatRoomRepository.save(roomEntityOptional);
                    log.info("[ChatMessageSavedListener] 채팅방 ID '{}'의 lastMessageTime DB 업데이트 완료: {}", 
                            roomEntityOptional.getRoomId(), roomEntityOptional.getLastMessageTime());
                    
                    chatService.updateRoomLastMessageTime(roomEntityOptional.getRoomId(), roomEntityOptional.getLastMessageTime());
                } else {
                    log.warn("[ChatMessageSavedListener] ID '{}'에 해당하는 채팅방을 DB에서 찾을 수 없어 lastMessageTime을 업데이트할 수 없습니다.", 
                            chatMessageVo.getRoomId());
                }
            } else {
                 log.warn("[ChatMessageSavedListener] 메시지에 roomId가 없어 채팅방의 lastMessageTime을 업데이트할 수 없습니다. 발신자: {}, 메시지: {}", 
                    chatMessageVo.getSender(), chatMessageVo.getMessage());
            }

            // 2. 관리자가 보낸 메시지인 경우 즉시 읽음 처리
            if (isAdminSender(chatMessageVo.getSender())) {
                String adminId = extractAdminIdForReadOperation(chatMessageVo.getSender());
                markAsReadForAdmin(chatMessageVo.getRoomId(), adminId, messageSendTime);
                log.debug("[ChatMessageSavedListener] 관리자 메시지에 대한 읽음 처리 완료. 방 ID: {}, 관리자: {}", 
                        chatMessageVo.getRoomId(), adminId);
            }

        } catch (Exception e) {
            log.error("[ChatMessageSavedListener] 채팅방 업데이트 중 오류 발생. 메시지: {}, 에러: {}", 
                    chatMessageVo.getMessage(), e.getMessage(), e);
        }
    }

    /**
     * 발신자가 관리자인지 확인하는 헬퍼 메서드
     * @param sender 발신자 정보
     * @return 관리자인 경우 true, 아닌 경우 false
     */
    private boolean isAdminSender(String sender) {
        return sender != null && sender.equals("관리자");
    }

    /**
     * 관리자 ID를 추출하는 헬퍼 메서드
     * @param sender 발신자 정보
     * @return 관리자 ID
     */
    private String extractAdminIdForReadOperation(String sender) {
        // 기본 관리자 ID 사용
        return "admin";
    }

    /**
     * 관리자가 메시지를 보낸 경우 즉시 읽음 처리
     * @param roomId 채팅방 ID
     * @param adminId 관리자 ID
     * @param messageTime 메시지 전송 시간
     */
    private void markAsReadForAdmin(String roomId, String adminId, Date messageTime) {
        try {
            UserChatRoomLastRead lastRead = userChatRoomLastReadRepository.findByUserIdAndRoomId(adminId, roomId)
                    .orElse(UserChatRoomLastRead.builder()
                            .userId(adminId)
                            .roomId(roomId)
                            .build());
            
            lastRead.setLastReadTimestamp(messageTime);
            userChatRoomLastReadRepository.save(lastRead);
            
            log.debug("[ChatMessageSavedListener] 관리자 읽음 처리 완료. 관리자: {}, 방 ID: {}, 읽음 시간: {}", 
                    adminId, roomId, messageTime);
                    
        } catch (Exception e) {
            log.error("[ChatMessageSavedListener] 관리자 읽음 처리 중 오류 발생. 관리자: {}, 방 ID: {}, 에러: {}", 
                    adminId, roomId, e.getMessage(), e);
        }
    }
} 